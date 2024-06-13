/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.ActorRepository;
import hr.algebra.model.Actor;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;


public class SqlActorRepository implements ActorRepository {
    private static final String ID_ACTOR = "IDActor";
    private static final String ACTOR_NAME = "ActorName";
    private static final String CREATE_ACTOR = "{ CALL createActor (?,?,?) }";
    private static final String CREATE_NEW_ACTOR = "{ CALL createNewActor (?) }";
    private static final String UPDATE_ACTOR = "{ CALL updateActor (?,?) }";
    private static final String DELETE_ACTOR = "{ CALL deleteActor (?) }";
    private static final String SELECT_ACTOR = "{ CALL selectActor (?) }";
    private static final String SELECT_ACTOR_NAME = "{ CALL selectActorName (?) }";
    private static final String SELECT_ACTORS = "{ CALL selectActors (?) }";
    private static final String SELECT_ALL_ACTORS = "{ CALL selectAllActors  }";
    private static final String ACTOR_MOVIE_RELATION = "{ CALL actorMovieRelation (?,?) }";

    @Override
    public int createActor(int id, Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_ACTOR)) {
            stmt.setInt(1, id);
            stmt.setString(2, actor.getActorName());
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public void createNewActor(String actorName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_NEW_ACTOR)) {
            stmt.setString(1, actorName);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateActor(int idActor, String ActorName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_ACTOR)) {
            stmt.setInt(1, idActor);
            stmt.setString(2, ActorName);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ACTOR)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Actor> selectActor(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ACTOR)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTOR_NAME)
                    ));
                }
            }
        }

        return Optional.empty();
    }

    @Override
    public Optional<Actor> selectActorName(Actor actor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(SELECT_ACTOR_NAME)) {
            stmt.setString(1, actor.getActorName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTOR_NAME)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Actor> selectActors(int selectedMovieId) throws Exception {
        List<Actor> actorsForSelectedMovie = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(SELECT_ACTORS)) {
            stmt.setInt(1, selectedMovieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    actorsForSelectedMovie.add(new Actor(
                            rs.getInt(ID_ACTOR),
                            rs.getString(ACTOR_NAME)
                    ));
                }
            }
        }
        return actorsForSelectedMovie;
    }

    @Override
    public List<Actor> selectAllActors() throws Exception {
        List<Actor> actors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ALL_ACTORS);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                actors.add(new Actor(
                        rs.getInt(ID_ACTOR),
                        rs.getString(ACTOR_NAME)
                ));
            }
            return actors;
        }
    }

    @Override
    public int actorMovieRelated(int idActor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(ACTOR_MOVIE_RELATION)) {
            stmt.setInt(1, idActor);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }
    
}
