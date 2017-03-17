package org.nhsrc;

import java.util.Arrays;
import java.util.List;

public class Trial {
    public static double hyp(int x, int y)   {
        return Math.sqrt((x*x)+(y*y));
    }
    public static void main(String[] args) {

        int a=6, b=9;
//        System.out.println(hyp(2,3));
        List<String> listOfStrings = Arrays.asList("ramesh", "suresh", "mahesh");
        System.out.println(listOfStrings);
        int i=0;
        while(i < listOfStrings.size()){
            System.out.println(listOfStrings.get(i).toUpperCase());
            i++;
        }
    }
}
