package com.unmsm.sistemas.integracion.isg5.model.thirdparty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OgitLogin {
    private boolean status;
    private String message;
    private OgitLoginSesion sesion;
}
