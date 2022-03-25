package com.unmsm.sistemas.integracion.isg5.proxy.api;

import com.unmsm.sistemas.integracion.isg5.model.api.OgitLoginRequest;
import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitLogin;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface OgitLoginAPI {
    @Headers({"Accept: */*",})
    @POST("login")
    Call<OgitLogin> login(@Body OgitLoginRequest ogitLoginRequest);
}
