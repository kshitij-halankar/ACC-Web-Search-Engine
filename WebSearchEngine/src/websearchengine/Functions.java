package websearchengine;

public class Functions {
	 private static void swap(IndexObject indexObject, int i, int j)
	    {
	        int tempFreq = indexObject.freq.get(i);
			indexObject.freq.add(i,indexObject.freq.get(j));
			indexObject.freq.add(j,tempFreq);

			String tempDoc = indexObject.documentName.get(i);
			indexObject.documentName.add(i,indexObject.documentName.get(j));
			indexObject.documentName.add(j,tempDoc);

	    }

	    private static int partition(IndexObject indexObject, int low, int high)
	    {
			int pivot = indexObject.freq.get(high);
	        int i = (low - 1);
	        for(int j = low; j < high; j++)
	        {
	            if (indexObject.freq.get(j) > pivot)
	            {
	                i++;
	                swap(indexObject, i, j);
	            }
	        }
	        swap(indexObject, i + 1, high);
	        return (i + 1);
	    }


	    public static void quickSort(IndexObject indexObject, int low, int high)
	    {
	        if (low < high)
	        {
	            int pivot = partition(indexObject, low, high);
	            quickSort(indexObject, low, pivot - 1);
	            quickSort(indexObject, pivot + 1, high);
	        }
	    }
}
