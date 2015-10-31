package it.fds.toolbox.gears;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import it.fds.toolbox.utils.TimeCounter;

public class ExecScript {

    private final Logger LOGGER = Logger.getLogger(ExecScript.class);
    
    private CommandLine cmdLine;
    
    private TimeCounter tc;
    
    public ExecScript(){}
    
    public ExecScript(String script){
        cmdLine = new CommandLine(script);
        tc = new TimeCounter();
    }
    
    public ExecScript(File script){
        if(script == null || !script.exists() || !script.isFile() || !script.canExecute()){
            LOGGER.error("the script provided is null, doesn't exist, it's not a file or it cannot be executed...");
            throw new IllegalArgumentException("the script provided is null, doesn't exist, it's not a file or it cannot be executed...");
        }
        cmdLine = new CommandLine(script);
        tc = new TimeCounter();
    }
    
    public ExecScript(String scriptAbsPath, boolean dirtyFlag){
        this(new File(scriptAbsPath));
    }
    
    public ExecScript(File scriptDir, String scriptName){
        this(new File(scriptDir, scriptName));
    }
    
    public ExecScript(String scriptDir, String scriptName){
        this(new File(scriptDir, scriptName));
    }
    
    /**
     * 
     * @param substitutionMap example:
     *          <pre>
     *               Map<String, File> map = new HashMap<>();
     *               map.put("file", new File("invoice.pdf"));
     *               cmdLine.setSubstitutionMap(map);
     *          </pre>
     *          
     * @param args the arguments tu pass to the script to run
     * @throws Exception 
     * 
     */
    public String runScript(Map substitutionMap, String... args) throws Exception{
        for(String param : args){
            cmdLine.addArgument(param);
        }
        if(substitutionMap != null){
            cmdLine.setSubstitutionMap(substitutionMap);
        }

        OutputStream executorOut = new ByteArrayOutputStream();
        
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        ExecuteWatchdog watchdog = new ExecuteWatchdog(60*1000);
        Executor executor = new DefaultExecutor();
        executor.setExitValue(1);
        executor.setWatchdog(watchdog);
        executor.setStreamHandler(new PumpStreamHandler(executorOut));
        try {
            LOGGER.info("Calling the scritpt...");
            tc.restartCounter();
            executor.execute(cmdLine, resultHandler);
            executor.getStreamHandler();
            LOGGER.info("Script called... waiting...");
            resultHandler.waitFor();
            LOGGER.info("Script execution last: '" + tc.getIntermediateSeconds() + "' seconds");
            return executorOut.toString();
        } catch (IOException | InterruptedException e) {
            LOGGER.info("An exception occurred while executing the script..." + e.getMessage());
            throw new Exception("VECTY EXCEPTION");
        } finally {
            try {
                if(executorOut != null){
                    executorOut.close();
                }
            } catch (IOException e) {
                //swallow exception
            }
        }
    }
}
