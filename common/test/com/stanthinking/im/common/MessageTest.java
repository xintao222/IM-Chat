package com.stanthinking.im.common;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.Test;
import org.xml.sax.SAXException;


/**
 *
 * @author Stanislav Peshterliev
 */
public class MessageTest {

    @Test
    public void testParse() throws ParserConfigurationException, SAXException, IOException {
        String xml = "<message sender=\"sender\" date=\"1283002905\" action=\"action\" authtoken=\"123\"><parameters><parameter name=\"parameter1\">value1\\n</parameter><parameter name=\"parameter2\">value2</parameter></parameters><receivers><receiver>reciver1</receiver><receiver>reciver2</receiver></receivers></message>";
        Message message = Message.parse(xml);

        assertEquals("action", message.action);
        assertEquals("sender", message.sender);
        assertEquals("123", message.authtoken);
        assertEquals(new Date(Long.parseLong("1283002905")), message.date);

        assertTrue(message.receivers.equals(Arrays.asList("reciver1", "reciver2")));

        assertEquals("value1\n", message.parameters.get("parameter1"));
        assertEquals("value2", message.parameters.get("parameter2"));
    }

    @Test
    public void testToXml() throws ParserConfigurationException, SAXException, IOException {
        Parameters parameters = Parameters.of("parameter1", "value1\n", "parameter2", "value2");

        Message message = new Message(
                "sender", "action", "123", Arrays.asList("reciver1", "reciver2"), parameters, new Date(Long.parseLong("1283002905")));

        String xml = "<message action=\"action\" authtoken=\"123\" date=\"1283002905\" sender=\"sender\"><parameters><parameter name=\"parameter2\">value2</parameter><parameter name=\"parameter1\">value1\\n</parameter></parameters><receivers><receiver>reciver1</receiver><receiver>reciver2</receiver></receivers></message>";
        assertEquals(xml, message.xmlize());
    }
}
