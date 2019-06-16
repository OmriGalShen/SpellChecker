/**
 * Task: 15
 * Name : Omri Gal Shenhav
 * Contact Info: shenhav.omri@gmail.com
 * id: 318230844
 * Time: June 2019
 * This is the main class of the project.
 * Run this file to start the project.
 * This project will use my implementation of red black tree (based on the text book)
 * and my implementation of an chain based hash table (see pages 189-192)
 *
 * Let k= number of words in dictionary,
 *     n=number of words in the input file,
 *     l = longest word in both the dictionary file and input file.
 *
 * Our time analysis will be based on the usage of the dictionary file
 * having k=16200 words (more or less). The hashtable will be initialize with size k
 * and so insert,delete and contain will be O(1) as detailed in text book.
 * (with usage of of smaller dictionary file those action will also take O(1).)
 *
 * Important to note I implemented a suggestion of correct words using my own custom
 * score system of closeness of words (as this is not well defined enough).
 */

import java.io.FileInputStream;
import java.util.ArrayList;

public class SpellChecker
{
    /**
     * This is the main function. In here it is define which text files to use
     * as input and dictionary.
     * Based on the given dictionary and input text
     * displays words which are suspicious of being incorrectly typed.
     * the program displays words which are suspicious of being incorrectly typed.
     * @param args default main argument
     */
    public static void main(String[] args)
    {
        String dictionary = "src\\DictionaryFile.txt"; //dictionary with 6133 english words
        String basicDict = "src\\BasicDictionaryFile.txt"; //option to use basic dictionary file instead
        String input = "src\\InputFile.txt"; //input file with text given in the assignment
        String customInput = "src\\CustomInput.txt"; // option to use custom input file
        spellCheck(dictionary,input);
    }

    /**
     * Based on the given dictionary and input text
     * displays words which are suspicious of being incorrectly typed.
     * Time complexity without suggestions: O(n*lgn+k)
     * Time complexity with suggestions: O(n*k*l + k + n*lgn)
     * @param dictInput A string describing relative file location of the dictionary file
     * @param textInput A string describing relative file location of the input file
     */
    public static void spellCheck(String dictInput, String textInput)
    {
        //get dictionary file:
        FileInputStream dictFile = FileHelper.readTextFile(dictInput); //O(1)
        //get input file:
        FileInputStream inputFile = FileHelper.readTextFile(textInput); // O(1)
        //initiate table with capacity close to the number of words in the dictionary
        MyHashtable<String> dictTable = new MyHashtable<String>(62000);
        // insert dictionary words to hash table
        FileHelper.insertFileToTable(dictFile,dictTable);  //O(k)
        //insert input text to red black tree:
        RedBlackTree<String> inputTree = new RedBlackTree<String>(); // initialize tree
        FileHelper.insertFileToTree(inputFile,inputTree); // insert input words to tree O(n*lgn)
        // delete words in dictionary from the input tree
        deleteCorrectWords(inputTree,dictTable); // O(n)
        //list of words left in the tree
        ArrayList<String> incorrectWords = inputTree.getListInOrder();//O(n)
        //print list of suspicious words:
        //printIncorrectWords(incorrectWords); //O(n)
        printSuggestions(incorrectWords,dictTable,true); // option to see suggestion O(n*k*l)
    }

    /**
     * Delete words in the dictionary table from the tree.
     * Time complexity : O(n)
     * (number of words in the input file)
     * @param tree The tree to delete words from
     * @param dictTable The dictionary table
     */
    private static void deleteCorrectWords(RedBlackTree<String> tree, MyHashtable<String> dictTable)
    {
        ArrayList<String> wordToDelete = new ArrayList<>();//initiate list of correct words in the tree
        treeToList(tree.getRoot(),wordToDelete,dictTable);//update list with correct words O(n)
        for(String word : wordToDelete) // O(n)
            tree.delete(word);//delete every incorrect words from the tree
    }

    /**
     * Given reference to a tree root node, empty string list and a dictionary table
     * Add to the list, words in the tree which are also in the dictionary.
     * time complexity : O(n)
     * @param p A reference to the tree root node
     * @param wordList An initialized list of strings
     * @param dictTable The dictionary table
     */
    private static void treeToList(Node<String> p, ArrayList<String> wordList, MyHashtable<String> dictTable)
    {
        if(p.notLeaf())
        {
            if(dictTable.contains(p.key)) //current not contain word in the dictionary
            {
                wordList.add(p.key);
            }
            treeToList(p.getLeft(),wordList,dictTable);//left sub tree
            treeToList(p.getRight(),wordList,dictTable);// right sub tree
        }
    }

