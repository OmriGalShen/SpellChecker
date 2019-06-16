/**
 * Name : Omri Gal Shenhav
 * Contact Info: shenhav.omri@gmail.com
 * id: 318230844
 * This is my implementation of very basic chained based hashtable.
 * This is a generic implementation, in which given object used as a value type.
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

    private int size; //the of values inserted to the hashtable


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
        int index = Math.abs(hashCode);
        return index % table.length;
    }

    /**
     * Insert an object to the the hashtable
     * Best time complexity: O(1)
     * Average time complexity : O(1)
     * Worst time complexity : O(n), where n is number of objects in the table
     * @param obj Reference to the object to insert to the table
     * @return True if object inserted, false otherwise (if object already in table)
     */
    public boolean insert(T obj)
    {
        // using the object hashCode refine a index in table:
        int index = hashRefine(obj.hashCode());
        HashNode current = table[index];

        while (current != null)
        {
            if (current.key.equals(obj))  // obj is already in table
            {
                return false;
            }
            // otherwise visit next hashNode in the table cell
            current = current.next;
        }
        // no obj found so insert new hashNode
        HashNode hashNode = new HashNode();
        hashNode.key = obj;
        // set the new node as head of the list
        hashNode.next  = table[index];
        table[index] = hashNode;
        size++;// new object was added to the table
        return true;
    }

    /**
     * Remove an object from the table
     * Best time complexity: O(1)
     * Average time complexity : O(1)
     * Worst time complexity : O(n), where n is number of objects in the table
     * @param obj The object to remove from the table
     * @return True if object removed from table, false otherwise (was't found).
     */
    public boolean remove(T obj)
    {
        // using the object hashCode refine a index in table:
        int index = hashRefine(obj.hashCode());
        HashNode current = table[index]; //ref to the head of the list
        HashNode previous = null;

        while (current != null)
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
     * Best time complexity: O(1)
     * Average time complexity : O(1)
     * Worst time complexity : O(n), where n is number of objects in the table
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
