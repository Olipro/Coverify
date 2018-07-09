package com.olipro.utils.cobertura;

import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import java.io.StringWriter;

import static org.testng.Assert.*;

public class TestCoberturaLines {
    @Test
    public void testListIsValidAfterDefaultConstruction() {
        var cl = new CoberturaLines();
        assertNotNull(cl.getLineList());
        assertEquals(cl.getLineList().size(), 0);
    }

    @Test
    public void testClassCorrectlySerializesToXML() throws Exception {
        final var xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><lines>" +
                "<line branch=\"true\" condition-coverage=\"50% (4/8)\" hits=\"2\" number=\"5\"/>" +
                "<conditions><condition number=\"2\" coverage=\"4%\" type=\"jump\"/></conditions></lines>";
        var x = new CoberturaLines();
        x.getLineList().add(new CoberturaLine(true, 2, 4, 8, 5));
        var cc = new CoberturaConditions();
        cc.getConditionList().add(new CoberturaCondition(4, 2));
        x.getLineList().add(cc);
        var m = JAXBContext.newInstance(CoberturaLines.class).createMarshaller();
        var sw = new StringWriter();
        m.marshal(x, sw);
        assertEquals(sw.toString(), xml);
    }
}