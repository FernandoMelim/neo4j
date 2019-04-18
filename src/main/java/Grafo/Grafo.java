package Grafo;

import java.util.Random;

public class Grafo {

    private ListEncadVertices verticesDesteGrafo = new ListEncadVertices();
    private int numeroDeVertices;
    private int numeroDeArestas;
    private int maxArestasPorVertice;
    private double[][] matrizAdjacencia;
    private double[][] matrizDistancias;

    public Grafo() {
        this.numeroDeArestas = 0;
        this.numeroDeVertices = 0;
        this.maxArestasPorVertice = 5;
    }

    public Grafo(int quantidadeVertices) {
        this.numeroDeArestas = 0;
        this.numeroDeVertices = 0;
        this.maxArestasPorVertice = 5;
        
        for (int i = 1; i <= quantidadeVertices; i++) {
            System.out.println(i);
            Vertice novoVertice = new Vertice(i);
            this.setVertice(novoVertice);
        }
    }

    public int getNumeroDeVertices() {
        return numeroDeVertices;
    }

    public ListEncadVertices getVerticesDesteGrafo() {
        return verticesDesteGrafo;
    }

    public void setNumeroDeVertices(int numeroDeVertices) {
        this.numeroDeVertices = numeroDeVertices;
    }

    public int getMaxArestasPorVertice() {
        return maxArestasPorVertice;
    }

    public void setMaxArestasPorVertice(int maxArestasPorVertice) {
        this.maxArestasPorVertice = maxArestasPorVertice;
    }

    public boolean verificaExistenciaDoVertice(int indice) {

        for (Vertice v = this.verticesDesteGrafo.getPrimeiro(); v != null; v = v.getProximo()) {
            if (v.getIndice() == indice) {
                return true;
            }
        }
        return false;
    }

    public Vertice getVertice(int indice) {
        return verticesDesteGrafo.getVerticePorIndice(indice);
    }

    public void setVertice(Vertice novoVertice) {

        if (!verificaExistenciaDoVertice(novoVertice.getIndice())) {
            this.verticesDesteGrafo.setVertice(novoVertice);
            this.numeroDeVertices += 1;
        } else {
            System.out.println("Vértice já existe. Não foi adicionado.");
        }
    }

    public void setAresta(Aresta novaAresta, int verticeBase, int verticeDestino) {

        if (verificaExistenciaDoVertice(verticeBase) && verificaExistenciaDoVertice(verticeDestino)) {
            Vertice base = null;
            Vertice destino = null;
            for (Vertice v = this.verticesDesteGrafo.getPrimeiro(); v != null; v = v.getProximo()) {
                if (v.getIndice() == verticeBase) {
                    base = v;
                }

                if (v.getIndice() == verticeDestino) {
                    destino = v;
                }

                if (base != null && destino != null) {
                    break;
                }
            }

            base.setAresta(destino, novaAresta);

        } else {
            //System.out.println("Algum dos vértices não existem.");
        }

    }

    public void printGrafo() {
        for (Vertice v = this.verticesDesteGrafo.getPrimeiro(); v != null; v = v.getProximo()) {
            System.out.println("Vértice " + v.getIndice() + ": ");
            v.getArestasDesteVertice().printArestas();
        }
        System.out.println();
    }

    public double[][] getMatrizAdjacencia() {
        return matrizAdjacencia;
    }

    public void setMatrizAdjacencia(double[][] matrizAdjacencia) {
        this.matrizAdjacencia = matrizAdjacencia;
    }

    public void atualizaMatrizAdjacencia() {
        this.matrizAdjacencia = new double[this.numeroDeVertices][this.numeroDeVertices];
        Vertice auxVertice = verticesDesteGrafo.getPrimeiro();
        Aresta auxAresta;
        while (auxVertice != null) {
            auxAresta = auxVertice.getArestasDesteVertice().getPrimeira();
            while (auxAresta != null) {
                matrizAdjacencia[auxVertice.getIndice() - 1][auxAresta.getVerticeDestino().getIndice() - 1] = auxAresta.getPeso();
                auxAresta = auxAresta.getProxima();
            }
            auxVertice = auxVertice.getProximo();
        }
    }

    public void imprimeMatrizAdjacencia() {
        String texto = "";
        for (int i = 0; i < this.matrizAdjacencia.length; i++) {
            for (int j = 0; j < this.matrizAdjacencia[i].length; j++) {
                texto = texto + "matAdj[" + i + "][" + j + "]: " + this.matrizAdjacencia[i][j] + "  -  ";
            }
            texto = texto + "\n";
        }
        System.out.println(texto);
    }

    public double[][] getMatrizDistancias() {
        return matrizDistancias;
    }

    public void setMatrizDistancias(double[][] matrizDistancias) {
        this.matrizDistancias = matrizDistancias;
    }

    public void atualizaMatrizDistancias() {
        this.matrizDistancias = new double[this.numeroDeVertices][this.numeroDeVertices];
        for (int i = 0; i < this.numeroDeVertices; i++) {
            double[] distAux = algoritmoDijkstra(i + 1);
            for (int j = 0; j < this.numeroDeVertices; j++) {
                this.matrizDistancias[i][j] = distAux[j];
            }
        }
    }

    public void imprimeMatrizDistancias() {
        String texto = "";
        for (int i = 0; i < this.matrizDistancias.length; i++) {
            for (int j = 0; j < this.matrizDistancias[i].length; j++) {
                texto = texto + "matDist[" + i + "][" + j + "]: " + this.matrizDistancias[i][j] + "  -  ";
            }
            texto = texto + "\n";
        }
        System.out.println(texto);
    }

