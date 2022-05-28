package org.graph;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GraphReader {

    public final int V;

    public List<List<Node>> adj;

    public GraphReader(String path)
    {
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            this.V = scanner.nextInt();
            this.adj = new ArrayList<>();
            for (int i = 0; i < V; i++) {
                List<Node> item = new ArrayList<Node>();
                adj.add(item);
            }


            int e = scanner.nextInt();
            if (e < 0) throw new IllegalArgumentException("Number of edges must be nonnegative");
            while (e > 0) {
                int v = scanner.nextInt();
                int w = scanner.nextInt();
                double weight = scanner.nextDouble();
                adj.get(v).add(new Node(w,weight));
                e--;
            }
        } catch (Exception ex) {
            throw new IllegalArgumentException("invalid input format in Graph constructor", ex);
        }
    }
}
