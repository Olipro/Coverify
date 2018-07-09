package com.olipro.utils.cobertura;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import java.io.StringWriter;

import static org.testng.Assert.*;

public class TestCoberturaMethod {
    private CoberturaMethod cmPriv;

    @BeforeMethod
    private void GetPrivateInstance() throws Exception {
        var constructor = CoberturaMethod.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        cmPriv = constructor.newInstance();
    }

    @Test
    public void testNameSetAndGet() {
        final var s = "jt8042nq0p8gio";
        assertNull(cmPriv.getName());
        cmPriv.setName(s);
        assertEquals(cmPriv.getName(), s);
    }

    @Test
    public void testSignatureSetAndGet() {
        final var s = "0283nfp2iow4jqf";
        assertNull(cmPriv.getSignature());
        cmPriv.setSignature(s);
        assertEquals(cmPriv.getSignature(), s);
    }

    @Test
    public void testLinerateSetAndGet() {
        final double d = 23423.234234;
        assertEquals(cmPriv.getLineRate(), 0.0);
        cmPriv.setLineRate(d);
        assertEquals(cmPriv.getLineRate(), d);
    }

    @Test
    public void testBranchrateSetAndGet() {
        final double d = 423423.63463;
        assertEquals(cmPriv.getBranchRate(), 0.0);
        cmPriv.setBranchRate(d);
        assertEquals(cmPriv.getBranchRate(), d);
    }

    @Test
    public void testCoberturaLinesIsNullOnDefaultConstruct() {
        assertNull(cmPriv.getLines());
    }

    @Test
    public void testPublicConstructorSetsFieldsCorrectly() {
        final var name = "0284gpqi";
        final var sig = "80342gnpio;aqw";
        final double lineRate = 23542.32423;
        final double branchRate = 2352346.463432;
        var cm = new CoberturaMethod(name, sig, lineRate, branchRate);
        assertEquals(cm.getName(), name);
        assertEquals(cm.getSignature(), sig);
        assertEquals(cm.getLineRate(), lineRate);
        assertEquals(cm.getBranchRate(), branchRate);
        assertNotNull(cm.getLines());
    }

    @Test
    public void testClassCorrectlySerializesToXML() throws Exception {
        final var xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
                "<method branch-rate=\"6.6\" line-rate=\"4.5\" name=\"test\" signature=\"func()\"><lines>" +
                "<line branch=\"true\" condition-coverage=\"50% (4/8)\" hits=\"2\" number=\"5\"/><conditions>" +
                "<condition number=\"2\" coverage=\"4%\" type=\"jump\"/></conditions></lines></method>";
        var x = new CoberturaMethod("test", "func()", 4.5, 6.6);
        x.getLines().getLineList().add(new CoberturaLine(true, 2, 4, 8, 5));
        var cc = new CoberturaConditions();
        cc.getConditionList().add(new CoberturaCondition(4, 2));
        x.getLines().getLineList().add(cc);
        var m = JAXBContext.newInstance(CoberturaMethod.class).createMarshaller();
        var sw = new StringWriter();
        m.marshal(x, sw);
        assertEquals(sw.toString(), xml);
    }
}