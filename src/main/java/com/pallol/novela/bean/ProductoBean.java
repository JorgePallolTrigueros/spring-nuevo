package com.pallol.novela.bean;

import com.pallol.novela.dao.CompraRealizadaDAO;
import com.pallol.novela.dao.ProductoCompraDAO;
import com.pallol.novela.dao.ProductoDAO;
import com.pallol.novela.dao.UsuarioDAO;
import com.pallol.novela.entities.CompraRealizada;
import com.pallol.novela.entities.Producto;
import com.pallol.novela.entities.ProductoCompra;
import com.pallol.novela.entities.Usuario;
import com.pallol.novela.util.ListaElementosPaginados;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author casa pallol
 */
@ManagedBean
@SessionScoped
//@RequestScoped - Vive solo hasta que termina de cargar una pagina xhtml
//@ViewScoped - Util para llamadas jQuery o Ajax
public class ProductoBean implements Serializable {

    @Inject
    private UsuarioBean usuarioBean;
    public List<Producto> lstProducto;
    private Producto producto;
    private ListaElementosPaginados<Producto> listaElementosPaginados;
    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private Usuario usuario;
    private Integer usuarioId;
    private String tablaProductoId = "tabla_producto";

    public ProductoBean() {
        producto = new Producto();
        
        //Obtener usuario de session
        FacesContext context = FacesContext.getCurrentInstance();
        Application application = context.getApplication();
        usuarioBean = application.evaluateExpressionGet(context, "#{usuarioBean}", UsuarioBean.class);
        usuario = usuarioBean.getUsuario();
        usuarioId = usuario.getUsuarioId();
    }

    public List<Producto> muestraProductos() {
        ProductoDAO productoDAO = new ProductoDAO();
        lstProducto = productoDAO.obtieneProducto();

        return lstProducto;
    }

    //Jorge - Metodo que muestra el contenido del carrito hasta ese momento
    public List<ProductoCompra> muestraProductosUsuario() {
        ProductoCompraDAO productoCompraDAO = new ProductoCompraDAO();
        List<ProductoCompra> lstProductoCompras = productoCompraDAO.obtieneProductoCompraPorUsuarioId(usuarioId);

        return lstProductoCompras;
    }

    public List<Producto> muestraProductoPaginado() {
        ProductoDAO productoDAO = new ProductoDAO();
        lstProducto = productoDAO.obtieneProducto();
        if (listaElementosPaginados == null) {
            listaElementosPaginados = new ListaElementosPaginados(lstProducto, 0);
        }

        return listaElementosPaginados.getElementos();
    }

    public void anadeProductoCarrito(Long productoId, Long cantidad) {

        CompraRealizadaDAO compraRealizadaDAO = new CompraRealizadaDAO();
        CompraRealizada compraRealizada = compraRealizadaDAO.obtieneCompraRealizadaPorUsuarioId(usuarioId);

        //En caso de que no haya un proceso de compra relacionada, abre un nuevo proceso (o carrito)
        if (compraRealizada == null) {
            compraRealizada = compraRealizadaDAO.insertaCompra(usuarioId);
        }

        ProductoCompraDAO productoCompraDAO = new ProductoCompraDAO();
        productoCompraDAO.insertaProductoCompra(compraRealizada, productoId.intValue(), cantidad.intValue());
    }

    public void actualizaProductoCompra(Integer productoCompraId, Integer cantidad) {

        ProductoCompra productoCompra = new ProductoCompra();
        productoCompra.setId(productoCompraId);
        productoCompra.setCantidad(cantidad);

        ProductoCompraDAO productoCompraDAO = new ProductoCompraDAO();
        productoCompraDAO.actualizaProductoCompra(productoCompra);
    }

    public Integer obtieneCuentaTotalProductos() {
        ProductoCompraDAO productoCompraDAO = new ProductoCompraDAO();
        return productoCompraDAO.obtieneTotalProductoCompraPorUsuarioId(usuarioId);
    }

    public void finalizaCompra() {
        CompraRealizadaDAO compraRealizadaDAO = new CompraRealizadaDAO();
        ProductoCompraDAO productoCompraDAO = new ProductoCompraDAO();
        Integer montoTotal = productoCompraDAO.obtieneTotalProductoCompraPorUsuarioId(usuarioId);
        compraRealizadaDAO.actualizaCompraRealizada(usuarioId, montoTotal);
    }

    public void eliminaProductoCarrito(Integer productoCompraId) {

        ProductoCompraDAO productoCompraDAO = new ProductoCompraDAO();
        productoCompraDAO.eliminaProductoCompra(productoCompraId);
    }

    public void obtieneProducto(Integer productoId) {
        if (productoId != null) {
            ProductoDAO productoDAO = new ProductoDAO();
            producto = productoDAO.obtieneProductoPorId(productoId);
        }
    }

    public void reset() {
        PrimeFaces.current().resetInputs("form:pqcuenta");
    }

    public List<Producto> getLstProducto() {
        return lstProducto;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public ListaElementosPaginados<Producto> getListaElementosPaginados() {
        return listaElementosPaginados;
    }

    public void setListaElementosPaginados(ListaElementosPaginados<Producto> listaElementosPaginados) {
        this.listaElementosPaginados = listaElementosPaginados;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getTablaProductoId() {
        return tablaProductoId;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
