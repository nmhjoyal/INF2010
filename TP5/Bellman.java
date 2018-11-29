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
		Node baseNode = sourceNode;
		//Variable servant à casser l'algorithme
		boolean breakAlgo = false;
		while (k <= graph.getNodes().size()) {

			//Init nouvelle ligne
			Vector<Double> next = new Vector<>();
			Vector<Integer> nextRLine = new Vector<>();
			
			//On reprend les valeurs de la ligne précédente
			for(int i = 0; i < graph.getNodes().size(); i++) {
                next.add(piTable.get(k - 1).get(i));
                nextRLine.add(rTable.get(k - 1).get(i));
			}

			//Retourne la liste les arcs sortants du prochain noeud (baseNode)
			List<Edge> outEdges = graph.getOutEdges(baseNode);

			//Si le noeud ne comporte aucun arc sortant, on brise l'algorithme
			if(outEdges.isEmpty()) {
                if(breakAlgo)
                    break;
                if (baseNode.getId() + 1 >= graph.getNodes().size()) {
                    baseNode = graph.getNodeById((baseNode.getId() + 1) - graph.getNodes().size());
                } else {
                    baseNode = graph.getNodeById(baseNode.getId() + 1);
                }
                //On casse la boucle si les arcs sortants sont nuls et que le prochain noeud
                // est le noeud source
                if(baseNode == sourceNode)
                    breakAlgo = true;
                continue;
			}

			//Pour chaque arc sortant, nous l'analysons
			for(Edge edge : outEdges) {
			    //Si l'ancienne valeur additionnée à l'arc courant est plus petit, nous le remplaçons dans la table pi
                boolean estPlusPetit = (next.get(edge.getSource().getId()) + edge.getDistance()) < next.get(edge.getDestination().getId());

                if(next.get(edge.getDestination().getId()) == Graph.inf){
                    next.set(edge.getDestination().getId(), edge.getDistance() + piTable.get(k - 1).get(edge.getSource().getId()));
                    nextRLine.set(edge.getDestination().getId(), edge.getSource().getId());

                }else if(next.get(edge.getDestination().getId()) != Graph.inf && estPlusPetit) {
                    next.set(edge.getDestination().getId(), piTable.get(k - 1).get(edge.getSource().getId()) + edge.getDistance());
                    nextRLine.set(edge.getDestination().getId(), edge.getSource().getId());

                    //Gestion de valeurs négatives : Si le noeud source a changé la distance d'un noeud déjà rencontré,
                    // on remet le noeud de base à sa destination pour la prochaine itération
                    if(next.get(edge.getDestination().getId()) < piTable.get(k - 1).get(edge.getDestination().getId())){
                        baseNode = edge.getDestination();
                        continue;
                    }
                }
			}

			//Test l'égalité des lignes
            boolean ligneEstPareille = true;
            for(int i = 0; i < graph.getNodes().size(); i++){
                if(!next.get(i).equals(piTable.get(k - 1).get(i))){
                    ligneEstPareille = false;

                }
            }

            //On ajoute la ligne et incrémente k seulement si celle-là est différente
            if(!ligneEstPareille) {
                piTable.add(next);
                rTable.add(nextRLine);
                k++;
            }else{
                //Si les lignes sont pareils, nous voulons passer au prochain noeud s'il existe
                if (baseNode.getId() + 1 >= graph.getNodes().size()) {
                    baseNode = graph.getNodeById((baseNode.getId() + 1) - graph.getNodes().size());
                } else {
                    baseNode = graph.getNodeById(baseNode.getId() + 1);
                }
            }


		}
		if(k == graph.getNodes().size())
		    System.out.println("\nSTOP : k = n");
	}
	
	public void  diplayShortestPaths() {
		//Compl�ter
        //Init de variables
        StringBuilder chemin = new StringBuilder("\n\n");
        List<Stack<Node>> path = new ArrayList<>();

        //Parcourir dans le sens inverse les noeuds du graphe pour ajouter les noeuds dans le bon
        // ordre
        boolean contientBoucle = false;
        int indiceBoucle = -1;
        int pathBoucle = -1;
        for(int i = 0; i < this.graph.getNodes().size(); i++){
            int ligne = this.rTable.size() - 1;
            int noeud = i;
            Stack<Node> pileChemin = new Stack<>();
            if(this.rTable.get(ligne).get(noeud) != -1){
                //Jusqu'à ce que l'on trouve un -1 (indice de noeud impossible) comme parent,
                // on ajoute l'indice à la pile
                while(this.rTable.get(ligne).get(noeud) != -1 && !contientBoucle){
                    if(pileChemin.contains(this.graph.getNodeById(noeud))) {
                        //Si le noeud rencontré existe déjà, nous avons une boucle
                        contientBoucle = pileChemin.contains(this.graph.getNodeById(noeud));
                        //Pour faciliter l'affichage
                        indiceBoucle = this.graph.getNodeById(noeud).getId();
                        pathBoucle = path.size();
                    }
                    pileChemin.push(this.graph.getNodeById(noeud));
                    noeud = this.rTable.get(ligne).get(noeud);
                    ligne--;
                }
                pileChemin.push(this.sourceNode);
                path.add(pileChemin);
            }
            if(contientBoucle)
                break;
        }

        if(contientBoucle){
            chemin.append("\n\n==> Le graphe contient un circuit de coût négatif :\n");
            Node noeudBase = graph.getNodeById(indiceBoucle);
            chemin.append("[" + noeudBase.getName() + " - " + noeudBase.getName() + "] : ");
            path.get(pathBoucle).pop();
            chemin.append(path.get(pathBoucle).pop().getName());
            while(!path.get(pathBoucle).empty() && !path.get(pathBoucle).peek().equals(noeudBase)){
                chemin.append(" -> ");
                chemin.append(path.get(pathBoucle).pop().getName());
            }
            chemin.append(" -> ");
            chemin.append(noeudBase.getName());
        }else{
            //Deux StringBuilder pour faciliter la concaténation
            StringBuilder crochets = new StringBuilder("\n\n==> Les chemins sont :");
            for(int i = 0; i < path.size(); i++){
                Node dernier = new Node(-1,"");
                crochets.append("\n[" + path.get(i).pop().getName() + " - ");
                chemin = new StringBuilder(sourceNode.getName());
                while(!path.get(i).empty()){
                    chemin.append(" -> ");
                    dernier = path.get(i).pop();
                    chemin.append(dernier.getName());
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
		System.out.print("<< PiTable >>:\n\t\t");
		int k = 0;
		//Première ligne
		for(Node node : graph.getNodes()) {
			System.out.print(node.getName());
			System.out.print("\t");
		}

		//Données de la table Pi
		System.out.print("\n");
		for(Vector<Double> vec : piTable) {
		    System.out.print(k + "\t:\t");
			for(Double el: vec) {
				if(el.equals(graph.inf))
					System.out.print("inf");
				else	
					System.out.print(el);
				System.out.print("\t");
			}
			k++;
			System.out.print("\n");
		}
		
		System.out.print("\n\n<< RTable >>:\nk\t:\t");
		k = 0;
		for(Node node : graph.getNodes()) {
			System.out.print(node.getName());
			System.out.print("\t");
		}

		//Données de la table R
		System.out.print("\n");
		for(Vector<Integer> vec : rTable) {
            System.out.print(k + "\t:\t");
			for(Integer el: vec) {
				if(el.equals(-1)) 
					System.out.print("-");
				else
					System.out.print(graph.getNodeById(el).getName());
				System.out.print("\t");
			}
            k++;
			System.out.print("\n");
		}
	}
}
