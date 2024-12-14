package org.example;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String token;
        GitHub gitHub;
        GHRepository repository;
        try {
            token = ConfigLoader.loadToken("./config.json");
            gitHub = GithubConnector.connect(token);
            repository = RepositorySelector.selectRepository(gitHub);
            PullRequestCreator.createPullRequest(repository);
        }
        catch (IOException e) {
            System.err.println("Error encountered " + e.getMessage());
        }
    }
}