/* Generated By:JJTree: Do not edit this line. ASTmultiplicativeSetExpression.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTmultiplicativeSetExpression extends SimpleNode {
  public ASTmultiplicativeSetExpression(int id) {
    super(id);
  }

  public ASTmultiplicativeSetExpression(SparcTranslator p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SparcTranslatorVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
}
/* JavaCC - OriginalChecksum=3977bfafefb6c050f304bddf4c55ce96 (do not edit this line) */
