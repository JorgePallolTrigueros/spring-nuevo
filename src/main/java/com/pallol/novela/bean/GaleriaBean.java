/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.bean;

import com.pallol.novela.dao.GaleriaDAO;
import com.pallol.novela.dao.NovelaDAO;
import com.pallol.novela.dao.PersonajeDAO;
import com.pallol.novela.entities.Galeria;
import com.pallol.novela.entities.Novela;
import com.pallol.novela.util.UtileriasArchivo;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.hibernate.HibernateException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class GaleriaBean implements Serializable{

    private Galeria galeria;
    private Integer novelaId;
    private UploadedFile file;


    
    public GaleriaBean() {
        galeria = new Galeria();
        galeria.setNovela(new Novela());
    }


    public void insertaGaleria() {
        UtileriasArchivo utileriasArchivo = new UtileriasArchivo();
        if (utileriasArchivo.almacenaImagen(file, "galeria")) {

            NovelaDAO novelaDAO = new NovelaDAO();
            Novela novela = novelaDAO.obtieneNovelaPorId(novelaId);
            galeria.setNovela(novela);
            galeria.setUrl(utileriasArchivo.getNombreImagenParaGuardar());
            GaleriaDAO gajeriaDAO = new GaleriaDAO();
            gajeriaDAO.insertaGaleria(galeria);       
        }
    }

    
    
    public void obtieneGleria(Integer galeriaId) {
        if (galeriaId != null) {
            GaleriaDAO gajeriaDAO = new GaleriaDAO();
            galeria = gajeriaDAO.obtienealeriaGPorGaleriaId(galeriaId);
        }
    }


    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    
        public void eliminaGaleria(Integer galeriaId, Integer novelaId) 
  {
        try {
            GaleriaDAO  galeriaDAO = new GaleriaDAO();
            galeria = galeriaDAO.obtienealeriaGPorGaleriaId(galeriaId);
            galeriaDAO.eliminaGaleria(galeria);
        } catch (HibernateException he) {
            he.printStackTrace();
            //algun mensaje
        }
       
    }
    

    
    

    
        public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public void upload() {
        if (file != null) {
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    
    
    
    
    public Galeria getGaleria() {
        return galeria;
    }

    public void setGaleria(Galeria galeria) {
        this.galeria = galeria;
    }

    public Integer getNovelaId() {
        return novelaId;
    }

    public void setNovelaId(Integer novelaId) {
        this.novelaId = novelaId;
    }

}
