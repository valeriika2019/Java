package sk.tuke.gamestudio.game.core;

public class Game {
    private final String name = "Dice Merge";
    private Board board;
    private Player player;
    private WoodDice woodDice;
    private GameState gameState;
    private Cell[] startCubes;


    public Game() {
        this.player = new Player("Player1");
        this.woodDice = new WoodDice();
        this.board = new Board();
    }

    public void startGame() {
        board.initializeBoard();
        this.gameState = GameState.PLAYING;
    }


    public void endGame() { // jeho inicializacia bude v checkBoard

    }

    public void restartGame() {
        board.initializeBoard();
        player.resetScore();
        gameState = GameState.PLAYING;
    }

    public void makeMove(Cell[] cells, int posX, int posY) throws CellAlreadyContainSymbolException {
        if (cells.length == 1) {
            if (board.isFree(posX, posY))
                board.putSymbol(cells[0].getSymbols(), posX, posY);
            else throw new CellAlreadyContainSymbolException("This Cell already contains a symbol! ");
        }

        else if (cells.length == 2) {

            Cell first;
            Cell second;
            if (cells[0].getX() == 0 && cells[0].getY() == 0) {
                first = cells[0];
                second = cells[1];
            }
            else {
                first = cells[1];
                second = cells[0];
            }

            int x2 = second.getX();
            int y2 = second.getY();

            if (board.isFree(posX, posY) && board.isFree(posX + x2, posY + y2)) {
                board.putSymbol(first.getSymbols(), posX, posY);
                board.putSymbol(second.getSymbols(), posX + x2, posY + y2);
            } else throw new CellAlreadyContainSymbolException("At least one of the Cells already contains a symbol!");
        }

        // якщо виконання метода дійшло сюди, це означає що стартові кубики розміщено на дошку, і тепер необхідно перевірити:

        // 2) поле, чи потрібно там обєднати сусідні кубики
        // але так як checkBoard проходить перевірку лише один раз і обєднує лише один раз
        // нам потрібно побудувати цикл
        // умова зупинки циклу - метод checkBoard повертає 0, це означає що немає кубиків які треба обєднувати
        // але так як метод має відпрацювати хоча б раз, використовуєм do .. while




        int tmpScore;
        int score = 0;
        int iterationCounter = 0;
        do {
            iterationCounter++;
            tmpScore = board.checkBoard();
            score += tmpScore * iterationCounter;
        } while (tmpScore != 0);

        player.addToScore(score);

        // todo 1) чи гра не закінчена
        if(!board.hasAtLeastOneFreeCell()){
            gameState=GameState.FAILED;
            System.out.println("No space left");
        }
    }

    public void rotateStartCubesLeft() {
        if (startCubes[0].getX()==0 && startCubes[0].getY()==0) {
            if (startCubes[1].getX()==1 && startCubes[1].getY()==0) {
                startCubes[0].setX(0);
                startCubes[0].setY(1);
                startCubes[1].setX(0);
                startCubes[1].setY(0);
            }
            else if (startCubes[1].getX()==0 && startCubes[1].getY()==1) {
                startCubes[0].setX(0);
                startCubes[0].setY(0);
                startCubes[1].setX(1);
                startCubes[1].setY(0);
            }
        }
        else if (startCubes[1].getX()==0 && startCubes[1].getY()==0) {
            if(startCubes[0].getX()==0 && startCubes[0].getY()==1) {
                startCubes[0].setX(1);
                startCubes[0].setY(0);
                startCubes[1].setX(0);
                startCubes[1].setY(0);
            }
            else if (startCubes[0].getX()==1 && startCubes[0].getY()==0) {
                startCubes[0].setX(0);
                startCubes[0].setY(0);
                startCubes[1].setX(0);
                startCubes[1].setY(1);
            }
        }
    }

    public void getRandomStartCubes() {
        startCubes = getWoodDice().getStartCubes(board.checkTwoFreeCellsExists());
    }

    public GameState getCurrentState() {
        return gameState;
    }

    public Board getBoard() {
        return board;
    }

    public WoodDice getWoodDice() {
        return woodDice;
    }

    public Player getPlayer() {
        return player;
    }

    public Cell[] getStartCubes() {
        return startCubes;
    }

    public GameState getState() {
        return gameState;
    }

    public String getName() {
        return this.name;
    }
}
