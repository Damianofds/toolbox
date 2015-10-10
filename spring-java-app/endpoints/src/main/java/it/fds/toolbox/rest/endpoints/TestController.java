package it.fds.toolbox.rest.endpoints;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.fds.toolbox.gears.RandomNumbers;
import it.fds.toolbox.rest.model.dto.Message;

@RestController
public class TestController {

    private static final String template = "You got you endpoint running, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    private RandomNumbers rn;
    
    @RequestMapping("/basicTextEndPoint")
    public Message message(@RequestParam(value = "msg", defaultValue = "Enjoy it Man!!!") String msg) {
        return new Message(counter.incrementAndGet(), String.format(template, msg));
    }
    
    @RequestMapping("/machineryTextEndPoint")
    public String randomNumbers() {
        return rn.produceRandomNumbers();
    }
}