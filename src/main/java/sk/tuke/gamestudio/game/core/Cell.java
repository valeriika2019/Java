package sk.tuke.gamestudio.game.core;
public class Cell {
    private Symbol symbols;
    private int x;
    private int y;


    public Symbol getSymbols() {
        return symbols;
    }

    public void setSymbols(Symbol symbols) {
        this.symbols = symbols;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }


}