package com.P5.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class IndexController {

    @FXML
    private Button delegacionLayoutBtn;

    @FXML
    private Button proyectoLayoutBtn;

    @FXML
    private Button personalLayoutBtn;

    @FXML
    public void goToDelegacionesLayout(ActionEvent actionEvent) throws IOException {
        Parent proyectosLayout = FXMLLoader.load(getClass().getResource("../views/delegacion/delegacionesLayout.fxml"));
        Stage window = (Stage) delegacionLayoutBtn.getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Proyectos");
        window.setScene(new Scene(proyectosLayout, 500, 500));
    }

    @FXML
    void goToProyectoLayout(ActionEvent event) throws IOException {
        Parent proyectosLayout = FXMLLoader.load(getClass().getResource("../views/proyecto/proyectosLayout.fxml"));
        Stage window = (Stage) proyectoLayoutBtn.getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Proyectos");
        window.setScene(new Scene(proyectosLayout, 500, 500));
    }

    @FXML
    void goToPersonalLayout(ActionEvent event) throws IOException {
        Parent proyectosLayout = FXMLLoader.load(getClass().getResource("../views/personal/personalLayout.fxml"));
        Stage window = (Stage) personalLayoutBtn.getScene().getWindow();
        window.setTitle("ONG Entre Culturas - Personal");
        window.setScene(new Scene(proyectosLayout, 500, 500));
    }
}
