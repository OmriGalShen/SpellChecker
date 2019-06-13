import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SpellChecker {
    public static void main(String[] args)
    {
        //get text files
        FileInputStream dictFile = FileHelper.readTextFile("src\\DictionaryFile.txt"); //read dictionary file
        FileInputStream inputFile = FileHelper.readTextFile("src\\InputFile.txt"); // read input file
        // you can use test text file:
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
        printIncorrectWords(inputTree,dictMap);
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
    private static void printIncorrectWords(RedBlackTree<String> tree,HashMap<String,Integer> dictMap)
    {
        ArrayList<String> incorrectWords = tree.getListInOrder();
        if(incorrectWords.size()>0) {
            System.out.println("Suspicious words:");
            System.out.println();
            String temp;
            for (int i = 0; i < incorrectWords.size(); i++)
            {
                if(i>0)
                    System.out.print(", ");
                temp=incorrectWords.get(i);
                System.out.print(temp + " ("+closestWord(temp,dictMap)+")");
            }
        }
        else
        {
            System.out.println("Spelling seems ok");
        }
        System.out.println();
    }
    private static String closestWord(String checkWord, HashMap<String,Integer> dictMap)
    {
        double matchScore, bestMatchScore=Integer.MIN_VALUE;
        String bestMatch="";
        for(String dictWord: dictMap.keySet())
        {
            matchScore=matchCalc(checkWord,dictWord);
            if(matchScore>bestMatchScore) // find match with better score
            {
                bestMatchScore=matchScore;
                bestMatch=dictWord;
            }
        }
        return bestMatch;
    }
    private static int matchCalc(String s1,
                          String s2)
    {
        int matchPoints = 0;
        char temp;
        int diff=0;
        for (int i = 0; i < s1.length(); i++)
        {
            temp=s1.charAt(i);
            if(s2.indexOf(temp)>=0)
            {
                matchPoints+=3;
                diff = Math.abs(i-s2.indexOf(temp));
                matchPoints-=diff*2;
            }
        }
        matchPoints-=Math.abs(s1.length()-s2.length());
        return matchPoints;
    }
}
