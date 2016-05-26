package com.dnastack.beacon.LiftOvers;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * Created by patrickmagee on 2016-05-26.
 */
@RunWith(JUnit4.class)
public class ChainFileTest {

    @Test( expected = IllegalArgumentException.class)
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