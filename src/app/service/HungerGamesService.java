package app.service;

import app.model.*;
import app.repository.HungerGamesRepository;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class HungerGamesService {
    private HungerGamesRepository repository;
    private List<Tribut> tributes;
    private List<Ereignis> events;
    private List<SponsorGeschenk> gifts;

    public HungerGamesService(HungerGamesRepository repository) {
        this.repository = repository;
    }
    // ubung 1 , das loaden der daten
    public void loadData(String tributesFile, String eventsFile, String giftsFile) throws IOException {
        this.tributes = repository.loadTributes(tributesFile);
        this.events = repository.loadEvents(eventsFile);
        this.gifts = repository.loadGifts(giftsFile);
    }

    public List<Tribut> getAllTributes() {
        return tributes;
    }

    public int getEventsCount() { return events.size(); }
    public int getGiftsCount() { return gifts.size(); }

    // das filtern der tributes nach district und status
    public List<Tribut> filterTributes(int district, Status status) {
        return tributes.stream()
                .filter(t -> t.getDistrikt() == district && t.getStatus() == status)
                .collect(Collectors.toList());
    }

    //logik der sortierung
    public List<Tribut> getSortedTributes() {
        return tributes.stream()
                .sorted(Comparator.comparingInt(Tribut::getSkillLevel).reversed() //nach skill level absteigend
                        .thenComparing(Tribut::getName))
                .collect(Collectors.toList());
    }

    // ubung 4, schreiben der sortierten tributes in eine text datei
    public void exportSortedTributes(String filename) throws IOException {
        List<Tribut> sorted = getSortedTributes();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Tribut t : sorted) {
                writer.write(t.toString());
                writer.newLine();
            }
        }
    }

    // Task 5: Calculate Points Logic
    public int calculateComputedPoints(Ereignis e) {
        int points = e.getPoints();
        int day = e.getDay();
        switch (e.getTyp()) {
            case FOUND_SUPPLIES: return points + (2 * day);
            case INJURED:        return points - day;
            case ATTACK:         return (points * 2) + day;
            case HELPED_ALLY:    return points + 5;
            case SPONSORED:      return points + 10;
            default:             return points;
        }
    }

    public List<Ereignis> getFirstNEvents(int n) {
        return events.stream().limit(n).collect(Collectors.toList());
    }

    // Task 6: Ranking Logic
    public List<Tribut> getTopTributes(int limit) {
        Map<Integer, Integer> scores = new HashMap<>();

        // Calculate event points
        for (Ereignis e : events) {
            scores.put(e.getTributId(), scores.getOrDefault(e.getTributId(), 0) + calculateComputedPoints(e));
        }
        // Calculate gift points
        for (SponsorGeschenk g : gifts) {
            scores.put(g.getTributId(), scores.getOrDefault(g.getTributId(), 0) + g.getValue());
        }

        return tributes.stream()
                .sorted((t1, t2) -> {
                    int s1 = scores.getOrDefault(t1.getId(), 0);
                    int s2 = scores.getOrDefault(t2.getId(), 0);
                    if (s1 != s2) return Integer.compare(s2, s1); // Descending score
                    return t1.getName().compareTo(t2.getName()); // Ascending name
                })
                .limit(limit)
                .collect(Collectors.toList());
    }

    public int getScoreForTribute(int tributeId) {
        int score = 0;
        for (Ereignis e : events) {
            if (e.getTributId() == tributeId) score += calculateComputedPoints(e);
        }
        for (SponsorGeschenk g : gifts) {
            if (g.getTributId() == tributeId) score += g.getValue();
        }
        return score;
    }

    //report generation
    public void generateArenaReport(String filename) throws IOException {
        Map<EventTyp, Long> counts = events.stream()
                .collect(Collectors.groupingBy(Ereignis::getTyp, Collectors.counting()));

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (EventTyp type : EventTyp.values()) {
                if (counts.containsKey(type)) {
                    writer.write(type + " -> " + counts.get(type));
                    writer.newLine();
                }
            }
        }
    }
}