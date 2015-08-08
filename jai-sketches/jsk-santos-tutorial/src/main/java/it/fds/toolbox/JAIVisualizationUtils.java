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

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;

import javax.media.jai.PlanarImage;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import com.sun.media.jai.widget.DisplayJAI;

/**
 * A set of features to easily create visualization of the images for debugging and testing purposes
 * 
 * @author DamianoG
 *
 */
public class JAIVisualizationUtils {

    /**
     * Visualize an image in a JFrame
     * 
     * @param image the image to visualize
     * @throws IOException
     */
    public void displayJAIHelper(PlanarImage image) throws IOException {

        String imageInfo = "Dimensions: " + image.getWidth() + "x" + image.getHeight() + " Bands: " + image.getNumBands();
        JFrame frame = new JFrame();
        frame.setTitle("Display images using JAI");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BorderLayout());
        DisplayJAI dj = new DisplayJAI(image);
        contentPane.add(new JScrollPane(dj), BorderLayout.CENTER);
        contentPane.add(new JLabel(imageInfo), BorderLayout.SOUTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setVisible(true);
     }
}
