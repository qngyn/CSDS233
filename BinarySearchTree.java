import java.util.Stack;

public class BinarySearchTree {
    private class Node{
        private int key;
        private Node left;
        private Node right;
        
        Node(int key) {
            this.key = key; 
            this.left = null;
            this.right = null;
        }
    }
   
    private Node root; 
    
    //Constructor
    public BinarySearchTree(){
        this.root = null;
    }

    //Helper method to insert a node in the BST
     
    private Node insert (Node root, int key){
        //Find the parent of the new Node
        Node trav = root;
        Node parent = null;

        //if the BST is not empty
        while (trav != null) {
            parent = trav;
 
            //if the key is less than the trav's node, going left
            if (key < trav.key)
                trav = trav.left;
            
            //otherwise, going right
            else 
                trav = trav.right;
        }
        
        //if there is no node in the BST, make the root 
        if (parent == null) {
            return new Node(key);
        }
        
        //add to the left side if the key is smaller than the parent node 
        if (key < parent.key)
            parent.left = new Node(key);
        
        //otherwise, add to the right side 
        else 
            parent.right = new Node(key);
 
        return root;
    }
        
  
     //inserts a node in the BST
    
    public void insert(int key){
        System.out.println("A node with key " + key + " is added");
        root = insert(root, key); 
    }
    
    
     // printing all the node of the tree in order transversal 
        
    private void inorder(Node root){
        //if there is node in the tree
        if (root != null) { 
           inorder(root.left);
           System.out.print(root.key + " ");
           inorder(root.right);
        }
    }

    // inorder traversal of BST

    public void inorder(){
        inorder(root);
    }
    

     // Helper method to find the sum of the keys of the node in the BST
   
    private int sum(Node root){
        //base case 
        //if the root is null, it is 0
        if (root == null) 
            return 0;
        //recursive 
        return (root.key + sum(root.left) + sum(root.right));
    }

     // sum of all the keys of a BST
    
    public int sum(){
        System.out.println("Sum of all the keys is: " + sum(root));
        return sum(root);
    }

     // Helper method for search a node with a specific key in BST
    
    private Node search(Node root, int key){
        //base case 1
        //if the BST is empty or no node contains that key, nothing to search
        if (root == null) 
            return null;
        
        //base case 2 
        else if (root.key == key)
            return root; 
        
        //search at the left side if the key is smaller than the current node's key
        else if (key < root.key)
            //recur on left child 
            return search(root.left, key);

        //search at the right side if the key is bigger than the current noode's keyy
        else 
            //recur on right child 
            return search(root.right, key);
    }

     //search a node with a specific key in BST
     
    public Node search (int key){
        Node node = search(root,key);
        if (node != null) 
            System.out.println("There is a node contain key " + key + " in the tree" );
        else
            System.out.println("There is no node like this in the tree");
        return node;
    }
    
    
     // Helper method for counting the number of node in BST
    private int count(Node root) {
        if (root == null)
            return 0;

        return (1 + count(root.left) + count(root.right));
    }
    
    //Helper method to return the kth smallest node of the BST
     
    private Node kthSmallest(Node root, int k) {
        int numNode = count(root);
        
        //create a stack to add node 
        Stack<Node> stack = new Stack<Node>();
        
        //add all the node from the left side, from top to bottom
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
        
        //return the kth smallest by counting down the k
        while (k != 0 && numNode != 0 && k <= numNode) {
            Node returnNode = stack.pop();
            k--; 
            
            //return when the k node is zero
            if (k == 0)
                return returnNode;
            
            //if after pushing all the node but the k is still not zero, starting to add node from the right
            else {
                Node node = returnNode.right;
                while (node != null){
                    stack.push(node);
                    node = node.left;
                }
            }
        }
        
        //if root == null or k is invalid 
        return null;
    }
    
    // the kth smallest node of the BST
    public int kthSmallest(int k){
        Node node =  kthSmallest(root,k);
        if (node == null){
            System.out.println("Invalid k or the tree is empty");
            return 0;
        }
        
        System.out.println("The " + k + "th smallest is " + node.key);
        return node.key;
    }
    

    // Helper method that need to delete the node in BST
    private Node delete(Node root, int key){
    // Base case
    if (root == null)
        return null;

    //Recursive call
    if (key < root.key){
        root.left = delete(root.left, key);
        return root;
    }
    
    else  if (key > root.key) {
        root.right = delete(root.right, key);
        return root;
    }

    //Case 1 and Case 2
    if (root.left == null) {
        return root.right;
    }
    
    else if (root.right == null) {
        return root.left;
    }
 
    // Case 3
    else {
        
        //Find the deepest left node of the right subtree, replace it will the root 
        Node prev = root;
        Node curr = root.right;
         
        while (curr.left != null) {
            prev = curr;
            curr = curr.left;
        }

        
        root.key = curr.key;
        if (prev != root)
            prev.left = curr.right;
        else
            prev.right = curr.right;
        
        return root;
    }
}
    

     //delete a node from BST

    public int delete (int key){

        return delete(root, key).key;
    }
}
