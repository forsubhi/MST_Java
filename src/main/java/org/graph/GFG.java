package org.graph;

import java.util.*;

public class GFG {

    private final int[] parent;
    // Member variables of this class
    private double dist[];
    private Set<Integer> settled;
    private PriorityQueue<Node> pq;
    // Number of vertices
    private int V;
    List<List<Node>> adj;

    // Constructor of this class
    public GFG(int V) {

        // This keyword refers to current object itself
        this.V = V;
        dist = new double[V];
        settled = new HashSet<Integer>();
        pq = new PriorityQueue<Node>(V, new Node());
        parent = new int[V];
    }

    // Method 1
    // Dijkstra's Algorithm
    public void dijkstra(List<List<Node>> adj, int src) {
        this.adj = adj;

        for (int i = 0; i < V; i++)
            dist[i] = Integer.MAX_VALUE;

        // Add source node to the priority queue
        pq.add(new Node(src, 0));

        // Distance to the source is 0
        dist[src] = 0;

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
    public static void main(String arg[]) {

        String path = "D:\\workspace\\java\\algs4-data\\tiny2.txt";
        GraphReader graphReader = new GraphReader(path);

        int V = 9;
        int source = 0;

        // Adjacency list representation of the
        // connected edges by declaring List class object
        // Declaring object of type List<Node>
        List<List<Node>> adj
                = new ArrayList<List<Node>>();

        // Initialize list for every node
        for (int i = 0; i < V; i++) {
            List<Node> item = new ArrayList<Node>();
            adj.add(item);
        }

        // Inputs for the GFG(dpq) graph
        adj.get(0).add(new Node(1, 4));
        adj.get(0).add(new Node(7, 8));

        adj.get(1).add(new Node(0, 4));
        adj.get(1).add(new Node(2, 8));
        adj.get(1).add(new Node(7, 11));

        adj.get(2).add(new Node(1, 8));
        adj.get(2).add(new Node(3, 7));
        adj.get(2).add(new Node(5, 4));
        adj.get(2).add(new Node(8, 2));

        adj.get(3).add(new Node(2, 7));
        adj.get(3).add(new Node(4, 9));
        adj.get(3).add(new Node(5, 14));

        adj.get(4).add(new Node(3, 9));
        adj.get(4).add(new Node(5, 10));

        adj.get(5).add(new Node(2, 4));
        adj.get(5).add(new Node(3, 14));
        adj.get(5).add(new Node(4, 10));
        adj.get(5).add(new Node(6, 2));

        adj.get(6).add(new Node(5, 2));
        adj.get(6).add(new Node(7, 1));
        adj.get(6).add(new Node(8, 6));

        adj.get(7).add(new Node(0, 8));
        adj.get(7).add(new Node(1, 11));
        adj.get(7).add(new Node(8, 7));
        adj.get(7).add(new Node(6, 1));

        // Calculating the single source shortest path
        GFG dpq = new GFG(V);
        dpq.dijkstra(adj, source);

        // Printing the shortest path to all the nodes
        // from the source node
        System.out.println("The shorted path from node :");

        for (int i = 0; i < dpq.dist.length; i++)
            System.out.println(source + " to " + i + " is "
                    + dpq.dist[i]);
        printMST(dpq.parent, adj);
    }

    static void printMST(int parent[], List<List<Node>> adj) {
        System.out.println("Edge \tWeight");
        for (int i = 1; i < parent.length; i++) {
            List<Node> nodes = adj.get(parent[i]);
            for (Node node : nodes) {
                if(node.node==i)
                    System.out.println(parent[i] + " - " + i + "\t" + node.cost);
            }

        }
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