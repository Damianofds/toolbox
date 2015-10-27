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
package it.fds.toolbox.utils;

import org.springframework.stereotype.Component;

/**
 * Utility class to print computation time for tests
 * 
 * @author DamianoG
 *
 */
public class TimeCounter {

    private static final long SECONDS_CONVERSION = 1000;
    
    /**
     * The start time, a linux milliseconds timestamp 
     */
    private long startTime;
    
    /**
     * The previous requested intermediate time, used in order to allow the delta calculation between intermediate times
     */
    private long lastIntermediateTime;
    
    public TimeCounter(){
        startTime = System.currentTimeMillis();
        lastIntermediateTime = startTime;
    }
    
    public void restartCounter(){
        startTime = System.currentTimeMillis();
        lastIntermediateTime = startTime;
    }
    
    public long getIntermediateSeconds(){
        long currentTime = System.currentTimeMillis();
        lastIntermediateTime = currentTime;
        return (currentTime - startTime) / SECONDS_CONVERSION;
    }
    
    public long getDeltaIntermediateSeconds(){
        long currentTime = System.currentTimeMillis();
        return (currentTime - lastIntermediateTime) / SECONDS_CONVERSION;
    }
}
