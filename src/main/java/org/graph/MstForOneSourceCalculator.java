package org.graph;

import java.util.concurrent.ConcurrentHashMap;

public class MstForOneSourceCalculator implements Runnable {


    private final EdgeWeightedGraph edgeWeightedGraph;
    private final int source;
    private final ConcurrentHashMap<Double, DijkstraUndirectedSP> totalCostMap;

    public MstForOneSourceCalculator(EdgeWeightedGraph edgeWeightedGraph, int source, ConcurrentHashMap<Double, DijkstraUndirectedSP> totalCostMap) {
        this.edgeWeightedGraph = edgeWeightedGraph;
        this.source = source;
        this.totalCostMap = totalCostMap;
    }

    @Override
    public void run() {
        calculateMstForOneSource(edgeWeightedGraph, source);
    }


    private void calculateMstForOneSource(EdgeWeightedGraph G, int i) {
        int source = i;
        DijkstraUndirectedSP sp = new DijkstraUndirectedSP(G, source);

        double totalCost = 0;
        for (int j = 0; j < G.V(); j++) {
            Edge edge = sp.getEdgeTo()[j];
            if (edge != null)
                totalCost += edge.weight();
        }
        System.out.println("Thread Name " + Thread.currentThread().getName() +
                " Total cost for source  " + source + " = " + totalCost);
        totalCostMap.put(totalCost, sp);
    }
}
