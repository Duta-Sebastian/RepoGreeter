package org.example;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

public class RepositorySelector {
    public static GHRepository selectRepository(GitHub gitHub) throws IOException {
        Map<String, GHRepository> repositories = gitHub.getMyself().getRepositories();
        System.out.println("Available repositories:");

        int i = 1;
        for (String name : repositories.keySet()) {
            System.out.println(i + ". " + name);
            i++;
        }

        Scanner scanner = new Scanner(System.in);
        System.out.print("Select a repository by number: ");
        int choice = scanner.nextInt();

        return (GHRepository) repositories.values().toArray()[choice - 1];
    }
}
