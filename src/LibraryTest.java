public class LibraryTest {
    public static void main(String[] args) {
        // Step 1: Create a Library instance
        Library library = new Library();

        // Step 2: Load books from a serialized file
        System.out.println("Loading books from file...");
        library.loadFromFile("library_data.ser");

        // Step 3: Add Disney-themed books to the library
        Book book1 = new Book("Peter Pan", "J.M. Barrie", "9781234567001", 1911, "Fantasy");
        Book book2 = new Book("The Jungle Book", "Rudyard Kipling", "9781234567002", 1894, "Adventure");
        Book book3 = new Book("Beauty and the Beast", "Gabrielle-Suzanne B.", "9781234567003", 1756, "Romance");
        Book book4 = new Book("The Lion King", "Disney Adaptation", "9781234567004", 1994, "Adventure");
        Book book5 = new Book("Aladdin and the Magic Lamp", "Antoine Galland", "9781234567005", 1704, "Fantasy");
        Book book6 = new Book("Cinderella", "Charles Perrault", "9781234567006", 1697, "Fantasy");
        Book book7 = new Book("Snow White and the Seven Dwarfs", "Brothers Grimm", "9781234567007", 1812, "Fantasy");
        Book book8 = new Book("Pinocchio", "Carlo Collodi", "9781234567008", 1883, "Adventure");
        Book book9 = new Book("Alice in Wonderland", "Lewis Carroll", "9781234567009", 1865, "Fantasy");
        Book book10 = new Book("Sleeping Beauty", "Charles Perrault", "9781234567010", 1697, "Romance");

        library.addBook(book1);
        library.addBook(book2);
        library.addBook(book3);
        library.addBook(book4);
        library.addBook(book5);
        library.addBook(book6);
        library.addBook(book7);
        library.addBook(book8);
        library.addBook(book9);
        library.addBook(book10);

        // Step 4: Test adding a duplicate book
        System.out.println("\nAdding a duplicate book:");
        if (!library.addBook(book1)) {
            System.out.println("Duplicate book not added: " + book1);
        }

        // Step 5: Display all books in the library
        System.out.println("\nAll books in the library:");
        library.displayAllBooks();

        // Step 6: Search for books
        System.out.println("\nSearching for 'The Jungle Book':");
        library.searchBooksByTitle("The Jungle Book");

        System.out.println("\nSearching for books by author 'Charles Perrault':");
        library.searchBooksByAuthor("Charles Perrault");

        System.out.println("\nSearching for book with ISBN '9781234567009':");
        library.searchBooksByISBN("9781234567009");

        // Step 7: Filter books by genre
        System.out.println("\nFiltering books by genre 'Fantasy':");
        library.filterBooksByGenre("Fantasy");

        // Step 8: Sort books
        System.out.println("\nSorting books by year of publication:");
        library.sortBooksByYear();

        System.out.println("\nSorting books by title:");
        library.sortBooksByTitle();

        System.out.println("\nSorting books by author:");
        library.sortBooksByAuthor();

        // Step 9: Remove a book by ISBN
        System.out.println("\nRemoving book with ISBN '9781234567004':");
        library.removeBook("9781234567004");

        // Step 10: Display all books after removal
        System.out.println("\nAll books after removal:");
        library.displayAllBooks();

        // Step 11: Export books to a CSV file
        System.out.println("\nExporting books to CSV...");
        library.exportToCSV("books.csv");

        // Step 12: Clear the current library
        library = new Library();

        // Step 13: Import books from the CSV file
        System.out.println("\nImporting books from CSV...");
        library.importFromCSV("books.csv");

        // Step 14: Display all books after importing
        System.out.println("\nBooks after importing from CSV:");
        library.displayAllBooks();

        // Step 15: Save books to a file
        System.out.println("\nSaving books to file...");
        library.saveToFile("library_data.ser");

        // Step 16: Reload the library to confirm serialization
        Library newLibrary = new Library();
        newLibrary.loadFromFile("library_data.ser");
        System.out.println("\nBooks after reloading from file:");
        newLibrary.displayAllBooks();
    }
}
