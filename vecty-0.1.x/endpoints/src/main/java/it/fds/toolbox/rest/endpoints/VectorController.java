package it.fds.toolbox.rest.endpoints;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.fds.toolbox.gears.ExecScript;

@Controller
public class VectorController {

    private final Logger LOGGER = Logger.getLogger(VectorController.class);

    @Autowired
    private ExecScript es;

//    @RequestMapping(value = "/potraceIt", method = RequestMethod.POST, produces = "image/svg+xml")
//    public @ResponseBody String potraceIt(@RequestParam("file") MultipartFile file) throws Exception {
//
//        final String POTRACE_BIN_NAME = "potrace.exe";
//        final String POTRACE_DIR = "D:/work/programs/potrace-1.12.win64";
//
//        LOGGER.debug("/potraceIt POST request started...");
//        FileOutputStream fos = null;
//        FileInputStream fis = null;
//        File potraceInputImage = null;
//        if (!file.isEmpty()) {
//            try {
//                byte[] bytes = file.getBytes();
//                potraceInputImage = File.createTempFile("potraceInputImage" + (int) (Math.random() * 10000), ".tmp");
//                fos = new FileOutputStream(potraceInputImage);
//                fos.write(bytes);
//                fos.close();
//                LOGGER.info("potraceIt POST request SUCCESS, file: '" + potraceInputImage.getName()
//                        + "' successfully uploaded...");
//
//                File potraceOutput = File.createTempFile("potraceOutput" + (int) (Math.random() * 10000), ".tmp");
////                ExecScript es = new ExecScript(POTRACE_DIR, POTRACE_BIN_NAME);
//                String outMsg = es.runScript(null, "-s", "-o", potraceOutput.getAbsolutePath(),
//                        potraceInputImage.getAbsolutePath());
//
//                StringBuilder potraceOutMsg = new StringBuilder();
//                potraceOutMsg.append("POTRACE EXEC OUT: >> '");
//                potraceOutMsg.append(outMsg);
//                if (outMsg.isEmpty()) {
//                    potraceOutMsg.append("No potrace output... it could be a good sign...");
//                }
//                potraceOutMsg.append("'");
//                LOGGER.info(potraceOutMsg.toString());
//
//                fis = new FileInputStream(potraceOutput);
//                return IOUtils.toString(fis);
//
//            } catch (IOException e) {
//                LOGGER.error("potraceIt POST request FAILED " + e.getMessage());
//                return null;
//            } finally {
//                IOUtils.closeQuietly(fis);
//                IOUtils.closeQuietly(fos);
//            }
//        } else {
//            LOGGER.error("potraceIt POST request FAILED, the file: '" + potraceInputImage.getName() + "' is empty...");
//            return null;
//        }
//    }
    
    @RequestMapping(value = "/processIt", method = RequestMethod.POST, produces = "image/svg+xml")
    public @ResponseBody String processIt(@RequestParam("file") MultipartFile file) throws Exception {

        final String POTRACE_BIN_NAME = "potrace.exe";
        final String POTRACE_DIR = "D:/work/programs/potrace-1.12.win64";

        LOGGER.debug("/processIt POST request started...");
        FileOutputStream fos = null;
        FileInputStream fis = null;
        File potraceInputImage = null;
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                potraceInputImage = File.createTempFile("processInputImage" + (int) (Math.random() * 10000), ".tmp");
                fos = new FileOutputStream(potraceInputImage);
                fos.write(bytes);
                fos.close();
                LOGGER.info("processIt POST request SUCCESS, file: '" + potraceInputImage.getName()
                        + "' successfully uploaded...");

                File potraceOutput = File.createTempFile("processOutput" + (int) (Math.random() * 10000), ".tmp");
//                ExecScript es = new ExecScript(POTRACE_DIR, POTRACE_BIN_NAME);
                String outMsg = es.runScript(null, "-s", "-o", potraceOutput.getAbsolutePath(),
                        potraceInputImage.getAbsolutePath());

                StringBuilder potraceOutMsg = new StringBuilder();
                potraceOutMsg.append("PROCESS EXEC OUT: >> '");
                potraceOutMsg.append(outMsg);
                if (outMsg.isEmpty()) {
                    potraceOutMsg.append("No script output... it could be a good sign...");
                }
                potraceOutMsg.append("'");
                LOGGER.info(potraceOutMsg.toString());

                fis = new FileInputStream(potraceOutput);
                return IOUtils.toString(fis);

            } catch (IOException e) {
                LOGGER.error("processIt POST request FAILED " + e.getMessage());
                return null;
            } finally {
                IOUtils.closeQuietly(fis);
                IOUtils.closeQuietly(fos);
            }
        } else {
            LOGGER.error("processIt POST request FAILED, the file: '" + potraceInputImage.getName() + "' is empty...");
            return null;
        }
    }

}