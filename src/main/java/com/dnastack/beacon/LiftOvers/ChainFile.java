package com.dnastack.beacon.LiftOvers;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Class for wrapping the chain files used in the liftOver process. Extends java.io.file
 * to easily retrieve chainFiles with minimal user input
 * <p>
 * Created by patrickmagee on 2016-05-26.
 */
public class ChainFile extends File {

    private static final String FILE_PATH_TEMPLATE = "chains/%s/hg%dToHg%d.over.chain";

    private final String buildFrom;

    private final String buildTo;

    /**
     * Constructor for The Chainfile.
     *
     * @param from GenomeBuild that the interval is in
     * @param to   GenomeBuild that is the target
     * @throws IOException
     */
    public ChainFile(GenomeBuild from, GenomeBuild to) throws IOException {
        super(getChainFilePath(from, to));

        buildFrom = from.getBuildName();
        buildTo = to.getBuildName();

        if (!this.exists()) {
            throw new IOException("File does not exist");
        }
    }


    /**
     * Constructor for the ChainFile. Points to a chainfile outside the resources folder
     *
     * @param pathname Path to chainfile
     * @param from Starting GenomeBuild
     * @param to Target GenomeBUild
     * @throws IOException
     */
    public ChainFile(String pathname, GenomeBuild from, GenomeBuild to) throws IOException {
        super(pathname);

        if (from == null || to == null) {
            throw new IllegalArgumentException("GenomeBuilds cannot be null");
        }

        buildFrom = from.getBuildName();
        buildTo = to.getBuildName();

        if (!this.exists()) {
            throw new IOException("File Does not exist");
        }
    }

    /**
     * Constructor for a chainfile with no defined build information. This may be one of the many other chainfiles that are
     * supported
     * @param pathname path to chain file
     * @param buildFrom String of starting genome build
     * @param buildTo String of target genome build
     * @throws IOException
     */
    public ChainFile(String pathname, String buildFrom, String buildTo) throws IOException {
        super(pathname);
        if (buildFrom == null || buildTo == null){
            throw new IllegalArgumentException("Build versions cannot null");
        }

        this.buildFrom = buildFrom;
        this.buildTo = buildTo;

        if (!this.exists()) {
            throw new IOException("File Does not exist");
        }
    }

    /**
     * Retrieve one of the pre-existing chain files from the resource bundle
     *
     * @param from Starting GenomeBuild
     * @param to Target GenomeBuild
     * @return Path of the target ChainFile
     * @throws IOException
     */
    private static String getChainFilePath(GenomeBuild from, GenomeBuild to) throws IOException {
        if (from == null || to == null) {
            throw new IllegalArgumentException("GenomeBuilds cannot be null");
        }

        String resourcePath = String.format(FILE_PATH_TEMPLATE, from.getBuildName(), from.getBuild(), to.getBuild());
        URL url = ChainFile.class.getClassLoader().getResource(resourcePath);

        if (url == null) {
            throw new IOException("Invalid chain file");
        }

        String path = url.getPath();

        if (path.length() == 0) {
            throw new IOException("Invalid chain file");
        }

        return path;
    }

    /**
     * Get the starting genomebuild version, if the build is supported
     *
     * @return GenomeBuild Version
     */
    public String getBuildFrom() {
        return buildFrom;
    }


    /**
     * Get the target genomebuild version, if the build is supported
     *
     * @return GenomeBuild Version
     */
    public String getBuildTo() {
        return buildTo;
    }


    /**
     * Convienence method for retrieving a resource chain file
     *
     * @return Target ChainFile from resource bundle
     * @throws IOException
     */
    public static ChainFile hg17ToHg18() throws IOException {
        return new ChainFile(GenomeBuild.HG17, GenomeBuild.HG18);
    }

    /**
     * Convienence method for retrieving a resource chain file
     *
     * @return Target ChainFile from resource bundle
     * @throws IOException
     */
    public static ChainFile hg17ToHg19() throws IOException {
        return new ChainFile(GenomeBuild.HG17, GenomeBuild.HG19);
    }

    /**
     * Convienence method for retrieving a resource chain file
     *
     * @return Target ChainFile from resource bundle
     * @throws IOException
     */
    public static ChainFile hg18ToHg17() throws IOException {
        return new ChainFile(GenomeBuild.HG18, GenomeBuild.HG17);
    }

    /**
     * Convienence method for retrieving a resource chain file
     *
     * @return Target ChainFile from resource bundle
     * @throws IOException
     */
    public static ChainFile hg18ToHg19() throws IOException {
        return new ChainFile(GenomeBuild.HG18, GenomeBuild.HG19);
    }

    /**
     * Convienence method for retrieving a resource chain file
     *
     * @return Target ChainFile from resource bundle
     * @throws IOException
     */
    public static ChainFile hg18ToHg38() throws IOException {
        return new ChainFile(GenomeBuild.HG18, GenomeBuild.HG38);
    }

    /**
     * Convienence method for retrieving a resource chain file
     *
     * @return Target ChainFile from resource bundle
     * @throws IOException
     */
    public static ChainFile hg19ToHg18() throws IOException {
        return new ChainFile(GenomeBuild.HG19, GenomeBuild.HG18);
    }

    /**
     * Convienence method for retrieving a resource chain file
     *
     * @return Target ChainFile from resource bundle
     * @throws IOException
     */
    public static ChainFile hg19ToHg38() throws IOException {
        return new ChainFile(GenomeBuild.HG19, GenomeBuild.HG38);
    }

    /**
     * Convienence method for retrieving a resource chain file
     *
     * @return Target ChainFile from resource bundle
     * @throws IOException
     */
    public static ChainFile hg19ToHg17() throws IOException {
        return new ChainFile(GenomeBuild.HG19, GenomeBuild.HG17);
    }

    /**
     * Convienence method for retrieving a resource chain file
     *
     * @return Target ChainFile from resource bundle
     * @throws IOException
     */
    public static ChainFile hg38ToHg19() throws IOException {
        return new ChainFile(GenomeBuild.HG38, GenomeBuild.HG19);
    }

}
