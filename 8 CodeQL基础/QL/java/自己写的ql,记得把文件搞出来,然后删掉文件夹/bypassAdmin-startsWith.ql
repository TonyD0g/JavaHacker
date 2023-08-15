/**
 * @id java/examples/vuldemo
 * @name bypassAdmin-startsWith
 * @description bypassAdmin-startsWith
 * @kind path-problem
 * @problem.severity warning
 */

 import java
 import semmle.code.java.dataflow.FlowSources
 import semmle.code.java.security.QueryInjection
 import DataFlow::PathGraph
 
 class VulConfig extends TaintTracking::Configuration {
   VulConfig() { this = "bypassAdmin-startsWith" }
 
   // set source
   override predicate isSource(DataFlow::Node src) { src instanceof RemoteFlowSource }
 
   // set sink
   override predicate isSink(DataFlow::Node sink) {
    exists(Method method ,MethodAccess call2|
      method.hasName("startsWith")
      and call2.getMethod() = method
      and call2.getQualifier() = sink.asExpr()
      )
   }
 }
 
 from VulConfig config, DataFlow::PathNode source, DataFlow::PathNode sink
 where
   config.hasFlowPath(source, sink) 
select source.getNode(), source, sink, "source"

