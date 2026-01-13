package app.controller;

import app.model.Ereignis;
import app.model.Status;
import app.model.Tribut;
import app.service.HungerGamesService;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class HungerGamesController {
    private HungerGamesService service;

    public HungerGamesController(HungerGamesService service) {
        this.service = service;
    }

    public void run() {
        try {
            // Uebung1: Lesen der Daten(das load)
            service.loadData("src/app/tributes.json", "src/app/events.json", "src/app/gifts.json");

            System.out.println("Tributes loaded: " + service.getAllTributes().size());
            System.out.println("Events loaded: " + service.getEventsCount());
            System.out.println("Gifts loaded: " + service.getGiftsCount());

            for (Tribut t : service.getAllTributes()) {
                System.out.println(t);
            }

            // uebung2: Filtern nach Distrikt
            handleDistrictFilter();

            // ubung 3 : sortieren der tribute
            System.out.println("\nSorted Tributes:");
            List<Tribut> sorted = service.getSortedTributes();
            sorted.forEach(System.out::println);

            // ubung 4: exportieren der sortierten tribute in eine txt datei
            service.exportSortedTributes("tributes_sorted.txt");
            System.out.println("\nTask 4: tributes_sorted.txt created.");

            //ubung 5: berechnung der punkte
            System.out.println("\nComputed Points (First 5 events):");
            for (Ereignis e : service.getFirstNEvents(5)) {
                System.out.printf("Event %d -> rawPoints=%d -> computedPoints=%d%n",
                        e.getId(), e.getPoints(), service.calculateComputedPoints(e));
            }




    private void handleDistrictFilter() {
        System.out.print("Input district: ");
        Scanner scanner = new Scanner(System.in);//wir lesen den Distrikt von der Konsole ein
        if (scanner.hasNextInt()) {
            int distrikt = scanner.nextInt();       //wir filtern die Tribute nach dem eingegebenen Distrikt und dem Status LEBENDIG
            List<Tribut> filtered = service.filterTributes(distrikt, Status.LEBENDIG);
            filtered.forEach(System.out::println);  //wir geben die gefilterten Tribute aus
        } else {
            System.out.println("Invalid input.");   //Fehlermeldung bei nicht guten Eingabe
        }
    }
}