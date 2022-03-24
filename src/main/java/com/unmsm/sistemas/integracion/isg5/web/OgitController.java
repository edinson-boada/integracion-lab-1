package com.unmsm.sistemas.integracion.isg5.web;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesRequest;
import com.unmsm.sistemas.integracion.isg5.proxy.service.GoogleSheetsService;
import com.unmsm.sistemas.integracion.isg5.proxy.service.OgitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@RequiredArgsConstructor
public class OgitController {
    private final OgitService ogitService;
    private final GoogleSheetsService googleSheetsService;

    @PostMapping("/incidentes")
    public OgitIncidentesResponse getIncidentes(@RequestBody OgitIncidentesRequest ogitIncidentesRequest) {
        return ogitService.getIncidentes(ogitIncidentesRequest);
    }

    @GetMapping("/google-sheet")
    public String getSpreadsheetValues() throws IOException, GeneralSecurityException {
        googleSheetsService.getSpreadsheetValues();
        return "OK";
    }
}
