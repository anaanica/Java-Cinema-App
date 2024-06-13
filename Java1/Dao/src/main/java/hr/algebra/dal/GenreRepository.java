/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Genre;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ana
 */
public interface GenreRepository {
    int createGenre(int id, Genre genre) throws Exception;
    Optional<Genre> selectGenre(Genre genre) throws Exception;
    List<Genre> selectGenres(int selectedMovieId) throws Exception;
    List<Genre> selectAllGenres() throws Exception;
    
}
