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
import java.util.LinkedList;
import java.util.List;

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
            (new RewritingToASP()).rewriteToASP(fStream, null);
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
            GenericError error = new GenericError() {
                @Override
                public void setWarning(boolean isWarning) {

                }

                @Override
                public void setMessage(String message) {

                }

                @Override
                public boolean isWarning() {
                    // TODO Auto-generated method stub
                    return false;
                }

                @Override
                public StorageSupport getStorageSupport() {
                    return SparcProgramHandler.this;
                }

                @Override
                public String getMessage() {
                    return e.getMessage();
                }

                @Override
                public int getLine() {
                    return 0;
                }

                @Override
                public int getColumn() {
                    return 0;
                }
            };
            //JOptionPane.showMessageDialog(null, "10");
            addError(error);
            //JOptionPane.showMessageDialog(null, "11");
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
    }

    public void clearErrors(){
        errors = new LinkedList<GenericError>();
    }
}