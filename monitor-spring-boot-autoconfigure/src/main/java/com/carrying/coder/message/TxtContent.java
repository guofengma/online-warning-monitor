package com.carrying.coder.message;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;
import lombok.ToString;

/**
 * @author Liu Hailin
 * @create 2017-12-13 下午4:11
 **/
@ToString
@Data
@JsonInclude(Include.NON_NULL)
public class TxtContent{

    private String touser;
    private String toparty;
    private String totag;
    private String msgtype;
    private int agentid;
    private int safe;

    private Map<String, String> text = new HashMap<String, String>();

    public TxtContent(String text) {
        this.text.put( "content", text );
    }
}
