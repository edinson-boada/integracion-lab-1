package com.unmsm.sistemas.integracion.isg5.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThirdpartyUtils {
    public static final List<Object> INCIDENTS_DATA = Arrays.asList("inc_id", "uni_id", "uni_nombre",
            "tip_id", "tip_nombre", "sti_id",
            "sti_nombre", "nin_id", "nin_nombre",
            "fue_id", "fue_nombre", "inc_descripcion",
            "inc_obs", "inc_fecha_abierto", "inc_fecha_cierre",
            "inc_otro_apoyo", "est_id", "est_nombre", "inc_dir",
            "inc_longitud", "inc_latitud", "inc_contacto_cargo",
            "inc_contacto_institucion", "ubigeo", "inc_distrito",
            "zon_id", "zon_nombre", "inc_fecha_registro");

    public static List<List<Object>> GOOGLE_SHEETS_MATRIX = new ArrayList<>();
}
