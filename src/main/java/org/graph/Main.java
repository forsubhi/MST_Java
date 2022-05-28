package org.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.graph.Graph.computeTotalCost;


public class Main {

    private static HashMap<Double, Graph> totalCostMap = new HashMap<>();

    public static void main(String arg[]) {

        String path = "D:\\workspace\\java\\algs4-data\\tiny2.txt";
        GraphReader graphReader = new GraphReader(path);
        for (int i = 0; i < graphReader.V; i++) {
            int source = i;
            computeMstForOneSource(graphReader.adj, graphReader.V, source);
        }

        double minCost = Double.MAX_VALUE;
        for (Map.Entry<Double, Graph> entry : totalCostMap.entrySet()) {
            Double cost = entry.getKey();
            if (cost < minCost)
                minCost = cost;
        }
        Graph gfg = totalCostMap.get(minCost);
        printMST(gfg.parent, gfg.adj);

        System.out.println("The shorted path from node :");

        for (int i = 0; i < gfg.dist.length; i++)
            System.out.println(gfg.source + " to " + i + " is "
                    + gfg.dist[i]);
    }

    private static void computeMstForOneSource(List<List<Node>> adj, int V, int source) {

        Graph dpq = new Graph(V,source);
        dpq.dijkstra(adj);
        double totalCost = computeTotalCost(dpq.parent, adj);
        System.out.println("Total cost for source " + source + " = " + totalCost);
        totalCostMap.put(totalCost, dpq);

    }

    static void printMST(int parent[], List<List<Node>> adj) {
        double totalCost = 0;
        System.out.println("Edge \tWeight");
        for (int i = 0; i < parent.length; i++) {
            List<Node> nodes = adj.get(parent[i]);
            for (Node node : nodes) {
                if (node.node == i) {
                    System.out.println(parent[i] + " - " + i + "\t" + node.cost);
                    totalCost += node.cost;
                }
            }
        }

        System.out.println("Total Cost = " + totalCost);
    }
}
