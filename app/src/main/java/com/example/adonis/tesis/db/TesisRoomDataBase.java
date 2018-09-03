package com.example.adonis.tesis.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.adonis.tesis.dao.ConsultaDao;
import com.example.adonis.tesis.dao.InterconsultaDao;
import com.example.adonis.tesis.dao.PacienteDao;
import com.example.adonis.tesis.dao.SignoVitalDao;
import com.example.adonis.tesis.dao.UsuarioDao;
import com.example.adonis.tesis.dto.Consulta;
import com.example.adonis.tesis.dto.Interconsulta;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.dto.SignoVital;
import com.example.adonis.tesis.dto.Usuario;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

import util.Constantes;
import util.Converters;

@Database(entities = {Usuario.class,
        Paciente.class,
        Interconsulta.class,
        Consulta.class,
        SignoVital.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class TesisRoomDataBase extends RoomDatabase {

    public abstract UsuarioDao usuarioDao();

    public abstract PacienteDao pacienteDao();

    public abstract InterconsultaDao interconsultaDao();

//    public abstract ConsultaDao consultaDao();

//    public abstract SignoVitalDao signoVitalDao();

    private static TesisRoomDataBase INSTANCE;

    public static TesisRoomDataBase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TesisRoomDataBase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TesisRoomDataBase.class, "tesis").addCallback(roomDataBaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback roomDataBaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
            new PopulateRoomDatabaseAsync(INSTANCE).execute();
        }
    };

    private static class PopulateRoomDatabaseAsync extends AsyncTask<Void, Void, Void> {

        private final UsuarioDao usuarioDao;
        private final PacienteDao pacienteDao;
        private final InterconsultaDao interconsultaDao;
//        private final ConsultaDao consultaDao;
//        private final SignoVitalDao signoVitalDao;

        PopulateRoomDatabaseAsync(TesisRoomDataBase tesisRoomDataBase) {
            this.usuarioDao = tesisRoomDataBase.usuarioDao();
            this.pacienteDao = tesisRoomDataBase.pacienteDao();
            this.interconsultaDao = tesisRoomDataBase.interconsultaDao();
//            this.consultaDao = tesisRoomDataBase.consultaDao();
//            this.signoVitalDao = tesisRoomDataBase.signoVitalDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Usuario usuario = new Usuario();
            usuario.setUsuarioId(1);
            usuario.setNombre("admin");
            usuario.setClave("123");
            usuario.setNombreCompleto("Adonis de Jesús Estrada Vallejo");
            usuario.setEspecialidadPersonal("Otorrinolaringologo");
            usuario.setEspecialidad("Otorrinolaringología");
            usuario.setCedula(22246767);
            usuario.setSexo(true);
            usuarioDao.insert(usuario);

            Paciente paciente = new Paciente();
            paciente.setPacienteId(1);
            paciente.setActivo(true);
            paciente.setNombre("Elber");
            paciente.setApellido("Gonzales");
            paciente.setCedula("8269873");
//            paciente.setEdad("48 años");
            paciente.setUsuario(1);
            paciente.setFecha(new Date());
            paciente.setSexo(true);
            Calendar edadPaciente1 = Calendar.getInstance();
            edadPaciente1.set(1965, 9, 25);
            paciente.setFechaIngreso(edadPaciente1.getTime());
            pacienteDao.insert(paciente);

            Paciente paciente2 = new Paciente();
            paciente2.setPacienteId(2);
            paciente2.setActivo(true);
            paciente2.setNombre("Alberto");
            paciente2.setApellido("Mates");
            paciente2.setCedula("8345673");
            paciente2.setUsuario(1);
            paciente2.setFecha(new Date());
            paciente2.setSexo(true);
            Calendar edadPaciente2 = Calendar.getInstance();
            edadPaciente2.set(1970, 8, 30);
            paciente2.setFechaIngreso(edadPaciente2.getTime());
//            paciente2.setEdad("39 años");
            pacienteDao.insert(paciente2);

            Interconsulta interconsulta = new Interconsulta();
            interconsulta.setInterconsultaId(1);
            interconsulta.setDescripcion("Información de consulta");
            interconsulta.setPaciente(1);
            interconsulta.setFecha(new Date());
            interconsulta.setActivo(true);
            interconsulta.setTipoInterconsulta(Constantes.TIPO_INTERCONSULTA_CONSULTA);

            Consulta consulta = new Consulta();
            consulta.setConsultaId(1);
            consulta.setInforme("Paciente varon de 86 años " +
                    "con antecedentes de TBC pulmonar hace 5 años con " +
                    "tratamiento completo, refiere SAT no cuantificada, " +
                    "desde hace 2 semanas, que cede parcialmente a medio físicos, " +
                    "asi como tos esporadica productiva, baja peso, constipacion " +
                    "(que cede a manejo con enemas), y sintomatologia " +
                    "urinaria(dificultad para iniciar la miccion, poliaquiuria)." +
                    " Familiar refiere paciente, presenta aumento de sed en" +
                    " comparacion a meses anteriores.");
            interconsulta.setConsulta(consulta);
            interconsultaDao.insert(interconsulta);

//            consultaDao.insert(consulta);


//            Interconsulta interconsulta2 = new Interconsulta();
//            interconsulta2.setInterconsultaId(2);
//            interconsulta2.setDescripcion("Signos vitales");
//            interconsulta2.setPaciente(1);
//            interconsulta2.setActivo(true);
//            interconsulta2.setFecha(new Date());
//            interconsulta2.setTipoInterconsulta(Constantes.TIPO_INTERCONSULTA_SIGNO_VITAL);
//            interconsultaDao.insert(interconsulta2);
//            SignoVital signoVital = new SignoVital();

            return null;
        }
    }
}
