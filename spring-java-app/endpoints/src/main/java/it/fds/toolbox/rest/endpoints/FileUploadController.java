package it.fds.toolbox.rest.endpoints;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import it.fds.toolbox.gears.ExecScript;

@Controller
public class FileUploadController {

    private final Logger LOGGER = Logger.getLogger(FileUploadController.class);

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {
        LOGGER.debug("/upload POST request started...");
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                LOGGER.info("upload POST request SUCCESS, file: '" + name + "' successfully uploaded...");
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                LOGGER.error("upload POST request FAILED " + e.getMessage());
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            LOGGER.error("upload POST request FAILED, the file: '" + name + "' is empty...");
            return "You failed to upload '" + name + "' because the file was empty.";
        }
    }
    
    @RequestMapping(value = "/uploadAndProcess", method = RequestMethod.POST, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] handleFileUploadAndProcess(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) throws IOException {
        LOGGER.debug("/uploadAndProcess POST request started...");
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                LOGGER.info("uploadAndProcess POST request SUCCESS, file: '" + name + "' successfully uploaded...");
                return bytes;
            } catch (Exception e) {
                LOGGER.error("uploadAndProcess POST request FAILED " + e.getMessage());
                return null;
            }
        } else {
            LOGGER.error("uploadAndProcess POST request FAILED, the file: '" + name + "' is empty...");
            return null;
        }
    }
}