import java.util.Arrays;

public class MaxHeap {
    private int[] array;
    private int size;
    public MaxHeap(int[] arr){
        size=0;
        array = new int[arr.length];
        for (int value : arr) {
            add(value);
        }
    }

    public MaxHeap(int size){
        array=new int[size];
    }

    public void add(int i){

        if(size++>=array.length){
            int[] newArr = new int[array.length*2];
            System.arraycopy(array, 0, newArr, 0, array.length);
            array = newArr;
        }

        array[size-1]=i;
        percolateUp();
    }

    private void percolateUp() {

        int i = size-1;

        while(i>0&&array[getParent(i)]<array[i]){
            swap(i,getParent(i));
            i = getParent(i);

        }

    }

    private void percolateDown() {
        int i = 0;
        while(getLeftChild(i)<=size-1&&array[getGreatestChild(i)]>array[i]){

            swap(i,i = getGreatestChild(i));

        }
    }

    private int getGreatestChild(int i){
        int retVal;
        if(getRightChild(i)==size
                ||array[getLeftChild(i)]>array[getRightChild(i)]){
            retVal = getLeftChild(i);

        }
        else retVal = getRightChild(i);
        return retVal;
    }


    private void swap(int index1, int index2){
        int temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

    private int getLeftChild(int i){
        return i*2+1;
    }
    private int getRightChild(int i){
        return i*2+2;
    }
    private int getParent(int i){
        return (i-1)/2;
    }

    public int pop(){
        if (size-- == 0){
            throw new IndexOutOfBoundsException();
        }
        swap(0,size);
        percolateDown();
        return array[size];
    }

    public void printArray(){
        System.out.println(Arrays.toString(array));
    }
}
