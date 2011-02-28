package im.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.google.common.collect.Lists;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;

/**
 * Message xml format
 * <?xml version="1.0"?>
 * <message sender="sender" date="213123213123" controller="controller" action="action" authtoken="123">
 *   <parameters>
 *     <parameter name="parameter1">value1</parameter>
 *     <parameter name="parameter2">value2</parameter>
 *     <parameter name="parameter3">value3</parameter>
 *   </parameters>
 *   <receivers>
 *     <receiver>reciver1</receiver>
 *     <receiver>reciver2</receiver>
 *   </receivers>
 * </message>
 *
 * @author Stanislav Peshterliev
 */
public class Message {

    public final String sender;
    public final Date date;
    public final String action;
    public final String authtoken;
    public final Parameters parameters;
    public final List<String> receivers;

    public Message(String sender, String action) {
        this(sender, action, "", null, null, new Date());
    }

    public Message(String sender, String action, Parameters parameters) {
        this(sender, action, "", null, parameters);
    }

    public Message(String sender, String action, String authtoken) {
        this(sender, action, authtoken, null, null, new Date());
    }

    public Message(String sender, String action, String authtoken,
            List<String> receivers) {
        this(sender, action, authtoken, receivers, null, new Date());
    }

    public Message(String sender, String action, String authtoken,
            List<String> receivers, Parameters parameters) {
        this(sender, action, authtoken, receivers, parameters, new Date());
    }

    public Message(String sender, String action, String authtoken,
            List<String> receivers, Parameters parameters, Date date) {
        this.sender = sender;
        this.date = date;
        this.action = action;
        this.authtoken = authtoken;

        this.parameters = parameters == null ? new Parameters() : parameters;

        if (receivers == null) {
            receivers = Lists.newArrayList();
        }
        this.receivers = (List<String>) Collections.unmodifiableList(receivers);
    }

    public static Message parse(String text) throws SAXException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document dom;
        try {
            dom = db.parse(new ByteArrayInputStream(text.getBytes("UTF-8")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Element messageElement = dom.getDocumentElement();

        NodeList receiversNodeList = messageElement.getElementsByTagName("receiver");
        NodeList parametersNodeList = messageElement.getElementsByTagName("parameter");

        List<String> receivers = Lists.newArrayList();
        Parameters parameters = new Parameters();

        if (receiversNodeList != null && receiversNodeList.getLength() > 0) {
            for (int i = 0; i < receiversNodeList.getLength(); i++) {
                receivers.add(receiversNodeList.item(i).getTextContent());
            }
        }

        if (parametersNodeList != null && parametersNodeList.getLength() > 0) {
            for (int i = 0; i < parametersNodeList.getLength(); i++) {
                Element element = (Element) parametersNodeList.item(i);
                parameters.put(element.getAttribute("name"),
                        unespace(element.getTextContent()));
            }
        }

        return new Message(messageElement.getAttribute("sender"),
                messageElement.getAttribute("action"),
                messageElement.getAttribute("authtoken"), receivers, parameters,
                new Date(Long.parseLong(messageElement.getAttribute("date"))));
    }

    public String xmlize() {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }

        Document dom = db.newDocument();

        Element messageElement = dom.createElement("message");
        dom.appendChild(messageElement);

        messageElement.setAttribute("sender", sender);
        messageElement.setAttribute("date", Long.toString(date.getTime()));
        messageElement.setAttribute("action", action);
        messageElement.setAttribute("authtoken", authtoken);

        Element parametersElement = dom.createElement("parameters");

        for (Map.Entry<String, String> param : parameters.entrySet()) {
            Element parameterElement = dom.createElement("parameter");
            
            parameterElement.setAttribute("name", param.getKey());
            parameterElement.setTextContent(espace(param.getValue()));

            parametersElement.appendChild(parameterElement);
        }

        messageElement.appendChild(parametersElement);

        Element receiversElement = dom.createElement("receivers");

        for (String receiver : receivers) {
            Element receiverElement = dom.createElement("receiver");
            
            receiverElement.setTextContent(receiver);
            
            receiversElement.appendChild(receiverElement);
        }

        messageElement.appendChild(receiversElement);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputFormat format = new OutputFormat(dom);
        format.setOmitXMLDeclaration(true);
        XMLSerializer serializer = new XMLSerializer(outputStream, format);

        try {
            serializer.serialize(dom);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            return outputStream.toString("UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    private static String espace(String text) {
        return text.replace("\n", "\\n");
    }

    private static String unespace(String text) {
        return text.replace("\\n", "\n");
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((action == null) ? 0 : action.hashCode());
        result = prime * result
                + ((authtoken == null) ? 0 : authtoken.hashCode());
        result = prime * result + ((date == null) ? 0 : date.hashCode());
        result = prime * result
                + ((parameters == null) ? 0 : parameters.hashCode());
        result = prime * result
                + ((receivers == null) ? 0 : receivers.hashCode());
        result = prime * result + ((sender == null) ? 0 : sender.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Message other = (Message) obj;
        if (action == null) {
            if (other.action != null) {
                return false;
            }
        } else if (!action.equals(other.action)) {
            return false;
        }
        if (authtoken == null) {
            if (other.authtoken != null) {
                return false;
            }
        } else if (!authtoken.equals(other.authtoken)) {
            return false;
        }

        if (parameters == null) {
            if (other.parameters != null) {
                return false;
            }
        } else if (!parameters.equals(other.parameters)) {
            return false;
        }
        if (receivers == null) {
            if (other.receivers != null) {
                return false;
            }
        } else if (!receivers.equals(other.receivers)) {
            return false;
        }
        if (sender == null) {
            if (other.sender != null) {
                return false;
            }
        } else if (!sender.equals(other.sender)) {
            return false;
        }
        return true;
    }

    public boolean identical(Object obj) {
        boolean equals = this.equals(obj);
        
        Message other = (Message) obj;
        if (date == null) {
            if (other.date != null) {
                return false;
            }
        } else if (!date.equals(other.date)) {
            return false;
        }

        return equals;
    }

    @Override
    public String toString() {
        return "Message{" + "sender=" + sender + ",date=" + date + ",action=" + action + ",authtoken=" + authtoken + ",parameters=" + parameters + ",receivers=" + receivers + '}';
    }
}
