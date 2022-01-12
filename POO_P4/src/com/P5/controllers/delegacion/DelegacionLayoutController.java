package com.P5.controllers.delegacion;

import com.P5.entities.Delegacion;
import com.P5.repositories.DelegacionRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DelegacionLayoutController implements Initializable {

    @FXML
    private Button homeBtn;

    @FXML
    private Button addDelegacionBtn;

    @FXML
    private TableView<Delegacion> delegacionesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Delegacion> delegaciones = DelegacionRepository.getAll();

        TableColumn id = new TableColumn("ID");
        TableColumn direccion = new TableColumn("Direccion");
        TableColumn ciudad = new TableColumn("Ciudad");
        TableColumn email = new TableColumn("Email");
        TableColumn telefono = new TableColumn("Telefono");
        TableColumn central = new TableColumn("Central");
        delegacionesList.getColumns().addAll(id, direccion, ciudad, email, telefono, central);

        ObservableList<Delegacion> delegacionesData = FXCollections.observableArrayList(delegaciones);

        id.setCellValueFactory(new PropertyValueFactory<Delegacion, Long>("id"));
        direccion.setCellValueFactory(new PropertyValueFactory<Delegacion, String>("direccion"));
        ciudad.setCellValueFactory(new PropertyValueFactory<Delegacion, String>("ciudad"));
        email.setCellValueFactory(new PropertyValueFactory<Delegacion, String>("email"));
        telefono.setCellValueFactory(new PropertyValueFactory<Delegacion, String>("telefono"));
        central.setCellValueFactory(new PropertyValueFactory<Delegacion, Boolean>("central"));

        delegacionesList.setItems(delegacionesData);

        delegacionesList.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    System.out.println(delegacionesList.getSelectionModel().getSelectedItem());
                    try {
                        goTODelegacionDetail(event, delegacionesList.getSelectionModel().getSelectedItem());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void goTODelegacionDetail(MouseEvent event, Delegacion delegacionSeleted) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../views/delegacion/delegacionDetail.fxml"));
        Parent delegacionDetail = loader.load();

        DelegacionDetailController delegacionDetailController = loader.getController();
        delegacionDetailController.showDelegacionDetail(delegacionSeleted);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Delegacion " + delegacionSeleted.getId());
        window.setScene(new Scene(delegacionDetail, 500, 500));
    }

    @FXML
    void goToHome(ActionEvent event) throws IOException {
        Parent index = FXMLLoader.load(getClass().getResource("../../views/index.fxml"));
        Stage window = (Stage) homeBtn.getScene().getWindow();
        window.setTitle("ONG Entre Culturas");
        window.setScene(new Scene(index, 500, 500));
    }

    @FXML
    void goToaddDelegacion(ActionEvent event) throws IOException {
        Parent index = FXMLLoader.load(getClass().getResource("../../views/delegacion/delegacionForm.fxml"));
        Stage window = (Stage) homeBtn.getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Nueva Delegación");
        window.setScene(new Scene(index, 500, 500));
    }
}
