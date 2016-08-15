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

import com.dnastack.beacon.converter.liftover.api.ChainFile;
import com.dnastack.beacon.converter.util.GenomeBuild;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;

/**
 * Class for wrapping the chain files used in the liftOver process. Extends java.io.file
 * to easily retrieve chainFiles with minimal user input
 *
 * @author patrickmagee
 */
public class UCSChainFile extends ChainFile {

    private static final String FILE_PATH_TEMPLATE = "chains/%s/hg%dToHg%d.over.chain";
    private static final String UCSC_REMOTE_TEMPLATE = "http://hgdownload.cse.ucsc.edu/goldenPath/%s/liftOver/%s";

    /**
     * Constructor for The Chainfile.
     *
     * @param from GenomeBuild that the interval is in
     * @param to   GenomeBuild that is the target
     * @throws IOException
     */
    public UCSChainFile(GenomeBuild from, GenomeBuild to) throws IOException {
        super(getChainFilePath(from, to), from, to);

        if (!exists()) {
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
    public UCSChainFile(String pathname, GenomeBuild from, GenomeBuild to) throws IOException {
        super(pathname, from, to);
        if (from == null || to == null) {
            throw new IllegalArgumentException("GenomeBuilds cannot be null");
        }
        if (!exists()) {
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
    public UCSChainFile(String pathname, String buildFrom, String buildTo) throws IOException {
        super(pathname, buildFrom, buildTo);
        if (buildFrom == null || buildTo == null) {
            throw new IllegalArgumentException("Build versions cannot null");
        }
        if (!exists()) {
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
    private static ChainFile fromUrl(URL url, String buildFrom, String buildTo) throws IOException {
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
        return new UCSChainFile(pathname, buildFrom, buildTo);
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

}
