package it.fds.toolbox;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import it.fds.toolbox.gears.ExecScript;

@Configuration
public class AppConfig {

    private final static String DEFAULT_CONFIG_FILENAME = "appConfig,properties";
    private final static String PROP_POTRACE_DIR = "potraceDir";
    private final static String PROP_POTRACE_EXEC = "potraceExecutable";
    
    private org.apache.commons.configuration.Configuration configuration;
    
    public AppConfig() throws ConfigurationException{
        File propFile = lookupConfigFile();
        configuration = new PropertiesConfiguration(propFile);
    }

    @Bean
    public ExecScript script() {
        return new ExecScript(configuration.getString(PROP_POTRACE_DIR),configuration.getString(PROP_POTRACE_EXEC));
    }
    
    /**
     * This method search for a config file. 
     * The lookup logic should implement a geoserver-datadir-like lookup sooner or later but for the moment simply spit out a fucking file.
     *  
     * @return a properties {@link File} contains the app configurations
     */
    private File lookupConfigFile(){
        return new File(AppConfig.class.getResource(DEFAULT_CONFIG_FILENAME).getFile());
    }

}