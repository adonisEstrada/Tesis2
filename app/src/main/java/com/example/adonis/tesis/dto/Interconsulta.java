package com.example.adonis.tesis.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(foreignKeys = @ForeignKey(entity = Paciente.class,
        parentColumns = "paciente_id",
        childColumns = "paciente"))
public class Interconsulta {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "interconsulta_id")
    private int interconsultaId;
    private int paciente;
    private String descripcion;
    @ColumnInfo(name = "tipo_interconsulta")
    private int tipoInterconsulta;
    private Date fecha;
    private boolean activo;

    @Embedded
    private Consulta consulta;

    @Embedded
    private SignoVital signoVital;


    public Consulta getConsulta() {
        return consulta;
    }

    public void setConsulta(Consulta consulta) {
        this.consulta = consulta;
    }

    public SignoVital getSignoVital() {
        return signoVital;
    }

    public void setSignoVital(SignoVital signoVital) {
        this.signoVital = signoVital;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getTipoInterconsulta() {
        return tipoInterconsulta;
    }

    public void setTipoInterconsulta(int tipoInterconsulta) {
        this.tipoInterconsulta = tipoInterconsulta;
    }


    public Interconsulta() {
    }

    public Interconsulta(int interconsultaId, int paciente, String descripcion) {
        this.interconsultaId = interconsultaId;
        this.paciente = paciente;
//        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public int getInterconsultaId() {

        return interconsultaId;
    }

    public void setInterconsultaId(int interconsultaId) {
        this.interconsultaId = interconsultaId;
    }

    public int getPaciente() {
        return paciente;
    }

    public void setPaciente(int paciente) {
        this.paciente = paciente;
    }

//    public Date getFecha() {
//        return fecha;
//    }

//    public void setFecha(Date fecha) {
//        this.fecha = fecha;
//    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
