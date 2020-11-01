/**
 * 
 */
package graph;

import util.GraphLoader;

import java.util.*;

/**
 * @author Alejo Acosta.
 * 
 */

public class CapGraph implements Graph {

	HashMap<Integer, HashSet<Integer>> graph;


	/* (non-Javadoc)
	 * @see graph.Graph#addVertex(int)
	 */

	public CapGraph (){
		this.graph = new HashMap<Integer, HashSet<Integer>>();
	}

	@Override
	public void addVertex(int num) {
		// TODO Auto-generated method stub
		if (!this.graph.containsKey(num)){
			HashSet<Integer> edges = new HashSet<>();
			this.graph.put(num, edges);
		}
	}

	/* (non-Javadoc)
	 * @see graph.Graph#addEdge(int, int)
	 */
	@Override
	public void addEdge(int from, int to) {
		// TODO Auto-generated method stub
		if (!this.graph.containsKey(from)){
			this.graph.put(from, new HashSet<Integer>());
		}
		HashSet <Integer> edges = this.graph.get(from);
		edges.add(to);
		this.graph.put(from, edges);
	}

	private void printElements (Collection<?> c, String s){
		int count = 0;
		System.out.println("Printing " + s);
		for (Object o : c){
			count ++;
			System.out.println(count + ". Element: " + o);
		}

	}

	/* (non-Javadoc)
	 * @see graph.Graph#getEgonet(int)
	 */
	@Override
	public Graph getEgonet(int center) {
		// TODO Auto-generated method stub
		if (!this.graph.containsKey(center)) return null;
		Graph egoGraph = new CapGraph();
		egoGraph.addVertex(center);
		HashSet<Integer> centerNeighbors = this.graph.get(center);
		for (Integer neighbor : centerNeighbors){
			egoGraph.addVertex(neighbor); // add all neighbors to the EgoGraph
			for (Integer nextNeighbor : this.graph.get(neighbor)){
				// if this neighbor connects to another neighbor of centerNode,
				// add it as an EDGE to egoGraph
				if (centerNeighbors.contains(nextNeighbor)) {
					egoGraph.addEdge(neighbor, nextNeighbor);
				}
			}
		}
		return egoGraph;

	}

	private void DFSvisit (HashMap<Integer, HashSet<Integer>> G, int vertex, HashSet<Integer> visited,Stack<Integer> finished , CapGraph scc){
		visited.add(vertex);
		System.out.println("DFSVisit: entering recurrent DFSVisit with vertex visited: " + vertex);
		for (int n : G.get(vertex)){
			if (!visited.contains(n)){
				if (scc!=null){
					System.out.println("DFSvisit: adding vertex to SCC: " + n + " and edge [" + n+"]->["+vertex+"]");
					scc.addVertex(n);
					scc.addEdge(n,vertex);
				}
				DFSvisit(G, n, visited, finished, scc);
			}
		}
		finished.push(vertex);
		System.out.println("DFSVisit: just pushed to finish stack vertex: " + vertex);
	}

	private Stack<Integer> initializeVerticesStack (){
		Stack<Integer> vertices = new Stack<>();
		Stack<Integer> auxStack = new Stack<>();
		for (int vertex : this.graph.keySet()){
			vertices.push(vertex);
		}
		int aux;
		while (!vertices.isEmpty()){
			aux = vertices.pop();
			auxStack.push(aux);
		}
		return auxStack;

	}

	private Stack<Integer> DFS (Stack<Integer> vertices ){
		System.out.println("DFS: Starting DFS with Vertices:");
		printElements(vertices, "Vertices");
		HashSet<Integer> visited = new HashSet<>();
		Stack<Integer> finished = new Stack<>();
		while (!vertices.isEmpty()){
			int vertex = vertices.pop();
			System.out.println("DFS: poping out from stack vertex: " + vertex);
			if (!visited.contains(vertex)){
				DFSvisit(this.graph, vertex, visited, finished, null);
			}
		}
		return finished;
	}

	private HashMap<Integer, HashSet<Integer>> computeTranspose (){
		HashMap<Integer, HashSet<Integer>> graphT = new HashMap<Integer, HashSet<Integer>>();
		for (int vertex : this.graph.keySet()){
			for (int n : this.graph.get(vertex)){
				if (!graphT.containsKey(n)){
					HashSet<Integer> neighbors = new HashSet<Integer>();
					graphT.put(n,neighbors);
				}
				graphT.get(n).add(vertex);
			}
		}
		return graphT;
	}

	private List<Graph> DFSgetSCC (HashMap<Integer, HashSet<Integer>> graphT,Stack<Integer> vertices ){
		HashSet<Integer> visited = new HashSet<>();
		Stack<Integer> finished = new Stack<>();
		List<Graph> sccList = new LinkedList<>();
		System.out.println("DFSgetSCC: About to start DFSgetSCC with the following data");
		System.out.println("DFSgetSCC: Transposed graph:" + graphT );
		System.out.println("DFSgetSCC: Stack of vertices (from step1): ");
		printElements(vertices, "vertices");
		while (!vertices.isEmpty()){
			int vertex = vertices.pop();
			System.out.println("DFSgetSCC: poping out from stack vertex: " + vertex);
			if (!visited.contains(vertex)){
				CapGraph SCC = new CapGraph();
				SCC.addVertex(vertex);
				System.out.println("DFSgetSCC: Creating new SCC with first Vertex: " + vertex);
				DFSvisit(graphT, vertex, visited, finished, SCC);
				System.out.println("Adding last edge to SCC: [" + vertex + "]->");
				sccList.add(SCC);
			}
		}
		return sccList;
	}


	/* (non-Javadoc)
	 * @see graph.Graph#getSCCs()
	 */
	@Override
	public List<Graph> getSCCs() {
		// TODO Auto-generated method stub
		Stack<Integer> verticesToVisit = initializeVerticesStack();
		Stack<Integer> verticesFinished = DFS(verticesToVisit);
		HashMap<Integer, HashSet<Integer>> graphT = computeTranspose();
		List<Graph> sccList = DFSgetSCC(graphT, verticesFinished);
		return sccList;
	}

	/* (non-Javadoc)
	 * @see graph.Graph#exportGraph()
	 */
	@Override
	public HashMap<Integer, HashSet<Integer>> exportGraph() {
		// TODO Auto-generated method stub
		return this.graph;
	}

	public String toString(){
		String s = "";
		for (int v : this.graph.keySet()){
			s += "Vertex: " + v + " Neighbors: ";
			for (int n: this.graph.get(v)){
				s+= n + ", ";

			}

		}
		return s;
	}

	public static void main (String[] args){
		CapGraph myGraph = new CapGraph();
		GraphLoader.loadGraph(myGraph, "data/small_test_graph.txt");
		System.out.println("Printing map");
		System.out.println("MapSize: " +  myGraph.graph.size());
		myGraph.printElements(myGraph.graph.keySet(), "nodes");
		myGraph.printElements(myGraph.graph.values(), "edges");
	}

}
