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
package com.dnastack.beacon.converter.hgvs;

import com.dnastack.beacon.converter.hgvs.exceptions.HGVSException;
import com.dnastack.beacon.converter.hgvs.models.GenomeInterval;
import com.dnastack.beacon.converter.hgvs.task.HGVSToGenomicTaskOutput;
import com.dnastack.beacon.converter.hgvs.task.HgvsToGenomicTask;
import com.dnastack.beacon.converter.util.TaskRunner;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Wrapper for converting an HGVS to genomic coordinates
 *
 * @author patmagee
 */
public class HGVSConverter {

    /**
     * Convert a list of HGVS Identifiers to a genomic coordinate. This method wraps the python hgvs package
     * and returns a list of converted Intervals, or throws an exception
     *
     * @param identifiers list of HGVS ids
     * @return List of genome intervals
     * @throws HGVSException
     */
    public static List<GenomeInterval> hgvsToGenomic(List<String> identifiers) throws HGVSException {
        if (identifiers == null) {
            throw new NullPointerException("Identifiers cannot be null");
        } else if (identifiers.size() == 0) {
            throw new IllegalArgumentException("Must provide at least one identifier");
        }

        HgvsToGenomicTask task = new HgvsToGenomicTask(identifiers);
        TaskRunner runner = new TaskRunner();

        try {
            HGVSToGenomicTaskOutput output = (HGVSToGenomicTaskOutput) runner.exec(task);
            List<GenomeInterval> intervals = output.parseOutput();

            //Check to see if there was an error
            for (GenomeInterval interval : intervals) {
                if (interval.getError() != null) {
                    throw new HGVSException(interval.getError());
                }
            }
            return intervals;
        } catch (IOException e) {
            throw new HGVSException("There was an error while executing the conversion");
        }

    }

    /**
     * Convert a single HGVS Identifier to genomic coordinates
     *
     * @param identifier a Single HGVS Identifier
     * @return single GenomeInterval
     * @throws HGVSException
     */
    public static GenomeInterval hgvsToGenomic(String identifier) throws HGVSException {
        return hgvsToGenomic(Arrays.asList(identifier)).get(0);
    }

}
