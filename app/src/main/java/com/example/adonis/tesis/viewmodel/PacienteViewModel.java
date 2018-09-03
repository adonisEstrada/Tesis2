package com.example.adonis.tesis.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.repository.PacienteRepository;

import java.util.Date;
import java.util.List;

public class PacienteViewModel extends AndroidViewModel {

    PacienteRepository pacienteRepository;

    public PacienteViewModel(@NonNull Application application) {
        super(application);
        pacienteRepository = new PacienteRepository(application);
    }

    public LiveData<List<Paciente>> getPacientes(int usuario) {
        return pacienteRepository.getPacientes(usuario);
    }

    public LiveData<Paciente> getPaciente(int paciente) {
        return pacienteRepository.getPaciente(paciente);
    }

    public LiveData<List<Paciente>> getPacientes(Date fechaDesde, Date fechaHasta) {
        return pacienteRepository.getPacientes(fechaDesde, fechaHasta);
    }

    /**
     * busca paciente por nombre, apellido y cedula
     *
     * @param search String a buscar
     * @return lista de pacientes
     */
    public LiveData<List<Paciente>> getPaciente(String search) {
        return pacienteRepository.getPacientes(search);
    }

    public void insertPaciente(Paciente paciente) {
        pacienteRepository.insertPaciente(paciente);
    }


}
