package com.unmsm.sistemas.integracion.isg5.proxy.api;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitSelSeguridadRequest;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitIncidentes;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OgitSelSeguridadAPI {
    @Headers({ "Accept: */*" })
    @POST("sel_seguridad_incidente")
    Call<OgitIncidentes> getSelSeguridad(@Header("x-access-token") String token, @Body OgitSelSeguridadRequest ogitSelSeguridadRequest);
}
