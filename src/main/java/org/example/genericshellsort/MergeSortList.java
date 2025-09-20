package org.example.genericshellsort;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public final class MergeSortList {
    private MergeSortList() {}

    public static class Stats {
        public int comparisons = 0;
        public int moves = 0;
    }

    public static <T> Stats sort(List<T> list, Comparator<? super T> cmp) {
        Stats stats = new Stats();
        if (list == null || list.size() < 2) return stats;
        List<T> aux = new ArrayList<>(list);
        sort(list, aux, 0, list.size() - 1, cmp, stats);
        return stats;
    }

    private static <T> void sort(List<T> a, List<T> aux, int lo, int hi,
                                 Comparator<? super T> cmp, Stats stats) {
        if (lo >= hi) return;
        int mid = (lo + hi) >>> 1;
        sort(a, aux, lo, mid, cmp, stats);
        sort(a, aux, mid + 1, hi, cmp, stats);
        if (cmp.compare(a.get(mid), a.get(mid + 1)) <= 0) return;
        merge(a, aux, lo, mid, hi, cmp, stats);
    }

    private static <T> void merge(List<T> a, List<T> aux, int lo, int mid, int hi,
                                  Comparator<? super T> cmp, Stats stats) {
        for (int k = lo; k <= hi; k++) aux.set(k, a.get(k));
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) { a.set(k, aux.get(j++)); stats.moves++; }
            else if (j > hi) { a.set(k, aux.get(i++)); stats.moves++; }
            else {
                stats.comparisons++;
                if (cmp.compare(aux.get(i), aux.get(j)) <= 0) {
                    a.set(k, aux.get(i++)); stats.moves++;
                } else {
                    a.set(k, aux.get(j++)); stats.moves++;
                }
            }
        }
    }
}