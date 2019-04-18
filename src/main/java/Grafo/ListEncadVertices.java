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
public class ListEncadVertices {

    private Vertice primeiro;
    private Vertice ultimo;

    public boolean checkIfListaVazia() {
        if (this.primeiro == null) {
            return true;
        }
        return false;
    }

    public void setVertice(Vertice novoVertice) {

        if (this.checkIfListaVazia()) {
            this.primeiro = novoVertice;
            this.ultimo = this.primeiro;
            this.ultimo.setProximo(null);
        } else //Lista contem ao menos 1 vertice
        {
            this.ultimo.setProximo(novoVertice);
            this.ultimo = novoVertice;
            this.ultimo.setProximo(null);
        }
    }
    
    public Vertice getVerticePorIndice(int indice){
        Vertice aux = this.primeiro;
        while(aux != null){
            if(aux.getIndice() == indice){
                return aux;
            }
            else{
                aux = aux.getProximo();
            }
        }
        return null;
    }

    public Vertice getPrimeiro() {
        return primeiro;
    }
    
    public Vertice getUltimo()
    {
        return ultimo;
    }

}
