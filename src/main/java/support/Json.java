package support;

import com.google.gson.Gson;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Json {
    private Map<String, Object> mapa;

    public Json() {
        this.mapa = new HashMap<>();
    }

    public void setValue(String key, Object value) {
        this.mapa.put(key, value);
    }

    public Map<String, Object> getMap() {
        return mapa;
    }

    public String toJson() {
        Map<String, Object> convertedMap = new HashMap<>();
        for (Map.Entry<String, Object> entry : mapa.entrySet()) {
            Object value = entry.getValue();
            if (value instanceof Json) {
                convertedMap.put(entry.getKey(), ((Json) value).getMap());
            } else {
                convertedMap.put(entry.getKey(), value);
            }
        }
        return (new Gson()).toJson(convertedMap);
    }
}
