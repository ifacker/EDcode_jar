package plugins.EDcode.DataType;

import java.util.HashMap;
import java.util.Map;

public class Config {
    Map<String, Advanced> advanceds = new HashMap<>();


    public Map<String, Advanced> getAdvanceds() {
        return advanceds;
    }

    public void setAdvanceds(Map<String, Advanced> advanceds) {
        this.advanceds = advanceds;
    }

}
