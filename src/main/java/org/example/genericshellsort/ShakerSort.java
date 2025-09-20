package org.example.genericshellsort;

import java.util.Comparator;
import java.util.List;

public final class ShakerSort {
	private ShakerSort() {}
	
	public static class Stats {
		int comparisons = 0;
		int swaps = 0;
	}
	
	public static <T> Stats sort(List<T> list, Comparator<? super T> cmp) {
		Stats stats = new Stats();
		int n = list.size();
		if (n <= 1) return stats;
		boolean swapped;	
		
		int left = 0; int right = n - 1;
		while (left < right) {
			swapped = false;
			for (int i = left; i < right; i++) {
				stats.comparisons++;
				if (cmp.compare(list.get(i), list.get(i+1)) > 0) {
					swap(list, i, i+1);
					swapped = true;
					stats.swaps++;
				}
				right--;
				
				if (!swapped) break;
				
				for (int j = right; j > left; j--) {
					stats.comparisons++;
					if (cmp.compare(list.get(j), list.get(j-1)) < 0) {
						swap(list, j, j-1);
						swapped = true;
						stats.swaps++;
					}
				}
				left++;
				
				if (!swapped) break;
			}
		}
		return stats;
	}
	
	private static <T> void swap(List<T> list, int i, int j) {
		T temp = list.get(i);
		list.set(i, list.get(j));
		list.set(j, temp);
	}
}
