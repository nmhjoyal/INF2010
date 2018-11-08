import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Graph {

	private List<Node> nodes = new ArrayList<Node>(); // Noeuds
	private List<Edge> edges = new ArrayList<Edge>(); // Les arcs
	static final double inf = 99999;
	public Graph() {
		
	}

	public void readFromFile(String filePath,String separator){
		//compl�ter
		BufferedReader br = null;
		FileReader fr = null;
		char sepChar = separator.charAt(0);
		try {
			fr = new FileReader(filePath);
			br = new BufferedReader(fr);
			String sCurrentLine=br.readLine();
			char[] Noeuds = sCurrentLine.toCharArray();
			int index=0;
			for (char i : Noeuds) {
				if (i!=sepChar){
					String s = "" + i;
					nodes.add(new Node(index,s));
					index++;
				}
			}
			int indexNoeudOut = 0;
			while ((sCurrentLine = br.readLine()) != null) {
				char[] Arcs = sCurrentLine.toCharArray();
				index=0;
				for (char i : Arcs) {
					if (i == sepChar){
						index++;
					}
					if (i != sepChar && i!='0' && i!='i' && i!='n' && i!='f'){
						int dist = (int) i - 48;
						Node source = getNodeById(indexNoeudOut);
						Node dest = getNodeById(index);
						edges.add(new Edge(source, dest, dist));
					}
				}
			indexNoeudOut++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if (br != null)
					br.close();
				if (fr != null)
					fr.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public List<Edge> getOutEdges(Node source) {
		List<Edge> outEdges = new ArrayList<Edge>(); 
		// compl�ter
		for(Edge edge : this.edges) {
			if(edge.getSource().equals(source)) {
				if(edge.getDistance() != 0 && edge.getDistance() != inf) 
					outEdges.add(edge);
			}
		}
		
		return outEdges;	
	}
	
	public List<Edge> getInEdges(Node dest) {
		List<Edge> inEdges = new ArrayList<Edge>(); 
		// compl�ter
		for(Edge edge : this.edges) {
			if(edge.getDestination().equals(dest)) {
				if(edge.getDistance() != 0 && edge.getDistance() != inf) 
					inEdges.add(edge);
			}
		}
		return inEdges;		
	}
	
	// Accesseurs 
	public List<Node> getNodes() {
		return nodes;
	}
	public void setNodes(List<Node> nodes) {
		this.nodes = nodes;
	}
	public List<Edge> getEdges() {
		return edges;
	}
	public void setEdges(List<Edge> edges) {
		this.edges = edges;
	}
	public Node getNodeByName(String name){
		for (Node node : nodes) {
			if(node.getName().equals(name)){
				return node;
			}
		}
		return null;
	}
	
	public Node getNodeById(int id){
		for (Node node : nodes) {
			if(node.getId()==id){
				return node;
			}

		}
		return null;
	}
}
