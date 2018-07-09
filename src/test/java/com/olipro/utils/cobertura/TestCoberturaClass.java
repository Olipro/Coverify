package com.olipro.utils.cobertura;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;

import java.io.StringWriter;

import static org.testng.Assert.*;

public class TestCoberturaClass {
    CoberturaClass ccPriv;

    @BeforeClass
    private void GetPrivateInstance() throws Exception {
        var constructor = CoberturaClass.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ccPriv = constructor.newInstance();
    }

    @Test
    public void testBranchRateSetAndGet() {
        final double val = 23.234235;
        assertEquals(ccPriv.getBranchRate(), 0.0);
        ccPriv.setBranchRate(val);
        assertEquals(ccPriv.getBranchRate(), val);
    }

    @Test
    public void testComplexitySetAndGet() {
        final double val = 234.6246346;
        assertEquals(ccPriv.getComplexity(), 0.0);
        ccPriv.setComplexity(val);
        assertEquals(ccPriv.getComplexity(), val);
    }

    @Test
    public void testFilenameSetAndGet() {
        final var f = "foishja9pouwnf";
        assertEquals(ccPriv.getFilename(), "");
        ccPriv.setFilename(f);
        assertEquals(ccPriv.getFilename(), f);
    }

    @Test
    public void testLinerateSetAndGet() {
        final double val = 34.2345346;
        assertEquals(ccPriv.getLineRate(), 0.0);
        ccPriv.setLineRate(val);
        assertEquals(ccPriv.getLineRate(), val);
    }

    @Test
    public void testNameSetAndGet() {
        final var f = "osjad;hf90q8w4f";
        assertEquals(ccPriv.getName(), "");
        ccPriv.setName(f);
        assertEquals(ccPriv.getName(), f);
    }

    @Test
    public void testLinesIsNullWhenDefaultConstructed() throws Exception {
        var m = ccPriv.getClass().getDeclaredMethod("getCoberturaLines");
        m.setAccessible(true);
        assertNull(m.invoke(ccPriv));
        assertThrows(() -> ccPriv.getLines());
    }

    @Test
    public void testMethodsIsNullWhenDefaultConstructed() {
        assertNull(ccPriv.getMethods());
    }

    @Test
    public void testPublicConstructorSetsFieldsCorrectly() {
        final double branchRate = 3243.234562345;
        final double complexity = 232.5235346;
        final String filename = "a8hf90pa8wnuioe4f";
        final double lineRate = 343.34235235;
        final String name = "q90824nvpqoggqw";
        var cls = new CoberturaClass(branchRate, complexity, filename, lineRate, name);
        assertEquals(cls.getBranchRate(), branchRate);
        assertEquals(cls.getComplexity(), complexity);
        assertEquals(cls.getFilename(), filename);
        assertEquals(cls.getLineRate(), lineRate);
        assertEquals(cls.getName(), name);
        assertEquals(cls.getMethods().size(), 0);
        assertEquals(cls.getLines().size(), 0);
    }

    @Test
    public void testClassCorrectlySerializesToXML() throws Exception {
        final var xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<class branch-rate=\"1.1\" complexity=\"2.2\" filename=\"asdf\" " +
                "line-rate=\"3.3\" name=\"fdsa\"><lines/><methods/></class>";
        var cc = new CoberturaClass(1.1, 2.2, "asdf", 3.3, "fdsa");
        var m = JAXBContext.newInstance(CoberturaClass.class).createMarshaller();
        var sw = new StringWriter();
        m.marshal(cc, sw);
        assertEquals(xml, sw.toString());
    }
}