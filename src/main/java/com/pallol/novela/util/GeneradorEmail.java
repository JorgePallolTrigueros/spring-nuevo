/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.util;

import com.pallol.novela.dto.EspecificacionEmail;
import com.pallol.novela.entities.Usuario;
import java.io.IOException;

/**
 *
 * @author Jessai
 */
public class GeneradorEmail {

    private Usuario usuario;

    public GeneradorEmail() {
    }

    public GeneradorEmail(Usuario usuario) {
        this.usuario = usuario;
    }

    public void preparaEnviaMailRecuperacion() throws IOException {
        EspecificacionEmail bienvenidoEmail = new EspecificacionEmail();


        bienvenidoEmail.setAsunto("Instrucciones para recuperar contraseña");
        /*Cambiar estos correos*/
        bienvenidoEmail.setEmailDestino(usuario.getCodigo());
        bienvenidoEmail.setEmailOrigen("jorgepallol5@gmail.com");
        bienvenidoEmail.setPasswd("234234234");
        bienvenidoEmail.setNombreRemitente("Libreria Alexandra");
        bienvenidoEmail.setMensajeHTML("<h3><b><a href='http://localhost:8080/NovelaPallol/restore.xhtml?codigo=" + usuario.getCodigo()+"'>Reestablecer tu contraseña</a></b></h3><br/>");
        LanzadorDeEmail lanzadorDeEmail = new LanzadorDeEmail(bienvenidoEmail);
        lanzadorDeEmail.enviaEmail();
    }

}
