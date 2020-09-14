public class MaxHeapTest {
    public static void main(String[] args) {
        int[] arr = {1,2,5,1,3,16,14,14,33,23,-12};
        MaxHeap heap = new MaxHeap(arr);
        for (int i = 0; i < arr.length; i++) {
            System.out.print(heap.pop()+" ");
        }
    }
}
