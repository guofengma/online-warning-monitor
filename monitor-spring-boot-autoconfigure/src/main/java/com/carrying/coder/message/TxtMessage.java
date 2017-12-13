package com.carrying.coder.message;

import lombok.Builder;
import lombok.Data;

/**
 * @author Liu Hailin
 * @create 2017-12-13 下午3:40
 **/
@Data
@Builder
public class TxtMessage {

    private String txtMsg;

    private MessageType type;

}
