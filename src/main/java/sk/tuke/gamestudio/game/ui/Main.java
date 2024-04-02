package sk.tuke.gamestudio.game.ui;

import sk.tuke.gamestudio.game.core.Game;

public class Main {
    public static void main(String[] args) {
        ConsoleUI ui = new ConsoleUI(new Game());
        ui.runMenu();
    }
}
