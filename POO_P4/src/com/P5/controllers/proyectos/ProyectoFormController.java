package com.P5.controllers.proyectos;

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
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.hibernate.exception.ConstraintViolationException;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProyectoFormController implements Initializable {

    @FXML
    private Label formTitle;

    @FXML
    private Button proyectoLayoutBtn;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField paisField;

    @FXML
    private TextField localizacionField;

    @FXML
    private TextField lineaAccionField;

    @FXML
    private TextField subLineaAccionField;

    @FXML
    private DatePicker fechaInicioField;

    @FXML
    private DatePicker fechaFinField;

    @FXML
    private TextField socioLocalField;

    @FXML
    private TextField financiadorField;

    @FXML
    private TextField financiacionAportadaField;

    @FXML
    private ListView<Personal> personalAsociadoList;

    @FXML
    private ChoiceBox<Delegacion> selectDelegacionField;

    @FXML
    private Button saveBtn;

    private Proyecto proyecto;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Delegacion> delegaciones = DelegacionRepository.getAll();
        ObservableList<Delegacion> delegacionesOptions = FXCollections.observableArrayList(delegaciones);
        selectDelegacionField.setItems(delegacionesOptions);

        List<Personal> personal = PersonalRepository.getAll();
        ObservableList<Personal> personalOptions = FXCollections.observableArrayList(personal);
        personalAsociadoList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        personalAsociadoList.setItems(personalOptions);
    }

    @FXML
    void goToProyectoLayout(ActionEvent event) throws IOException {
        Parent delegacionesLayout = FXMLLoader.load(getClass().getResource("../../views/proyecto/proyectosLayout.fxml"));
        Stage window = (Stage) proyectoLayoutBtn.getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Proyectos");
        window.setScene(new Scene(delegacionesLayout, 500, 500));
    }

    @FXML
    void addNewProyecto(ActionEvent event) {
        String nombre = nombreField.getText();
        String pais = paisField.getText();
        String localizacion = localizacionField.getText();
        String lineaAccion = lineaAccionField.getText();
        String subLineaAccion = subLineaAccionField.getText();
        LocalDate fechaInicio = fechaInicioField.getValue();
        LocalDate fechaFin = fechaFinField.getValue();
        String socioLocal = socioLocalField.getText();
        String financiador = financiadorField.getText();
        String financiacionAportada = financiacionAportadaField.getText();
        List<Personal> personalAsociado = extractSelectedPersonal(personalAsociadoList.getSelectionModel().getSelectedItems());
        Delegacion delegacion = selectDelegacionField.getValue();

        if (!nombre.isEmpty() && !pais.isEmpty() && !localizacion.isEmpty() && !lineaAccion.isEmpty()
                && !subLineaAccion.isEmpty() && fechaInicio != null && fechaFin != null && !socioLocal.isEmpty()
                && !financiador.isEmpty() && !financiacionAportada.isEmpty() && delegacion != null) {

            Date fechaInicioEpochMilli = new Date(fechaInicio.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
            Date fechaFinEpochMilli = new Date(fechaFin.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());

            if (this.proyecto != null) {
                proyecto.setNombre(nombre);
                proyecto.setPais(pais);
                proyecto.setLocalizacion(localizacion);
                proyecto.setLineaAccion(lineaAccion);
                proyecto.setSubLineaAccion(subLineaAccion);
                proyecto.setFechaInicio(fechaInicioEpochMilli);
                proyecto.setFechaFin(fechaFinEpochMilli);
                proyecto.setSocioLocal(socioLocal);
                proyecto.setFinanciador(financiador);
                proyecto.setFinanciacionAportada(financiacionAportada);
                proyecto.setPersonalAsociado(personalAsociado);
                proyecto.setDelegacion(delegacion);
            } else {
                proyecto = new Proyecto(nombre, pais, localizacion, lineaAccion, subLineaAccion, fechaInicioEpochMilli,
                        fechaFinEpochMilli, socioLocal, financiador, financiacionAportada, personalAsociado, delegacion);
            }

            try {
                ProyectoRepository.save(proyecto);
                goToProyectoLayout(null);
            } catch (ConstraintViolationException cve) {
                Dialog.error(cve.getSQLException().getMessage());
            } catch (IOException e) {
                Dialog.error(e.getMessage());
            }
        } else {
            Dialog.error("Todos los campos son obligatorios.");
        }
    }

    private List<Personal> extractSelectedPersonal(ObservableList<Personal> selectedItems) {
        List<Personal> personal = new ArrayList<>();

        for (Personal p : selectedItems) {
            personal.add(p);
        }

        return personal;
    }

    public void editProyecto(Proyecto proyectoSelected) {
        this.proyecto = proyectoSelected;
        formTitle.setText("Editar Proyecto");

        nombreField.setText(proyectoSelected.getNombre());
        paisField.setText(proyectoSelected.getPais());
        localizacionField.setText(proyectoSelected.getLocalizacion());
        lineaAccionField.setText(proyectoSelected.getLineaAccion());
        subLineaAccionField.setText(proyectoSelected.getSubLineaAccion());
        fechaInicioField.setValue(LOCAL_DATE(proyectoSelected.getFechaInicio().toString()));
        fechaFinField.setValue(LOCAL_DATE(proyectoSelected.getFechaFin().toString()));
        socioLocalField.setText(proyectoSelected.getSocioLocal());
        financiadorField.setText(proyectoSelected.getFinanciador());
        financiacionAportadaField.setText(proyectoSelected.getFinanciacionAportada());
        selectDelegacionField.setValue(proyectoSelected.getDelegacion());

        for (Personal p : proyectoSelected.getPersonalAsociado()) {
            personalAsociadoList.getSelectionModel().select(p);
            for (int i = 0; i < personalAsociadoList.getItems().size(); i++) {
                Personal pList = personalAsociadoList.getItems().get(i);
                if (pList.getId().equals(p.getId())) {
                    personalAsociadoList.getSelectionModel().select(i);
                }
            }
        }
    }

    private LocalDate LOCAL_DATE(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(dateString, formatter);
    }
}
