package graph;

import java.util.HashSet;

public class MapNode {
    private int node;

    public MapNode (int node){
        this.node = node;
    }

    public int getNode() {
        return node;
    }

    public void setNode(int node) {
        this.node = node;
    }

    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        MapNode otherNode =  (MapNode) obj;
        return (otherNode.getNode() == this.node);
    }

    @Override
    public int hashCode() {
        //return super.hashCode();
        return this.node;
    }


    @Override
    public String toString() {
        return ("Node #" + this.getNode());
    }
}
