import java.util.ArrayList;
import java.util.Random;

public class QuadraticSpacePerfectHashing<AnyType> 
{
	static int p = 46337;

	int a, b;
	AnyType[] items;

	QuadraticSpacePerfectHashing()
	{
		a=b=0; items = null;
	}

	QuadraticSpacePerfectHashing(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	public void SetArray(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	public int Size()
	{
		if( items == null ) return 0;

		return items.length;
	}

	public boolean containsKey(int key)
	{
		// A completer
        int index = ((a * key + b) % p) % items.length;
		return (items[index] != null);
	}

	public boolean containsValue(AnyType x )
	{
		// A completer
		int index = ((a * x.hashCode() + b) % p) % items.length;
		
		if (items[index]!=null){
			return (items[index].equals(x));
		}
		else{
			return false;
		}
	}

	public void remove (AnyType x) {
		// A completer
        int index = ((a * x.hashCode() + b) % p) % items.length;
        if(items[index] != null)
            items[index] = null;
	}

	public int getKey (AnyType x) {
		// A completer
        return x.hashCode();
	}

	@SuppressWarnings("unchecked")
	private void AllocateMemory(ArrayList<AnyType> array)
	{
		Random generator = new Random( System.nanoTime() );

		if(array == null || array.size() == 0)
		{
			// A completer
			items = (AnyType[]) new Object[0];
			return;
		}
		if(array.size() == 1)
		{
			a = b = 0;

			// A completer
			items = (AnyType[]) new Object[1];
			items[0] = array.get(0);
			return;
		}

		// A completer
		
		boolean hasCollision = true;
		int n = array.size();
		int m = array.size()*array.size();
		

		while(hasCollision) {

            items = (AnyType[]) new Object[m];
			hasCollision = false;
			a = generator.nextInt(p - 1) + 1;
			b = generator.nextInt(p - 1);
			int index = 0;
			
			for(int i = 0; i < n; i++) {
				
				int code = array.get(i).hashCode();
				index = ((a * code + b) % p) % m;
				
				if(!containsKey(code)) {
					items[index] = array.get(i);
				}else {
					hasCollision = true;
				}
			}
		}
	}

	
	
	public String toString () {
		String result = "";
		
		// A completer
        for (int i = 0; i < items.length; i++) {
            if(items[i] != null) {
                result += "(" + getKey(items[i]) + "," + items[i].toString() + "), ";
            }
        }

//        result = result.replace(result.substring(result.lastIndexOf(", ")), "");
		return result; 
	}

	public void makeEmpty () {
		   // A completer
        for (AnyType item : items) {
            if(containsKey(item.hashCode())){
                remove(item);
            }
        }
   	}
}
