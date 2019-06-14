/**
 * Name : Omri Gal Shenhav
 * Contact Info: shenhav.omri@gmail.com
 * id: 318230844
 * This is the node object used to implant a node in a red black tree.
 * It is generic, as in the given object is used as key type.
 * The given object needs to implement Comparable interface because
 * keys need to be comparable when using the red black tree.
 */
public class Node<T extends Comparable>
{
    //Node variables
    private Node<T>parent,left,right; //pointers to other nodes
    public enum Color{RED,BLACK}
    private Color color; // color can be black or red
    public T key;
    private boolean isNull; // for "leafs" nodes this is true, for internal nodes this is false

    /**
     * Constructor for object Node. Construct a null/leaf node
     * with the color black. key value in null.
     */
    public Node()
    {
        parent = this;
        left=this;
        right=this;
        color = Color.BLACK;
        this.key = null;
        this.isNull=true;
    }

    /**
     * Constructor for object Node. Construct a non-leaf/ internal node.
     * default color is black. the pointers (parent,left,right) are set to leaf nodes.
     * @param key key value of the new node
     */
    public Node(T key){
        Node<T>p = new Node<>();
        Node<T>child = new Node<>();
        this.setRight(child);
        this.setLeft(child);
        this.setParent(p);
        this.key=key;
        this.color=Color.BLACK;
        this.isNull=false;
    }

    /**
     * Copy constructor for object Node.
     * Copy properties of other node given as parameter to this Node.
     * @param other Node to copy properties from
     */
    public Node(Node<T>other){
        this.parent=other.parent;
        this.left=other.left;
        this.right=other.right;
        this.color=other.color;
        this.key=(T)other.key;
        this.isNull=other.isNull;
    }

    /**
     * return is Node is red color
     * @return if this node color is red
     */
    public boolean isRed() {
        return color==Color.RED;
    }

    /**
     * return is Node is black color
     * @return true if node color is black, false otherwise
     */
    public boolean isBlack() {
        return !isRed();
    }

    /**
     * Set Node color to red
     */
    public void setRed(){
        if(!this.isNull)
            this.color= Color.RED;
    }
    /*
    * Set node color to black
     */
    public void setBlack(){
        this.color=Color.BLACK;
    }

    /**
     * return if node is leaf/null type node
     * @return true if node is leaf/null and false otherwise
     */
    public boolean isLeaf() {
        return isNull;
    }
    /*
    * set node to be leaf/null node.
    * also changes color to black (as property of leaf node)
     */
    public void makeNull() {
        this.isNull = true;
        this.color=Color.BLACK;
    }

    /**
     * get a direct reference to node parent
     * @return a direct reference to node parent
     */
    public Node<T> getParent() {
        return parent;
    }

    /**
     * set node's parent with direct reference.
     * node: the given parent node doesn't get updates with this node as child
     * @param parent reference to parent node
     */
    public void setParent(Node<T> parent){
        this.parent=parent;
    }

    /**
     * get a direct reference to this node's left child
     * @return a direct reference to this node's left child
     */
    public Node<T>getLeft() {
        return left;
    }

    /**
     * set node's left child with direct reference.
     * node: the given node gets update with this node as parent node
     * @param left reference to left node
     */
    public void setLeft(Node<T> left) {
        this.left = left;
        this.left.parent=this;
    }
    /**
     * get a direct reference to this node's right child
     * @return a direct reference to this node's right child
     */
    public Node<T>getRight() {
        return right;
    }
    /**
     * set node's right child with direct reference.
     * node: the given node gets update with this node as parent node
     * @param right reference to right node
     */
    public void setRight(Node<T> right) {
        this.right = right;
        this.right.parent = this;
    }

    /**
     * return true if node isn't leaf/null, false otherwise
     * @return true if node isn't leaf/null, false otherwise
     */
    public boolean notLeaf(){
        return !this.isNull;
    }

    /**
     * Copy given node color to this node
     * @param other Node to copy color from
     */
    public void copyColor(Node<T> other)
    {
        this.color=other.color;
    }

    /**
     * return true if node is right child to his parent node, false otherwise
     * @return true if node is right child to his parent node, false otherwise
     */
    public boolean isRightChild(){
        return this==this.getParent().getRight();
    }
    /**
     * return true if node is left child to his parent node, false otherwise
     * @return true if node is left child to his parent node, false otherwise
     */
    public boolean isLeftChild(){
        return !isRightChild();
    }

    /**
     * if leaf return "leaf"
     * otherwise return for example (3,R) (3- value R-Red B-black)
     * @return a String representing current node value and color
     */
    public String toString()
    {
        if(this.isNull)
            return "leaf";
        if(this.color==Color.RED)
            return "("+this.key+",R)";
        return "("+this.key+",B)";
    }
}
