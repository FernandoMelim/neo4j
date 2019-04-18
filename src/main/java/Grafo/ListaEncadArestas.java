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
public class ListaEncadArestas {

    private Aresta primeira = null;
    private Aresta ultima = null;

    public boolean listaVazia() {
        if (this.primeira == null) {
            return true;
        }
        return false;
    }

    public void setAresta(Vertice v, int pai, Aresta novaAresta) {

        novaAresta.setVerticeDestino(v);
        novaAresta.setPai(pai);

        if (this.listaVazia()) {
            this.primeira = novaAresta;
            this.ultima = novaAresta;
        } else //Lista contem ao menos 1 vertice
        {
            this.ultima.setProxima(novaAresta);
            this.ultima = novaAresta;
            this.ultima.setProxima(null);
        }
    }

    public Aresta getPrimeira() {
        return this.primeira;
    }
    
    public int getQtdArestas(){
        int qtdArestas = 0;
        for (Aresta a = this.primeira; a != null; a = a.getProxima()) {
            qtdArestas++;
        }
        return qtdArestas;
    }

    public void printArestas() {
        for (Aresta a = this.primeira; a != null; a = a.getProxima()) {
            System.out.println("Destino: " + a.getVerticeDestino().getIndice() + " Peso: " + a.getPeso());
        }
    }
}
