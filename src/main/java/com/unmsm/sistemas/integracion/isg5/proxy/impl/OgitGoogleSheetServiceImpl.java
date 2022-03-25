package com.unmsm.sistemas.integracion.isg5.proxy.impl;

import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.unmsm.sistemas.integracion.isg5.config.GoogleAuthorizationConfig;
import com.unmsm.sistemas.integracion.isg5.mapper.OgitMapper;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesMesRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesResponse;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitIncidentesRequest;
import com.unmsm.sistemas.integracion.isg5.model.api.OgitSelSeguridadRequest;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitIncidentes;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitIncidentesData;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitLogin;
import com.unmsm.sistemas.integracion.isg5.proxy.api.OgitSelSeguridadAPI;
import com.unmsm.sistemas.integracion.isg5.proxy.api.OgitLoginAPI;
import com.unmsm.sistemas.integracion.isg5.proxy.service.GoogleSheetsService;
import com.unmsm.sistemas.integracion.isg5.proxy.service.OgitService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OgitGoogleSheetServiceImpl implements OgitService, GoogleSheetsService {

    private final OgitLoginAPI ogitLoginAPI;
    private final OgitSelSeguridadAPI ogitSelSeguridadAPI;
    private final OgitMapper ogitMapper;
    private final GoogleAuthorizationConfig googleAuthorizationConfig;
    private OgitIncidentesResponse ogitIncidentesResponse;
    private List<OgitIncidentesResponse> ogitIncidentesMesResponse;

    @Value("${spreadsheet.id}")
    private String spreadsheetId;

    @Override
    public OgitIncidentesResponse getIncidentes(OgitIncidentesRequest ogitIncidentesRequest) {
        try {
            OgitLogin ogitLogin = ogitLoginAPI.login(ogitMapper.toLoginRequest(ogitIncidentesRequest)).execute().body();
            OgitIncidentes ogitIncidentes = ogitSelSeguridadAPI.getSelSeguridad(
                    ogitLogin.getSesion().getToken(),
                    OgitSelSeguridadRequest.builder().fecha(ogitIncidentesRequest.getFecha()).build())
                    .execute()
                    .body();
            ogitIncidentesResponse = ogitMapper.toIncidentesResponse(ogitIncidentes);
            return ogitIncidentesResponse;
        } catch (IOException ioException) {
            ioException.getMessage();
        }
        return null;
    }

    @Override
    public void setIncidentesToSheets() throws IOException, GeneralSecurityException {

        Sheets sheetsService = googleAuthorizationConfig.getSheetsService();

        List<List<Object>> matrix = new ArrayList<>();
        List<Object> nombres = Arrays.asList("inc_id", "uni_id", "uni_nombre",
                "tip_id", "tip_nombre", "sti_id",
                "sti_nombre", "nin_id", "nin_nombre",
                "fue_id", "fue_nombre", "inc_descripcion",
                "inc_obs", "inc_fecha_abierto", "inc_fecha_cierre",
                "inc_otro_apoyo", "est_id", "est_nombre", "inc_dir",
                "inc_longitud", "inc_latitud", "inc_contacto_cargo",
                "inc_contacto_institucion", "ubigeo", "inc_distrito",
                "zon_id", "zon_nombre", "inc_fecha_registro");
        matrix.add(nombres);

        for (OgitIncidentesData ogitIncidentesData: ogitIncidentesResponse.getData()) {
            matrix.add(Arrays.asList(ogitIncidentesData.getInc_id(),
                    ogitIncidentesData.getUni_id(), ogitIncidentesData.getUni_nombre(), ogitIncidentesData.getTip_id(),
                    ogitIncidentesData.getTip_nombre(), ogitIncidentesData.getSti_id(), ogitIncidentesData.getSti_nombre(),
                    ogitIncidentesData.getNin_id(), ogitIncidentesData.getNin_nombre(), ogitIncidentesData.getFue_id(),
                    ogitIncidentesData.getFue_nombre(), ogitIncidentesData.getInc_descripcion(), ogitIncidentesData.getInc_obs(),
                    ogitIncidentesData.getInc_fecha_abierto(), ogitIncidentesData.getInc_fecha_cierre(), ogitIncidentesData.getInc_otro_apoyo(),
                    ogitIncidentesData.getEst_id(),  ogitIncidentesData.getEst_nombre(), ogitIncidentesData.getInc_dir(),
                    ogitIncidentesData.getInc_longitud(), ogitIncidentesData.getInc_latitud(), ogitIncidentesData.getInc_contacto_cargo(),
                    ogitIncidentesData.getInc_contacto_institucion(), ogitIncidentesData.getUbigeo(), ogitIncidentesData.getInc_distrito(),
                    ogitIncidentesData.getZon_id(), ogitIncidentesData.getZon_nombre(), ogitIncidentesData.getInc_fecha_registro()));
        }

        ValueRange body = new ValueRange()
                .setValues(matrix);
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(spreadsheetId, "A1", body)
                .setValueInputOption("RAW")
                .execute();
    }

    @Override
    public List<OgitIncidentesResponse> getIncidentesMes(OgitIncidentesMesRequest ogitIncidentesMesRequest) {
        Calendar fecha = Calendar.getInstance();
        fecha.set(ogitIncidentesMesRequest.getAnio(), ogitIncidentesMesRequest.getMes(), 0);
        ogitIncidentesMesResponse = new ArrayList<>();

        try {
            OgitLogin ogitLogin = ogitLoginAPI.login(ogitMapper.toLoginRequest(ogitIncidentesMesRequest)).execute().body();

            for (int i = 1; i <= fecha.getActualMaximum(Calendar.DAY_OF_MONTH); i++) {

                OgitIncidentes ogitIncidentes = ogitSelSeguridadAPI.getSelSeguridad(
                        ogitLogin.getSesion().getToken(),
                        OgitSelSeguridadRequest.builder()
                                .fecha(String.valueOf(ogitIncidentesMesRequest.getAnio())
                                        .concat("-")
                                        .concat(String.valueOf(ogitIncidentesMesRequest.getMes()))
                                        .concat("-")
                                        .concat(String.valueOf(i)))
                                .build())
                        .execute()
                        .body();
                ogitIncidentesMesResponse.add(
                        ogitMapper.toIncidentesResponse(ogitIncidentes));
            }
            return ogitIncidentesMesResponse;
        } catch (IOException ioException) {
            ioException.getMessage();
        }
        return null;
    }

    @Override
    public void setIncidentesMesToSheets() throws IOException, GeneralSecurityException {
        Sheets sheetsService = googleAuthorizationConfig.getSheetsService();

        List<List<Object>> matrix = new ArrayList<>();
        List<Object> nombres = Arrays.asList("inc_id", "uni_id", "uni_nombre",
                "tip_id", "tip_nombre", "sti_id",
                "sti_nombre", "nin_id", "nin_nombre",
                "fue_id", "fue_nombre", "inc_descripcion",
                "inc_obs", "inc_fecha_abierto", "inc_fecha_cierre",
                "inc_otro_apoyo", "est_id", "est_nombre", "inc_dir",
                "inc_longitud", "inc_latitud", "inc_contacto_cargo",
                "inc_contacto_institucion", "ubigeo", "inc_distrito",
                "zon_id", "zon_nombre", "inc_fecha_registro");

        for (int i = 0; i < ogitIncidentesMesResponse.size(); i++) {
            matrix.add(Arrays.asList(i+1));
            matrix.add(nombres);
            for (OgitIncidentesData ogitIncidentesData: ogitIncidentesMesResponse.get(i).getData()) {
                matrix.add(Arrays.asList(ogitIncidentesData.getInc_id(),
                        ogitIncidentesData.getUni_id(), ogitIncidentesData.getUni_nombre(), ogitIncidentesData.getTip_id(),
                        ogitIncidentesData.getTip_nombre(), ogitIncidentesData.getSti_id(), ogitIncidentesData.getSti_nombre(),
                        ogitIncidentesData.getNin_id(), ogitIncidentesData.getNin_nombre(), ogitIncidentesData.getFue_id(),
                        ogitIncidentesData.getFue_nombre(), ogitIncidentesData.getInc_descripcion(), ogitIncidentesData.getInc_obs(),
                        ogitIncidentesData.getInc_fecha_abierto(), ogitIncidentesData.getInc_fecha_cierre(), ogitIncidentesData.getInc_otro_apoyo(),
                        ogitIncidentesData.getEst_id(),  ogitIncidentesData.getEst_nombre(), ogitIncidentesData.getInc_dir(),
                        ogitIncidentesData.getInc_longitud(), ogitIncidentesData.getInc_latitud(), ogitIncidentesData.getInc_contacto_cargo(),
                        ogitIncidentesData.getInc_contacto_institucion(), ogitIncidentesData.getUbigeo(), ogitIncidentesData.getInc_distrito(),
                        ogitIncidentesData.getZon_id(), ogitIncidentesData.getZon_nombre(), ogitIncidentesData.getInc_fecha_registro()));
            }
        }

        ValueRange body = new ValueRange()
                .setValues(matrix);
        UpdateValuesResponse result = sheetsService.spreadsheets().values()
                .update(spreadsheetId, "A1", body)
                .setValueInputOption("RAW")
                .execute();
    }
}
