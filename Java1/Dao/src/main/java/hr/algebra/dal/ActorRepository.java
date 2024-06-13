/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Actor;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ana
 */
public interface ActorRepository {
    int createActor(int id, Actor actor) throws Exception;
    void createNewActor(String actorName) throws Exception;
    void updateActor(int idActor, String ActorName) throws Exception;
    void deleteActor(int id) throws Exception;
    Optional<Actor> selectActor(int id) throws Exception;
    Optional<Actor> selectActorName(Actor actor) throws Exception;
    List<Actor> selectActors(int selectedMovieId) throws Exception;
    List<Actor> selectAllActors() throws Exception;
    int actorMovieRelated(int idActor) throws Exception;
    
}
