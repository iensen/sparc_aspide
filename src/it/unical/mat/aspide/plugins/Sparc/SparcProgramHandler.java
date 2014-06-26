package it.unical.mat.aspide.plugins.Sparc;


import it.unical.mat.aspide.lgpl.bridgePlugin.model.StorageSupport;
import it.unical.mat.aspide.lgpl.plugin.environment.AspideProject;
import it.unical.mat.aspide.lgpl.plugin.interfaces.*;
import it.unical.mat.aspide.plugins.Sparc.exceptions.ParseException;


import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SparcProgramHandler extends InputStorageAdapter{
    List<GenericError> errors;
    private RewritingPlugin defaultRewriter;

    @Override
    public RewritingPlugin getDefaultRewriter() {
        return defaultRewriter;
    }
    public SparcProgramHandler(File file, AspideProject project,RewritingPlugin rp) {
        super(file, project);
        this.defaultRewriter=rp;
    }

    @Override
    public String getIconName() {
        return "Sparc.png";
    }

    @Override
    public List<GenericError> getErrors() {
        return errors;
    }

    public void addError(GenericError error){
        if (errors == null){
            errors = new LinkedList<GenericError>();
        }
        if (error != null){
            errors.add(error);
        }
    }

    @Override
    public void updateErrors() {
        clearErrors();
        FileInputStream fStream = null;
        try {
            fStream = new FileInputStream(getFile());
            (new RewritingToASP(defaultRewriter)).rewriteToASP(fStream, null);
            fStream.close();
        } catch (final ParseException e) {
            if (fStream != null){
                try {

                    fStream.close();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
            ArrayList<String> errors=new ArrayList<String>();
            String msgStr=e.getMessage();

            if(msgStr.startsWith("%WARNINGS")) {
                msgStr=msgStr.substring(10);
                int wIndex=0;
                int nextIndex= msgStr.indexOf("%WARNING:");
                while(true) {

                    wIndex=nextIndex;
                    nextIndex=msgStr.substring(wIndex+9).indexOf("%WARNING:");
                    if(nextIndex==-1)
                        nextIndex=msgStr.length();
                    else
                        nextIndex+=9;
                    errors.add(msgStr.substring(wIndex+1,nextIndex));
                    if(nextIndex==msgStr.length())
                        break;
                }

            }

            else {
                errors.add(msgStr);
            }
            for(final String errorMsg:errors) {
            final ErrorMessage msg=new ErrorMessage(errorMsg);
            GenericError error = new GenericError() {

                @Override
                public void setWarning(boolean isWarning) {

                }

                @Override
                public void setMessage(String message) {

                }

                @Override
                public boolean isWarning() {
                    return errorMsg.startsWith("WARNING:");
                }

                @Override
                public StorageSupport getStorageSupport() {
                    return SparcProgramHandler.this;
                }

                @Override
                public String getMessage() {
                    return msg.message;
                }

                @Override
                public int getLine() {
                    return msg.lineNumber;
                }

                @Override
                public int getColumn() {
                    return msg.columnNumber;
                }
            };
            //JOptionPane.showMessageDialog(null, "10");
            addError(error);
            //JOptionPane.showMessageDialog(null, "11");
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }

    }

    public void clearErrors(){
        errors = new LinkedList<GenericError>();
    }
}

/**

 * Class for error message representation
 */
class ErrorMessage {
    int lineNumber;
    int columnNumber;
    String message;

    /**
     * Constructor
     * @param rawMessage produced by SPARC to ASP translator
     *   store original message with some auxiliary information removed in message field
     *   and line and column number fetched from the error message  in lineNumber and columnNumber fields
     */

    /**
     */
    public ErrorMessage(String rawMessage) {
        StringBuilder newMessage=new StringBuilder();
        lineNumber=0;
        columnNumber=0;
        if(rawMessage.startsWith("ERROR:"))
        {
            rawMessage=rawMessage.substring(6);
        }
        if(rawMessage.startsWith("WARNING:")) {
            rawMessage=rawMessage.substring(8);
        }

        Pattern pattern =
                Pattern.compile("at\\s+[lL]ine\\s+[1-9][0-9]*\\s*,\\s*column\\s+[1-9][0-9]*");

        Matcher matcher =
                pattern.matcher(rawMessage);

        if (matcher.find()) {
            message=rawMessage.substring(0,matcher.start())+rawMessage.substring(matcher.end());
            String lineColumnString=rawMessage.substring(matcher.start(),matcher.end());
            Pattern numberPattern=Pattern.compile("[1-9][0-9]*");
            Matcher numberMatcher=numberPattern.matcher(lineColumnString);
            numberMatcher.find();
            lineNumber=Integer.parseInt(lineColumnString.substring(numberMatcher.start(),numberMatcher.end()));
            numberMatcher.find();
            columnNumber=Integer.parseInt(lineColumnString.substring(numberMatcher.start(),numberMatcher.end()));
        }
        else {
            message=rawMessage;
        }
    }


}
