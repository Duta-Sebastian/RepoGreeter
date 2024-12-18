package RepoGreeter;

import org.kohsuke.github.GHBranch;
import org.kohsuke.github.GHRepository;

import java.io.IOException;

public class PullRequestCreator {
    public static void createPullRequest(GHRepository repository) throws IOException {
        String branchName = "add-hello-file";
        String baseBranch = repository.getDefaultBranch();

        GHBranch base = repository.getBranch(baseBranch);
        repository.createRef("refs/heads/" + branchName, base.getSHA1());

        repository.createContent()
                .content("Hello world")
                .path("Hello.txt")
                .branch(branchName)
                .message("Add Hello.txt")
                .commit();

        repository.createPullRequest(
                "Add Hello.txt",
                branchName,
                baseBranch,
                "This PR adds a Hello.txt file containing 'Hello world'."
        );
    }
}
