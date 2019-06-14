/**
 * Name : Omri Gal Shenhav
 * Contact Info: shenhav.omri@gmail.com
 * id: 318230844
 * This is my implementation of a generic red black tree.
 * The keys used are generic, but they have to implement Comparable Interface.
 * (because binary search tree relay on comparable keys)
 * Examples of appropriate objects to use as keys: String,Integer, Double ..
 * This project uses the String implementation relaying on the compareTo
 * method to compare two string in order to place in the tree.
 * This implementation fully relying on the text book implementation
 * while using minor changes.
 */

import java.util.ArrayList;

public class RedBlackTree<T extends Comparable>
{
    //Tree variables
    private Node<T>root; //root node accessible through getters and setters
    //

    /**
     * Constructor for object red black tree.
     * Set root node as null node.
     */
    public RedBlackTree()
    {
        this.root=new Node<T>();
    }

    /**
     * Search for node with given key.
     * First node found will be returned (direct ref)
     * If no node was found return a null node.
     * based on : page 216
     * time complexity : O(lg n)
     * @return a node with given key if found, null node otherwise
     */
    public Node<T> search(T key)
    {
        Node<T> x = this.root;
        while (x.notLeaf() && !x.key.equals(key))
        {
            if(key.compareTo(x.key)<0)
                x=x.getLeft();
            else
                x=x.getRight();
        }
        return x;
    }
    public Node<T>getMinNode()
    {
        return getMinNode(this.root);
    }
    private Node<T>getMinNode(Node<T> x)
    {
        while (x.getLeft().notLeaf())
            x=x.getLeft();
        return x;
    }
    public Node<T>getMaxNode()
    {
        return getMaxNode(this.root);
    }
    private Node<T>getMaxNode(Node<T> x)
    {
        while (x.getRight().notLeaf())
            x=x.getRight();
        return x;
    }
    public Node<T>getSuccessor(Node<T> x)
    {
        if(x.getRight().notLeaf())
            return getMinNode(x.getRight());
        Node<T>y = x.getParent();
        while (y.notLeaf() && x==y.getRight()){
            x=y;
            y=y.getParent();
        }
        return y;
    }
    public Node<T>getPredecessor(Node<T> x)
    {
        if(!x.getLeft().isLeaf())
            return getMaxNode(x.getLeft());
        Node<T>y = x.getParent();
        while (y.notLeaf() && x==y.getLeft()){
            x=y;
            y=y.getParent();
        }
        return y;
    }
    private void leftRotate(Node<T> x)
    {
        Node<T>y = x.getRight();
        x.setRight(y.getLeft());
        if(!y.getLeft().isLeaf()){
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent().isLeaf()){
            this.root=y;
        }
        else if(x==x.getParent().getLeft())
            x.getParent().setLeft(y);
        else
            x.getParent().setRight(y);
        y.setLeft(x);
        x.setParent(y);
    }
    private void rightRotate(Node<T> x)
    {
        Node<T>y = x.getLeft();
        x.setLeft(y.getRight());
        if(y.getRight().isLeaf())
            y.getRight().setParent(x);
        y.setParent(x.getParent());
        if(x.getParent().isLeaf())
            this.root=y;
        else if(x.isRightChild())
            x.getParent().setRight(y);
        else
            x.getParent().setLeft(y);
        y.setRight(x);
        x.setParent(y);
    }
    public void insert(T key)
    {
        insert(new Node<T>(key));
    }
    public void insert(Node<T> z)
    {
        Node<T>y = new Node();
        Node<T>x = this.root;
        while (!x.isLeaf())
        {
            y=x;
            if(z.key.compareTo(x.key)<0)
                x=x.getLeft();
            else
                x=x.getRight();
        }
        z.setParent(y);
        if(y.isLeaf())
            this.root=z;
        else if(z.key.compareTo(y.key)<0)
            y.setLeft(z);
        else
            y.setRight(z);
        z.getLeft().makeNull();
        z.getRight().makeNull();
        z.setRed();
        insertFixer(z);
    }
    private void insertFixer(Node<T> z)
    {
        while (z.getParent().isRed())//overall loop
        {
            if(z.getParent()==z.getParent().getParent().getLeft()) // z in left sub tree case
            {
                Node<T>y =z.getParent().getParent().getRight(); //uncle
                if(y.isRed())//case 1
                {
                    z.getParent().setBlack();
                    y.setBlack();
                    z.getParent().getParent().setRed();
                    z = z.getParent().getParent();
                    continue;
                }
                else if(z==z.getParent().getRight())//case 2
                {
                    z = z.getParent();
                    this.leftRotate(z);
                }
                //case 3
                z.getParent().setBlack();
                z.getParent().getParent().setRed();
                this.rightRotate(z.getParent().getParent());
            }
            else //z in right sub tree case
            {
                Node<T>y =z.getParent().getParent().getLeft();//uncle
                if(y.isRed())//case 1
                {
                    z.getParent().setBlack();
                    y.setBlack();
                    z.getParent().getParent().setRed();
                    z = z.getParent().getParent();
                    continue;
                }
                else if(z==z.getParent().getLeft())//case 2
                {
                    z = z.getParent();
                    this.rightRotate(z);
                }
                //case 3
                z.getParent().setBlack();
                z.getParent().getParent().setRed();
                this.leftRotate(z.getParent().getParent());
            }// end z is in right sub tree case
        }//loop close
        this.root.setBlack();
    }//end method
    public Node<T>delete(T key)
    {
        Node<T>x = search(key);
        if(x.notLeaf())
            return delete(x);
        return x;
    }
    public Node<T>delete(Node<T>z)
    {
        Node<T>y;
        if(z.getLeft().isLeaf() || z.getRight().isLeaf())
            y=z;
        else
            y=getSuccessor(z);
        Node<T>x;
        if(y.getLeft().notLeaf())
            x=y.getLeft();
        else
            x=y.getRight();
        x.setParent(y.getParent());
        if(y.getParent().isLeaf())
            this.root=x;
        else if(y.isLeftChild())
            y.getParent().setLeft(x);
        else
            y.getParent().setRight(x);
        if(y!=z)
            z.key = y.key;
        if(y.isBlack())
            deleteFixer(x);
        return y;
    }
    private void deleteFixer(Node<T> x)
    {
        while (x.notLeaf() && x.isBlack())
        {// main loop
            Node<T>w;
            if(x.isLeftChild()) // x is left child
            {
                w=x.getParent().getRight(); // brother of x
                if(w.isRed())
                {//case 1
                    w.setBlack();
                    x.getParent().setRed();
                    leftRotate(x.getParent());
                    w=x.getParent().getRight();
                }//end case 1
                if(w.getLeft().isBlack() && w.getRight().isBlack())
                {//case 2
                    w.setRed();
                    x=x.getParent();
                    continue;
                }//end case 2
                else if(w.getRight().isBlack())
                {//case 3
                    w.getLeft().setBlack();
                    w.setRed();
                    rightRotate(w);
                    w=x.getParent().getRight();
                }//end case 3
                //case 4
                w.copyColor(x.getParent());
                x.getParent().setBlack();
                w.getRight().setBlack();
                leftRotate(x.getParent());
                x=this.root;
                //end case 4
            }//end x is left child
            else
            {// x is right child
                w=x.getParent().getLeft(); // brother of x
                if(w.isRed())
                {//case 1
                    w.setBlack();
                    x.getParent().setRed();
                    rightRotate(x.getParent());
                    w=x.getParent().getLeft();
                }//end case 1
                if(w.getLeft().isBlack() && w.getRight().isBlack())
                {//case 2
                    w.setRed();
                    x=x.getParent();
                    continue;
                }//end case 2
                else if(w.getLeft().isBlack())
                {//case 3
                    w.getRight().setBlack();
                    w.setRed();
                    leftRotate(w);
                    w=x.getParent().getLeft();
                }//end case 3
                //case 4
                w.copyColor(x.getParent());
                x.getParent().setBlack();
                w.getLeft().setBlack();
                rightRotate(x.getParent());
                x=this.root;
                //end case 4
            }// end x is right child
        }//end main loop
        x.setBlack();
    }
    public int nodeCount()
    {
        return nodeCount(this.root,0);
    }
    private int nodeCount(Node<T> x, int count)
    {
        if(x.isLeaf())
            return count;
        return 1+nodeCount(x.getLeft(),count)+nodeCount(x.getRight(),count);
    }
    public void printInOrder()
    {
        printInOrder(this.root);
    }
    private void printInOrder(Node<T> x)
    {
        if(x.notLeaf())
        {
            printInOrder(x.getLeft());
            System.out.print(x);
            printInOrder(x.getRight());
        }
    }
    public ArrayList<T> getListInOrder()
    {
        ArrayList<T> list = new ArrayList<>(); // initialize list of values to return
        getArrayInOrder(this.root, list); //use recursive function for in order travers
        return list;
    }
    private void getArrayInOrder(Node<T> x, ArrayList<T> list)
    {
        if(x.notLeaf())
        {
            getArrayInOrder(x.getLeft(),list);
            list.add(x.key);
            getArrayInOrder(x.getRight(),list);
        }
    }
    public Node<T>getRoot() {
        return root;
    }

    public void setRoot(Node<T> root) {
        this.root = root;
    }
}

