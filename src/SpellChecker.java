public class SpellChecker {
    public static void main(String[] args) {
        RedBlackTree t = new RedBlackTree();
        t.insert(1);
        t.insert(2);
        t.insert(3);
//        t.insert(4);
//        System.out.println(t.root);
        t.printInOrder();
        System.out.println(t.nodeCount());
    }
}
