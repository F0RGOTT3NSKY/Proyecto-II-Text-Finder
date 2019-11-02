package Application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

class QuickSort 
{ 
	/* This function takes last element as pivot, 
	places the pivot element at its correct 
	position in sorted array, and places all 
	smaller (smaller than pivot) to left of 
	pivot and all greater elements to right 
	of pivot */
	int partition(ArrayList<String> arr, int low, int high) 
	{ 
		String pivot = arr.get(high); 
		int i = (low-1); // index of smaller element 
		for (int j=low; j<high; j++) 
		{ 
			// If current element is smaller than the pivot 
			if (arr.get(j).compareToIgnoreCase(pivot)<0) 
			{ 
				i++; 

				// swap arr[i] and arr[j] 
				String temp = arr.get(i); 
				arr.set(i, arr.get(j)); 
				arr.set(j, temp); 
			} 
		} 

		// swap arr[i+1] and arr[high] (or pivot) 
		String temp = arr.get(i+1); 
		arr.set(i+1, arr.get(high)); 
		arr.set(high, temp);

		return i+1; 
	} 


	/* The main function that implements QuickSort() 
	arr[] --> Array to be sorted, 
	low --> Starting index, 
	high --> Ending index */
	void sort(ArrayList<String> arr, int low, int high) 
	{ 
		if (low < high) 
		{ 
			/* pi is partitioning index, arr[pi] is 
			now at right place */
			int pi = partition(arr, low, high); 

			// Recursively sort elements before 
			// partition and after partition 
			sort(arr, low, pi-1); 
			sort(arr, pi+1, high); 
		} 
	} 

	/* A utility function to print array of size n */
	static void printArray(ArrayList<String> arr) 
	{ 
		int n = arr.size(); 
		for (int i=0; i<n; ++i) 
			System.out.print(arr.get(i)+" "); 
		System.out.println(); 
	}


}