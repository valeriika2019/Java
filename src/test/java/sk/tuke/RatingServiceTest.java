package sk.tuke;

import org.junit.jupiter.api.Test;
import sk.tuke.gamestudio.entity.Rating;
import sk.tuke.gamestudio.service.RatingService;
import sk.tuke.gamestudio.service.RatingServiceJDBC;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RatingServiceTest {
    private RatingService createService() {
        return new RatingServiceJDBC();
    }



    @Test
    public void testGetRating() {
        RatingService service = createService();
        service.reset();
        Rating rating1 = new Rating("Dice Merge", "Player1", 5, new Date());
        service.setRating(rating1);

        int rating = service.getRating("Dice Merge","Player1");

        assertEquals(5, rating);
    }

    @Test
    public void testAverageRating() {
        RatingService service = createService();
        service.reset();
        Rating rating1 = new Rating("Dice Merge", "Player1", 5, new Date());
        Rating rating2 = new Rating("Dice Merge", "Player2", 4, new Date());
        Rating rating3 = new Rating("Dice Merge", "Player3", 3, new Date());
        Rating rating4 = new Rating("Dice Merge", "Player4", 2, new Date());
        Rating rating5 = new Rating("Dice Merge", "Player4", 4, new Date());

        service.setRating(rating1);
        service.setRating(rating2);
        service.setRating(rating3);
        service.setRating(rating4);
        service.setRating(rating5);

        assertEquals(4, service.getAverageRating("Dice Merge"));
    }
}
