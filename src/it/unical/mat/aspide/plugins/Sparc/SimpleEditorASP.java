package it.unical.mat.aspide.plugins.Sparc;

import it.unical.mat.aspide.lgpl.bridgePlugin.model.exception.EditorNotOpenableException;
import it.unical.mat.aspide.lgpl.plugin.defaultElements.DLVEditorView;
import it.unical.mat.aspide.lgpl.plugin.environment.AspideEnvironment;
import it.unical.mat.aspide.lgpl.plugin.interfaces.RewritingPlugin;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class SimpleEditorASP extends DLVEditorView{	
	List<RewritingPlugin> rewritingPlugins;
	
	@Override
	public void save() {

		try {
			setModified(false);			
			originalText = getText();
			AspideEnvironment.getInstance().notifyModified(this);			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Error on saving. " 
					+ e.getMessage(), "Saving", JOptionPane.ERROR_MESSAGE);			
		}


	}
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SimpleEditorASP(List<RewritingPlugin> rewritingPlugins) {
		this.rewritingPlugins = rewritingPlugins;
	}
		
	@Override
	public String getEditorName() {
		return "Simple ASP Editor";
	}
	
	
	@Override
	public void load() throws EditorNotOpenableException{
		boolean canContinue = true;
		File file = new File(getInputStorageHandler().getAbsolutePath());
		try {
			Scanner scanner = new Scanner(new FileInputStream(file));
			if (!scanner.hasNext()){
				canContinue = false;
			}
			scanner.close();
		} catch (Exception e1) {
			throw new EditorNotOpenableException(e1.getMessage());
		}
		
		if (canContinue){
			try {
				SparcPlugin plugin = getToASPRewriter();
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				plugin.setRewritingHandler((SparcProgramHandler)getInputStorageHandler());
				plugin.rewrite(new FileInputStream(file), out);	
				InputStream stream = new ByteArrayInputStream(out.toByteArray()); 
				read(stream, null);
				stream.close();
			} catch (Exception e) {			
				throw new EditorNotOpenableException(e.getMessage());		
			}
			originalText = getText();
		}
	}
		
	
	private SparcPlugin getToASPRewriter(){
		if (rewritingPlugins.get(0) instanceof SparcPlugin){
			return (SparcPlugin)rewritingPlugins.get(0);
		}else{
			return (SparcPlugin)rewritingPlugins.get(1);
		}
	}
	

}
