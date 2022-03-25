package com.unmsm.sistemas.integracion.isg5.model.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OgitIncidentsMonthRequest {
    private String u;
    private String p;
    private int month;
    private int year;
}