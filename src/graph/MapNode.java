package graph;

import java.util.HashSet;

public class MapNode {
    private int node;
    private HashSet<MapEdge> edges ;

    public MapNode (int node){
        this.node = node;
    }

    public int getNode() {
        return node;
    }

    public HashSet<MapEdge> getEdges() {
        return edges;
    }

    public void setEdges(HashSet<MapEdge> edges) {
        this.edges = edges;
    }

    public void setNode(int node) {
        this.node = node;
    }

    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        int otherNode =  (int) obj;
        return (otherNode == this.node);
    }

    @Override
    public int hashCode() {
        //return super.hashCode();
        return this.node;
    }
}
