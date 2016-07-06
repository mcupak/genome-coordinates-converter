package com.dnastack.beacon.converter.hgvs;

import com.dnastack.beacon.converter.hgvs.exceptions.HGVSException;
import com.dnastack.beacon.converter.hgvs.models.GenomeInterval;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by patrickmagee on 2016-07-06.
 */
public class HGVSConverterTest {


    @Test
    public void testGetCoordinates() throws HGVSException {
        String hgvs = "NM_182763.2:c.688+403C>T";
        GenomeInterval interval = HGVSConverter.hgvsToGenomic(hgvs);

        assertNotNull(interval.getStart());
        assertNotNull(interval.getVariant());
        assertNotNull(interval.getRef());
        assertNotNull(interval.getEnd());
        assertNull(interval.getError());
    }

    @Test
    public void testGetMultipleCoordinates() throws HGVSException {
        List<String> hgvss = Arrays.asList("NM_182763.2:c.688+403C>T", "NM_182763.2:c.688+403C>T");
        List<GenomeInterval> intervals = HGVSConverter.hgvsToGenomic(hgvss);

        assertTrue(intervals.size() > 0);

        for (GenomeInterval interval : intervals) {
            assertNotNull(interval.getStart());
            assertNotNull(interval.getVariant());
            assertNotNull(interval.getRef());
            assertNotNull(interval.getEnd());
            assertNull(interval.getError());
        }
    }

    @Test(expected = HGVSException.class)
    public void testInvalidHGVSThrowsError() throws HGVSException {
        HGVSConverter.hgvsToGenomic("ASDASDASDASD");
    }

    @Test(expected = HGVSException.class)
    public void testInvalidHGVSInListThrowsError() throws HGVSException {
        List<String> hgvss = Arrays.asList("NM_182763.2:c.688+403C>T", "NM_182763.2:c.688+403C>T", "INVALID");
        List<GenomeInterval> intervals = HGVSConverter.hgvsToGenomic(hgvss);
    }

    @Test(expected = NullPointerException.class)
    public void testNullHGVSThrowsError() throws HGVSException {
        List<String> hgvs = null;
        HGVSConverter.hgvsToGenomic(hgvs);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyListThrowsError() throws HGVSException {
        List<String> hgvs = new ArrayList<>();
        HGVSConverter.hgvsToGenomic(hgvs);
    }


}