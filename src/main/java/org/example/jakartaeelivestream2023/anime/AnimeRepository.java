package org.example.jakartaeelivestream2023.anime;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@ApplicationScoped
public class AnimeRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Anime> findAll() {
        return this.entityManager
                .createQuery("SELECT a FROM Anime a", Anime.class)
                .getResultList();
    }

}
