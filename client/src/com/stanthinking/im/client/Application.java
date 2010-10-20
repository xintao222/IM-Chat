package com.stanthinking.im.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.stanthinking.im.client.ui.actions.Action;
import com.stanthinking.im.client.ui.LoginFrame;
import com.stanthinking.im.common.Client;
import com.stanthinking.im.common.Controller;
import com.stanthinking.im.common.Message;

/**
 *
 * @author Stanislav Peshterliev
 */
final public class Application {
    private static Client client;

    public static Client getClient() {
        return client;
    }

    public static void send(Message message) {
        client.getSender().send(message);
    }

    public static void start(final String address, final int port) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                LoginFrame loginFrame = Frames.get(LoginFrame.class);
                loginFrame.setVisible(true);

                String errorConnecting = "Error connecting to server";
                
                try {
                    createClient(address, port);
                } catch (UnknownHostException e) {
                    loginFrame.setError(errorConnecting);
                } catch (IOException e) {
                    loginFrame.setError(errorConnecting);
                }
            }
        });
    }

    private static void createClient(String address, int port) throws UnknownHostException, IOException {
        Socket socket = new Socket(address, port);

        client = new Client(socket);
        client.setListener(new ServerListener(client, new Controller<Action>("com.stanthinking.im.client.ui.actions")));
        client.setSender(new ServerSender(client));

        client.getListener().start();
        client.getSender().start();
    }

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        
        Application.start(args[0], Integer.parseInt(args[1]));
    }
}
