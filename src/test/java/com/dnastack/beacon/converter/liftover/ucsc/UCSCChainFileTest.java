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