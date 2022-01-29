import java.util.*;

public class CustomSQueue {
    private Stack<Integer> s1 = new Stack<Integer>();
    private Stack<Integer> s2;
    

    public CustomSQueue(Stack s1){
        this.s1 = s1;
        this.s2 = new Stack<Integer>();
    }
    
    public boolean add (int i) {
        s1.push(i);
        return true;
    }
    

    public int poll() {
        if (s1.empty()) {
            System.out.println("Empty1");
            return -1; 
        }
        
        else {
            while (!s1.empty()) {
                s2.push(s1.pop());
            }
            
            int remove = s2.pop();
            
            while (!s2.empty()) {
                s1.push(s2.pop());
            }

            return remove;
        }
    }

    public String print() {
        return s1.toString();
    }
    

    public static void main(String[] args) {
        Stack ex1 = new Stack();
        
        for (int i = 5; i <= 9; i++) {
            ex1.push(i);
        }
        
        System.out.println("Printing the elements in the current stack");
        System.out.println(ex1);
        
        CustomSQueue d = new CustomSQueue(ex1);
        
        System.out.println("Testing the poll method");
        int x = d.poll();
        System.out.println(x);
        
        System.out.println("Testing the poll method");
        x = d.poll();
        System.out.println(x);
        
        System.out.println("Testing the add method");
        d.add(2);
        System.out.println(d.print());
    }

}
