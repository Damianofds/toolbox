package it.fds.toolbox.gears;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.geotools.test.TestData;
import org.junit.Assert;
import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.Ignore;
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
    public void execScriptSuccess() throws Exception{
        Assume.assumeTrue(isWindows());
        final String EXPECTED_OUTPUT = "first_param" + WINDOWS_LINEBREAK + "second_param" + WINDOWS_LINEBREAK + "ELABORATING..." + WINDOWS_LINEBREAK + "- yo! -" + WINDOWS_LINEBREAK + "";
        File script = TestData.file(this, "echo.bat");
        
        ExecScript exec = new ExecScript(script);
        String out = exec.runScript(null, "first_param", "second_param");
        assertEquals(EXPECTED_OUTPUT.length(), out.length());
        assertEquals(EXPECTED_OUTPUT.toCharArray().length, out.toCharArray().length);
        assertEquals(EXPECTED_OUTPUT, out);
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

    /**
     * Test if potrace works and the version is 1.12
     * @throws Exception 
     */
    @Test
    public void execPotraceVersion() throws Exception{
        final String EXPECTED_OUTPUT = "potrace 1.12. Copyright (C) 2001-2015 Peter Selinger." + LINUX_LINEBREAK + "Library version: potracelib 1.12" + LINUX_LINEBREAK + "Default unit: inches" + LINUX_LINEBREAK + "Default page size: letter" + LINUX_LINEBREAK;
        ExecScript es = new ExecScript(POTRACE_DIR, POTRACE_BIN_NAME);
        String out = es.runScript(null, "-v");
        
        assertEquals(EXPECTED_OUTPUT.length(), out.length());
        assertEquals(EXPECTED_OUTPUT.toCharArray().length, out.toCharArray().length);
        assertEquals(EXPECTED_OUTPUT, out);
    }
    
    @Ignore
    @Test
    public void execPotrace() throws Exception{
        File image = TestData.file(this, "sampleRaster.pbm");
        File outFileTest = TestData.file(this, "sampleRasterTest.svg");
        File outFile = TestData.temp(this, "sampleRaster.svg");
        
        ExecScript es = new ExecScript(POTRACE_DIR, POTRACE_BIN_NAME);
        String out = es.runScript(null, "-s", "-o", outFile.getAbsolutePath(), image.getAbsolutePath());
        
        InputStream is = new FileInputStream(outFile);
        final String result = IOUtils.toString(is);
        IOUtils.closeQuietly(is);
        
        is = new FileInputStream(outFileTest);
        final String EXPECTED_OUTPUT = IOUtils.toString(is);
        IOUtils.closeQuietly(is);
        assertEquals("".length(), out.length());
        assertEquals(EXPECTED_OUTPUT.toCharArray().length, result.toCharArray().length);
        assertEquals(EXPECTED_OUTPUT, result);
    }
    
    private boolean isWindows() {
        return System.getProperty("os.name").startsWith("Windows");
    }   
}
