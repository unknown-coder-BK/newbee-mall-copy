package ltd.newbee.mall.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class R implements Serializable {
    private int code;
    private String msg;
    private Map<String, Object> map = new HashMap<>();

    public static R error(String msg) {
        R r = new R();
        r.code = 500;
        r.msg = msg;
        return r;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
