package org.example.genericshellsort;
import java.util.Comparator;
import java.util.List;

public final class InsertionSort {
    private InsertionSort() {}

    public static class Stats {
        public int comparisons = 0;
        public int shifts = 0;
    }

    public static <T> Stats sort(List<T> list, Comparator<? super T> cmp) {
        Stats stats = new Stats();
        for (int i = 1; i < list.size(); i++) {
            T key = list.get(i);
            int j = i - 1;
            while (j >= 0) {
                stats.comparisons++;
                if (cmp.compare(list.get(j), key) > 0) {
                    list.set(j + 1, list.get(j));
                    stats.shifts++;
                    j--;
                } else break;
            }
            list.set(j + 1, key);
        }
        return stats;
    }
}