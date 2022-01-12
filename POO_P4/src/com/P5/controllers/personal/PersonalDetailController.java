package com.P5.controllers.personal;

import com.P5.entities.Personal;
import com.P5.entities.Proyecto;
import com.P5.repositories.PersonalRepository;
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
import java.sql.Date;
import java.util.List;

public class PersonalDetailController {

    @FXML
    private Button proyectoLayoutBtn;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label nifLabel;

    @FXML
    private Label direccionLabel;

    @FXML
    private TableView<Proyecto> proyectosTablePanel;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    private Personal personalSelected;

    @FXML
    void goToPersonalLayout(ActionEvent event) throws IOException {
        Parent personalLayout = FXMLLoader.load(getClass().getResource("../../views/personal/personalLayout.fxml"));
        Stage window = (Stage) proyectoLayoutBtn.getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Personal");
        window.setScene(new Scene(personalLayout, 500, 500));
    }

    public void showPersonalDetail(Personal personal) {
        this.personalSelected = personal;
        nombreLabel.setText(personal.getNombre());
        nifLabel.setText(personal.getNif());
        direccionLabel.setText(personal.getDireccion());

        buildProyectosAccordionTable(personal.getProyectos());
    }

    private void buildProyectosAccordionTable(List<Proyecto> proyectos) {
        TableColumn id = new TableColumn("ID");
        TableColumn nombre = new TableColumn("Nombre");
        TableColumn pais = new TableColumn("País");
        TableColumn localizacion = new TableColumn("Localización");
        TableColumn lineaAccion = new TableColumn("Linea de Acción");
        TableColumn subLineaAccion = new TableColumn("SubLinea de Acción");
        TableColumn fechaInicio = new TableColumn("Fecha de Inicio");
        TableColumn fechaFin = new TableColumn("Fecha de Fin");
        TableColumn socioLocal = new TableColumn("Socio Local");
        TableColumn financiador = new TableColumn("Financiador");
        TableColumn financiacionAportada = new TableColumn("Financiación Aportada");
        proyectosTablePanel.getColumns().addAll(id, nombre, pais, localizacion, lineaAccion, subLineaAccion, fechaInicio, fechaFin, socioLocal, financiador, financiacionAportada);

        ObservableList<Proyecto> proyectosData = FXCollections.observableArrayList(proyectos);

        id.setCellValueFactory(new PropertyValueFactory<Proyecto, Long>("id"));
        nombre.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("nombre"));
        pais.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("pais"));
        localizacion.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("localizacion"));
        lineaAccion.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("lineaAccion"));
        subLineaAccion.setCellValueFactory(new PropertyValueFactory<Proyecto, Boolean>("subLineaAccion"));
        fechaInicio.setCellValueFactory(new PropertyValueFactory<Proyecto, Date>("fechaInicio"));
        fechaFin.setCellValueFactory(new PropertyValueFactory<Proyecto, Date>("fechaFin"));
        socioLocal.setCellValueFactory(new PropertyValueFactory<Proyecto, Boolean>("socioLocal"));
        financiador.setCellValueFactory(new PropertyValueFactory<Proyecto, Boolean>("financiador"));
        financiacionAportada.setCellValueFactory(new PropertyValueFactory<Proyecto, Boolean>("financiacionAportada"));

        proyectosTablePanel.setItems(proyectosData);
    }

    @FXML
    void goToEditPersonalForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../views/personal/personalForm.fxml"));
        Parent delegacionFom = loader.load();

        PersonalFormController personalFormController = loader.getController();
        personalFormController.editPersonal(personalSelected);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Editar Empleado " + personalSelected.getId());
        window.setScene(new Scene(delegacionFom, 500, 500));
    }

    @FXML
    void deletePersonal(ActionEvent event) {
        ButtonType responseDialog = Dialog.confirmation("¿Desea eleminar definitivamente este empleado?");

        if (responseDialog == ButtonType.YES) {
            PersonalRepository.delete(personalSelected);
            try {
                goToPersonalLayout(null);
            } catch (IOException e) {
                Dialog.error(e.getMessage());
            }
        }
    }
}
