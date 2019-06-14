import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyHashtable<T> implements Iterator<T>
{
    private HashNode[] buckets;

    private int size;

    private int currentBucket=-1;
    private int previousBucket=-1;
    private HashNode currentNode =null;
    private HashNode previousNode =null;

    private static class HashNode<T> {
        T key;
        HashNode next;
    }

    public MyHashtable(int capacity) {

        buckets = new HashNode[capacity];
        size = 0;
    }
    private int hashFunction(int hashCode) {

        int index = hashCode;
        if (index < 0) { index = -index; }
        return index % buckets.length;
    }
    public boolean add(Object element) {

        int index = hashFunction(element.hashCode());
        HashNode current = buckets[index];

        while (current != null) {
            // element is already in set
            if (current.key.equals(element)) { return false; }
            // otherwise visit next hashNode in the bucket
            current = current.next;
        }
        // no element found so add new hashNode
        HashNode hashNode = new HashNode();
        hashNode.key = element;
        // current HashNode is null if bucket is empty
        // if it is not null it becomes next HashNode
        hashNode.next  = buckets[index];
        buckets[index] = hashNode;
        size++;
        return true;
    }
    public boolean remove(Object element) {

        int index = hashFunction(element.hashCode());
        HashNode current = buckets[index];
        HashNode previous = null;

        while (current != null) {
            // element found so remove it
            if (current.key.equals(element)) {

                if (previous == null) {
                    buckets[index] = current.next;
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

        int index = hashFunction(element.hashCode());
        HashNode current = buckets[index];

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
        for (int index = currentBucket+1; index < buckets.length; index++) {
            if (buckets[index] != null) { return true; }
        }

        // nothing left
        return false;
    }

    public void initIterator()
    {
        this.currentBucket=-1;
        this.previousBucket=-1;
        this.currentNode =null;
        this.previousNode =null;
    }

    @Override
    public T next() {
        previousNode = currentNode;
        previousBucket = currentBucket;

        // if either the current or next node are null
        if (currentNode == null || currentNode.next == null) {

            // go to next bucket
            currentBucket++;

            // keep going until you find a bucket with a node
            while (currentBucket < buckets.length &&
                    buckets[currentBucket] == null) {
                // go to next bucket
                currentBucket++;
            }

            // if bucket array index still in bounds
            // make it the current node
            if (currentBucket < buckets.length) {
                currentNode = buckets[currentBucket];
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
