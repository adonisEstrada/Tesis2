package util;

import com.example.adonis.tesis.dto.Usuario;

public class SessionSettings {
    private static Usuario usuarioIniciado;

    public static Usuario getUsuarioIniciado() {
        return usuarioIniciado;
    }

    public static void setUsuarioIniciado(Usuario usuarioIniciado) {
        SessionSettings.usuarioIniciado = usuarioIniciado;
    }
}
