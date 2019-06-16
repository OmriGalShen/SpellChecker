/**
 * Name : Omri Gal Shenhav
 * Contact Info: shenhav.omri@gmail.com
 * id: 318230844
 * This is class contain static function which help with
 * working with the text files.
 */

import java.io.FileInputStream;
import java.util.Scanner;

public class FileHelper
{
    /**
     * Open a text file using given relative loc.
     * @param loc string describing relative file location
     * (example "src\\DictionaryFile.txt")
     * time complexity : O(1)
     * @return The text file
     */
    public static FileInputStream readTextFile(String loc)
    {
        FileInputStream file=null;
        try {
            file = new FileInputStream(loc);
        }
        catch (Exception e){
            System.out.println("problem with opening text file");
        }
        return file;
    }

    /**
     * Given text file, word by word (Separated by spaces)
     * insert given string words to MyHashtable.
     * @param file Text file to read words from
     * time complexity : O(k), where k is the number of words in text file
     * @param table The table to insert words to.
     */
    public static void insertFileToTable(FileInputStream file, MyHashtable<String> table)
    {
        if(file==null) //problem with file
            return;
        try
        {
            //Read file Word by Word
            Scanner input=new Scanner(file);//help cut file to words
            String temp;
            //loop over the words
            while(input.hasNext())
            {
                temp=input.next(); // current word in file
                temp=fixWord(temp,false); //trim spaces and make lower case
                if(temp.length()>0)
                {
                    table.insert(temp);
                }
            }

            //Close the input stream
            file.close();
        }
        catch (Exception e)
        {
            System.out.println("problem with reading file");
        }
    }

    /**
     * Given text file, word by word (Separated by spaces)
     * insert given string words to red black tree.
     * time complexity : O(k), where k is the number of words in text file
     * @param file Text file to read words from.
     * @param tree The tree to insert words to.
     */
    public static void insertFileToTree(FileInputStream file, RedBlackTree<String> tree)
    {
        if(file==null)//problem with file
            return;
        try
        {
            //Read file Word by Word
            Scanner input=new Scanner(file);//help cut file to words
            String temp;
            while(input.hasNext())
            {
                temp=input.next();// current word in file
                temp=fixWord(temp,false); //trim spaces and make lower case
                if(temp.length()>0) //word is valid
                    tree.insert(temp); //insert word in tree
            }

            //Close the input stream
            file.close();
        }
        catch (Exception e)
        {
            System.out.println("problem with reading file");
        }
    }

    /**
     * Make string lower case and without spaces.
     * If given true also remove special characters.
     * @param str String to "fix"
     * @param fixSpecialChars true for removing special character and false otherwise
     * @return the fixed String
     */
    private static String fixWord(String str, Boolean fixSpecialChars)
    {
        String result=str;
        if(fixSpecialChars)
             result = str.replaceAll("[-+.^:,\")(]","");
        result = result.toLowerCase().trim();
        return result;
    }
}
