public class Node
{
    //Node variables
    public Node parent,left,right;
    public enum Color{RED,BLACK}
    public Color color;
    public int key;
    public static Node nil = initNil();
    //
    public static Node initNil()
    {
        Node nil = new Node();
        nil.parent=nil;
        nil.left=nil;
        nil.right=nil;
        nil.color=Color.BLACK;
        return nil;
    }
    public Node()
    {
        parent = nil;
        left=nil;
        right=nil;
        color = Color.RED;
        this.key = -1;
    }
    public Node(int key){
        this();
        this.key=key;
    }
    public Node(Node other){
        this.parent=other.parent;
        this.left=other.left;
        this.right=other.right;
        this.color=other.color;
        this.key=other.key;
    }
    public String toString()
    {
        if(this == nil)
            return "nil";
        if(this.color==Color.RED)
            return "("+this.key+",R)";
        return "("+this.key+",B)";
    }
}
