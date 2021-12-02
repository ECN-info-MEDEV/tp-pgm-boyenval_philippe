/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.centralenantes.medev;

/**
 *
 * @author constance
 */
public class testMedev {
    
    public static void main(String[] args){
        String path = "/Users/constance/Desktop/images/brain.pgm";
        ImageProcessing ip = new ImageProcessing(path);
        ip.read();
        ip.histogram();
        int[] grayscale = ip.getGrayscaleLevels();
        for (int i = 0; i < 256; i++){
            System.out.println(i + " : " + Integer.toString(grayscale[i]));
        }
    }
}
