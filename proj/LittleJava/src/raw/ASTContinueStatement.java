/* Generated By:JJTree: Do not edit this line. ASTContinueStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package raw;

public
class ASTContinueStatement extends SimpleNode {
  public ASTContinueStatement(int id) {
    super(id);
  }

  public ASTContinueStatement(LittleJava p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(LittleJavaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=201dfcfd46287560c43490eb3e788d7e (do not edit this line) */
