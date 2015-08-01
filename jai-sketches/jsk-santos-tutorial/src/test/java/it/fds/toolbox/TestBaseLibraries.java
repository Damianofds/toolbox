package it.fds.toolbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.geotools.test.TestData;
import org.junit.Assert;
import org.junit.Test;

/**
 * Unit test for test the generic utility libraries.
 */
public class TestBaseLibraries 
    extends Assert
{

    private final Logger LOGGER = Logger.getLogger(TestBaseLibraries.class);
    
    /**
     * Test the test libraries used junit and geotools TestData
     * Test Apache commons
     * 
     * @throws IOException 
     * @throws FileNotFoundException 
     */
    @Test
    public void testLibraries()
    {
        //Test JUnit :)
        assertTrue( true );
        
        // Test geotools TestData to load a test properties file
        // See this http://docs.geotools.org/latest/developer/conventions/test/data.html
        File propFile = null;
        try {
            propFile = TestData.file(this, "testDataSample.properties");
        } catch (IOException e) {
            fail();
        }
        assertNotNull(propFile);
        assertTrue(propFile.exists());
        assertTrue(propFile.isFile());
        
        // Test apache common configuration loading the propFile
        Configuration config = null;
        try {
            config = new PropertiesConfiguration(propFile);
        } catch (ConfigurationException e) {
            fail();
        }
        String awesome = (String) config.getProperty("test.awesome");
        assertNotNull(awesome);
        assertEquals("What a awesome maven template!", awesome);
        
        // Test Apache StringUtils
        assertTrue(StringUtils.isBlank(null));
        
        // Very strict test about proper log4j working
        LOGGER.info("yeah log4j works!");
    }
}
