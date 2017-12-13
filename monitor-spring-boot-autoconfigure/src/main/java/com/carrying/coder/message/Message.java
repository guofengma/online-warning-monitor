package com.carrying.coder.message;

import lombok.Data;

/**
 * @author Liu Hailin
 * @create 2017-12-13 下午3:40
 **/
@Data
public class Message<T extends AbstractContent> {

    private T msgContent;

    private MessageType type;

}
