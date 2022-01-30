import java.util.Random;

public class Sort {
    
    //MERGE SORT 
    public void mergeSort(int[] input){
        int[] temp = new int[input.length];
        myMergeSort(input, temp, 0, input.length - 1);
    }
  private void myMergeSort(int[] input, int[]temp, int start, int end){
        if (start >= end) //base case 
            return;
        
        int mid = (start + end)/2; //the splitting step 
        
        //sort first and second halves
        myMergeSort(input, temp, start, mid);
        myMergeSort(input, temp, mid+1, end);
        
        
        merge(input, temp, start, mid, mid+1, end); //merge the sorted halves
    }
    
    private void merge(int[] input, int[] temp, int leftStart, int leftEnd, int rightStart, int rightEnd){
        int i = leftStart; 
        int j = rightStart;
        int k = leftStart;
        
        while (i  <= leftEnd && j <= rightEnd) {
            if (input[i] >= input[j]) {
              temp[k] = input[i]; 
              i++; 
            }
          
          else{ 
            temp[k] = input[j]; 
            j++; 
          } 
          k++; 
        }
        
      while (i <= leftEnd){ 
        temp[k] = input[i];
        i++; 
        k++; 
      }
        while(j <= rightEnd) {
          temp[k] = input[j];
          j++;
          k++;
        }
        for(int index = leftStart; index <= rightEnd; index++)
            input[index] = temp[index];
    }
  
  //QUICK SORT  
    public void quickSort(int[] input){
        myQuickSort(input, 0, input.length -1);
    }
    
    private void myQuickSort(int[] input, int first, int last){
        if (first >= last)
            return;
        int split = partition(input,first,last);
        
        myQuickSort(input, first, split);
        myQuickSort(input, split + 1, last);
    }
    
  private int partition(int[]input, int first, int last){
        int pivot = input[(first + last)/2];
        int i = first -1;
        int j = last + 1;
        while (true) {
            do {
                i++;
            }
            while (input[i] > pivot);
            
            do {
                j--;
            }
            while(input[j] < pivot);
            
            if(i < j)
                swap(input,i,j);
            else
                return j;
        }
    }
  
  private void swap(int[] input, int i, int j){
        if (i >= input.length || j > input.length){
            return;
        }
        
        int temp = input[i];
        input[i] = input[j];
        input[j] = temp;
    }
   //INSERTION SORT
    public void insertionSort(int[] input) {
        for (int i = 1; i < input.length; i++) {
        int temp = input[i];
int j = 0;
for (j = i; j > 0 && temp > input[j-1]; j--) 
    input[j] = input[j-1];
input[j] = temp;
}
    }
    //upgradeQuickSort
    public void upgradedQuickSort(int[] input, int d, int k){
        myUpgradedQuickSort(input, 0, input.length - 1, d,k);
    }
    
    private void myUpgradedQuickSort(int[] input, int first, int last, int d, int 
k){
          int depth = 0;
          
          //if depth > d, switch to mergeSort
          if (depth >= d) {
              mergeSort(input);
          }
          
          //if subarray length < k, switch to insertion Sort
          if (input.length < k) {
              insertionSort(input);
          }
          
          if (first < last) { 
            int split = partition(input, first, last);
            depth++;
            myUpgradedQuickSort(input, first, split,d,k);
            myUpgradedQuickSort(input, split+1, last, d,k);  
          }
    }
    
    //select
    public int select(int[] input, int k){
        if (k >= input.length){
            System.out.println("k is out of range");
            return -1;
        }
        bucketSort(input);
        return input[input.length - k - 1];
    }
    
    //helper method for select 
    private void bucketSort(int[] array){
        int length = array.length; 
        
        //no elements in the array
        if (length == 0){
            return;
        }
        
        //find the bucket size
        int min = array[0];
        int max = array[0];
        
        for (int i = 0; i < length; i++){
            if (array[i] > max)
                max = array[i];
            else if (array[i] < min)
                min = array[i];
               }
        
        //add elements to the bucket
        int bucketSize =  (max - min) + 1;
        int[] buckets = new int[bucketSize];
        for (int j = 0; j < length; j++) {
           buckets[array[j] - min]++;
        }
        
        //sort + place back to the input array
        int currIndex = 0;
        for (int m = 0; m < buckets.length; m++){
            for (int n = 0; n < buckets[m]; n++){
                array[currIndex++] = m + min;
            }
        }
    }
      //EXTRA METHOD
    //to printArray
    public void printArray(int[] input){
        for (int i = 0; i < input.length; i++)
            System.out.print(input[i] + " ");
        
        System.out.println();
    }
    
    //randomly generate interger array
    public int[] randArr(int[] input){
        Random rd = new Random();
        for (int i = 0; i < input.length; i++){
            input[i] = (int)(rd.nextDouble()*50);
        }
        return input;
    }
  public static void main(String[] args){
        Sort test = new Sort();
        int[] arr = new int[10];
        
        //mergeSort
        System.out.println("Testing mergeSort:");
        System.out.println("Before sort");
        test.randArr(arr);
        test.printArray(arr);
        
        System.out.println("After sort");
        test.mergeSort(arr);
        test.printArray(arr);
        
        //quickSort
        System.out.println("\nTesting quickSort:");
        System.out.println("Before sort:");
        test.randArr(arr);
        test.printArray(arr);
        System.out.println("After sort");
        test.quickSort(arr);
        test.printArray(arr);
    /insertionSort
        System.out.println("\nTesting insertionSort:");
        System.out.println("Before sort:");
        test.randArr(arr);
        test.printArray(arr);
        System.out.println("After sort");
        test.insertionSort(arr);
        test.printArray(arr);
        
        //upgradeQuickSort
        System.out.println("\nTesting upgradedQuickSort:");
        System.out.println("Before sort:");
        test.randArr(arr);
        test.printArray(arr);
        System.out.println("After sort");
        test.upgradedQuickSort(arr,5,2);
        test.printArray(arr);
        
        
        //select
        System.out.println("\nTesting select:");
        test.randArr(arr);
        test.printArray(arr);
        for (int i = 0; i < arr.length;i++){
            System.out.println(test.select(arr,i));
        }
    }
}
