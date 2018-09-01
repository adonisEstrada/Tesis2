package com.example.adonis.tesis.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity(foreignKeys = @ForeignKey(entity = Usuario.class,
        parentColumns = "usuario_id",
        childColumns = "usuario_id"))
public class Paciente {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "paciente_id")
    private int pacienteId;
    private String nombre;
    private String apellido;
    private String cedula;
    private boolean sexo;
    @ColumnInfo(name = "fecha_ingreso")
    private Date fechaIngreso;
    @ColumnInfo(name = "usuario_id")
    private int usuario;
    private boolean activo;

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

//    public String getEdad() {
//        return edad;
//    }
//
//    public void setEdad(String edad) {
//        this.edad = edad;
//    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }


    public Paciente() {
    }

//    private List<Interconsulta> interconsultas;

//    public Paciente(int pacienteId, String nombre, String apellido, String cedula, String cama, String descripcionAtencion, List<Interconsulta> interconsultas) {
//        this.pacienteId = pacienteId;
//        this.nombre = nombre;
//        this.apellido = apellido;
//        this.cedula = cedula;
//        this.cama = cama;
//        this.descripcionAtencion = descripcionAtencion;
////        this.interconsultas = interconsultas;
//    }

//    public List<Interconsulta> getInterconsultas() {
//        return interconsultas;
//    }

//    public void setInterconsultas(List<Interconsulta> interconsultas) {
//        this.interconsultas = interconsultas;
//    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

//    public String getCama() {
//        return cama;
//    }
//
//    public void setCama(String cama) {
//        this.cama = cama;
//    }
//
//    public String getDescripcionAtencion() {
//        return descripcionAtencion;
//    }
//
//    public void setDescripcionAtencion(String descripcionAtencion) {
//        this.descripcionAtencion = descripcionAtencion;
//    }
}
