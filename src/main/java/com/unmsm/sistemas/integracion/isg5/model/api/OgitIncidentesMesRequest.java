package com.unmsm.sistemas.integracion.isg5.model.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OgitIncidentesMesRequest {
    private String u;
    private String p;
    private int mes;
    private int anio;
}