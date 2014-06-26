package it.unical.mat.aspide.plugins.Sparc;

import it.unical.mat.aspide.lgpl.bridgePlugin.model.exception.PluginException;
import it.unical.mat.aspide.lgpl.plugin.environment.AspideProject;
import it.unical.mat.aspide.lgpl.plugin.interfaces.*;
import it.unical.mat.aspide.plugins.Sparc.exceptions.ParseException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

public class SparcPlugin extends RewritingPluginAdapter implements InputPlugin,RewritingPlugin{



    public SparcPlugin(){};
    SparcProgramHandler rewritingHandler;




    public SimpleEditorASP getASPEditor(){
        List<RewritingPlugin> plugins = new LinkedList<RewritingPlugin>();
        plugins.add(this);
        return new SimpleEditorASP(plugins);
    }

    public SimpleEditorSparc getSparcEditor(){
        List<RewritingPlugin> plugins = new LinkedList<RewritingPlugin>();
        plugins.add(this);
        return new SimpleEditorSparc(plugins);
    }

    @Override
    public InputStorageHandler createFileHandler(File file, AspideProject project) {
        final SparcProgramHandler newHandler = new SparcProgramHandler(file, project, this);
        return newHandler;
    }

//	@Override
//	public List<InputStorageHandler> getFileHandlers() {
//		return storageHandlers;
//	}

    @Override
    public List<EditorView> getViews() {
        List<EditorView> views = new LinkedList<EditorView>();
        views.add(getSparcEditor());
        views.add(getASPEditor());
        return views;
    }

    @Override
    public EditorView getDefaultView() {
        //return getASPEditor();
        return getSparcEditor();
    }

    @Override
    public EditorView nextView(EditorView view) {
        if (view instanceof SimpleEditorASP){
            return getSparcEditor();
        }else{
            return getASPEditor();
        }
    }


    public void setRewritingHandler(SparcProgramHandler handler){
        this.rewritingHandler = handler;
    }

    @Override
    public void rewrite(InputStream inputStream, OutputStream outputStream)
            throws IOException, PluginException {
        final SparcProgramHandler handler = rewritingHandler;
        try {
            (new RewritingToASP(this,false)).rewriteToASP(inputStream, outputStream);
            inputStream.close();
            outputStream.flush();
            outputStream.close();
//			handler.clearErrors();
        } catch (final ParseException e) {
            inputStream.close();
            outputStream.flush();
            outputStream.close();
            throw new PluginException(e);
        }
    }

    @Override
    public List<CustomMenuItem> getFileMenuItems(InputStorageHandler inputStorageHandler) {
        return null;
    }


}