    /**
     * Given a list with suspicious words print them
     * Without suggestions.
     * Time complexity : O(n)
     * @param incorrectWords list of incorrect words
     */
    private static void printIncorrectWords(ArrayList<String> incorrectWords)
    {
        if(incorrectWords.size()>0)
        {
            System.out.println("Suspicious words:");
            String temp;
            for (int i = 0; i < incorrectWords.size(); i++) // O(n)
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

    /**
     * Print a list of Suspicious words in the input tree
     * Based on the given boolean, if false Can display one word suggestion
     * Or if true display 3 words suggestions.
     * Time complexity : O(n*k*l)
     * @param incorrectWords list of incorrect words
     * @param dictTable The dictionary table
     * @param isMultiple if true display 3 word suggestions and one word otherwise
     */
    private static void printSuggestions(ArrayList<String> incorrectWords,MyHashtable<String> dictTable, boolean isMultiple)
    {
        if(incorrectWords.size()>0)
        {
            System.out.println("Suspicious words:");
            String temp;
            for (int i = 0; i < incorrectWords.size(); i++) //O(n) loops
            {
                temp=incorrectWords.get(i);
                if(isMultiple) // display top three fix suggestions
                {
                    String[] closestWords = threeClosestWord(temp,dictTable); //O(k*l)
                    System.out.println(temp + "? ("+closestWords[0]+","+closestWords[1]+","+closestWords[2]+")");
                }
                else // display one fix suggestion
                {
                    if (i > 0)
                        System.out.print(", ");
                    System.out.print(temp + " (" + closestWord(temp, dictTable) + "?)"); //O(k*l)
                }
            }
        }
        else
        {
            System.out.println("Spelling seems ok");
        }
        System.out.println();
    }

    /**
     * Given a word and a dictionary table return the closest word
     * in the dictionary based on custom scoring system.
     * This is a specif implementation meaning the closeness of the words
     * is calculated with spastic ideas in mind and it isn't universally determent.
     * time complexity : O(k*l)
     * @param checkWord The word to check
     * @param dictTable The dictionary table
     * @return A close word in the dictionary based on custom rating system
     */
    private static String closestWord(String checkWord, MyHashtable<String> dictTable)
    {
        double matchScore, bestMatchScore=Integer.MIN_VALUE;
        String bestMatch="";
        for (String dictWord : dictTable) // O(k) loops
        {
            matchScore=matchCalc(checkWord,dictWord); // O(l)
            if(matchScore>bestMatchScore) // find match with better score
            {
                bestMatchScore=matchScore;
                bestMatch=dictWord;
            }
        }
        return bestMatch;
    }

    /**
     * Given a word and a dictionary table return the 3 closest words
     * in the dictionary based on custom scoring system.
     * This is a specif implementation meaning the closeness of the words
     * is calculated with spastic ideas in mind and it isn't universally determent.
     * Time complexity : O(k*l)
     * @param checkWord The word to check
     * @param dictTable The dictionary table
     * @return An array size 3 with the 3 suggested words
     */
    private static String[] threeClosestWord(String checkWord, MyHashtable<String> dictTable)
    {
        String[] bestMatches = new String[3];
        double[] scores = new double[3];
        double matchScore;
        //goes throughout all words in dictionary
        for (String dictWord : dictTable) // O(k) loops
        {
            //consider both the "closeness of check word to dict word and dict word to check word
            matchScore=Math.min(matchCalc(checkWord,dictWord),matchCalc(dictWord,checkWord)); //O(l)
            if(matchScore>scores[0])//First highest match score
            {
                scores[0]=matchScore;
                bestMatches[0]=dictWord;
            }
            else if(matchScore>scores[1]) //second highest match score
            {
                scores[1]=matchScore;
                bestMatches[1]=dictWord;
            }
            else if(matchScore>scores[2])//third highest match score
            {
                scores[2]=matchScore;
                bestMatches[2]=dictWord;
            }
        }
        return bestMatches;
    }

    /**
     * Calculate a custom value representing the closeness of two words.
     * Time complexity : O(l)
     * @param s1 The first word to check
     * @param s2 The second word to check
     * @return A custom value representing the closeness of two words.
     */
    private static double matchCalc(String s1, String s2)
    {
        double matchPoints = 0;
        int temp,diff;
        double matchFactor = 5; //tweak this to emphasis second word containing the fist word letters
        double diffFactor = 1; //teak this to emphasis the importance of distance between the same letter in both words
        for (int i = 0; i < s1.length(); i++)
        {
            temp=s1.charAt(i);
            if(s2.indexOf(temp)>=0)//second word contain first word letter
            {
                matchPoints+=matchFactor;
                diff = Math.abs(i-s2.indexOf(temp));//distance between same letter in both words
                matchPoints-=diff*diffFactor;//the distance give lower score
            }
        }
        matchPoints-=Math.abs(s1.length()-s2.length());//diff in lengths lower score
        return matchPoints;
    }
}
