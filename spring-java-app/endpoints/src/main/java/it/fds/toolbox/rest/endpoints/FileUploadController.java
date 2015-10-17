package it.fds.toolbox.rest.endpoints;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class FileUploadController {

    @RequestMapping(value = "/upload", method = RequestMethod.GET)
    public @ResponseBody String provideUploadInfo() {
        return "You can upload a file by posting to this same URL.";
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody String handleFileUpload(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(name)));
                stream.write(bytes);
                stream.close();
                return "You successfully uploaded " + name + "!";
            } catch (Exception e) {
                return "You failed to upload " + name + " => " + e.getMessage();
            }
        } else {
            return "You failed to upload " + name + " because the file was empty.";
        }
    }

    @RequestMapping(value = "/uploadAndProcess", method = RequestMethod.POST, produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] handleFileUploadAndProcess(@RequestParam("name") String name,
            @RequestParam("file") MultipartFile file) throws IOException {

        // InputStream in =
        // servletContext.getResourceAsStream("/images/no_image.jpg");
        // return IOUtils.toByteArray(in);

        if (!file.isEmpty()) {
            InputStream stream = null;
            try {
                byte[] bytes = file.getBytes();
                return bytes;
            } catch (Exception e) {
                return null;
            } finally {
                if (stream != null)
                    stream.close();
            }
        } else {
            return null;
        }
    }

}