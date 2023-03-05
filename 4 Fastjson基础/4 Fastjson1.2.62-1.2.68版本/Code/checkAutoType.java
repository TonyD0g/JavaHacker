public Class<?> checkAutoType(String typeName, Class<?> expectClass, int features) {
        if (typeName == null) {
            return null;
        }

        if (autoTypeCheckHandlers != null) {
            for (AutoTypeCheckHandler h : autoTypeCheckHandlers) {
                Class<?> type = h.handler(typeName, expectClass, features);
                if (type != null) {
                    return type;
                }
            }
        }
        // 判断是否开启了safeMode,safeMode开启后,完全禁用AutoType即@type,就无Fastjson漏洞了
        final int safeModeMask = Feature.SafeMode.mask;
        boolean safeMode = this.safeMode
                || (features & safeModeMask) != 0
                || (JSON.DEFAULT_PARSER_FEATURE & safeModeMask) != 0;
        if (safeMode) {
            throw new JSONException("safeMode not support autoType : " + typeName);
        }

        if (typeName.length() >= 192 || typeName.length() < 3) {
            throw new JSONException("autoType is not support. " + typeName);
        }
        // 给expectClassFlag进行赋值
        final boolean expectClassFlag;
        if (expectClass == null) {
            expectClassFlag = false;
        } else {
            if (expectClass == Object.class
                    || expectClass == Serializable.class
                    || expectClass == Cloneable.class
                    || expectClass == Closeable.class
                    || expectClass == EventListener.class
                    || expectClass == Iterable.class
                    || expectClass == Collection.class
                    ) {
                expectClassFlag = false;
            } else {
                expectClassFlag = true;
            }
        }

        String className = typeName.replace('$', '.');
        Class<?> clazz;

        final long BASIC = 0xcbf29ce484222325L;
        final long PRIME = 0x100000001b3L;

        // className以L为开头，直接被干掉
        final long h1 = (BASIC ^ className.charAt(0)) * PRIME;
        if (h1 == 0xaf64164c86024f1aL) { // [
            throw new JSONException("autoType is not support. " + typeName);
        }
        // className以;为结尾,直接被干掉
        if ((h1 ^ className.charAt(className.length() - 1)) * PRIME == 0x9198507b5af98f0L) {
            throw new JSONException("autoType is not support. " + typeName);
        }

        final long h3 = (((((BASIC ^ className.charAt(0))
                * PRIME)
                ^ className.charAt(1))
                * PRIME)
                ^ className.charAt(2))
                * PRIME;

        long fullHash = TypeUtils.fnv1a_64(className);
        boolean internalWhite = Arrays.binarySearch(INTERNAL_WHITELIST_HASHCODES,  fullHash) >= 0;
        // 跑名单,跑到就被干掉
        if (internalDenyHashCodes != null) {
            long hash = h3;
            for (int i = 3; i < className.length(); ++i) {
                hash ^= className.charAt(i);
                hash *= PRIME;
                if (Arrays.binarySearch(internalDenyHashCodes, hash) >= 0) {
                    throw new JSONException("autoType is not support. " + typeName);
                }
            }
        }
        // 
        if ((!internalWhite) && (autoTypeSupport || expectClassFlag)) {
            long hash = h3;
            for (int i = 3; i < className.length(); ++i) {
                hash ^= className.charAt(i);
                hash *= PRIME;
                if (Arrays.binarySearch(acceptHashCodes, hash) >= 0) {
                    clazz = TypeUtils.loadClass(typeName, defaultClassLoader, true);
                    if (clazz != null) {
                        return clazz;
                    }
                }
                if (Arrays.binarySearch(denyHashCodes, hash) >= 0 && TypeUtils.getClassFromMapping(typeName) == null) {
                    if (Arrays.binarySearch(acceptHashCodes, fullHash) >= 0) {
                        continue;
                    }

                    throw new JSONException("autoType is not support. " + typeName);
                }
            }
        }

        clazz = TypeUtils.getClassFromMapping(typeName);

        if (clazz == null) {
            clazz = deserializers.findClass(typeName);
        }

        if (clazz == null) {
            clazz = typeMapping.get(typeName);
        }
        // 如果是白名单中的则加载
        if (internalWhite) {
            clazz = TypeUtils.loadClass(typeName, defaultClassLoader, true);
        }
        // 1.clazz不为null,不为expectClass 类的继承类且不是 HashMap 类型。抛出异常
        if (clazz != null) {
            if (expectClass != null
                    && clazz != java.util.HashMap.class
                    && !expectClass.isAssignableFrom(clazz)) {
                throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
            }

            return clazz;
        }
        // 当autoTypeSupport未开启时，先黑名单过滤，再白名单过滤，若白名单匹配上则直接加载该类，否则报错
        if (!autoTypeSupport) {
            long hash = h3;
            for (int i = 3; i < className.length(); ++i) {
                char c = className.charAt(i);
                hash ^= c;
                hash *= PRIME;

                if (Arrays.binarySearch(denyHashCodes, hash) >= 0) {
                    throw new JSONException("autoType is not support. " + typeName);
                }

                // white list
                if (Arrays.binarySearch(acceptHashCodes, hash) >= 0) {
                    clazz = TypeUtils.loadClass(typeName, defaultClassLoader, true);

                    if (expectClass != null && expectClass.isAssignableFrom(clazz)) {
                        throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
                    }

                    return clazz;
                }
            }
        }

        boolean jsonType = false;
        InputStream is = null;
        try {
            String resource = typeName.replace('.', '/') + ".class";
            if (defaultClassLoader != null) {
                is = defaultClassLoader.getResourceAsStream(resource);
            } else {
                is = ParserConfig.class.getClassLoader().getResourceAsStream(resource);
            }
            if (is != null) {
                ClassReader classReader = new ClassReader(is, true);
                TypeCollector visitor = new TypeCollector("<clinit>", new Class[0]);
                classReader.accept(visitor);
                jsonType = visitor.hasJsonType();
            }
        } catch (Exception e) {
            // skip
        } finally {
            IOUtils.close(is);
        }

        final int mask = Feature.SupportAutoType.mask;
        boolean autoTypeSupport = this.autoTypeSupport
                || (features & mask) != 0
                || (JSON.DEFAULT_PARSER_FEATURE & mask) != 0;

        if (autoTypeSupport || jsonType || expectClassFlag) {
            // 如果AutoType关闭且jsonType为false,则不开启cacheClass：
            boolean cacheClass = autoTypeSupport || jsonType;
            clazz = TypeUtils.loadClass(typeName, defaultClassLoader, cacheClass);
        }

        if (clazz != null) {
            if (jsonType) {
                // 添加到map缓存
                TypeUtils.addMapping(typeName, clazz);
                return clazz;
            }
            // clazz 是 ClassLoader、DataSource，RowSet的子类，则被干掉
            // 是过滤大多数JNDI注入Gadget的机制
            if (ClassLoader.class.isAssignableFrom(clazz) // classloader is danger
                    || javax.sql.DataSource.class.isAssignableFrom(clazz) // dataSource can load jdbc driver
                    || javax.sql.RowSet.class.isAssignableFrom(clazz) //
                    ) {
                throw new JSONException("autoType is not support. " + typeName);
            }

            if (expectClass != null) {
                // expectClass绕过checkAutoType的核心： clazz是expectClass的子类。则return clazz;
                // return clazz;后是一系列的反序列化操作
                if (expectClass.isAssignableFrom(clazz)) {
                    TypeUtils.addMapping(typeName, clazz);
                    return clazz;
                } else {
                    throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
                }
            }

            JavaBeanInfo beanInfo = JavaBeanInfo.build(clazz, clazz, propertyNamingStrategy);
            if (beanInfo.creatorConstructor != null && autoTypeSupport) {
                throw new JSONException("autoType is not support. " + typeName);
            }
        }

        if (!autoTypeSupport) {
            throw new JSONException("autoType is not support. " + typeName);
        }

        if (clazz != null) {
            TypeUtils.addMapping(typeName, clazz);
        }

        return clazz;
    }