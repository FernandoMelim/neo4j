package com.mycompany.mavenproject1;

import java.io.File;
import java.sql.DriverManager;
import java.util.Iterator;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.driver.v1.GraphDatabase;

/*
import org.neo4j.driver.v1.*;
import static org.neo4j.driver.v1.Values.parameters;*/
public class NewMain {

    /*
    private final Driver driver;

    public NewMain(String uri, String user, String password) {
        driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password));
    }

    public void close() throws Exception {
        driver.close();
    }

    private void addNode(String name) {
        // Sessions are lightweight and disposable connection wrappers.
        try (Session session = driver.session()) {
            // Wrapping Cypher in an explicit transaction provides atomicity
            // and makes handling errors much easier.
            try (Transaction tx = session.beginTransaction()) {
                tx.run("MERGE (a:Node {name: {x}})", parameters("x", name));
                tx.success();  // Mark this write as successful.
            }
        }
    }

    private void printPeople(String initial) {
        try (Session session = driver.session()) {
            // Auto-commit transactions are a quick and easy way to wrap a read.
            StatementResult result = session.run(
                    "MATCH (a:Person) WHERE a.name STARTS WITH {x} RETURN a.name AS name",
                    parameters("x", initial));
            // Each Cypher execution returns a stream of records.
            while (result.hasNext()) {
                Record record = result.next();
                // Values can be extracted from a record by index or name.
                System.out.println(record.get("name").asString());
            }
        }
    }

    public static void main(String[] args) throws Exception {

        NewMain example = new NewMain("bolt://localhost:7687", "neo4j", "oi");
        example.addNode("Ada");
        example.addNode("Alice");
        example.addNode("Fernandoo");
        example.printPeople("A");
        example.close();

    }*/
 /*    
    A parte comentada é a outra forma de manipular o banco
     */
    
    // tipos de nós que haverão
    public enum NodeType implements Label {
        Person, Course;
    }

    // tipos de relação existente entre os nós
    public enum RelationType implements RelationshipType {
        Knows, BelongsTo;
    }

    public static void main(String[] args) {
        
        // deletar diretório do banco de dados
        deleteFileOrDirectory(new File("C:\\Users\\ferna\\OneDrive\\Documentos\\NetBeansProjects\\neo4j-community-3.5.4\\data\\databases\\graph.db"));
        

        // cria o diretório do banco de dados para começar um grafo zerado
        GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(new File("C:\\Users\\ferna\\OneDrive\\Documentos\\NetBeansProjects\\neo4j-community-3.5.4\\data\\databases\\graph.db"));
        
        
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
        graphDb.shutdown();

    }

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
