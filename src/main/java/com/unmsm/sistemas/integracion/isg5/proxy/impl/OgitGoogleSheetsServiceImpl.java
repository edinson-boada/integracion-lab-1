package com.unmsm.sistemas.integracion.isg5.proxy.impl;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.ClearValuesRequest;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.unmsm.sistemas.integracion.isg5.config.GoogleAuthorizationConfig;
import com.unmsm.sistemas.integracion.isg5.mapper.OgitMapper;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsMonthRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentsResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitSelSecurityRequest;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitIncidents;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitIncidentsData;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitLogin;
import com.unmsm.sistemas.integracion.isg5.proxy.api.OgitLoginAPI;
import com.unmsm.sistemas.integracion.isg5.proxy.api.OgitSelSecurityAPI;
import com.unmsm.sistemas.integracion.isg5.proxy.service.OgitGoogleSheetsService;
import com.unmsm.sistemas.integracion.isg5.utils.CalendarUtils;
import com.unmsm.sistemas.integracion.isg5.utils.ThirdpartyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OgitGoogleSheetsServiceImpl implements OgitGoogleSheetsService {
    private final OgitLoginAPI ogitLoginAPI;
    private final OgitSelSecurityAPI ogitSelSecurityAPI;
    private final OgitMapper ogitMapper;
    private final GoogleAuthorizationConfig googleAuthorizationConfig;

    @Value("${spreadsheet.id}")
    private String spreadsheetId;

    @Override
    public OgitIncidentsResponse setIncidentsToSheets(OgitIncidentsRequest ogitIncidentsRequest) throws IOException, GeneralSecurityException {
        OgitIncidentsResponse ogitIncidentsResponse = new OgitIncidentsResponse();
        Sheets sheetsService = googleAuthorizationConfig.getSheetsService();

        //Limpiando Matriz
        ThirdpartyUtils.GOOGLE_SHEETS_MATRIX.clear();
        ThirdpartyUtils.GOOGLE_SHEETS_MATRIX.add(Arrays.asList("FECHA", ogitIncidentsRequest.getFecha()));
        ThirdpartyUtils.GOOGLE_SHEETS_MATRIX.add(ThirdpartyUtils.INCIDENTS_DATA);

        try {
            OgitLogin ogitLogin = ogitLoginAPI.login(ogitMapper.toLoginRequest(ogitIncidentsRequest)).execute().body();
            OgitIncidents ogitIncidents = ogitSelSecurityAPI.getSelSecurity(
                    ogitLogin.getSesion().getToken(),
                    OgitSelSecurityRequest.builder().fecha(ogitIncidentsRequest.getFecha()).build())
                    .execute()
                    .body();
            ogitIncidentsResponse = ogitMapper.toIncidentsResponse(ogitIncidents);
        } catch (IOException ioException) {
            ioException.getMessage();
        }

        //Llenado de celdas
        for (OgitIncidentsData ogitIncidentsData : ogitIncidentsResponse.getData())
            ThirdpartyUtils.GOOGLE_SHEETS_MATRIX.add(Arrays.asList(ogitIncidentsData.getInc_id(),
                    ogitIncidentsData.getUni_id(), ogitIncidentsData.getUni_nombre(), ogitIncidentsData.getTip_id(),
                    ogitIncidentsData.getTip_nombre(), ogitIncidentsData.getSti_id(), ogitIncidentsData.getSti_nombre(),
                    ogitIncidentsData.getNin_id(), ogitIncidentsData.getNin_nombre(), ogitIncidentsData.getFue_id(),
                    ogitIncidentsData.getFue_nombre(), ogitIncidentsData.getInc_descripcion(), ogitIncidentsData.getInc_obs(),
                    ogitIncidentsData.getInc_fecha_abierto(), ogitIncidentsData.getInc_fecha_cierre(), ogitIncidentsData.getInc_otro_apoyo(),
                    ogitIncidentsData.getEst_id(),  ogitIncidentsData.getEst_nombre(), ogitIncidentsData.getInc_dir(),
                    ogitIncidentsData.getInc_longitud(), ogitIncidentsData.getInc_latitud(), ogitIncidentsData.getInc_contacto_cargo(),
                    ogitIncidentsData.getInc_contacto_institucion(), ogitIncidentsData.getUbigeo(), ogitIncidentsData.getInc_distrito(),
                    ogitIncidentsData.getZon_id(), ogitIncidentsData.getZon_nombre(), ogitIncidentsData.getInc_fecha_registro()));

        ValueRange body = new ValueRange()
                .setValues(ThirdpartyUtils.GOOGLE_SHEETS_MATRIX);

        sheetsService
                .spreadsheets()
                .values()
                .update(spreadsheetId, "A1", body)
                .setValueInputOption("RAW")
                .execute();
        return ogitIncidentsResponse;
    }

    @Override
    public List<OgitIncidentsResponse> setIncidentsMonthToSheets(OgitIncidentsMonthRequest ogitIncidentsMonthRequest) throws IOException, GeneralSecurityException {
        //Limpiando Matriz
        ThirdpartyUtils.GOOGLE_SHEETS_MATRIX.clear();

        Sheets sheetsService = googleAuthorizationConfig.getSheetsService();
        List<OgitIncidentsResponse> ogitIncidentsResponses = new ArrayList<>();
        int daysQuantity = CalendarUtils.getDaysQuantityFromDate(
                ogitIncidentsMonthRequest.getYear(),
                ogitIncidentsMonthRequest.getMonth());

        try {
            OgitLogin ogitLogin = ogitLoginAPI.login(ogitMapper.toLoginRequest(ogitIncidentsMonthRequest)).execute().body();
            for (int i = 1; i <= daysQuantity; i++) {
                OgitIncidents ogitIncidents = ogitSelSecurityAPI.getSelSecurity(
                        ogitLogin.getSesion().getToken(),
                        OgitSelSecurityRequest.builder()
                                .fecha(CalendarUtils.getDate(ogitIncidentsMonthRequest.getYear(), ogitIncidentsMonthRequest.getMonth(), i))
                                .build())
                        .execute()
                        .body();
                ogitIncidentsResponses.add(ogitMapper.toIncidentsResponse(ogitIncidents));
            }
        } catch (IOException ioException) {
            ioException.getMessage();
        }

        //Llenado de celdas
        for (int i = 0; i < ogitIncidentsResponses.size(); i++) {
            ThirdpartyUtils.GOOGLE_SHEETS_MATRIX.add(Arrays.asList("FECHA", CalendarUtils.getDate(ogitIncidentsMonthRequest.getYear(), ogitIncidentsMonthRequest.getMonth(), i)));
            ThirdpartyUtils.GOOGLE_SHEETS_MATRIX.add(ThirdpartyUtils.INCIDENTS_DATA);
            for (OgitIncidentsData ogitIncidentsData : ogitIncidentsResponses.get(i).getData()) {
                ThirdpartyUtils.GOOGLE_SHEETS_MATRIX.add(Arrays.asList(ogitIncidentsData.getInc_id(),
                        ogitIncidentsData.getUni_id(), ogitIncidentsData.getUni_nombre(), ogitIncidentsData.getTip_id(),
                        ogitIncidentsData.getTip_nombre(), ogitIncidentsData.getSti_id(), ogitIncidentsData.getSti_nombre(),
                        ogitIncidentsData.getNin_id(), ogitIncidentsData.getNin_nombre(), ogitIncidentsData.getFue_id(),
                        ogitIncidentsData.getFue_nombre(), ogitIncidentsData.getInc_descripcion(), ogitIncidentsData.getInc_obs(),
                        ogitIncidentsData.getInc_fecha_abierto(), ogitIncidentsData.getInc_fecha_cierre(), ogitIncidentsData.getInc_otro_apoyo(),
                        ogitIncidentsData.getEst_id(),  ogitIncidentsData.getEst_nombre(), ogitIncidentsData.getInc_dir(),
                        ogitIncidentsData.getInc_longitud(), ogitIncidentsData.getInc_latitud(), ogitIncidentsData.getInc_contacto_cargo(),
                        ogitIncidentsData.getInc_contacto_institucion(), ogitIncidentsData.getUbigeo(), ogitIncidentsData.getInc_distrito(),
                        ogitIncidentsData.getZon_id(), ogitIncidentsData.getZon_nombre(), ogitIncidentsData.getInc_fecha_registro()));
            }
            ThirdpartyUtils.GOOGLE_SHEETS_MATRIX.add(Collections.emptyList());
        }

        ValueRange body = new ValueRange()
                .setValues(ThirdpartyUtils.GOOGLE_SHEETS_MATRIX);

        sheetsService.spreadsheets()
                .values()
                .update(spreadsheetId, "A1", body)
                .setValueInputOption("RAW")
                .execute();
        return ogitIncidentsResponses;
    }
}
