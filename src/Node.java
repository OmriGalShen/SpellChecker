public class Node<T extends Comparable>
{
    //Node variables
    private Node<T>parent,left,right;
    public enum Color{RED,BLACK}
    private Color color;
    public T key;
    private boolean isNull;

    public Node()
    {
        parent = this;
        left=this;
        right=this;
        color = Color.BLACK;
        this.key = null;
        this.isNull=true;
    }
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
    public Node(Node<T>other){
        this.parent=other.parent;
        this.left=other.left;
        this.right=other.right;
        this.color=other.color;
        this.key=(T)other.key;
        this.isNull=other.isNull;
    }

    public boolean isRed() {
        return color==Color.RED;
    }

    public boolean isBlack() {
        return !isRed();
    }

    public void setRed(){
        if(!this.isNull)
            this.color= Color.RED;
    }

    public void setBlack(){
        this.color=Color.BLACK;
    }

    public boolean isLeaf() {
        return isNull;
    }

    public void makeNull() {
        this.isNull = true;
        this.color=Color.BLACK;
    }

    public Node<T>getParent() {
        return parent;
    }

    public void setParent(Node<T>parent){
        this.parent=parent;
//        if(this.isLeftChild())
//            parent.setLeft(this);
//        else
//            parent.setRight(this);
    }

    public Node<T>getLeft() {
        return left;

    }

    public void setLeft(Node<T>left) {
        this.left = left;
        this.left.parent=this;
    }

    public Node<T>getRight() {
        return right;
    }

    public void setRight(Node<T>right) {
        this.right = right;
        this.right.parent = this;
    }

    public boolean notLeaf(){
        return !this.isNull;
    }

    public void copyColor(Node<T>other)
    {
        this.color=other.color;
    }

    public boolean isRightChild(){
        return this==this.getParent().getRight();
    }
    public boolean isLeftChild(){
        return !isRightChild();
    }
    public String toString()
    {
        if(this.isNull)
            return "leaf";
        if(this.color==Color.RED)
            return "("+this.key+",R)";
        return "("+this.key+",B)";
    }
}
