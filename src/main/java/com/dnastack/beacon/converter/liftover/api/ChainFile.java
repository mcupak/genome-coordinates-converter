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
package com.dnastack.beacon.converter.liftover.api;

import com.dnastack.beacon.converter.util.GenomeBuild;

import java.io.File;

/**
 * Abstract Chain File Class.
 * The chain file contains all of the coordinate conversion from one genome-build to another
 *
 * @author patrickmagee
 */
public abstract class ChainFile extends File {

    private final String buildFrom;

    private final String buildTo;

    public ChainFile(String pathname, GenomeBuild buildFrom, GenomeBuild buildTo) {
        super(pathname);
        this.buildFrom = buildFrom.getBuildName();
        this.buildTo = buildTo.getBuildName();
    }

    public ChainFile(String pathname, String buildFrom, String buildTo) {
        super(pathname);
        this.buildFrom = buildFrom;
        this.buildTo = buildTo;
    }

    /**
     * Get the current genome build version
     *
     * @return
     */
    public String getBuildFrom() {
        return buildFrom;
    }

    /**
     * Get the target genome build version
     *
     * @return GenomeBuild Version
     */
    public String getBuildTo() {
        return buildTo;
    }

}
