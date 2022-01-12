package com.P5.controllers.proyectos;

import com.P5.entities.Personal;
import com.P5.entities.Proyecto;
import com.P5.repositories.ProyectoRepository;
import com.P5.utils.Dialog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ProyectoDetailController {

    @FXML
    private Button proyectoLayoutBtn;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label paisLabel;

    @FXML
    private Label localizacionLabel;

    @FXML
    private Label lineaAccionLabel;

    @FXML
    private Label sublineaAccionLabel;

    @FXML
    private Label fechaInicioLabel;

    @FXML
    private Label fechaFinLabel;

    @FXML
    private Label socioLocalLabel;

    @FXML
    private Label financiadorLabel;

    @FXML
    private Label financiacionAportadaLabel;

    @FXML
    private TableView<Personal> personalTablePanel;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    private Proyecto proyectoSelected;

    @FXML
    void goToProyectoLayout(ActionEvent event) throws IOException {
        Parent proyectosLayout = FXMLLoader.load(getClass().getResource("../../views/proyecto/proyectosLayout.fxml"));
        Stage window = (Stage) proyectoLayoutBtn.getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Proyectos");
        window.setScene(new Scene(proyectosLayout, 500, 500));
    }

    public void showProyectoDetail(Proyecto proyectoSeleted) {
        this.proyectoSelected = proyectoSeleted;
        nombreLabel.setText(proyectoSeleted.getNombre());
        paisLabel.setText(proyectoSeleted.getPais());
        localizacionLabel.setText(proyectoSeleted.getLocalizacion());
        lineaAccionLabel.setText(proyectoSeleted.getLineaAccion());
        sublineaAccionLabel.setText(proyectoSeleted.getSubLineaAccion());
        fechaInicioLabel.setText(proyectoSeleted.getFechaInicio().toString());
        fechaFinLabel.setText(proyectoSeleted.getFechaFin().toString());
        socioLocalLabel.setText(proyectoSeleted.getSocioLocal());
        financiadorLabel.setText(proyectoSeleted.getFinanciador());
        financiacionAportadaLabel.setText(proyectoSeleted.getFinanciacionAportada());

        buildPersonalAccordionTable(proyectoSeleted.getPersonalAsociado());
    }

    private void buildPersonalAccordionTable(List<Personal> personalAsociado) {
        TableColumn id = new TableColumn("ID");
        TableColumn nombre = new TableColumn("Nombre");
        TableColumn nif = new TableColumn("NIF");
        TableColumn direccion = new TableColumn("Dirección");
        personalTablePanel.getColumns().addAll(id, nombre, nif, direccion);

        ObservableList<Personal> personalData = FXCollections.observableArrayList(personalAsociado);

        id.setCellValueFactory(new PropertyValueFactory<Proyecto, Long>("id"));
        nombre.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("nombre"));
        nif.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("nif"));
        direccion.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("direccion"));

        personalTablePanel.setItems(personalData);
    }

    @FXML
    void goToEditProyectoForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../views/proyecto/proyectoForm.fxml"));
        Parent delegacionFom = loader.load();

        ProyectoFormController proyectoFormController = loader.getController();
        proyectoFormController.editProyecto(proyectoSelected);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Editar Proyecto " + proyectoSelected.getId());
        window.setScene(new Scene(delegacionFom, 500, 500));
    }

    @FXML
    void deleteProyecto(ActionEvent event) {
        ButtonType responseDialog = Dialog.confirmation("¿Desea eleminar definitivamente este proyecto?");

        if (responseDialog == ButtonType.YES) {
            ProyectoRepository.delete(proyectoSelected);
            try {
                goToProyectoLayout(null);
            } catch (IOException e) {
                Dialog.error(e.getMessage());
            }
        }
    }
}
