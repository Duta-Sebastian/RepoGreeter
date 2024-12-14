package org.example;

import org.kohsuke.github.GitHub;

import java.io.IOException;

public class GithubConnector {
    public static GitHub connect(String token) throws IOException {
        return GitHub.connectUsingOAuth(token);
    }
}
