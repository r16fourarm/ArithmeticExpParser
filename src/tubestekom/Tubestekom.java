/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubestekom;

/**
 *
 * @author R16
 */
public class Tubestekom {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       gui g = new gui();
       g.setVisible(true);
       modeltekom m = new modeltekom();
        System.out.println(m.isoperand('+'));

    }
    
}
