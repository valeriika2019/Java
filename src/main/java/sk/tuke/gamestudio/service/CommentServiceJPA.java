package sk.tuke.gamestudio.service;

import jakarta.transaction.Transactional;
import sk.tuke.gamestudio.entity.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
public class CommentServiceJPA implements CommentService {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void addComment(Comment comment){
         entityManager.persist(comment);

    }

    @Override
    public List<Comment> getComments(String game){
        return entityManager.createQuery("select c from Comment c where c.game = :game order by c.commentedOn desc")
                .setParameter("game",game)
                .getResultList();
    }

    @Override
    public void reset(){
        entityManager.createNativeQuery("delete from comment").executeUpdate();
    }
}