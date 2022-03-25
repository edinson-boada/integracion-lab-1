package com.unmsm.sistemas.integracion.isg5.web;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsMonthRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsRequest;
import com.unmsm.sistemas.integracion.isg5.proxy.service.OgitGoogleSheetsService;
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
    private final OgitGoogleSheetsService ogitGoogleSheetsService;

    @PostMapping("/incidents-to-sheets")
    public OgitIncidentsResponse setIncidentsToSheets(@RequestBody OgitIncidentsRequest ogitIncidentsRequest) throws IOException, GeneralSecurityException {
        return ogitGoogleSheetsService.setIncidentsToSheets(ogitIncidentsRequest);
    }

    @PostMapping("/incidents-month-to-sheets")
    public List<OgitIncidentsResponse> setIncidentsMonthToSheets(@RequestBody OgitIncidentsMonthRequest ogitIncidentsMonthRequest) throws IOException, GeneralSecurityException {
        return ogitGoogleSheetsService.setIncidentsMonthToSheets(ogitIncidentsMonthRequest);
    }
}
