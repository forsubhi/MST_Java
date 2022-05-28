package org.graph;

import com.sun.glass.ui.Clipboard;
import javafx.fxml.FXMLLoader;

import java.util.*;

public class Graph {



    public final int[] parent;
    public final int source;
    // Member variables of this class
    public double dist[];
    private Set<Integer> settled;
    private PriorityQueue<Node> pq;
    // Number of vertices
    private int V;
    List<List<Node>> adj;

    // Constructor of this class
    public Graph(int V, int source) {

        // This keyword refers to current object itself
        this.V = V;
        this.source = source;
        dist = new double[V];
        settled = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());
        parent = new int[V];
    }

    // Method 1
    // Dijkstra's Algorithm
    public void dijkstra(List<List<Node>> adj) {
        this.adj = adj;

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        // Add source node to the priority queue
        pq.add(new Node(source, 0));

        // Distance to the source is 0
        dist[source] = 0;

        while (settled.size() != V) {

            // Terminating condition check when
            // the priority queue is empty, return
            if (pq.isEmpty())
                return;

            // Removing the minimum distance node
            // from the priority queue
            int u = pq.remove().node;

            // Adding the node whose distance is
            // finalized
            if (settled.contains(u))

                // Continue keyword skips execution for
                // following check
                continue;

            // We don't have to call e_Neighbors(u)
            // if u is already present in the settled set.
            settled.add(u);

            e_Neighbours(u);
        }
    }

    // Method 2
    // To process all the neighbours
    // of the passed node
    private void e_Neighbours(int u) {

        double edgeDistance = -1;
        double newDistance = -1;

        // All the neighbors of v
        for (int i = 0; i < adj.get(u).size(); i++) {
            Node v = adj.get(u).get(i);

            // If current node hasn't already been processed
            if (!settled.contains(v.node)) {
                edgeDistance = v.cost;
                newDistance = dist[u] + edgeDistance;

                // If new distance is cheaper in cost
                if (newDistance < dist[v.node]) {
                    dist[v.node] = newDistance;
                    parent[v.node] = u;
                }

                // Add the current node to the queue
                pq.add(new Node(v.node, dist[v.node]));
            }
        }
    }

    // Main driver method







   public static double computeTotalCost(int parent[], List<List<Node>> adj) {
        double totalCost = 0;
        for (int i = 0; i < parent.length; i++) {
            List<Node> nodes = adj.get(parent[i]);
            for (Node node : nodes) {
                if (node.node == i) {
                    totalCost += node.cost;
                }
            }
        }
      return totalCost;
    }
}

// Class 2
// Helper class implementing Comparator interface
// Representing a node in the graph
class Node implements Comparator<Node> {

    // Member variables of this class
    public int node;
    public double cost;

    // Constructors of this class

    // Constructor 1
    public Node() {
    }

    // Constructor 2
    public Node(int node, double cost) {

        // This keyword refers to current instance itself
        this.node = node;
        this.cost = cost;
    }

    // Method 1
    @Override
    public int compare(Node node1, Node node2) {

        if (node1.cost < node2.cost)
            return -1;

        if (node1.cost > node2.cost)
            return 1;

        return 0;
    }
}