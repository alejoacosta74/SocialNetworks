/**
 * 
 */
package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Alejo Acosta.
 * 
 * For the warm up assignment, you must implement your Graph in a class
 * named CapGraph.  Here is the stub file.
 *
 */
public class CapGraph implements Graph {

	private HashMap <MapNode, HashSet<MapEdge>> graph;
	private HashSet <MapEdge> edges;


	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */
	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		MapNode newNode = new MapNode(num);

		this.graph.put(newNode, null);
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		MapNode start = new MapNode(from);
		MapNode end = new MapNode(to);
		MapEdge newEdge = new MapEdge(start, end);
		if (!this.graph.containsKey(start)){
			this.graph.put(start, new HashSet<>());
		}
		HashSet <MapEdge> edges = this.graph.get(start);
		edges.add(newEdge);
		this.graph.put(start, edges);
		// adding Edge to field edges in case future use
		this.edges.add(newEdge);

	}

	private HashSet getNeighbors (MapNode centerNode){
		HashSet<MapNode> neighbors = new HashSet<>();
		for (MapEdge e : this.graph.get(centerNode)){
			neighbors.add(e.getEnd());
		}
		return neighbors;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		MapNode centerNode = new MapNode(center);
		if (!this.graph.containsKey(centerNode)) return null;
		Graph egoGraph = new CapGraph();
		egoGraph.addVertex(center);
		HashSet<MapNode> neighbors = getNeighbors(centerNode);
		for (MapNode neighbor : neighbors){
			egoGraph.addVertex(neighbor.getNode()); // add all neighbors to the EgoGraph
			for (MapEdge edge : neighbor.getEdges()){
				// if the edge of any neighbor connects to another neighbor or
				if (neighbors.contains(edge.getEnd())){
					egoGraph.addEdge(edge.getStart().getNode(), edge.getEnd().getNode());
				}
			}
		}
		return egoGraph;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		return null;
	}

}
