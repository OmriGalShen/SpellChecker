/**
 * Name : Omri Gal Shenhav
 * Contact Info: shenhav.omri@gmail.com
 * id: 318230844
 * This is class contain static function which help with
 * working with the text files.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FileHelper
{
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
    public static void insertFileToTable(FileInputStream file, MyHashtable dictTable)
    {
        if(file==null) //problem with file
            return;
        // Open the file
        BufferedReader br = new BufferedReader(new InputStreamReader(file));

        String strLine;
        try {
            /*Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                strLine = processWord(strLine);
                tree.insert(strLine);
            }*/
            //Read file Word by Word
            Scanner input=new Scanner(file);//help cut file to words
            String temp;
            //loop over the words
            while(input.hasNext())
            {
                temp=input.next(); // current word in file
                temp=fixWord(temp); //trim spaces and make lower case
                if(temp.length()>0)
                {
                    dictTable.insert(temp);
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
    public static void insertFileToTree(FileInputStream file, RedBlackTree<String> tree)
    {
        if(file==null)//problem with file
            return;
        // Open the file
        BufferedReader br = new BufferedReader(new InputStreamReader(file));

        String strLine;
        try {
            /*Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                strLine = processWord(strLine);
                tree.insert(strLine);
            }*/
            //Read file Word by Word
            Scanner input=new Scanner(file);//help cut file to words
            String temp;
            while(input.hasNext())
            {
                temp=input.next();// current word in file
                temp=fixWord(temp); //trim spaces and make lower case
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
     * Make a fixed string without special characters and spaces
     * and returns it
     * @param str String to fix
     * @return the given string without special characters and spaces
     */
    private static String fixWord(String str)
    {
        String result = str.replaceAll("[-+.^:,\")(]","");
        result = result.toLowerCase().trim();
        return result;
    }
}
