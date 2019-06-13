import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SpellChecker {
    public static void main(String[] args)
    {
        //get text files
        FileInputStream dictFile = FileHelper.readTextFile("src\\DictionaryFile.txt"); //read dictionary file
        FileInputStream inputFile = FileHelper.readTextFile("src\\InputFile.txt"); // read input file
        //
        //insert dictionary to hash table
        HashMap<String,Integer> dictMap = new HashMap<String, Integer>();// initialize hash table
        FileHelper.insertFileToMap(dictFile,dictMap); // insert dictionary words to hash table
        //
        ////insert input text to red black tree
        RedBlackTree<String> inputTree = new RedBlackTree<String>(); // initialize tree
        FileHelper.insertFileToTree(inputFile,inputTree); // insert input words to tree
        //
        deleteCorrectWords(inputTree,dictMap);
        printTree(inputTree);
    }
    public static void deleteCorrectWords(RedBlackTree<String> tree, HashMap<String,Integer> dictMap)
    {
         deleteCorrectWords(tree.getRoot(),tree,dictMap);//call recursive function
    }
    private static void deleteCorrectWords(Node<String> p, RedBlackTree<String> tree, HashMap<String,Integer> dictMap)
    {
        if(p.notLeaf())
        {
            if(dictMap.containsKey(p.key)) //current not contain word in the dictionary
            {
                tree.delete(p); // delete correct word from tree
            }
            deleteCorrectWords(p.getLeft(),tree,dictMap);//left sub tree
            deleteCorrectWords(p.getRight(),tree,dictMap);// right sub tree
        }
    }
    private static void printTree(RedBlackTree<String> tree)
    {
        ArrayList<String> incorrectWords = tree.getListInOrder();
        System.out.println();
        System.out.println("Suspicious words:");
        for (int i = 0; i <incorrectWords.size()-1 ; i++) {
            System.out.print(incorrectWords.get(i)+", ");
        }
        System.out.print(incorrectWords.get(incorrectWords.size()-1));
        System.out.println();

    }
}
