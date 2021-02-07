/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pallol.novela.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Rafael
 * @param <T>
 */
public class ListaElementosPaginados<T> implements Serializable {

    private List<T> elementos;
    //Esto lo hacemos para poder utilizar el repeat
    private List<Integer> listaTotalPaginas;
    private int tamano;
    private int numeroPaginaSeleccionada;
    private Integer totalPaginas;
    private static final int noDeRegistroPorPagina = 10;

    public ListaElementosPaginados(List<T> elementos, int pagInicial) {
        this.tamano = elementos.size();
        this.elementos = elementos;
        if (elementos.size() % noDeRegistroPorPagina != 0) {
            this.totalPaginas = elementos.size() / noDeRegistroPorPagina + 1;
        } else {
            this.totalPaginas = elementos.size() / noDeRegistroPorPagina;
        }

        if (elementos.size() == 0) {
            this.totalPaginas = 1;
        }
        this.numeroPaginaSeleccionada = pagInicial;
        
        if(listaTotalPaginas == null){
            listaTotalPaginas = new ArrayList<>();
            for(int i = 0; i < totalPaginas; i++){
                listaTotalPaginas.add(i + 1);
            }
        }
    }

    public List<T> getElementos() {
        List<T> listaAux = new ArrayList<>();
        int indiceInicial = numeroPaginaSeleccionada * noDeRegistroPorPagina;
        for (int i = indiceInicial; i < indiceInicial + noDeRegistroPorPagina && i < tamano; i++) {
            listaAux.add(elementos.get(i));
        }
        return listaAux;
    }

    public int getNumeroPaginaSeleccionada() {
        return numeroPaginaSeleccionada;
    }

    public Integer getTotalPaginas() {
        return totalPaginas;
    }

    public void setTotalPaginas(Integer totalPaginas) {
        this.totalPaginas = totalPaginas;
    }

    public int getNoDeRegistroPorPagina() {
        return noDeRegistroPorPagina;
    }

    public int getTamano() {
        return tamano;
    }

    public T get(int indice) {
        return elementos.get(indice);
    }

    public void setNumeroPaginaSeleccionada(int numeroPaginaSeleccionada) {
        if (numeroPaginaSeleccionada < 0) {
            this.numeroPaginaSeleccionada = 0;
        } else if (numeroPaginaSeleccionada >= totalPaginas) {
            this.numeroPaginaSeleccionada = totalPaginas - 1;
        } else {
            this.numeroPaginaSeleccionada = numeroPaginaSeleccionada;
        }

    }

    public List<Integer> getListaTotalPaginas() {
        return listaTotalPaginas;
    }
}
