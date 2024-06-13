/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package hr.algebra.view.model;

import hr.algebra.model.Movie;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author ana
 */
public class MovieTableModel extends AbstractTableModel {

    private static final String[] COLUMN_NAMES = {"Id", "Title", "Published", "Description",
        "Original Title", "Duration", "Poster Path", "Link", "Expected"};

    private List<Movie> movies;

    public MovieTableModel(List<Movie> movies) {
        this.movies = movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return movies.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return movies.get(rowIndex).getId();
            case 1:
                return movies.get(rowIndex).getTitle();
            case 2:
                return movies.get(rowIndex).getPublishedDate().format(Movie.DATE_FORMATTER);
            case 3:
                return movies.get(rowIndex).getDescription();
            case 4:
                return movies.get(rowIndex).getOriginalTitle();
            case 5:
                return movies.get(rowIndex).getDuration();
            case 6:
                return movies.get(rowIndex).getPosterPath();
            case 7:
                return movies.get(rowIndex).getLink();
            case 8:
                return movies.get(rowIndex).getExpectedDate().format(Movie.DATE_FORMAT);

            default:
                throw new RuntimeException("No such column");
        }
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Integer.class;
        }
        return super.getColumnClass(columnIndex);
    }

}
