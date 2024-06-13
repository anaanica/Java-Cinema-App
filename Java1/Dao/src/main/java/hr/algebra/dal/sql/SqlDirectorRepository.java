/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.DirectorRepository;
import hr.algebra.model.Director;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;


public class SqlDirectorRepository implements DirectorRepository {
    private static final String ID_DIRECTOR = "IDDirector";
    private static final String DIRECTOR_NAME = "DirectorName";
    private static final String CREATE_DIRECTOR = "{ CALL createDirector (?,?,?) }";
    private static final String CREATE_NEW_DIRECTOR = "{ CALL createNewDirector (?) }";
    private static final String UPDATE_DIRECTOR = "{ CALL updateDirector (?,?) }";
    private static final String DELETE_DIRECTOR = "{ CALL deleteDirector (?) }";
    private static final String SELECT_DIRECTOR = "{ CALL selectDirector (?) }";
    private static final String SELECT_DIRECTORS = "{ CALL selectDirectors (?) }";
    private static final String SELECT_ALL_DIRECTORS = "{ CALL selectAllDirectors }";
    private static final String DIRECTOR_MOVIE_RELATION = "{ CALL directorMovieRelation (?,?) }";

    @Override
    public int createDirector(int id, Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_DIRECTOR)) {
            stmt.setInt(1, id);
            stmt.setString(2, director.getDirectorName());
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public void createNewDirector(String directorName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_NEW_DIRECTOR)) {
            stmt.setString(1, directorName);
            stmt.executeUpdate();
        }
    }

    @Override
    public void updateDirector(int idDirector, String directorName) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_DIRECTOR)) {
            stmt.setInt(1, idDirector);
            stmt.setString(2, directorName);
            stmt.executeUpdate();
        }
    }

    @Override
    public void deleteDirector(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_DIRECTOR)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Director> selectDirector(Director director) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(SELECT_DIRECTOR)) {

            stmt.setString(1, director.getDirectorName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(DIRECTOR_NAME)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Director> selectDirectors(int selectedMovieId) throws Exception {
        List<Director> directorsForSelectedMovie = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(SELECT_DIRECTORS)) {
            stmt.setInt(1, selectedMovieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    directorsForSelectedMovie.add(new Director(
                            rs.getInt(ID_DIRECTOR),
                            rs.getString(DIRECTOR_NAME)
                    ));
                }
            }
        }
        return directorsForSelectedMovie;
    }

    @Override
    public List<Director> selectAllDirectors() throws Exception {
        List<Director> directors = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ALL_DIRECTORS);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                directors.add(new Director(
                        rs.getInt(ID_DIRECTOR),
                        rs.getString(DIRECTOR_NAME)
                ));
            }
            return directors;
        }
    }

    @Override
    public int directorMovieRelated(int idDirector) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DIRECTOR_MOVIE_RELATION)) {
            stmt.setInt(1, idDirector);
            stmt.registerOutParameter(2, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(2);
        }
    }
    
}
