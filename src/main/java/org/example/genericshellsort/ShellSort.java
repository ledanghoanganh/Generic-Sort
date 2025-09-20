package org.example.genericshellsort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Shell Sort generic (unstable) với dãy khoảng cách Ciura (mở rộng).
 * Hỗ trợ sắp cho List<T> và mảng T[] bằng Comparator<? super T>.
 * Thống kê: comparisons (so sánh), moves (ghi/di chuyển).
 */
public final class ShellSort {
    private ShellSort() {}

    public static class Stats {
        public long comparisons = 0; // số lần so sánh trong vòng while
        public long moves = 0;       // số lần ghi/di chuyển (set/assign)
        @Override public String toString() {
            return "comparisons=" + comparisons + ", moves=" + moves;
        }
    }

    /** Sắp xếp List<T> bằng Shell Sort (Ciura). */
    public static <T> Stats sort(List<T> list, Comparator<? super T> cmp) {
        Stats stats = new Stats();
        int n = list.size();
        if (n < 2) return stats;

        int[] gaps = ciuraGaps(n);
        for (int gap : gaps) {
            for (int i = gap; i < n; i++) {
                T temp = list.get(i);
                int j = i;
                // Insertion-sort trên các chuỗi cách nhau 'gap'
                while (j >= gap) {
                    stats.comparisons++;
                    if (cmp.compare(list.get(j - gap), temp) > 0) {
                        list.set(j, list.get(j - gap)); stats.moves++;
                        j -= gap;
                    } else break;
                }
                list.set(j, temp); stats.moves++;
            }
        }
        return stats;
    }

    /** Sắp xếp mảng T[] bằng Shell Sort (Ciura). */
    public static <T> Stats sort(T[] a, Comparator<? super T> cmp) {
        Stats stats = new Stats();
        int n = a.length;
        if (n < 2) return stats;

        int[] gaps = ciuraGaps(n);
        for (int gap : gaps) {
            for (int i = gap; i < n; i++) {
                T temp = a[i];
                int j = i;
                while (j >= gap) {
                    stats.comparisons++;
                    if (cmp.compare(a[j - gap], temp) > 0) {
                        a[j] = a[j - gap]; stats.moves++;
                        j -= gap;
                    } else break;
                }
                a[j] = temp; stats.moves++;
            }
        }
        return stats;
    }

    /** Dãy khoảng cách Ciura mở rộng (nhân ~2.25) đến khi >= n, rồi đảo ngược để chạy từ lớn -> nhỏ. */
    private static int[] ciuraGaps(int n) {
        int[] base = {1, 4, 10, 23, 57, 132, 301, 701, 1750};
        List<Integer> gaps = new ArrayList<>();
        for (int g : base) gaps.add(g);
        // mở rộng nếu cần
        while (gaps.get(gaps.size() - 1) < n) {
            int next = (int) Math.round(gaps.get(gaps.size() - 1) * 2.25);
            if (next == gaps.get(gaps.size() - 1)) next++;
            gaps.add(next);
        }
        // loại các gap >= n (không hữu ích), giữ < n, và đảo ngược
        List<Integer> filtered = new ArrayList<>();
        for (int g : gaps) if (g < n) filtered.add(g);
        int m = filtered.size();
        int[] arr = new int[m];
        for (int i = 0; i < m; i++) arr[i] = filtered.get(m - 1 - i);
        return arr.length == 0 ? new int[]{1} : arr;
    }
}
