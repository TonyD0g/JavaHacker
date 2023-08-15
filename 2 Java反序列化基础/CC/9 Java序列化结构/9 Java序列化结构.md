## 1.前言

1.必读

[Java安全漫谈 - 19.Java反序列化协议构造与分析](https://t.zsxq.com/ZfiEeEY)

https://github.com/1nhann/java_ser_format

看不懂p牛给的go文件？：

查看zkar文档： https://pkg.go.dev/github.com/phith0n/zkar/



2.工具下载

1. https://github.com/phith0n/zkar

2. Go语言环境安装

---

### 详细的反序列化结构查看：

https://github.com/1nhann/java_ser_format

### 利用zkar解析序列化结构：

testUser.java生成的序列化：

```md
.\zkar.exe dump -B rO0ABXNyAARVc2VyNUSIENTkU1wCAAJMAARuYW1ldAASTGphdmEvbGFuZy9TdHJpbmc7TAAGcGFyZW50dAAGTFVzZXI7eHB0AANCb2JzcQB+AAB0AAVKb3N1YXA=
```

结合得出的 **解析结果和testUser.java** 进行相互对照，学习序列化的结构。



### 构造一个包含垃圾数据的序列化流

- 脏数据添加到cc6末尾：

​	使用【Code/CC6-rubbish-after.go】去加载【Code/cc6.ser】，从而生成 cc6-padding-after.ser 

- 脏数据添加到cc6头部：

  直接添加 blockdata在object前是不行的。因为底层源码会先处理object，blockdata放前面会报错。 object 后填充一个 blockdata 的方法之所以可行，就是因为首个结构是 object ，处理完后反序列化就结束了， blockdata 根本没有处理，也就不会抛出异常了。

  **看不懂？那就看伪代码：**

  ```java
  // 遍历 contents
  for(i=0;i<contents.length;i++){
      // serializeStruct : java反序列化协议结构
      for(j=0;j<serializeStruct.list.length;j++){
          // 按照 java反序列化协议结构列表 去解析
              Switch(){
                  case ...:    
                  	...
                      ...    
                  case 解析到blockdata:          
                     退出全部for循环.
              }
      }
  }
  ```

**如何解决：**

在处理 object 前Java会丢弃所有的 `TC_RESET` （ `TC_RESET` 是 `object` 结构的一部分），所以我们可以用这个字符来填充																						——[Java安全漫谈 - 19.Java反序列化协议构造与分析](https://t.zsxq.com/ZfiEeEY)

【详情代码查看：CC6-rubbish-frist.go】
