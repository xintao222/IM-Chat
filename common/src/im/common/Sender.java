package im.common;

/**
 *
 * @author Stanislav Peshterliev
 */
abstract public class Sender extends Thread {
    abstract public void send(Message message);
}
