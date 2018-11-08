import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		// Exerice 1: creation de graphe � partir du fichier 
		Graph g = new Graph();
		List<Node> nodes = new ArrayList<Node>();
		Node nA = new Node(1, "A");
		Node nB = new Node(2, "B");
		Node nC = new Node(3, "C");
		Node nD = new Node(4, "D");
		Node nE = new Node(5, "E");
		Node nF = new Node(6, "F");
		
		nodes.add(nA);
		nodes.add(nB);
		nodes.add(nC);
		nodes.add(nD);
		nodes.add(nE);
		nodes.add(nF);
		
		List<Edge> edges = new ArrayList<Edge>();
		edges.add(new Edge(nA, nD, 5));
		edges.add(new Edge(nB, nF, 2));
		edges.add(new Edge(nC, nA, 3));
		edges.add(new Edge(nD, nB, 8));
		edges.add(new Edge(nA, nC, 1));
		edges.add(new Edge(nF, nE, 2));
		edges.add(new Edge(nE, nD, 7));
		
		g.setNodes(nodes);
		g.setEdges(edges);
		
		List<Edge> inEdges = g.getInEdges(nF);
		System.out.print("Arcs entrants \n");
		for(Edge edge : inEdges) {
			System.out.print(edge.getSource().getName());
			System.out.print(" > ");
			System.out.print(edge.getDestination().getName());
			System.out.print(" : ");
			System.out.print(edge.getDistance());
			System.out.print("\n");
		}
		
		List<Edge> outEdges = g.getOutEdges(nF);
		System.out.print("\nArcs sortants \n");
		for(Edge edge : outEdges) {
			System.out.print(edge.getSource().getName());
			System.out.print(" > ");
			System.out.print(edge.getDestination().getName());
			System.out.print(" : ");
			System.out.print(edge.getDistance());
			System.out.print("\n");
		}
	}
}
