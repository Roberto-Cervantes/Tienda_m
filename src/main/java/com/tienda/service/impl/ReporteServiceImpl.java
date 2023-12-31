package com.tienda.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.tienda.service.ReporteService;
import org.springframework.stereotype.Service;

@Service
public class ReporteServiceImpl implements ReporteService {

    @Autowired
    private DataSource dataSource;

    @Override
    public ResponseEntity<Resource> generaReporte(String reporte,
            Map<String, Object> parametros,
            String tipo) throws IOException {

        // Se asigna el tipo de pagina a generar
        String estilo = tipo.equalsIgnoreCase("vPdf")
                ? "inline; " : "attachment; ";

        // se establece la ruta de los reportes
        String reportePath = "reportes";

        // se define la salida temporal del reporte generado
        ByteArrayOutputStream salida = new ByteArrayOutputStream();

        // se establece la fuente para ller el reporte . jasper
        ClassPathResource fuente = new ClassPathResource(
                reportePath
                + File.separator
                + reporte
                + ".jasper");
        // se define el objeto que leer el archivo de reporte .jasper
        InputStream elReporte = fuente.getInputStream();

        try {
            // se genera el reporte en memoria
            var reporteJasper = JasperFillManager.fillReport(
                    elReporte,
                    parametros,
                    dataSource.getConnection());

            // A partir de aqui, se genera la respuesta al usuario
            MediaType mediaType = null;
            String archivoSalida = "";

            // se debe decidir cual tipo de reporte se general
            switch (tipo) {
                case "Pdf","vPdf" -> { // se descargará un pdf
                    JasperExportManager.exportReportToPdfStream(reporteJasper, salida);
                    mediaType = MediaType.APPLICATION_PDF;
                    archivoSalida = reporte + ".pdf";
                }
                case "Xls" -> { // se descargará un xls
                    JRXlsxExporter paraExcel = new JRXlsxExporter();

                    paraExcel.setExporterInput(
                            new SimpleExporterInput(
                                    reporteJasper));

                    paraExcel.setExporterOutput(
                            new SimpleOutputStreamExporterOutput(salida));  
                    
                    SimpleXlsxReportConfiguration configuracion= new SimpleXlsxReportConfiguration();
                    
                    configuracion.setDetectCellType(true);
                    configuracion.setCollapseRowSpan(true);
                    
                    paraExcel.setConfiguration(configuracion);
                    paraExcel.exportReport();

                    mediaType = MediaType.APPLICATION_OCTET_STREAM;
                    archivoSalida = reporte + ".xlsx";
                }
                case "Csv" -> { // se descargará un  csv
                    JRCsvExporter paraCsv = new JRCsvExporter();

                    paraCsv.setExporterInput(
                            new SimpleExporterInput(
                                    reporteJasper));

                    paraCsv.setExporterOutput(
                            new SimpleWriterExporterOutput(salida));
                    
                    paraCsv.exportReport();
                    
                    mediaType = MediaType.TEXT_PLAIN;
                    archivoSalida = reporte + ".csv";
                }
            }
            
            // A PARTIR DE AQUI SE GENERAL LA RESPUESTA AL USUARIO
            
            byte[] data=salida.toByteArray();
            HttpHeaders headers=new HttpHeaders();
            headers.set("Content-Disposition", estilo+"filename=\""+archivoSalida+"\"");
            
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(data.length)
                    .contentType(mediaType)
                    .body(new InputStreamResource(
                            new ByteArrayInputStream(data)
                    ));

        } catch (JRException | SQLException ex) {
            ex.printStackTrace();
        }

        //
        return null;
    }

}
