/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.bean;

import com.pallol.novela.dao.CategoriaDAO;
import com.pallol.novela.dao.UsuarioDAO;
import com.pallol.novela.dao.NovelaDAO;
import com.pallol.novela.entities.Categoria;
import com.pallol.novela.entities.Usuario;
import com.pallol.novela.entities.Novela;
import com.pallol.novela.entities.Persona;
import com.pallol.novela.entities.Rol;
import com.pallol.novela.util.GeneradorEmail;
import com.pallol.novela.util.UtileriasArchivo;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

@ManagedBean
@SessionScoped
public class UsuarioBean implements Serializable {

    private Integer novelaId;
    private UploadedFile file;
    private Usuario usuario;
    private Persona persona;
    private Rol rol;
    private Integer UsuarioidId;
    private Boolean usuarioRegistrado = false;

    public List lstComentario;
    public List<Usuario> lstUsuario;
    public List<Novela> lstNovela;

    public UsuarioBean() {
        usuario = new Usuario();
    }

    public List<Usuario> muestraUsuario() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        lstUsuario = usuarioDAO.obtieneUsuario();
        return lstUsuario;
    }

    public String muestraNovelasDetalleUsuario(Integer usuarioId) {
        this.UsuarioidId = usuarioId;
        NovelaDAO novelaDAO = new NovelaDAO();
        lstNovela = novelaDAO.obtieneNovelaPorAutor(usuarioId);
        return "detalleusuario";
    }

    public String iniciaSesion() {
        String paginaDestino = "index";

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuario = usuarioDAO.validaUsuario(usuario);
        if (usuario == null) {
            paginaDestino = "login";
            usuario = new Usuario();
        } else if (usuario != null) {
            persona = usuarioDAO.obtieneDetallesUsuario(usuario);
            rol = usuarioDAO.obtieneRolUsuario(usuario);
            usuarioRegistrado = true;
        }

        if (rol != null && "admin".equals(rol.getNombre())) {
            paginaDestino = "panelAdministrador";
        }

        return paginaDestino + "?faces-redirect=true";
    }

    public String cierraSesion() {
        ((HttpSession) FacesContext.getCurrentInstance()
                .getExternalContext().getSession(false)).invalidate();
        return "login?faces-redirect=true";
    }

    public String ingresaCambioContrasena() {
        return "recordarcontrasena?faces-redirect=true";
    }

    public void enviaCodigoContrasena() {
        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuario = usuarioDAO.obtieneUsuarioPorCodigo(usuario.getCodigo());
            usuario.setEstado(false);
            usuarioDAO.actualizaUsuario(usuario);

            GeneradorEmail generadorEmail = new GeneradorEmail(usuario);
            generadorEmail.preparaEnviaMailRecuperacion();
        } catch (IOException ex) {
            Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertaUsuario() {

        UtileriasArchivo utileriasArchivo = new UtileriasArchivo();
        if (utileriasArchivo.almacenaImagen(file, "usuario")) {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.insertaUsuario(usuario);
        }
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

    public void restaurarcontrasena() {

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        usuarioDAO.insertaUsuario(usuario);
    }

    public void eliminaAutorDeNovela(Integer novelaId) {
        //Obtener usuario de session
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        UsuarioBean usuarioBean = application.evaluateExpressionGet(context, "#{usuarioBean}", UsuarioBean.class);
        Usuario usuario = usuarioBean.getUsuario();
        
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean comprobacion = usuarioDAO.eliminaUsuarioDeNovela(usuario.getUsuarioId(), novelaId);
        System.out.println("Eliminado: " + comprobacion);
    }

    public UploadedFile getFile() {
        return file;
    }

    public Integer getNovelaId() {
        return novelaId;
    }

    public void setNovelaId(Integer novelaId) {
        this.novelaId = novelaId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public List getLstComentario() {
        return lstComentario;
    }

    public List<Usuario> getLstUsuario() {
        return lstUsuario;
    }

    public void setLstUsuario(List<Usuario> lstUsuario) {
        this.lstUsuario = lstUsuario;
    }

    public Integer getUsuarioidId() {
        return UsuarioidId;
    }

    public void setUsuarioidId(Integer UsuarioidId) {
        this.UsuarioidId = UsuarioidId;
    }

    public List<Novela> getLstNovela() {
        return lstNovela;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Boolean getUsuarioRegistrado() {
        return usuarioRegistrado;
    }

    public void setUsuarioRegistrado(Boolean usuarioRegistrado) {
        this.usuarioRegistrado = usuarioRegistrado;
    }
}
