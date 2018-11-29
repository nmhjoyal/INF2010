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
		Node baseNode = graph.getNodeById(sourceNode.getId());
		
		while (k <= graph.getNodes().size()) {
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
			    System.out.println("\nSTOP : Aucun arc sortant");
				break;
			}

			//Pour chaque arc sortant, nous l'analysons
			for(Edge edge : outEdges) {
			    //Si l'ancienne valeur additionnée à l'arc courant est plus petit, nous le remplaçons dans la table pi
                boolean estPlusPetit = (next.get(edge.getSource().getId()) + edge.getDistance()) < next.get(edge.getDestination().getId());
                if(next.get(edge.getDestination().getId()) == Graph.inf){
                    next.set(edge.getDestination().getId(), edge.getDistance() + piTable.get(k - 1).get(edge.getSource().getId()));
                    nextRLine.set(edge.getDestination().getId(), baseNode.getId());
                }else if(next.get(edge.getDestination().getId()) != Graph.inf && estPlusPetit) {
                    next.set(edge.getDestination().getId(), piTable.get(k - 1).get(edge.getSource().getId()) + edge.getDistance());
                    nextRLine.set(edge.getDestination().getId(), baseNode.getId());
                }
			}

			//Test l'égalité des lignes
            boolean ligneEstPareille = true;
            for(int i = 0; i < graph.getNodes().size(); i++){
                if(!next.get(i).equals(piTable.get(k - 1).get(i))){
                    ligneEstPareille = false;

                }
            }
            //On casse l'algorithme si les deux dernières lignes sont pareilles
            if(!ligneEstPareille) {
                piTable.add(next);
                rTable.add(nextRLine);
                k++;
            }
            if (baseNode.getId() + 1 > graph.getNodes().size()) {
                baseNode = graph.getNodeById((baseNode.getId() + 1) - graph.getNodes().size());
            } else {
                baseNode = graph.getNodeById(baseNode.getId() + 1);
            }
		}
		if(k == graph.getNodes().size())
		    System.out.println("\nSTOP : k = n");
	}
	
	public void  diplayShortestPaths() {
		//Compl�ter
        //Init de variables
        StringBuilder chemin = new StringBuilder("\n\n");
        Stack<Node> path = new Stack<>();
        int ligne = this.rTable.size() - 1;
        int noeud = this.sourceNode.getId();
        boolean contientNegatif = false;

        //Si le graphe contient un chemin négatif, faut concaténer la notification
        if(this.rTable.get(this.rTable.size() - 1).get(this.sourceNode.getId()) == this.sourceNode.getId() &&
                this.piTable.get(this.rTable.size() - 1).get(this.sourceNode.getId()) < 0){
            chemin.append("==> Le graphe contient un circuit de cout négatif :\n");

            //Marque à true pour l'affichage ci-dessous
            contientNegatif = true;
            //Parcourir la table R pour ajouter les noeuds parents à la pile
            while(this.rTable.get(ligne).get(noeud) != -1){
                path.push(this.graph.getNodeById(noeud));
                noeud = this.rTable.get(ligne).get(noeud);
                ligne--;
            }

        }else {
            chemin.append("==> Les chemins sont :");
            //Parcourir dans le sens inverse les noeuds du graphe pour ajouter les noeuds dans le bon
            // ordre
            for(int i = this.graph.getNodes().size() - 1; i >= 0; i--){
                ligne = this.rTable.size() - 1;
                noeud = i;
                if(this.rTable.get(ligne).get(noeud) != -1){

                    //Jusqu'à ce que l'on trouve un -1 (indice de noeud impossible) comme parent,
                    // on ajoute l'indice à la pile
                    while(this.rTable.get(ligne).get(noeud) != -1){
                        path.push(this.graph.getNodeById(noeud));
                        noeud = this.rTable.get(ligne).get(noeud);
                        ligne--;
                    }
                    //On ajoute le noeud source à la fin de chaque chemin pour faciliter l'affichage
                    // ci-bas
                    path.push(this.sourceNode);
                }
            }
        }

        //Vérification que la pile n'est pas vide
        if(!path.empty()){

            //Procédure lorsqu'il y a un circuit de coût négatif
            if(contientNegatif){
                chemin.append("[" + sourceNode.getName() + " - " + sourceNode.getName() + "] : ");
                chemin.append(path.pop().getName());
                while (!path.empty()) {
                    chemin.append(" -> ");
                    chemin.append(path.pop().getName());
                }
            }else{
                //Deux StringBuilder pour faciliter la concaténation
                StringBuilder crochets = new StringBuilder(chemin + "\n[" + path.pop().getName() + " - ");

                //Noeud qui marque le dernier de chaque chemin, facilite la recherche du coût et l'affichage
                // du nom
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
                //Concaténer les crochets avant le chemin, réinitialiser le chemin
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
