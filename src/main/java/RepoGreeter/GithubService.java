package RepoGreeter;

import RepoGreeter.TokenProvider.TokenProvider;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GithubService {
    private final TokenProvider tokenProvider;
    private GitHub gitHub;

    public GithubService(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public CompletableFuture<Void> connect() {
        return CompletableFuture.runAsync(() -> {
            try {
                String token = tokenProvider.getToken();
                this.gitHub = GithubConnector.connect(token);
            } catch (IOException e) {
                throw new RuntimeException("Failed to connect to GitHub: " + e.getMessage(), e);
            }
        });
    }

    public CompletableFuture<List<GHRepository>> getRepositories() {
        if (gitHub == null) {
            throw new IllegalStateException("GitHub is not connected");
        }
        return CompletableFuture.supplyAsync(() -> {
            try {
                return gitHub.getMyself().listRepositories().toList();
            } catch (IOException e) {
                throw new RuntimeException("Failed to fetch repositories: " + e.getMessage(), e);
            }
        });
    }

    public void createPullRequest(GHRepository repository) {
        CompletableFuture.runAsync(() -> {
            try {
                PullRequestCreator.createPullRequest(repository);
            } catch (IOException e) {
                throw new RuntimeException("Failed to create pull request: " + e.getMessage(), e);
            }
        });
    }

    public GitHub getGitHub() {
        return gitHub;
    }
}
