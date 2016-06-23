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
package com.dnastack.beacon.liftover.api;

import com.dnastack.beacon.liftover.util.LiftOverException;
import htsjdk.samtools.util.Interval;

/**
 * Liftover interface
 *
 * @author patrickmagee
 */
public interface LiftOver {

    /**
     * LiftOver a single Coordinate
     *
     * @param contig chromosome or contig reference. ie "chr1"
     * @param start  start position.
     * @param end    end position
     * @return new interval of lifted over interval
     * @throws LiftOverException
     */
    Interval liftOver(String contig, int start, int end) throws LiftOverException;

    /**
     * LiftOver a single Coordinate
     *
     * @param contig   chromosome or contig reference. ie "chr1"
     * @param start    start position.
     * @param end      end position
     * @param minMatch % mismatch between positions
     * @return new interval
     * @throws LiftOverException
     */
    Interval liftOver(String contig, int start, int end, double minMatch) throws LiftOverException;

    /**
     * LiftOver a single Coordinate
     *
     * @param interval interval to start with
     * @return new interval
     * @throws LiftOverException
     */
    Interval liftOver(Interval interval) throws LiftOverException;

    /**
     * LiftOver a single Coordinate
     *
     * @param interval interval to start with
     * @param minMatch minimum mismatch percentage
     * @return new interval
     * @throws LiftOverException
     */
    Interval liftOver(Interval interval, double minMatch) throws LiftOverException;

}
