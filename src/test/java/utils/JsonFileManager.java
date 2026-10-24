package utils;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.FileReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class JsonFileManager {

    private static final Logger log = LogManager.getLogger(JsonFileManager.class);
    private static final Type TYPE = new TypeToken<LinkedHashMap<String, Object>>() {}.getType();

    private final LinkedHashMap<String, Object> data;

    /**
     * Loads the JSON file at the given path into memory.
     *
     * @param jsonPath the path to the JSON file
     */
    public JsonFileManager(String jsonPath) {
        if (jsonPath == null || jsonPath.isEmpty()) {
            log.error("❌ JSON path is null or empty");
            throw new IllegalArgumentException("JSON path must be provided");
        }
        log.info("📄 Loading JSON from [{}]", jsonPath);
        try (FileReader reader = new FileReader(jsonPath)) {
            LinkedHashMap<String, Object> loaded = new Gson().fromJson(reader, TYPE);
            data = loaded == null ? new LinkedHashMap<>() : loaded;
        } catch (IOException e) {
            log.error("❌ Could not read JSON file [{}]", jsonPath, e);
            throw new UncheckedIOException("Could not read JSON file: " + jsonPath, e);
        } catch (JsonParseException e) {
            log.error("❌ Invalid JSON in [{}]", jsonPath, e);
            throw e;
        }
        log.debug("✅ Loaded {} top-level keys from [{}]", data.size(), jsonPath);
    }

    /**
     * Retrieves the value associated with the specified key.
     *
     * @param key the key to search for
     * @return the corresponding value, or null if the key is not found
     */
    public Object getValueByKey(String key) {
        if (!data.containsKey(key)) {
            log.warn("⚠️ Key '{}' not found in JSON", key);
            return null;
        }
        Object value = data.get(key);
        log.debug("🔑 Found key '{}' ({})", key, value == null ? "null" : value.getClass().getSimpleName());
        return value;
    }

    public Object[][] getUsersByKey(String key){

        List<Map<String,String>> users = (List<Map<String,String>>)getValueByKey(key);
        int i = 0, st = 0, end = 1;
        users = users.subList(st, end);
        Object res[][] = new Object[users.size()][2];

        for (Map<String,String> user : users){
            res[i][0] = user.get("username");
            res[i][1] = user.get("password");
            i++;
        }
        log.debug("👥 Built {} user rows for key '{}'", users.size(), key);
        return res;
    }
}