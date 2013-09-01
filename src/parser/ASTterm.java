/* Generated By:JJTree: Do not edit this line. ASTterm.java Version 4.3 */
/* JavaCCOptions:MULTI=true,NODE_USES_PARSER=false,VISITOR=true,TRACK_TOKENS=false,NODE_PREFIX=AST,NODE_EXTENDS=,NODE_FACTORY=,SUPPORT_CLASS_VISIBILITY_PUBLIC=true */
package parser;

import java.util.ArrayList;
import java.util.regex.Pattern;



import warnings.Pair;
import warnings.StringListUtils;

public class ASTterm extends SimpleNode {
	public ASTterm(int id) {
		super(id);
	}

	public ASTterm(SparcTranslator p, int id) {
		super(p, id);
	}

	public ASTterm(long value) {
		super(SparcTranslatorTreeConstants.JJTTERM);
		ASTatomicArithmeticTerm aaterm = new ASTatomicArithmeticTerm(
				SparcTranslatorTreeConstants.JJTATOMICARITHMETICTERM);
		ASTmultiplicativeArithmeticTerm materm = new ASTmultiplicativeArithmeticTerm(
				SparcTranslatorTreeConstants.JJTMULTIPLICATIVEARITHMETICTERM);
		ASTadditiveArithmeticTerm adaterm = new ASTadditiveArithmeticTerm(
				SparcTranslatorTreeConstants.JJTADDITIVEARITHMETICTERM);


		aaterm.image = Long.toString(value);
		adaterm.image="+";
		// attach subtrees to the root
		materm.jjtAddChild(aaterm, 0);
		adaterm.jjtAddChild(materm, 0);
		this.jjtAddChild(adaterm, 0);
	
	}

    public ASTterm(String image) {
        super(SparcTranslatorTreeConstants.JJTTERM);
        Pair<String, ArrayList<String>> recordContents = StringListUtils
                .splitTerm(image);
        if (recordContents != null) {
            ASTsymbolicFunction func = new ASTsymbolicFunction(
                    SparcTranslatorTreeConstants.JJTSYMBOLICFUNCTION);
            func.image = recordContents.first + "(";
            ASTtermList termList = new ASTtermList(
                    SparcTranslatorTreeConstants.JJTTERMLIST);
            for (int i = 0; i < recordContents.second.size(); i++) {
                ASTterm newTerm = new ASTterm(recordContents.second.get(i));
                termList.jjtAddChild(newTerm, i);
            }
            ASTsymbolicTerm sterm = new ASTsymbolicTerm(
                    SparcTranslatorTreeConstants.JJTSYMBOLICTERM);
            sterm.jjtAddChild(func, 0);
            sterm.jjtAddChild(termList, 1);
            this.jjtAddChild(sterm, 0);
        } else {
            if(!Character.isLowerCase(image.charAt(0))) {
                ASTvar var=new ASTvar(SparcTranslatorTreeConstants.JJTVAR);
                var.image=image;
                this.jjtAddChild(var, 0);
            }
            else {
                ASTsymbolicConstant sconstant = new ASTsymbolicConstant(
                        SparcTranslatorTreeConstants.JJTSYMBOLICCONSTANT);
                sconstant.image = image;
                ASTsymbolicTerm sterm = new ASTsymbolicTerm(
                        SparcTranslatorTreeConstants.JJTSYMBOLICTERM);
                sterm.jjtAddChild(sconstant, 0);
                this.jjtAddChild(sterm, 0);
            }
        }
    }

    public ASTterm(String recordName,ArrayList<String> varArgs) {
		super(SparcTranslatorTreeConstants.JJTTERM);
		ASTsymbolicFunction symFunction = new ASTsymbolicFunction(
				SparcTranslatorTreeConstants.JJTSYMBOLICFUNCTION);
    	symFunction.image=recordName+"(";
    	
    	ASTtermList termList=new ASTtermList(SparcTranslatorTreeConstants.JJTTERMLIST);
    	
    	for(int i=0;i<varArgs.size();i++) {
    		ASTterm term=new ASTterm(SparcTranslatorTreeConstants.JJTTERM);
    		ASTvar var=new ASTvar(SparcTranslatorTreeConstants.JJTVAR);
    		var.image=varArgs.get(i);
    		term.jjtAddChild(var, 0);
    		termList.jjtAddChild(term, i);
    	}
    	
		ASTsymbolicTerm sterm = new ASTsymbolicTerm(
				SparcTranslatorTreeConstants.JJTSYMBOLICTERM);
		sterm.jjtAddChild(symFunction, 0);
		sterm.jjtAddChild(termList, 1);
		this.jjtAddChild(sterm, 0);
	}

	/** Accept the visitor. **/
	public Object jjtAccept(SparcTranslatorVisitor visitor, Object data) {
		return visitor.visit(this, data);
	}

	/**
	 * Check if this term has variables
	 * 
	 * @return true if it has and false otherwise
	 */
	public boolean hasVariables() {
		return hasVariables((SimpleNode) this);
	}

	/**
	 * Check if SimpleNode has variables
	 * 
	 * @param n
	 *            SimpleNode to check
	 * @return true if it has and false otherwise
	 */
	private boolean hasVariables(SimpleNode n) {
		if (n.getId() == SparcTranslatorTreeConstants.JJTVAR) {
			return true;
		}
		boolean result = false;
		for (int i = 0; i < n.jjtGetNumChildren(); i++) {
			if (hasVariables((SimpleNode) n.jjtGetChild(i))) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * Check if the terms has arithmetic operations
	 * 
	 * @return true if it has an arithmetic operation ( +,-,*,/) and false
	 *         otherwise
	 */
	public boolean hasArithmeticOperations() {
		String termString = this.toString();
		String operations = "+-*/";
		for (char c : operations.toCharArray()) {
			if (termString.indexOf(c) != -1)
				return true;
		}
		return false;
	}

	/**
	 * Check if the term is ground
	 * 
	 * @return true or false
	 */
	public boolean isGround() {
		return !hasArithmeticOperations() && !hasVariables();
	}

	public boolean isVariable() {
		SimpleNode child = (SimpleNode) this.jjtGetChild(0);
		return child.getId() == SparcTranslatorTreeConstants.JJTVAR;
	}

	public boolean isRecord() {
		SimpleNode child = (SimpleNode) this.jjtGetChild(0);
		if (child.getId() == SparcTranslatorTreeConstants.JJTSYMBOLICTERM) {
			SimpleNode childOfChild = (SimpleNode) child.jjtGetChild(0);
			return childOfChild.getId() == SparcTranslatorTreeConstants.JJTSYMBOLICFUNCTION;
		} else {
			return false;
		}
	}

	public String toString() {
		return toString(false);
	}

	public String toString(boolean useOriginalImages) {
		if (this.jjtGetNumChildren() == 0) {
			return this.image;
		} else
			return ((SimpleNode) (this.jjtGetChild(0)))
					.toString(useOriginalImages);
	}

}
/*
 * JavaCC - OriginalChecksum=c1ac1ba327197118b424963735d16a51 (do not edit this
 * line)
 */
