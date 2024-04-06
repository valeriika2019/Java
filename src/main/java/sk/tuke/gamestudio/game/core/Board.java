package sk.tuke.gamestudio.game.core;

import java.util.ArrayList;
import java.util.HashSet;



public class Board {
    private final Cell[][] board;
    public static final int ROWS = 5;
    public static final int COLUMNS = 5;

    public Board() {
        this.board = new Cell[ROWS][COLUMNS];
    }

    public int getSymbolNumberByCoordinates(int i, int j) {
        return Symbol.fromEnumToInt(board[i][j].getSymbols());
    }

    public Cell[][] getBoard() {
        return board;
    }

    public void initializeBoard() {  // Метод для ініціалізації

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {
                board[i][j] = new Cell();
                board[i][j].setSymbols(Symbol.EMPTY);
                board[i][j].setX(i);
                board[i][j].setY(j);
            }
        }
    }


    public ArrayList<Cell> findNeighborhutWithSameSymbol(Cell cell) {
        ArrayList<Cell> susede = new ArrayList<>();
        int cellX = cell.getX();
        int cellY = cell.getY();

        Cell susedZlava;
        Cell susedSprava;
        Cell susedHore;
        Cell susedDole;

        if(cellX > 0 ) {
            susedZlava = board[cellX - 1][cellY];
            if (susedZlava.getSymbols() == cell.getSymbols())
                susede.add(susedZlava);
        }
        if(cellX + 1 < ROWS) {
            susedSprava = board[cellX + 1][cellY];
            if (susedSprava.getSymbols() == cell.getSymbols())
                susede.add(susedSprava);
        }
        if(cellY > 0) {
            susedHore = board[cellX][cellY - 1];
            if (susedHore.getSymbols() == cell.getSymbols())
                susede.add(susedHore);
        }
        if(cellY + 1 < COLUMNS) {
            susedDole = board[cellX][cellY + 1];
            if (susedDole.getSymbols() == cell.getSymbols())
                susede.add(susedDole);
        }
        return susede; // список сусідів
    }


    public int checkBoard() {  // перевіряємо дошку для того щоб обєднати клітинки
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLUMNS; j++) {

                // Якщо символ клітинки не порожній - необхідно спробувати знайти сусідів з таким же символом
                if (board[i][j].getSymbols() != Symbol.EMPTY) {


                    // для зручності знайдену не пусту клітинку назвемо cellWithSomeSymbol
                    Cell cellWithSomeSymbol = board[i][j];
                    ArrayList<Cell> toCheck = new ArrayList<>();
                    HashSet<Cell> toMerge = new HashSet<>();
                    toCheck.add(cellWithSomeSymbol);
                    toMerge.add(cellWithSomeSymbol);

                    while (!toCheck.isEmpty()) {
                        cellWithSomeSymbol = toCheck.get(0);
                        // знаходимо сусідів з тим же символом
                        ArrayList<Cell> foundedNeighborhoods = findNeighborhutWithSameSymbol(cellWithSomeSymbol);

                        // проходимо по списку foundedNeighborhoods. Якщо елемент списку вже міститься в toMerge - до списку toCheck його не додаєм
                        // це необхідно робити, для того щоб у список для перевірки не потрапляли одні й ті самі клітинки по декілька разів, тому що в такому разі цикл ніколи не закінчить свою роботу
                        for (Cell c : foundedNeighborhoods) {
                            if (toMerge.contains(c))
                                continue;
                            else {
                                toCheck.add(c);
                                toMerge.add(c);
                            }
                        }
                        // ми перевірили клітинку cellWithSomeSymbol, а отже видаляєм її зі списку toCheck
                        toCheck.remove(cellWithSomeSymbol);
                    }

                    // якщо знайдено 3 і більше - потрібно їх обєднати
                    if (toMerge.size() > 2)
                        return mergeCells(toMerge);
                }
                // додати перевірку чи всі клітинки зайняті, якщо так то END  і виводимо повідомлення No space left
            }
        }

        return 0;
    }

    // метод для обєднання кубиків
    private int mergeCells(HashSet<Cell> toMerge) {
        ArrayList<Cell> cellsToMerge = new ArrayList<>(toMerge);

        Symbol symbol;
        int symbolScore;
        int count = cellsToMerge.size();

        int x = cellsToMerge.get(0).getX();
        int y =cellsToMerge.get(0).getY();
        symbol = cellsToMerge.get(0).getSymbols();

        for(Cell c : cellsToMerge) {
            board[c.getX()][c.getY()].setSymbols(Symbol.EMPTY);
        }
        // create a higher rank cube
        Symbol nextRankSymbol = Symbol.nextRank(symbol);
        board[x][y].setSymbols(nextRankSymbol);

        symbolScore = Symbol.getSymbolScore(symbol);
        return symbolScore * count;
    }




    public boolean checkTwoFreeCellsExists() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Cell currentCell = board[row][col];
                if (currentCell.getSymbols() == Symbol.EMPTY) {
                    ArrayList<Cell> neighbors = findNeighborhutWithSameSymbol(currentCell);
                    if (neighbors.size() >= 1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // метод який перевіряє чи клітинка порожня
    public boolean isFree(int posX, int posY) {
        return board[posX][posY].getSymbols() == Symbol.EMPTY;
    }

    // метод який встановлює новий символ для клітинки
    public void putSymbol(Symbol symbol, int posX, int posY) {
        board[posX][posY].setSymbols(symbol);
    }

    public int getROWS() {
        return ROWS;
    }

    public int getCOLUMNS() {
        return COLUMNS;
    }

    public boolean hasAtLeastOneFreeCell() {
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLUMNS; col++) {
                Cell currentCell = board[row][col];
                if (currentCell.getSymbols() == Symbol.EMPTY) {
                    return true;
                }
            }
        }
        return false;
    }
}
