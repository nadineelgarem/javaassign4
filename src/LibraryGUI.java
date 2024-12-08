import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;


/**
 * LibraryGUI class represents the graphical user interface for the library management system.
 * It allows users to:
 * - Add, remove, search, and filter books.
 * - Save and load books using serialization.
 * - Import and export book data to/from CSV files.
 */


public class LibraryGUI extends Application {
    private Library library = new Library();

    @Override
    public void start(Stage primaryStage) {
        // Main layout
        BorderPane root = new BorderPane();

        // Table to display books
        TableView<Book> bookTable = new TableView<>();
        configureTable(bookTable);

        // Form to add books (placed on the right)
        VBox form = createBookForm(bookTable);

        // Buttons and Search Field
        HBox buttons = createButtons(bookTable);

        // Status Bar at the bottom
        Label statusBar = new Label("Welcome to the Library Management System");
        statusBar.setPadding(new Insets(5));

        // Search Field and Button
        TextField searchField = new TextField();
        searchField.setPromptText("Search by Title, Author, or ISBN (Press Enter to Reset)");

        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            String searchText = searchField.getText().toLowerCase();
            if (!searchText.isEmpty()) {
                bookTable.getItems().setAll(library.getBooks().stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(searchText) ||
                                book.getAuthor().toLowerCase().contains(searchText) ||
                                book.getIsbn().contains(searchText))
                        .toList());
                statusBar.setText("Search completed for: " + searchText);
            } else {
                bookTable.getItems().setAll(library.getBooks());
                statusBar.setText("Showing all books.");
            }
        });

        // Add the Search Field and Search Button to Buttons HBox
        buttons.getChildren().addAll(new Label("Search:"), searchField, searchButton);

        // Layout: Table, Buttons, and Status Bar
        VBox layout = new VBox(5, bookTable, buttons, statusBar);

        // Integrate components into BorderPane
        root.setCenter(layout);  // Center: Table + Buttons + Status Bar
        root.setRight(form);     // Right: Add Book Form

        // Scene Setup
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Library Management System");
        primaryStage.show();
    }



    private void configureTable(TableView<Book> table) {
        TableColumn<Book, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(data -> data.getValue().titleProperty());

        TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(data -> data.getValue().authorProperty());

        TableColumn<Book, String> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(data -> data.getValue().isbnProperty());

        TableColumn<Book, Number> yearColumn = new TableColumn<>("Year");
        yearColumn.setCellValueFactory(data -> data.getValue().yearOfPublicationProperty());

        TableColumn<Book, String> genreColumn = new TableColumn<>("Genre");
        genreColumn.setCellValueFactory(data -> data.getValue().genreProperty());

        table.getColumns().addAll(titleColumn, authorColumn, isbnColumn, yearColumn, genreColumn);
    }

    private VBox createBookForm(TableView<Book> table) {
        VBox form = new VBox(10);
        form.setPadding(new Insets(10));

        // Form fields
        TextField titleField = new TextField();
        titleField.setPromptText("Title");

        TextField authorField = new TextField();
        authorField.setPromptText("Author");

        TextField isbnField = new TextField();
        isbnField.setPromptText("ISBN");

        TextField yearField = new TextField();
        yearField.setPromptText("Year");

        TextField genreField = new TextField();
        genreField.setPromptText("Genre");

        Button addButton = new Button("Add Book");
        addButton.setOnAction(e -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();
                int year = Integer.parseInt(yearField.getText());
                String genre = genreField.getText();

                Book book = new Book(title, author, isbn, year, genre);
                if (library.addBook(book)) {
                    table.getItems().add(book);
                } else {
                    showAlert(Alert.AlertType.WARNING, "Duplicate Book", "This book already exists in the library.");
                }
            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Invalid Input", "Year must be a number.");
            }
        });

        form.getChildren().addAll(
                new Label("Add New Book"),
                titleField,
                authorField,
                isbnField,
                yearField,
                genreField,
                addButton
        );

        return form;
    }

    private HBox createButtons(TableView<Book> table) {
        HBox buttonBar = new HBox(10);
        buttonBar.setPadding(new Insets(10));
        buttonBar.setAlignment(Pos.CENTER_LEFT);

        // Load Button
        Button loadButton = new Button("Load");
        loadButton.setPrefWidth(80);
        loadButton.setOnAction(e -> {
            library.loadFromFile("library_data.ser");
            table.getItems().setAll(library.getBooks());
        });

        // Save Button
        Button saveButton = new Button("Save");
        saveButton.setPrefWidth(80);
        saveButton.setOnAction(e -> library.saveToFile("library_data.ser"));

        // Export to CSV Button
        Button exportButton = new Button("Export to CSV");
        exportButton.setPrefWidth(120);
        exportButton.setOnAction(e -> library.exportToCSV("books.csv"));

        // Import from CSV Button
        Button importButton = new Button("Import from CSV");
        importButton.setPrefWidth(120);
        importButton.setOnAction(e -> {
            library.importFromCSV("books.csv");
            table.getItems().setAll(library.getBooks());
        });

        // Remove Book Button
        Button removeButton = new Button("Remove Book");
        removeButton.setPrefWidth(120);
        removeButton.setOnAction(e -> {
            Book selectedBook = table.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                library.removeBook(selectedBook.getIsbn());
                table.getItems().remove(selectedBook);
            } else {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a book to remove.");
            }
        });

        // Search Field and Button
        Label searchLabel = new Label("Search:");
        TextField searchField = new TextField();
        searchField.setPromptText("Search by Title, Author, or ISBN");
        searchField.setPrefWidth(200); // Fixed width
        HBox.setHgrow(searchField, Priority.ALWAYS); // Allow searchField to grow

        Button searchButton = new Button("Search");
        searchButton.setPrefWidth(80);
        searchButton.setOnAction(e -> {
            String searchText = searchField.getText().toLowerCase();
            if (!searchText.isEmpty()) {
                table.getItems().setAll(library.getBooks().stream()
                        .filter(book -> book.getTitle().toLowerCase().contains(searchText) ||
                                book.getAuthor().toLowerCase().contains(searchText) ||
                                book.getIsbn().contains(searchText))
                        .toList());
            } else {
                table.getItems().setAll(library.getBooks());
            }
        });

        // Genre Filter ComboBox
        Label genreLabel = new Label("Filter by Genre:");
        ComboBox<String> genreFilter = new ComboBox<>();
        genreFilter.setPromptText("Filter by Genre");
        genreFilter.getItems().addAll("Classic", "Dystopian", "Political Satire", "Fantasy", "Fairy Tale", "Adventure", "Romance");
        genreFilter.setPrefWidth(130);

        genreFilter.setOnAction(e -> {
            String selectedGenre = genreFilter.getValue();
            if (selectedGenre != null) {
                table.getItems().setAll(library.getBooks().stream()
                        .filter(book -> book.getGenre().equalsIgnoreCase(selectedGenre))
                        .toList());
            } else {
                table.getItems().setAll(library.getBooks());
            }
        });

        // Reset Filter Button
        Button resetFilterButton = new Button("Reset Filter");
        resetFilterButton.setPrefWidth(100);
        resetFilterButton.setOnAction(e -> {
            genreFilter.setValue(null);
            table.getItems().setAll(library.getBooks());
        });

        // Add components to the HBox
        buttonBar.getChildren().addAll(
                loadButton, saveButton, exportButton, importButton, removeButton,
                searchLabel, searchField, searchButton,
                genreLabel, genreFilter, resetFilterButton
        );

        return buttonBar;
    }


    private void showAlert(Alert.AlertType alertType, String title, String content) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }


        public static void main(String[] args) {
        launch(args);
    }
}
