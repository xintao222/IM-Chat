package com.stanthinking.im.server;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.io.Closeables;
import com.stanthinking.im.common.Client;
import com.stanthinking.im.util.Exceptions;
import com.stanthinking.im.common.Message;
import com.stanthinking.im.common.Sender;

/**
 *
 * @author Stanislav Peshterliev
 */
public class ClientSender extends Sender {

    private static final Logger logger = Logger.getLogger(ClientSender.class.getName());
    private Client client;
    private Dispatcher dispatcher;
    private BlockingQueue<Message> queue;

    public ClientSender(Client client, Dispatcher dispatcher) {
        this.client = client;
        this.dispatcher = dispatcher;
        this.queue = new LinkedBlockingQueue<Message>();
    }

    @Override
    public void send(Message message) {
        queue.offer(message);
    }

    @Override
    public void run() {
        PrintWriter out = null;
        boolean threw = true;

        try {
            out = new PrintWriter(new OutputStreamWriter(
                    client.getSocket().getOutputStream()));

            while (!isInterrupted()) {
                Message message = queue.take();
                out.println(message.xmlize());
                out.flush();
            }
            threw = false;
        } catch (IOException e) {
            logger.log(Level.WARNING, "Problem writing to socket (broken connection) \n{0}", Exceptions.getStackTrace(e));
        } catch (InterruptedException e) {
            logger.log(Level.WARNING, "Client disconnected");
        } finally {
            try {
                Closeables.close(out, threw);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Problem closing stream \n{0}", Exceptions.getStackTrace(e));
            }
        }

        // Communication is broken. Interrupt both listener and
        // sender threads
        client.getListener().interrupt();
        dispatcher.removeClient(client);
    }
}
