package sk.tuke.gamestudio.game.ui;

import org.springframework.beans.factory.annotation.Autowired;
import sk.tuke.gamestudio.entity.Comment;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.entity.Score;
import sk.tuke.gamestudio.game.core.*;
import sk.tuke.gamestudio.service.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private final Game game;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    private ScoreService scoreService;
    @Autowired
    private RatingService ratingService;
    @Autowired
    private CommentService commentService;

    public ConsoleUI(Game game) {
        this.game = game;
    }


    //todo менюшка по бажанню
    public void runMenu() {
        boolean running = true;
        while (running) {
            printMenuOptions();
            int choice = getUserChoice();
            switch (choice){
                case 1:
                    play();
                    break;
                case 2:
                    seeRecords();
                    break;
                case 3:
                    showSettings();
                    break;
                case 4:
                    showRatingMenu();
                    break;
                case 5:
                    running = false;
                    System.out.println("Exiting the game.Goodbye!"+Color.TEXT_YELLOW);
                    break;
                default:
                    System.out.println(Color.TEXT_RED+"Invalid choice.Please enter a number from the menu.");
                    break;

            }
        }

    }

    private void showRatingMenu() {
        boolean running = true;
        while (running) {
            printRatingMenuOptions();
            int choice = getUserChoice();
            switch (choice){
                case 1:
                    System.out.println("Input you rating for this game: ");
                    int rating = 0;
                    try {
                        rating = Integer.parseInt(scanner.nextLine());
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                    if (rating < 1 || rating > 5) {
                        System.err.println("Wrong input, please enter value from 1 to 5!");
                        break;
                    }
                    Rating ratingObj = new Rating(game.getName(),game.getPlayer().getName(),rating,new Date());
                    ratingService.setRating(ratingObj);
                    break;
                case 2:
                    running = false;
                    break;
            }
        }
    }

    private void printRatingMenuOptions() {
        int rating = ratingService.getRating(game.getName(),game.getPlayer().getName());
        if (rating == 0) {
            System.out.println("You haven't rate this game");
        }
        else {
            System.out.println("You rated this game "+rating);
        }

        System.out.println("1. Set rating for this game");
        System.out.println("2. Back to menu");
    }

    private void showSettings() {
    }

    private void seeRecords() {
        ArrayList<Score> top = (ArrayList<Score>) scoreService.getTopScores(game.getName());
        if(top.size() == 0){
            System.out.println("There is no records!");
        }
        else{
            System.out.println(top);
        }
    }





    private void printMenuOptions() {
        System.out.println(Color.TEXT_PURPLE+"-------Menu-------");
        System.out.println(Color.TEXT_GREEN+"1. Start new game");
        System.out.println(Color.TEXT_YELLOW+"2.See records");
        System.out.println(Color.TEXT_RED+"3.Settings");
        System.out.println(Color.TEXT_RED+"4.Rating Menu");
        System.out.println(Color.TEXT_BLUE+"5.Exit\n");
        System.out.println(Color.TEXT_PURPLE+"Choose an option: ");

    }
    private int getUserChoice() {
        int choice;
        try{
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e){
            choice = -1;
        }
        return choice;
    }

    public void play() {
        game.startGame();
        do {
            printBoard();
            game.getRandomStartCubes();
            printStartCubes();
            handleInput("makeMove");
        } while (game.getState() == GameState.PLAYING);

        printBoard();

        if (game.getState() == GameState.FAILED) {
            System.out.println(Color.TEXT_RED+"Oh, no!" + game.getPlayer().getName()+ " Game failed!");
            System.out.println(Color.TEXT_YELLOW+"Your score: "+game.getPlayer().getScore());
            //game.endGame();
            scoreService.addScore(new Score(game.getName(),game.getPlayer().getName(),game.getPlayer().getScore(),new Date()));

            handleInput("restart");
        }
    }

    private void printBoard() {
        System.out.println("------------------------------");
        System.out.println(Color.TEXT_GREEN + "Player: " + game.getPlayer().getName());
        System.out.println(game.getPlayer().getScore() + " points!");
        System.out.println(Color.TEXT_PURPLE + "------------------------------" + Color.TEXT_WHITE);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < Board.COLUMNS * 4 + 1; i++) {
            result.append("-");
        }
        result.append("\n");


        for (int i = 0; i < Board.ROWS; i++) {
            result.append("|"); // Ліва рамка
            for (int j = 0; j < Board.COLUMNS; j++) {
                int symbolNumber = game.getBoard().getSymbolNumberByCoordinates(j, i);
                String backgroundColor = getBackgroundColor(symbolNumber);
                String textColor = getTextColor(symbolNumber);
                result.append(backgroundColor).append(textColor).append(" ").append(symbolNumber).append(" ").append(Color.ANSI_RESET).append("|");
            }
            result.append("\n");

            // Виведення рамки між рядками
            for (int k = 0; k < Board.COLUMNS * 4 + 1; k++) {
                result.append("-");
            }
            result.append("\n");
        }

        System.out.println(result);
    }

    private String getTextColor(int symbolNumber) {
        return Color.TEXT_BLACK;
    }

    private String getBackgroundColor(int symbolNumber) {
        switch (symbolNumber) {
            case 1:
                return Color.BACKGROUND_RED;
            case 2:
                return Color.BACKGROUND_GREEN;
            case 3:
                return Color.BACKGROUND_YELLOW;
            case 4:
                return Color.BACKGROUND_WHITE;
            case 5:
                return Color.BACKGROUND_PURPLE;
            case 6:
                return Color.BACKGROUND_CYAN;
            default:
                return Color.BACKGROUND_BLUE;
        }
    }


    private void handleInput(String type) {
        switch (type) {
            case "restart" : {
                System.out.println("U wanna play again? Y/N" +Color.TEXT_PURPLE);
                String choice = scanner.nextLine();
                if(choice.equals("Y")) {
                    game.restartGame();
                    play();
                }
                else if(choice.equals("N")) {
                    System.out.println("Thank you for playing!" +Color.TEXT_YELLOW);
                }
                else {
                    System.err.println("Invalid input.Please enter 'Y' to play again on 'N' to exit.");
                    handleInput("restart");
                }
                System.out.println("Do you want to leave a comment? (Y/N)");
                String commentChoice = scanner.nextLine();
                if (commentChoice.equals("Y")) {
                    leaveComment();
                }
                break;
            }
            case "makeMove" : {
                System.out.println("Enter coordinates to place the starting cubes (x,y) or rotateLeft to rotate cubes or rotateRight to rotate cubes: ");
                String cs = scanner.nextLine();
                if (cs.equals("menu")) {

                }
                else if (cs.equals("rotateLeft")) {
                    if(game.getStartCubes().length == 2) {
                        game.rotateStartCubesLeft();
                        printStartCubes();
                        handleInput("makeMove");
                    }

                    else {
                        System.err.println("There is only 1 cube, you can't rotate it!");
                        System.out.println("Starting cubes:");
                        printStartCubes();
                        handleInput("makeMove");
                    }
                } else if(cs.equals("rotateRight")){
                    if(game.getStartCubes().length == 2){
                        game.rotateStartCubesRight();
                        printStartCubes();
                        handleInput("makeMove");
                    } else {
                        System.out.println("There is only 1 cube, you can't rotate it!");
                        System.out.println("Starting cubes:");
                        printStartCubes();
                        handleInput("makeMove");
                    }
                }
                else if (cs.contains(",")) {
                    try {
                        String [] coordinates = cs.split(",");
                        game.makeMove(game.getStartCubes(), Integer.parseInt(coordinates[0]), Integer.parseInt(coordinates[1]));
                    } catch (CellAlreadyContainSymbolException e) {
                        System.err.println("The selected position is already occupied. Please choose another position.");
                        System.out.println("Starting cubes:");
                        printStartCubes();
                        handleInput("makeMove");
                    }
                    catch (ArrayIndexOutOfBoundsException e) {
                        System.err.println("Invalid input: cubes getting out of Board. Please enter valid coordinates.");
                        System.out.println("Starting cubes:");
                        printStartCubes();
                        handleInput("makeMove");
                    }
                    catch (NumberFormatException e) {
                        System.err.println("Invalid input: Please enter valid coordinates.");
                        System.out.println("Starting cubes:");
                        printStartCubes();
                        handleInput("makeMove");
                    }
                }
                else {
                    System.out.println(Color.TEXT_RED+"Unknown Input: Please try again."+Color.ANSI_RESET);
                    printStartCubes();
                    handleInput("makeMove");
                }
                break;
            }
        }
    }

    private void leaveComment() {
        try {
            System.out.println("Enter your comment:");
            String commentText = scanner.nextLine();
            Comment comment = new Comment(game.getPlayer().getName(), game.getName(), commentText, new Date());
            if (comment.getCommentedOn() == null) {
                comment.setCommentedOn(new Date()); // Ініціалізуємо дату, якщо вона null
            }
            commentService.addComment(comment);
            System.out.println("Thank you for your comment!");
        } catch (CommentException e) {
            System.err.println("Error while adding comment: " + e.getMessage());
        }
    }



    private void printStartCubes() {
        Cell[] startCubes = game.getStartCubes();
        // коли стартовий кубик один
        if (startCubes.length == 1) {
            int num = Symbol.fromEnumToInt(startCubes[0].getSymbols());
            String bgColor = getBackgroundColor(num);

            System.out.println("-----\n|" + bgColor + " " + num + " " + Color.ANSI_RESET_BG + "|\n-----");
        }
        // коли стартових кубика два
        else {
            Cell first, second;
            if (startCubes[0].getX() == 0 && startCubes[0].getY() == 0) {
                first = startCubes[0];
                second = startCubes[1];
            }
            else {
                first = startCubes[1];
                second = startCubes[0];
            }
            int num1 = Symbol.fromEnumToInt(first.getSymbols());
            int num2 = Symbol.fromEnumToInt(second.getSymbols());

            String firstBgColor = getBackgroundColor(num1);
            String secondBgColor = getBackgroundColor(num2);

            if (second.getX() == 1) {
                System.out.println("---------\n|"+firstBgColor+" " + num1 + " "+Color.ANSI_RESET_BG+"|"+secondBgColor+" " + num2 + " "+Color.ANSI_RESET_BG+"|\n---------");
            }
            else {
                System.out.println("-----\n|" + firstBgColor + " " + num1 + " "+ Color.ANSI_RESET_BG + "|\n-----\n|" + secondBgColor +" "+ num2 + " "+Color.ANSI_RESET_BG+"|\n-----");
            }
        }
    }
}