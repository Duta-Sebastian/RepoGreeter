package RepoGreeter;

import RepoGreeter.TokenProvider.FileTokenProvider;
import RepoGreeter.TokenProvider.TokenProvider;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.kohsuke.github.GHRepository;

import java.io.IOException;

public class MainApp extends Application {
    TokenProvider tokenProvider = new FileTokenProvider("config.json");
    GithubService gitHubService = new GithubService(tokenProvider);
    private GHRepository gitRepo;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("RepoGreeter");

        VBox layout = new VBox();
        layout.setSpacing(10);

        Scene scene = new Scene(layout, 800, 800);
        primaryStage.setScene(scene);

        Text nameText = new Text();
        ComboBox<GHRepository> repoComboBox = RepositoryComboBoxConfig.createRepositoryComboBox();
        Button pullRequestButton = new Button("Create the pull request");

        layout.getChildren().addAll(nameText, repoComboBox, pullRequestButton);

        gitHubService.connect().thenRunAsync(() -> {
            String username;
            try {
                username = gitHubService.getGitHub().getMyself().getLogin();
                updateUIWithUserName(nameText, username);
                retrieveAndShowRepositories(gitHubService, repoComboBox);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        pullRequestButton.setOnAction(_ -> {
            if (gitRepo == null) {
                System.out.println("Please select a repo");
            } else {
                gitHubService.createPullRequest(gitRepo);
            }
        });

        repoComboBox.setOnAction(_ -> {
            GHRepository selectedRepo = repoComboBox.getSelectionModel().getSelectedItem();
            System.out.println("Selected repo: " + selectedRepo.getName());
            this.gitRepo = selectedRepo;
            repoComboBox.hide();
        });
        primaryStage.show();
    }

    private void updateUIWithUserName(Text nameText, String userName) {
        Platform.runLater(() -> nameText.setText("Hello " + userName));
        System.out.println("Hello " + userName);
    }

    private void retrieveAndShowRepositories(GithubService gitHubService, ComboBox<GHRepository> repoComboBox) {
        gitHubService.getRepositories()
                .thenAccept(repos -> RepositoryComboBoxConfig
                        .updateComboBoxItems(repoComboBox, repos));
    }
}
