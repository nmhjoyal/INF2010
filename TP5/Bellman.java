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
			
			//On reprend les valeurs de la ligne précédente
			for(int i = 0; i < graph.getNodes().size(); i++) {
				if(i == sourceNode.getId()) {
					next.add(0.0);
					nextRLine.add(-1);
				}else {
					next.add(piTable.get(k - 1).get(i));
					nextRLine.add(rTable.get(k - 1).get(i));
				}
			}

			//Retourne la liste les arcs sortants du prochain noeud (baseNode)
			List<Edge> outEdges = graph.getOutEdges(baseNode);

			//Si le noeud ne comporte aucun arc sortant, on brise l'algorithme
			if(outEdges.isEmpty()) {
				break;
			}

			for(Edge edge : outEdges) {
                if(edge.getDestination().getId() != sourceNode.getId()) {
                    boolean estPlusPetit = piTable.get(k - 1).get(baseNode.getId()) + edge.getDistance() < piTable.get(k - 1).get(baseNode.getId());
                    if (next.get(baseNode.getId()) != Graph.inf && estPlusPetit)
                        next.set(edge.getDestination().getId(), piTable.get(k - 1).get(baseNode.getId()) + edge.getDistance());
                    else
                        next.set(edge.getDestination().getId(), edge.getDistance());

                    nextRLine.set(edge.getDestination().getId(), baseNode.getId());
                }
			}

			//Test l'égalité des lignes
            boolean ligneEstPareille = true;
            for(int i = 0; i < graph.getNodes().size(); i++){
                if(!next.get(i).equals(piTable.get(k - 1).get(i))){
                    ligneEstPareille = false;
                    break;
                }
            }
            //On casse l'algorithme si les deux dernières lignes sont pareilles
            if(ligneEstPareille)
                break;
            piTable.add(next);
            rTable.add(nextRLine);
			k++;
			baseNode = graph.getNodeById(k);
		}
		
	}
	
	public void  diplayShortestPaths() {
		//Compl�ter
        StringBuilder chemin = new StringBuilder("\n\n");
        Stack<Node> path = new Stack<>();
        int ligne = this.rTable.size() - 1;
        int noeud = this.sourceNode.getId();
        boolean contientNegatif = false;

        if(this.rTable.get(this.rTable.size() - 1).get(this.sourceNode.getId()) == this.sourceNode.getId() &&
                this.piTable.get(this.rTable.size() - 1).get(this.sourceNode.getId()) < 0){
            chemin.append("==> Le graphe contient un circuit de cout négatif :\n");
            contientNegatif = true;
            while(this.rTable.get(ligne).get(noeud) != -1){
                path.push(this.graph.getNodeById(noeud));
                noeud = this.rTable.get(ligne).get(noeud);
                ligne--;
            }

        }else {
            chemin.append("==> Les chemins sont :");
            for(int i = this.graph.getNodes().size() - 1; i >= 0; i--){
                ligne = this.rTable.size() - 1;
                noeud = i;
                if(this.rTable.get(ligne).get(noeud) != -1){
                    while(this.rTable.get(ligne).get(noeud) != -1){
                        path.push(this.graph.getNodeById(noeud));
                        noeud = this.rTable.get(ligne).get(noeud);
                        ligne--;
                    }
                    path.push(this.sourceNode);
                }
            }
        }

        if(!path.empty()){
            if(contientNegatif){
                chemin.append("[" + sourceNode.getName() + " - " + sourceNode.getName() + "] : ");
                chemin.append(path.pop().getName());
                while (!path.empty()) {
                    chemin.append(" -> ");
                    chemin.append(path.pop().getName());
                }
            }else{
                StringBuilder crochets = new StringBuilder(chemin + "\n[" + path.pop().getName() + " - ");
                Node dernier = new Node(-1,"");
                chemin = new StringBuilder(sourceNode.getName());

                while(!path.empty()) {
                    if (path.peek().getId() != this.sourceNode.getId()) {
                        chemin.append(" -> ");
                        dernier = path.pop();
                        chemin.append(dernier.getName());
                    } else {
                        crochets.append(dernier.getName() + "] " +
                                        this.piTable.get(this.piTable.size() - 1).get(dernier.getId()) + " : ");
                        crochets.append(chemin);
                        crochets.append("\n[" + path.pop().getName() + " - ");
                        chemin = new StringBuilder(sourceNode.getName());
                    }
                }
                crochets.append(dernier.getName() + "] " +
                        this.piTable.get(this.piTable.size() - 1).get(dernier.getId()) + " : ");
                crochets.append(chemin);
                chemin = new StringBuilder(crochets);
            }
        }
        System.out.println(chemin);
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
