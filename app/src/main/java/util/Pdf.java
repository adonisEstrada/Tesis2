package util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;

import com.example.adonis.tesis.R;
import com.example.adonis.tesis.dto.Consulta;
import com.example.adonis.tesis.dto.Interconsulta;
import com.example.adonis.tesis.dto.Paciente;
import com.example.adonis.tesis.dto.SignoVital;
import com.example.adonis.tesis.dto.Usuario;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.HeaderFooter;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.ColumnText;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Pdf {
    private final static String NOMBRE_DIRECTORIO = "Reportes";
    private final static String NOMBRE_DOCUMENTO = new SimpleDateFormat("dd-MM-yy-HH-mm-ss").format(new Date()) + "-listado.pdf";
    private final static String NOMBRE_DOCUMENTO_INFORME = new SimpleDateFormat("dd-MM-yy-HH-mm-ss").format(new Date()) + "-informe-medico.pdf";
    private final static String ETIQUETA_ERROR = "ERROR";

    public void generarPdf(Context context, List<Paciente> pacientes) {

        // Creamos el documento.
        Document documento = new Document();

        try {

            File f = crearFichero(NOMBRE_DOCUMENTO);

            // Creamos el flujo de datos de salida para el fichero donde
            // guardaremos el pdf.
            FileOutputStream ficheroPdf = new FileOutputStream(
                    f.getAbsolutePath());

            // Asociamos el flujo que acabamos de crear al documento.
            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

            // Incluimos el pie de pagina y una cabecera
//            HeaderFooter cabecera = new HeaderFooter(new Phrase(
//            ), false);
            HeaderFooter pie = new HeaderFooter(new Phrase(
                    new Date().toString()), false);

            documento.setFooter(pie);

            // Abrimos el documento.
            documento.open();

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.logo_top);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.scaleToFit(400, 100);
            documento.add(imagen);
            // Añadimos un titulo con la fuente por defecto.
            Usuario usuario = SessionSettings.getUsuarioIniciado();

            Paragraph paragraph = new Paragraph("Pacientes del "
                    + (usuario.isSexo() ? "Dr. " : "Dra. ")
                    + usuario.getNombreCompleto());
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(25);
            documento.add(paragraph);
            Paragraph paragraph2 = new Paragraph("Pacientes en Atención");
            paragraph2.setAlignment(Element.ALIGN_CENTER);
            paragraph2.setSpacingAfter(25);
            documento.add(paragraph2);

//            Font font = FontFactory.getFont(FontFactory.HELVETICA, 28,
//                    Font.BOLD, Color.RED);
//            documento.add(new Paragraph("Titulo personalizado"));
            // Insertamos una tabla.
            PdfPTable headerTabla1 = new PdfPTable(4);
            headerTabla1.getDefaultCell().setBackgroundColor(new harmony.java.awt.Color(159, 222, 190));
            headerTabla1.addCell("Paciente");
            headerTabla1.addCell("Cédula");
            headerTabla1.addCell("Edad");
            headerTabla1.addCell("Fecha ingreso");


            PdfPTable tableNoAtendidos = new PdfPTable(4);
            tableNoAtendidos.setHorizontalAlignment(Element.ALIGN_CENTER);
            tableNoAtendidos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_MIDDLE);

            PdfPTable headerTabla2 = new PdfPTable(5);
            headerTabla2.getDefaultCell().setBackgroundColor(new harmony.java.awt.Color(159, 222, 190));
            headerTabla2.addCell("Paciente");
            headerTabla2.addCell("Cédula");
            headerTabla2.addCell("Edad");
            headerTabla2.addCell("Fecha ingreso");
            headerTabla2.addCell("Fecha Atendido");

            PdfPTable tablaAtendidos = new PdfPTable(5);
            tablaAtendidos.getDefaultCell().setHorizontalAlignment(Element.ALIGN_MIDDLE);

            for (Paciente paciente : pacientes) {
                if (paciente.isActivo()) {
                    tableNoAtendidos.addCell(paciente.getNombre() + " " + paciente.getApellido());
                    tableNoAtendidos.addCell(paciente.getCedula());
                    tableNoAtendidos.addCell(Converters.getEdad(paciente.getFechaIngreso()));
                    tableNoAtendidos.addCell(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa").format(paciente.getFecha()));
                } else {
                    tablaAtendidos.addCell(paciente.getNombre() + " " + paciente.getApellido());
                    tablaAtendidos.addCell(paciente.getCedula());
                    tablaAtendidos.addCell(Converters.getEdad(paciente.getFechaIngreso()));
                    tablaAtendidos.addCell(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa").format(paciente.getFecha()));
                    tablaAtendidos.addCell(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa").format(paciente.getFechaAtendido()));
                }
            }
            documento.add(headerTabla1);
            documento.add(tableNoAtendidos);

            Paragraph paragraph3 = new Paragraph("Pacientes ya atendidos");
            paragraph3.setAlignment(Element.ALIGN_CENTER);
            paragraph3.setSpacingAfter(25);
            paragraph3.setSpacingBefore(25);
            documento.add(paragraph3);

            documento.add(headerTabla2);
            documento.add(tablaAtendidos);

            // Agregar marca de agua
//            font = FontFactory.getFont(FontFactory.HELVETICA, 42, Font.BOLD,
//                    Color.GRAY);
//            ColumnText.showTextAligned(writer.getDirectContentUnder(),
//                    Element.ALIGN_CENTER, new Paragraph(
//                            "androfast.com"), 297.5f, 421,
//                    writer.getPageNumber() % 2 == 1 ? 45 : -45);

        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } finally {
            // Cerramos el documento.
            documento.close();
        }
    }

    public void generarPdf(Context context, Consulta consulta, Paciente paciente, SignoVital signoVital) {
        Document documento = new Document();

        try {

            File f = crearFichero(NOMBRE_DOCUMENTO_INFORME);

            FileOutputStream ficheroPdf = new FileOutputStream(
                    f.getAbsolutePath());

            PdfWriter writer = PdfWriter.getInstance(documento, ficheroPdf);

            HeaderFooter pie = new HeaderFooter(new Phrase(
                    new Date().toString()), true);

            documento.setFooter(pie);

            // Abrimos el documento.
            documento.open();

            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.logo_top);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            Image imagen = Image.getInstance(stream.toByteArray());
            imagen.scaleToFit(400, 100);
            documento.add(imagen);
            // Añadimos un titulo con la fuente por defecto.
            Usuario usuario = SessionSettings.getUsuarioIniciado();

            Paragraph paragraph = new Paragraph("Informe Médico del "
                    + (usuario.isSexo() ? "Dr. " : "Dra. ")
                    + usuario.getNombreCompleto());
            paragraph.setAlignment(Element.ALIGN_CENTER);
            paragraph.setSpacingAfter(25);
            documento.add(paragraph);

            PdfPTable headerTabla1 = new PdfPTable(4);
            headerTabla1.getDefaultCell().setBackgroundColor(new harmony.java.awt.Color(159, 222, 190));
            headerTabla1.addCell("Paciente");
            headerTabla1.addCell("Cédula");
            headerTabla1.addCell("Edad");
            headerTabla1.addCell("Fecha ingreso");
            documento.add(headerTabla1);

            PdfPTable informacionPaciente = new PdfPTable(4);
            informacionPaciente.addCell(paciente.getNombre() + " " + paciente.getApellido());
            informacionPaciente.addCell(paciente.getCedula());
            informacionPaciente.addCell(Converters.getEdad(paciente.getFechaIngreso()));
            informacionPaciente.addCell(new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa").format(paciente.getFecha()));
            documento.add(informacionPaciente);

            if (signoVital != null) {
                PdfPTable headerSignos = new PdfPTable(1);
                headerSignos.getDefaultCell().setBackgroundColor(new harmony.java.awt.Color(159, 222, 190));
                headerSignos.getDefaultCell().setHorizontalAlignment(PdfPCell.ALIGN_CENTER);
                headerSignos.addCell("SignosVitales");
                documento.add(headerSignos);
                PdfPTable tableSignosVitales = new PdfPTable(5);
                PdfPCell cellFrecuenciaCardiaca = new PdfPCell();
                PdfPCell cellTemperatura = new PdfPCell();
                PdfPCell cellPresionArterialDiastolica = new PdfPCell();
                PdfPCell cellPresionArterialSistolica = new PdfPCell();
                PdfPCell cellFrecuenciaRespiratoria = new PdfPCell();
                PdfPCell asdasd = new PdfPCell();
                int sistole = signoVital.getSistolica();
                int diastole = signoVital.getDiatolica();
                float temperatura = signoVital.getTemperatura();
                int tipoTemperatura = signoVital.getTipoTemperatura();
                int fc = signoVital.getFrecuenciaCardiaca();
                int fr = signoVital.getFecuenciaRespiratoria();
                harmony.java.awt.Color colorRojo = new harmony.java.awt.Color(255, 191, 191);
                harmony.java.awt.Color colorAzul = new harmony.java.awt.Color(191, 195, 255);
                harmony.java.awt.Color colorGris = new harmony.java.awt.Color(223, 244, 233);

                if (sistole > 130) {
                    cellPresionArterialSistolica.setBackgroundColor(colorRojo);
                } else if (sistole < 100) {
                    cellPresionArterialSistolica.setBackgroundColor(colorAzul);
                } else {
                    cellPresionArterialSistolica.setBackgroundColor(colorGris);
                }
                if (diastole > 96) {
                    cellPresionArterialDiastolica.setBackgroundColor(colorRojo);
                } else if (diastole < 70) {
                    cellPresionArterialDiastolica.setBackgroundColor(colorAzul);
                } else {
                    cellPresionArterialDiastolica.setBackgroundColor(colorGris);
                }
                if ((temperatura > 37 && tipoTemperatura == 0)
                        || (temperatura > 98.6 && tipoTemperatura == 1)) {
                    cellTemperatura.setBackgroundColor(colorRojo);
                } else if ((temperatura < 36 && tipoTemperatura == 0)
                        || (temperatura < 96.8 && tipoTemperatura == 1)) {
                    cellTemperatura.setBackgroundColor(colorAzul);
                } else {
                    cellTemperatura.setBackgroundColor(colorGris);
                }
                if (fc > 100) {
                    cellFrecuenciaCardiaca.setBackgroundColor(colorRojo);
                } else if (fc < 60) {
                    cellFrecuenciaCardiaca.setBackgroundColor(colorAzul);
                } else {
                    cellFrecuenciaCardiaca.setBackgroundColor(colorGris);
                }
                if (fr > 12) {
                    cellFrecuenciaRespiratoria.setBackgroundColor(colorRojo);
                } else if (fr < 19) {
                    cellFrecuenciaRespiratoria.setBackgroundColor(colorAzul);
                } else {
                    cellFrecuenciaRespiratoria.setBackgroundColor(colorGris);
                }
                cellFrecuenciaRespiratoria.setPhrase(new Phrase("FR: " + fr));
                cellFrecuenciaCardiaca.setPhrase(new Phrase("FC: " + fc));
                cellTemperatura.setPhrase(new Phrase(
                        "TEMP: " + String.valueOf(temperatura) + (tipoTemperatura == 0 ? " °C" : " °F")));
                cellPresionArterialDiastolica.setPhrase(new Phrase("DIA: " + diastole));
                cellPresionArterialSistolica.setPhrase(new Phrase("SIS: " + sistole));

                tableSignosVitales.addCell(cellFrecuenciaCardiaca);
                tableSignosVitales.addCell(cellFrecuenciaRespiratoria);
                tableSignosVitales.addCell(cellPresionArterialDiastolica);
                tableSignosVitales.addCell(cellPresionArterialSistolica);
                tableSignosVitales.addCell(cellTemperatura);
                documento.add(tableSignosVitales);
            }

            PdfPTable informacionConsulta = new PdfPTable(1);
            informacionConsulta.addCell(consulta.getInforme());
            documento.add(informacionConsulta);

        } catch (DocumentException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } catch (IOException e) {

            Log.e(ETIQUETA_ERROR, e.getMessage());

        } finally {
            // Cerramos el documento.
            documento.close();
        }
    }

    public static File crearFichero(String nombreFichero) throws IOException {
        File ruta = getRuta();
        File fichero = null;
        if (ruta != null) {
            fichero = new File(ruta, nombreFichero);
        }
        return fichero;
    }

    /**
     * Obtenemos la ruta donde vamos a almacenar el fichero.
     *
     * @return
     */
    public static File getRuta() {

        // El fichero sera almacenado en un directorio dentro del directorio
        // Descargas
        File ruta = null;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            ruta = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    NOMBRE_DIRECTORIO);

            if (ruta != null) {
                if (!ruta.mkdirs()) {
                    if (!ruta.exists()) {
                        return null;
                    }
                }
            }
        }
        return ruta;
    }


}
