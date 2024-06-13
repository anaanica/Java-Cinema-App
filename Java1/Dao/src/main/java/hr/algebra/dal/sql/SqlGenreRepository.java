/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.GenreRepository;
import hr.algebra.model.Genre;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;


public class SqlGenreRepository implements GenreRepository {
    private static final String ID_GENRE = "IDGenre";
    private static final String GENRE_NAME = "GenreName";
    private static final String CREATE_GERNE = "{ CALL createGenre (?,?,?) }";
    private static final String SELECT_GENRE = "{ CALL selectGenre (?) }";
    private static final String SELECT_GENRES = "{ CALL selectGenres (?) }";
    private static final String SELECT_ALL_GENRES = "{ CALL selectAllGenres }";

    @Override
    public int createGenre(int id, Genre genre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_GERNE)) {
            stmt.setInt(1, id);
            stmt.setString(2, genre.getGenreName());
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(3);
        }
    }

    @Override
    public Optional<Genre> selectGenre(Genre genre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(SELECT_GENRE)) {
            stmt.setString(1, genre.getGenreName());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(GENRE_NAME)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Genre> selectGenres(int selectedMovieId) throws Exception {
        List<Genre> genresForSelectedMovie = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(SELECT_GENRES)) {
            stmt.setInt(1, selectedMovieId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    genresForSelectedMovie.add(new Genre(
                            rs.getInt(ID_GENRE),
                            rs.getString(GENRE_NAME)
                    ));
                }
            }
        }
        return genresForSelectedMovie;
    }

    @Override
    public List<Genre> selectAllGenres() throws Exception {
        List<Genre> genres = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_ALL_GENRES);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                genres.add(new Genre(
                        rs.getInt(ID_GENRE),
                        rs.getString(GENRE_NAME)
                ));
            }
            return genres;
        }
    }
}
