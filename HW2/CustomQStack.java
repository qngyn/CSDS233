import java.util.*;

public class CustomQStack {
    private Queue<Integer> queue = new LinkedList<Integer>(); 

    public CustomQStack (Queue queue){
        this.queue = queue;
    }

    public boolean empty(){
      return this.queue.peek() == null;  
    }
    
    public boolean push(int i){
        return this.queue.add(i); 
    }
    

    public int pop() {
        if (this.empty()) {
            System.out.println("Queue is empty");
            return -1;
        }
        
        else {
            for (int i = 0; i < this.queue.size() - 1; i++) {
                int j = this.queue.poll();
                this.queue.add(j);
            } 
            return this.queue.poll();
        } 
    } 
       
    //Testing the CustomQStack class
    public static void main(String[]args) {
        Queue<Integer> e = new LinkedList<Integer>();    
        CustomQStack d = new CustomQStack(e);
        
        System.out.println("Testing empty method before adding element into the queue");
        System.out.println(d.empty());
        
        Queue<Integer> e1 = new LinkedList<Integer>(); 
        
        //add element to queue
        for (int i = 5; i <= 9; i++) {
            e1.add(i);
        }
        
        System.out.println("Priting element in queue");
        System.out.println(e1);
      
        d = new CustomQStack(e1);
       
        System.out.println("testing pop method");
        int x = d.pop();
        System.out.println(x);
        
        x = d.pop();
        System.out.println(x);
        System.out.println("testing empty method");
        System.out.println(d.empty());
        
        //Test push method
        System.out.println("Testing push method");
        d.push(1);
        System.out.println(d.queue);

        //Test pop method 
        System.out.println("Test pop method");
        x = d.pop();
        System.out.println(x);
    }
}
