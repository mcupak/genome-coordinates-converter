package com.dnastack.beacon.LiftOvers;

/**
 * Genome Builds which have supporting data in the resource bundles
 * Created by patrickmagee on 2016-05-26.
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
