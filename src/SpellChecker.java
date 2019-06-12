public class SpellChecker {
    public static void main(String[] args) {
        RedBlackTree t = new RedBlackTree();
        t.insert(1);
        t.insert(3);
        t.insert(5);
        t.insert(2);
        t.insert(7);
        t.insert(10);
        t.insert(2);
//        System.out.println(t.root);
        t.printInOrder();
        t.printTree();
        System.out.println("nodes: "+t.nodeCount());
    }
}
