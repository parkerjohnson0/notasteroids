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
public interface MyList {
    public int size();
    public Object get(int index);
    public void add(Object o);
    public void add(int index, Object o);
    public void remove(int index);
    public void remove(Object o);
    public void set(int index, Object o);
    public boolean contains(Object o);
    public int indexOfObject(Object o);
    public boolean isEmpty();
    public void removeAll();
}
