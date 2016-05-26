package com.dnastack.beacon.LiftOvers;

import htsjdk.samtools.liftover.LiftOver;
import htsjdk.samtools.util.Interval;

import java.io.IOException;

/**
 * <p>IntervalLiftOver</p>
 *
 * IntervalLiftOver is a conveience wrapper around htsjdk liftOver. The implementation is the same, however
 * it provides easy methods for the user to implement the liftOver tool.
 * Created by patrickmagee on 2016-05-26.
 */
public class IntervalLiftOver {

    private final ChainFile chainFile;
    private final LiftOver liftOverContainer;


    /**
     * Constructor for creating the IntervalLiftOver Object. Uses chain files in resources
     * @param from Starting GenomeBuild version.
     * @param to Target GenomeBuild version
     * @throws IOException
     */
    public IntervalLiftOver(GenomeBuild from, GenomeBuild to) throws IOException {
        if (from == null || to == null) {
            throw new IllegalArgumentException("Genomne Builds cannot be null");
        }
        chainFile = new ChainFile(from, to);
        liftOverContainer = new LiftOver(chainFile);
    }


    /**
     * Constructor for creating the IntervalLiftOver Object. Uses a custom defined ChainFIle
     * @param chainFile Chainfile to use for liftover
     */
    public IntervalLiftOver(ChainFile chainFile){
        if (chainFile == null){
            throw new IllegalArgumentException("Chainfile cannot be null");
        }
        this.chainFile = chainFile;
        liftOverContainer = new LiftOver(chainFile);
    }

    /**
     * LiftOver a single Coordinate
     * @param contig chromosome or contig reference. ie "chr1"
     * @param start start position.
     * @param end end position
     * @return new interval of lifted over interval
     * @throws LiftOverException
     */
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
     * LiftOver a single Coordinate
     * @param contig chromosome or contig reference. ie "chr1"
     * @param start start position.
     * @param end end position
     * @param minMatch % mismatch between positions
     * @return new interval
     * @throws LiftOverException
     */
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
     * LiftOver a single Coordinate
     * @param interval interval to start with
     * @return new interval
     * @throws LiftOverException
     */
    public Interval liftOver(Interval interval) throws LiftOverException {
        if (interval == null) {
            throw new IllegalArgumentException("Interval cannot be null");
        }
        Interval result = liftOverContainer.liftOver(interval);
        if (result == null) {
            throw new LiftOverException("Could not perform " + getBuildFrom() + " to " + getBuildTo() + " liftover for: " + interval
                    .getContig() + " start: " + interval.getStart() + " end" + interval.getEnd());

        }
        return result;
    }

    /**
     * LiftOver a single Coordinate
     * @param interval interval to start with
     * @param minMatch minimum mismatch percentage
     * @return new interval
     * @throws LiftOverException
     */
    public Interval liftOver(Interval interval, double minMatch) throws LiftOverException {
        if (interval == null) {
            throw new IllegalArgumentException("Interval cannot be null");
        }
        if (minMatch < 0 || minMatch > 1.0) {
            throw new IllegalArgumentException("Illegal min match value. must be between 0 and 1");
        }

        Interval result = liftOverContainer.liftOver(interval, minMatch);
        if (result == null) {
            throw new LiftOverException("Could not perform " + getBuildFrom() + " to " + getBuildTo() + " liftover for: " + interval
                    .getContig() + " start: " + interval.getStart() + " end" + interval.getEnd());

        }
        return result;
    }

    /**
     * Get the starting genomebuild version, if the build is supported
     * @return GenomeBuild Version
     */
    public String getBuildFrom() {
        return chainFile.getBuildFrom();
    }

    /**
     * Get the genomebuild version to lift to, if the build is supported
     * @return GenomeBuild Version
     */
    public String getBuildTo() {
        return chainFile.getBuildTo();
    }


    /**
     * Convienience method for Building a liftover from hg17 to hg18
     * @return new IntervalLiftOver object
     * @throws IOException
     */
    public static IntervalLiftOver hg17ToHg18() throws IOException {
        return new IntervalLiftOver(ChainFile.hg17ToHg18());
    }

    /**
     * Convienience method for Building a liftover from hg17 to hg19
     * @return new IntervalLiftOver object
     * @throws IOException
     */
    public static IntervalLiftOver hg17ToHg19() throws IOException {
        return new IntervalLiftOver(ChainFile.hg17ToHg19());
    }

    /**
     * Convienience method for Building a liftover from hg18 to hg19
     * @return new IntervalLiftOver object
     * @throws IOException
     */
    public static IntervalLiftOver hg18ToHg17() throws IOException {
        return new IntervalLiftOver(ChainFile.hg18ToHg17());
    }

    /**
     * Convienience method for Building a liftover from hg18 to hg19
     * @return new IntervalLiftOver object
     * @throws IOException
     */
    public static IntervalLiftOver hg18ToHg19() throws IOException {
        return new IntervalLiftOver(ChainFile.hg17ToHg19());
    }

    /**
     * Convienience method for Building a liftover from hg18 to hg38
     * @return new IntervalLiftOver object
     * @throws IOException
     */
    public static IntervalLiftOver hg18ToHg38() throws IOException {
        return new IntervalLiftOver(ChainFile.hg18ToHg38());
    }

    /**
     * Convienience method for Building a liftover from hg19 to hg18
     * @return new IntervalLiftOver object
     * @throws IOException
     */
    public static IntervalLiftOver hg19ToHg18() throws IOException {
        return new IntervalLiftOver(ChainFile.hg19ToHg18());
    }

    /**
     * Convienience method for Building a liftover from hg19 to hg38
     * @return new IntervalLiftOver object
     * @throws IOException
     */
    public static IntervalLiftOver hg19ToHg38() throws IOException {
        return new IntervalLiftOver(ChainFile.hg19ToHg38());
    }

    /**
     * Convienience method for Building a liftover from hg19 to hg17
     * @return new IntervalLiftOver object
     * @throws IOException
     */
    public static IntervalLiftOver hg19ToHg17() throws IOException {
        return new IntervalLiftOver(ChainFile.hg19ToHg17());
    }

    /**
     * Convienience method for Building a liftover from hg38 to hg19
     * @return new IntervalLiftOver object
     * @throws IOException
     */
    public static IntervalLiftOver hg38ToHg19() throws IOException {
        return new IntervalLiftOver(ChainFile.hg38ToHg19());
    }

}
