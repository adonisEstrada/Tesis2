package com.example.adonis.tesis.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

//@Entity(foreignKeys = @ForeignKey(entity = Interconsulta.class,
//        parentColumns = "interconsulta_id",
//        childColumns = "interconsulta_id"))
@Entity
public class SignoVital {

    @PrimaryKey()
//    @NonNull
    @ColumnInfo(name = "signo_vital_id")
    private int signoVitalId;
    //    @ColumnInfo(name = "interconsulta_id")
//    private int interconsulta;
    private Integer peso;
    private Integer diatolica;
    private Integer sistolica;
    private Float temperatura;
    /**
     * tipoTemperatura, 0 para centígrados, 1 para Fahrenheit
     */
    private Integer tipoTemperatura;
    @ColumnInfo(name = "frecuancia_cardiaca")
    private int frecuenciaCardiaca;

    @ColumnInfo(name = "frecuencia_respiratoria")
    private int fecuenciaRespiratoria;

    public int getFecuenciaRespiratoria() {
        return fecuenciaRespiratoria;
    }

    public void setFecuenciaRespiratoria(int fecuenciaRespiratoria) {
        this.fecuenciaRespiratoria = fecuenciaRespiratoria;
    }

    public Integer getPeso() {
        return peso;
    }

    public void setPeso(Integer peso) {
        this.peso = peso;
    }

    public Integer getDiatolica() {
        return diatolica;
    }

    public void setDiatolica(Integer diatolica) {
        this.diatolica = diatolica;
    }

    public Integer getSistolica() {
        return sistolica;
    }

    public void setSistolica(Integer sistolica) {
        this.sistolica = sistolica;
    }

    public Float getTemperatura() {
        return temperatura;
    }

    public void setTemperatura(Float temperatura) {
        this.temperatura = temperatura;
    }

    public Integer getTipoTemperatura() {
        return tipoTemperatura;
    }

    public void setTipoTemperatura(Integer tipoTemperatura) {
        this.tipoTemperatura = tipoTemperatura;
    }

    public SignoVital() {
    }

    public int getSignoVitalId() {

        return signoVitalId;
    }

    public void setSignoVitalId(int signoVitalId) {
        this.signoVitalId = signoVitalId;
    }

    public int getFrecuenciaCardiaca() {
        return frecuenciaCardiaca;
    }

    public void setFrecuenciaCardiaca(int frecuenciaCardiaca) {
        this.frecuenciaCardiaca = frecuenciaCardiaca;
    }

}
