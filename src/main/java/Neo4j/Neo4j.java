/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Neo4j;

import Grafo.Aresta;
import Grafo.Grafo;
import Grafo.VerticeItem;
import java.io.File;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 *
 * @author ferna
 */
public class Neo4j {

    private static final String DB_PATH = "C:\\Users\\ferna\\OneDrive\\Documentos\\NetBeansProjects\\neo4j-community-3.5.4\\data\\databases\\graph.db";
    private GraphDatabaseService graphDb;

    // tipos de nós que haverão
    public enum NodeType implements Label {
        State;
    }

    // tipos de relação existente entre os nós
    public enum RelationType implements RelationshipType {
        Neighbour;
    }

    // Adiciona itens no banco de dados
    public void createDb(Grafo grafo) {

        this.deleteFileOrDirectory(new File(DB_PATH));
        // Acessa o banco de dados do grafo. Caso o banco de dados não exista, ele é criado.
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(DB_PATH));

        try (Transaction tx = graphDb.beginTx()) {

            // adicionando todos os vértices no banco de dados do Neo4j
            for (VerticeItem v = (VerticeItem) grafo.getVerticesDesteGrafo().getPrimeiro(); v != null; v = (VerticeItem) v.getProximo()) {

                String b = "";

                for (int j = 0; j < v.getItens().size(); j++) {
                    b = b + v.getItens().get(j);
                }
                Node newNode = graphDb.createNode(NodeType.State);
                newNode.setProperty("Indice: ", v.getIndice());
                newNode.setProperty("Lista itens: ", b);

            }

            // adicionando as arestas no banco de dados do Neo4j
            for (VerticeItem v = (VerticeItem) grafo.getVerticesDesteGrafo().getPrimeiro(); v != null; v = (VerticeItem) v.getProximo()) {
                
                Node base = graphDb.getNodeById(v.getIndice() - 1);
                
                for (Aresta a = v.getArestasDesteVertice().getPrimeira(); a != null; a = a.getProxima()) {
                    
                    Node destino = graphDb.getNodeById(a.getVerticeDestino().getIndice() - 1);
                    
                    if (destino != null) {
                        base.createRelationshipTo(destino, RelationType.Neighbour);
                    }
                }
            }

            tx.success();

        }

        this.shutDown();

    }

    // Se desconecta do banco de dados
    void shutDown() {
        graphDb.shutdown();
    }
    // Deleta o banco de dados existente

    private void deleteFileOrDirectory(File file) {
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File child : file.listFiles()) {
                    deleteFileOrDirectory(child);
                }
            }
            file.delete();
        }
    }
}
