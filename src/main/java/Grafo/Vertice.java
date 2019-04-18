/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Grafo;

/**
 *
 * @author ferna
 */
public class Vertice {

    private Vertice proximo;
    private int indice;
    private ListaEncadArestas arestasDesteVertice = new ListaEncadArestas();
    boolean visitado;

    public Vertice() {
    }

    public Vertice(int indice) {
        this.indice = indice;
    }
    
    public void setVisitado(boolean visitado) {
        this.visitado = visitado;
    }

    public boolean getVisitado() {
        return visitado;
    }

    public ListaEncadArestas getArestasDesteVertice() {
        return arestasDesteVertice;
    }

    public void setIndice(int indice) {
        this.indice = indice;
    }

    public void setProximo(Vertice proximo) {
        this.proximo = proximo;
    }

    public int getIndice() {
        return indice;
    }

    public Vertice getProximo() {
        return proximo;
    }

    public void setAresta(Vertice destino, Aresta novaAresta) {
        this.arestasDesteVertice.setAresta(destino, this.indice, novaAresta);
    }

    public Aresta getPrimAresta() {
        return this.arestasDesteVertice.getPrimeira();
    }

}
