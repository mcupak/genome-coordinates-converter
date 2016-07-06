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

import com.dnastack.beacon.converter.hgvs.models.GenomeInterval;
import com.dnastack.beacon.converter.util.TaskOutput;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Task output class for HGVS to Genomic coordinates task
 *
 * @author patmagee
 */
public class HGVSToGenomicTaskOutput extends TaskOutput<ByteArrayOutputStream> {

    public HGVSToGenomicTaskOutput(ByteArrayOutputStream output) {
        super(output);
    }

    /**
     * Convienient method for parsing the oputput from the converter and returning GenomeInterval objects
     */
    public List<GenomeInterval> parseOutput() throws IOException {

        List<GenomeInterval> intervals = new ArrayList<>();
        JSONParser parser = new JSONParser();
        try {
            JSONArray array = (JSONArray) parser.parse(toString());
            for (Object obj : array) {
                JSONObject jsonObject = (JSONObject) obj;
                intervals.add(toObject(jsonObject, GenomeInterval.class));
            }
            return intervals;

        } catch (ParseException e) {
            IOException ex = new IOException(e.getMessage());
            ex.setStackTrace(e.getStackTrace());
            throw ex;
        }
    }

    @Override
    public String toString() {
        return getOutput().toString();
    }
}
