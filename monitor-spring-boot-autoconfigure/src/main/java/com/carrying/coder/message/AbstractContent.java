package com.carrying.coder.message;

import lombok.Data;

/**
 * @author Liu Hailin
 * @create 2017-12-13 下午3:55
 **/
@Data
public class AbstractContent {

    protected String touser;
    protected String toparty;
    protected String totag;
    protected String msgtype;
    protected int agentid;

    protected int safe;

}
