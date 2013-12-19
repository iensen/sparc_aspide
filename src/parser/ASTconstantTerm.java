/* Generated By:JJTree: Do not edit this line. ASTconstantTerm.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

public
class ASTconstantTerm extends SimpleNode {
  public ASTconstantTerm(int id) {
    super(id);
  }

  public ASTconstantTerm(SparcTranslator p, int id) {
    super(p, id);
  }
  
  // constructor to create a simple constant term
  // it may only be an identifier or a number
  public ASTconstantTerm(String content) {
	  super(SparcTranslatorTreeConstants.JJTCONSTANTTERM);
	  this.image = content;
  }

  /** Accept the visitor. **/
  public Object jjtAccept(SparcTranslatorVisitor visitor, Object data) {
    return visitor.visit(this, data);
  }
  
  public String toString() {
	  
	  if(this.image.indexOf(' ')!=-1) {
		  String termName=this.image.split(" ")[0];
		  StringBuilder result=new StringBuilder();
		  result.append(termName);
		  result.append(((ASTconstantTermList)this.jjtGetChild(0)).toString());
		  result.append(")");
		  return result.toString();
	  }
	  else {
		  return this.image;
	  }
  }
}
/* JavaCC - OriginalChecksum=a5bec175c1c359d829a6ad4ef07d81d2 (do not edit this line) */
