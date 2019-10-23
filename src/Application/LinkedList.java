package Application;

public class LinkedList {
    Node head;  
    public class Node { 
        String data; 
        Node next; 
        Node(String d) {
        	data = d;
        	next = null;
        	}
        }
    public void Agregar_a_Lista (String Item) { 
        Node new_node = new Node(Item); 
        if (head == null) { 
            head = new Node(Item); 
            return; 
        } 
        new_node.next = null; 
        Node last = head;  
        while (last.next != null) 
            last = last.next; 
        last.next = new_node; 
        return; 
    } 
}
