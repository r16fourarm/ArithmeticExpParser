/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubestekom;

import java.util.ArrayList;
import java.util.Stack;

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
    private Stack stkgo;
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

    public modeltekom() {
        aryOperator = new ArrayList();
        aryBesaran = new ArrayList();
        aryToken = new ArrayList();
        stkgo = new Stack();
        status = false;
    }

    public modeltekom(String kata) {
        this.kata = kata;
        aryOperand = new ArrayList();
        aryOperator = new ArrayList();
        aryString = new ArrayList();
        aryBesaran = new ArrayList();
        aryToken = new ArrayList();
        stkgo = new Stack();
        status = false;
    }

    public boolean isvar(String s) {
        boolean stat = false;
        for (int i = 0; i < s.length(); i++) {
            /*if ((s.length() == 1 && Character.isLetter(0)) || (Character.isLetter(s.charAt(0)) && (s.length() > 1 && Character.isLetterOrDigit(s.charAt(1)))) || (i < s.length() - 1 && Character.isLetter(s.charAt(i)) && Character.isLetterOrDigit(s.charAt(i + 1)) && !Character.isDigit(s.charAt(i - 1)))) {
             stat = true;
             }*/
            stat = (isreal(s) || isint(s) || !Character.isLetterOrDigit(s.charAt(i)) || isoperator(s.charAt(i)) || Character.isDigit(s.charAt(0))) ? false : true;
        }
        return stat;
    }

    public void inputtotoken(String s) {
        for (int i = 0; i < s.length(); i++) {
            aryToken.add(s.charAt(i));
        }
    }

    public boolean isint(String s) {
        boolean stat;
        try {
            Integer x = Integer.parseInt(s);
            stat = (x == null) ? false : true;
        } catch (Exception e) {
            stat = false;
        }
        return stat;
    }

    public boolean isreal(String s) {
        boolean stat;
        try {
            Double x = Double.parseDouble(s);
            stat = (x == null) ? false : true;
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
                if (isoperand(kata.charAt(i))) {
                    aryOperand.add(kata.charAt(i));
                }

                if (isoperator1(kata.charAt(i))) {
                    if (kata.charAt(0) == 'e'||((i>0&&kata.charAt(i-1)=='e')&&(i>=2&&(!Character.isDigit(kata.charAt(i-2))||isoperator(kata.charAt(i-2)))))||iskurungbuka(kata.charAt(i+1))) {
                        String temp3 = "";
                        for (Character c : aryOperand) {
                            temp3 = temp3 + c;
                        }
                        if (!aryOperand.isEmpty()) {
                            tokenoperand(temp3);
                            aryString.add(temp3);
                        }
                        tokenoperator(kata.charAt(i));
                        aryString.add(kata.charAt(i));
                        aryOperand.clear();
                        continue;

                    } else if (i > 0 && (kata.charAt(i - 1) == 'e')) {
                        aryOperand.add(kata.charAt(i));
                        continue;
                    }
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
            }
            for (Character c : aryOperand) {
                temp = temp + c;
            }
            if (!temp.equals("")) {
                tokenoperand(temp);
                aryString.add(temp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isgord(ArrayList<String> a) {
        Object v = 'v';
        Object temp = null;
        boolean stat = false;
        try {
            for (String s : a) {
                if (s.equals("left parenthesis")) {
                    stkgo.push(v);
                } else if (s.equals("right parenthesis")) {
                    temp = stkgo.pop();
                }
            }
            if (stkgo.isEmpty()) {
                stat = true;
            } else {
                stat = false;
            }
        } catch (Exception e) {
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
            if (aryBesaran.get(i).equals("undefined") || !isgord(aryBesaran)) {
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

    public boolean isoperator(Character c) {
        return (c.equals('+') || c.equals('-') || c.equals('*') || c.equals('/'));
    }

    public boolean isoperator1(Character c) {
        return (c.equals('+') || c.equals('-'));
    }

    public boolean isoperand(Character c) {
        return !isoperator(c) && !isgrouping(c);
    }

    public boolean iskurungbuka(Character c) {
        return c.equals('(');
    }

    public boolean iskurungtutup(Character c) {
        return c.equals(')');
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

    public void bacajenis() {
        for (Character c : aryToken) {
            aryBesaran.add(bacajenistoken(c));
        }
    }

    public void printaryjenis() {
        bacajenis();
        System.out.println(aryBesaran.toString().replace(',', '|'));
    }

}
