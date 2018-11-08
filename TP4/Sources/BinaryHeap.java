import org.omg.CORBA.Any;

import java.util.*;


public class BinaryHeap<AnyType extends Comparable<? super AnyType>> extends AbstractQueue<AnyType>
{
    private static final int DEFAULT_CAPACITY = 100;
    private int currentSize;      // Nombre d'elements
    private AnyType [ ] array;    // Tableau contenant les donnees (premier element a l'indice 1)
    private boolean min;
    private int modifications;    // Nombre de modifications apportees a ce monceau
    
    @SuppressWarnings("unchecked")
    public BinaryHeap( boolean min )
    {
		this.min = min;
		currentSize = 0;
		array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
    }
    
    @SuppressWarnings("unchecked")
    public BinaryHeap( AnyType[] items, boolean min )
    {
    	this.min = min;
	
		// COMPLETEZ
		// invoquez buildMinHeap() ou buildMaxHeap() en fonction du parametre min;
    	
    	this.array = (AnyType[]) new Comparable[items.length + 1];
    	for(AnyType item : items)
    		this.offer(item);

    	if(min) 
			this.buildMinHeap();
		else
			this.buildMaxHeap();
    }
    
    public boolean offer( AnyType x )
    {
		if (x == null)
		    throw new NullPointerException("Cannot insert null in a BinaryHeap");
		
		if( currentSize + 1 == array.length )
		    doubleArray();
		
		// COMPLETEZ
		
		int space = ++currentSize;
		if(min) {
			for( ; space > 1 && x.compareTo( array[ space / 2 ] ) < 0; space /= 2)
				array[ space ] = array[ space / 2 ];
		}else {
			for( ; space > 1 && x.compareTo( array[ space / 2 ] ) > 0; space /= 2)
				array[ space ] = array[ space / 2 ];
		}
		array[ space ] = x;
		this.modifications++;
		return true;
    }
    
    public AnyType peek()
    {
		if(!isEmpty())
		    return array[1];
		
		return null;
    }
    
    public AnyType poll()
    {
    	//COMPLETEZ
    	if(isEmpty())
    		return null;
    	
    	AnyType elTete = array[1]; 
    	array[1] = array[currentSize--];
    	if(min)
    		percolateDownMinHeap(1, currentSize);
    	else
    		percolateDownMaxHeap(1, currentSize);
    
    	return elTete;
    }
    
    public Iterator<AnyType> iterator()
    {
    	return new HeapIterator();
    }
    
    private void buildMinHeap()
    {
    	//COMPLETEZ
    	for( int i = currentSize / 2; i > 0; i-- ) 
    		percolateDownMinHeap( i , currentSize );
    	
    }
    
    private void buildMaxHeap()
    {
    	//COMPLETEZ
    	for( int i = currentSize / 2; i > 0; i-- )
    		percolateDownMaxHeap( i , currentSize );
    }
    
    public boolean isEmpty()
    {
    	return currentSize == 0;
    }
    
    public int size()
    {
    	return currentSize;
    }
    
    public void clear()
    {
		currentSize = 0;
		modifications = 0;
		array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
    }
    
    private static int leftChild( int i, boolean heapIndexing )
    {
    	return ( heapIndexing ? 2*i : 2*i+1 );
    }
    
    private void swapReferences( int index1, int index2 )
    {
    	swapReferences(array, index1, index2);
    }
    
    private static <AnyType extends Comparable<? super AnyType>>
				    void swapReferences( AnyType[] array, int index1, int index2 )
    {
		AnyType tmp = array[ index1 ];
		array[ index1 ] = array[ index2 ];
		array[ index2 ] = tmp;
    }
    
    @SuppressWarnings("unchecked")
	private void doubleArray()
    {
		AnyType [ ] newArray;
		
		newArray = (AnyType []) new Comparable[ array.length * 2 ];
		for( int i = 0; i < array.length; i++ )
		    newArray[ i ] = array[ i ];
		array = newArray;
    }
    
    
    /**
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     */
    private void percolateDownMinHeap( int hole, int size )
    {
    	percolateDownMinHeap(array, hole, size, true);
    	modifications++;
    }
    
    /**
     * @param array   Tableau d'element
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     * @param heapIndexing  True si les elements commencent a l'index 1, false sinon
     */
    private static <AnyType extends Comparable<? super AnyType>>
				    void percolateDownMinHeap( AnyType[] array, int hole, int size, boolean heapIndexing )
    {
    	//COMPLETEZ
    	
    	AnyType temp = array[hole];
    	int child;
    	
    	for(; hole * 2 <= size; hole = child) {
    		child = hole * 2; //fils de GAUCHE
    		if(child != size && array[child + 1].compareTo(array[child]) < 0) { //if fils de GAUCHE < fils de DROITE, child devient fils de droite
                child++;
            }
            if(array[child] != null) {
                if (array[child].compareTo(temp) < 0)
                    array[hole] = array[child];
                else
                    break;
            }else{
                break;
            }
    					
    	}
    	
    	array[hole] = temp;
    }
    
