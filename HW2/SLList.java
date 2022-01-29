public class SLList {
    private class Node {
        private int item; 
        private Node next; 
        
        public Node(int item, Node next) {
            this.item = item; 
            this.next= next;
        }
        
        public Node (int item) {
            this.item = item; 
            this.next = null;
        }
    }
  
    private Node first; 
    
    public void reverse() {
        Node prev = null; 
        Node curr = first; 
        Node next = null;
        while (curr != null) {
            next = curr.next; 
            curr.next = prev; 
            prev = curr; 
            curr = next;
        }
         first = prev;
    }
  
    public void print() {
        Node curr = first;
        if (curr == null) {
            System.out.println("Empty list");
        }
        
        else {
            while (curr != null) {
                System.out.print(curr.item + " ");
                curr = curr.next;
            }
           System.out.println();
            
        }
    }
    
    public void addFirst (int item) {
        first = new Node (item, first);
    }
    
    public static void main(String[] args) {
        SLList lst = new SLList();
        
        for (int i = 10; i >= 0; i--) {
            lst.addFirst(i);
        }
        
        
        System.out.println("List before reverse");
        lst.print();
        
        System.out.println("List after reverse");
        lst.reverse();
        lst.print();        
        
    }
}
