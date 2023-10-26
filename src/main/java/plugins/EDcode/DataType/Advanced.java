package plugins.EDcode.DataType;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Advanced implements Serializable {
    private boolean status; // 是否展开
    private String value;   // 选项的值
    private String name;    // 表示加解密的名字，如：base64
    private Object otherValue;  // 其他值，也可以是隐藏值，不做任何展示，但是负责记录一些东西，比如 nextID
    Map<String, Advanced> advanceds = new HashMap<>();  // 高级

    public Map<String, Advanced> getAdvanceds() {
        return advanceds;
    }

    public void setAdvanceds(Map<String, Advanced> advanceds) {
        this.advanceds = advanceds;
    }

    public Object getOtherValue() {
        return otherValue;
    }

    public void setOtherValue(Object otherValue) {
        this.otherValue = otherValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
