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
        if(data==null){
            data=item;
        }
    	else {
    		if(item.compareTo(data) > 0) {
    			if(right == null) {
                    right = new BinaryNode<T>(item);
	    		}else {
                    right.insert(item);
	    		}
	    	}else {
	    		if(left == null) {
	    			left = new BinaryNode<T>(item);
	    		}else {
	    			left.insert(item);
	    		}
	    	}
    	}
    }

    // TODO: est-ce que l'item fais partie du noeuds courant
    // O(log(n))
    public boolean contains(T item) {
        if (data==null){
            return false;
        }
        else {
            if (data==item.data){
                return true;
            }
	    	else if(item.compareTo(data) > 0) {
	    	    if(right != null) {
                    return right.contains(item);
                } else return false;
	    	}else if(item.compareTo(data) < 0){
	    	    if(left != null) {
                    return left.contains(item);
                } else return false;
            }
            
    	}
    }

    // TODO: on retourne la maximale de l'arbre
    // O(n)
    public int getHeight() {
        if(this.data==null){
            return 0;
        }
        else{
            
                int LeftHeight = left.getHeight(); 
            
            
            
                int RightHeight = right.getHeight(); 
            
            if(LeftHeight>=RightHeight){
                return 1+LeftHeight;
            }
            else{
                return 1+RightHeight;
            }
            
        }
    }

    // TODO: l'ordre d'insertion dans la liste est l'ordre logique
    // de manière que le plus petit item sera le premier inseré
    // O(n)
    public void fillListInOrder(List<BinaryNode<T>> result) {
        if(left != null) {
            left.fillListInOrder(result);
    	}

        result.add(this);
        
        if(right != null) {
            right.fillListInOrder(result);
        }
    }
    
    public String toString() {
        return data.toString();
    }
}
