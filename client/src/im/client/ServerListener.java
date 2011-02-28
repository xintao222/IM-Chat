package im.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.xml.sax.SAXException;

import com.google.common.io.Closeables;
import im.client.ui.actions.Action;
import im.common.Client;
import im.common.Controller;
import im.common.Listener;
import im.util.Exceptions;
import im.common.Message;

/**
 *
 * @author Stanislav Peshterliev
 */
public class ServerListener extends Listener {

    private static final Logger logger = Logger.getLogger(ServerListener.class.getName());
    private Client client;
    private Controller<Action> controller;

    public ServerListener(Client client, Controller<Action> controller) {
        this.client = client;
        this.controller = controller;
    }

    @Override
    public void process(Message message) {
        try {
            controller.getAction(message.action + "Action").execute(message, client);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        BufferedReader in = null;
        boolean threw = true;

        try {
            in = new BufferedReader(new InputStreamReader(
                    client.getSocket().getInputStream()));

            while (!isInterrupted()) {
                try {
                    String text = in.readLine();
                    if (text == null) {
                        break;
                    }

                    Message message = Message.parse(text);
                    process(message);
                } catch (SocketTimeoutException e) {
                    client.getSender().send(new Message("server", "KeepAlive"));
                } catch (SAXException e) {
                    logger.log(Level.WARNING, "Can not parse message \n{0}", Exceptions.getStackTrace(e));
                }
            }

            threw = false;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Problem reading from socket (broken connection) \n{0}", Exceptions.getStackTrace(e));
        } finally {
            try {
                Closeables.close(in, threw);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Problem closing stream \n{0}", Exceptions.getStackTrace(e));
            }
        }

        // Communication is broken. Interrupt both sender and
        // sender threads
        client.getSender().interrupt();
    }
}
