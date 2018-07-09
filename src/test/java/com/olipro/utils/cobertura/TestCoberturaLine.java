package com.olipro.utils.cobertura;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import java.io.StringWriter;

import static org.testng.Assert.*;

public class TestCoberturaLine {
    private CoberturaLine clPriv;

    @BeforeMethod
    private void GetPrivateInstance() throws Exception {
        var constructor = CoberturaLine.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        clPriv = constructor.newInstance();
    }

    @Test
    public void testBranchSetAndGet() {
        assertFalse(clPriv.isBranch());
        clPriv.setBranch(true);
        assertTrue(clPriv.isBranch());
    }

    @Test
    public void testHitsSetAndGet() {
        final long hits = 234234;
        assertEquals(clPriv.getHits(), 0);
        clPriv.setHits(hits);
        assertEquals(clPriv.getHits(), hits);
    }

    @Test
    public void testCoveredConditionsSetAndGet() {
        final int coveredConditions = 232576457;
        assertEquals(clPriv.getCoveredConditions(), 0);
        clPriv.setCoveredConditions(coveredConditions);
        assertEquals(clPriv.getCoveredConditions(), coveredConditions);
    }

    @Test
    public void testTotalConditionsSetAndGet() {
        final int totalConditions = 623435634;
        assertEquals(clPriv.getTotalConditions(), 0);
        clPriv.setTotalConditions(totalConditions);
        assertEquals(clPriv.getTotalConditions(), totalConditions);
    }

    @Test
    public void testNumberSetAndGet() {
        final long number = 123123123;
        assertEquals(clPriv.getNumber(), 0);
        clPriv.setNumber(number);
        assertEquals(clPriv.getNumber(), number);
    }

    @Test
    public void testStringCoverageConversionIsCorrect() {
        clPriv.setTotalConditions(100);
        clPriv.setCoveredConditions(50);
        assertEquals(clPriv.getConditionCoverage(), "50% (50/100)");
    }

    @Test
    public void testFullCoverageIsCorrect() {
        clPriv.setTotalConditions(299999999);
        clPriv.setCoveredConditions(299999999);
        assertEquals(clPriv.getConditionCoverage(), "100% (299999999/299999999)");
    }

    @Test
    public void testOneMissingLineIsClampedTo99Percent() {
        clPriv.setTotalConditions(299999999);
        clPriv.setCoveredConditions(299999998);
        assertEquals(clPriv.getConditionCoverage(), "99% (299999998/299999999)");
    }

    @Test
    public void testNullStringIsReturnedIfTotalConditionsIsZero() {
        clPriv.setTotalConditions(0);
        clPriv.setCoveredConditions(20);
        assertNull(clPriv.getConditionCoverage());
    }

    @Test
    public void testConstructorCorrectlySetsMemberValues() {
        var cl = new CoberturaLine(true, 33, 44, 55 ,66);
        assertTrue(cl.isBranch());
        assertEquals(cl.getHits(), 33);
        assertEquals(cl.getCoveredConditions(), 44);
        assertEquals(cl.getTotalConditions(), 55);
        assertEquals(cl.getNumber(), 66);
    }

    @Test
    public void testClassCorrectlySerializesToXML() throws Exception {
        final var xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<line branch=\"false\" condition-coverage=\"75% (3/4)\" hits=\"2\" number=\"5\"/>";
        var x = new CoberturaLine(false, 2, 3, 4, 5);
        var m = JAXBContext.newInstance(CoberturaLine.class).createMarshaller();
        var sw = new StringWriter();
        m.marshal(x, sw);
        assertEquals(sw.toString(), xml);
    }
}