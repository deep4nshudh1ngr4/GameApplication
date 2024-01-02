package com.simpleViralGames.project.Database.mongo.Dao;

import com.simpleViralGames.project.Database.mongo.Entity.Game;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameDao extends MongoRepository<Game, String> {

    Game findByNameAndDeleted(String name, Integer deleted);

    Game findByAuthorAndDeleted(String authorName, Integer deleted);
    List<Game> findAllByDeleted(Integer deleted);
}
