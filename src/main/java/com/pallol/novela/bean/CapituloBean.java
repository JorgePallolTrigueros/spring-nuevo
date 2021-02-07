package com.pallol.novela.bean;

            import com.pallol.novela.dao.CapituloDAO;
            import com.pallol.novela.dao.NovelaDAO;
            import com.pallol.novela.entities.Capitulo;
            import com.pallol.novela.entities.Novela;
            import com.pallol.novela.util.UtileriasArchivo;
            import java.io.Serializable;
            import javax.faces.bean.ManagedBean;
            import javax.faces.bean.ViewScoped;
            import org.hibernate.HibernateException;
            import org.primefaces.model.UploadedFile;

@ManagedBean
@ViewScoped
public class CapituloBean implements Serializable{

    private Capitulo capitulo;
    private Integer novelaId;
    
    

    public CapituloBean() {
        capitulo = new Capitulo();
        capitulo.setNovela(new Novela());
    }

    
    
    
    public void insertaCapitulo() {
        
        

            NovelaDAO novelaDAO = new NovelaDAO();
            Novela novela = novelaDAO.obtieneNovelaPorId(novelaId);
            capitulo.setNovela(novela);
           
            CapituloDAO capituloDAO = new CapituloDAO();

            if (capitulo.getCapituloId() == null) {
                capituloDAO.insertaCapitulo(capitulo);
            } else{
                capituloDAO.actualizaCapitulo(capitulo);
            }
       
    }

    
    
    
    
    
    public void obtieneCapitulo(Integer capituloId) {
        if (capituloId != null) {
            CapituloDAO capituloDAO = new CapituloDAO();
            capitulo = capituloDAO.obtienvapituloPorCapituloId(capituloId);
        }
}



    public void eliminaCapitulo(Integer capituloId, Integer novelaId) {
        try {
            CapituloDAO capituloDAO = new CapituloDAO();
            capitulo = capituloDAO.obtienvapituloPorCapituloId(capituloId);
            capituloDAO.eliminaCapitulo(capitulo);
        } catch (HibernateException he) {
            he.printStackTrace();
            //algun mensaje
        }
       
    }


    public Capitulo getCapitulo() {
        return capitulo;
    }

    public void setCapitulo(Capitulo capitulo) {
        this.capitulo = capitulo;
    }

    public Integer getNovelaId() {
        return novelaId;
    }

    public void setNovelaId(Integer novelaId) {
        this.novelaId = novelaId;
    }


    
    
    
}
