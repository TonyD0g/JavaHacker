## 1.前言

学习来源：

https://lsieun.github.io/java/asm/java-asm-season-01.html



环境：

pom.xml:

```xml
<dependencies>
        <!-- https://mvnrepository.com/artifact/org.ow2.asm/asm -->
        <dependency>
            <groupId>org.ow2.asm</groupId>
            <artifactId>asm</artifactId>
            <version>5.0.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.ow2.asm/asm-commons -->
        <dependency>
            <groupId>org.ow2.asm</groupId>
            <artifactId>asm-commons</artifactId>
            <version>5.0.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.ow2.asm/asm-util -->
        <dependency>
            <groupId>org.ow2.asm</groupId>
            <artifactId>asm-util</artifactId>
            <version>5.0.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.ow2.asm/asm-tree -->
        <dependency>
            <groupId>org.ow2.asm</groupId>
            <artifactId>asm-tree</artifactId>
            <version>5.0.3</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/org.ow2.asm/asm-analysis -->
        <dependency>
            <groupId>org.ow2.asm</groupId>
            <artifactId>asm-analysis</artifactId>
            <version>5.0.3</version>
        </dependency>

    </dependencies>
```



注意：

为方便学习，源码中的注释绝大部分都直接复制于【学习来源】。

所以请直接忽略源码注释的这几个字： **[在上述示例代码中]**  