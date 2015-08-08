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
package it.fds.toolbox.vectify.utils;

import java.awt.image.RenderedImage;
import java.util.Collection;

import javax.media.jai.JAI;
import javax.media.jai.ParameterBlockJAI;
import javax.media.jai.RenderedOp;

import org.jaitools.media.jai.contour.ContourDescriptor;
import org.jaitools.media.jai.vectorize.VectorizeDescriptor;

import com.vividsolutions.jts.geom.Geometry;

/**
 * @author DamianoG
 *
 */
public class VectifyUtils {

    /**
     * Run the Vectorize operation with given parameters and
     * return the vectors as Collections of JTS Polygon.
     * 
     * @param src the source image
     * @param args a {@code Map} of parameter names and values
     * 
     * @return the generated vectors as JTS Polygons
     */
    public static Collection<Geometry> doVectorize(RenderedImage src) {
        ParameterBlockJAI pb = new ParameterBlockJAI("Vectorize");
        pb.setSource("source0", src);
        
        // Filter polygons with area up to 5 pixels by merging
        // them with the largest neighbouring polygon. Where no neighbour
        // exists (e.g. small region surrounded by NODATA) the polygon
        // will be discarded.
        pb.setParameter("filterThreshold", 2.1);
        pb.setParameter("filterMethod", VectorizeDescriptor.FILTER_DELETE);
        pb.setParameter("band", 0);
        
        // Get the desintation image: this is the unmodified source image data
        // plus a property for the generated vectors
        RenderedOp dest = JAI.create("Vectorize", pb);
        
        // Get the vectors
        Object property = dest.getProperty(VectorizeDescriptor.VECTOR_PROPERTY_NAME);
        return (Collection<Geometry>) property;
    }
    
    public static Collection<Geometry> doContour(RenderedImage src, int band) {
        ParameterBlockJAI pb = new ParameterBlockJAI("Contour");
        pb.setSource("source0", src);

        pb.setParameter("band", band);
        pb.setParameter("interval", 30.0);
        
        // Get the desintation image: this is the unmodified source image data
        // plus a property for the generated vectors
        RenderedOp dest = JAI.create("Contour", pb);
        
        // Get the vectors
        Object property = dest.getProperty(ContourDescriptor.CONTOUR_PROPERTY_NAME);
        return (Collection<Geometry>) property;
    }
    
    public static RenderedOp doRegionalize(RenderedImage src, int band) {
        
        ParameterBlockJAI pb = new ParameterBlockJAI("Regionalize");
        pb.setSource("source0", src);
        pb.setParameter("band", band);
        //An high tollerance means that I should get wider regions 
        pb.setParameter("tolerance", 100d);
        pb.setParameter("diagonal", true);
        RenderedOp regionImg = JAI.create("Regionalize", pb);
        
        return regionImg;
    }
}
