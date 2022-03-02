/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package myutils;

/**
 *
 * @author peppe
 */
public class MyLinkedList implements MyList {

    private Node head, tail;
    private int size;
    private Object obj;

    public MyLinkedList(){
        size = 0;
    }
    @Override
    public int size() {
        return size;
    }

    @Override
    public Object get(int index) {
        int i = 0;
        Node curr = head;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        while (i < index) {
            curr = curr.next;
            i++;
        }
        return curr.getObj();
    }

    @Override
    public void add(Object o) {
        Node n = new Node(o);
        Node curr;
        if (isEmpty()) {
            head = n;
            tail = n;
        } else {
            n.prev = tail;
            tail.next = n;
            tail = n;
        }
        size++;
    }

    @Override
    public void add(int index, Object o) {
        Node n = new Node(o);
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        if (size == 0 && index == 0) {
            head = n;
            tail = n;
        } else if (index == 0) {
            n.next = head;
            head.prev = n;
            head = n;
        } else if (index == size) {
            add(o);
        } else {
            Node curr = head;
            int i = 1;
            while (i < index) {
                curr = curr.next;
                i++;
            }
            n.next = curr.next;
            curr.next.prev = n;
            n.prev = curr;
            curr.next = n;
        }
        size++;
    }

    @Override
    public void remove(int index) {
        if (index < 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }
        if (index == 0){
            head = head.next;
            head.prev = null;
        }
        else if (index == size - 1){
            tail = tail.prev;
            tail.next = null;
        }
        else{
            Node curr = head;
            int i = 0;
            while (i < index){
                curr = curr.next;
                i++;
            }
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
        }
        size--;
    }

    @Override
    public void remove(Object o) {
        int i = indexOfObject(o);
        if ( i >= 0){
            remove(i);
        }
    }

    @Override
    public void set(int index, Object o) {
        Node curr = head;
        int i = 0;
        while (i < index){
            curr = curr.next;
            i++;
        }
        curr.setObj(o);
    }

   
    @Override
    public boolean contains(Object o) {
        Node curr = head;
        int i = 0;
        while (i < size) {
            if (curr.getObj() == o) {
                return true;
            } else {
                curr = curr.next;
                i++;
            }
        }
        return false;

    }

    @Override
    public int indexOfObject(Object o) {
        Node curr = head;
        int i = 0;
        while (i < size) {
            if (curr.getObj() == o) {
                return i;
            } else {
                curr = curr.next;
            }
            i++;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        if (size == 0 && head == null && tail == null) {
            return true;
        }
        return false;
    }

    @Override
    public void removeAll() {
        size = 0;
        head = null;
        tail = null;
    }

}
