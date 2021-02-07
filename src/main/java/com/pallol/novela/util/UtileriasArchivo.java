/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author casa pallol
 */
public class UtileriasArchivo {

    private String nombreImagenParaGuardar = null;

    public boolean almacenaImagen(UploadedFile archivoCargado, String directorioDestino) {

        InputStream archivoEntranteBinario = null;
        OutputStream nuevoArchivoBinario = null;
        String rutaServidor = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("") + File.separator;

        boolean archivoCargadoConExito = true;

        try {
//            File f = new File(directorio);
//            if (!f.exists()) {
//                f.mkdirs();
//            }
            String imagen = archivoCargado.getFileName();
            imagen = imagen.substring(imagen.lastIndexOf("\\") + 1, imagen.length());

            String imagenConDirectorio = directorioDestino + File.separator + imagen;
            setNombreImagenParaGuardar(imagenConDirectorio);

            String rutaFinal = rutaServidor + imagenConDirectorio;
            File archivoDestino = new File(rutaFinal);

            archivoEntranteBinario = archivoCargado.getInputstream();
            nuevoArchivoBinario = new FileOutputStream(archivoDestino);

            byte[] buffer = new byte[1024];
            int bytesRead = 0;
            while ((bytesRead = archivoEntranteBinario.read(buffer)) != -1) {
                nuevoArchivoBinario.write(buffer, 0, bytesRead);
            }
        } catch (IOException ex) {
            Logger.getLogger(UtileriasArchivo.class.getName()).log(Level.SEVERE, null, ex);
            archivoCargadoConExito = false;
        } finally {
            try {
                if (nuevoArchivoBinario != null && archivoEntranteBinario != null) {
                    nuevoArchivoBinario.close();
                    archivoEntranteBinario.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(UtileriasArchivo.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return archivoCargadoConExito;
    }

    public String getNombreImagenParaGuardar() {
        return nombreImagenParaGuardar;
    }

    public void setNombreImagenParaGuardar(String nombreImagenParaGuardar) {
        this.nombreImagenParaGuardar = nombreImagenParaGuardar;
    }

}
