/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Movie;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ana
 */
public interface MovieRepository {
    int createMovie(Movie movie) throws Exception;
    void updateMovie(int id, Movie data) throws Exception;
    void deleteMovie(int idMovie) throws Exception;
    Optional<Movie> selectMovie(int id) throws Exception;
    List<Movie> selectMovies() throws Exception;
    void deleteAllMoviesFromDatabase() throws Exception;
    void setMovieDirectors(int idMovie, int idDirector) throws Exception;
    void setMovieActors(int idMovie, int idActor) throws Exception;
    void setMovieGenres(int idMovie, int idGenre) throws Exception;
    void removeMovieActors(int idMovie) throws Exception;
    void removeMovieDirectors(int idMovie) throws Exception;
    void removeMovieGenres(int idMovie) throws Exception;  
}
