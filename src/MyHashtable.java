/**
 * Name : Omri Gal Shenhav
 * Contact Info: shenhav.omri@gmail.com
 * id: 318230844
 *
 * This is my implementation of very basic chained based hashtable.
 * This is a generic implementation, in which given object used as a value type.
 * This is based on the the text book (pages 189-192)
 *
 * Let n be the number of objects in the table.
 * let m be the capacity of the table (length of the table initialize in the constructor)
 * Let a be the load factor (n/m)
 *
 * This project uses specifically String objects as values.
 * We will be using the hashCode of the String class in Java,
 * This function is very well implemented as so we will be make the assumption
 * at least for this project sake that the hash function is simple and uniform.
 *
 * By using the text book we can now say the complexity of failed search is O(1+a),
 * because we know the size of the dictionary in advance.
 * we will set a (load factor) to be 1 so we will get O(1).
 *
 * Note: we will take the String hashCode and refined it to serve as a index in the table.
 */

import java.util.Iterator;

public class MyHashtable<T> implements Iterable<T>
{
    /**
     * Node in the hash table, practically a basic linked list.
     * Hold a reference to a key value and a reference
     * to the next node.
     * @param <T> The type of the key value
     */
    private static class HashNode<T>
    {
        T key;
        HashNode<T> next;
    }

    private HashNode<T>[] table; //The hash table

    private int size; //number of objects in the table


    /**
     * The constructor for MyHashtable object.
     * Initiate a hashtable with given capacity.
     * time complexity : O(1)
     * @param capacity The size of the hashtable
     */
    public MyHashtable(int capacity)
    {
        this.size = 0; //initiate size
        table = new HashNode[capacity]; // initiate the array
    }

    /**
     * Getter for the number of object in the table
     * @return The number of object in the table
     */
    public int getSize()
    {
        return size;
    }

    /**
     * Refine given hashcode to an appropriate one,
     * in order to use as an index in the table.
     * time complexity : O(1)
     * @param hashCode hashcode to be refined
     * @return an a refined hashcode to be used as an index in the table.
     */
    private int hashRefine(int hashCode)
    {
        int index = Math.abs(hashCode);//O(1)
        return index % table.length;//O(1)
    }

    /**
     * Insert an object to the the hashtable
     * Time complexity : O(1)
     * note : Assuming the hashCode of the object is "good"
     * @param obj Reference to the object to insert to the table
     */
    public void insert(T obj)
    {
        // using the object hashCode refine a index in table:
        int index = hashRefine(obj.hashCode()); //O(1)
        // initiate new node with obj as value
        HashNode<T> nodeToInsert = new HashNode<T>();//O(1)
        nodeToInsert.key = obj;//O(1)
        // set the new node as head of the list
        nodeToInsert.next  = table[index];//O(1)
        table[index] = nodeToInsert;//O(1)
        size++;// new object was added to the table
    }

    /**
     * Remove an object from the table
     * Time complexity : same complexity of failed search O(1+a) (See page 191)
     * Note : Assuming the hashCode of the object is "good"
     * @param obj The object to remove from the table
     * @return True if object removed from table, false otherwise (was't found).
     */
    public boolean remove(T obj)
    {
        // using the object hashCode refine a index in table:
        int index = hashRefine(obj.hashCode());
        HashNode current = table[index]; //ref to the head of the list
        HashNode previous = null;

        while (current != null)  //same complexity of failed search O(1+a) (See page 191)
        {
            if (current.key.equals(obj)) // obj found
            {
                //using previous ref remove obj from list
                if (previous == null)
                {
                    table[index] = current.next; // set new list head
                }
                else {
                    previous.next = current.next;//"pass over" the object
                }
                size--;// a object was remove from the table
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false; // obj not found, nothing to remove
    }

    /**
     * Return true if object is in the table, false otherwise.
     * Time complexity : same complexity of failed search O(1+a) (See page 191)
     * note : Assuming the hashCode of the object is "good"
     * @param object The object to check if in the table
     * @return True if object is in the table, false otherwise.
     */
    public boolean contains(T object)
    {
        // using the object hashCode refine a index in table:
        int index = hashRefine(object.hashCode());
        HashNode current = table[index]; //ref to the head of the list

        while (current != null)
        {
            if (current.key.equals(object)) // check if node contains object
            {
                return true;
            }
            current = current.next; // otherwise visit next node in the list
        }

        return false; // object not found
    }

    /**
     * This let us iterate over the table's objects
     * @return iterator used to iterate over the table's objects
     */
    public Iterator<T> iterator() {
        return new MyHashTableIterator();
    }
    private class MyHashTableIterator implements Iterator<T>
    {
        private int currentElement =-1; //used to keep track of current index of hashtable in order to iterate over the table.
        private HashNode<T> currentNode =null;//used to keep track of current node in order to iterate over the table element.
        /**
         * Used to iterate over the table's objects.
         * Return true if there is more object in the table to iterate over,
         * return false otherwise.
         * To initiate iterator use initIterator()
         * To get the next object use next()
         * Best time complexity: O(1)
         * Worst time complexity : O(k) where k is the length of the table.
         * @return true if there is more object in the table to iterate over, and false otherwise.
         */
        @Override
        public boolean hasNext()
        {
            // currentNode node has next
            if (currentNode != null && currentNode.next != null) { return true; }
            //check if there is a table element after current element, with nodes
            for (int i = currentElement +1; i < table.length; i++)
            {
                if (table[i] != null)//found element with nodes
                    return true;
            }
            return false; //no next object to iterate over
        }

        /**
         * Used to iterate over the table's objects.
         * Return the next object in the iteration.
         * If no such object exist return null.
         * To initiate iterator use initIterator()
         * To check if there is a next object use hasNext()
         * Best time complexity: O(1)
         * Worst time complexity : O(k) where k is the length of the table.
         * @return The next object in the iteration
         */
        @Override
        public T next()
        {
            // if either the current or next node are null
            if (currentNode == null || currentNode.next == null)
            {
                currentElement++;// go to next element in the table

                // looping until element with object found
                while (currentElement < table.length &&
                        table[currentElement] == null)
                {
                    currentElement++; // go to next element in the table
                }
                if (currentElement < table.length)
                {
                    currentNode = table[currentElement];// update current node
                }
                else // no object has been found
                {
                    return null;
                }
            }
            else //current element in table is ref an object
            {
                currentNode = currentNode.next;
            }
            if(currentNode.key !=null)// the new current element ref an obj
            {
                return currentNode.key;
            }
            return null;
        }
    }
}
