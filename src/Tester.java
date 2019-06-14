public class Tester
{
    public static void main(String[] args) {
        RedBlackTree<Integer> t = new RedBlackTree<>();
        for (int i = 0; i < 100000; i++) {
            t.insert(i);
        }
        for (int i = 0; i < 100000; i++) {
            if(t.search(i).key!=i)
                System.out.println("bad");
        }
        Node<Integer> p = t.getMinNode();
        for (int i = 0; i < 500; i++) {
            p = t.getSuccessor(p);
        }
        for (int i = 0; i < 500; i++) {
            p = t.getPredecessor(p);
        }
        if(!p.equals(t.getMinNode()))
            System.out.println("bad");

        p = t.getMaxNode();
        for (int i = 0; i < 500; i++) {
            p = t.getPredecessor(p);
        }
        for (int i = 0; i < 500; i++) {
            p = t.getSuccessor(p);
        }
        if(!p.equals(t.getMaxNode()))
            System.out.println("bad");


    }
}
