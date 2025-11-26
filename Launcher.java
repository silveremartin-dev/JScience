import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Portable launcher for JScience.
 * Usage: java Launcher.java [CPU|GPU|AUTO]
 */
public class Launcher {
    public static void main(String[] args) throws Exception {
        String mode = "AUTO";
        if (args.length > 0) {
            mode = args[0].toUpperCase();
        }

        String classpath = "target/classes" + File.pathSeparator + "target/test-classes";
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";

        List<String> command = new ArrayList<>();
        command.add(javaBin);
        command.add("-cp");
        command.add(classpath);
        command.add("-Dorg.jscience.compute.mode=" + mode);
        command.add("org.jscience.JScience");

        ProcessBuilder pb = new ProcessBuilder(command);
        pb.inheritIO();
        Process process = pb.start();
        process.waitFor();
    }
}
