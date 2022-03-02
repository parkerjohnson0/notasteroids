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
public class MyHeap {
    int heap[] = new int[100];
    int index = 1;
    
    public void add(int num){
        int i = index;
        heap[index] = num;
        int parent;
        index++;
        
        boolean done = false;
        
        while (i > 1 && !done){
            parent = i/2;
            if (heap[i] > heap[parent]){
                int tmp = heap[i];
                heap[parent] = tmp;
                i = i/2;
            }
            else {
                done = true;
            }
        }
    }
    
    public int getMax(){
        int ans = heap[1];
        heap[1] = heap[index - 1];
        heap[index -1 ] = 0;
        index--;
        int i = 1;
        boolean done = false;
        
        while (i < index && !done){
            if ((heap[i] > heap[i*2]) && (heap[i] > heap[i*2+1])){
                done = true;
            }
            else if (heap[i*2] >= heap[i*2+1]){
                int tmp = heap[i];
                heap[i] = heap[i*2];
                heap[i*2] = tmp;
                i = i*2;
            }
            else{
                int tmp = heap[i];
                heap[i] = heap[i*2+1];
                heap[i*2+1] = tmp;
                i = i*2+1;
            }
        }
        
        return ans;
    }
}
