package externaltools;

import it.unical.mat.aspide.lgpl.bridgePlugin.environment.ExecutableInfo;
import it.unical.mat.aspide.lgpl.plugin.environment.AspideEnvironment;

import java.io.*;
import java.security.SecureRandom;
import java.util.List;


public class PrologSolver extends ExternalSolver {

	private String pathToProlog;
	public PrologSolver() throws FileNotFoundException {
		this(null);
	}

	public PrologSolver(String program) throws FileNotFoundException {
		this.program = program;
		pathToProlog = searchForExe();
		if (pathToProlog == null) {
			throw new FileNotFoundException("Swi-prolog not found. "
					+ "You should have swipl or swipl.exe available "
					+ "from your path or current folder.");
		}
	}

	public boolean isSatisfiable() {
		String output = run(true);
		return output.equals("yes");
	}
	//TODO: Implement ignore warnings properly
	public String run(boolean ignoreWarnings) {
		// Create a temporary file: FileWriter fw = new
		// FileWriter(file.getAbsoluteFile());
		File tempDir = new File(System.getProperty("java.io.tmpdir"));
		File tempFile = generateFile("prolog_q",
				Long.toString(System.currentTimeMillis()), tempDir);
		// write the program to the temporary file:
		FileWriter fw = null;
		try {
			fw = new FileWriter(tempFile.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(program);
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String options = " -s " + tempFile.getAbsolutePath()
				+ " -t " + " main " + " -q ";

		OsUtils.runCommand(pathToProlog, options,null);

		if (OsUtils.errors.length()>0) {
			throw new IllegalArgumentException(
					"prolog program constructed from a rule for warnings "
							+ "checking contains errors: " + OsUtils.errors.toString());

		}
		return OsUtils.result.toString().trim();
	}

	public static String searchForExe() {
        List<ExecutableInfo> solvers = AspideEnvironment.getInstance().getExecutablesSolvers(AspideEnvironment.ExecType.EXECUTABLES_SOLVERS);
        for (ExecutableInfo solver : solvers) {
            if(solver.getName().equals("swipl") ||  solver.getName().equals("SWIPL")) {
                return solver.getLocation();
            }
        }
        return null;
	}


	private static final SecureRandom random = new SecureRandom();

	static File generateFile(String prefix, String suffix, File dir) {
		long n = random.nextLong();
		if (n == Long.MIN_VALUE) {
			n = 0;      // corner case
		} else {
			n = Math.abs(n);
		}
		return new File(dir, prefix +"_"+ Long.toString(n) + "_"+suffix);
	}
}
