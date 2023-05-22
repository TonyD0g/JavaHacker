/**
 * @id java/examples/vuldemo
 * @name Sql-Injection
 * @description Sql-Injection
 * @kind path-problem
 * @problem.severity warning
 */

import java
import semmle.code.java.dataflow.FlowSources
import semmle.code.java.security.QueryInjection
import DataFlow::PathGraph

predicate isTaintedString(Expr expSrc, Expr expDest) {
  exists(Method method, MethodAccess call, MethodAccess call1 |
    expSrc = call1.getArgument(0) and
    expDest = call and
    call.getMethod() = method and
    method.hasName("get") and
    method.getDeclaringType().toString() = "Optional<String>" and
    call1.getArgument(0).getType().toString() = "Optional<String>"
  )
}

class VulConfig extends TaintTracking::Configuration {
  VulConfig() { this = "SqlInjectionConfig" }

  override predicate isSource(DataFlow::Node src) { src instanceof RemoteFlowSource }

  override predicate isSanitizer(DataFlow::Node node) {
    node.getType() instanceof PrimitiveType
    or
    node.getType() instanceof BoxedType
    or
    node.getType() instanceof NumberType
    or
    exists(ParameterizedType pt |
      node.getType() = pt and pt.getTypeArgument(0) instanceof NumberType
    )
  }

  override predicate isSink(DataFlow::Node sink) {
    exists(Method method, MethodAccess call |
      method.hasName("query") and
      call.getMethod() = method and
      sink.asExpr() = call.getArgument(0)
    )
  }

  override predicate isAdditionalTaintStep(DataFlow::Node node1, DataFlow::Node node2) {
    isTaintedString(node1.asExpr(), node2.asExpr())
  }
}

from VulConfig config, DataFlow::PathNode source, DataFlow::PathNode sink
where config.hasFlowPath(source, sink)
select source.getNode(), source, sink, "source"
