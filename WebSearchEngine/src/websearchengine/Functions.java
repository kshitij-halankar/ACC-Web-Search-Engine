package websearchengine;

import java.util.Collections;

public class Functions {
	private void swap(IndexObject indexObject, int i, int j) {
		Collections.swap(indexObject.indicesHolder, i, j);

	}

	private int partition(IndexObject indexObject, int low, int high) {
		int pivot = indexObject.indicesHolder.get(high).frequency;
		int i = (low - 1);
		for (int j = low; j < high; j++) {
			if (indexObject.indicesHolder.get(j).frequency > pivot) {
				i++;
				swap(indexObject, i, j);
			}
		}
		swap(indexObject, i + 1, high);
		return (i + 1);
	}

	public void quickSort(IndexObject indexObject, int low, int high) {
		if (low < high) {
			int pivot = partition(indexObject, low, high);
			quickSort(indexObject, low, pivot - 1);
			quickSort(indexObject, pivot + 1, high);
		}
	}
}
