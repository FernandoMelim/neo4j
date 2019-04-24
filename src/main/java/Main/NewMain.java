package Main;

import Grafo.Grafo;
import Grafo.GrafoGenerator;
import Grafo.GrafoGeneratorTeia;
import Neo4j.Neo4j;

public class NewMain {

    public static void main(String[] args) {

        GrafoGenerator gerador = new GrafoGeneratorTeia();
        
        Grafo grafo = gerador.getGrafo(20);

        Neo4j neo = new Neo4j();
        neo.createDb(grafo);

    }

}
