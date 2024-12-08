import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import java.io.Serializable;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
/**
 * Represents a Book in the library with title, author, ISBN, year, and genre.
 * Implements Serializable to allow saving and loading of book data.
 */


public class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    // Transient JavaFX properties
    private transient StringProperty title;
    private transient StringProperty author;
    private transient StringProperty isbn;
    private transient IntegerProperty yearOfPublication;
    private transient StringProperty genre;

    // Values to support custom serialization

    private String titleValue;
    private String authorValue;
    private String isbnValue;
    private int yearValue;
    private String genreValue;

    public Book(String title, String author, String isbn, int yearOfPublication, String genre) {
        this.title = new SimpleStringProperty(title);
        this.author = new SimpleStringProperty(author);
        this.isbn = new SimpleStringProperty(isbn);
        this.yearOfPublication = new SimpleIntegerProperty(yearOfPublication);
        this.genre = new SimpleStringProperty(genre);
    }

    // Properties (for JavaFX bindings)
    public StringProperty titleProperty() {
        return title;
    }

    public StringProperty authorProperty() {
        return author;
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    public IntegerProperty yearOfPublicationProperty() {
        return yearOfPublication;
    }

    public StringProperty genreProperty() {
        return genre;
    }

    // Getters for values
    public String getTitle() {
        return title.get();
    }

    public String getAuthor() {
        return author.get();
    }

    public String getIsbn() {
        return isbn.get();
    }

    public int getYearOfPublication() {
        return yearOfPublication.get();
    }

    public String getGenre() {
        return genre.get();
    }

    // Custom serialization logic
    private void writeObject(ObjectOutputStream oos) throws IOException {
        titleValue = title.get();
        authorValue = author.get();
        isbnValue = isbn.get();
        yearValue = yearOfPublication.get();
        genreValue = genre.get();
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        title = new SimpleStringProperty(titleValue);
        author = new SimpleStringProperty(authorValue);
        isbn = new SimpleStringProperty(isbnValue);
        yearOfPublication = new SimpleIntegerProperty(yearValue);
        genre = new SimpleStringProperty(genreValue);
    }

    // Equals and hashCode (based on ISBN for uniqueness)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return isbn.get().equals(book.getIsbn()); // Compare ISBN
    }

    @Override
    public int hashCode() {
        return isbn.get().hashCode();
    }

    // toString for debugging purposes
    @Override
    public String toString() {
        return "Book{" +
                "title=" + title.get() +
                ", author=" + author.get() +
                ", isbn=" + isbn.get() +
                ", yearOfPublication=" + yearOfPublication.get() +
                ", genre=" + genre.get() +
                '}';
    }
}
