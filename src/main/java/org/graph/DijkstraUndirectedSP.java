package org.graph;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DijkstraUndirectedSP {
    private final int source;
    private double[] distTo;
    private Edge[] edgeTo;
    private IndexMinPQ<Double> pq;

    private static HashMap<Double, DijkstraUndirectedSP> totalCostMap = new HashMap<>();

    public DijkstraUndirectedSP(EdgeWeightedGraph graph, int s) {
        this.source = s;
        for (Edge edge : graph.edges()) {
            if (edge.weight() < 0) throw new IllegalArgumentException("edge " + edge + " has negative weight");
        }

        distTo = new double[graph.V()];
        edgeTo = new Edge[graph.V()];

        for (int i = 0; i < graph.V(); i++) {
            distTo[i] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0.0;

        pq = new IndexMinPQ<>(graph.V());
        pq.insert(s, distTo[s]);
        while (!pq.isEmpty()) {
            int v = pq.delMin();
            for (Edge edge : graph.adj(v)) relax(edge, v);
        }
        assert check(graph, s);
    }

    private void relax(Edge edge, int v) {
        int w = edge.other(v);
        if (distTo[w] > distTo[v] + edge.weight()) {
            distTo[w] = distTo[v] + edge.weight();
            edgeTo[w] = edge;
            if (pq.contains(w)) pq.decreaseKey(w, distTo[w]);
            else pq.insert(w, distTo[w]);
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Iterable<Edge> pathTo(int v) {
        if (!hasPathTo(v)) return null;
        Stack<Edge> path = new Stack<>();
        int x = v;
        for (Edge edge = edgeTo[v]; edge != null; edge = edgeTo[x]) {
            path.push(edge);
            x = edge.other(x);
        }
        return path;
    }

    private boolean check(EdgeWeightedGraph G, int s) {

        // check that edge weights are nonnegative
        for (Edge e : G.edges()) {
            if (e.weight() < 0) {
                System.err.println("negative edge weight detected");
                return false;
            }
        }

        // check that distTo[v] and edgeTo[v] are consistent
        if (distTo[s] != 0.0 || edgeTo[s] != null) {
            System.err.println("distTo[s] and edgeTo[s] inconsistent");
            return false;
        }
        for (int v = 0; v < G.V(); v++) {
            if (v == s) continue;
            if (edgeTo[v] == null && distTo[v] != Double.POSITIVE_INFINITY) {
                System.err.println("distTo[] and edgeTo[] inconsistent");
                return false;
            }
        }

        // check that all edges e = v-w satisfy distTo[w] <= distTo[v] + e.weight()
        for (int v = 0; v < G.V(); v++) {
            for (Edge e : G.adj(v)) {
                int w = e.other(v);
                if (distTo[v] + e.weight() < distTo[w]) {
                    System.err.println("edge " + e + " not relaxed");
                    return false;
                }
            }
        }

        // check that all edges e = v-w on SPT satisfy distTo[w] == distTo[v] + e.weight()
        for (int w = 0; w < G.V(); w++) {
            if (edgeTo[w] == null) continue;
            Edge e = edgeTo[w];
            if (w != e.either() && w != e.other(e.either())) return false;
            int v = e.other(w);
            if (distTo[v] + e.weight() != distTo[w]) {
                System.err.println("edge " + e + " on shortest path not tight");
                return false;
            }
        }
        return true;
    }

    public int getSource() {
        return source;
    }

    public static void main(String[] args) {
        String path = "D:\\workspace\\java\\algs4-data\\tiny2.txt";
        EdgeWeightedGraph G = new EdgeWeightedGraph(path);
        for (int i = 0; i < G.V(); i++) {
            int source = i;
            DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, source);

            double totalCost = 0;
            for (int j = 0; j < G.V(); j++) {
                Edge edge = sp.edgeTo[j];
                if (edge != null)
                    totalCost += edge.weight();
            }
            System.out.println("Total cost for source  " + source + " = " + totalCost);
            totalCostMap.put(totalCost, sp);
        }

        double minCost = Double.MAX_VALUE;
        for (Map.Entry<Double, DijkstraUndirectedSP> entry : totalCostMap.entrySet()) {
            Double cost = entry.getKey();
            if (cost < minCost)
                minCost = cost;
        }
        DijkstraUndirectedSP sp = totalCostMap.get(minCost);


        // print shortest path
        for (int t = 0; t < G.V(); t++) {
            if (sp.hasPathTo(t)) {
                StdOut.printf("%d to %d (%.2f)  ", sp.getSource(), t, sp.distTo(t));
                for (Edge e : sp.pathTo(t)) {
                    StdOut.print(e + "   ");
                }
                StdOut.println();
            } else {
                StdOut.printf("%d to %d         no path\n", sp.getSource(), t);
            }
        }

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
