import java.math.BigInteger;

/**
 *
 * @author Quynh Nguyen
 */
public class WordCount {
    
    //wordCount method
    public static void wordCount(String str){
        //creating a new hashtable and adding string into it and print the result out 
        HashTable hashtable = new HashTable(7);
        hashtable.add(str);
        hashtable.print();
        
    }

    /**
     * Testing the wordCount Method
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        wordCount("The final season is coming , but I have not prepare for any of them yet. I think I am gonna screw it");
        System.out.println();
        wordCount("I wanna go die right now");
    }
    
}
