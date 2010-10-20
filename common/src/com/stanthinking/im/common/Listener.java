package com.stanthinking.im.common;

/**
 *
 * @author Stanislav Peshterliev
 */
public abstract class Listener extends Thread {
    abstract public void process(Message message);
}
