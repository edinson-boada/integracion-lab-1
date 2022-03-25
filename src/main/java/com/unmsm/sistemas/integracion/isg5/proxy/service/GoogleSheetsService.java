package com.unmsm.sistemas.integracion.isg5.proxy.service;

import java.io.IOException;
import java.security.GeneralSecurityException;

public interface GoogleSheetsService {
    void setIncidentesToSheets() throws IOException, GeneralSecurityException;
    void setIncidentesMesToSheets() throws IOException, GeneralSecurityException;
}
