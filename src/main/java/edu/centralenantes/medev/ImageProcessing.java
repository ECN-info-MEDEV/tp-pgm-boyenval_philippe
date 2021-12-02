/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.centralenantes.medev;

import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author constance
 */
public class ImageProcessing {
    // Attributs
    private String path;
    private int largeur;
    private int hauteur;
    private int highestValue;
    private int[][] img;
    private int[] grayscaleLevels = new int[256]; //histogram
    
    // Constructeurs
    public ImageProcessing(String filepath){
        this.path = filepath;
    }

    public String getPath() {
        return path;
    }

    public int getLargeur() {
        return largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public int getHighestValue() {
        return highestValue;
    }

    public int[][] getImg() {
        return img;
    }

    public int[] getGrayscaleLevels() {
        return grayscaleLevels;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public void setHighestValue(int highestValue) {
        this.highestValue = highestValue;
    }

    public void setImg(int[][] img) {
        this.img = img;
    }

    public void setGrayscaleLevels(int[] grayscaleLevels) {
        this.grayscaleLevels = grayscaleLevels;
    }
    
    // Methodes
    public void read() {
        FileInputStream fileInputStream;
        try{
            fileInputStream = new FileInputStream(this.path);
            Scanner scan = new Scanner(fileInputStream);
            // Discard the magic number
            scan.nextLine();
            // Discard the comment line
            scan.nextLine();
            // Read pic width, height and max value
            this.largeur = scan.nextInt();
            this.hauteur = scan.nextInt();
            this.highestValue = scan.nextInt();
            fileInputStream.close();
            
            // Now parse the file as binary data
            fileInputStream = new FileInputStream(this.path);
            DataInputStream dis = new DataInputStream(fileInputStream);

            // look for 4 lines (i.e.: the header) and discard them
            int numnewlines = 4;
            while (numnewlines > 0) {
                char c;
                do {
                    c = (char)(dis.readUnsignedByte());
                } while (c != '\n');
                numnewlines--;
            }

            // read the image data
            this.img = new int[this.hauteur][this.largeur];
            for (int row = 0; row < this.hauteur; row++) {
                for (int col = 0; col < this.largeur; col++) {
                    this.img[row][col] = dis.readUnsignedByte();
                    System.out.print(this.img[row][col] + " ");
                }
                System.out.println();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void write(String filename) {
        try {
            File f = new File("/Users/constance/Desktop/"+filename);
            if (!f.createNewFile()){
                System.out.println("Le fichier existe deja.");
            }
            FileWriter fw = new FileWriter(f.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("P2\n");
            bw.write(Integer.toString(this.largeur) + " " + Integer.toString(this.hauteur) + "\n");
            bw.write(Integer.toString(this.highestValue)+"\n");
            for (int i=0; i<=255 ;i++){
                bw.write(Integer.toString(this.grayscaleLevels[i]) + " ");
            }
            bw.close();

        } 
        catch (IOException ex) {
            Logger.getLogger(ImageProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void histogram(){
        for (int i = 0; i < this.hauteur; i++){
            for (int j = 0; j < this.largeur; j++){
                int value = this.img[i][j];
                this.grayscaleLevels[value] ++;
            }
        }
    }
    
    public void threshold(int th0, int th1){
        for (int i = 0; i < this.hauteur; i++){
            for (int j = 0; j < this.largeur; j++){
                if (this.img[i][j] <= th0){
                    this.img[i][j] = 0;
                }
                if (this.img[i][j] >= th1){
                    this.img[i][j] = 255;
                }
            }
        }
    }
    
    public void diff(int[][] imgbis){
        for (int i = 0; i < this.hauteur; i++){
            for (int j = 0; j < this.largeur; j++){
                this.img[i][j] = this.img[i][j] - imgbis[i][j];
            }
        }
    }
}
