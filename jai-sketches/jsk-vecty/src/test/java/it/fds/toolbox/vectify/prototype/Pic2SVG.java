/*
 *  Copyright (C) 2007-2012 GeoSolutions S.A.S.
 *  http://www.geo-solutions.it
 *
 *  GPLv3 + Classpath exception
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package it.fds.toolbox.vectify.prototype;

import static it.fds.toolbox.vectify.utils.VectifyUtils.*;
import it.fds.toolbox.vectify.utils.JAIVisualizationUtils;
import it.fds.toolbox.vectify.utils.TimeCounter;

import java.awt.Dimension;
import java.awt.image.RenderedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RenderedOp;
import javax.media.jai.TiledImage;

import org.apache.log4j.Logger;
import org.geoserver.wms.svg.VectifySVGWriter;
import org.geotools.test.TestData;
import org.jaitools.media.jai.regionalize.Region;
import org.jaitools.media.jai.regionalize.RegionalizeDescriptor;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;

/**
 * This is a prototype of the core vectify component.
 * <b>Input:</b> User pass a picture as input (a phot took with a mobile phone ideally) 
 * <b>Output:</b> this stuff returns a vector representation in the svg format.
 * 
 * @author DamianoG
 *
 */
public class Pic2SVG {
    
    private final Logger LOGGER = Logger.getLogger(Pic2SVG.class);
    
    private static final int JAI_TILE_WIDTH = 128;
    
    private TimeCounter tc;
    @Rule 
    public TestName name = new TestName();
    
    @Before
    public void initTests(){
        tc = new TimeCounter();
    }
    
    @Test
    public void vectLowRes() throws FileNotFoundException, IOException{
        pic2SVG(TestData.file(this, "img/vectThisRetiled.tif").getAbsolutePath());
    }
    
    @Ignore
    @Test
    public void vectHighRes() throws FileNotFoundException, IOException{
        pic2SVG(TestData.file(this, "img/vectThis.jpg").getAbsolutePath());
    }

    /**
     * The test body
     * 
     * @throws IOException 
     * @throws FileNotFoundException 
     * 
     */
    private void pic2SVG(String imgPath) throws FileNotFoundException, IOException {
        
        JAI.setDefaultTileSize(new Dimension(JAI_TILE_WIDTH, JAI_TILE_WIDTH));
        Map<String, Object> args = new HashMap<String, Object>();
        
        /**
         * JAI - File LOAD
         */
        PlanarImage pi = JAI.create("fileload", imgPath);
        TiledImage src = new TiledImage(pi, false);
        
        tc.restartCounter();
        
        /**
         * JAI - Do REGIONALIZE
         */
//        JAIVisualizationUtils.displayJAIHelper(src, regions);
        PlanarImage img = doRegionalize(src, 2);
        img.getData();

        
        JAIVisualizationUtils.displayJAIHelper(img, (List<Region>)img.getProperty(RegionalizeDescriptor.REGION_DATA_PROPERTY));
        
        // Save the image on a file
        String outFile = TestData.temp(this, "regionalize.tif").getAbsolutePath();
        RenderedOp outImg = JAI.create("filestore", img, outFile, "TIFF");
        
        /**
         * JAI - Do CONTOUR or VECTORIZE?
         */
//        Collection<Geometry> polys = doContour(img, 0);
        Collection<Geometry> polys = doVectorize(img);
        LOGGER.info("Test '" + name.getMethodName() + "', doRegionalize + doVectorize operation performed in '" + tc.getIntermediateSeconds() + "' seconds...");
        
       
        
        OutputStream out = new FileOutputStream(TestData.temp(this, "svgContent"));
        Envelope env = new Envelope(0, 40, 0, 40);
        VectifySVGWriter writer = new VectifySVGWriter(out, env);
        writer.streamSVG(polys);
        writer.close();
        LOGGER.info("Test '" + name.getMethodName() + "', SVG conversion performed in '" + tc.getDeltaIntermediateSeconds() + "' seconds...");
        LOGGER.info("Test '" + name.getMethodName() + "', Total process performed in '" + tc.getIntermediateSeconds() + "' seconds!");
    }

}
