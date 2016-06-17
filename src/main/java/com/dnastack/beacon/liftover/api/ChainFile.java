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
package com.dnastack.beacon.liftover.api;

import com.dnastack.beacon.liftover.util.GenomeBuild;

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

    ;

    /**
     * Get the target genome build version
     *
     * @return GenomeBuild Version
     */
    public String getBuildTo() {
        return buildTo;
    }

    ;

}