    /**
     * @param hole    Position a percoler
     * @param size    Indice max du tableau
     */
    private void percolateDownMaxHeap( int hole, int size )
    {
    	percolateDownMaxHeap(array, hole, size, true);
    	modifications++;
    }
    
    /**
     * @param array         Tableau d'element
     * @param hole          Position a percoler
     * @param size          Indice max du tableau
     * @param heapIndexing  True si les elements commencent a l'index 1, false sinon
     */
    private static <AnyType extends Comparable<? super AnyType>> 
				    void percolateDownMaxHeap( AnyType[] array, int hole, int size, boolean heapIndexing )
    {
    	//COMPLETEZ
    	
    	AnyType temp = array[hole];
    	int child;
    	
    	for(; hole * 2 <= size; hole = child) {
    		child = hole * 2; //fils de GAUCHE
    		if(child != size && array[child + 1].compareTo(array[child]) > 0) { //if fils de GAUCHE > fils de DROITE, child devient fils de droite
    			child++;
    		}
    		if(child < size && hole < size) {
	    		if(array[child].compareTo(temp) > 0)
	    			array[hole] = array[child];
	    		else
	    			break;
    		}
    					
    	}
    	if(hole < size)
    		array[hole] = temp;
    }
    
    public static <AnyType extends Comparable<? super AnyType>>
				   void heapSort( AnyType[] a )
    {
	    //COMPLETEZ
        for( int i = a.length / 2; i >= 0; i-- )
            percolateDownMinHeap( a, i, a.length, true);
        for( int i = a.length - 1; i > 0; i-- )
        {
            swapReferences( a, 0, i );
            percolateDownMinHeap( a, 0, i, true);
        }


    }
    
    public static <AnyType extends Comparable<? super AnyType>>
				   void heapSortReverse( AnyType[] a )
    {
	    //COMPLETEZ
        for( int i = a.length / 2; i >= 0; i-- )
            percolateDownMaxHeap( a, i, a.length, true);
        for( int i = a.length - 1; i > 0; i-- )
        {
            swapReferences( a, 0, i );
            percolateDownMaxHeap( a, 0, i, true);
        }
    }
    
    public String nonRecursivePrintFancyTree()
    {
        String outputString = "";
        String _prefix = "";
        int treeIndex = 1;
        Boolean fromLeftChild = false;
        
        while (treeIndex > 0) {
            outputString += _prefix + "|__" + array[treeIndex] + "\n";

            // Enfant de gauche
            if (treeIndex * 2 <= currentSize) {
                _prefix += fromLeftChild ? "|  " : "   ";
                fromLeftChild = true;
                treeIndex *= 2;
            }
            // Voisin de la derniere valeur imprimée
            else if (fromLeftChild & treeIndex + 1 <= currentSize) {
                fromLeftChild = false;
                treeIndex++;
            }
            // Si pas d'enfants gauche ni voisin
            else {
                // On trouve l'enfant gauche le plus proche 
                do {
                    treeIndex = treeIndex /2;
                    if(treeIndex > 0) {
                        _prefix = _prefix.substring(0, _prefix.length() - 3);
                    }

                } while(treeIndex % 2 != 0 & treeIndex > 0);
                
                if(treeIndex > 0) {
                    // L'index est maintenant sur le voisin de cet enfant
                    treeIndex +=1;
                }
                fromLeftChild =  false;
            }
        }
	    return outputString;
    }
    
    public String printFancyTree()
    {
    	return printFancyTree(1, "");
    }
    
    private String printFancyTree( int index, String prefix)
    {
		String outputString;
		
		outputString = prefix + "|__";
		
		if( index <= currentSize ){
			boolean isLeaf = index > currentSize/2;
			
			outputString += array[ index ] + "\n";
			
			String _prefix = prefix;
			
			if( index%2 == 0 )
			    _prefix += "|  "; // un | et trois espace
			else
			    _prefix += "   " ; // quatre espaces
			
			if( !isLeaf ) {
			    outputString += printFancyTree( 2*index, _prefix);
			    outputString += printFancyTree( 2*index + 1, _prefix);
			}
		}else
		    outputString += "null\n";
		
		return outputString;
    }
    
    private class HeapIterator implements Iterator {

        int modificationsHI;
        int position;

        public HeapIterator() {
            this.modificationsHI = modifications;
            this.position = 0;
        }
	
        public boolean hasNext() {
            //COMPLETEZ
            return ++position <= currentSize;
        }

        public Object next() throws NoSuchElementException,
                        ConcurrentModificationException,
                        UnsupportedOperationException {
            //COMPLETEZ
            if(!hasNext())
                throw new NoSuchElementException();
            if(modificationsHI != modifications)
                throw new ConcurrentModificationException();
            else
                return array[++position];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}
