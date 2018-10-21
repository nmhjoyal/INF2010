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
    			if(this.right != null) {
    				this.right.insert(item);
	    		}else {
	    			this.right = new BinaryNode<T>(item);
	    		}
	    	}else {
	    		if(this.left != null) {
	    			this.left.insert(item);
	    		}else {
	    			this.left = new BinaryNode<T>(item);
	    		}
	    	}
    	}else {
    		this.data = item;
    	}
    }

    // TODO: est-ce que l'item fais partie du noeuds courant
    // O(log(n))
    public boolean contains(T item) {

    	if(this.data != null) {
	    	if(item.compareTo(data) > 0) {
	    	    if(this.right != null) {
                    return this.right.contains(item);
                }
	    	}else if(item.compareTo(data) < 0){
	    	    if(this.left != null) {
                    return this.left.contains(item);
                }
	    	}else {
	    		return true;
	    	}
    	}
    	return false;
    }

    // TODO: on retourne la maximale de l'arbre
    // O(n)
    public int getHeight() {
    	if(this.data != null) {
            if (this.right != null && this.left != null) {
                return 1 + Math.max(this.right.getHeight(), this.left.getHeight());
            } else{
                if(this.right != null){
                    return 1 + this.right.getHeight();
                }else if (this.left != null){
                    return 1 + this.left.getHeight();
                }else{
                    return 0;
                }
            }
        }else
    		return -1;
    }

    // TODO: l'ordre d'insertion dans la liste est l'ordre logique
    // de manière que le plus petit item sera le premier inseré
    // O(n)
    public void fillListInOrder(List<BinaryNode<T>> result) {
        if(this.left != null) {
            this.left.fillListInOrder(result);
    	}

    	result.add(this);

        if(this.right != null) {
            this.right.fillListInOrder(result);
        }

    	
    }
    
    public String toString() {
        return this.data.toString();
    }
}
