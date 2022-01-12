package com.P5.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table()
public class Delegacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, columnDefinition = "tinyint(1) default 0")
    private Boolean central;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "delegacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Proyecto> proyectos;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "delegacion", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Personal> personal;

    public Delegacion(String ciudad, String direccion, String telefono, String email, Boolean central) {
        this.ciudad = ciudad;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
        this.central = central;
    }

    public Delegacion() {

    }

    public Long getId() {
        return id;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getCentral() {
        return central;
    }

    public void setCentral(Boolean central) {
        this.central = central;
    }

    public Set<Proyecto> getProyectos() {
        return proyectos;
    }

    public Set<Personal> getPersonal() {
        return personal;
    }

    @Override
    public String toString() {
        return "Delegacion{" +
                "id=" + id +
                ", ciudad='" + ciudad + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email + '\'' +
                ", central=" + central +
                '}';
    }

}
