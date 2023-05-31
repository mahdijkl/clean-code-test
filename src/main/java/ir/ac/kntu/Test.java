package ir.ac.kntu;

import java.util.Scanner;
import java.lang.Math;

public class test{

    public static void main(String[] args){
        int x = 5;
        String          y      =      "hi";
        double Test = 0;
        double j = 0.3;
        String a = "";
        String MyCoolPointless_string= "1";
        String myString = "ksjdkdj    kjdkjdkjd" +
                            "dkjdkjdkjdkjd";
        int g = 0;  int h = 1;                    
        if (x < 10)
        {
            System.out.print("yes");
        }
        else {
        System.out.print("no");}
        
        while (x < 10) 
        {
        x++;
        }
        
        for (int i=0 ;
             i < 3 ; i++) {
            Test = Math.sin(j)+Math.cos(j) + 2*(Math.sin(j)+Math.cos(j)) + 3*(Math.sin(j)+Math.cos(j)) + 4*(Math.sin(j)+Math.cos(j)) + i;
        }

        switch (a)
        {
            case "1": break;
            case "2": break;
        }

        my_func(MyCoolPointless_string, MyCoolPointless_string, MyCoolPointless_string, MyCoolPointless_string, MyCoolPointless_string, MyCoolPointless_string, MyCoolPointless_string, MyCoolPointless_string);
    }
    
    public static String my_func (String str1, String str2, String str3, String str4, String str5, String str6, String str7, String str8)
    {
        return str1+str2+str3+str4+str5+str6+str7+str8;
    }
    
    
}