package org.example.genericshellsort;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainApp {
    public static void main(String[] args) throws Exception {
        List<Employee> employees = loadEmployees();

        System.out.println("== RAW DATA ==");
        employees.forEach(System.out::println);

        // Bubble Sort
        List<Employee> bubble = new ArrayList<>(employees);
        var bubbleStats = BubbleSortFlag.sort(bubble, EmployeeComparators.BY_CODE_ASC);
        System.out.println("\n== Bubble Sort (by code ASC) ==");
        bubble.forEach(System.out::println);
        System.out.printf("Comparisons: %d, Swaps: %d%n", bubbleStats.comparisons, bubbleStats.swaps);
        
        //Shaker Sort
        List<Employee> shaker = new ArrayList<>(employees);
        var shakerStats = ShakerSort.sort(shaker, EmployeeComparators.BY_CODE_ASC);
        System.out.println("\n== Shaker Sort (by code ASC) ==");
        shaker.forEach(System.out::println);
        System.out.printf("Comparisons: %d, Swaps: %d%n", shakerStats.comparisons, shakerStats.swaps);

        // Merge Sort
        List<Employee> merge = new ArrayList<>(employees);
        var mergeStats = MergeSortList.sort(merge, EmployeeComparators.BY_FIRSTNAME_THEN_LASTNAME_ASC);
        System.out.println("\n== Merge Sort (by firstName+lastName ASC) ==");
        merge.forEach(System.out::println);
        System.out.printf("Comparisons: %d, Moves: %d%n", mergeStats.comparisons, mergeStats.moves);

        // Insertion Sort
        List<Employee> insertion = new ArrayList<>(employees);
        var insertionStats = InsertionSort.sort(insertion, EmployeeComparators.BY_SALARY_DESC);
        System.out.println("\n== Insertion Sort (by salary DESC) ==");
        insertion.forEach(System.out::println);
        System.out.printf("Comparisons: %d, Shifts: %d%n", insertionStats.comparisons, insertionStats.shifts);

        // Shell
        List<Employee> shell = new ArrayList<>(employees);
        var sStats = ShellSort.sort(shell, EmployeeComparators.BY_FIRSTNAME_THEN_LASTNAME_ASC);
        // (tuỳ chọn) in kết quả để đối chiếu
        System.out.println("\n== Shell Sort (by firstName+lastName ASC) ==:");
        shell.forEach(System.out::println);
        System.out.printf("Shell Sort  -> comparisons=%d, moves=%d%n", sStats.comparisons, sStats.moves);
    }

    private static List<Employee> loadEmployees() throws Exception {
        try (InputStream in = MainApp.class.getResourceAsStream("/employees.json")) {
            if (in == null) throw new RuntimeException("employees.json not found in resources!");
            String json = new String(in.readAllBytes(), java.nio.charset.StandardCharsets.UTF_8);
            Type listType = new TypeToken<ArrayList<Employee>>(){}.getType();
            return new Gson().fromJson(json, listType);
        }
    }
}
