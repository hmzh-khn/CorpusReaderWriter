import java.util.ArrayList;


public class QuickSort1 {
	
	static public void sort(ArrayList<WordEntry> words) {
		quicksort(words, 0, words.size()-1);
	}

	static public void quicksort (ArrayList<WordEntry> words, int low, int high) {
		if (low < high) {
			int splitpoint = partition(words, low, high);
			quicksort(words, low, splitpoint);
			quicksort(words, splitpoint+1, high);
		}
	}

	static private int partition(ArrayList<WordEntry> words, int low, int high) {
		WordEntry pivot = words.get(low);//getPivot(nums);
		while(true) {
			while(words.get(low).compareTo(pivot) > 0) {
				low++;
			}
			while(words.get(high).compareTo(pivot) < 0) {
				high--;
			}
			if(low < high) {
				swap(words, low, high);
				low++;
				high--;
			}
			else {
				return high;
			}
		}	
	}

	//swaps two things
	static private void swap(ArrayList<WordEntry> words, int low, int high) {
		WordEntry temp = words.get(low);
		words.set(low, words.get(high));
		words.set(high, temp);
	}
}
