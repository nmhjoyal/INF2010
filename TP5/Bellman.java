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
	static final double inf = 99999;
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
				init.add(inf);
				initR.add(-1);
			}
		}
		piTable.add(init);
		rTable.add(initR);
		
		int k = 1;
		Node baseNode = sourceNode;
		Vector<Node> currentAccessibleNodes = new Vector<>();
		currentAccessibleNodes.add(baseNode);
		while (k <= graph.getNodes().size()) {
			//Init nouvelle ligne, On reprend les valeurs de la ligne précédente
			Vector<Double> next = new Vector(piTable.get(k-1));
			Vector<Integer> nextRLine = new Vector(rTable.get(k-1));
			// On sauvegarde l'etat de currentAcccessibleNodes, puisqu'il peut changer
			Vector<Node> accessibleNodes = new Vector(currentAccessibleNodes);
			for(Node node: accessibleNodes){

				//Retourne la liste les arcs sortants du prochain noeud à analyser (node)
				List<Edge> outEdges = graph.getOutEdges(node);

				//Si le noeud ne comporte aucun arc sortant, on passe au prochain noeud
				if(outEdges.isEmpty()) {
					System.out.print("skip\n");
					continue;
				}
				
				for(Edge edge : outEdges) {
					//Chemin suggéré
					double suggestedPathLength = next.get(node.getId()) + edge.getDistance();
					//Chemin actuel
					double currentPathLength = next.get(edge.getDestination().getId());
					// Si le chemin suggéré est meilleur
					if (suggestedPathLength<currentPathLength){
						//Si le noeud etait auparavant innaccessible, on l'ajoute aux noeuds accessibles
						if (currentPathLength==inf){
							System.out.print("node added\n");
							currentAccessibleNodes.add(edge.getDestination());
						}
						//on ajoute le nouveau chemin dans les tables
						next.set(edge.getDestination().getId(),suggestedPathLength);
						nextRLine.set(edge.getDestination().getId(), node.getId());
					}
				}
			}
			
			// Si les 2 dernieres itérations sont pareilles, on break
			if (nextRLine.equals(rTable.get(k-1))){
				System.out.print("test worked\n");
				break;
			}
			piTable.add(next);
			rTable.add(nextRLine);
			k++;
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
		System.out.print("<< PITable >>:\n");
		System.out.print("k    :    ");
		for(Node node : graph.getNodes()) {
			System.out.print(node.getName());
			System.out.print("    ");
		}
		System.out.print("\n");
		int k=0;
		for(Vector<Double> vec : piTable) {
			System.out.print(k+"    :    ");
			for(Double el: vec) {
				if(el.equals(inf))
					System.out.print("inf");
				else	
					System.out.print(el);
				System.out.print("  ");
			}
			k++;
			System.out.print("\n");
		}
		k=0;
		System.out.print("\n\n<< RTable >>:\n");
		System.out.print("k    :    ");
		for(Node node : graph.getNodes()) {
			System.out.print(node.getName());
			System.out.print("  ");
		}
		System.out.print("\n");
		for(Vector<Integer> vec : rTable) {
			System.out.print(k+"    :    ");
			for(Integer el: vec) {
				if(el.equals(-1)) 
					System.out.print("-");
				else
					
					System.out.print(graph.getNodeById(el).getName());
				System.out.print("  ");
			}
			k++;
			System.out.print("\n");
		}
	}
}
