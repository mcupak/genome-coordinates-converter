package com.dnastack.beacon.LiftOvers;

import htsjdk.samtools.util.Interval;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by patrickmagee on 2016-05-26.
 */
@RunWith(JUnit4.class)
public class IntervalLiftOverTest {

    private static Interval VALID_INTERVAL;
    private static Interval INVALID_INTERVAL;
    private static IntervalLiftOver liftover;
    private static final String VALID_CONTIG = "chr1";
    private static final String INVALID_CONTIG = "chr20";
    private static final int VALID_START = 743267;
    private static final int VALID_STOP = 743268;
    private static final int INVALID_START = 77777777;
    private static final int INVALID_STOP = 777777778;
    private static final int ERROR_VALUE = -1;


    @Before
    public void setUpClass() throws IOException {
        //Intervals are for hg19
        VALID_INTERVAL = new Interval(VALID_CONTIG, VALID_START, VALID_STOP);
        INVALID_INTERVAL = new Interval(INVALID_CONTIG, INVALID_START, INVALID_STOP);
        liftover = new IntervalLiftOver(GenomeBuild.HG19, GenomeBuild.HG38);
    }


    @Test
    public void testIntervalLiftOverCreation() throws IOException {
        IntervalLiftOver liftOver = new IntervalLiftOver(GenomeBuild.HG19, GenomeBuild.HG38);
        assertEquals(liftOver.getBuildFrom(), GenomeBuild.HG19.getBuildName());
        assertEquals(liftOver.getBuildTo(), GenomeBuild.HG38.getBuildName());
    }

    @Test
    public void testIntervalLiftOVerCreationFromChainFile() throws IOException {
        IntervalLiftOver liftOver = new IntervalLiftOver(ChainFile.hg19ToHg38());
        assertEquals(liftOver.getBuildFrom(), GenomeBuild.HG19.getBuildName());
        assertEquals(liftOver.getBuildTo(), GenomeBuild.HG38.getBuildName());

    }

    @Test(expected = IllegalArgumentException.class)
    public void testIntervalLiftOverCreationLeftNullParams() throws IOException {
        IntervalLiftOver liftOver = new IntervalLiftOver(null, GenomeBuild.HG38);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testIntervalLiftOverCreationRightNullParams() throws IOException {
        IntervalLiftOver liftOver = new IntervalLiftOver(GenomeBuild.HG18, null);
    }


    @Test(expected = IOException.class)
    public void testIntervalLiftOverCreationInvalidGenomeCombination() throws IOException {
        IntervalLiftOver liftOver = new IntervalLiftOver(GenomeBuild.HG38, GenomeBuild.HG18);
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