    public double minDistance(double dist[], boolean visitados[]) {
        // Initialize min value 
        double min = Double.MAX_VALUE, min_index = -1;

        for (int v = 0; v < this.numeroDeVertices; v++) {
            if (visitados[v] == false && dist[v] <= min) {
                min = dist[v];
                min_index = v;
            }
        }

        return min_index;
    }

    // Calcula a distancia entre um vértice determinado e todos os outros através do algoritmo de Dijkstra 
    // aplicado em uma matriz de adjacência
    public double[] algoritmoDijkstra(int origem) {
        double[] dist = new double[this.numeroDeVertices];
        boolean[] visitados = new boolean[this.numeroDeVertices];
        double infinito = Double.MAX_VALUE;//Integer.MAX_VALUE;                               // Valor bem grande para representar quando não há aresta de um vertice para o outro
        for (int i = 0; i < this.numeroDeVertices; i++) {
            dist[i] = infinito;
            visitados[i] = false;
        }

        dist[origem - 1] = 0;               // Distancia da fonte pra ela mesma é sempre 0

        for (int i = 0; i < this.numeroDeVertices - 1; i++) {

            double u = minDistance(dist, visitados);

            visitados[(int) u] = true;

            for (int j = 0; j < this.numeroDeVertices; j++) {
                if (!visitados[j] && this.matrizAdjacencia[(int) u][j] != 0 && dist[(int) u] != infinito && dist[(int) u] + this.matrizAdjacencia[(int) u][j] < dist[j]) {
                    dist[j] = dist[(int) u] + this.matrizAdjacencia[(int) u][j];
                }
            }
        }

        return dist;
    }

    // essa função verifica se tem uma aresta orientada saindo do vertice base para o vertice destino
    public boolean verificaExistenciaAresta(int verticeBas, int verticeDest) {
        if (verificaExistenciaDoVertice(verticeBas) && verificaExistenciaDoVertice(verticeDest)) {

            Vertice verticeBase = new Vertice();

            for (Vertice v = this.verticesDesteGrafo.getPrimeiro(); v != null; v = v.getProximo()) {
                if (v.getIndice() == verticeBas) {
                    verticeBase = v;
                }

            }

            for (Aresta a = verticeBase.getArestasDesteVertice().getPrimeira(); a != null; a = a.getProxima()) {
                if (a.getVerticeDestino().getIndice() == verticeDest) {
                    return true;
                }
            }
        }
        return false;
    }

    private void auxiliarEhConexo(Vertice visitando) {
        visitando.setVisitado(true);
        for (Aresta a = visitando.getPrimAresta(); a != null; a = a.getProxima()) {
            if (a.getVerticeDestino().getVisitado() == false) {
                auxiliarEhConexo(a.getVerticeDestino());
            }
        }
    }

    // retorna true se existe pelo menos um caminho entre todos os pares de vértices do grafo
    // busca de profundidade utilizada
    public boolean ehConexo() {

        //escolhe o vértice inicial da busca em profundidade
        for (Vertice a = this.verticesDesteGrafo.getPrimeiro(); a != null; a = a.getProximo()) {

            // marca todos os vértices como "não visitados"
            for (Vertice v = this.verticesDesteGrafo.getPrimeiro(); v != null; v = v.getProximo()) {
                v.setVisitado(false);
            }

            // percorre o grafo para verificar se existe um caminho entre todos os pares de vértices
            // marca os vértices como "visitado" se ele foi visitado
            auxiliarEhConexo(a);

            // verifica se todos os vértices foram visitados
            for (Vertice v = this.verticesDesteGrafo.getPrimeiro(); v != null; v = v.getProximo()) {
                if (v.getVisitado() == false) {
                    return false;
                }
            }
        }

        return true;
    }

    public int numeroAleatorio(int min, int max) {

        Random rand = new Random();
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public float numeroAleatorioFloat(int min, int max) {

        Random rand = new Random();
        float randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public void criarArestasAutomaticamente() {

        while (!ehConexo()) {
            int primeiroVertice = numeroAleatorio(0, numeroDeVertices);
            int segundoVertice = numeroAleatorio(0, numeroDeVertices);

            if (primeiroVertice != segundoVertice) {
                if (!this.verificaExistenciaAresta(primeiroVertice, segundoVertice)) {
                    Aresta ida = new Aresta(1);
                    Aresta volta = new Aresta(2);

                    this.setAresta(ida, primeiroVertice, segundoVertice);
                    this.setAresta(volta, segundoVertice, primeiroVertice);
                }

            }

        }
    }

    public String impressaoGraphViz() {
        StringBuilder dotFormat = new StringBuilder();
        VerticeItem vertice = (VerticeItem) verticesDesteGrafo.getPrimeiro();     //vertice = verticesDesteGrafo.getPrimeiro();
        VerticeItem aux;
        while (vertice != null) {
            Aresta aresta = vertice.getArestasDesteVertice().getPrimeira();
            while (aresta != null) {
                aux = (VerticeItem) aresta.getVerticeDestino();
                dotFormat.append(vertice.getLabel() + "->" + aux.getLabel() + ";");
                aresta = aresta.getProxima();
            }
            vertice = (VerticeItem) vertice.getProximo();
        }
        return dotFormat.toString();
    }
}
