public class Test {

    /**
     * @param args the command line arguments
     */
public static void main(String[] args){
        BinarySearchTree keys = new BinarySearchTree();
        
        System.out.println("Test inorder and insert:");
        keys.inorder();
        //Add node into the tree
        keys.insert(15);
        keys.insert(10);
        keys.insert(20);
        keys.insert(8);
        keys.insert(12);
        keys.insert(16);
        keys.insert(25);

        //Printing out the tree
        keys.inorder();
        System.out.println();
        //Test sum
        System.out.println("Test sum");
        keys.sum();
        
        
        System.out.println("Test search: ");
        keys.search(25);
        keys.search(30);
        keys.search(12);
        
        
        System.out.println("test kthSmallest: ");
        keys.kthSmallest(2);
        keys.kthSmallest(3);
        keys.kthSmallest(15);
        
        System.out.println("Test delete: ");
        keys.delete(15);
        keys.inorder();
        
        System.out.println();
        keys.delete(10);
        keys.inorder();
        
        System.out.println();
        keys.sum();
        keys.kthSmallest(2);
    }
    
}
