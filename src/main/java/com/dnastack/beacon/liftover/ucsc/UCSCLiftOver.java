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
package com.dnastack.beacon.liftover.ucsc;

import com.dnastack.beacon.liftover.util.GenomeBuild;
import com.dnastack.beacon.liftover.util.LiftOverException;
import com.dnastack.beacon.liftover.api.ChainFile;
import htsjdk.samtools.liftover.LiftOver;
import htsjdk.samtools.util.Interval;

import java.io.IOException;

/**
 * <p>UCSCLiftOver</p>
 *
 * UCSCLiftOver is a conveience wrapper around htsjdk liftOver. The implementation is the same, however
 * it provides easy methods for the user to implement the liftOver tool.
 * @author patrickmagee
 */
public class UCSCLiftOver implements com.dnastack.beacon.liftover.api.LiftOver {

    private final ChainFile chainFile;
    private final LiftOver liftOverContainer;


    /**
     * Constructor for creating the UCSCLiftOver Object. Uses chain files in resources
     * @param from Starting GenomeBuild version.
     * @param to Target GenomeBuild version
     * @throws IOException
     */
    public UCSCLiftOver(GenomeBuild from, GenomeBuild to) throws IOException {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Genomne Builds cannot be null");
        }
        chainFile = new UCSChainFile(from, to);
        liftOverContainer = new LiftOver(chainFile);
    }


    /**
     * Constructor for creating the UCSCLiftOver Object. Uses a custom defined ChainFIle
     * @param chainFile Chainfile to use for liftover
     */
    public UCSCLiftOver(ChainFile chainFile){
        if (chainFile == null){
            throw new IllegalArgumentException("Chainfile cannot be null");
        }
        this.chainFile = chainFile;
        liftOverContainer = new LiftOver(chainFile);
    }

    /**
     * {@inheritDocs}
     */
    @Override
    public Interval liftOver(String contig, int start, int end) throws LiftOverException {
        if (contig == null) {
            throw new IllegalArgumentException("Contig cannot be null");
        }
        if (start < 0 || end < 0 || start <= end) {
            throw new IllegalArgumentException("Invalid start and end values");
        }

        Interval interval = new Interval(contig, start, end);
        return liftOver(interval);
    }


    /**
     * {@inheritDocs}
     */
    @Override
    public Interval liftOver(String contig, int start, int end, double minMatch) throws LiftOverException {
        if (contig == null) {
            throw new IllegalArgumentException("Contig cannot be null");
        }
        if (start < 0 || end < 0 || start <= end) {
            throw new IllegalArgumentException("Invalid start and end values");
        }

        Interval interval = new Interval(contig, start, end);
        return liftOver(interval, minMatch);
    }

    /**
     * {@inheritDocs}
     */
    @Override
    public Interval liftOver(Interval interval) throws LiftOverException {
        if (interval == null) {
            throw new IllegalArgumentException("Interval cannot be null");
        }
        Interval result = liftOverContainer.liftOver(interval);
        if (result == null) {
            throw new LiftOverException("Could not perform " + chainFile.getBuildFrom() + " to " + chainFile.getBuildTo() + " liftover for: " + interval
                    .getContig() + " start: " + interval.getStart() + " end" + interval.getEnd());

        }
        return result;
    }

    /**
     * {@inheritDocs}
     */
    @Override
    public Interval liftOver(Interval interval, double minMatch) throws LiftOverException {
        if (interval == null) {
            throw new IllegalArgumentException("Interval cannot be null");
        }
        if (minMatch < 0 || minMatch > 1.0) {
            throw new IllegalArgumentException("Illegal min match value. must be between 0 and 1");
        }

        Interval result = liftOverContainer.liftOver(interval, minMatch);
        if (result == null) {
            throw new LiftOverException("Could not perform " + chainFile.getBuildFrom() + " to " + chainFile.getBuildTo() + " liftover for: " + interval
                    .getContig() + " start: " + interval.getStart() + " end" + interval.getEnd());

        }
        return result;
    }

}
