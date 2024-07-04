package controller;

import feature.addMember.AddNewMembersToChannel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AddNewMembersToChannelController {

    @FXML
    private Label ChannelIDError;

    @FXML
    private TextField ChannelIDField;

    @FXML
    private Button SubmitButton;

    @FXML
    private Label TeamIDError;

    @FXML
    private TextField TeamIDField;

    @FXML
    private Button backButton;

    @FXML
    private Label userIDError;

    @FXML
    private TextField userIDField;

    @FXML
    void SubmitButtonClicked(ActionEvent event) {
        String TeamID = TeamIDField.getText();
        String ChannelID = ChannelIDField.getText();
        String userID = userIDField.getText();
        new AddNewMembersToChannel(TeamID, ChannelID, userID, Verify.owner);
        try {
            // Load the SyncDialog.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SyncDialog.fxml"));
            Parent root = loader.load();

            // Create a new stage for the dialog
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add new members to channels");
            dialogStage.setScene(new Scene(root));
            dialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Submit successfully!");
    }

    @FXML
    void backButtonClicked(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Team.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) backButton.getScene().getWindow();
            // Set new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }catch(Exception e){
            e.printStackTrace();

        }
    }

    @FXML
    void validateUserIDTyped(KeyEvent event) {

    }

}
