package com.dnastack.beacon.LiftOvers;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Class for wrapping the chain files used in the liftOver process. Extends java.io.file
 * to easily retrieve chainFiles with minimal user input
 * <p>
 * Created by patrickmagee on 2016-05-26.
 */
public class ChainFile extends File {

    private static final String FILE_PATH_TEMPLATE = "chains/%s/hg%dToHg%d.over.chain";
    private static final String UCSC_REMOTE_TEMPLATE = "http://hgdownload.cse.ucsc.edu/goldenPath/%s/liftOver/%s";

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
     * @param from     Starting GenomeBuild
     * @param to       Target GenomeBUild
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
     *
     * @param pathname  path to chain file
     * @param buildFrom String of starting genome build
     * @param buildTo   String of target genome build
     * @throws IOException
     */
    public ChainFile(String pathname, String buildFrom, String buildTo) throws IOException {
        super(pathname);
        if (buildFrom == null || buildTo == null) {
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
     * @param to   Target GenomeBuild
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
     * Given a File object, return a new ChainFile object pointing to the same file referenced by the File Object
     *
     * @param file      File to Open
     * @param buildFrom Starting GenomeBuild
     * @param buildTo   Target GenomeBuild
     * @return Chainfile
     * @throws IOException
     */
    public static ChainFile fromFile(File file, String buildFrom, String buildTo) throws IOException {
        String pathname = file.getAbsolutePath();
        return new ChainFile(pathname, buildFrom, buildTo);
    }

    /**
     * UCSC hosts a large repository of publicly available data. Their repo includes a large number of chain files
     * for a variety of different species and genome builds. The general url pattern of their download site is:
     * <p>
     * http://hgdownload.cse.ucsc.edu/goldenPath/[Genome Build]/liftOver/[File Name]
     * <p>
     * Usage of this methods simply requires the user to defined the build that they are starting with, the build
     * they are going to and the file name that they would like to retrieve.
     * <p>
     * For a full list of all buiilds and downloads:
     *
     * @param buildFrom Starting GenomeBuild
     * @param buildTo   Target GenomeBuild
     * @param fileName  name of the target file
     * @return ChainFile
     * @throws IOException
     * @see <a href="http://hgdownload.cse.ucsc.edu/downloads.html">UCSC Downloads</a>
     */
    public static ChainFile fromUCSCRemote(String buildFrom, String buildTo, String fileName) throws IOException {
        if (buildFrom == null || fileName == null) {
            throw new IllegalArgumentException("Arguments cannot be null");
        }

        URL url = new URL(String.format(UCSC_REMOTE_TEMPLATE, buildFrom, fileName));

        return fromUrl(url, buildFrom, buildTo);
    }


    /**
     * This method takes a url pointing to a chainfile hosted on a remote server, and attempts to download the file.
     * The file is saved as a temp file.
     * Note:
     * There is no cleanup after this method, and the temp file is not deleted once all references to it are gone
     *
     * @param url       Url to a chainFile
     * @param buildFrom Starting GenomeBuild
     * @param buildTo   Target GenomeBuild
     * @return ChainFile
     * @throws IOException
     */
    public static ChainFile fromUrl(URL url, String buildFrom, String buildTo) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("url cannot be null");
        }

        InputStream stream;
        InputStream inStream = url.openStream();
        if (url.getPath().endsWith(".gz")) {
            GZIPInputStream gzipInputStream = new GZIPInputStream(inStream);
            stream = gzipInputStream;
        } else {
            stream = inStream;
        }

        File temp = File.createTempFile("chain", "temp");
        FileOutputStream fileOutputStream = new FileOutputStream(temp);
        byte[] bytes = new byte[1024];
        int read = 0;

        while ((read = stream.read(bytes)) != -1) {
            fileOutputStream.write(bytes, 0, read);
        }
        fileOutputStream.close();
        return fromFile(temp, buildFrom, buildTo);
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
