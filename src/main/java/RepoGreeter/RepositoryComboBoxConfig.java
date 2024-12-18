package RepoGreeter;

import javafx.application.Platform;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import org.kohsuke.github.GHRepository;

import java.util.List;

public class RepositoryComboBoxConfig {
    public static ComboBox<GHRepository> createRepositoryComboBox() {
        ComboBox<GHRepository> comboBox = new ComboBox<>();
        configureRepositoryComboBox(comboBox);
        return comboBox;
    }

    private static void configureRepositoryComboBox(ComboBox<GHRepository> comboBox) {
        comboBox.setVisible(false);
        comboBox.setPromptText("Select the repository you want to add the greeting to!");

        comboBox.setCellFactory(_ -> new ListCell<>() {
            @Override
            protected void updateItem(GHRepository repo, boolean empty) {
                super.updateItem(repo, empty);
                if (empty || repo == null) {
                    setText(null);
                } else {
                    setText(repo.getName());
                }
            }
        });

        comboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(GHRepository repo, boolean empty) {
                super.updateItem(repo, empty);
                if (empty || repo == null) {
                    setText(null);
                } else {
                    setText(repo.getName());
                }
            }
        });
    }

    public static void updateComboBoxItems(ComboBox<GHRepository> comboBox, List<GHRepository> repositories) {
        Platform.runLater(() -> {
            comboBox.getItems().setAll(repositories);
            comboBox.setVisible(!repositories.isEmpty());
        });
    }
}
