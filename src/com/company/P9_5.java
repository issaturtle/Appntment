package com.company;


/**
 * Hung Nguyen
 * 013626210
 * This program asks user to check appointment or create appointments
 */

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.FileNotFoundException;
import java.io.File;



/**
 * superclass to create appointments
 */
class Appointments{
    //store appointment info
    private int mDate, mMonth, mYear;
    private char cType;
    //store appointment description
    private String mDescription;


    //constructor for appointments
    public Appointments(int nYear, int nMonth, int nDay, String tempDes){
        this.mDate = nDay;
        this.mMonth = nMonth;
        this.mYear = nYear;
        this.mDescription = tempDes;
    }

    /**
     * method to check if appointment occurs on date
     * @param year
     * @param month
     * @param day
     * @return true/false
     */
    public boolean occursOn(int year, int month, int day) {
        if ((this.mDate == day) && (this.mMonth == month) && (this.mYear == year)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * setter for date
     * @param nDate
     */
    public void setmDate(int nDate) {
        this.mDate = nDate;
    }

    /**
     * getter for date
     * @return mDate
     */
    public int getmDate() {
        return this.mDate;
    }

    /**
     * setter for month
     * @param nMonth
     */
    public void setmMonth(int nMonth){
        this.mMonth = nMonth;
    }

    /**
     * getter for month
     * @return mMonth
     */
    public int getmMonth(){
        return this.mMonth;
    }

    /**
     * setter for year
     * @param nYear
     */
    public void setmYear(int nYear){
        this.mYear = nYear;
    }

    /**
     * getter for year
     * @return mYear
     */
    public int getmYear(){
        return this.mYear;
    }

    /**
     * setter for description
     * @param strDescription
     */
    public void setmDescription(String strDescription){
        this.mDescription = strDescription;
    }
    public String getmDescription(){
        return this.mDescription;
    }

    /**
     * getter for appointment
     * @return strReturn
     */
    public String getAppointment() {
        String strReturn = this.mDescription + " " + this.mDate + "-" + this.mMonth + "-" + this.mYear;
        return strReturn;
    }

//    public ArrayList<Appointments> getAppLists() {
//        return this.appLists;
//    }
}

/**
 * Inherits appointment as a one time appointment
 */
class Onetimes extends Appointments {
    //passes user value to Appointment
    public Onetimes( int nYear, int nMonth, int nDay, String tempDes) {
        super( nYear, nMonth, nDay, tempDes);
    }

    /**
     * override method from Appointment
     * @return strOneTime
     */
    public String getAppointment() {
        String strOneTime = "Onetime appointment: " + super.getAppointment();
        return strOneTime;
    }
}

/**
 * inherits Appointment as daily appointment
 */
class Dailies extends Appointments {
    //passes user input to Daily
    public Dailies( int nYear, int nMonth, int nDay, String tempDes) {
        super( nYear, nMonth, nDay, tempDes);
    }

    @Override
    /**
     * overrides occursOn and check if input is the same or after
     * appointment
     * @param year,month,day
     * @return bReturn
     */
    public boolean occursOn(int year, int month, int day) {
        boolean bReturn = false;
        //create instances of LocalDateTime to compare time
        LocalDateTime a = LocalDateTime.of(year, month, day, 12, 00);
        LocalDateTime b = LocalDateTime.of(this.getmYear(), this.getmMonth(), this.getmDate(), 12, 00);
        //if userInput > daily or if equal date
        if(a.isAfter(b) || a.isEqual(b)){
            bReturn = true;
        }
        else{
            bReturn = false;
        }

        return bReturn;
    }
    @Override
    /**
     * overirdes getAppointment and return daily appointment string
     * @return strDaily
     */
    public String getAppointment() {
        String strDaily = "Daily appoitnment: " + super.getAppointment();
        return strDaily;
    }
}

/**
 * extends Appointment as monthly appointment
 */
class Monthlies extends Appointments{
    public Monthlies( int nYear, int nMonth, int nDay, String tempDes) {
        super(nYear,nMonth,nDay,tempDes);
    }

    /**
     * Check if monthly is the same day as user input
     * @param year
     * @param month
     * @param day
     * @return occursOn
     */
    @Override
    public boolean occursOn(int year, int month, int day) {
        if ((this.getmMonth() <= month && this.getmDate()== day && this.getmYear() <= year)
                ||(this.getmMonth()>= month && this.getmDate() == day && this.getmYear() < year )){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Return monthly appointment string
     * @return strMonthly
     */
    @Override
    public String getAppointment() {
        String strMonthly = "Monthly appointment: " + super.getAppointment();
        return strMonthly;
    }

}

/**
 * asks user to enter appointment
 */
public class P9_5 {
    public static final Scanner in = new Scanner(System.in);

    /**
     * check if string has letters/special characters
     *
     * @param strTemp
     * @return ArrayList
     */
    public static ArrayList<Character> CheckValid(String strTemp) {
        ArrayList<Character> arrChars = new ArrayList<Character>();
        boolean bRep = true;
        boolean flag = true;

        //loop through string to see if it has illegal inputs
        for (int i = 0; i < strTemp.length(); i++) {
            flag = Character.isDigit(strTemp.charAt(i));
            if (flag == true) {
                i++;
            } else {
                arrChars.add(strTemp.charAt(i));
            }
        }
        return arrChars;
    }

    /**
     * Check if user input of day is valid
     *
     * @return nDay
     */
    public static int CheckDay() {
        int nDay = 0;
        int nCount = 0;
        //create a pattern instance
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        //check if input is valid
        do {
            if (nCount >= 1) {

                System.out.println(String.format("%s is not valid", nDay));
            }
            System.out.print("Enter day: ");
            //while userinput is wrong
            while (!in.hasNextInt()) {
                String strTemp = in.next();
                Matcher m = p.matcher(strTemp);
                boolean b = m.find();
                //if input is invalid
                if (b) {
                    System.out.println(String.format("Special character %s is not valid", strTemp));
                    System.out.print("Enter day: ");
                } //if there is no special character
                else {
                    System.out.println(String.format("Letters are not valid: %s", strTemp));
                    System.out.print("Enter day: ");
                }
            }
            nDay = in.nextInt();
            nCount++;
        } while (nDay < 0 || nDay > 32);

        return nDay;

    }

    /**
     * Checking if user input of month is valid
     *
     * @return nMonth
     */
    public static int CheckMonth() {
        int nCount = 0;
        int nMonth = 0;
        //create pattern instance
        Pattern p = Pattern.compile("[^a-z0-9 ]", Pattern.CASE_INSENSITIVE);
        //check if user input of month is valid
        do {
            if (nCount >= 1) {
                System.out.println(String.format("%s is not valid", nMonth));
            }
            System.out.print("Enter Month: ");
            //while user input is invalid
            while (!in.hasNextInt()) {
                String strTemp = in.next();
                Matcher m = p.matcher(strTemp);
                boolean b = m.find();
                //if input contains special characters
                if (b) {
                    System.out.println(String.format("Special character %s is not valid", strTemp));
                    System.out.print("Enter Month: ");
                } else { //if input contains letters
                    System.out.println(String.format("Letters are not valid: %s", strTemp));
                    System.out.print("Enter Month: ");
                }
            }
            nMonth = in.nextInt();
            nCount++;
        } while (nMonth < 0 || nMonth > 12);

        return nMonth;

    }

    /**
     * create new substring of user input date string
     *
     * @param strTemp
     * @return strTemp
     */
    public static String moveStringIndex(String strTemp) {
        //create a new substring after -
        strTemp = strTemp.substring(strTemp.indexOf('-') + 1);
        return strTemp;
    }

    /**
     * Opening file and saving new appointment into the file
     * @param file
     * @param appointment
     * @return bSave
     * @throws IOException
     */
    public static boolean save(String file, Appointments appointment) throws IOException {
        //flag to check if save worked
        boolean bSave = false;
        //add .txt into file
        file = file + ".txt";
        //create filewriter instance to append into file
        FileWriter fwriter = new FileWriter(file, true);
        //create printwriter instance to write to file
        PrintWriter outF = new PrintWriter(fwriter);
        //append into file
        outF.println(appointment.getAppointment());
        //close file
        outF.close();

        //create new instance of file
        File appFile = new File(file);
        //Scanner for reading file
        Scanner inputF = new Scanner(appFile);

        String strLast = "";
        //String array to store file input
        String[] strTemps = new String[6];
        //store last string line and split them into array
        while(inputF.hasNextLine()){
            strTemps = inputF.nextLine().split(" ");
        }
        //if saved successfully
        if(strTemps[2].equals(appointment.getmDescription())) {
            bSave = true;
        }
        //return flag
        return bSave;
    }

    /**
     * Load data into an array list before returning it
     * @param fileName
     * @return data
     * @throws FileNotFoundException
     */
    public static ArrayList<Appointments> load(String fileName) throws FileNotFoundException {
        //add file extension
        fileName = fileName + ".txt";
        //create instance of File
        File appFile = new File(fileName);
        //array to store string input
        String[] temp;
        //array list of all appointments
        ArrayList<Appointments> data = new ArrayList<Appointments>();
        //appointments counter
        int i = 0;
        try {
            //create instance of scanner input
            Scanner inputF = new Scanner(appFile);

            String strLine = "";
            //while looping through file
            while (inputF.hasNextLine()) {
                //store nextline into string
                strLine = inputF.nextLine();
                //split string into array
                temp = strLine.split(" ");
                //store type into string
                String strType = temp[0].trim();
                //store description
                String strDescription = temp[2].trim();
                //replace - with space
                strDescription = strDescription.replace('-', ' ');
                //split date into month day and year
                String[] nDate = temp[3].trim().split("-");
                int nDay = Integer.parseInt(nDate[0]);
                int nMonth = Integer.parseInt(nDate[1]);
                int nYear = Integer.parseInt(nDate[2]);
                //create new instances of appointments
                if (strType.equals("Monthly") ) {
                    data.add(new Monthlies(nYear, nMonth, nDay, strDescription));
                } else if (strType.equals("Daily") ) {
                    data.add(new Dailies(nYear, nMonth, nDay, strDescription));
                } else if (strType.equals("Onetime") ) {
                    data.add(new Onetimes(nYear, nMonth, nDay, strDescription));
                }
                i += 1;

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
        if (i == data.size() && i != 0) { //if counter = size of data it means all appointments are loaded
            System.out.println("All appointments have been loaded");
        }
        else{
            System.out.println("Appointments missing");
        }
        return data;
    }

    /**
     * asks for user input
     *
     * @param args
     */
    public static void main(String[] args) throws FileNotFoundException {

//        ArrayList<Appointments> appLists = load("appointments");
//        for (int i = 0; i < appLists.size(); i++) {
//            System.out.println(appLists.get(i).getAppointment());
//        }


        boolean bRepeat = true;
        //create array list of appointments
        ArrayList<Appointments> appLists = new ArrayList<Appointments>();
        //store user input
        char cInput = 1;
        //while user doesn't enter q
        while (cInput != 'Q') {
            //while(cInput != 'Q' || cInput != 'A' || cInput)
            System.out.print("Select an option: A for add an appointment, C for checking, L to load, Q to quit: ");
            //read user input and store the first uppercase character
            String strInput = in.next();
            cInput = strInput.charAt(0);
            cInput = Character.toUpperCase(cInput);
            //if user choose A
            if (cInput == 'A') {
                //asks user to enter choice
                System.out.print("Enter the type (O-Onetime, D-Daily, or M-Monthly):");
                char cType = Character.toUpperCase(in.next().charAt(0));
                while(cType != 'O' && cType != 'D' && cType != 'M') {
                    System.out.print("Only enter O D or M: ");
                    cType = Character.toUpperCase(in.next().charAt(0));
                }
                System.out.print("Enter the date (yyyy-mm-dd): ");
                //create an instance of DateTimeFormatter to change date values from yyyy/mm/d to yyyy-mm-dd
                DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                strInput = in.next();
                String strDash = "/";
                //if string contains / then format to -
                for(int i = 0; i< strInput.length();i++){
                    if(strDash.contains(Character.toString(strInput.charAt(i)))){
                        LocalDate localDate = LocalDate.parse(strInput, format);
                        strInput = String.valueOf(localDate);
                    }
                }
               // LocalDate localDate = LocalDate.parse(strInput, format);

                //store formatted date into strInput
                //strInput = String.valueOf(localDate);
                //store year into nYear
                int nYear = Integer.parseInt(strInput.substring(0, strInput.indexOf('-')));
                //find next numbers after -
                strInput = moveStringIndex(strInput);
                //store into month
                int nMonth = Integer.parseInt(strInput.substring(0, strInput.indexOf('-')));
                //find next numbers after -
                strInput = moveStringIndex(strInput);
                //store number after last -
                int nDay = Integer.parseInt(strInput);
                in.nextLine();
                System.out.print("Enter the description: ");
                String strDescription = in.nextLine();
                //replace space with -
                strDescription= strDescription.trim();
                String strFileDescription = "";
                if(strDescription.contains(" ")){
                    strFileDescription = strDescription.replace(" ", "-");
                }
                else {
                    strFileDescription = strDescription;
                }
                Appointments appoint = null;
                //Check which type of appointment user want, then append into list
                if (cType == 'O') {
                    appLists.add(new Onetimes(nYear, nMonth, nDay, strDescription));
                    appoint = new Onetimes(nYear, nMonth, nDay, strFileDescription);
                } else if (cType == 'D') {
                    appLists.add(new Dailies(nYear, nMonth, nDay, strDescription));
                    appoint = new Dailies(nYear, nMonth, nDay, strFileDescription);
                } else if (cType == 'M') {
                    appLists.add(new Monthlies(nYear, nMonth, nDay, strDescription));
                    appoint = new Monthlies(nYear, nMonth, nDay, strFileDescription);
                }
                System.out.println("Do you want to save appointment? Y/N: ");

                char cSave = 1;
                String strChoice = "";
                strChoice = in.next();
                //store first letter of string into a capitalized char
                cSave = strChoice.charAt(0);
                cSave = Character.toUpperCase(cSave);
                //while character is not y and n
                while (cSave != 'Y' && cSave != 'N') {
                    //while(cInput != 'Q' || cInput != 'A' || cInput)
                    System.out.print("Choose only Y or N: ");
                    //read user input and store the first uppercase character
                    strChoice = in.next();
                    cSave = strChoice.charAt(0);
                    cSave = Character.toUpperCase(cSave);
                }
                //if char is y
                if (cSave == 'Y') {
                    try{
                        //check if appointments are saved
                        boolean bCheck = save("appointments", appoint);
                        if(bCheck == true) {
                            System.out.println("saved successfully");
                        }
                        else {
                            System.out.println("failed to save");
                        }
                    }
                    catch (IOException e){
                        System.out.println("error");
                    }

                }
                else{
                    System.out.println("appointment not saved, only one time use");
                }

            } else if (cInput == 'C') { // let user check which appointments are available
                String strYear = "";
                bRepeat = true;
                //while user wrong inputs
                while (bRepeat) {
                    try {
                        System.out.print("Enter Year (numbers>=0): ");
                        strYear = in.next();
                        if (Integer.parseInt(strYear) < 0) { //repeat if user enters negative year
                            System.out.println("Year can't be smaller than 0");
                            continue;
                        }
                        bRepeat = false;
                    } catch (NumberFormatException e) { //if user entered any other value than integer
                        //an array list to store wrong inputs
                        ArrayList<Character> arrChars = new ArrayList<Character>();
                        arrChars = CheckValid(strYear);
                        //if List is not empty
                        if (!arrChars.isEmpty()) {
                            System.out.print("Please dont enter: ");
                            //print list
                            for (char cTemp : arrChars) {
                                System.out.print(String.format(" %c", cTemp));
                            }
                            System.out.println("");
                        }
                        in.nextLine();
                    }
                }
                //asks user to enter day
                int nDay = CheckDay();
                //asks user to enter month
                int nMonth = CheckMonth();
                //if there are appointments
                if(!appLists.isEmpty()) {

                    //check if appointments fit the dates
                    for (int i = 0; i < appLists.size(); i++) {
                        if (appLists.get(i).occursOn(Integer.parseInt(strYear), nMonth, nDay) == true) {
                            System.out.println(appLists.get(i).getAppointment());

                        }
                    }

                    System.out.println("Checking finished");
                } //if there aren't any appointments
                else {
                    System.out.println("No Appointment set");
                }
            }
            else if (cInput == 'L') {
                appLists = load("appointments");
                if (appLists.size() != 0){
                    System.out.println("Loaded successfully");
                }else{
                    System.out.println();
                }
            }
            else if (cInput == 'Q') { //end program if user enters Q
                System.out.println("End");
            } else{ //if user enter wrong choices
                System.out.println("Please enter correct choice");
            }
        }
    }

}



