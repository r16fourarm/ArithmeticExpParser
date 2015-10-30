/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubestekom;

import java.text.ParseException;
import java.util.ArrayList;
import javax.xml.bind.ParseConversionEvent;

/**
 *
 * @author R16
 */
public class modeltekom {

    private String kata;
    private ArrayList<Character> aryOperand;
    private ArrayList<Character> aryOperator;
    private ArrayList aryString;
    private ArrayList<String> aryBesaran;
    private ArrayList<Character> aryToken;
    /*
     token 1`= var
     token 2 = real
     token 3 = int
     token 4 = left parenth
     token 5 = right parenth
     token 6 = add
     token 7 = sub
     token 8 = multipy
     token 9 = divide
     toke 0 = unidentified
     */
    private boolean status;

    public String getKata() {
        return kata;
    }

    public ArrayList getAryString() {
        return aryString;
    }

    public ArrayList<String> getAryBesaran() {
        return aryBesaran;
    }

    public ArrayList<Character> getAryToken() {
        return aryToken;
    }

    public modeltekom(String kata) {
        this.kata = kata;
        aryOperand = new ArrayList();
        aryOperator = new ArrayList();
        aryString = new ArrayList();
        aryBesaran = new ArrayList();
        aryToken = new ArrayList();
        status = false;
    }

