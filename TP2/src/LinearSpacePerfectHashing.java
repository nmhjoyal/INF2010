import java.util.Random;
import java.util.ArrayList;

public class LinearSpacePerfectHashing<AnyType>
{
	static int p = 46337;

	QuadraticSpacePerfectHashing<AnyType>[] data;
	int a, b;

	LinearSpacePerfectHashing()
	{
		a=b=0; data = null;
	}

	LinearSpacePerfectHashing(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	public void SetArray(ArrayList<AnyType> array)
	{
		AllocateMemory(array);
	}

	@SuppressWarnings("unchecked")
	private void AllocateMemory(ArrayList<AnyType> array)
	{
		Random generator = new Random( System.nanoTime() );

		if(array == null || array.size() == 0)
		{
			// A completer
            data = new QuadraticSpacePerfectHashing[0];
			return;
		}
		if(array.size() == 1)
		{
			a = b = 0;

			// A completer
            data = new QuadraticSpacePerfectHashing[1];
			return;
		}

		// A completer
        int n = array.size();
        data = new QuadraticSpacePerfectHashing[n];

        a = generator.nextInt(p - 1) + 1;
        b = generator.nextInt(p - 1);
        int index;

        for(int i = 0; i < n; i++) {
            int key = array.get(i).hashCode();
            index = ((a * key + b) % p) % n;
            if(data[index].containsKey(key)) {

            }else{
                data[index] = new QuadraticSpacePerfectHashing<AnyType>();
            }
        }
	}

	public int Size()
	{
		if( data == null ) return 0;

		int size = 0;
		for(int i=0; i<data.length; ++i)
		{
			size += (data[i] == null ? 1 : data[i].Size());
		}
		return size;
	}

	public boolean containsKey(int key)
	{
		// A completer
        int index = ((a * key + b) % p) % data.length;
        return (data[index].containsKey(key));
	}
	
	public int getKey (AnyType x) {
		// A completer
        return x.hashCode();
	}
	
	public boolean containsValue (AnyType x) {
		// A completer
        int index = ((a * x.hashCode() + b) % p) % data.length;
        if(data[index] != null)
            return (data[index].containsValue(x));
        return false;
	}
	
	public void remove (AnyType x) {
		// A completer
        int index = ((a * x.hashCode() + b) % p) % data.length;
        data[index].remove(x);
	}

	public String toString () {
		String result = "";
		
		// A completer
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                result += data[i].toString();
                result += "\n";
            }
        }
		return result; 
	}

	public void makeEmpty () {
		// A completer
        for (QuadraticSpacePerfectHashing item : data) {
            item.makeEmpty();
        }
   	}
	
}
