import java.util.List;

public class BinaryNode<T extends Comparable<? super T> > {
    private T data;
    private BinaryNode<T> right;
    private BinaryNode<T> left;

    // TODO: initialisation
    // O(1)
    public BinaryNode(T data) {
    	this.data = data;
    	this.right = null;
    	this.left = null;
    }

    // TODO: on retourne la donnee voulue
    // O(1)
    public T getData() {
        return data;
    }

    // TODO: on ajoute une nouvelle donnee au bon endroit
    // O(log(n))
    public void insert(T item) {
    	if(this.data != null) {
    		if(item.compareTo(data) > 0) {
	    		this.right.insert(item);
	    	}else {
	    		this.left.insert(item);
	    	}
    	}
    }

    // TODO: est-ce que l'item fais partie du noeuds courant
    // O(log(n))
    public boolean contains(T item) {
    	if(this.data != null) {
	    	if(item.compareTo(data) > 0) {
	    		this.right.contains(item);
	    	}else if(item.compareTo(data) < 0){
	    		this.left.contains(item);
	    	}else {
	    		return true;
	    	}
    	}
    	return false;
    }

    // TODO: on retourne la maximale de l'arbre
    // O(n)
    public int getHeight() {
    	if(this.data != null)
    		return 1 + Math.max(this.right.getHeight(), this.left.getHeight());
    	else
    		return -1;
    }

    // TODO: l'ordre d'insertion dans la liste est l'ordre logique
    // de manière que le plus petit item sera le premier inseré
    // O(n)
    public void fillListInOrder(List<BinaryNode<T>> result) {
    	if(this.left == null) {
    		result.add(this);
    		if(this.right != null) {
    			this.right.fillListInOrder(result);
    		}
    	}else {
    		this.left.fillListInOrder(result);
    	}
    	
    }
    
//    public String toString() {
//    	return this.data.toString();
//    }
}
