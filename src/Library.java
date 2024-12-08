import java.io.*;
import java.util.HashSet;//
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;

/**
 * Library class manages a collection of books.
 * Supports add, remove, search, sorting, saving/loading, and import/export functionalities.
 */


public class Library {


    private ObservableList<Book> books;


    public Library() {
        books = FXCollections.observableArrayList();
    }

    // Add a book (prevents duplicates using ISBN)
    public boolean addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
            return true;
        }
        return false; // Book already exists
    }


    // Remove a book by ISBN
    public boolean removeBook(String isbn) {
        return books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    // Sort books by year of publication
    public void sortBooksByYear() {
        List<Book> sortedBooks = new ArrayList<>(books);
        sortedBooks.sort(Comparator.comparingInt(Book::getYearOfPublication));

        System.out.println("Books sorted by year of publication:");
        sortedBooks.forEach(System.out::println);
    }

    // Search books by title, author, or ISBN
    public void searchBooksByTitle(String title) {
        books.stream()
                .filter(book -> book.getTitle().equalsIgnoreCase(title))
                .forEach(System.out::println);
    }
    public void sortBooksByTitle() {
        List<Book> sortedBooks = new ArrayList<>(books);
        sortedBooks.sort(Comparator.comparing(Book::getTitle));

        System.out.println("Books sorted by title:");
        sortedBooks.forEach(System.out::println);
    }
    public void sortBooksByAuthor() {
        List<Book> sortedBooks = new ArrayList<>(books);
        sortedBooks.sort(Comparator.comparing(Book::getAuthor));

        System.out.println("Books sorted by author:");
        sortedBooks.forEach(System.out::println);
    }
    public void filterBooksByGenre(String genre) {
        System.out.println("Books in the genre '" + genre + "':");
        books.stream()
                .filter(book -> book.getGenre().equalsIgnoreCase(genre))
                .forEach(System.out::println);
    }
    public void searchBooksByAuthor(String author) {
        System.out.println("Books by author '" + author + "':");
        books.stream()
                .filter(book -> book.getAuthor().equalsIgnoreCase(author))
                .forEach(System.out::println);
    }
    public ObservableList<Book> getBooks() {
        return books;
    }
    public void searchBooksByISBN(String isbn) {
        books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .forEach(book -> System.out.println("Book found: " + book));
    }


    public void importFromCSV(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            reader.readLine(); // Skip header row
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length == 5) {
                    String title = fields[0];
                    String author = fields[1];
                    String isbn = fields[2];
                    int year = Integer.parseInt(fields[3]);
                    String genre = fields[4];

                    Book book = new Book(title, author, isbn, year, genre);

                    // Check for duplicates before adding
                    if (!books.contains(book)) {
                        addBook(book);
                    }
                }
            }
            System.out.println("Books imported from CSV file: " + fileName);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error importing from CSV: " + e.getMessage());
        }
    }


    // Display all books
    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            books.forEach(System.out::println);
        }
    }

    // Save the books to a file
    public void saveToFile(String fileName) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            List<Book> serializableBooks = new ArrayList<>(books); // Convert to List
            oos.writeObject(serializableBooks);
            System.out.println("Library saved to file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error saving library: " + e.getMessage());
        }
    }

    // Export books to CSV
    public void exportToCSV(String fileName) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Title,Author,ISBN,Year,Genre\n"); // Header row
            for (Book book : books) {
                writer.write(String.format("%s,%s,%s,%d,%s\n",
                        book.getTitle(), book.getAuthor(), book.getIsbn(),
                        book.getYearOfPublication(), book.getGenre()));
            }
            System.out.println("Books exported to CSV file: " + fileName);
        } catch (IOException e) {
            System.err.println("Error exporting to CSV: " + e.getMessage());
        }
    }

    // Load the library from a serialized file
    @SuppressWarnings("unchecked")
    public void loadFromFile(String fileName) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            List<Book> loadedBooks = (List<Book>) ois.readObject(); // Deserialize as List
            books.setAll(loadedBooks); // Convert back to ObservableList
            System.out.println("Library loaded from file: " + fileName);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading library: " + e.getMessage());
        }
    }

}
