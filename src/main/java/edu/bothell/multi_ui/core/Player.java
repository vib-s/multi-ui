package edu.bothell.multi_ui.core;

public class Player {

    private char    chr     = 'x';
    private String  sId     = "";
    private int     noTiles = 0;

    public Player(){
    }

    public Player(char chr){
        this.chr = chr;
    }

    public char getChar(){
        return this.chr;
    }

    public void setSId(String s){
        this.sId = s;
    }

    public String getSId(){
        return this.sId;
    }

    public int getNoTiles(){
        return this.noTiles;
    }

    public void addTile(){
        this.noTiles++;
    }
}
