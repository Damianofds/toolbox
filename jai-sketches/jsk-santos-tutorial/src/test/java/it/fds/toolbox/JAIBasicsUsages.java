package it.fds.toolbox;

import java.awt.Point;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferByte;
import java.awt.image.Raster;
import java.awt.image.SampleModel;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.media.jai.JAI;
import javax.media.jai.PlanarImage;
import javax.media.jai.RasterFactory;
import javax.media.jai.RenderedOp;
import javax.media.jai.TiledImage;

import org.apache.log4j.Logger;
import org.geotools.test.TestData;
import org.junit.Assert;
import org.junit.Test;

/**
 * Handy overview of the JAI usage
 * 
 * @see http://seer.ufrgs.br/rita/article/viewFile/rita_v11_n1_p93-124/3555
 * 
 */
public class JAIBasicsUsages extends Assert {

    private final Logger LOGGER = Logger.getLogger(JAIBasicsUsages.class);
    
    /**
     * Creates from scratch an image representing an oblique grayshade gradient
     * 
     * @throws IOException
     */
    @Test
    public void grayShadeTest() throws IOException {

        int width = 1024;
        int height = 1024; // Dimensions of the image.
        float[] imageData = new float[width * height]; // Image data array.
        int count = 0; // Auxiliary counter.

        // Fill the array with a degradé pattern.
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                imageData[count++] = (float) (Math.sqrt(w + h));
            }
        }

        // Create a DataBuffer from the values on the image array.
        javax.media.jai.DataBufferFloat dbuffer = new javax.media.jai.DataBufferFloat(imageData,
                width * height);

        // Create a float data sample model.
        SampleModel sampleModel = RasterFactory.createBandedSampleModel(DataBuffer.TYPE_FLOAT,
                width, height, 1);

        // Create a compatible ColorModel.
        ColorModel colorModel = PlanarImage.createColorModel(sampleModel);

        // Create a WritableRaster.
        Raster raster = RasterFactory.createWritableRaster(sampleModel, dbuffer, new Point(0, 0));

        // Create a TiledImage using the float SampleModel.
        TiledImage tiledImage = new TiledImage(0, 0, width, height, 0, 0, sampleModel, colorModel);

        // Set the data of the tiled image to be the raster.
        tiledImage.setData(raster);

        // Save the image on a file
        String outFile = TestData.temp(this, "floatpattern.tif").getAbsolutePath();
        RenderedOp outImg = JAI.create("filestore", tiledImage, outFile, "TIFF");
        // PLACE A BREAKPOINT HERE IF YOU WANT TO OPEN THE PRODUCE IMAGE (You can use this nice viewer http://www.irfanview.com/)
        // The created file is automatically deleted after this method ends.
        assertNotNull(outImg);
    }

    /**
     * Creates from scratch an image representing an oblique RGB gradient
     * 
     * @throws IOException
     */
    @Test
    public void rgbTest() throws IOException {

        int width = 121;
        int height = 121; // Dimensions of the image
        byte[] data = new byte[width * height * 3]; // Image data array.
        int count = 0; // Temporary counter.

        // Fill the array with a pattern.
        for (int w = 0; w < width; w++) {
            for (int h = 0; h < height; h++) {
                data[count + 0] = (count % 2 == 0) ? (byte) 255 : (byte) 0;
                data[count + 1] = 0;
                data[count + 2] = (count % 2 == 0) ? (byte) 0 : (byte) 255;
                count += 3;
            }
        }

        // Create a Data Buffer from the values on the single image array.
        DataBufferByte dbuffer = new DataBufferByte(data, width * height * 3);

        // Create an pixel interleaved data sample model.
        SampleModel sampleModel = RasterFactory.createPixelInterleavedSampleModel(
                DataBuffer.TYPE_BYTE, width, height, 3);

        // Create a compatible ColorModel.
        ColorModel colorModel = PlanarImage.createColorModel(sampleModel);

        // Create a WritableRaster.
        Raster raster = RasterFactory.createWritableRaster(sampleModel, dbuffer, new Point(0, 0));

        // Create a TiledImage using the SampleModel.
        TiledImage tiledImage = new TiledImage(0, 0, width, height, 0, 0, sampleModel, colorModel);

        // Set the data of the tiled image to be the raster.
        tiledImage.setData(raster);

        // Save the image on a file
        String outFile = TestData.temp(this, "rgbpattern.tif").getAbsolutePath();
        RenderedOp outImg = JAI.create("filestore", tiledImage, outFile, "TIFF");
        ;
        // PLACE A BREAKPOINT HERE IF YOU WANT TO OPEN THE PRODUCE IMAGE (You can use this nice viewer http://www.irfanview.com/)
        // The created file is automatically deleted after this method ends.
        assertNotNull(outImg);
    }

    @Test
    public void infoTest() throws FileNotFoundException, IOException {

        File imgDir = TestData.file(this, "img");
        assertNotNull(imgDir);
        assertTrue(imgDir.isDirectory());
        
        File [] files = imgDir.listFiles();
        assertNotNull(files);
        assertEquals(3, files.length);
        for(File f : files){
            // Open the image (using the name passed as a command line parameter)
            PlanarImage pi = JAI.create("fileload", f.getAbsoluteFile().toString());
            // Get the image file size (non-JAI related).
            String info = JAIUtils.jaiInfo(pi);
            LOGGER.info("***** Inspecting file '" + f.getName() + "' Image file size: " + f.length() + " bytes. *****");
            LOGGER.info("\n"+info);
        }
    }

}
