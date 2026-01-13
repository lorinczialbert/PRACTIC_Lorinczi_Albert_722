package app;

import app.controller.HungerGamesController;
import app.repository.HungerGamesRepository;
import app.service.HungerGamesService;

public class Main {
    public static void main(String[] args) {
        // Dependency Injection setup
        HungerGamesRepository repository = new HungerGamesRepository();
        HungerGamesService service = new HungerGamesService(repository);
        HungerGamesController controller = new HungerGamesController(service);

        // Start application
        controller.run();
    }
}