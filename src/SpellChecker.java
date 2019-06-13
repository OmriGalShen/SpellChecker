import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class SpellChecker {
    public static void main(String[] args)
    {
        // Open the dictionary file
        FileInputStream dict=null;
        try {
            dict = new FileInputStream("src\\DictionaryFile.txt");
        }
        catch (Exception e){
            System.out.println("problem with opening dictionary file");
        }
        RedBlackTree dictTree = new RedBlackTree();
        if(dict!=null) //successfully dictionary file
            insertDicToTree(dict,dictTree);

    }
    private static void insertDicToTree(FileInputStream dict, RedBlackTree tree)
    {
        // Open the file
        BufferedReader br = new BufferedReader(new InputStreamReader(dict));

        String strLine;
        try {
            //Read File Line By Line
            while ((strLine = br.readLine()) != null) {
                // Print the content on the console
                System.out.println(strLine);
            }

            //Close the input stream
            dict.close();
        }
        catch (Exception e)
        {
            System.out.println("problem with reading dictionary file");
        }
    }
}
