/* Generated By:JJTree: Do not edit this line. ASTsymbolicTerm.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTsymbolicTerm extends SimpleNode {
  public ASTsymbolicTerm(int id) {
    super(id);
  }

  public ASTsymbolicTerm(SparcTranslator p, int id) {
    super(p, id);
  }


  /** Accept the visitor. **/
  public Object jjtAccept(SparcTranslatorVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
  public String toString(boolean useOriginalImages) {
	 if( ((SimpleNode)(this.jjtGetChild(0))).id
			 ==SparcTranslatorTreeConstants.JJTSYMBOLICCONSTANT) {
		 return (  ((SimpleNode) (this.jjtGetChild(0))).toString(useOriginalImages));
	 } else {
		 String result="";
		 for(int i=0;i<this.jjtGetNumChildren();i++) {
			 result += ((SimpleNode)(this.jjtGetChild(i))).toString(useOriginalImages);
		 }
		 result+=")";
		 return result;
	 }
  }
  public String toString() {
	  return toString(false);
  }
}
/* JavaCC - OriginalChecksum=585198cc7bf59badaa534b0523dbe77d (do not edit this line) */
