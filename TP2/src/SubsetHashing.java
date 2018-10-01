import java.util.HashSet;

public class SubsetHashing {
	
	public static int hash(int cle, int taille) {
		if(cle < taille) {
			return taille - cle;
		}else {
			return cle % taille;
		}
	}
	
    /* Return true si Tab2 [] est un sous-ensemble de Tab1 [] */
	static boolean isSubset(int Tab1[], int Tab2[], int m, int n)
    {
        // compléter
        //Trouver le max de Tab1[]
        int max=Tab1[0];
        for(int i = 1; i < m; i++) {
			if (Tab1[i]>max){
                max=Tab1[i];
            }
		}
        
		//Stockage de la Tab1 dans une table de hashage
		int[] hashTable = new int[max];
		for(int i = 0; i < m; i++) {
			hashTable[hash(Tab1[i], max)] = Tab1[i];
		}
		
		//verifier que chaque element (de Tab2) existe dans la hashtable
		for(int j = 0; j < n; j++) {
			if(Tab2[j] != hashTable[hash(Tab2[j], max)]) {
				return false;
			}
			
		}
        return true;
        
       //Complexite aymptotique :
       /*
       Puisque la boucle qui a le plus d'itération est celle avec l'élément m, la compléxiteé
       asymptotique est O(m).

       NOTE: is it 2*O(m)+O(n) ?
       */
       

    } 
 
    public static void main(String[] args) 
    { 
        int T1[] = {5, 11, 12, 1, 10, 3, 7};
        int T2[] = {11, 1, 10};
         
        int m = T1.length;
        int n = T2.length;
     
        if(isSubset(T1, T2, m, n))
            System.out.print("Tab2 [] est un sous-ensemble de Tab1 [] ");
        else
            System.out.print("Tab2 [] n'est pas un sous-ensemble de Tab1 []"); 
    }
}


