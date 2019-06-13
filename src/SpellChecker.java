import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class SpellChecker {
    public static void main(String[] args)
    {
        //get text files
        //get dictionary file
        FileInputStream dictFile = FileHelper.readTextFile("src\\DictionaryFile.txt"); //read dictionary file
        //dictFile = FileHelper.readTextFile("src\\BasicDictionaryFile.txt"); //you can use this basic dictionary file instead
        //get input file
        FileInputStream inputFile = FileHelper.readTextFile("src\\InputFile.txt"); // read input file
        //inputFile = FileHelper.readTextFile("src\\Test.txt"); // you can use test text instead
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
            String temp;
            for (int i = 0; i < incorrectWords.size(); i++)
            {
                temp=incorrectWords.get(i);
                //option 1: print 1 suggestion
//                if(i>0)
//                    System.out.print(", ");
//                System.out.print(temp + " ("+closestWord(temp,dictMap)+"?)");
                //option 1: print 3 suggestions
                String[] closestWords = threeClosestWord(temp,dictMap);
                System.out.println(temp + "? ("+closestWords[0]+","+closestWords[1]+","+closestWords[2]+")");
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
    private static String[] threeClosestWord(String checkWord, HashMap<String,Integer> dictMap)
    {
        String[] bestMatches = new String[3];
        double[] scores = new double[3];
        double matchScore=0;
        for(String dictWord: dictMap.keySet())
        {
            matchScore=matchCalc(checkWord,dictWord);
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
    private static double matchCalc(String s1,
                          String s2)
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
