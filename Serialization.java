import java.io.*;
import java.util.Queue;
import java.util.LinkedList;

public class Serialization {

    //First create Node of tree
    class Node{
        int item;
        Node left = null,right=null;

        public Node(int item){
            this.item = item;
        }
    }

    Node root;

    Serialization(){
        root = null; //initially creating a null tree
    }

    Serialization(int item){
        root = new Node(item);
    }

    //wrapper function to call insert
    public void insert(int item){
        //that will call actual function
        root = insert(root, item);
    }

    //actual function to insert, that class can call only
    private Node insert(Node root, int item){
        //if empty, insert
        if(root == null){
            root = new Node(item);
            return root;
        }

        //else see if we get to insert to ri8 subtree or left
        if(item < root.item){
            //left side, if item is small
            root.left = insert(root.left, item);
        }
        else if(item > root.item){
            root.right = insert(root.right, item);
        }

        return root;
    }

    //Breadth First Search
    public boolean BFS(int item){
        //we will basically each traveressed elements child, so we can visit them later
        Queue<Node> queue = new LinkedList<Node>();
        queue.add(root);
        while(! queue.isEmpty()){
            Node temp = queue.poll(); // get head
            System.out.println(temp.item);
            //check if that is equal
            if(temp.item == item){
                return true;
            }

            //now enqueue right child in queue
            if(temp.left != null){
                queue.add(temp.left);
            }

            if(temp.right != null){
                queue.add(temp.right);
            }
        }

        //have not found the item
        return false;
    }
    //Depth First Search
    //inorder

    //wrapper function
    public void inorder(){
        inorder(root);
    }
    //actual func
    private void inorder(Node root){
        if(root!=null){
            inorder(root.left); //first left sub tree
            System.out.println(root.item);//current item
            inorder(root.right);//right sub tree
        }
    }

    //Pre order
    //wrapper function
    public void preorder(){
        preorder(root);
    }
    //actual func
    private void preorder(Node root){
        if(root!=null){
            System.out.println(root.item);//current item
            preorder(root.left); //left sub tree
            preorder(root.right);//right sub tree
        }
    }

    //Post order
    //wrapper function
    public void postorder(){
        postorder(root);
    }
    //actual func
    private void postorder(Node root){
        if(root!=null){
            postorder(root.left); //left sub tree
            postorder(root.right);//right sub tree
            System.out.println(root.item);//current item
        }
    }


    //Minimum depth
    public int minimumDepth(){
        return minimumDepth(root);
    }

    private int minimumDepth(Node root){
        //No tree, height 0
        if(root == null)
            return 0;
        //No child, height 1
        if(root.left == null && root.right == null){
            return 1;
        }
        //if there is no left sub tree, we will go for right
        if(root.left == null){
            return minimumDepth(root.right)+1; //+1 to incl this node
        }

        if(root.right == null){
            return minimumDepth(root.left)+1;
        }

        //last case, there are both right and left child
        return Math.min( minimumDepth(root.left), minimumDepth(root.right))+1;
    }

    //Maximum depth
    public int maximumDepth(){
        return maximumDepth(root);
    }

    private int maximumDepth(Node root){
        //No tree, height 0
        if(root == null)
            return 0;
        //No child, height 1
        if(root.left == null && root.right == null){
            return 1;
        }
        //if there is no left sub tree, we will go for right
        if(root.left == null){
            return maximumDepth(root.right)+1; //+1 to incl this node
        }

        if(root.right == null){
            return maximumDepth(root.left)+1;
        }

        //last case, there are both right and left child
        return Math.max( maximumDepth(root.left), maximumDepth(root.right))+1;
    }

    public void serialize()
    {
        String serializedTree = serialize(root);
        try {
            File f = new File("abc.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(f));

            bw.write(serializedTree);

            bw.close();
        }
        catch(FileNotFoundException e)  {

        } catch (IOException e) {

        }
    }

    //using the same logic of level order traversal again
    public String serialize(Node root) {
        if(root==null){
            return "";
        }

        StringBuilder sb = new StringBuilder();

        LinkedList<Node> queue = new LinkedList<Node>();

        queue.add(root);
        while(!queue.isEmpty()){
            Node t = queue.poll();
            if(t!=null){
                sb.append(String.valueOf(t.item) + ",");
                queue.add(t.left);
                queue.add(t.right);
            }else{ //else, it was null
                sb.append("+,");
            }
        }

        sb.deleteCharAt(sb.length()-1);
        //     System.out.println(sb.toString());
        return sb.toString();

    }

    public void deserialize()
    {
        String deserializedTree = null;
        try {
            File f = new File("abc.txt");
            BufferedReader bw = new BufferedReader(new FileReader(f));

            deserializedTree = bw.readLine();
            root = deserialize(deserializedTree);

            bw.close();
        }
        catch(FileNotFoundException e)  {

        } catch (IOException e) {

        }
    }

    private Node deserialize(String data) {
        if(data==null || data.length()==0)
            return null;

        String[] arr = data.split(",");
        Node root = new Node(Integer.parseInt(arr[0]));


        LinkedList<Node> queue = new LinkedList<Node>();
        queue.add(root);

        int i=1;
        while(!queue.isEmpty()){
            Node t = queue.poll();

            if(t==null)
                continue;

            //if its next level
            if(!arr[i].equals("+")){
                t.left = new Node(Integer.parseInt(arr[i]));
                queue.offer(t.left);

            }else{
                t.left = null;
                queue.offer(null);
            }
            i++;

            if(!arr[i].equals("+")){
                t.right = new Node(Integer.parseInt(arr[i]));
                queue.offer(t.right);

            }else{
                t.right = null;
                queue.offer(null);
            }
            i++;
        }
        return root;
    }

    public static void main(String []args){
        Serialization tree = new Serialization();
    /*creating this BST
                  10
               /     \
              5       15
             /  \    /  \
            3    7  12    18
           /         \
          1           13
         /
        0
            */

        tree.insert(10);
        tree.insert(5);
        tree.insert(15);
        tree.insert(3);
        tree.insert(7);
        tree.insert(12);
        tree.insert(18);
        tree.insert(1);
        tree.insert(13);
        tree.insert(0);

        tree.serialize();
        Serialization tree2 = new Serialization();
        tree2.deserialize();



        System.out.println(tree2.BFS(20));

        //System.out.println(tree.maximumDepth());
        //System.out.println(tree.minimumDepth());
    }
}
