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
package com.dnastack.beacon.converter.hgvs.task;

import com.dnastack.beacon.converter.util.Task;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

/**
 * Created by patrickmagee on 2016-07-06.
 */
public class HgvsToGenomicTask implements Task<HGVSToGenomicTaskOutput, ByteArrayOutputStream> {

    private static final String PYTHON_SCRIPT = "python/hgvsUtils/hgvsToGenomic.py";

    private List<String> identifiers;


    public HgvsToGenomicTask(List<String> identifiers) {
        if (identifiers == null || identifiers.size() < 1) {
            throw new IllegalArgumentException("Identifiers cannot be null or empty");
        }
        this.identifiers = identifiers;
    }

    @Override
    public String getCommand() {
        String path = new File(PYTHON_SCRIPT).getAbsolutePath();
        String cmd = "python -W ignore " + path;
        for (String identifier : identifiers) {
            cmd += " \"" + identifier + "\"";
        }
        return cmd;
    }

    @Override
    public HGVSToGenomicTaskOutput generateOutput(ByteArrayOutputStream output) {
        return new HGVSToGenomicTaskOutput(output);
    }

    @Override
    public ByteArrayOutputStream getOutputStream() {
        return new ByteArrayOutputStream();
    }
}
