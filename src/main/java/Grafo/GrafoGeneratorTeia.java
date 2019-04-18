package Grafo;

import java.util.ArrayList;
import java.util.List;

public class GrafoGeneratorTeia implements GrafoGenerator {

    @Override
    public Grafo getGrafo(int qtdVertices) {

        int n = 1;
        Grafo novoGrafo = new Grafo();
        VerticeItem centro = new VerticeItem(n++);
        novoGrafo.setVertice(centro);
        List<VerticeItem> nAtual = new ArrayList<VerticeItem>();
        
        if(qtdVertices <= 6){
            for (int i = 0; i < qtdVertices - 1; i++) {
                VerticeItem v = new VerticeItem(n++);
                novoGrafo.setVertice(v);
                centro.setAresta(v, new Aresta(1));
                v.setAresta(centro, new Aresta(1));
                nAtual.add(v);
            }
        }
        else{
            for (int i = 0; i < novoGrafo.getMaxArestasPorVertice(); i++) {
                VerticeItem v = new VerticeItem(n++);
                novoGrafo.setVertice(v);
                centro.setAresta(v, new Aresta(1));
                v.setAresta(centro, new Aresta(1));
                nAtual.add(v);
            }
            List<VerticeItem> nAux = new ArrayList<VerticeItem>();
        
            int sorteioQtd = 0;

            while(n <= qtdVertices + 1){
                nAux.clear();
                for(int j = 1; j < nAtual.size(); j++){
                    if(novoGrafo.numeroAleatorio(0, 1) == 1){               //Aresta para a esquerda
                        if(nAtual.get(j - 1).getArestasDesteVertice().getQtdArestas() < novoGrafo.getMaxArestasPorVertice()){
                            nAtual.get(j).setAresta(nAtual.get(j - 1), new Aresta(1));
                            nAtual.get(j - 1).setAresta(nAtual.get(j), new Aresta(1));
                        }
                    }
                }
                for(int j = 0; j < nAtual.size(); j++){
                    sorteioQtd = novoGrafo.numeroAleatorio(0, novoGrafo.getMaxArestasPorVertice() - nAtual.get(j).getArestasDesteVertice().getQtdArestas());
                    for (int k = 0; k < sorteioQtd; k++) {
                        if(n >= qtdVertices + 1){
                           break;
                        }
                        VerticeItem v = new VerticeItem(n++);
                        novoGrafo.setVertice(v);
                        nAtual.get(j).setAresta(v, new Aresta(1));
                        v.setAresta(nAtual.get(j),  new Aresta(1));
                        nAux.add(v);
                    }
                }
                if(n >= qtdVertices + 1){
                    break;
                }
                nAtual.clear();
                nAtual.addAll(nAux);
            }
        }
        //VerticeItem teste = new VerticeItem(n++);
        //novoGrafo.setVertice(teste);
        //teste.addItem("teste");
        novoGrafo.atualizaMatrizAdjacencia();       //Após criar o grafo todo, é salvo a matriz de adjacencia correspondente para poder utilizar o algoritmo de Dijkstra
        novoGrafo.atualizaMatrizDistancias();
        //novoGrafo.imprimeMatrizDistancias();
        //novoGrafo.imprimeMatrizAdjacencia();
        
        return novoGrafo;
    }
}
