package sk.tuke.gamestudio;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sk.tuke.gamestudio.game.core.Game;
import sk.tuke.gamestudio.game.ui.ConsoleUI;
import sk.tuke.gamestudio.service.*;

@SpringBootApplication
public class ZaverApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZaverApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(ConsoleUI ui) {
        return args -> ui.play();
    }

    @Bean
    public ConsoleUI consoleUI() {
        return new ConsoleUI(new Game());
    }

    @Bean
    public ScoreService scoreService() {
        //return new ScoreServiceJDBC();
        return new ScoreServiceJPA();
        //return new ScoreServiceRestClient();
    }
    @Bean
    public RatingService retingService() {
        return new RatingServiceJPA();
        //return new RatingServiceRestClient();
    }

    @Bean
    public CommentService commentService() {
        return new CommentServiceJPA();
        //return new CommentServiceRestClient();
    }
}
