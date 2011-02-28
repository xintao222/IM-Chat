package im.server;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.Maps;
import im.common.Client;
import im.util.Exceptions;
import im.common.Message;
import im.common.Parameters;
import java.util.Arrays;

/**
 *
 * @author Stanislav Peshterliev
 */
public class Dispatcher extends Thread {

    private static final Logger logger = Logger.getLogger(Dispatcher.class.getName());
    private BlockingQueue<Message> queue;
    private Map<String, Client> clients;

    public synchronized void addClient(String name, Client client) {
        clients.put(name, client);
        dispatch(new Message("server", "UserOnline", "", Arrays.asList("all"), Parameters.of("user", name)));
    }

    public synchronized void removeClient(Client client) {
        if (client.getUser() == null) {
            return;
        }

        String username = client.getUser().username;

        if (clients.containsKey(username)) {
            clients.remove(username);

            dispatch(new Message("server", "UserOffline", "", Arrays.asList("all"), Parameters.of("user", username)));
        }
    }

    public Client getClient(String username) {
        return clients.get(username);
    }

    public String[] getClientsNames() {
        return clients.keySet().toArray(new String[0]);
    }

    public Dispatcher() {
        this.queue = new LinkedBlockingQueue<Message>();
        this.clients = Maps.newHashMap();
    }

    public void dispatch(Message message) {
        queue.offer(message);
    }

    @Override
    public void run() {
        try {
            while (true) {
                Message message = queue.take();

                if (message.sender.equals("server") && message.receivers.contains("all")) {
                    for (Client client : clients.values()) {
                        client.getSender().send(message);
                    }
                } else {
                    for (String reciver : message.receivers) {
                        Client client = clients.get(reciver);
                        if (client == null) continue;
                        
                        client.getSender().send(message);
                    }
                }
            }
        } catch (InterruptedException e) {
            logger.log(Level.INFO, "Dispatcher stopped \n{0}", Exceptions.getStackTrace(e));
        }
    }
}