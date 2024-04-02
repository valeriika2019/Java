package sk.tuke.gamestudio.service;

import sk.tuke.gamestudio.entity.Rating;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.List;

@Transactional
public class RatingServiceJPA implements RatingService {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void setRating(Rating rating) {
        entityManager.merge(rating);

    }

    @Override
    public int getAverageRating(String game) {
        List<Rating> ratings = entityManager.createQuery("select r from Rating r where r.game = :game")
                .setParameter("game",game)
                .getResultList();
        if(ratings.isEmpty()){
            return 0;
        }
        int sum = 0;
        for(Rating r: ratings){
            sum += r.getRating();
        }
        return sum/ratings.size();
    }

    @Override
    public int getRating(String game, String player) {
        Rating rating = entityManager.createQuery("select r from Rating r where r.game = :game and r.player = :player", Rating.class)
                .setParameter("game", game)
                .setParameter("player", player)
                .getSingleResult();

        if (rating != null) {
            return rating.getRating();
        } else {
            return 0;
        }
    }

    @Override
    public void reset() {
        entityManager.createQuery("delete from Rating").executeUpdate();
    }
}
