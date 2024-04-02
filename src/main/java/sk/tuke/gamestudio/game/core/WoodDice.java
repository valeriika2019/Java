package sk.tuke.gamestudio.game.core;

import java.util.Random;

public class WoodDice {
    private final int SIDES = 6;  // грані кубика
    private Random random;

    public WoodDice(){
        this.random=new Random();
    }

    public Symbol rollTheDice(){

        return Symbol.getSymbolFromInt(random.nextInt(SIDES) + 1);
    }



    public Cell[] getStartCubes(boolean hasTwoFreeNeighboursCells) {
        // Перевірка, чи існують вільні клітинки на дошці
        if (!hasTwoFreeNeighboursCells) {
            Cell[] cells = new Cell[1];
            cells[0] = new Cell();
            cells[0].setSymbols(rollTheDice());
            return cells;
        }

        // Якщо є -> генеруємо випадкову кількість кубиків (1 або 2)
        Cell[] cells = new Cell[random.nextInt(2) + 1];

        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell();
            cells[i].setSymbols(rollTheDice());
            if (i == 1 ) {
                cells[i].setX(1);
                cells[i].setY(0);
            }
        }
        return cells;
    }

}
