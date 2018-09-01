package com.example.adonis.lib;


import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JasperUtil {
    public static String generarReposte() {
        // Compile jrxml file.
        JasperReport jasperReport;
        try {
            jasperReport = JasperCompileManager
                    .compileReport(JasperUtil.class.getResource("/report/fomerca_report.jrxml").getFile());
//                    .compileReport(JasperUtil.class.getResource("/report/fomerca_report.jrxml").getFile());

            // Parameters for report
            Map<String, Object> parameters = new HashMap<String, Object>();
//            parameters.put("IMAGE", JasperUtil.class.getResource("/resource/vidrio.jpg").getFile());
            // DataSource
            // This is simple example, no database.
            // then using empty datasource.
            JRDataSource dataSource = new JRBeanCollectionDataSource(new ArrayList<Object>());

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    parameters, dataSource);

            // Make sure the output directory exists.
            File outDir = new File("/Reportes");
            outDir.mkdirs();

            // Export to PDF.
            String nombreArchivo = "/reporte" + UUID.randomUUID().toString() + ".pdf";
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    outDir.getAbsolutePath() + nombreArchivo);
            return outDir.getAbsolutePath() + nombreArchivo;
        } catch (JRException ex) {
            ex.printStackTrace();
        }
        return "";
    }

//    public static void generarReporteTemporal(FacturaReport factura) {
//        // Compile jrxml file.
//        JasperReport jasperReport;
//        try {
//            jasperReport = JasperCompileManager
//                    .compileReport(JasperUtil.class.getResource("/report/fomerca_report.jrxml").getFile());
//
//            // Parameters for report
//            Map<String, Object> parameters = new HashMap<String, Object>();
//            parameters.put("IMAGE", JasperUtil.class.getResource("/resource/vidrio.jpg").getFile());
//            // DataSource
//            // This is simple example, no database.
//            // then using empty datasource.
//            JRDataSource dataSource = new JRBeanCollectionDataSource(new ArrayList<FacturaReport>(Arrays.asList(factura)));
//
//            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
//                    parameters, dataSource);
//
//            // Make sure the output directory exists.
//            File outDir = new File(System.getProperty("java.io.tmpdir") + "/");
//
//            // Export to PDF.
//            String nombreArchivo = "/reportefomerca" + UUID.randomUUID() + ".pdf";
//            JasperExportManager.exportReportToPdfFile(jasperPrint,
//                    outDir.getAbsolutePath() + nombreArchivo);
////            Desktop.getDesktop().open(new File(outDir + nombreArchivo));
//        } catch (JRException ex) {
//        } catch (IOException ex) {
//        }
//    }
}
