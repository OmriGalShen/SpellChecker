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
    public void leftRotate(Node x)
    {
        Node y = x.getRight();
        x.setRight(y.getLeft());
        if(!y.getLeft().isNull()){
            y.getLeft().setParent(x);
        }
        y.setParent(x.getParent());
        if(x.getParent().isNull()){
            this.root=y;
        }
        else if(x==x.getParent().getLeft())
            x.getParent().setLeft(y);
        else
            x.getParent().setRight(y);
        y.setLeft(x);
        x.setParent(y);
    }
    public void rightRotate(Node x)
    {
        Node y = x.getLeft();
        x.setLeft(y.getLeft());
        if(y.getRight().isNull())
            y.getRight().setParent(x);
        y.setParent(x.getParent());
        if(x.getParent().isNull())
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
        while (!x.isNull())
        {
            y=x;
            if(z.key<x.key)
                x=x.getLeft();
            else
                x=x.getRight();
        }
        z.setParent(y);
        if(y.isNull())
            this.root=z;
        else if(z.key<y.key)
            y.setLeft(z);
        else
            y.setRight(z);
        z.getLeft().makeNull();
        z.getRight().makeNull();
        z.makeRed();
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
                    z.getParent().makeBlack();
                    y.makeBlack();
                    z.getParent().getParent().makeRed();
                    z = z.getParent().getParent();
                    continue;
                }
                else if(z==z.getParent().getRight())//case 2
                {
                    z = z.getParent();
                    this.leftRotate(z);
                }
                //case 3
                z.getParent().makeBlack();
                z.getParent().getParent().makeRed();
                this.rightRotate(z.getParent().getParent());
            }
            else //z in right sub tree case
            {
                Node y =z.getParent().getParent().getLeft();//uncle
                if(y.isRed())//case 1
                {
                    z.getParent().makeBlack();
                    y.makeBlack();
                    z.getParent().getParent().makeRed();
                    z = z.getParent().getParent();
                    continue;
                }
                else if(z==z.getParent().getLeft())//case 2
                {
                    z = z.getParent();
                    this.rightRotate(z);
                }
                //case 3
                z.getParent().makeBlack();
                z.getParent().getParent().makeRed();
                this.leftRotate(z.getParent().getParent());
            }// end z is in right sub tree case
        }//loop close
        this.root.makeBlack();
    }//end method
    public int nodeCount()
    {
        return nodeCount(this.root,0);
    }
    private int nodeCount(Node x, int count)
    {
        if(x.isNull())
            return count;
        return 1+nodeCount(x.getLeft(),count)+nodeCount(x.getRight(),count);
    }
    public void printInOrder()
    {
        printInOrder(this.root);
    }
    private void printInOrder(Node x)
    {
        if(!x.isNull())
        {
            printInOrder(x.getLeft());
            System.out.println(x);
            printInOrder(x.getRight());
        }
    }
}

