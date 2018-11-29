import java.util.ArrayList;
import java.util.List;

public class Test {
	public static void main(String[] args) {
		// Exerice 1: creation de graphe � partir du fichier 
		Graph g = new Graph();
		List<Node> nodes = new ArrayList<Node>();
		Node nS = new Node(0, "S");
		Node nB = new Node(1, "B");
		Node nC = new Node(2, "C");
		Node nD = new Node(3, "D");
		Node nE = new Node(4, "E");
		Node nF = new Node(5, "F");
		
		nodes.add(nS);
		nodes.add(nB);
		nodes.add(nC);
		nodes.add(nD);
		nodes.add(nE);
		nodes.add(nF);
		
		List<Edge> edges = new ArrayList<Edge>();
		edges.add(new Edge(nS, nB, 2));
		edges.add(new Edge(nS, nC, 1));
		edges.add(new Edge(nB, nC, 2));
		edges.add(new Edge(nC, nD, -4));
		edges.add(new Edge(nC, nE, 5));
		edges.add(new Edge(nD, nB, 1));
		edges.add(new Edge(nD, nF, 5));
        edges.add(new Edge(nE, nF, 1));

		g.setNodes(nodes);
		g.setEdges(edges);

		Bellman bell = new Bellman(g);
		bell.setSourceNode(nS);

        bell.shortestPath();
		bell.displayTables();
		bell.diplayShortestPaths();

		
//		List<Edge> inEdges = g.getInEdges(nF);
//		System.out.print("Arcs entrants \n");
//		for(Edge edge : inEdges) {
//			System.out.print(edge.getSource().getName());
//			System.out.print(" > ");
//			System.out.print(edge.getDestination().getName());
//			System.out.print(" : ");
//			System.out.print(edge.getDistance());
//			System.out.print("\n");
//		}
//
//		List<Edge> outEdges = g.getOutEdges(nF);
//		System.out.print("\nArcs sortants \n");
//		for(Edge edge : outEdges) {
//			System.out.print(edge.getSource().getName());
//			System.out.print(" > ");
//			System.out.print(edge.getDestination().getName());
//			System.out.print(" : ");
//			System.out.print(edge.getDistance());
//			System.out.print("\n");
//		}
	}
}
