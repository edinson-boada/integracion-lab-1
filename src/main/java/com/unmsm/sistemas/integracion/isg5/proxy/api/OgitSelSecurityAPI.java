package com.unmsm.sistemas.integracion.isg5.proxy.api;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitSelSecurityRequest;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitIncidents;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OgitSelSecurityAPI {
    @Headers({ "Accept: */*" })
    @POST("sel_seguridad_incidente")
    Call<OgitIncidents> getSelSecurity(@Header("x-access-token") String token, @Body OgitSelSecurityRequest ogitSelSecurityRequest);
}