    public boolean isvar(String s) {
        boolean stat = false;
        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetter(s.charAt(0)) || (i < s.length() - 1 && Character.isLetter(s.charAt(i)) && Character.isLetterOrDigit(s.charAt(i + 1)) && !Character.isDigit(s.charAt(i - 1)))) {
                stat = true;
            }
        }
        return stat;
    }

    public boolean isint(String s) {
        boolean stat = false;
        try {
            Integer x = Integer.parseInt(s);
            if (x == null) {
            } else {
                stat = true;
            }
        } catch (Exception e) {
            stat = false;
        }
        return stat;
    }

    /*public boolean isreal(String s) {
     boolean stat = false;
     for (int i = 0; i < s.length(); i++) {
     if (s.charAt(i) == 'e' && s.length() >= 3) {
     if (Character.isDigit(s.charAt(i + 1)) || Character.isDigit(s.charAt(i - 1)) || (isoperator1(s.charAt(i + 1)) && Character.isDigit(s.charAt(i + 2)))) {
     stat = true;
     }
     } else if (s.charAt(i) == ',') {
     if (Character.isLetter(s.charAt(i - 1))) {
     stat = false;
     break;
     } else {
     stat = true;
     }
     }
     }
     return stat;
     }*/
    public boolean isreal(String s) {
        boolean stat = false;
        try {
            Double x = Double.parseDouble(s);
            if (x == null) {
            } else {
                stat = true;
            }
        } catch (Exception w) {
            stat = false;
        }
        return stat;
    }

    public void tokenoperand(String s) {
        if (isvar(s)) {
            aryToken.add('1');
        } else if (isreal(s) && !isint(s)) {
            aryToken.add('2');
        } else if (isint(s)) {
            aryToken.add('3');
        } else {
            aryToken.add('0');
        }
    }

    public void tokenoperator(Character c) {
        switch (c) {
            case '(':
                aryToken.add('4');
                break;
            case ')':
                aryToken.add('5');
                break;
            case '+':
                aryToken.add('6');
                break;
            case '-':
                aryToken.add('7');
                break;
            case '*':
                aryToken.add('8');
                break;
            case '/':
                aryToken.add('9');
                break;
        }

    }

    public void baca() {
        try {
            String temp = "";
            for (int i = 0; i < kata.length(); i++) {
                /*if(invalidatcc1(kata, i)||invalidatcc1(kata, i+1)){
                 continue;
                 }*/
                /*if (iskurungbuka(kata.charAt(i))) {
                 tokenoperator(kata.charAt(i));
                 aryOperator.add(kata.charAt(i));
                 aryString.add(kata.charAt(i));
                 }*/
                if (isoperand(kata.charAt(i))) {
                    aryOperand.add(kata.charAt(i));
                }
                if (isoperator1(kata.charAt(i)) && (kata.charAt(i - 1) == 'e')) {
                    aryOperand.add(kata.charAt(i));
                    continue;
                }
                if (isoperator(kata.charAt(i)) || iskurungbuka(kata.charAt(i)) || iskurungtutup(kata.charAt(i))) {
                    String temp1 = "";
                    for (Character c : aryOperand) {
                        temp1 = temp1 + c;
                    }
                    if (!aryOperand.isEmpty()) {
                        tokenoperand(temp1);
                        aryString.add(temp1);
                    }
                    tokenoperator(kata.charAt(i));
                    aryString.add(kata.charAt(i));
                    aryOperand.clear();
                }
                /*if (iskurungtutup(kata.charAt(i))) {
                 tokenoperator(kata.charAt(i));
                 aryOperator.add(kata.charAt(i));
                 aryString.add(kata.charAt(i));
                 }*/
            }
            for (Character c : aryOperand) {
                temp = temp + c;
            }
            if (!temp.equals("")) {
                tokenoperand(temp);
                aryString.add(temp);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
            e.printStackTrace();
        }
    }

    public boolean isgord(ArrayList<String> a) {
        int l = 0;
        int r = 0;
        boolean stat = false;
        for (String s : a) {
            if (s.equals("left parenthesis")) {
                l++;
            } else if (s.equals("right parenthesis")) {
                r++;
            }
        }
        if (l == r) {
            stat = true;
        } else {
            stat = false;
        }
        return stat;
    }

    public String bacajenistoken(Character token) {
        String jenis;
        if (token.equals('1') || token.equals('2') || token.equals('3')) {
            jenis = "operand";
        } else if (token.equals('4')) {
            jenis = "left parenthesis";
        } else if (token.equals('5')) {
            jenis = "right parenthesis";
        } else if (token.equals('6') || token.equals('7') || token.equals('8') || token.equals('9')) {
            jenis = "operator";
        } else {
            jenis = "undefined";
        }
        return jenis;
    }

    public void bacavaliditas() {
        for (int i = 0; i < aryBesaran.size(); i++) {
            if (aryBesaran.get(i).equals("undefined")) {
                status = false;
                break;
            }
            if (aryBesaran.get(i).equals("operator")) {
                if (i == 0 || i == aryBesaran.size() - 1 || aryBesaran.get(i + 1).equals("operator")
                        || !(aryBesaran.get(i + 1).equals("left parenthesis") || aryBesaran.get(i + 1).equals("operand"))) {
                    status = false;
                    break;
                } else {
                    status = true;
                }

            } else if (aryBesaran.get(i).equals("operand")) {
                if (i < aryBesaran.size() - 1 && aryBesaran.get(i + 1).equals("operand")) {
                    status = false;
                    break;
                } else {
                    status = true;
                }
            } else if (aryBesaran.get(i).equals("left parenthesis")) {
                if(i==aryBesaran.size()-1||aryBesaran.get(i+1).equals("operator")){
                    status=false;
                    break;
                }
                for (int x = i + 1; x < aryBesaran.size(); x++) {
                    if (!aryBesaran.get(x).equals("right parenthesis") && !isgord(aryBesaran) == true || i != aryBesaran.size() - 1) {
                        status = false;
                        break;
                    } else{    
                        status = true;
                    }
                }
            } else if (aryBesaran.get(i).equals("right parenthesis")) {
                if (!isgord(aryBesaran) == true) {
                    status = false;
                    break;
                } else {
                    status = true;
                }
            }
        }
    }

    public boolean isStatus() {
        return status;
    }

    public void printvaliditas() {
        System.out.println(status);
        if (status == true) {
            System.out.println("valid");
        } else {
            System.out.println("invalid");
        }
    }

    public boolean isgrouping(Character c) {
        return iskurungbuka(c) || iskurungtutup(c);
    }

    public boolean invalidatcc1(String s, int i) {
        return isoperator(s.charAt(1)) || iskurungtutup(s.charAt(1)) || (Character.isLetter(s.charAt(i)) && s.charAt(i + 1) == ',');
    }

    public boolean isoperator(Character c) {
        return (c.equals('+') || c.equals('-') || c.equals('*') || c.equals('/'));
    }

    public boolean isoperator1(Character c) {
        return (c.equals('+') || c.equals('-'));
    }

    public boolean isoperand(Character c) {
        return Character.isLetterOrDigit(c) || c.equals(',');
    }

    public boolean iskurungbuka(Character c) {
        return c.equals('(');
    }

    public boolean iskurungtutup(Character c) {
        return c.equals(')');
    }

    public void bacaoperator(Character c) {
        if (isoperator(c)) {
            aryOperator.add(c);
        }
    }

    public void bacaoperand(Character c) {
        if (isoperand(c)) {
            aryOperand.add(c);
        }
    }

    public void printtoken() {
        System.out.println(aryToken.toString().replace(',', ' '));
    }

    public void printaryoperand() {
        System.out.println(aryOperand.toString());
    }

    public void printaryoperat() {
        System.out.println(aryOperator.toString());
    }

    public void printarystring() {
        System.out.println(aryString.toString());
    }
    public void bacajenis(){
    for (Character c : aryToken) {
            aryBesaran.add(bacajenistoken(c));
        }
    }
    public void printaryjenis() {
        for (Character c : aryToken) {
            aryBesaran.add(bacajenistoken(c));
        }
        System.out.println(aryBesaran.toString().replace(',', '|'));
    }

}
