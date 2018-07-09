package com.olipro.utils.cobertura;

import org.testng.annotations.Test;

import javax.xml.bind.JAXBContext;
import java.io.StringWriter;

import static org.testng.Assert.*;

public class TestCoberturaConditions {
    @Test
    public void ClassConstructsWithValidListObject() {
        var cc = new CoberturaConditions();
        assertNotNull(cc.getConditionList());
        assertEquals(cc.getConditionList().size(), 0);
    }

    @Test
    public void testClassCorrectlySerializesToXML() throws Exception {
        final var xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><conditions>" + "" +
                "<condition number=\"2\" coverage=\"1%\" type=\"jump\"/>" +
                "<condition number=\"3\" coverage=\"1%\" type=\"jump\"/></conditions>";
        var x = new CoberturaConditions();
        x.getConditionList().add(new CoberturaCondition(1, 2));
        x.getConditionList().add(new CoberturaCondition(1, 3));
        var m = JAXBContext.newInstance(CoberturaConditions.class).createMarshaller();
        var sw = new StringWriter();
        m.marshal(x, sw);
        assertEquals(sw.toString(), xml);
    }
}