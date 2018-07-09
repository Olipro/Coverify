package com.olipro.utils.cobertura;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.StringWriter;

import static org.testng.Assert.*;

public class TestCoberturaRoot {
    private CoberturaRoot crPriv;

    @BeforeMethod
    private void GetPrivateInstance() throws Exception {
        var constructor = CoberturaRoot.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        crPriv = constructor.newInstance();
    }

    @Test
    public void testBranchRateSetAndGet() {
        final double br = 2415.7276;
        assertEquals(crPriv.getBranchRate(), 0.0);
        crPriv.setBranchRate(br);
        assertEquals(crPriv.getBranchRate(), br);
    }

    @Test
    public void testBranchesCoveredSetAndGet() {
        final long bc = 1361346236;
        assertEquals(crPriv.getBranchesCovered(), 0);
        crPriv.setBranchesCovered(bc);
        assertEquals(crPriv.getBranchesCovered(), bc);
    }

    @Test
    public void testBranchesValidSetAndGet() {
        final long bv = 356123466;
        assertEquals(crPriv.getBranchesValid(), 0);
        crPriv.setBranchesValid(bv);
        assertEquals(crPriv.getBranchesValid(), bv);
    }

    @Test
    public void testComplexitySetAndGet() {
        final double c = 3256127.72346;
        assertEquals(crPriv.getComplexity(), 0.0);
        crPriv.setComplexity(c);
        assertEquals(crPriv.getComplexity(), c);
    }

    @Test
    public void testLinerateSetAndGet() {
        final double lr = 23526.23477;
        assertEquals(crPriv.getLineRate(), 0.0);
        crPriv.setLineRate(lr);
        assertEquals(crPriv.getLineRate(), lr);
    }

    @Test
    public void testLinescoveredSetAndGet() {
        final long lc = 351661667;
        assertEquals(crPriv.getLinesCovered(), 0);
        crPriv.setLinesCovered(lc);
        assertEquals(crPriv.getLinesCovered(), lc);
    }

    @Test
    public void testLinesvalidSetAndGet() {
        final long lv = 36123347;
        assertEquals(crPriv.getLinesValid(), 0);
        crPriv.setLinesValid(lv);
        assertEquals(crPriv.getLinesValid(), lv);
    }

    @Test
    public void testTimestampSetAndGet() {
        final long t = 361732457;
        assertEquals(crPriv.getTimestamp(), 0);
        crPriv.setTimestamp(t);
        assertEquals(crPriv.getTimestamp(), t);
    }

    @Test
    public void testSourcesIsVoidForDefaultConstruct() {
        assertNull(crPriv.getSources());
    }

    @Test
    public void testPackagesIsVoidForDefaultConstruct() {
        assertNull(crPriv.getPackages());
    }

    @Test
    public void testVersionMatchesPrivateClassFieldString() throws Exception {
        var cls = crPriv.getClass().getDeclaredField("version");
        cls.setAccessible(true);
        assertEquals(crPriv.getVersion(), (String)cls.get(crPriv));
    }

    @Test
    public void testPublicConstructorCorrectlySetsFields() {
        final double br = 264376.236;
        final long bc = 53216126;
        final long bv = 4616236;
        final double c = 626.1466;
        final double lr = 4563426.64646;
        final long lc = 52362346;
        final long lv = 63426236;
        final long ts = 6123466;
        var cr = new CoberturaRoot(br, bc, bv, c, lr, lc, lv, ts);
        assertEquals(cr.getBranchRate(), br);
        assertEquals(cr.getBranchesCovered(), bc);
        assertEquals(cr.getBranchesValid(), bv);
        assertEquals(cr.getComplexity(), c);
        assertEquals(cr.getLineRate(), lr);
        assertEquals(cr.getLinesCovered(), lc);
        assertEquals(cr.getLinesValid(), lv);
        assertEquals(cr.getTimestamp(), ts);
        assertNotNull(cr.getSources());
        assertEquals(cr.getSources().size(), 0);
        assertNotNull(cr.getPackages());
        assertEquals(cr.getPackages().size(), 0);
    }

    @Test
    public void testMarshallerIsCorrectlySetupByStaticMethod() throws Exception {
        final var xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<!DOCTYPE coverage SYSTEM 'http://cobertura.sourceforge.net/xml/coverage-04.dtd'>\n" +
                "<coverage branch-rate=\"0.0\" branches-covered=\"0\" branches-valid=\"0\" complexity=\"0.0\" " +
                "line-rate=\"0.0\" lines-covered=\"0\" lines-valid=\"0\" timestamp=\"0\" " +
                "version=\"LLVMToCobertura v0.1.0\"/>";
        var m = crPriv.getJAXBMarshaller();
        var dt = crPriv.getClass().getDeclaredField("DTD");
        dt.setAccessible(true);
        assertEquals(dt.get(crPriv), (String)m.getProperty("com.sun.xml.bind.xmlHeaders"));
        var sb = new StringWriter();
        m.marshal(crPriv, sb);
        assertEquals(sb.toString(), xml);
    }
}