package com.unmsm.sistemas.integracion.isg5.model.api;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OgitLoginResponse {
    private boolean status;
    private String message;
    private int usu_id;
    private int per_id;
    private String nombre;
    private int ins_id;
    private String token;
}
