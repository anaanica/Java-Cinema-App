/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package hr.algebra.view;

import hr.algebra.dal.ActorRepository;
import hr.algebra.dal.DirectorRepository;
import hr.algebra.dal.GenreRepository;
import hr.algebra.dal.MovieRepository;
import hr.algebra.dal.RepositoryFactory;
import hr.algebra.model.Actor;
import hr.algebra.model.Director;
import hr.algebra.model.Genre;
import hr.algebra.model.Movie;
import hr.algebra.parsers.rss.MovieParser;
import hr.algebra.utilities.MessageUtils;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;

/**
 *
 * @author ana
 */
public class UploadMoviesPanel extends javax.swing.JPanel {
    private DefaultListModel<Movie> moviesModel;

    private Director director;
    private Actor actor;
    private Genre genre;
    
    private ActorRepository actorRepository;
    private DirectorRepository directorRepository;
    private GenreRepository genreRepository;
    private MovieRepository movieRepository;
    
    private static final String DIR = "src/assets";

    /**
     * Creates new form UploadMoviesPanel
     */
    public UploadMoviesPanel() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        lsMovies = new javax.swing.JList<>();
        btnDelete = new javax.swing.JButton();
        btnUpload = new javax.swing.JButton();
        lbReport = new javax.swing.JLabel();

        jScrollPane1.setViewportView(lsMovies);

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnUpload.setText("Upload");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 806, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbReport, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbReport, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpload, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed

        handleGui(true);

        new Thread(() -> {
            try {
                List<Movie> allMovies = MovieParser.parse();

                for (Movie movie : allMovies) {
                    int idMovie = movieRepository.createMovie(movie);

                    if (movie.getDirectors() != null) {
                        for (Director director : movie.getDirectors()) {
                            Optional<Director> optionalDirector = directorRepository.selectDirector(director);
                            if (optionalDirector.isPresent()) {
                                this.director = optionalDirector.get();
                                movieRepository.setMovieDirectors(idMovie, this.director.getIdDirector());
                            } else {
                                directorRepository.createDirector(idMovie, director);
                            }
                        }
                    }

                    if (movie.getActors() != null) {
                        for (Actor actor : movie.getActors()) {
                            Optional<Actor> optionalActor = actorRepository.selectActorName(actor);
                            if (optionalActor.isPresent()) {
                                this.actor = optionalActor.get();
                                movieRepository.setMovieActors(idMovie, this.actor.getIdActor());
                            } else {
                                actorRepository.createActor(idMovie, actor);
                            }
                        }
                    }

                    if (movie.getGenres() != null) {
                        for (Genre genre : movie.getGenres()) {
                            Optional<Genre> optionalGenre = genreRepository.selectGenre(genre);
                            if (optionalGenre.isPresent()) {
                                this.genre = optionalGenre.get();
                                movieRepository.setMovieGenres(idMovie, this.genre.getIdGenre());
                            } else {
                                genreRepository.createGenre(idMovie, genre);
                            }
                        }
                    }
                    List<Movie> movies = movieRepository.selectMovies();
                    loadModel(movies);
                }
                handleGui(false);
                MessageUtils.showInformationMessage("Success", "Movies successfully loaded");
            } catch (Exception ex) {
                Logger.getLogger(UploadMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }//GEN-LAST:event_btnUploadActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            if (MessageUtils.showConfirmDialog("Delete database", "Delete all movies in database?")) {
                movieRepository.deleteAllMoviesFromDatabase();
                moviesModel.clear();
                Arrays.stream(new File(DIR).listFiles()).forEach(File::delete);
                btnUpload.setEnabled(true);
            }

        } catch (Exception ex) {
            Logger.getLogger(UploadMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnUpload;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbReport;
    private javax.swing.JList<Movie> lsMovies;
    // End of variables declaration//GEN-END:variables

    private void init() {
        try {
            initRepository();
            initModel();
        } catch (Exception ex) {
            Logger.getLogger(UploadMoviesPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void initRepository() throws Exception {
        actorRepository = RepositoryFactory.getActorRepository();
        directorRepository = RepositoryFactory.getDirectorRepository();
        genreRepository = RepositoryFactory.getGenreRepository();
        movieRepository = RepositoryFactory.getMovieRepository();
    }

    private void initModel() throws Exception {
        moviesModel = new DefaultListModel<>();
        moviesModel.clear();
        List<Movie> movies = movieRepository.selectMovies();
        movies.forEach(moviesModel::addElement);
        lsMovies.setModel(moviesModel);

        handleGui(false);
    }

    private void loadModel(List<Movie> movies) {
        java.awt.EventQueue.invokeLater(() -> {
            moviesModel.clear();
            movies.forEach(moviesModel::addElement);
            lsMovies.setModel(moviesModel);
        });
    }

    private void handleGui(boolean searching) {
        lbReport.setText(searching ? "Please wait while movies load" : "Please choose action");
        if (lsMovies.getModel().getSize() != 0) {
            btnUpload.setEnabled(false);
        }
    }
}
