package org.graph;


import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.graph.DijkstraUndirectedSP.printMST;

public class ParallelDijkstraUndirectedSP {


    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ConcurrentHashMap<Double, DijkstraUndirectedSP> totalCostMap = new ConcurrentHashMap<>();
        String path = "data\\mediumEWG.txt";
        EdgeWeightedGraph G = new EdgeWeightedGraph(path);
        ArrayList<Future> futures = new ArrayList<>();
        for (int i = 0; i < G.V(); i++) {

            MstForOneSourceCalculator mstForOneSourceCalculator = new MstForOneSourceCalculator(G, i, totalCostMap);
            Future<?> future = executorService.submit(mstForOneSourceCalculator);
            futures.add(future);
        }

        for (Future future : futures) {
            try {
                Object o = future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        double minCost = Double.MAX_VALUE;
        for (Map.Entry<Double, DijkstraUndirectedSP> entry : totalCostMap.entrySet()) {
            Double cost = entry.getKey();
            if (cost < minCost)
                minCost = cost;
        }
        DijkstraUndirectedSP sp = totalCostMap.get(minCost);
        System.out.println("The selected source is = " + sp.getSource());

        printMST(sp);

        long end = System.currentTimeMillis();
        System.out.println("Time = " + (end - start) + " ms");
        executorService.shutdown();
    }


}
