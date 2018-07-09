package com.olipro.utils.cobertura;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import java.io.StringWriter;

import static org.testng.Assert.*;

public class TestCoberturaPackage {
    private CoberturaPackage cpPriv;

    @BeforeMethod
    private void GetPrivateInstance() throws Exception {
        var constructor = CoberturaPackage.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        cpPriv = constructor.newInstance();
    }

    @Test
    public void testBranchrateSetAndGet() {
        final double b = 23452.3264372;
        assertEquals(cpPriv.getBranchRate(), 0.0);
        cpPriv.setBranchRate(b);
        assertEquals(cpPriv.getBranchRate(), b);
    }

    @Test
    public void testComplexitySetAndGet() {
        final double c = 325347.143623;
        assertEquals(cpPriv.getComplexity(), 0.0);
        cpPriv.setComplexity(c);
        assertEquals(cpPriv.getComplexity(), c);
    }

    @Test
    public void testLinerateSetAndGet() {
        final double l = 2572.3462727;
        assertEquals(cpPriv.getLineRate(), 0.0);
        cpPriv.setLineRate(l);
        assertEquals(cpPriv.getLineRate(), l);
    }

    @Test
    public void testNameSetAndGet() {
        final var s = "234n8itopq;wgkael";
        assertEquals(cpPriv.getName(), "");
        cpPriv.setName(s);
        assertEquals(cpPriv.getName(), s);
    }

    @Test
    public void testClassesAreNullWhenDefaultConstructed() {
        assertNull(cpPriv.getClasses());
    }

    @Test
    public void testPublicConstructorSetsFieldsCorrectly() {
        var cp = new CoberturaPackage(1.1, 2.2, 3.3, "asdf");
        assertEquals(cp.getBranchRate(), 1.1);
        assertEquals(cp.getComplexity(), 2.2);
        assertEquals(cp.getLineRate(), 3.3);
        assertEquals(cp.getName(), "asdf");
        assertNotNull(cp.getClasses());
        assertEquals(cp.getClasses().size(), 0);
    }

    @Test
    public void testClassCorrectlySerializesToXML() throws Exception {
        final var xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<package branch-rate=\"1.1\" complexity=\"2.2\" line-rate=\"3.3\" name=\"testy\"><classes>" +
                "<class branch-rate=\"1.1\" complexity=\"2.2\" filename=\"test\" line-rate=\"3.3\" name=\"f\">" +
                "<lines/><methods/></class></classes></package>";
        var x = new CoberturaPackage(1.1, 2.2, 3.3, "testy");
        x.getClasses().add(new CoberturaClass(1.1, 2.2, "test", 3.3, "f"));
        var m = JAXBContext.newInstance(CoberturaPackage.class).createMarshaller();
        var sw = new StringWriter();
        m.marshal(x, sw);
        assertEquals(sw.toString(), xml);
    }
}