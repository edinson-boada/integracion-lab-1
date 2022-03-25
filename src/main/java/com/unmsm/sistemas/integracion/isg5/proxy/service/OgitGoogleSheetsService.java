package com.unmsm.sistemas.integracion.isg5.proxy.service;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsMonthRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public interface OgitGoogleSheetsService {
    OgitIncidentsResponse setIncidentsToSheets(OgitIncidentsRequest ogitIncidentsRequest) throws IOException, GeneralSecurityException;
    List<OgitIncidentsResponse> setIncidentsMonthToSheets(OgitIncidentsMonthRequest ogitIncidentsMonthRequest) throws IOException, GeneralSecurityException;
}
