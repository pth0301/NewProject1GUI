package controller;

import feature.sync.SyncToAirtable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class SyncController {

    @FXML
    private Button BackButton;

    @FXML
    private TextField ChannelTableID;

    @FXML
    private Button SyncButton;

    @FXML
    private TextField TeamIDField;

    @FXML
    private Button TerminateButton;

    @FXML
    private TextField baseIDField;

    @FXML
    private TextField memberTableIDField;

    @FXML
    private TextField periodField;

    @FXML
    private TextField AirtableKeyField;

    private SyncToAirtable syncTask;
    private Thread syncThread;

    @FXML
    void BackButtonClicked(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Team.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) BackButton.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SyncButtonClicked(ActionEvent event) {
        String TeamID = TeamIDField.getText();
        String channelTableID = ChannelTableID.getText();
        String baseID = baseIDField.getText();
        String airtableKey = AirtableKeyField.getText();
        String memberTableID = memberTableIDField.getText();
        int period = Integer.parseInt(periodField.getText());

        syncTask = new SyncToAirtable(TeamID, baseID, memberTableID, channelTableID, airtableKey, period, Verify.owner);

        syncThread = new Thread(syncTask);
        syncThread.setDaemon(true);
        syncThread.start();

        System.out.println("Sync started!");
    }

    @FXML
    void TerminateButtonClicked(ActionEvent event) {
        if (syncTask != null) {
            syncTask.cancel();
            System.out.println("Sync terminated!");

            try {
                // Load the SyncDialog.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SyncDialog.fxml"));
                Parent root = loader.load();

                // Create a new stage for the dialog
                Stage dialogStage = new Stage();
                dialogStage.setTitle("Terminating state");
                dialogStage.setScene(new Scene(root));
                dialogStage.showAndWait();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
