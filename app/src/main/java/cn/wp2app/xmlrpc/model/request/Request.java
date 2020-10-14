package cn.wp2app.xmlrpc.model.request;


import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.util.Map;

import static cn.wp2app.xmlrpc.tools.JsonKit.fromJson;
import static cn.wp2app.xmlrpc.tools.JsonKit.toJson;

/**
 * @author robin
 */
public abstract class Request {

    public Map<String, Object> toMap() throws IOException {
        String json = toJson(this);
        return fromJson(json, new TypeReference<Map<String, Object>>() {
        });
    }
}
