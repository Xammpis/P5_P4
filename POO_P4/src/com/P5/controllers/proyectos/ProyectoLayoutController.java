package com.P5.controllers.proyectos;

import com.P5.controllers.delegacion.DelegacionDetailController;
import com.P5.entities.Delegacion;
import com.P5.entities.Proyecto;
import com.P5.repositories.ProyectoRepository;
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
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ProyectoLayoutController implements Initializable {

    @FXML
    private Button homeBtn;

    @FXML
    private Button addProyectoBtn;

    @FXML
    private TableView<Proyecto> proyectosList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Proyecto> proyectos = ProyectoRepository.getAll();

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
        TableColumn delegacion = new TableColumn("Delegacion ID");
        proyectosList.getColumns().addAll(id, nombre, pais, localizacion, lineaAccion, subLineaAccion, fechaInicio, fechaFin, socioLocal, financiador, financiacionAportada, delegacion);

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
        delegacion.setCellValueFactory(new PropertyValueFactory<Proyecto, Delegacion>("delegacion"));

        proyectosList.setItems(proyectosData);

        proyectosList.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    System.out.println(proyectosList.getSelectionModel().getSelectedItem());
                    try {
                        goToProyectoDetail(event, proyectosList.getSelectionModel().getSelectedItem());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void goToProyectoDetail(MouseEvent event, Proyecto proyectoSeleted) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../../views/proyecto/proyectoDetail.fxml"));
        Parent delegacionDetail = loader.load();

        ProyectoDetailController proyectoDetailController = loader.getController();
        proyectoDetailController.showProyectoDetail(proyectoSeleted);

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Proyecto " + proyectoSeleted.getId());
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
    void goToAddProyecto(ActionEvent event) throws IOException {
        Parent index = FXMLLoader.load(getClass().getResource("../../views/proyecto/proyectoForm.fxml"));
        Stage window = (Stage) homeBtn.getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Nuevo Proyecto");
        window.setScene(new Scene(index, 500, 500));
    }
}
