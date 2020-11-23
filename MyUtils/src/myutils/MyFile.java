package myutils;


import java.io.*;
import java.util.ArrayList;

public class MyFile {
    public static MyArrayList readFile(String fname) throws FileNotFoundException {

        MyArrayList myList = new MyArrayList();
        try  {
            File file = new File(fname);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null){
                myList.add(st);
            }
            br.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return myList;
    }

    public static void writeFile(MyArrayList myList, String fname){
        try{
            File file = new File(fname);
            PrintWriter printWriter = new PrintWriter(file);
            for (int i = 0; i < myList.size(); i++){
                printWriter.println(myList.get(i));
            }
            printWriter.close();
        }
        catch (Exception e){

        }
    }
}
