package com.example.huntinggame;

public class GameManager {

    private int colHaunted,rowHaunted;
    private int colHunter,rowHunter;
    private int colSnitch,rowSnitch;
    private boolean isEnd=false;
    private String direction="DOWN";
    private int maxRow;
    private int maxCol;
    private Score score;
    private int lives=3;

    public GameManager() {
        score=new Score();
    }
    public GameManager(int maxRow,int maxCol) {
        score=new Score();
        this.maxRow=maxRow;
        this.maxCol=maxCol;
    }
    public boolean ifACatchWhenUp(){
        return getRowHunter()==getRowHaunted()-1 && getColHunter()==getColHaunted();
    }

    public boolean ifACatchWhenDown(){
        return getRowHunter()==getRowHaunted()+1 && getColHunter()==getColHaunted();
    }
    public boolean ifACatchWhenLeft(){
        return getRowHunter()==getRowHaunted() && getColHunter()==getColHaunted()-1;
    }

    public boolean ifACatchWhenRight(){
        return getRowHunter()==getRowHaunted() && getColHunter()==getColHaunted()+1;
    }

    public int getScore() {
        return score.getNumOfScore();
    }

    public void setScore(int score) {
        this.score.setNumOfScore(score);
    }



    public int getMaxRow() {
        return maxRow;
    }

    public void setMaxRow(int maxRow) {
        this.maxRow = maxRow;
    }

    public int getMaxCol() {
        return maxCol;
    }

    public void setMaxCol(int maxCol) {
        this.maxCol = maxCol;
    }

    public boolean isEnd() {
        return isEnd;
    }

    public void setIsEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public int getColSnitch() {
        return colSnitch;
    }

    public void setColSnitch(int colSnitch) {
        this.colSnitch = colSnitch;
    }

    public int getRowSnitch() {
        return rowSnitch;
    }

    public void setRowSnitch(int rowSnitch) {
        this.rowSnitch = rowSnitch;
    }


    public boolean checkIfSnitchErased(){
        boolean answer=(getColSnitch()==getColHaunted() && getRowSnitch()==getRowHaunted())
                || (getRowSnitch()==getRowHunter() && getColSnitch()==getColHunter());
        return answer;
    }
    public int getLives() {
        return lives;
    }

    public void loseLives() {
        lives--;
    }




    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public int getColHaunted() {
        return colHaunted;
    }



    public void setColHaunted(int colHaunted) {
        this.colHaunted = colHaunted;
    }

    public int getRowHaunted() {
        return rowHaunted;

    }

    public int getColHunter() {
        return colHunter;
    }

    public void setColHunter(int colHunter) {
        this.colHunter = colHunter;
    }

    public int getRowHunter() {
        return rowHunter;
    }

    public void setRowHunter(int rowHunter) {
        this.rowHunter = rowHunter;
    }

    public void setRowHaunted(int rowHaunted) {
        this.rowHaunted = rowHaunted;
    }




}

