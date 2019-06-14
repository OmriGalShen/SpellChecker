public class Test
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
    }
}
