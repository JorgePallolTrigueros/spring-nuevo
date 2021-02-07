/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.bean;

import com.pallol.novela.dao.CapituloDAO;
import com.pallol.novela.dao.CategoriaDAO;
import com.pallol.novela.dao.GaleriaDAO;
import com.pallol.novela.dao.NovelaDAO;
import com.pallol.novela.dao.PersonajeDAO;
import com.pallol.novela.dao.UsuarioDAO;
import com.pallol.novela.entities.Capitulo;
import com.pallol.novela.entities.Categoria;
import com.pallol.novela.entities.Galeria;
import com.pallol.novela.entities.Novela;
import com.pallol.novela.entities.Personaje;
import com.pallol.novela.entities.Usuario;
import com.pallol.novela.entities.UsuarioNovela;
import com.pallol.novela.util.ListaElementosPaginados;
import com.pallol.novela.util.UtileriasArchivo;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.hibernate.HibernateException;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author casa pallol
 */
@ManagedBean
@ViewScoped
//@RequestScoped - Vive solo hasta que termina de cargar una pagina xhtml
//@ViewScoped - Util para llamadas jQuery o Ajax
public class NovelaBean implements Serializable {

    public List lstComentario;
    public List<Personaje> lstPersonaje;
    public List<Capitulo> lstCapitulo;
    public List<Galeria> lstGaleria;
    public List<Novela> lstNovela;
    private Integer novelaId;
    private Novela novela;

    private Integer categoriaId;
    private UploadedFile file;
    private ListaElementosPaginados<Novela> listaElementosPaginados;
    private String tablaNovelaId = "tabla_novela";

    public NovelaBean() {
        novela = new Novela();
        novela.setCategoria(new Categoria());

    }

    public List<Novela> muestraNovela() {
        NovelaDAO novelaDAO = new NovelaDAO();
        lstNovela = novelaDAO.obtieneNovela();
        return lstNovela;
    }

    public List<Novela> muestraNovelaPaginada() {
        NovelaDAO novelaDAO = new NovelaDAO();
        lstNovela = novelaDAO.obtieneNovela();
        if (listaElementosPaginados == null) {
            listaElementosPaginados = new ListaElementosPaginados(lstNovela, 0);
        }
        return listaElementosPaginados.getElementos();
    }

    public String muestraDetallesNovela(Integer novelaId) {
        this.novelaId = novelaId;
        PersonajeDAO personajeDAO = new PersonajeDAO();
        lstPersonaje = personajeDAO.obtienePersonajePorNovelaId(novelaId);
        CapituloDAO capituloDAO = new CapituloDAO();
        lstCapitulo = capituloDAO.obtienePCapitulo(novelaId);
        GaleriaDAO galeriaDAO = new GaleriaDAO();
        lstGaleria = galeriaDAO.obtienePGaleria(novelaId);

        return "novela?faces-redirect=true";
    }

    public void muestraDetallesNovelaparaedicion(Integer novelaId) {
        NovelaDAO novelaDAO = new NovelaDAO();
        novelaId = novelaId == null ? this.novelaId : novelaId;
        novela = novelaDAO.obtieneNovelaPorId(novelaId);

        PersonajeDAO personajeDAO = new PersonajeDAO();
        lstPersonaje = personajeDAO.obtienePersonajePorNovelaId(novelaId);
        CapituloDAO capituloDAO = new CapituloDAO();
        lstCapitulo = capituloDAO.obtienePCapitulo(novelaId);
        GaleriaDAO galeriaDAO = new GaleriaDAO();
        lstGaleria = galeriaDAO.obtienePGaleria(novelaId);
    }

    public String muestraDetallesNovelaparavisitantes(Integer novelaId) {

        this.novelaId = novelaId;
        PersonajeDAO personajeDAO = new PersonajeDAO();
        lstPersonaje = personajeDAO.obtienePersonajePorNovelaId(novelaId);
        CapituloDAO capituloDAO = new CapituloDAO();
        lstCapitulo = capituloDAO.obtienePCapitulo(novelaId);
        GaleriaDAO galeriaDAO = new GaleriaDAO();
        lstGaleria = galeriaDAO.obtienePGaleria(novelaId);

        return "novelavisitante?faces-redirect=true";

    }

    public void insertaNovela() {
        UtileriasArchivo utileriasArchivo = new UtileriasArchivo();
        if (utileriasArchivo.almacenaImagen(file, "novela")) {

            CategoriaDAO categoriaDAO = new CategoriaDAO();
            Categoria categoria = categoriaDAO.obtieneCategoriaPorId(novela.getCategoria().getCategoriaId());
            novela.setCategoria(categoria);
            novela.setImagen(utileriasArchivo.getNombreImagenParaGuardar());

            NovelaDAO novelaDAO = new NovelaDAO();
            if (novela.getNovelaId() == null) {
                novela = novelaDAO.insertaNovela(novela);
            } else {
                novela = novelaDAO.actualizaNovela(novela);
            }

            //Obtener usuario de session
            FacesContext context = FacesContext.getCurrentInstance();
            Application application = context.getApplication();
            UsuarioBean usuarioBean = application.evaluateExpressionGet(context, "#{usuarioBean}", UsuarioBean.class);
            Usuario usuario = usuarioBean.getUsuario();
            
            UsuarioNovela usuarioNovela = new UsuarioNovela();
            usuarioNovela.setUsuario(usuario);
            usuarioNovela.setNovela(novela);
            
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioNovela = usuarioDAO.insertaUsuarioNovela(usuarioNovela);
        }
    }

    public void obtieneNovela(Integer novelaId) {
        if (novelaId != null) {
            NovelaDAO novelaDAO = new NovelaDAO();
            novela = novelaDAO.obtieneNovelaPorId(novelaId);
        }
    }

    public void eliminaNovela(Integer novelaId) {
        try {
            NovelaDAO novelaDAO = new NovelaDAO();
            novela = novelaDAO.obtieneNovelaPorNovelaId(novelaId);
            novelaDAO.eliminaNovela(novela);
        } catch (HibernateException he) {
            he.printStackTrace();
            //algun mensaje
        }
    }

    public String cargaPaginaEdicionNovela(Integer novelaId) {
        return "novelaeditable?faces-redirect=true&novelaId=" + novelaId;
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

    public void reset() {
        PrimeFaces.current().resetInputs("form:pqcuenta");
    }

    public List getLstComentario() {
        return lstComentario;
    }

    public List<Capitulo> getLstCapitulo() {
        return lstCapitulo;
    }

    public List<Galeria> getLstGaleria() {
        return lstGaleria;
    }

    public List<Novela> getLstNovela() {
        return lstNovela;
    }

    public List<Personaje> getLstPersonaje() {
        return lstPersonaje;
    }

    public Novela getNovela() {
        return novela;
    }

    public void setNovela(Novela novela) {
        this.novela = novela;
    }

    public ListaElementosPaginados<Novela> getListaElementosPaginados() {
        return listaElementosPaginados;
    }

    public void setListaElementosPaginados(ListaElementosPaginados<Novela> listaElementosPaginados) {
        this.listaElementosPaginados = listaElementosPaginados;
    }

    public String getTablaNovelaId() {
        return tablaNovelaId;
    }

    public Integer getNovelaId() {
        return novelaId;
    }

    public void setNovelaId(Integer novelaId) {
        this.novelaId = novelaId;
    }

    public Integer getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Integer categoriaId) {
        this.categoriaId = categoriaId;
    }

}
