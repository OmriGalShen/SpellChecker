/**
 * Name : Omri Gal Shenhav
 * Contact Info: shenhav.omri@gmail.com
 * id: 318230844
 * This is my implementation of very basic chained based hashtable.
 * This is a generic implementation, in which given object used as a value type.
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashtable<T> implements Iterator<T>
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
        HashNode next;
    }

    private HashNode[] table; //The hash table

    private int size; //the of values inserted to the hashtable
    private int currentBucket=-1; //used to keep track of current index of hashtable in order to iterate over the hashtable.

    private HashNode currentNode =null;//used to keep track of current node in order to iterate over the hashtable.

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
     * time complexity : O(1)
     * @param obj
     * @return
     */
    public boolean insert(T obj)
    {

        int index = hashRefine(obj.hashCode());
        HashNode current = table[index];

        while (current != null) {
            // obj is already in set
            if (current.key.equals(obj)) { return false; }
            // otherwise visit next hashNode in the bucket
            current = current.next;
        }
        // no obj found so insert new hashNode
        HashNode hashNode = new HashNode();
        hashNode.key = obj;
        // current HashNode is null if bucket is empty
        // if it is not null it becomes next HashNode
        hashNode.next  = table[index];
        table[index] = hashNode;
        size++;
        return true;
    }
    public boolean remove(Object element) {

        int index = hashRefine(element.hashCode());
        HashNode current = table[index];
        HashNode previous = null;

        while (current != null) {
            // element found so remove it
            if (current.key.equals(element)) {

                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return true;
            }

            previous = current;
            current = current.next;
        }
        // no element found nothing to remove
        return false;
    }
    public boolean contains(Object element) {

        int index = hashRefine(element.hashCode());
        HashNode current = table[index];

        while (current != null) {
            // check if node contains element
            if (current.key.equals(element)) { return true; }
            // otherwise visit next node in the bucket
            current = current.next;
        }
        // no element found
        return false;
    }
    public int getSize() {
        return size;
    }

    @Override
    public boolean hasNext() {
        // currentNode node has next
        if (currentNode != null && currentNode.next != null) { return true; }

        // there are still nodes
        for (int index = currentBucket+1; index < table.length; index++) {
            if (table[index] != null) { return true; }
        }

        // nothing left
        return false;
    }

    public void initIterator()
    {
        this.currentBucket=-1;
        this.currentNode =null;
    }

    @Override
    public T next() {
        // if either the current or next node are null
        if (currentNode == null || currentNode.next == null) {

            // go to next bucket
            currentBucket++;

            // keep going until you find a bucket with a node
            while (currentBucket < table.length &&
                    table[currentBucket] == null) {
                // go to next bucket
                currentBucket++;
            }

            // if bucket array index still in bounds
            // make it the current node
            if (currentBucket < table.length) {
                currentNode = table[currentBucket];
            }
            // otherwise there are no more elements
            else {
                throw new NoSuchElementException();
            }
        }
        // go to the next element in bucket
        else {

            currentNode = currentNode.next;
        }

        // return the element in the current node
        if(currentNode.key !=null)
            return (T)currentNode.key;
        return null;
    }
}
