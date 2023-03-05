public Class<?> checkAutoType(String typeName, Class<?> expectClass) {
    if (typeName == null) {
        return null;
    }
 
    final String className = typeName.replace('$', '.');
 
    // autoTypeSupport默认为False
    // 当autoTypeSupport开启时，先白名单过滤，匹配成功即可加载该类，否则再黑名单过滤
    if (autoTypeSupport || expectClass != null) {
        for (int i = 0; i < acceptList.length; ++i) {
            String accept = acceptList[i];
            if (className.startsWith(accept)) {
                return TypeUtils.loadClass(typeName, defaultClassLoader);
            }
        }
 
        for (int i = 0; i < denyList.length; ++i) {
            String deny = denyList[i];
            if (className.startsWith(deny)) {
                throw new JSONException("autoType is not support. " + typeName);
            }
        }
    }
 
    // 从Map缓存中获取类，注意这是后面版本的漏洞点
    Class<?> clazz = TypeUtils.getClassFromMapping(typeName);
    if (clazz == null) {
        clazz = deserializers.findClass(typeName);
    }
 
    if (clazz != null) {
        if (expectClass != null && !expectClass.isAssignableFrom(clazz)) {
            throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
        }
 
        return clazz;
    }
 
    // 当autoTypeSupport未开启时，先黑名单过滤，再白名单过滤，若白名单匹配上则直接加载该类，否则报错
    if (!autoTypeSupport) {
        for (int i = 0; i < denyList.length; ++i) {
            String deny = denyList[i];
            if (className.startsWith(deny)) {
                throw new JSONException("autoType is not support. " + typeName);
            }
        }
        for (int i = 0; i < acceptList.length; ++i) {
            String accept = acceptList[i];
            if (className.startsWith(accept)) {
                clazz = TypeUtils.loadClass(typeName, defaultClassLoader);
 
                if (expectClass != null && expectClass.isAssignableFrom(clazz)) {
                    throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
                }
                return clazz;
            }
        }
    }
 
    if (autoTypeSupport || expectClass != null) {
        clazz = TypeUtils.loadClass(typeName, defaultClassLoader);
    }
 
    if (clazz != null) {
 
        if (ClassLoader.class.isAssignableFrom(clazz) // classloader is danger
            || DataSource.class.isAssignableFrom(clazz) // dataSource can load jdbc driver
           ) {
            throw new JSONException("autoType is not support. " + typeName);
        }
 
        if (expectClass != null) {
            if (expectClass.isAssignableFrom(clazz)) {
                return clazz;
            } else {
                throw new JSONException("type not match. " + typeName + " -> " + expectClass.getName());
            }
        }
    }
 
    if (!autoTypeSupport) {
        throw new JSONException("autoType is not support. " + typeName);
    }
 
    return clazz;
}