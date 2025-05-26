import java.util.Arrays;

public class Sorts {

    public static void insertionSort(Integer[] a) {
        for (int j = 2; j < a.length; j++) {
            Integer key = a[j];
            int i = j - 1;
            while (i > 0 && a[i] > key) {
                a[i + 1] = a[i];
                i = i - 1;
            }
            a[i + 1] = key;
        }
    }

    public static Integer[] mergeSort(Integer[] a) {
        int n = a.length;
        if (n <= 1) {
            return a;
        }
        Integer[] left = Arrays.copyOfRange(a, 0, n / 2);
        Integer[] right = Arrays.copyOfRange(a, n / 2, n);

        left = mergeSort(left);
        right = mergeSort(right);

        return mergeSort(left, right);

    }

    public static Integer[] mergeSort(Integer[] A, Integer[] B) {
        Integer[] C = new Integer[A.length + B.length];
        int i = 0, j = 0, k = 0;

        while (i < A.length && j < B.length) {
            if (A[i] > B[j]) {
                C[k++] = B[j++];
            } else {
                C[k++] = A[i++];
            }
        }

        while (i < A.length) {
            C[k++] = A[i++];
        }
        while (j < B.length) {
            C[k++] = B[j++];
        }

        return C;
    }

    public static int[] countingSort(Integer[] a, Integer k) {
        int[] count = new int[k + 1];
        int[] output = new int[a.length];
        int size = a.length;

        for (int i = 0; i < size; i++) {
            int j = a[i];
            count[j]++;
        }

        for (int i = 1; i <= k; i++) {
            count[i] += count[i - 1];
        }

        for (int i = size - 1; i >= 0; i--) {
            int j = a[i];
            count[j]--;
            output[count[j]] = a[i];
        }
        return output;

    }

    public static Integer findMax(Integer[] a){
        Integer maxNumber = a[0];
        for (int i = 0 ; i<a.length; i++){
            if (a[i] > maxNumber){
                maxNumber = a[i];
            }
        }
        return maxNumber;
    }


}