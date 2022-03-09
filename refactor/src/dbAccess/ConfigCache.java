package dbAccess;

import java.util.HashMap;

public class ConfigCache {
    private static ConfigDbAccess service=new ConfigDbAccess();
    private static HashMap<String,String>cache=new HashMap<String,String>();
    public static boolean putConfig(String key,String value) {
        return true;
    }

    public static String getConfig(String key) {
        if(cache.containsKey(key))
            return cache.get(key);
        service.getConfig(key);
        return null;
    }
}
