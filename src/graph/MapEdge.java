package graph;

import org.jetbrains.annotations.Contract;

import java.util.Objects;

public class MapEdge {
    private MapNode start;
    private MapNode end;

    /**
     * @param start
     * @param end
     */
    public MapEdge (MapNode start, MapNode end){
        this.start = start;
        this.end = end;
    }

    public void setEnd(MapNode end) {
        this.end = end;
    }

    public void setStart(MapNode start) {
        this.start = start;
    }

    public MapNode getEnd() {
        return end;
    }

    public MapNode getStart() {
        return start;
    }

    @Override
    public boolean equals(Object obj) {
        //return super.equals(obj);
        MapEdge otherEdge = (MapEdge) obj;
        if (otherEdge.start == this.start && otherEdge.end == this.end) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        //return super.hashCode();
        return Objects.hash(this.start, this.end);
    }
}
