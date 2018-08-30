package com.example.adonis.tesis.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.repository.PacienteRepository;

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

    public void insertPaciente(Paciente paciente) {
        pacienteRepository.insertPaciente(paciente);
    }


}
