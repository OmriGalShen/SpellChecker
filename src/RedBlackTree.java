public class RedBlackTree
{
    //Tree variables
    public Node root;
    //
    public RedBlackTree()
    {
        this.root=new Node();
    }
    public RedBlackTree(Node root)
    {
        this.root=root;
    }
    public Node search(int key)
    {
        return search(this.root,key);
    }
    private Node search(Node x, int key)
    {
        if(x.isLeaf() || x.key==key)
            return x;
        if(key<x.key)
            return  search(x.getLeft(),key);
        return search(x.getRight(),key);
    }
    public Node getMinNode()
    {
        return getMinNode(this.root);
    }
    private Node getMinNode(Node x)
    {
        while (x.getLeft().notLeaf())
            x=x.getLeft();
        return x;
    }
    public Node getMaxNode()
    {
        return getMaxNode(this.root);
    }
    private Node getMaxNode(Node x)
    {
        while (x.getRight().notLeaf())
            x=x.getRight();
        return x;
    }
    public Node getSuccessor(Node x)
    {
        if(x.getRight().notLeaf())
            return getMinNode(x.getRight());
        Node y = x.getParent();
        while (y.notLeaf() && x==y.getRight()){
            x=y;
            y=y.getParent();
        }
        return y;
    }
    public Node getPredecessor(Node x)
    {
        if(!x.getLeft().isLeaf())
            return getMaxNode(x.getLeft());
        Node y = x.getParent();
        while (y.notLeaf() && x==y.getLeft()){
            x=y;
            y=y.getParent();
        }
        return y;
    }
    private void leftRotate(Node x)
    {
        Node y = x.getRight();
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
    private void rightRotate(Node x)
    {
        Node y = x.getLeft();
        x.setLeft(y.getLeft());
        if(y.getRight().isLeaf())
            y.getRight().setParent(x);
        y.setParent(x.getParent());
        if(x.getParent().isLeaf())
            this.root=y;
        else if(x==x.getParent().getRight())
            x.getParent().setRight(y);
        else
            x.getParent().setLeft(y);
        y.setRight(x);
        x.setParent(y);
    }
    public void insert(int key)
    {
        insert(new Node(key));
    }
    public void insert(Node z)
    {
        Node y = new Node();
        Node x = this.root;
        while (!x.isLeaf())
        {
            y=x;
            if(z.key<x.key)
                x=x.getLeft();
            else
                x=x.getRight();
        }
        z.setParent(y);
        if(y.isLeaf())
            this.root=z;
        else if(z.key<y.key)
            y.setLeft(z);
        else
            y.setRight(z);
        z.getLeft().makeNull();
        z.getRight().makeNull();
        z.setRed();
        insertFixup(z);
    }
    private void insertFixup(Node z)
    {
        while (z.getParent().isRed())//overall loop
        {
            if(z.getParent()==z.getParent().getParent().getLeft()) // z in left sub tree case
            {
                Node y =z.getParent().getParent().getRight(); //uncle
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
                Node y =z.getParent().getParent().getLeft();//uncle
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
    public Node delete(int key)
    {
        Node x = search(key);
        if(x.notLeaf())
            return delete(x);
        return x;
    }
    public Node delete(Node z)
    {
        Node y;
        if(z.getLeft().isLeaf() || z.getRight().isLeaf())
            y=z;
        else
            y=getSuccessor(z);
        Node x;
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
            deleteFixup(x);
        return y;
    }
    private void deleteFixup(Node x)
    {
        while (x.notLeaf() && x.isBlack())
        {// main loop
            Node w;
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
    private int nodeCount(Node x, int count)
    {
        if(x.isLeaf())
            return count;
        return 1+nodeCount(x.getLeft(),count)+nodeCount(x.getRight(),count);
    }
    public void printInOrder()
    {
        printInOrder(this.root);
    }
    private void printInOrder(Node x)
    {
        if(!x.isLeaf())
        {
            printInOrder(x.getLeft());
            System.out.println(x);
            printInOrder(x.getRight());
        }
    }
}

