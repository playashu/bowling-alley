package dbAccess;

import java.util.HashMap;

public class ConfigCache {
    private static ConfigDbAccess service=new ConfigDbAccess();
    private static HashMap<String,String>cache=new HashMap<String,String>();
    public static boolean putConfig(String key,String value) {
        if(service.putConfig(key,value))
        {
            cache.put(key,value);
            return true;
        }
        return false;
    }

    public static String getConfig(String key) {
        if(cache.containsKey(key))
            return cache.get(key);
        String value=service.getConfig(key);
        //cache.put();
        return null;
    }
}
