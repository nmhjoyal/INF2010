import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BinarySearchTree<T extends Comparable<? super T> > {

    private BinaryNode<T> root;

    public BinarySearchTree() { }

    // TODO: initialisation
    public BinarySearchTree(T item) {
    	root = new BinaryNode<T>(item);
    }

    // TODO: on insere un nouvel item a partir de la racine
    // O(log(n))
    public void insert(T item) {
    	if(root != null)
    		root.insert(item);
    	else
    		root = new BinaryNode<T>(item);
    }

    // TODO: est-ce qu'un item fais partie de l'arbre
    // O(log(n))
    public boolean contains(T item) {
    	if(root != null)
    		return root.contains(item);
    	return false;
    }

    // TODO: trouver la hauteur de l'arbre
    // O(n)
    public int getHeight() {
    	if(root != null)
    		return root.getHeight();
        return -1;
    }

    // TODO: placer dans une liste les items de l'arbre en ordre
    // O(n)
    public List<BinaryNode<T>> getItemsInOrder() {
    	List<BinaryNode<T>> list = new ArrayList<>();
    	if(root != null) {
    		root.fillListInOrder(list);
    		return list;
    	}
        return null;
    }

    // TODO: retourner la liste d'item en String selon le bon format
    // O(n)
    public String toStringInOrder() {
    	List<BinaryNode<T>> list = getItemsInOrder();
    	String result = "[";
    	for (BinaryNode<T> node : list) {
    		result += node.toString() + ", ";
        }
        
        return (result.substring(0, result.lastIndexOf(",")) + "]");
    }
}