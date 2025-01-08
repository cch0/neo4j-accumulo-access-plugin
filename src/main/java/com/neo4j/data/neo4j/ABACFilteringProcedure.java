package com.neo4j.data.neo4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import org.apache.accumulo.access.AccessEvaluator;
import org.apache.accumulo.access.Authorizations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.procedure.Context;
import org.neo4j.procedure.Description;
import org.neo4j.procedure.Mode;
import org.neo4j.procedure.Name;
import org.neo4j.procedure.Procedure;

public class ABACFilteringProcedure {
  private static final Logger logger = LogManager.getLogger(ABACFilteringProcedure.class);
  // Create a query to find nodes and extract label value
  private static final String CYPHER_QUERY_FORMAT =
      "MATCH (n) WHERE n.%s IS NOT NULL RETURN n, n.%s as labelValue";

  @Context public GraphDatabaseService db;

  @Procedure(name = "com.neo4j.ABACFiltering", mode = Mode.READ)
  @Description("Filters nodes by security label")
  public Stream<NodeResult> filterNodesByProperty(
      @Name("labelField") String labelField, @Name("authorizations") String authorizations) {

    String cypherQuery = CYPHER_QUERY_FORMAT.formatted(labelField, labelField);

    List<NodeResult> results = new ArrayList<>();

    try (Transaction tx = db.beginTx()) {
      Result result = tx.execute(cypherQuery);

      while (result.hasNext()) {
        var row = result.next();
        String labelValue = (String) row.getOrDefault("labelValue", "");

        AccessEvaluator evaluator =
            AccessEvaluator.of(Authorizations.of(Set.of(authorizations.split(","))));

        boolean canAccess = evaluator.canAccess(labelValue);

        logger.debug(
            "labelField:{}, labelValue:{}, authorizations:{}, canAccess?:{}",
            labelField,
            labelValue,
            authorizations,
            canAccess);

        if (canAccess) {
          Node node = (Node) row.get("n");
          results.add(new NodeResult(node.getElementId()));
        }
      }
      tx.commit();
    } catch (Exception e) {
      logger.warn("Error during ABAC filtering", e);
    }

    return results.stream();
  }

  public static class NodeResult {
    public final String id;

    public NodeResult(String elementId) {
      this.id = elementId;
    }
  }
}
