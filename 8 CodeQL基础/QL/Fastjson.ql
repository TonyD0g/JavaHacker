fastjson.qll

import java
import semmle.code.java.dataflow.DataFlow

/*
分析文:    https://xz.aliyun.com/t/7482
需要改进处:
    这种函数参数的匿名类，可能不在第一个索引，需要对所有的参数做判断
    对应的sink可以继续去拓展，可能有其他的调用方式没有加入（例如lambda表达式）
    危险方法不一定是JNDI查询，也可以是其他的方法
    没有使用到污点追踪，所以需要一点人工成本


*/

class FastJsonSetMethod extends Method{
    FastJsonSetMethod(){
        this.getName().indexOf("set") = 0 and
        this.getName().length() > 3 and
        this.isPublic() and
        this.fromSource() and
        exists(VoidType vt | 
            vt = this.getReturnType()
        ) and
        this.getNumberOfParameters() = 1
    }
}


class FastJsonGetMethod extends Method{
    FastJsonGetMethod(){
        this.getName().indexOf("get") = 0 and
        this.getName().length() > 3 and
        this.isPublic() and
        this.fromSource() and
        this.hasNoParameters()
    }
}

class JNDIMethod extends Method{
    JNDIMethod(){
        this.getDeclaringType().getASupertype*().hasQualifiedName("javax.naming", "Context") and
        this.hasName("lookup")
    }
}

predicate isClassField(RefType classType, string fieldName){
    classType.getAField().hasName(fieldName)
}

MethodAccess seekSink(Method sourceMethod){
    exists(
        MethodAccess ma, Method method|
        (ma.getEnclosingStmt() = sourceMethod.getBody().getAChild*() and
        method = ma.getMethod()) or
        (ma.getEnclosingStmt() = sourceMethod.getBody().getAChild*() and ma.getArgument(0).(ClassInstanceExpr).getAnonymousClass().isAnonymous() and method = ma.getArgument(0).(ClassInstanceExpr).getAnonymousClass().getAMethod())|
        if method instanceof JNDIMethod
        then result = ma
        else result = seekSink(method)
    )
}

fastjson.ql
import java
import semmle.code.java.dataflow.DataFlow
import fastjson


from FastJsonGetMethod getMethod
select getMethod, seekSink(getMethod)