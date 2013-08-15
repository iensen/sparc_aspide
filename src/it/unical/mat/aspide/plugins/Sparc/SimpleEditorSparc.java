package it.unical.mat.aspide.plugins.Sparc;

import it.unical.mat.aspide.lgpl.bridgePlugin.model.exception.PluginException;
import it.unical.mat.aspide.lgpl.plugin.defaultElements.DefaultEditorView;
import it.unical.mat.aspide.lgpl.plugin.interfaces.RewritingPlugin;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

public class SimpleEditorSparc extends DefaultEditorView{
	List<RewritingPlugin> rewritingPlugins;
	
	public SimpleEditorSparc(List<RewritingPlugin> rewritingPlugins) {
		this.rewritingPlugins = rewritingPlugins;
	}
	
	@Override
	public String getEditorName() {
		return "Simple Sparc Editor";
	}
	
	@Override
	public void save() throws IOException, PluginException
    {
        try{
       super.save();
        }
        catch(Exception pe) {
            JOptionPane.showMessageDialog(null, pe.getStackTrace());
        }
		//Refresh errors
		//SparcPlugin plugin =  getToASPRewriter();
		//plugin.setRewritingHandler((SparcProgramHandler)getInputStorageHandler());
	    //plugin.rewrite(new ByteArrayInputStream(getText().getBytes()), null);
	}	
	
	private SparcPlugin getToASPRewriter(){
        //System.out.println(rewritingPlugins.get(0).getClass());
		return (SparcPlugin) rewritingPlugins.get(0);
	}
}
