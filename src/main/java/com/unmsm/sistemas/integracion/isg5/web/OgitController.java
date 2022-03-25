package com.unmsm.sistemas.integracion.isg5.web;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesMesRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesRequest;
import com.unmsm.sistemas.integracion.isg5.proxy.service.GoogleSheetsService;
import com.unmsm.sistemas.integracion.isg5.proxy.service.OgitService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class OgitController {
    private final OgitService ogitService;
    private final GoogleSheetsService googleSheetsService;

    @PostMapping("/incidentes")
    public OgitIncidentesResponse getIncidentes(@RequestBody OgitIncidentesRequest ogitIncidentesRequest) {
        return ogitService.getIncidentes(ogitIncidentesRequest);
    }

    @PostMapping("/google-sheet")
    public String setIncidentesToSheets() throws IOException, GeneralSecurityException {
        googleSheetsService.setIncidentesToSheets();
        return "OK";
    }

    @PostMapping("/incidentes-mes")
    public List<OgitIncidentesResponse> getIncidentesMes(@RequestBody OgitIncidentesMesRequest ogitIncidentesMesRequest) {
        return ogitService.getIncidentesMes(ogitIncidentesMesRequest);
    }

    @PostMapping("/google-sheet-mes")
    public String setIncidentesMesToSheets() throws IOException, GeneralSecurityException {
        googleSheetsService.setIncidentesMesToSheets();
        return "OK";
    }
}
