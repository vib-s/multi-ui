package edu.bothell.multi_ui.core;

import java.util.ArrayList;


public class Game {
    private final int                  MAX_PLAYERS = 3;
    private final ArrayList<Player>    p;
    private final State                s;
    private int                        turn;
    private Player                     active;
    private Control                    c;

    public Game(Control c){
        this.turn = 0;
        this.s = new World();
        this.p = new ArrayList<>();
        this.c = c;
    }
    
    public Player addPlayer(Player p){
        this.p.add(p);
        if(this.active == null) active = p;

        return p;
    }

    public Player addPlayer(char c, String sId){
        Player p = new Player(c);
        p.setSId(sId);
        return addPlayer(p);
    }

    public char[] getPlayersChar(){
        char[] pcs = new char[p.size()];
        for(int i = 0; i < pcs.length; i++) 
            pcs[i] = p.get(i).getChar();
        
        return pcs;
    }
    
    public boolean isValid(int[] pos, String sId){
        System.out.println("isVAlid?"+s.getIt(pos)+"|" + sId+"|" + active.getSId()+"|");
        if (pos[0] > 2 || pos[1] > 2) return false;
        return s.isOpen(pos) && active.getSId().equals(sId);
    }

    public boolean end(){
        return (tie() || checkWin());
    }

    public boolean tie(){
        for(char[] cs : s.S){
            for(char c : cs){
                if(c == ' ') return false;
            }
        }
        return true;
    }

    public boolean checkWin(){
        // checks if the positive diagonal is true
        boolean winDiagonalPos = true;
        // checks if the negative diagonal is true
        boolean winDiagonalNeg = true;
        // checks if the player won horizontally
        boolean winHorizontally;
        // checks if the player won vertically
        boolean winVertically;

        char c = active.getChar();

        // primary outside loop
        for(int y = 0; y < 3; y++){
            // checking if diagonals are false
            if(this.s.S[y][y] != c) winDiagonalPos = false;
            if(this.s.S[2 - y][y] != c) winDiagonalNeg = false;

            // loops for horizontal and vertical wins
            // set horizontal wins and vertical wins to true before starting to check
            winHorizontally = true;
            winVertically = true;
            // iterate over each slot to check if it is a win
            for(int x = 0; x < 3; x++){
                // checking if horizontal is false
                if(this.s.S[y][x] != c) winHorizontally = false;
                // checking if vertical is false
                // for vertical x and y are opposite
                if(this.s.S[x][y] != c) winVertically = false;
            }
            // if horizontal still remains true that means the player won horizontally
            // if vertical still remains true that means the player won vertically
            if(winHorizontally || winVertically) return true;
        }
        // iteration through board done

        // if the player won through diagonals it returns true, otherwise return false
        return (winDiagonalNeg || winDiagonalPos);
    
    }

    public Player winningPlayer(){
        Player winningPlayer = new Player();
        for(Player player : this.p){
            if(player.getNoTiles() > winningPlayer.getNoTiles()){
                winningPlayer = player;
            }
        }
        return winningPlayer;
    }

    public char play(int[] pos, String sId){
        if(!isValid(pos, sId)) return ' ';
        this.s.setIt(active.getChar(), pos[0], pos[1]);
        turn++;
        
        if(end()){
            System.exit(0);
        }
        
        this.active = p.get( turn % p.size() );

        return active.getChar();
    }

    public Player getActive() {
        return this.active;
    }

    public State getState() {
        return this.s;
    }

    public Location getLocation(int x, int y) {
        return ((World)s).getLocation(x, y);
    }

    public int getMaxPlayers() {
        return MAX_PLAYERS;
    }

    public int getPlayerCount() {
        return p.size();
    }

    public ArrayList<Player> getPlayers(){
        return this.p;
    }

    public int getTurn(){
        return this.turn;
    }


}
