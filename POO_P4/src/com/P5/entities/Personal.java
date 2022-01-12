package com.P5.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table()
public class Personal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String nif;

    @Column(nullable = false)
    private String direccion;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Delegacion delegacion;

    @ManyToMany(fetch=FetchType.EAGER, mappedBy = "personalAsociado")
    private List<Proyecto> proyectos;

    public Personal(String nombre, String nif, String direccion, Delegacion delegacion) {
        this.nombre = nombre;
        this.nif = nif;
        this.direccion = direccion;
        this.delegacion = delegacion;
    }

    public Personal() {

    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Delegacion getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(Delegacion delegacion) {
        this.delegacion = delegacion;
    }

    public List<Proyecto> getProyectos() {
        return proyectos;
    }

    public void setProyectos(List<Proyecto> proyectos) {
        this.proyectos = proyectos;
    }

    @Override
    public String toString() {
        return "Personal{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nif='" + nif + '\'' +
                ", direccion='" + direccion + '\'' +
                ", delegacion=" + delegacion +
                '}';
    }
}
