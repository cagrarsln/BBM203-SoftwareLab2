import java.util.*;

public class EdgeWeightedGraph {
    private final int V;
    private final List<Edge>[] adj;

    public EdgeWeightedGraph(int V) {
        this.V = V;
        adj = (List<Edge>[]) new ArrayList[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new ArrayList<>();
        }
    }

    public void addEdge(Edge e) {
        int v = e.either(), w = e.other(v);
        adj[v].add(e);
        adj[w].add(e);
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public int V() {
        return V;
    }
}