/* Generated By:JJTree: Do not edit this line. ASTDecl.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package raw;

public
class ASTDecl extends LittleJavaNode {
  public ASTDecl(int id) {
    super(id);
  }

  public ASTDecl(LittleJava p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(LittleJavaVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=3cd95b373c2448ba12bdb928b5407f9d (do not edit this line) */
