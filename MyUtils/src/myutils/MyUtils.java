package myutils;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;

public class MyUtils {
    public static void main(String[] args) throws FileNotFoundException {

//
//        ArrayList<String> list = MyFile.readFile("testRead.csv");
//        for (int i = 0; i < list.size(); i++){
//            System.out.println(list.get(i));
//        }
//        File file = new File("testWrite.csv");
//        boolean exists = file.exists();
//        System.out.println(exists);
//        MyFile.writeFile(list, "testWrite.csv");
//        exists = file.exists();
//        System.out.println(exists);

    Random rand = new Random();
    MyHeap mh = new MyHeap();
    
    for (int i = 0; i < 10; i++){
        mh.add(rand.nextInt(100) + 1);
    }
    for (int i = 0; i < 10; i++){
        System.out.print(" " + mh.heap[i]);
        
    }
    System.out.println("");
    for (int i = 0; i < 10; i++){
        System.out.print(" " + mh.getMax());
        
    }




    }
}
