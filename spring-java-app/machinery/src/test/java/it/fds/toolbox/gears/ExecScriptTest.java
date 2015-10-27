package it.fds.toolbox.gears;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.geotools.test.TestData;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ExecScriptTest extends Assert{

    private static String POTRACE_DIR;
    
    private static final String POTRACE_BIN_NAME = "potrace.exe";
    private static final String GUESSED_POTRACE_DIR = "D:/work/programs/potrace-1.12.win64";
    private static final String LINUX_LINEBREAK = "\n";
    private static final String WINDOWS_LINEBREAK = "\r\n";

    @BeforeClass
    public static void init(){
        POTRACE_DIR = System.getProperty("POTRACE_DIR", GUESSED_POTRACE_DIR);
        File potrace = new File(POTRACE_DIR, POTRACE_BIN_NAME);
        if(potrace==null || !potrace.exists() || !potrace.isFile() || !potrace.canExecute()){
            fail("I can't find or not execute potrace man...\nDEFAULT Potrace test DIR: 'D:/work/programs/potrace-1.12.win64/'\nSet the POTRACE_DIR environment variable for a custom Potrace bin location");
        }
    }
    
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    
    @Test
    public void execScriptSuccess() throws FileNotFoundException, IOException, InterruptedException{
        Assume.assumeTrue(isWindows());
        final String EXPECTED_OUTPUT = "first_param" + WINDOWS_LINEBREAK + "second_param" + WINDOWS_LINEBREAK + "ELABORATING..." + WINDOWS_LINEBREAK + "- yo! -" + WINDOWS_LINEBREAK + "";
        File script = TestData.file(this, "echo.bat");
        
        ExecScript exec = new ExecScript(script);
        OutputStream out = exec.runScript(null, "first_param", "second_param");
        String outString = new String(((ByteArrayOutputStream)out).toByteArray());
        IOUtils.closeQuietly(out);
        assertEquals(EXPECTED_OUTPUT.length(), outString.length());
        assertEquals(EXPECTED_OUTPUT.toCharArray().length, outString.toCharArray().length);
        assertEquals(EXPECTED_OUTPUT, outString);
    }
    
    @Test
    public void execScriptNullError(){
        File f = null;
        thrown.expect(IllegalArgumentException.class);
        ExecScript es = new ExecScript(f);
        
    }
    
    @Test
    public void execScriptAbsPathError(){
        thrown.expect(IllegalArgumentException.class);
        ExecScript es = new ExecScript("/path/to/a/not/existing/file.txt", true);
    }
    
    private boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }   
}
