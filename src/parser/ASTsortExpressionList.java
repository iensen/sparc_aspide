/* Generated By:JJTree: Do not edit this line. ASTsortExpressionList.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTsortExpressionList extends SimpleNode {
  public ASTsortExpressionList(int id) {
    super(id);
  }

  public ASTsortExpressionList(SparcTranslator p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SparcTranslatorVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=c5af95d1f013963ea5018ae5166388a0 (do not edit this line) */
