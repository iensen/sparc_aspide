package it.unical.mat.aspide.plugins.Sparc;

import externaltools.PrologSolver;
import it.unical.mat.aspide.plugins.Sparc.exceptions.ParseException;
import parser.*;
import translating.InstanceGenerator;
import translating.Translator;
import typechecking.TypeChecker;

import javax.swing.*;
import java.io.*;


public class RewritingToASP {
    //---------------------------
    //FROM Sparc to ASP
    //---------------------------

    public void rewriteToASP(InputStream inputStream, OutputStream outputStream) throws ParseException, IOException {
        Reader reader = new InputStreamReader(inputStream);
        SparcTranslator p = new SparcTranslator(reader);
        Writer writer = null;
        try {
            SimpleNode  astProgramNode = p.program();
            if (astProgramNode.jjtGetNumChildren() > 2) {   // if the program file is not empty!
                InstanceGenerator generator = new InstanceGenerator(p.sortNameToExpression);

                TypeChecker tc = new TypeChecker(p.sortNameToExpression, p.predicateArgumentSorts, p.constantsMapping, p.curlyBracketTerms, p.definedRecordNames, generator);
                tc.checkRules((ASTprogramRules) astProgramNode.jjtGetChild(2));
                if (outputStream != null)
                    writer = new OutputStreamWriter(outputStream);
                Translator tr=null;
                if(PrologSolver.searchForExe()!=null)
                   tr = new Translator(writer, p, generator, false, true);
                else
                    tr = new Translator(writer, p, generator, false, false);
                tr.translateProgram((ASTprogram) astProgramNode, p.generatingSorts, false);
            }
            if (writer != null) {
                writer.flush();
                writer.close();
            }
        } catch (parser.ParseException pe) {
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            throw new ParseException(pe.getMessage());
        } catch (FileNotFoundException fe) {  //  swi-prolog not found (used for warnings checking)
            if (writer != null) {
                writer.flush();
                writer.close();
            }
            throw new ParseException(fe.getMessage());
        }
    }

}