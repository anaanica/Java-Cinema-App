/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package hr.algebra.dal;

import hr.algebra.model.Director;
import java.util.List;
import java.util.Optional;

/**
 *
 * @author ana
 */
public interface DirectorRepository {
    int createDirector(int id, Director director) throws Exception;
    void createNewDirector(String directorName) throws Exception;
    void updateDirector(int idDirector, String directorName) throws Exception;
    void deleteDirector(int id) throws Exception;
    Optional<Director> selectDirector(Director director) throws Exception;
    List<Director> selectDirectors(int selectedMovieId) throws Exception;
    List<Director> selectAllDirectors() throws Exception;
    int directorMovieRelated(int idDirector) throws Exception;
}
