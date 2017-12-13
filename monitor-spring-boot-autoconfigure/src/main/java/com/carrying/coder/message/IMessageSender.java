package com.carrying.coder.message;

/**
 * @author Liu Hailin
 * @create 2017-12-13 下午3:37
 **/
public interface IMessageSender {

    boolean send(Message msg);

    boolean send(TxtMessage txtMessage);
}
