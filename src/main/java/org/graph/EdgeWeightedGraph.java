package org.graph;


import java.io.File;
import java.util.Scanner;


public class EdgeWeightedGraph {
    private static final String NEWLINE = System.getProperty("line.separator");
    private final int V;
    private int E;
    private Bag<Edge>[] adj;

    public EdgeWeightedGraph(int v) {
        this.V = v;
        this.E = 0;
        adj = (Bag<Edge>[]) new Bag[v];
        for (int i = 0; i < v; i++) {
            adj[i] = new Bag<>();
        }
    }

    public EdgeWeightedGraph(String path) {
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            this.V = scanner.nextInt();
            this.adj = (Bag<Edge>[]) new Bag[this.V];
            for (int i = 0; i < V; i++) {
                adj[i] = new Bag<>();
            }
            int e = scanner.nextInt();
            if (e < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
            while (e > 0) {
                int v = scanner.nextInt();
                int w = scanner.nextInt();
                double weight = scanner.nextDouble();
                addEdge(new Edge(v, w, weight));
                e--;
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", ex);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<>();
        for (int i = 0; i < V; i++) {
            int count = 0;
            for (Edge e : adj(i)) {
                if (e.other(i) > i) {
                    list.add(e);
                } else if (e.other(i) == i) {
                    if (count%2 == 0) list.add(e);
                    count++;
                }
            }
        }
        return list;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " " + E + NEWLINE);
        for (int v = 0; v < V; v++) {
            s.append(v + ": ");
            for (Edge e : adj[v]) {
                s.append(e + "  ");
            }
            s.append(NEWLINE);
        }
        return s.toString();
    }
}
