import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SpellChecker {
    public static void main(String[] args)
    {
        FileInputStream dict = readTextFile("src\\DictionaryFile.txt"); //read dictionary file
        FileInputStream input = readTextFile("src\\InputFile.txt");
//        FileInputStream input = readTextFile("src\\Test.txt");
        RedBlackTree<String> inputTree = new RedBlackTree<String>(); // initialize tree
        insertFileToTree(input,inputTree); // insert input words to tree

        System.out.println(inputTree.nodeCount());

    }
    private static FileInputStream readTextFile(String loc)
    {
        // Open the dictionary file
        FileInputStream file=null;
        try {
            file = new FileInputStream(loc);
        }
        catch (Exception e){
            System.out.println("problem with opening text file");
        }
        return file;
    }
    private static void insertFileToTree(FileInputStream file, RedBlackTree tree)
    {
        if(file==null)
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
            Scanner input=new Scanner(file);
//            input.useDelimiter(" +"); //delimitor is one or more spaces
//            input.useDelimiter(); //delimitor is one or more spaces
            String temp;
            while(input.hasNext()){
                temp=input.next();
                if(temp.length()>0)
                    tree.insert(temp);
//                System.out.println(input.next());
            }

            //Close the input stream
            file.close();
        }
        catch (Exception e)
        {
            System.out.println("problem with reading file");
        }
    }
    private static String processWord(String word)
    {
        return word.trim().toLowerCase();
    }
}
