package ru.deathkiller2009.dvdspringbootproject.util;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.deathkiller2009.dvdspringbootproject.models.Person;
import ru.deathkiller2009.dvdspringbootproject.repositories.PeopleRepository;
import ru.deathkiller2009.dvdspringbootproject.services.PeopleService;

@Component
public class DataInitializer implements CommandLineRunner {

    private final PeopleRepository peopleRepository;
    private final PeopleService peopleService;

    public DataInitializer(PeopleRepository peopleRepository,
                           PeopleService peopleService) {
        this.peopleRepository = peopleRepository;
        this.peopleService = peopleService;
    }

    @Override
    public void run(String... args) {
        String adminUsername = "admin";

        if (!peopleRepository.existsByUsername(adminUsername)) {
            Person admin = new Person();
            admin.setUsername(adminUsername);
            admin.setPassword("admin");
            peopleService.addAdmin(admin);

            System.out.println("Создан начальный администратор:");
            System.out.println("login: admin");
            System.out.println("password: admin");
        }
    }
}
