package com.P5.entities;

import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Table()
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String pais;

    @Column(nullable = false)
    private String localizacion;

    @Column(nullable = false)
    private String lineaAccion;

    @Column(nullable = false)
    private String subLineaAccion;

    @Column(nullable = false)
    private Date fechaInicio;

    @Column(nullable = false)
    private Date fechaFin;

    @Column(nullable = false)
    private String socioLocal;

    @Column(nullable = false)
    private String financiador;

    @Column(nullable = false)
    private String financiacionAportada;

    @ManyToMany(fetch=FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "proyecto_personal",
            joinColumns = @JoinColumn(name = "personal_id"),
            inverseJoinColumns = @JoinColumn(name = "proyecto_id")
    )
    private List<Personal> personalAsociado;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Delegacion delegacion;

    public Proyecto(String nombre, String pais, String localizacion, String lineaAccion, String subLineaAccion,
                    Date fechaInicio, Date fechaFin, String socioLocal, String financiador, String financiacionAportada,
                    List<Personal> personalAsociado, Delegacion delegacion) {
        this.nombre = nombre;
        this.pais = pais;
        this.localizacion = localizacion;
        this.lineaAccion = lineaAccion;
        this.subLineaAccion = subLineaAccion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.socioLocal = socioLocal;
        this.financiador = financiador;
        this.financiacionAportada = financiacionAportada;
        this.personalAsociado = personalAsociado;
        this.delegacion = delegacion;
    }

    public Proyecto() {

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

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getLineaAccion() {
        return lineaAccion;
    }

    public void setLineaAccion(String lineaAccion) {
        this.lineaAccion = lineaAccion;
    }

    public String getSubLineaAccion() {
        return subLineaAccion;
    }

    public void setSubLineaAccion(String subLineaAccion) {
        this.subLineaAccion = subLineaAccion;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getSocioLocal() {
        return socioLocal;
    }

    public void setSocioLocal(String socioLocal) {
        this.socioLocal = socioLocal;
    }

    public String getFinanciador() {
        return financiador;
    }

    public void setFinanciador(String financiador) {
        this.financiador = financiador;
    }

    public String getFinanciacionAportada() {
        return financiacionAportada;
    }

    public void setFinanciacionAportada(String financiacionAportada) {
        this.financiacionAportada = financiacionAportada;
    }

    public List<Personal> getPersonalAsociado() {
        return personalAsociado;
    }

    public void setPersonalAsociado(List<Personal> personalAsociado) {
        this.personalAsociado = personalAsociado;
    }

    public Delegacion getDelegacion() {
        return delegacion;
    }

    public void setDelegacion(Delegacion delegacion) {
        this.delegacion = delegacion;
    }
}
