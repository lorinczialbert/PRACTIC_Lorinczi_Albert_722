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


}