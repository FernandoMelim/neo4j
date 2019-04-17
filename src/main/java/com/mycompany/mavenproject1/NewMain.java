package com.mycompany.mavenproject1;

import java.io.File;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;

public class NewMain {

    private static final String DB_PATH = "C:\\Users\\ferna\\OneDrive\\Documentos\\NetBeansProjects\\neo4j-community-3.5.4\\data\\databases\\graph.db";
    private GraphDatabaseService graphDb;

    public static void main(String[] args) {

        NewMain hello = new NewMain();
        deleteFileOrDirectory(new File(DB_PATH));
        hello.createDb();
        hello.shutDown();

    }

    // tipos de nós que haverão
    public enum NodeType implements Label {
        Person, Course;
    }

    // tipos de relação existente entre os nós
    public enum RelationType implements RelationshipType {
        Knows, BelongsTo;
    }

    // Adiciona itens no banco de dados
    void createDb() {
        // Acessa o banco de dados do grafo. Caso o banco de dados não exista, ele é criado.
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File(DB_PATH));

        try (Transaction tx = graphDb.beginTx()) {

            // CRIANDO GRAFO
            Node bobNode = graphDb.createNode(NodeType.Person);
            bobNode.setProperty("PID", 5001);
            bobNode.setProperty("Name", "Bob");
            bobNode.setProperty("Age", 23);

            Node aliceNode = graphDb.createNode(NodeType.Person);
            aliceNode.setProperty("PID", 5002);
            aliceNode.setProperty("Name", "Alice");
            aliceNode.setProperty("Age", 40);

            Node eveNode = graphDb.createNode(NodeType.Person);
            eveNode.setProperty("PID", 5001);
            eveNode.setProperty("Name", "Eve");
            eveNode.setProperty("Age", 15);

            Node itNode = graphDb.createNode(NodeType.Course);
            itNode.setProperty("ID", 1);
            itNode.setProperty("Name", "IT Beginners");
            itNode.setProperty("Location", "Room 153");

            Node eletronicNode = graphDb.createNode(NodeType.Course);
            eletronicNode.setProperty("Name", "Eletronic Advanced");

            bobNode.createRelationshipTo(aliceNode, RelationType.Knows);

            Relationship bobRelIt = bobNode.createRelationshipTo(itNode, RelationType.BelongsTo);
            bobRelIt.setProperty("Function", "Student");

            Relationship bobReleletronics = bobNode.createRelationshipTo(eletronicNode, RelationType.BelongsTo);
            bobReleletronics.setProperty("function", "supply teatcher");

            Relationship aliceRelIt = aliceNode.createRelationshipTo(itNode, RelationType.BelongsTo);
            aliceRelIt.setProperty("Function", "Teacher");

            tx.success();

        }

    }

    // Se desconecta do banco de dados
    void shutDown() {
        graphDb.shutdown();
    }

    // Deleta o banco de dados existente
    private static void deleteFileOrDirectory(File file) {
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
