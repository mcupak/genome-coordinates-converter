/*
 * The MIT License
 *
 * Copyright 2014 DNAstack.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.dnastack.beacon.liftover.api;

import com.dnastack.beacon.liftover.util.LiftOverException;
import htsjdk.samtools.util.Interval;

/**
 * Liftover interface
 * @author patrickmagee
 */
public interface LiftOver {

    /**
     * LiftOver a single Coordinate
     *
     * @param contig chromosome or contig reference. ie "chr1"
     * @param start  start position.
     * @param end    end position
     * @return new interval of lifted over interval
     * @throws LiftOverException
     */
    Interval liftOver(String contig, int start, int end) throws LiftOverException;


    /**
     * LiftOver a single Coordinate
     *
     * @param contig   chromosome or contig reference. ie "chr1"
     * @param start    start position.
     * @param end      end position
     * @param minMatch % mismatch between positions
     * @return new interval
     * @throws LiftOverException
     */
    Interval liftOver(String contig, int start, int end, double minMatch) throws LiftOverException;

    /**
     * LiftOver a single Coordinate
     *
     * @param interval interval to start with
     * @return new interval
     * @throws LiftOverException
     */
    Interval liftOver(Interval interval) throws LiftOverException;

    /**
     * LiftOver a single Coordinate
     *
     * @param interval interval to start with
     * @param minMatch minimum mismatch percentage
     * @return new interval
     * @throws LiftOverException
     */
    Interval liftOver(Interval interval, double minMatch) throws LiftOverException;


}
