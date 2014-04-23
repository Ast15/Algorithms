/* Problem from: http://oj.leetcode.com/problems/lru-cache/
Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and set.
get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.
set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate the least recently used item before inserting a new item.

-Idea: we use a HashMap for constant time lookup and a doubly linkedlist for easy removal of the located item. 
-Complexity: both get(int key) and set(int key, int value) are O(1).

@author: Tien D. Le
*/

public class LRUCache {
    int capacity;
    HashMap<Integer,Node>map;
    Node head,tail;//doubly linkedlist
    int size;
    
    public LRUCache(int capacity) {
        this.capacity=capacity;
        map=new HashMap<Integer,Node>();
        head=null;
        tail=null;
        size=0;
    }
    
    public int get(int key) {
        Node node=map.get(key);
        if(node==null)return -1;
        
        Node preN=node.pre;
        Node nextN=node.next;
        
        if(preN==null){//node is the head
            return node.value;//we are done
        }
        
        if(nextN==null){//node is the tail. We need to move the node to front of the list, and update the tail
            preN.next=null;
            tail=preN;
            
            moveFront(node);
            return node.value;
        }
        
        //otherwise, node is an intermediate node, we just need to move the node to front of the list.
        preN.next=nextN;
        nextN.pre=preN;
        
        moveFront(node);
        return node.value;
    }
    
    public void set(int key, int value) {
        Node node=map.get(key);
        if(node!=null){//node already in the map
            node.value=value;//update this value;
            get(key);//move the node to the front of the list
            return;
        }
        
        else node=new Node(key, value);
        
        //update the map
        map.put(key,node);
        
        //update the list
        if(head==null){//empty queue
            head=node;
            tail=node;
            size++;
            return;
        }
        
        else{
            //put the value in the front of the list
            moveFront(node);
            size++;

            //check if we need to delete the tail
            if(size>capacity){//delete the tail
                map.remove(tail.key);
                Node preTail=tail.pre;
                preTail.next=null;
                tail=preTail;
                size--;
            }
        }
    }
    private void moveFront(Node node){//move this node to the front of the list. Note the we need to keep track of head also.
        node.next=head;
        node.pre=null;
        head.pre=node;
        head=node;
    }
}

class Node{
    int key;
    int value;
    Node pre,next;
    
    public Node(int key, int value){
        this.key=key;
        this.value=value;
        pre=null;
        next=null;
    }
}