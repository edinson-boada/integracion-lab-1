package com.unmsm.sistemas.integracion.isg5.model.thirdparty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OgitIncidentsData {
    private int inc_id;
    private int uni_id;
    private String uni_nombre;
    private int tip_id;
    private String tip_nombre;
    private String sti_id;
    private String sti_nombre;
    private int nin_id;
    private String nin_nombre;
    private int fue_id;
    private String fue_nombre;
    private String inc_descripcion;
    private String inc_obs;
    private String inc_fecha_abierto;
    private String inc_fecha_cierre;
    private String inc_otro_apoyo;
    private int est_id;
    private String est_nombre;
    private String inc_dir;
    private int inc_longitud;
    private int inc_latitud;
    private String inc_contacto_cargo;
    private String inc_contacto_institucion;
    private String ubigeo;
    private String inc_distrito;
    private int zon_id;
    private String zon_nombre;
    private String inc_fecha_registro;
}
