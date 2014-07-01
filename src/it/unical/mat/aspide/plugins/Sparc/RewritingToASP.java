package it.unical.mat.aspide.plugins.Sparc;

import externaltools.DLVSolver;
import externaltools.PrologSolver;
import it.unical.mat.aspide.lgpl.plugin.environment.AspideEnvironment;
import it.unical.mat.aspide.lgpl.plugin.interfaces.Plugin;
import it.unical.mat.aspide.plugins.Sparc.exceptions.ParseException;
import parser.*;
import translating.InstanceGenerator;
import translating.Translator;
import typechecking.TypeChecker;

import javax.swing.*;
import java.io.*;
import java.util.Properties;


public class RewritingToASP {

    private Plugin pluginInstance;
    boolean throwWarningsException;

    public RewritingToASP(Plugin pluginInstance, boolean throwWarningsException) {
        this.throwWarningsException = throwWarningsException;
        this.pluginInstance = pluginInstance;
    }
    //---------------------------
    //FROM Sparc to ASP
    //---------------------------

    public void rewriteToASP(InputStream inputStream, OutputStream outputStream) throws ParseException, IOException {
        Reader reader = new InputStreamReader(inputStream);
        SparcTranslator p = new SparcTranslator(reader);
        Writer writer = null;
        try {
            SimpleNode astProgramNode = p.program();
            if (astProgramNode.jjtGetNumChildren() > 2) {   // if the program file is not empty!
                InstanceGenerator generator = new InstanceGenerator(p.sortNameToExpression);

                TypeChecker tc = new TypeChecker(p.sortNameToExpression, p.predicateArgumentSorts, p.constantsMapping, p.curlyBracketTerms, p.definedRecordNames, generator);
                tc.checkRules((ASTprogramRules) astProgramNode.jjtGetChild(2));
                if (outputStream != null)
                    writer = new OutputStreamWriter(outputStream);
                Translator tr = null;

                Properties plugProps = AspideEnvironment.getInstance().getPluginProperties(pluginInstance);
                if (plugProps.getProperty("CHECK_WARNINGS", "FALSE").equals("TRUE")) {
                    if (PrologSolver.searchForExe() != null)
                        tr = new Translator(writer, p, generator, false, true);
                    else {
                        JOptionPane.showMessageDialog(null, "Swi-Prolog not found", "InfoBox: 1", JOptionPane.ERROR_MESSAGE);
                    }
                } else
                    tr = new Translator(writer, p, generator, false, false);
                    tr.translateProgram((ASTprogram) astProgramNode, p.generatingSorts, !throwWarningsException);
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