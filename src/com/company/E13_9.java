package com.company;
/**
 * Hung Nguyen
 * 013626210
 * This program asks user to enter a string and substring before returning an index of first
 * substring occurence
 */
import java.util.Scanner;
public class E13_9 {
    /**
     * Recursive call until finding first occurrence of substring
     * @param text
     * @param str
     * @param nIndex
     * @return nIndex
     */
    public static int indexOf(String text, String str, int nIndex)
    {
        //if text starts with substring return index
        if(nIndex < text.length()) {
            if (text.substring(nIndex).startsWith(str) == true) {
                return nIndex;
            }
        }
        else{ //if substring cannot be found
            return -1;
        }
        //increment index
        nIndex++;
        //return index
        return(indexOf(text,str,nIndex));

    }

    /**
     * asks user to enter a string and substring
     * @param args
     */
    public static void main(String[] args) {
        //create instance of scanner
        Scanner in = new Scanner(System.in);
        boolean bCheck = true;

        String strText = "";
        String strSub = "";

        while(bCheck) {
            System.out.print("Enter a string: ");
            strText = in.nextLine();
            System.out.print("Enter a substring: ");
            strSub = in.nextLine();
            if (strSub.length() > strText.length()) {
                System.out.println(String.format("Substring %s cannot be longer than string %s", strSub, strText));
                continue;
            } else if (strSub.equals("") || strText.equals("")) {
                System.out.println("Substring and string cannot be empty");
                continue;
            }
            bCheck = false;
        }

        //get index
        int nResult = indexOf(strText, strSub, 0);
        //print statements based on the result
        if (nResult == -1) {
            System.out.println(String.format("Substring %s cannot be found in %s", strSub, strText));
        } else { //if result is >= 0
            String strTemp = String.valueOf(strText.charAt(nResult));
            //if nResult == 0 or nResult >= 04
            if ((nResult == 0 && strText.startsWith(strTemp)) || (nResult != 0)) {
                System.out.println(String.format("Substring %s starts at index %s", strSub, nResult));
            } else {
                System.out.println(String.format("Substring %s cannot be found in %s", strSub, strText));
            }

        }
    }
}
