/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.dal;

import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author daniel.bele
 */
public final class RepositoryFactory {
  private static final Properties properties = new Properties();
  private static final String PATH = "/config/repository.properties";
  private static final String ACTOR_REPOSITORY = "ACTOR_REPOSITORY";
  private static final String DIRECTOR_REPOSITORY = "DIRECTOR_REPOSITORY";
  private static final String GENRE_REPOSITORY = "GENRE_REPOSITORY";
  private static final String MOVIE_REPOSITORY = "MOVIE_REPOSITORY";
  private static final String USER_REPOSITORY = "USER_REPOSITORY";
  
  private static ActorRepository actorRepository;
  private static DirectorRepository directorRepository;
  private static GenreRepository genreRepository;
  private static MovieRepository movieRepository;
  private static UserRepository userRepository;

  static {
    try (InputStream is = RepositoryFactory.class.getResourceAsStream(PATH)) {
      properties.load(is);
      actorRepository = createRepository(ActorRepository.class, ACTOR_REPOSITORY);
      directorRepository = createRepository(DirectorRepository.class, DIRECTOR_REPOSITORY);
      genreRepository = createRepository(GenreRepository.class, GENRE_REPOSITORY);
      movieRepository = createRepository(MovieRepository.class, MOVIE_REPOSITORY);
      userRepository = createRepository(UserRepository.class, USER_REPOSITORY);
    } catch (Exception ex) {
      Logger.getLogger(RepositoryFactory.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  private static <T> T createRepository(Class<T> repositoryClass, String propertyName) throws Exception {
    String repositoryClassName = properties.getProperty(propertyName);
    return repositoryClass.cast(Class.forName(repositoryClassName)
        .getDeclaredConstructor()
        .newInstance());
  }

  public static ActorRepository getActorRepository() {
    return actorRepository;
  }

  public static DirectorRepository getDirectorRepository() {
    return directorRepository;
  }

  public static GenreRepository getGenreRepository() {
    return genreRepository;
  }

  public static MovieRepository getMovieRepository() {
    return movieRepository;
  }

  public static UserRepository getUserRepository() {
    return userRepository;
  }
}
