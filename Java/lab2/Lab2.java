import java.util.*;

public class Lab2 {

    // 1. Найти наибольшую подстроку без повторяющихся символов
    public static String task_one(String s) {
        if (s == null || s.length() == 0) return "";

        Map<Character, Integer> map = new HashMap<>();
        int maxLen = 0;
        int start = 0;
        int maxStart = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (map.containsKey(c) && map.get(c) >= start) {
                start = map.get(c) + 1;
            }
            map.put(c, i);
            if (i - start + 1 > maxLen) {
                maxLen = i - start + 1;
                maxStart = start;
            }
        }
        return s.substring(maxStart, maxStart + maxLen);
    }

    // 2. Объединить два отсортированных массива
    public static int[] task_two(int[] arr1, int[] arr2) {
        int[] result = new int[arr1.length + arr2.length];
        int i = 0, j = 0, k = 0;

        while (i < arr1.length && j < arr2.length) {
            if (arr1[i] < arr2[j]) {
                result[k++] = arr1[i++];
            } else {
                result[k++] = arr2[j++];
            }
        }

        while (i < arr1.length) {
            result[k++] = arr1[i++];
        }

        while (j < arr2.length) {
            result[k++] = arr2[j++];
        }

        return result;
    }

    // 3. Найти максимальную сумму подмассива
    public static int task_three(int[] nums) {
        int maxSum = nums[0];
        int currentSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentSum = Math.max(nums[i], currentSum + nums[i]);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    // 4. Повернуть массив на 90 градусов по часовой стрелке
    public static int[][] task_four(int[][] matrix) {
        int n = matrix.length;
        int[][] rotated = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[j][n - 1 - i] = matrix[i][j];
            }
        }

        return rotated;
    }

    // 5. Найти пару элементов в массиве, сумма которых равна заданному числу
    public static int[] task_five(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            if (map.containsKey(complement)) {
                return new int[]{map.get(complement), i};
            }
            map.put(nums[i], i);
        }
        return null;
    }

    // 6. Найти сумму всех элементов в двумерном массиве
    public static int task_six(int[][] matrix) {
        int sum = 0;
        for (int[] row : matrix) {
            for (int num : row) {
                sum += num;
            }
        }
        return sum;
    }

    // 7. Найти максимальный элемент в каждой строке двумерного массива
    public static int[] task_seven(int[][] matrix) {
        int[] maxElements = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int max = matrix[i][0];
            for (int j = 1; j < matrix[i].length; j++) {
                if (matrix[i][j] > max) {
                    max = matrix[i][j];
                }
            }
            maxElements[i] = max;
        }
        return maxElements;
    }

    // 8. Повернуть двумерный массив на 90 градусов против часовой стрелки
    public static int[][] task_eight(int[][] matrix) {
        int n = matrix.length;
        int[][] rotated = new int[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[n - 1 - j][i] = matrix[i][j];
            }
        }

        return rotated;
    }

    public static void main(String[] args) {
        // Примеры использования функций
        System.out.println(task_one("abcabacacbbbaaacbacbbbcabcbaacabcabacbaccbb")); // "abcabacac"
        System.out.println(Arrays.toString(task_two(new int[]{1, 3, 5}, new int[]{2, 4, 6}))); // [1, 2, 3, 4, 5, 6]
        System.out.println(task_three(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})); // 6
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};
        System.out.println(Arrays.deepToString(task_four(matrix))); // [[7, 4, 1], [8, 5, 2], [9, 6, 3]]
        System.out.println(Arrays.toString(task_five(new int[]{2, 7, 11, 15}, 9))); // [0, 1]
        System.out.println(task_six(matrix)); // 45
        System.out.println(Arrays.toString(task_seven(matrix))); // [3, 6, 9]
        System.out.println(Arrays.deepToString(task_eight(matrix))); // [[3, 6, 9], [2, 5, 8], [1, 4, 7]]
    }
}