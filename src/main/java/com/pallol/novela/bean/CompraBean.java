/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.bean;

import com.pallol.novela.dao.CategoriaDAO;
import com.pallol.novela.dao.CompraRealizadaDAO;
import com.pallol.novela.dao.ProductoDAO;
import com.pallol.novela.entities.CompraRealizada;
import com.pallol.novela.entities.Producto;
import com.pallol.novela.util.ListaElementosPaginados;
import java.util.List;
import org.primefaces.PrimeFaces;

/**
 *
 * @author casa pallol
 */
public class CompraBean {

    public List<CompraRealizada> lstProducto;
        private ListaElementosPaginados<Producto> listaElementosPaginados;
    private String tablaProductoId = "tabla_producto";

    public CompraBean() {

    }

    public List<CompraRealizada> muestraComprasRealizadas() {
        CompraRealizadaDAO compraRealizadaDAO = new CompraRealizadaDAO();
//        lstProducto = compraRealizadaDAO.obtiene();

        return lstProducto;
    }


    public void insertaComprasRealizada() {

    }


    public void reset() {
        PrimeFaces.current().resetInputs("form:pqcuenta");
    }


    public ListaElementosPaginados<Producto> getListaElementosPaginados() {
        return listaElementosPaginados;
    }

    public void setListaElementosPaginados(ListaElementosPaginados<Producto> listaElementosPaginados) {
        this.listaElementosPaginados = listaElementosPaginados;
    }

    public String getTablaProductoId() {
        return tablaProductoId;
    }

}
