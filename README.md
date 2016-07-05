# Genome Coordinates Converter [![Build Status](https://travis-ci.org/mcupak/genome-coordinates-converter.svg?branch=develop)](https://travis-ci.org/mcupak/genome-coordinates-converter) [![GitHub license](https://img.shields.io/badge/license-Apache%202-blue.svg)](https://raw.githubusercontent.com/mcupak/genome-coordinates-converter/develop/LICENSE)

Java wrapper for performing liftOvers on genomic coordinates. The wrapper provides easy to use methods for converting genomic coordinates from one build version to another. Additionally, it is pre-configured to offer support for the most common conversions.


## Requirements

- Java 8


## Chain Files

LiftOvers can be performed easily by either using the predefined liftovers, or by supplying a custom chain file. Chain files are written in the [Chain Format](https://genome.ucsc.edu/goldenpath/help/chain.html) from UCSC. A large number of chainfiles are publicly available to download [here](http://hgdownload.cse.ucsc.edu/downloads.html),


## Basic Usage

```java
    try {
        LiftOver intervalLiftOver = UCSCLiftOver.hg19ToHg38();
        Interval newInterval = intervalLiftOver.liftOver("chr1",743267,743268);
    } catch(LiftOverException e){
        e.printStackTrace();
    } catch(IOException e){
        e.printStackTrace();
        
    }
```

## Using a Custom Chain File

```java
   try {
       String path = "/path/to/customChainFile";
       String from = "mm9";
       String to = "mm10";
    
       ChainFile chainFile = new UCSCChainFile(path, from, to);
       LiftOver intervalLiftOver = new UCSCLiftOver(chainFile);
       Interval newInterval = intervalLiftOver.liftOver("chr1",7724,8662);
   } catch(LiftOverException e){
        e.printStackTrace();
   } catch(IOException e){
        e.printStackTrace();
   }
   
```

## Built in LiftOvers

Several liftovers have been included

| From |  To  |
|------|------|
| hg17 | hg18 |
| hg17 | hg19 |
| hg18 | hg17 |
| hg18 | hg19 |
| hg18 | hg38 |
| hg19 | hg17 |
| hg19 | hg18 |
| hg19 | hg38 |
| hg38 | hg19 |
