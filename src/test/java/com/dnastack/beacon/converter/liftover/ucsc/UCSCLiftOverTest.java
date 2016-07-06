/*
 * Copyright 2016 DNAstack
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.dnastack.beacon.converter.liftover.ucsc;

import com.dnastack.beacon.converter.liftover.api.LiftOver;
import com.dnastack.beacon.converter.util.GenomeBuild;
import com.dnastack.beacon.converter.liftover.exception.LiftOverException;
import htsjdk.samtools.util.Interval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author patrickmagee
 */
@RunWith(JUnit4.class)
public class UCSCLiftOverTest {

    private static final String VALID_CONTIG = "chr1";
    private static final String INVALID_CONTIG = "chr20";
    private static final int VALID_START = 743267;
    private static final int VALID_STOP = 743268;
    private static final int INVALID_START = 77777777;
    private static final int INVALID_STOP = 777777778;
    private static final int ERROR_VALUE = -1;
    private static Interval VALID_INTERVAL;
    private static Interval INVALID_INTERVAL;
    private static LiftOver liftover;

    @Before
    public void setUpClass() throws IOException {
        //Intervals are for hg19
        VALID_INTERVAL = new Interval(VALID_CONTIG, VALID_START, VALID_STOP);
        INVALID_INTERVAL = new Interval(INVALID_CONTIG, INVALID_START, INVALID_STOP);
        liftover = new UCSCLiftOver(GenomeBuild.HG19, GenomeBuild.HG38);
    }

    @Test
    public void testIntervalLiftOverCreation() throws IOException {
        LiftOver liftOver = new UCSCLiftOver(GenomeBuild.HG19, GenomeBuild.HG38);
    }

    @Test
    public void testIntervalLiftOVerCreationFromChainFile() throws IOException {
        LiftOver liftOver = new UCSCLiftOver(new UCSChainFile(GenomeBuild.HG18, GenomeBuild.HG19));

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntervalLiftOverCreationLeftNullParams() throws IOException {
        LiftOver liftOver = new UCSCLiftOver(null, GenomeBuild.HG38);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntervalLiftOverCreationRightNullParams() throws IOException {
        LiftOver liftOver = new UCSCLiftOver(GenomeBuild.HG18, null);
    }

    @Test(expected = IOException.class)
    public void testIntervalLiftOverCreationInvalidGenomeCombination() throws IOException {
        LiftOver liftOver = new UCSCLiftOver(GenomeBuild.HG38, GenomeBuild.HG18);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLiftOverWithNullInterval() throws LiftOverException {
        liftover.liftOver(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLiftOverWithInvalidMismatch() throws LiftOverException {
        liftover.liftOver(VALID_INTERVAL, 5);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLiftOverWithInvalidStart() throws LiftOverException {
        liftover.liftOver(VALID_CONTIG, ERROR_VALUE, VALID_STOP);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLiftOverWithInvalidStop() throws LiftOverException {
        liftover.liftOver(VALID_CONTIG, VALID_START, ERROR_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLiftOverWithInfalidStartAndStop() throws LiftOverException {
        liftover.liftOver(VALID_CONTIG, VALID_STOP, VALID_START);

    }

    @Test
    public void testValidLiftOver() throws LiftOverException {
        int expectedStart = 807887;
        int expectedEnd = 807888;

        Interval result = liftover.liftOver(VALID_INTERVAL);
        assertEquals(expectedStart, result.getStart());
        assertEquals(expectedEnd, result.getEnd());
        assertEquals(VALID_CONTIG, result.getContig());
    }

    @Test(expected = LiftOverException.class)
    public void testInvalidLiftOver() throws LiftOverException {
        Interval result = liftover.liftOver(INVALID_INTERVAL);

    }
}