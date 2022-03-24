package com.unmsm.sistemas.integracion.isg5.model.thirdparty;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OgitIncidentes {
    private boolean status;
    private String message;
    private List<OgitIncidentesData> data;
}
