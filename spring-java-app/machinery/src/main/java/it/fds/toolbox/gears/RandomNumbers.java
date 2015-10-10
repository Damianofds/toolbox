package it.fds.toolbox.gears;

import org.springframework.stereotype.Component;

/**
 * Produce a random length list of random numbers
 * 
 * @author fds
 */
@Component
public class RandomNumbers {

    public String produceRandomNumbers(){
        
        StringBuilder sb = new StringBuilder();
        int iterations = ((int)(Math.random()*10))+3;
        int multiplier = (int)(Math.random()*10);
        for(int i=0; i<iterations; i++){
            sb.append("'").append((int)(Math.random()*multiplier)).append("', ");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
