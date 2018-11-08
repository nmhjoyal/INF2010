import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.Vector;


public class Bellman {
	private Graph graph;
	private Node sourceNode;
	private List<Vector<Double>> piTable =  new ArrayList<Vector<Double>>();
	private List<Vector<Integer>> rTable =  new ArrayList<Vector<Integer>>();
	
	public Bellman (Graph g) {
		this.graph = g;
	}
	
	public void setSourceNode(Node source) {
		this.sourceNode = source;
	}
	
	public void shortestPath() {
		// Compl�ter
		
		Vector<Double> init = new Vector<>();
		Vector<Integer> initR = new Vector<>();
		
		for(int i = 0; i < graph.getNodes().size(); i++) {
			if(i == sourceNode.getId()) {
				init.add(0.0);
				initR.add(-1);
			}else {
				init.add(graph.inf);
				initR.add(-1);
			}
		}
		piTable.add(init);
		rTable.add(initR);
		
		int k = 1;
		Node baseNode = graph.getNodeById(k);
		
		while (k < graph.getNodes().size()) {
			//Init nouvelle ligne
			Vector<Double> next = new Vector<>();
			Vector<Integer> nextRLine = new Vector<>();
			
			//On prend les valeurs de la derniere ligne
			for(int i = 0; i < graph.getNodes().size(); i++) {
				if(i == sourceNode.getId()) {
					next.add(0.0);
					nextRLine.add(-1);
				}else {
					next.add(piTable.get(k - 1).get(i));
					nextRLine.add(rTable.get(k - 1).get(i));
				}
			}
			piTable.add(next);
			rTable.add(nextRLine);
			
			List<Edge> outEdges = graph.getOutEdges(baseNode);
			if(outEdges.isEmpty()) {
				break;
			}
			
			for(Edge edge : outEdges) {
				boolean whatevs = piTable.get(k - 1).get(baseNode.getId()) + edge.getDistance() < piTable.get(k - 1).get(baseNode.getId());
				if(piTable.get(k).get(baseNode.getId()) != graph.inf && whatevs) {
					next.set(edge.getDestination().getId(), piTable.get(k - 1).get(baseNode.getId()) + edge.getDistance());
				}
				else
					next.set(edge.getDestination().getId(), edge.getDistance());
				nextRLine.set(edge.getDestination().getId(),baseNode.getId());
			}
			
			piTable.set(k, next);
			rTable.set(k,nextRLine);
			k++;
			baseNode = graph.getNodeById(k);
		}
		
	}
	
	public void  diplayShortestPaths() {
		//Compl�ter
	}

	public void displayTables() {
	 //Compl�ter
		System.out.print("pi Table\n");
		for(Node node : graph.getNodes()) {
			System.out.print(node.getName());
			System.out.print("    ");
		}
		System.out.print("\n");
		for(Vector<Double> vec : piTable) {
			for(Double el: vec) {
				if(el.equals(graph.inf))
					System.out.print("inf");
				else	
					System.out.print(el);
				System.out.print("  ");
			}
			System.out.print("\n");
		}
		
		System.out.print("\n\nR Table\n");
		
		for(Node node : graph.getNodes()) {
			System.out.print(node.getName());
			System.out.print("  ");
		}
		System.out.print("\n");
		for(Vector<Integer> vec : rTable) {
			for(Integer el: vec) {
				if(el.equals(-1)) 
					System.out.print("-");
				else
					System.out.print(el);
				System.out.print("  ");
			}
			System.out.print("\n");
		}
	}
}
