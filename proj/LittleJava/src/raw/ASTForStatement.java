/* Generated By:JJTree: Do not edit this line. ASTForStatement.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package raw;

public
class ASTForStatement extends LittleJavaNode {
  public ASTForStatement(int id) {
    super(id);
  }

  public ASTForStatement(LittleJava p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(LittleJavaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=cc7a58d0d9fb6c8db714d88c50f912d5 (do not edit this line) */
