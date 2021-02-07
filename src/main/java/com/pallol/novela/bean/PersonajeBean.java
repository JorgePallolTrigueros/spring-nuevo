package com.pallol.novela.bean;

import com.pallol.novela.dao.NovelaDAO;
import com.pallol.novela.dao.PersonajeDAO;
import com.pallol.novela.entities.Novela;
import com.pallol.novela.entities.Personaje;
import com.pallol.novela.util.UtileriasArchivo;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class PersonajeBean implements Serializable{

    private Personaje personaje;
    private Integer novelaId;
    private UploadedFile file;

    public PersonajeBean() {
        personaje = new Personaje();
        personaje.setNovela(new Novela());
    }

    public void insertaPersonaje() {
        UtileriasArchivo utileriasArchivo = new UtileriasArchivo();
        if (utileriasArchivo.almacenaImagen(file, "personaje")) {

            NovelaDAO novelaDAO = new NovelaDAO();
            Novela novela = novelaDAO.obtieneNovelaPorId(novelaId);
            personaje.setNovela(novela);
            personaje.setUrl(utileriasArchivo.getNombreImagenParaGuardar());
            PersonajeDAO personajeDAO = new PersonajeDAO();
            if (personaje.getPersonajeId() == null) {
                personajeDAO.insertaPersonaje(personaje);
            } else{
                personajeDAO.actualizaPersonaje(personaje);
            }
        }
    }

    public void obtienePersonaje(Integer personajeId) {
        if (personajeId != null) {
            PersonajeDAO personajeDAO = new PersonajeDAO();
            personaje = personajeDAO.obtienePersonajePorPersonajeId(personajeId);
        }
    }

    public void eliminaPesonaje(Integer personajeId, Integer novelaId) {
        try {
            PersonajeDAO personajeDAO = new PersonajeDAO();
            personaje = personajeDAO.obtienePersonajePorPersonajeId(personajeId);
            personajeDAO.eliminaPersonaje(personaje);
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

    public void handleFileUpload(FileUploadEvent event) {
        FacesMessage msg = new FacesMessage("Succesful", event.getFile().getFileName() + " is uploaded.");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public Personaje getPersonaje() {
        return personaje;
    }

    public void setPersonaje(Personaje personaje) {
        this.personaje = personaje;
    }

    public Integer getNovelaId() {
        return novelaId;
    }

    public void setNovelaId(Integer novelaId) {
        this.novelaId = novelaId;
    }

}
