/**
 * Name : Omri Gal Shenhav
 * Contact Info: shenhav.omri@gmail.com
 * id: 318230844
 * This is the main class of the project
 */

import java.io.FileInputStream;
import java.util.ArrayList;

public class SpellChecker {
    public static void main(String[] args)
    {
        String dictionary = "src\\DictionaryFile.txt";
        String basicDict = "\"src\\\\BasicDictionaryFile.txt\""; //option to use basic dictionary file instead
        String input = "src\\InputFile.txt";
        String customInput = "src\\CustomInput.txt"; // option to use custom input file
        spellCheck(dictionary,input);
    }
    public static void spellCheck(String dictInput, String textInput)
    {
        //get dictionary file:
        FileInputStream dictFile = FileHelper.readTextFile(dictInput); //read dictionary file
        //get input file:
        FileInputStream inputFile = FileHelper.readTextFile(textInput); // read input file
        //insert dictionary to hash table:
        MyHashtable<String> dictTable = new MyHashtable<String>(62000);
        FileHelper.insertFileToTable(dictFile,dictTable); // insert dictionary words to hash table
        //insert input text to red black tree:
        RedBlackTree<String> inputTree = new RedBlackTree<String>(); // initialize tree
        FileHelper.insertFileToTree(inputFile,inputTree); // insert input words to tree
        // delete words in dictionary from the input tree
        deleteCorrectWords(inputTree,dictTable);
        //print list of suspicious words:
        //printIncorrectWords(inputTree);
        printSuggestions(inputTree,dictTable,true); // option to see suggestion
    }
    private static void deleteCorrectWords(RedBlackTree<String> tree, MyHashtable<String> dictTable)
    {
        ArrayList<String> wordToDelete = new ArrayList<>();
        fillWordList(tree.getRoot(),wordToDelete,dictTable);
        for(String word : wordToDelete)
            tree.delete(word);
    }
    private static void fillWordList(Node<String> p, ArrayList<String> wordList, MyHashtable<String> dictTable)
    {
        if(p.notLeaf())
        {
            if(dictTable.contains(p.key)) //current not contain word in the dictionary
            {
                wordList.add(p.key);
            }
            fillWordList(p.getLeft(),wordList,dictTable);//left sub tree
            fillWordList(p.getRight(),wordList,dictTable);// right sub tree
        }
    }
    private static String closestWord(String checkWord, MyHashtable<String> dictTable)
    {
        double matchScore, bestMatchScore=Integer.MIN_VALUE;
        String bestMatch="";
        for (String dictWord : dictTable)
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
    private static void printSuggestions(RedBlackTree<String> tree,MyHashtable<String> dictTable, boolean isMultiple)
    {
        ArrayList<String> incorrectWords = tree.getListInOrder();
        if(incorrectWords.size()>0) {
            System.out.println("Suspicious words:");
            String temp;
            for (int i = 0; i < incorrectWords.size(); i++)
            {
                temp=incorrectWords.get(i);
                if(isMultiple) // display top three fix suggestions
                {
                    String[] closestWords = threeClosestWord(temp,dictTable);
                    System.out.println(temp + "? ("+closestWords[0]+","+closestWords[1]+","+closestWords[2]+")");
                }
                else // display one fix suggestion
                {
                    if (i > 0)
                        System.out.print(", ");
                    System.out.print(temp + " (" + closestWord(temp, dictTable) + "?)");
                }
            }
        }
        else
        {
            System.out.println("Spelling seems ok");
        }
        System.out.println();
    }
    private static String[] threeClosestWord(String checkWord, MyHashtable<String> dictTable)
    {
        String[] bestMatches = new String[3];
        double[] scores = new double[3];
        double matchScore=0;
        for (String dictWord : dictTable)
        {
            matchScore=Math.min(matchCalc(checkWord,dictWord),matchCalc(dictWord,checkWord));
            if(matchScore>scores[0])
            {
                scores[0]=matchScore;
                bestMatches[0]=dictWord;
            }
            else if(matchScore>scores[1])
            {
                scores[1]=matchScore;
                bestMatches[1]=dictWord;
            }
            else if(matchScore>scores[2])
            {
                scores[2]=matchScore;
                bestMatches[2]=dictWord;
            }
        }
        return bestMatches;
    }
    private static void printIncorrectWords(RedBlackTree<String> tree)
    {
        ArrayList<String> incorrectWords = tree.getListInOrder();
        if(incorrectWords.size()>0) {
            System.out.println("Suspicious words:");
            String temp;
            for (int i = 0; i < incorrectWords.size(); i++)
            {
                temp=incorrectWords.get(i);
                if(i>0)
                    System.out.print(", ");
                System.out.print(temp);
            }
        }
        else
        {
            System.out.println("Spelling seems ok");
        }
        System.out.println();
    }
    private static double matchCalc(String s1, String s2)
    {
        double matchPoints = 0;
        int temp,diff;
        double matchFactor = 5;
        double diffFactor = 1;
        for (int i = 0; i < s1.length(); i++)
        {
            temp=s1.charAt(i);
            if(s2.indexOf(temp)>=0)
            {
                matchPoints+=matchFactor;
                diff = Math.abs(i-s2.indexOf(temp));
                matchPoints-=diff*diffFactor;
            }
        }
        matchPoints-=Math.abs(s1.length()-s2.length());
        return matchPoints;
    }
}
