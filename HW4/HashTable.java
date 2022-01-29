import java.util.*;
import java.lang.*;
import java.io.*;
import java.math.BigInteger;

public class HashTable {

    private class Entry {
        private String key; 
        private int count;
        private Entry next; 
            
        private Entry(String key) {
            this.key = key;
            next = null;
            count = 1;
        }
    }
    
    private Entry[] table;
    private int tableSize;
    private final double maxLoadFactor = 0.75;
    private double loadFactor;
    private int numElements;
    
    //Constructor 
    public HashTable(int size){
        //make sure the new HashTable is created with prime-number size
        BigInteger temp = new BigInteger(String.valueOf(size));
        this.tableSize = temp.nextProbablePrime().intValue();
        this.table = new Entry[tableSize];
        this.numElements = 0;
        this.loadFactor = 0;
    }

    private int getKeyIndex(String key) {
        return Math.abs(key.hashCode())%tableSize;
    }
    
    /**
     * Track as if the number of elements in the table is over 75% or not
     */
    private void trackLF(){
        loadFactor = ((double)numElements/(double)tableSize);
        //reharsh if the load factor is larger than the max load factor
        if (this.loadFactor > maxLoadFactor)
            rehash();
    }
    
    /**
     * Rehashing the table by make it into the nearest prime-number of the double number of the current table size
     */
    private void rehash(){
        //store the current table and its size
        Entry[] oldTable = table; 
        int oldSize = tableSize;
        
        //this prime number will be the number size of the table 
        BigInteger temp = new BigInteger(String.valueOf(oldSize * 2));
        tableSize = temp.nextProbablePrime().intValue();
        
        //create the new table with new size
        this.table = new Entry[tableSize];
        
        //put all the things from the previous table to the current table
        for (int i = 0; i < oldSize; i++){
            if (oldTable[i] != null) {
                addHelper(oldTable[i]);
            }
        }
    }
    
    //Helper method for add
    private void addHelper(Entry entry){
        //Setting index
        int index = getKeyIndex(entry.key);
        Entry trav = table[index];
        
        //if the index is empty, add the entry to the index
        if (trav == null) {
            table[index] = entry;
            numElements++;
            trackLF();
            return;
        }
        
        //if the index is not empty but has the same entry
        if (trav != null && trav.key.hashCode() == entry.key.hashCode()){
            table[index].count++;
            numElements++;
            trackLF();
            return;
        }
        
        //traverse through the list 
        while (trav.next != null) { 
            trav = trav.next;
            
            //if the entry is found, just increase the count
            if (trav.key.hashCode() == entry.key.hashCode()){
                trav.count++;
                numElements++;
                trackLF();
                return;
            }
        }
        
        //if the entry doesn't exist, add it to the end 
        trav.next = entry;
        trackLF();
    }


    //add method 
    public void add(String str) {
        Entry curr; 
        
        //Split the string into words 
        String[] words = str.split("\\P{Alpha}+");
        for (int i = 0; i < words.length; i++) {
            //convert all of them into lower case and add to the table;
            String s = words[i].toLowerCase();
            curr = new Entry(s);
            addHelper(curr);
        }
        
    }
    
    /**
     * Printing key and count out by traversing through the table
     */
    public void print(){
        for (int i = 0; i < table.length; i++) {
            Entry trav = table[i];
            while (trav != null) {
            System.out.println(trav.key + " : " + trav.count);
            trav = trav.next;
            }
        }
    }
}
