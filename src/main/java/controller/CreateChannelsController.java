package controller;

import feature.createChannel.*;
import entity.Owner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateChannelsController {
    
    @FXML
    private TextField displayNameField;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField membershipTypeField;

    @FXML
    private Button createButton;

    @FXML
    private Button backButton;

    @FXML
    private TextField TeamIDField;


    @FXML
    void backButtonClicked(ActionEvent event) {
        try {
            // Load the To Channel scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Team.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @FXML
    void handleCreateChannel(ActionEvent event) {
        String displayName = displayNameField.getText();
        String description = descriptionField.getText();
        String membershipType = membershipTypeField.getText();
        String teamID = TeamIDField.getText();

        //new CreateNewChannels(teamID, displayName, description, membershipType, Verify.owner);
        new CreateNewPrivateChannels(teamID, displayName, description, membershipType, Verify.owner);
        //new CreateNewSharedChannels(teamID, displayName, description, membershipType, Verify.owner);
        try {
            // Load the SyncDialog.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SyncDialog.fxml"));
            Parent root = loader.load();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Create new channels");
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("finish!");







    }
}
