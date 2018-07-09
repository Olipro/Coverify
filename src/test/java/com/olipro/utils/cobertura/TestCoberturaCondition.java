package com.olipro.utils.cobertura;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.StringWriter;

import static org.testng.Assert.*;

public class TestCoberturaCondition {
    private CoberturaCondition ccPriv;

    @BeforeClass
    private void GetPrivateInstance() throws Exception {
        var constructor = CoberturaCondition.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        ccPriv = constructor.newInstance();
    }

    @Test
    public void testCoverageSetAndGet() {
        final int coverage = 89;
        assertEquals(ccPriv.getCoverage(), 0);
        ccPriv.setCoverage(coverage);
        assertEquals(ccPriv.getCoverage(), coverage);
    }

    @Test
    public void testCoverageThrowsIfSetOver100OrBelow0() {
        ccPriv.setCoverage(0);
        assertThrows(() -> ccPriv.setCoverage(-1));
        assertEquals(ccPriv.getCoverage(), 0);
        ccPriv.setCoverage(100);
        assertThrows(() -> ccPriv.setCoverage(101));
        assertEquals(ccPriv.getCoverage(), 100);
    }

    @Test
    public void testNumberSetAndGet() {
        final long num = 342353245;
        assertEquals(ccPriv.getNumber(), 0);
        ccPriv.setNumber(num);
        assertEquals(ccPriv.getNumber(), num);
    }

    @Test
    public void testTypeSetAndGet() {
        final var s = "sldjnglokajsnf";
        assertNull(ccPriv.getType());
        ccPriv.setType(s);
        assertEquals(ccPriv.getType(), s);
    }

    @Test
    public void testStringOfCoverageIsCorrect() {
        final int coverage = 35;
        ccPriv.setCoverage(coverage);
        assertEquals(ccPriv.getStrCoverage(), 35 + "%");
    }

    @Test
    public void testDefaultConstructorInitsFieldsCorrectly() {
        final int coverage = 20;
        final long number = 3243244;
        final String s = "0324u3028htnof";
        var cc = new CoberturaCondition(coverage, number, s);
        assertEquals(cc.getCoverage(), coverage);
        assertEquals(cc.getNumber(), number);
        assertEquals(cc.getType(), s);
        assertEquals(cc.getStrCoverage(), coverage + "%");
    }

    @Test
    public void testConstructorWithoutTypeUsesJumpAsString() {
        var cc = new CoberturaCondition(0, 0);
        assertEquals(cc.getType(), "jump");
    }

    @XmlRootElement(name = "wrapper")
    private static class XMLWrapper {

        @XmlElement(name = "coberturacondition")
        private CoberturaCondition c;

        private XMLWrapper() {}

        public XMLWrapper(CoberturaCondition c) {
            this.c = c;
        }
    }

    @Test
    public void testClassCorrectlySerializesToXML() throws Exception {
        final var xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><wrapper>" + "" +
                "<coberturacondition number=\"6\" coverage=\"5%\" type=\"asdf\"/></wrapper>";
        var x = new XMLWrapper(new CoberturaCondition(5, 6, "asdf"));
        var m = JAXBContext.newInstance(XMLWrapper.class).createMarshaller();
        var sw = new StringWriter();
        m.marshal(x, sw);
        assertEquals(sw.toString(), xml);
    }
}