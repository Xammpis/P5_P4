package com.P5.controllers.delegacion;

import com.P5.entities.Delegacion;
import com.P5.entities.Personal;
import com.P5.entities.Proyecto;
import com.P5.repositories.DelegacionRepository;
import com.P5.repositories.PersonalRepository;
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
import java.sql.Date;
import java.util.List;
import java.util.Set;

public class DelegacionDetailController {

    @FXML
    private Button delegacionLayoutBtn;

    @FXML
    private Label idLabel;

    @FXML
    private Label direccionLabel;

    @FXML
    private Label ciudadLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label telefonoLabel;

    @FXML
    private Label centralLabel;

    @FXML
    private TableView<Proyecto> proyectosTablePanel;

    @FXML
    private TableView<Personal> personalTablePanel;

    @FXML
    private Button editBtn;

    @FXML
    private Button deleteBtn;

    private Delegacion delegacionSeleted;

    @FXML
    void goToDelegacionLayout(ActionEvent event) throws IOException {
        Parent delegacionesLayout = FXMLLoader.load(getClass().getResource("../../views/delegacion/delegacionesLayout.fxml"));
        Stage window = (Stage) delegacionLayoutBtn.getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Delegaciones");
        window.setScene(new Scene(delegacionesLayout, 500, 500));
    }

    public void showDelegacionDetail(Delegacion delegacionSeleted) {
        this.delegacionSeleted = delegacionSeleted;
        idLabel.setText(delegacionSeleted.getId().toString());
        direccionLabel.setText(delegacionSeleted.getDireccion());
        ciudadLabel.setText(delegacionSeleted.getCiudad());
        emailLabel.setText(delegacionSeleted.getEmail());
        telefonoLabel.setText(delegacionSeleted.getTelefono());
        centralLabel.setText(delegacionSeleted.getCentral() ? "Si" : "No");


        buildProyectosAccordionTable(delegacionSeleted);
        buildPersonalAccordionTable(delegacionSeleted);
    }

    private void buildProyectosAccordionTable(Delegacion delegacionSeleted) {
        Set<Proyecto> proyectos = delegacionSeleted.getProyectos();

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

    private void buildPersonalAccordionTable(Delegacion delegacionSeleted) {
        Set<Personal> personal = delegacionSeleted.getPersonal();

        TableColumn id = new TableColumn("ID");
        TableColumn nombre = new TableColumn("Nombre");
        TableColumn nif = new TableColumn("NIF");
        TableColumn direccion = new TableColumn("Dirección");
        personalTablePanel.getColumns().addAll(id, nombre, nif, direccion);

        ObservableList<Personal> personalData = FXCollections.observableArrayList(personal);

        id.setCellValueFactory(new PropertyValueFactory<Proyecto, Long>("id"));
        nombre.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("nombre"));
        nif.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("nif"));
        direccion.setCellValueFactory(new PropertyValueFactory<Proyecto, String>("direccion"));

        personalTablePanel.setItems(personalData);
    }

    @FXML
    void goToEditDelegacionForm(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../views/delegacion/delegacionForm.fxml"));
        Parent delegacionFom = loader.load();

        DelegacionFormController delegacionFormController = loader.getController();
        delegacionFormController.editDelegacion(delegacionSeleted);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Editar Delegacion " + delegacionSeleted.getId());
        window.setScene(new Scene(delegacionFom, 500, 500));
    }

    @FXML
    void deleteDelegacion(ActionEvent event) {
        ButtonType responseDialog = Dialog.confirmation("¿Desea eleminar definitivamente esta delegación?");

        if (responseDialog == ButtonType.YES) {
            DelegacionRepository.delete(delegacionSeleted);
            try {
                goToDelegacionLayout(null);
            } catch (IOException e) {
                Dialog.error(e.getMessage());
            }
        }
    }
}
