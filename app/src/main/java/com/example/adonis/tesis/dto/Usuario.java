package com.example.adonis.tesis.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.UUID;

@Entity
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "usuario_id")
    private int usuarioId;

    private String nombre;
    private String clave;

    @ColumnInfo(name = "nombre_completo")
    private String nombreCompleto;

    private String especialidad;

    @ColumnInfo(name = "especialidad_personal")
    private String especialidadPersonal;

    private Integer cedula;

    /**
     * hombre true, mujer false
     */
    private boolean sexo;

    public String getEspecialidadPersonal() {
        return especialidadPersonal;
    }

    public void setEspecialidadPersonal(String especialidadPersonal) {
        this.especialidadPersonal = especialidadPersonal;
    }

    public boolean isSexo() {
        return sexo;
    }

    public void setSexo(boolean sexo) {
        this.sexo = sexo;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public Integer getCedula() {
        return cedula;
    }

    public void setCedula(Integer cedula) {
        this.cedula = cedula;
    }

    public Usuario(int usuarioId, String nombre, String clave) {
        this.usuarioId = usuarioId;
        this.nombre = nombre;
        this.clave = clave;
    }

    public Usuario(String nombre, String clave) {

        this.nombre = nombre;
        this.clave = clave;
    }

    public Usuario() {

    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
