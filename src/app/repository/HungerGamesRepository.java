package app.repository;

import app.model.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class HungerGamesRepository {

    public List<Tribut> loadTributes(String filePath) throws IOException {
        List<Tribut> list = new ArrayList<>();
        String content = readFile(filePath);
        String[] objects = splitObjects(content);

        for (String obj : objects) {
            Map<String, String> map = parseObject(obj);
            list.add(new Tribut(
                    Integer.parseInt(map.get("id")),
                    map.get("name"),
                    Integer.parseInt(map.get("district")),
                    Status.fromJson(map.get("status")),
                    Integer.parseInt(map.get("skillLevel"))
            ));
        }
        return list;
    }

    public List<Ereignis> loadEvents(String filePath) throws IOException {
        List<Ereignis> list = new ArrayList<>();
        String content = readFile(filePath);
        String[] objects = splitObjects(content);

        for (String obj : objects) {
            Map<String, String> map = parseObject(obj);
            list.add(new Ereignis(
                    Integer.parseInt(map.get("id")),
                    Integer.parseInt(map.get("tributeId")),
                    EventTyp.valueOf(map.get("type")),
                    Integer.parseInt(map.get("points")),
                    Integer.parseInt(map.get("day"))
            ));
        }
        return list;
    }

    public List<SponsorGeschenk> loadGifts(String filePath) throws IOException {
        List<SponsorGeschenk> list = new ArrayList<>();
        String content = readFile(filePath);
        String[] objects = splitObjects(content);

        for (String obj : objects) {
            Map<String, String> map = parseObject(obj);
            list.add(new SponsorGeschenk(
                    Integer.parseInt(map.get("id")),
                    Integer.parseInt(map.get("tributeId")),
                    map.get("itemName"),
                    Integer.parseInt(map.get("value")),
                    Integer.parseInt(map.get("day"))
            ));
        }
        return list;
    }

    // Helper methods for manual JSON parsing
    private String readFile(String path) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(path)));
        return content.trim().substring(1, content.length() - 1); // remove [ ]
    }

    private String[] splitObjects(String content) {
        return content.split("\\},\\s*\\{");
    }

    private Map<String, String> parseObject(String obj) {
        obj = obj.replace("{", "").replace("}", "").replace("\"", "");
        Map<String, String> map = new HashMap<>();
        for (String field : obj.split(",")) {
            String[] kv = field.split(":");
            if (kv.length >= 2) {
                map.put(kv[0].trim(), kv[1].trim());
            }
        }
        return map;
    }

}