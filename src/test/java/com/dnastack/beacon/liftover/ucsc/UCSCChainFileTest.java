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


import com.dnastack.beacon.liftover.api.ChainFile;
import com.dnastack.beacon.liftover.util.GenomeBuild;
import com.dnastack.beacon.liftover.ucsc.UCSChainFile;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author patrickmagee
 */
@RunWith(JUnit4.class)
public class UCSCChainFileTest {

    private static final String FILE_NAME = "hg38ToHg19.over.chain";
    private static final String FILE_PATH = "src/main/resources/chains/hg38/" + FILE_NAME;
    private static final String BUIILD_FROM = "hg38";
    private static final String BUILD_TO = "hg19";

    @Test(expected = IllegalArgumentException.class)
    public void testLeftNullGenomeBuild() {
        try {
            new UCSChainFile(null, GenomeBuild.HG17);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRightNullGenomeBuild() {
        System.out.println("@#@#@");
        try {
            new UCSChainFile(GenomeBuild.HG17, null);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Test(expected = NullPointerException.class)
    public void testNullPathName() throws Exception {
        String nullName = null;
        new UCSChainFile(nullName, GenomeBuild.HG17, GenomeBuild.HG18);
    }

    @Test(expected = IOException.class)
    public void testInvalidGenomeBuildCombination() throws Exception {
        new UCSChainFile(GenomeBuild.HG38, GenomeBuild.HG17);
    }


    @Test
    public void testFromFile() throws IOException {
        File file = new File(FILE_PATH);
        ChainFile chainFile = UCSChainFile.fromFile(file, BUIILD_FROM, BUILD_TO);
        assertEquals(chainFile.getName(), FILE_NAME);
        assertEquals(chainFile.isFile(), true);
        assertEquals(chainFile.getBuildFrom(), BUIILD_FROM);
        assertEquals(chainFile.getBuildTo(), BUILD_TO);

    }

    @Test
    public void testFromUCSCRemote() throws IOException {
        ChainFile chainFile = UCSChainFile.fromUCSCRemote(BUIILD_FROM, BUILD_TO, FILE_NAME + ".gz");
        assertEquals(chainFile.getBuildTo(), BUILD_TO);
        assertEquals(chainFile.getBuildFrom(), BUIILD_FROM);

    }

    @Test(expected = FileNotFoundException.class)
    public void testFromUCSCRemoteError() throws IOException {
        ChainFile chainFile = UCSChainFile.fromUCSCRemote("ERROR", BUIILD_FROM, FILE_NAME + ".gz");
    }

}