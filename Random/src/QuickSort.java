public class QuickSort {

    private final int SIZE = 10;
    private final int MIN = 0;
    private final int MAX = 100;
    private int[] myArray;

    public static void main(String[] args) {
        QuickSort mySort = new QuickSort();
    }


    public QuickSort(){
        myArray= createRandomArray(MIN,MAX,SIZE);
        printArray(MIN,MAX);
        quickSort(0,myArray.length-1);

    }
    public void quickSort(int left,int right){
        if(right<=left){
            return;
        } else{
       int pivotLocation = partition(left,right,myArray[right]);
       printArray(left,right);
       quickSort(left,pivotLocation-1);
       quickSort(pivotLocation+1,right);
        }
    }
    private int partition(int left, int right, int pivot){
        System.out.println("Pivot: " + pivot);
        int leftPointer= left-1;
        int rightPointer = right;
        while(true){
            while (myArray[++leftPointer]<pivot);

            while (rightPointer>0&&myArray[--rightPointer]>=pivot);
            printArray(leftPointer,rightPointer);
            System.out.println();
            if(leftPointer>=rightPointer) {
                //swaps the pivot value into the middle once done
                System.out.println("Swapped: " + myArray[leftPointer]+" at pos "+leftPointer +" and "+myArray[right]+
                                " at pos " + right );
                swap(leftPointer,right);
                return leftPointer;
            }
            else{
                System.out.println("Swapped: " + myArray[leftPointer]+" at pos "+leftPointer +" and "+myArray[rightPointer]+
                        " at pos " + rightPointer );
                swap(leftPointer,rightPointer);

            }
        }
        //starts on left
        //finds value greater than pivot
        //starts on right
        //finds value less than pivot
        //swaps


    }
    private int[] getArray(){
        return (myArray);
    }

     void printArray(int i, int j) {

         for (int n = 0; n < 61; n++)
             System.out.print("-");
         System.out.println();

         for (int n = 0; n < myArray.length; n++) {

             System.out.format("| %2s " + " ", n);
         }
         System.out.println("|");

         for (int n = 0; n < 61; n++)
             System.out.print("-");

         System.out.println();

         for (int n = 0; n < myArray.length; n++) {

             System.out.print(String.format("| %2s " + " ", myArray[n]));

         }
         System.out.println("|");
         for (int n = 0; n < 61; n++) System.out.print("-");
         System.out.println();

         if (i != -1) {

             // Number of spaces to put before the F

             int spacesBeforeFront = 5 * i + 1;

             for (int k = 0; k < spacesBeforeFront; k++) System.out.print(" ");

             System.out.print("L");

             // Number of spaces to put before the R

             int spacesBeforeRear = (5 * j + 1 - 1) - spacesBeforeFront;
             for (int l = 0; l < spacesBeforeRear; l++) System.out.print(" ");
             System.out.print("R");

             System.out.println("\n");

         }
     }


        private void swap(int pos, int pos2){
        int temp = myArray[pos];
        myArray[pos] = myArray[pos2];
        myArray[pos2] = temp;
    }
    private int[] createRandomArray (int min, int max, int size){
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
           arr[i]= (int) (Math.random()*(max-min)+min);
        }
        return arr;
    }

}
