/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal.sql;

import hr.algebra.dal.MovieRepository;
import hr.algebra.model.Movie;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Types;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;


public class SqlMovieRepository implements MovieRepository {
    private static final String ID_MOVIE = "IDMovie";
    private static final String TITLE = "Title";
    private static final String PUBLISHED = "Published";
    private static final String DESCRIPTION = "Description";
    private static final String ORIGINAL_TITLE = "OriginalTitle";
    private static final String DURATION = "Duration";
    private static final String POSTER_PATH = "PosterPath";
    private static final String LINK = "Link";
    private static final String EXPECTED = "Expected";
    private static final String CREATE_MOVIE = "{ CALL createMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String UPDATE_MOVIE = "{ CALL updateMovie (?,?,?,?,?,?,?,?,?) }";
    private static final String DELETE_MOVIE = "{ CALL deleteMovie (?) }";
    private static final String SELECT_MOVIE = "{ CALL selectMovie (?) }";
    private static final String SELECT_MOVIES = "{ CALL selectMovies }";
    private static final String DELETE_ALL_MOVIES = "{ CALL deleteAllMoviesFromDatabase }";
    private static final String SET_MOVIE_DIRECTORS = "{ CALL setMovieDirectors (?,?) }";
    private static final String SET_MOVIE_ACTORS = "{ CALL setMovieActors (?,?) }";
    private static final String SET_MOVIE_GENRES = "{ CALL setMovieGenres (?,?) }";
    private static final String MOVIE_REMOVE_DIRECTORS = "{ CALL removeMovieDirectors (?) }";
    private static final String MOVIE_REMOVE_ACTORS = "{ CALL removeMovieActors (?) }";
    private static final String MOVIE_REMOVE_GENRE = "{ CALL removeMovieGenres (?) }";

    @Override
    public int createMovie(Movie movie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(CREATE_MOVIE)) {
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setString(3, movie.getDescription());
            stmt.setString(4, movie.getOriginalTitle());
            stmt.setString(5, movie.getDuration());
            stmt.setString(6, movie.getPosterPath());
            stmt.setString(7, movie.getLink());
            stmt.setString(8, movie.getExpectedDate().format(Movie.DATE_FORMAT));
            stmt.registerOutParameter(9, Types.INTEGER);
            stmt.executeUpdate();
            return stmt.getInt(9);
        }
    }

    @Override
    public void updateMovie(int id, Movie data) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(UPDATE_MOVIE)) {
            stmt.setString(1, data.getTitle());
            stmt.setString(2, data.getPublishedDate().format(Movie.DATE_FORMATTER));
            stmt.setString(3, data.getDescription());
            stmt.setString(4, data.getOriginalTitle());
            stmt.setString(5, data.getDuration());
            stmt.setString(6, data.getPosterPath());
            stmt.setString(7, data.getLink());
            stmt.setString(8, data.getExpectedDate().format(Movie.DATE_FORMAT));
            stmt.setInt(9, id);
            stmt.executeUpdate();
        }
    }
    
    @Override
    public void deleteMovie(int idMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_MOVIE)) {
            stmt.setInt(1, idMovie);
            stmt.executeUpdate();
        }
    }

    @Override
    public Optional<Movie> selectMovie(int id) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection conn = dataSource.getConnection();
                CallableStatement stmt = conn.prepareCall(SELECT_MOVIE)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Optional.of(new Movie(
                            rs.getInt(ID_MOVIE),
                            rs.getString(TITLE),
                            LocalDateTime.parse(rs.getString(PUBLISHED), Movie.DATE_FORMATTER),
                            rs.getString(DESCRIPTION),
                            rs.getString(ORIGINAL_TITLE),
                            rs.getString(DURATION),
                            rs.getString(POSTER_PATH),
                            rs.getString(LINK),
                            LocalDate.parse(rs.getString(EXPECTED), Movie.DATE_FORMAT)
                    ));
                }
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Movie> selectMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SELECT_MOVIES);
                ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                movies.add(new Movie(
                        rs.getInt(ID_MOVIE),
                        rs.getString(TITLE),
                        LocalDateTime.parse(rs.getString(PUBLISHED), Movie.DATE_FORMATTER),
                        rs.getString(DESCRIPTION),
                        rs.getString(ORIGINAL_TITLE),
                        rs.getString(DURATION),
                        rs.getString(POSTER_PATH),
                        rs.getString(LINK),
                        LocalDate.parse(rs.getString(EXPECTED), Movie.DATE_FORMAT)
                ));
            }
            return movies;
        }
    }

    @Override
    public void deleteAllMoviesFromDatabase() throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(DELETE_ALL_MOVIES)) {
            stmt.executeUpdate();
        }
    }

    @Override
    public void setMovieDirectors(int idMovie, int idDirector) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SET_MOVIE_DIRECTORS)) {
            stmt.setInt(1, idMovie);
            stmt.setInt(2, idDirector);
            stmt.executeUpdate();
        }
    }

    @Override
    public void setMovieActors(int idMovie, int idActor) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SET_MOVIE_ACTORS)) {
            stmt.setInt(1, idMovie);
            stmt.setInt(2, idActor);
            stmt.executeUpdate();
        }
    }

    @Override
    public void setMovieGenres(int idMovie, int idGenre) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(SET_MOVIE_GENRES)) {
            stmt.setInt(1, idMovie);
            stmt.setInt(2, idGenre);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeMovieActors(int idMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(MOVIE_REMOVE_ACTORS)) {
            stmt.setInt(1, idMovie);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeMovieDirectors(int idMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(MOVIE_REMOVE_DIRECTORS)) {
            stmt.setInt(1, idMovie);
            stmt.executeUpdate();
        }
    }

    @Override
    public void removeMovieGenres(int idMovie) throws Exception {
        DataSource dataSource = DataSourceSingleton.getInstance();
        try (Connection con = dataSource.getConnection();
                CallableStatement stmt = con.prepareCall(MOVIE_REMOVE_GENRE)) {
            stmt.setInt(1, idMovie);
            stmt.executeUpdate();
        }
    }
    
}
