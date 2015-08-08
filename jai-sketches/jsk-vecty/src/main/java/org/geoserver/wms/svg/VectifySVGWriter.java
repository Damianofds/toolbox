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
package org.geoserver.wms.svg;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.geoserver.wms.svg.SVGWriter;
import org.geotools.data.DataUtilities;
import org.geotools.data.simple.SimpleFeatureCollection;
import org.geotools.data.simple.SimpleFeatureIterator;
import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.geotools.feature.simple.SimpleFeatureTypeBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;

import com.vividsolutions.jts.geom.Envelope;
import com.vividsolutions.jts.geom.Geometry;
import com.vividsolutions.jts.geom.Polygon;

/**
 * @author DamianoG
 * 
 */
public class VectifySVGWriter extends SVGWriter {

    /** the XML and SVG header */
    private static final String SVG_HEADER = "<?xml version=\"1.0\" standalone=\"no\"?>\n\t"
            + "<svg xmlns=\"http://www.w3.org/2000/svg\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" \n\tstroke=\"green\" \n\tfill=\"none\" \n\tstroke-width=\"0.1%\"\n\tstroke-linecap=\"round\"\n\tstroke-linejoin=\"round\"\n\twidth=\"100\" \n\theight=\"100\" \n\tviewBox=\"0 0 100 100\" \n\tpreserveAspectRatio=\"xMidYMid meet\">\n";
    
    private static final String SVG_FOOTER = "</svg>\n";
    
    /**
     * @param out
     * @param mapAreaOfInterest
     */
    public VectifySVGWriter(OutputStream out, Envelope mapAreaOfInterest) {
        super(out, mapAreaOfInterest);
    }

    public void streamSVG(Collection<Geometry> geom) throws IOException {

        this.write(SVG_HEADER);
        this.writeFeatures(createFeatureType(), createFeatureList(geom), "bello_stile_bro!");
        this.write(SVG_FOOTER);
    }

    private SimpleFeatureType createFeatureType() {
        SimpleFeatureTypeBuilder b = new SimpleFeatureTypeBuilder();

        // set the name
        b.setName("vectorizedPic");

        // add a geometry property
        // b.setCRS(DefaultGeographicCRS.WGS84); // set crs first
        b.add("the_geom", Polygon.class); // then add geometry

        // add some properties
        // well.. just a name actually
        b.add("name", String.class);

        // build the type
        return b.buildFeatureType();
    }

    private SimpleFeatureIterator createFeatureList(Collection<Geometry> geoms) {

        List<SimpleFeature> features = new LinkedList<SimpleFeature>();
        int i = 0;
        for (Geometry poly : geoms) {
            features.add(createSimpleFeature(poly, i++));
        }

        SimpleFeatureCollection sfs = DataUtilities.collection(features);
        
        return sfs.features();
    }

    private SimpleFeature createSimpleFeature(Geometry poly, int i) {
        SimpleFeatureType sft = createFeatureType();
        SimpleFeatureBuilder builder = new SimpleFeatureBuilder(sft);

        // add the values
        builder.add(poly);
        builder.add("svg-" + i);

        // build the feature with provided ID
        return builder.buildFeature("fid." + i);
    }
}
