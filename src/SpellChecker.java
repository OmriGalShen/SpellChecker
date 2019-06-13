import java.io.FileInputStream;
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
        inputTree.printInOrder();
    }
    public static void deleteCorrectWords(RedBlackTree<String> tree, HashMap<String,Integer> dictMap)
    {
         deleteCorrectWords(tree.getRoot(),tree,dictMap);
    }
    private static void deleteCorrectWords(Node<String> p, RedBlackTree<String> tree, HashMap<String,Integer> dictMap)
    {
        if(p.notLeaf())
        {
            if(dictMap.containsKey(p.key)) //current not contain word in the dictionary
            {
                tree.delete(p); // delete correct word from tree
            }
            deleteCorrectWords(p.getLeft(),tree,dictMap);
            deleteCorrectWords(p.getRight(),tree,dictMap);
        }
    }
}
