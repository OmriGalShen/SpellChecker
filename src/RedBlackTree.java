public class RedBlackTree
{
    //Tree variables
    public Node root;
    public RedBlackTree()
    {
        this.root=Node.nil;
    }
    public RedBlackTree(Node root)
    {
        this.root=root;
    }
    public void leftRotate(Node x)
    {
        Node y=x.left;
        x.right=y.left;
        if(y.left!=null){
            y.parent.left=x;
        }
        y.parent=x.parent;
        if(x.parent==null){
            this.root=y;
        }
        else if(x==x.parent.left)
            x.parent.left=y;
        else
            x.parent.right=y;
        y.left=x;
        x.parent=y;
    }
    public void rightRotate(Node x)
    {
        Node y = x.left;
        x.left=y.right;
        if(y.right!=null)
            y.parent.right=x;
        y.parent=x.parent;
        if(x.parent==null)
            this.root=y;
        else if(x==x.parent.right)
            x.parent.right=y;
        else
            x.parent.left=y;
        x.right=x;
        x.parent=y;
    }
    public void insert(int key)
    {
        insert(new Node(key));
    }
    public void insert(Node z)
    {
        Node y = Node.nil;
        Node x = this.root;
        while (x!=Node.nil)
        {
            y=x;
            if(z.key<x.key)
                x=x.left;
            else
                x=x.right;
        }
        z.parent=y;
        if(y==Node.nil)
            this.root=z;
        else if(z.key<y.key)
            y.left=z;
        else
            y.right=z;
        z.left=Node.nil;
        z.right=Node.nil;
        z.color = Node.Color.RED;
        insertFixup(z);
    }
    private void insertFixup(Node z)
    {
        while (z.parent.color== Node.Color.RED)//overall loop
        {
            if(z.parent==z.parent.parent.left) // z in left sub tree case
            {
                Node y =z.parent.parent.right; //uncle
                if(y.color== Node.Color.RED)//case 1
                {
                    z.parent.color= Node.Color.BLACK;
                    y.color= Node.Color.BLACK;
                    z.parent.parent.color= Node.Color.RED;
                    z = z.parent.parent;
                }
                else if(z==z.parent.right)//case 2
                {
                    z = z.parent;
                    this.leftRotate(z);
                }
                //case 3
                z.parent.color= Node.Color.BLACK;
                z.parent.parent.color= Node.Color.RED;
                this.rightRotate(z.parent.parent);
            }
            else //z in right sub tree case
            {
                Node y =z.parent.parent.left;//uncle
                if(y.color== Node.Color.RED)//case 1
                {
                    z.parent.color= Node.Color.BLACK;
                    y.color= Node.Color.BLACK;
                    z.parent.parent.color= Node.Color.RED;
                    z = z.parent.parent;
                }
                else if(z==z.parent.left)//case 2
                {
                    z = z.parent;
                    this.rightRotate(z);
                }
                //case 3
                z.parent.color= Node.Color.BLACK;
                z.parent.parent.color= Node.Color.RED;
                this.leftRotate(z.parent.parent);
            }// end z is in right sub tree case
        }//loop close
        this.root.color=Node.Color.BLACK;
    }//end method
    public int nodeCount()
    {
        return nodeCount(this.root,0);
    }
    private int nodeCount(Node x, int count)
    {
        if(x==Node.nil)
            return count;
        return 1+nodeCount(x.left,count)+nodeCount(x.right,count);
    }
    public void printInOrder()
    {
        printInOrder(this.root);
    }
    private void printInOrder(Node x)
    {
        if(x!=Node.nil)
        {
            printInOrder(x.left);
            System.out.println(x);
            printInOrder(x.right);
        }
    }
}

