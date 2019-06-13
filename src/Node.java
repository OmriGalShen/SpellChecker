public class Node
{
    //Node variables
    private Node parent,left,right;
    public enum Color{RED,BLACK}
    private Color color;
    public String key;
    private boolean isNull;

    public Node()
    {
        parent = this;
        left=this;
        right=this;
        color = Color.BLACK;
        this.key = "";
        this.isNull=true;
    }
    public Node(String key){
        Node p = new Node();
        Node child = new Node();
        this.setRight(child);
        this.setLeft(child);
        this.setParent(p);
        this.key=key;
        this.color=Color.BLACK;
        this.isNull=false;
    }
    public Node(Node other){
        this.parent=other.parent;
        this.left=other.left;
        this.right=other.right;
        this.color=other.color;
        this.key=other.key;
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

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent){
        this.parent=parent;
//        if(this.isLeftChild())
//            parent.setLeft(this);
//        else
//            parent.setRight(this);
    }

    public Node getLeft() {
        return left;

    }

    public void setLeft(Node left) {
        this.left = left;
        this.left.parent=this;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
        this.right.parent = this;
    }

    public boolean notLeaf(){
        return !this.isNull;
    }

    public void copyColor(Node other)
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
