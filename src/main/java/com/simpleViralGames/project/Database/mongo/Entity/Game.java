package com.simpleViralGames.project.Database.mongo.Entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Game")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Game {

    @Id
    String id;

    @Field("name")
    String name;

    @Field("url")
    String url;

    @Field("author")
    String author;

    @Field("published_date")
    Long created;

    @Field("deleted")
    Integer deleted;

    @Field("updated_date")
    Long updated;

    @Field("updated_by")
    String updatedBy;

    public void create(String createdBy) {
        setCreated(System.currentTimeMillis());
        setAuthor(createdBy);
        setUpdated(System.currentTimeMillis());
        setUpdatedBy(updatedBy);
        setDeleted(0);
    }

    public void update(String updatedBy) {
        setUpdated(System.currentTimeMillis());
        setUpdatedBy(updatedBy);
        setDeleted(0);
    }
}
