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

        Scene scene = new Scene(layout, 400, 400);
        primaryStage.setScene(scene);

        Text nameText = new Text();
        ComboBox<GHRepository> repoComboBox = RepositoryComboBoxConfig.createRepositoryComboBox();
        Button pullRequestButton = new Button("Create the pull request");
        pullRequestButton.setVisible(false);

        layout.getChildren().addAll(nameText, repoComboBox, pullRequestButton);

        gitHubService.connect().thenRunAsync(() -> {
            String username;
            try {
                username = gitHubService.getGitHub().getMyself().getLogin();
                updateUIWithUserName(nameText, username);
                retrieveAndShowRepositories(gitHubService, repoComboBox, pullRequestButton);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        pullRequestButton.setOnAction(_ -> {
            if (gitRepo != null) {
                gitHubService.createPullRequest(gitRepo);
            }
        });

        repoComboBox.setOnAction(_ -> {
            this.gitRepo = repoComboBox.getSelectionModel().getSelectedItem();
            repoComboBox.hide();
        });
        primaryStage.show();
    }

    private void updateUIWithUserName(Text nameText, String userName) {
        Platform.runLater(() -> nameText.setText("Hello " + userName));
        System.out.println("Hello " + userName);
    }

    private void retrieveAndShowRepositories(GithubService gitHubService,
                                             ComboBox<GHRepository> repoComboBox,
                                             Button pullRequestButton) {
        gitHubService.getRepositories()
                .thenAccept(repos -> {
                    RepositoryComboBoxConfig.updateComboBoxItems(repoComboBox, repos);
                    pullRequestButton.setVisible(true);
                });
    }
}
