package im.server;

import im.common.Client;
import java.io.*;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import im.server.actions.Action;
import im.util.Exceptions;
import im.common.Controller;

/**
 *
 * @author Stanislav Peshterliev
 */
public class Application {

    private static final Logger logger = Logger.getLogger(Application.class.getName());
    public static int CLIENT_READ_TIMEOUT = 2 * 60 * 1000; // 5 minutes
    private int port;
    private ServerSocket serverSocket;
    private Dispatcher dispatcher;
    private Controller<Action> controller;

    public Application(int port) {
        this.port = port;
    }

    public void start() {
        logger.log(Level.INFO, "Started server on port: {0}", port);

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while starting server on port {0}\n{1}", new Object[]{port, Exceptions.getStackTrace(e)});
        }

        controller = new Controller<Action>("im.server.actions");

        dispatcher = new Dispatcher();
        dispatcher.start();

        while (true) {
            try {
                Socket socket = serverSocket.accept();
                socket.setSoTimeout(CLIENT_READ_TIMEOUT);

                logger.info("Accepted connection from client");

                Client client = new Client(socket);
                client.setListener(new ClientListener(client, dispatcher, controller));
                client.setSender(new ClientSender(client, dispatcher));

                client.getListener().start();
                client.getSender().start();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Connection from client can not be accepted \n{0}", Exceptions.getStackTrace(e));
            }
        }
    }

    public void stop() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while stopping server listening on port {0}\n{1}", new Object[]{port, Exceptions.getStackTrace(e)});
        }

        logger.log(Level.INFO, "Stopped server on port: {0}", port);
    }
}
