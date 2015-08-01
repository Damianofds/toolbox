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
package it.fds.toolbox;

import java.awt.Transparency;
import java.awt.image.ColorModel;
import java.awt.image.DataBuffer;
import java.awt.image.SampleModel;

import javax.media.jai.PlanarImage;

/**
 * A library of static methods gathered around the santos tutorial
 * 
 * @author DamianoG
 * 
 */
public class JAIUtils {

    private static final String CARRIAGE_RETURN = "\n";
    
    /**
     * Prints on a String object a bunch of info extracted from the input planar Image
     * 
     * @param planarImage
     * @return a formatted string
     */
    public static String jaiInfo(PlanarImage planarImage) {
        
        StringBuffer outBuffer = new StringBuffer();
        
        // Show the image dimensions and coordinates.
        outBuffer.append("PLANAR IMAGE INFO: ").append(CARRIAGE_RETURN);
        outBuffer.append("Dimensions: ");
        outBuffer.append(planarImage.getWidth()).append("x").append(planarImage.getHeight()).append(" pixels");
        // Remember getMaxX and getMaxY return the coordinate of the next point!
        outBuffer.append(" (from ").append(planarImage.getMinX()).append(",").append(planarImage.getMinY()).append(" to ")
            .append(planarImage.getMaxX() - 1).append(",").append(planarImage.getMaxY() - 1).append(")").append(CARRIAGE_RETURN);
        
        if ((planarImage.getNumXTiles() != 1) || (planarImage.getNumYTiles() != 1)) // Is it tiled?
        {
            // Tiles number, dimensions and coordinates.
            outBuffer.append("Tiles: ");
            outBuffer.append(planarImage.getTileWidth()).append("x").append(planarImage.getTileHeight())
                    .append(" pixels").append(" (").append(planarImage.getNumXTiles()).append("x")
                    .append(planarImage.getNumYTiles()).append(" tiles)");
            outBuffer.append(" (from ").append(planarImage.getMinTileX()).append(","
                   ).append(planarImage.getMinTileY()).append(" to ").append(planarImage.getMaxTileX()).append(","
                   ).append(planarImage.getMaxTileY()).append(")");
            outBuffer.append(" offset: ").append(planarImage.getTileGridXOffset()).append(","
                   ).append(planarImage.getTileGridXOffset());
            
            outBuffer.append(CARRIAGE_RETURN);
        }
        else{
            outBuffer.append("Tiles: NO TILING");
        }
        // Display info about the SampleModel of the image.
        SampleModel sm = planarImage.getSampleModel();
        outBuffer.append("Number of bands: ").append(sm.getNumBands()).append(CARRIAGE_RETURN);
        outBuffer.append("Data type: ");
        switch (sm.getDataType()) {
        case DataBuffer.TYPE_BYTE:
            outBuffer.append("byte").append(CARRIAGE_RETURN);
            break;
        case DataBuffer.TYPE_SHORT:
            outBuffer.append("short").append(CARRIAGE_RETURN);
            break;
        case DataBuffer.TYPE_USHORT:
            outBuffer.append("ushort").append(CARRIAGE_RETURN);
            break;
        case DataBuffer.TYPE_INT:
            outBuffer.append("int").append(CARRIAGE_RETURN);
            break;
        case DataBuffer.TYPE_FLOAT:
            outBuffer.append("float").append(CARRIAGE_RETURN);
            break;
        case DataBuffer.TYPE_DOUBLE:
            outBuffer.append("double").append(CARRIAGE_RETURN);
            break;
        case DataBuffer.TYPE_UNDEFINED:
            outBuffer.append("undefined").append(CARRIAGE_RETURN);
            break;
        }
        
        // Display info about the ColorModel of the image.
        ColorModel cm = planarImage.getColorModel();
        if (cm != null) {
            outBuffer.append("Number of color components: ").append(cm.getNumComponents());
            outBuffer.append("Bits per pixel: ").append(cm.getPixelSize()).append(CARRIAGE_RETURN);
            outBuffer.append("Transparency: ");
            switch (cm.getTransparency()) {
            case Transparency.OPAQUE:
                outBuffer.append("opaque").append(CARRIAGE_RETURN);
                break;
            case Transparency.BITMASK:
                outBuffer.append("bitmask").append(CARRIAGE_RETURN);
                break;
            case Transparency.TRANSLUCENT:
                outBuffer.append("translucent").append(CARRIAGE_RETURN);
                break;
            }
        } else {
            outBuffer.append("No color model.").append(CARRIAGE_RETURN);
        }
        return outBuffer.toString();
    }
}
