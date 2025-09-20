package org.example.genericshellsort;

import java.util.Comparator;
import java.util.List;

public final class BubbleSortFlag {
    private BubbleSortFlag() {}

    public static class Stats {
        public int comparisons = 0;
        public int swaps = 0;
    }

    public static <T> Stats sort(List<T> list, Comparator<? super T> cmp) {
        Stats stats = new Stats();
        if (list == null || list.size() < 2) return stats;
        int n = list.size();
        while (n > 1) {
            boolean swapped = false;
            int newN = 0;
            for (int i = 1; i < n; i++) {
                stats.comparisons++;
                if (cmp.compare(list.get(i - 1), list.get(i)) > 0) {
                    swap(list, i - 1, i);
                    stats.swaps++;
                    swapped = true;
                    newN = i;
                }
            }
            n = newN;
            if (!swapped) break;
        }
        return stats;
    }

    private static <T> void swap(List<T> list, int i, int j) {
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }
}
