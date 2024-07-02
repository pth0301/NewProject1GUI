package controller;

import feature.addMember.AddNewMembersToTeamFromCSV;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;

import entity.Member;

public class ToTeamFromCSVController implements Initializable {

    @FXML
    private Button ChoosefilesButton;

    @FXML
    private TableView<Member> TableView;

    @FXML
    private TextArea TextArea;

    @FXML
    private TableColumn<Member, String> displayName;

    @FXML
    private TableColumn<Member, String> email;

    @FXML
    private TableColumn<Member, String> roles;

    @FXML
    private TableColumn<Member, String> userId;

    @FXML
    private TextField TeamIDField;

    @FXML
    private Button addButton;
    @FXML
    private Button BackButton;

    private ObservableList<Member> list = FXCollections.observableArrayList();

    private File selectedFile;

    @FXML
    void ChoosefilesButtonClicked(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();

        Stage stage = (Stage) ChoosefilesButton.getScene().getWindow();
        selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            TextArea.setText(selectedFile.getAbsolutePath());
            initList(selectedFile);
        }
    }

    @FXML
    void addButtonClicked(ActionEvent event) {
        String TeamID = TeamIDField.getText();
        String filePath = TextArea.getText();
        if (filePath != null && !filePath.isEmpty()) {
            new AddNewMembersToTeamFromCSV(TeamID, filePath, Verify.owner);
            System.out.println("Add successfully!");
        } else {
            System.out.println("No file selected.");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayName.setCellValueFactory(new PropertyValueFactory<>("displayName"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        roles.setCellValueFactory(new PropertyValueFactory<>("roles"));
        TableView.setItems(list);
    }

    public void initList(File file) {
        list.clear();
        try {
            Scanner inputStream = new Scanner(file);
            if (inputStream.hasNext()) {
                inputStream.nextLine();
            }
            while (inputStream.hasNext()) {
                String data = inputStream.nextLine();
                String[] valuesLine = data.split(",");
                list.add(new Member(valuesLine[0], valuesLine[1], valuesLine[2], valuesLine[3]));
            }
            inputStream.close();
            TableView.setItems(list);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void BackButtonClicked(ActionEvent event) {
        try{
            // Load the To Channel scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Team.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) BackButton.getScene().getWindow();

            // Set the new scene
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
