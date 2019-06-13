import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class SpellChecker {
    public static void main(String[] args)
    {
        //get text files
        FileInputStream dictFile = FileHelper.readTextFile("src\\DictionaryFile.txt"); //read dictionary file
        FileInputStream inputFile = FileHelper.readTextFile("src\\InputFile.txt"); // read input file
//        FileInputStream inputFile = FileHelper.readTextFile("src\\Test.txt"); // read input file
        //insert dictionary to hash table
        HashMap<String,Integer> dictMap = new HashMap<String, Integer>();// initialize hash table
        FileHelper.insertFileToMap(dictFile,dictMap); // insert dictionary words to hash table
        ////insert input text to red black tree
        RedBlackTree<String> inputTree = new RedBlackTree<String>(); // initialize tree
        FileHelper.insertFileToTree(inputFile,inputTree); // insert input words to tree
        // delete words in dictionary from the input tree
        deleteCorrectWords(inputTree,dictMap);
        //print list of suspicious words
        printIncorrectWords(inputTree);
    }
    private static void deleteCorrectWords(RedBlackTree<String> tree, HashMap<String,Integer> dictMap)
    {
         ArrayList<String> wordToDelete = new ArrayList<>();
        fillWordList(tree.getRoot(),wordToDelete,dictMap);
        for(String word : wordToDelete)
            tree.delete(word);
    }
    private static void fillWordList(Node<String> p, ArrayList<String> wordList, HashMap<String,Integer> dictMap)
    {
        if(p.notLeaf())
        {
            if(dictMap.containsKey(p.key)) //current not contain word in the dictionary
            {
                wordList.add(p.key);
            }
            fillWordList(p.getLeft(),wordList,dictMap);//left sub tree
            fillWordList(p.getRight(),wordList,dictMap);// right sub tree
        }
    }
    private static void printIncorrectWords(RedBlackTree<String> tree)
    {
        ArrayList<String> incorrectWords = tree.getListInOrder();
        if(incorrectWords.size()>0) {
            System.out.println("Suspicious words:");
            System.out.println();
            for (int i = 0; i < incorrectWords.size() - 1; i++) {
                System.out.print(incorrectWords.get(i) + ", ");
            }
            System.out.print(incorrectWords.get(incorrectWords.size() - 1));
        }
        else
        {
            System.out.println("Spelling seems ok");
        }
        System.out.println();
    }
}
