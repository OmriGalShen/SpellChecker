public class DictTable
{
    private boolean[] dictArray;
    private int size;
    public DictTable(int size)
    {
        this.dictArray=new boolean[size];
        this.size=size;
    }
    public void insert(String word)
    {
        int hashKey = wordTohashKey(word);
        dictArray[hashKey]=true;
    }
    public void delete(String word)
    {
        int hashKey = wordTohashKey(word);
        dictArray[hashKey]=false;
    }
    public boolean isContain(String word)
    {
        int hashKey = wordTohashKey(word);
        return dictArray[hashKey];
    }
    public int wordTohashKey(String word)
    {
        int key = Integer.parseInt(word,26);
        return key%this.size;
    }
}
