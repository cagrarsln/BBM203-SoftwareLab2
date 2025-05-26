import java.util.*;

public class DijkstraSP {
    private double[] distTo;
    private Edge[] edgeTo;
    private PriorityQueue<Pair> pq;

    public DijkstraSP(EdgeWeightedGraph graph, int s) {
        distTo = new double[graph.V()];
        edgeTo = new Edge[graph.V()];
        pq = new PriorityQueue<>(Comparator.comparingDouble(pair -> pair.dist));

        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[s] = 0.0;
        pq.add(new Pair(s, 0.0));

        while (!pq.isEmpty()) {
            Pair pair = pq.poll();
            int v = pair.vertex;
            for (Edge e : graph.adj(v)) {
                relax(e, v);
            }
        }
    }

    private void relax(Edge e, int v) {
        int w = e.other(v);
        if (distTo[w] > distTo[v] + e.weight()) {
            distTo[w] = distTo[v] + e.weight();
            edgeTo[w] = e;
            pq.add(new Pair(w, distTo[w]));
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
        for (Edge e = edgeTo[v]; e != null; e = edgeTo[e.either() == v ? e.other(e.either()) : e.either()]) {
            path.push(e);
            v = e.other(v);
        }
        return path;
    }

    private static class Pair {
        int vertex;
        double dist;

        Pair(int vertex, double dist) {
            this.vertex = vertex;
            this.dist = dist;
        }
    }
}
