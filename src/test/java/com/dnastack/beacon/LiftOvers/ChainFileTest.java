package com.dnastack.beacon.LiftOvers;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import static org.junit.Assert.assertEquals;

/**
 * Created by patrickmagee on 2016-05-26.
 */
@RunWith(JUnit4.class)
public class ChainFileTest {

    private static final String FILE_NAME = "hg38ToHg19.over.chain";
    private static final String FILE_PATH = "src/main/resources/chains/hg38/" + FILE_NAME;
    private static final String URL_FROM_REMOTE = "http://hgdownload.cse.ucsc.edu/goldenPath/hg38/liftOver/hg38ToHg19.over.chain.gz";
    private static final String INVALID_URL = "http://hgdownload.cse.ucsc.edu/goldenPath/hg19/liftOver/hg38ToHg19.over.chain.gz";
    private static final String BUIILD_FROM = "hg38";
    private static final String BUILD_TO = "hg19";

    @Test(expected = IllegalArgumentException.class)
    public void testLeftNullGenomeBuild() {
        try {
            new ChainFile(null, GenomeBuild.HG17);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRightNullGenomeBuild() {
        try {
            new ChainFile(GenomeBuild.HG17, null);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test(expected = NullPointerException.class)
    public void testNullPathName() throws Exception {
        String nullName = null;
        new ChainFile(nullName, GenomeBuild.HG17, GenomeBuild.HG18);
    }

    @Test(expected = IOException.class)
    public void testInvalidGenomeBuildCombination() throws Exception {
        new ChainFile(GenomeBuild.HG38, GenomeBuild.HG17);
    }


    @Test
    public void testFromFile() throws IOException {

        File file = new File(FILE_PATH);
        ChainFile chainFile = ChainFile.fromFile(file, BUIILD_FROM, BUILD_TO);
        assertEquals(chainFile.getName(), FILE_NAME);
        assertEquals(chainFile.isFile(), true);
        assertEquals(chainFile.getBuildFrom(), BUIILD_FROM);
        assertEquals(chainFile.getBuildTo(), BUILD_TO);

    }

    @Test
    public void testFromURL() throws IOException {
        final URL url = new URL(URL_FROM_REMOTE);
        ChainFile chainFile = ChainFile.fromUrl(url, BUIILD_FROM, BUILD_TO);
        assertEquals(chainFile.isFile(), true);

        InputStream stream = new FileInputStream(chainFile);
        InputStream compareStream = new FileInputStream(new File(FILE_PATH));

        ReadableByteChannel remoteChannel = Channels.newChannel(stream);
        ReadableByteChannel compareChannel = Channels.newChannel(compareStream);

        ByteBuffer remoteBuffer = ByteBuffer.allocateDirect(1024);
        ByteBuffer compareBuffer = ByteBuffer.allocateDirect(1024);

        try {
            while (true) {
                int readRemote = remoteChannel.read(remoteBuffer);
                int readCompare = compareChannel.read(compareBuffer);

                if (readRemote == -1 || readCompare == -1) {
                    assertEquals(readRemote, readCompare);
                    return;
                }

                remoteBuffer.flip();
                compareBuffer.flip();

                for (int i = 0; i < Math.min(readRemote, readCompare); i++) {
                    assertEquals(remoteBuffer.get(), compareBuffer.get());
                }
                remoteBuffer.compact();
                compareBuffer.compact();

            }
        } finally {
            if (stream != null) {
                stream.close();
            }
            if (compareStream != null) {
                compareStream.close();
            }
        }
    }

    @Test(expected = FileNotFoundException.class)
    public void testFromURLERROR() throws IOException {
        final URL url = new URL(INVALID_URL);
        ChainFile.fromUrl(url, BUIILD_FROM, BUILD_TO);

    }

    @Test
    public void testFromUCSCRemote() throws IOException {
        ChainFile chainFile = ChainFile.fromUCSCRemote(BUIILD_FROM, BUILD_TO, FILE_NAME + ".gz");
        assertEquals(chainFile.getBuildTo(), BUILD_TO);
        assertEquals(chainFile.getBuildFrom(), BUIILD_FROM);

    }

    @Test(expected = FileNotFoundException.class)
    public void testFromUCSCRemoteError() throws IOException {
        ChainFile chainFile = ChainFile.fromUCSCRemote("ERROR", BUIILD_FROM, FILE_NAME + ".gz");
    }


    @Test
    public void testHg17ToHg18() throws Exception {
        final String fileName = "hg17ToHg18.over.chain";
        ChainFile file = ChainFile.hg17ToHg18();
        assertEquals(file.exists(), true);
        assertEquals(file.getName(), fileName);
    }

    @Test
    public void testHg17ToHg19() throws Exception {
        final String fileName = "hg17ToHg19.over.chain";
        ChainFile file = ChainFile.hg17ToHg19();
        assertEquals(file.exists(), true);
        assertEquals(file.getName(), fileName);

    }

    @Test
    public void testHg18ToHg17() throws Exception {
        final String fileName = "hg18ToHg17.over.chain";
        ChainFile file = ChainFile.hg18ToHg17();
        assertEquals(file.exists(), true);
        assertEquals(file.getName(), fileName);

    }

    @Test
    public void testHg18ToHg19() throws Exception {
        final String fileName = "hg18ToHg19.over.chain";
        ChainFile file = ChainFile.hg18ToHg19();
        assertEquals(file.exists(), true);
        assertEquals(file.getName(), fileName);

    }

    @Test
    public void testHg18ToHg38() throws Exception {
        final String fileName = "hg18ToHg38.over.chain";
        ChainFile file = ChainFile.hg18ToHg38();
        assertEquals(file.exists(), true);
        assertEquals(file.getName(), fileName);

    }

    @Test
    public void testHg19ToHg18() throws Exception {
        final String fileName = "hg19ToHg18.over.chain";
        ChainFile file = ChainFile.hg19ToHg18();
        assertEquals(file.exists(), true);
        assertEquals(file.getName(), fileName);

    }

    @Test
    public void testHg19ToHg38() throws Exception {
        final String fileName = "hg19ToHg38.over.chain";
        ChainFile file = ChainFile.hg19ToHg38();
        assertEquals(file.exists(), true);
        assertEquals(file.getName(), fileName);

    }

    @Test
    public void testHg19ToHg17() throws Exception {
        final String fileName = "hg19ToHg17.over.chain";
        ChainFile file = ChainFile.hg19ToHg17();
        assertEquals(file.exists(), true);
        assertEquals(file.getName(), fileName);

    }

    @Test
    public void testHg38ToHg19() throws Exception {
        final String fileName = "hg38ToHg19.over.chain";
        ChainFile file = ChainFile.hg38ToHg19();
        assertEquals(file.exists(), true);
        assertEquals(file.getName(), fileName);

    }

}