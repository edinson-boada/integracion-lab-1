package com.unmsm.sistemas.integracion.isg5.model.api;

import com.unmsm.sistemas.integracion.isg5.model.thirdparty.OgitIncidentsData;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OgitIncidentsResponse {
    private boolean status;
    private String message;
    private List<OgitIncidentsData> data;
}
