/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.dto;

import java.util.List;

/**
 *
 * @author Jessai
 */
public class EspecificacionEmail {
    
    private List<String> lstArchivosAdjuntos;
    private String mensajeHTML;
    private String emailOrigen;
    private String urlImagenFirma;
    private String urlImagenCuerpoCorreo;
    private String passwd;
    private String asunto;
    private String emailDestino;
    private String nombreCliente;
    private String nombreRemitente;
    private String pieDePagina;

    public String getPieDePagina() {
        return pieDePagina;
    }

    public void setPieDePagina(String pieDePagina) {
        this.pieDePagina = pieDePagina;
    }

    public String getUrlImagenCuerpoCorreo() {
        return urlImagenCuerpoCorreo;
    }

    public void setUrlImagenCuerpoCorreo(String urlImagenCuerpoCorreo) {
        this.urlImagenCuerpoCorreo = urlImagenCuerpoCorreo;
    }

    public String getNombreRemitente() {
        return nombreRemitente;
    }

    public void setNombreRemitente(String nombreRemitente) {
        this.nombreRemitente = nombreRemitente;
    }
    
    public List<String> getLstArchivosAdjuntos() {
        return lstArchivosAdjuntos;
    }

    public void setLstArchivosAdjuntos(List<String> lstArchivosAdjuntos) {
        this.lstArchivosAdjuntos = lstArchivosAdjuntos;
    }

    public String getMensajeHTML() {
        return mensajeHTML;
    }

    public void setMensajeHTML(String mensajeHTML) {
        this.mensajeHTML = mensajeHTML;
    }

    public String getEmailOrigen() {
        return emailOrigen;
    }

    public void setEmailOrigen(String emailOrigen) {
        this.emailOrigen = emailOrigen;
    }

    public String getUrlImagenFirma() {
        return urlImagenFirma;
    }

    public void setUrlImagenFirma(String urlImagenFirma) {
        this.urlImagenFirma = urlImagenFirma;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getEmailDestino() {
        return emailDestino;
    }

    public void setEmailDestino(String emailDestino) {
        this.emailDestino = emailDestino;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }
    
    
    
}
