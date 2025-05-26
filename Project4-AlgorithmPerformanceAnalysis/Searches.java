public class Searches {
    public static int linearSearch(Integer[] a, Integer number) {
        int size = a.length;
        for (int i = 0; i < size; i++) {
            if (a[i] == number) {
                return i;
            }
        }
        return -1;
    }

    public static int binarySearch(Integer[] a, Integer number) {
        int low = 0;
        int high = a.length - 1;
        while (high - low > 1){
            int mid = (high + low) / 2;

            if (a[mid] < number) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        if (a[low] == number){
            return low;
        } else if (a[high] == number){
            return high;
        }
        return -1;
    }
}
