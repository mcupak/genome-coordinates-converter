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
package com.dnastack.beacon.converter.util;

/**
 * Genome Builds which have supporting data in the resource bundles
 *
 * @author patrickmagee
 */
public enum GenomeBuild {

    HG17("hg17", 17), HG18("hg18", 18), HG19("hg19", 19), HG38("hg38", 38);

    private final int build;
    private final String buildName;

    GenomeBuild(String buildName, int build) {
        this.buildName = buildName;
        this.build = build;
    }

    public int getBuild() {
        return build;
    }

    public String getBuildName() {
        return buildName;
    }
}
