
package com.tienda.service;

// se define la firma del metodo que genera los reportes, con los siguientes parametros

import java.io.IOException;
import java.util.Map;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

// 1.  reporte: es el nombre del arhcivo de reporte (.jasper)
// 2. parametros:  un Map que contiene los parametros del reporte...  si los ocupa
// 3. tipo: es el tipo de reporte: vpdf, pdf, xls, csv

public interface ReporteService {
    public ResponseEntity<Resource> generaReporte(
    String reporte, 
    Map<String, Object> parametros,
    String tipo) throws IOException;
}
