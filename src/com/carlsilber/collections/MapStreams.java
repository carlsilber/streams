package com.carlsilber.collections;


import java.util.Map;

public class MapStreams {

    private static Map<Integer, String> map = Map.ofEntries(
            Map.entry(1, "value 1"),
            Map.entry(2, "value 2"),
            Map.entry(3, "value 3"),
            Map.entry(4, "value 4"),
            Map.entry(5, "value 5")

    );

    public void removingELementsFromMap() {
        // remove by value
        map.values().removeIf(value -> !value.contains("2"));
        // remove by key
        map.keySet().removeIf(key -> key != 1);
        // remove by entry / combination of key + value
        map.entrySet().removeIf(entry -> entry.getKey() != 1);
    }


}
