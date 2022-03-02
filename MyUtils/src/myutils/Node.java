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
public class Node <T>{
    Node prev, next;
    private Object obj;
    
    public Node(Object newObj){
      this.obj = newObj;   
    }
    
    public Object getObj(){
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
    
}
