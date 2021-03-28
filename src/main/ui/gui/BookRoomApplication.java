package ui.gui;

import model.Book;
import model.BookRoom;
import model.Bookshelf;
import model.Genre;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

// Book Room application with graphics
public class BookRoomApplication extends JFrame {
    private static final String JSON_STORE = "./data/bookroom.json";
    private int panelWidth = 400;
    private int panelHeight = 300;

    private BookRoom bookRoom;
    private Bookshelf allBooks;
    private Bookshelf completed;
    private Bookshelf toRead;
    private Bookshelf favourites;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GridBagConstraints constraints;
    private BooksView booksView;
    private BookshelvesPanel bookshelvesPanel;
    private BookView bookDisplay;
    private JPanel editView;
    private OptionPane optionPane;
    private Font myFont;
    private Border lineBorder = BorderFactory.createLineBorder(Color.WHITE,4);
    private int currentPage;

    //EFFECTS: constructs a book room for users to add books to
    public BookRoomApplication() {
        super("My Book Room");
        init();
        optionPane = new OptionPane(this);
        optionPane.showLoadOption();

        setWindowListener();

        setUndecorated(false);
        setVisible(false);
        setLayout(new GridBagLayout());
        setBackground(Color.WHITE);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;

        setUpBookshelvesView(new BookshelvesPanel(this));

        setBooksDisplay(new BooksView(this, -1));

        setBookDisplay(new BookView(this));

        setEditView(new ChangePanel(this));
        changeToChangePanel();

        addGenrePanel();

        pack();
        centreOnScreen();
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: adds panel to far right with all genres
    private void addGenrePanel() {
        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridheight = 6;
        GridBagConstraints genrePanelConstraints = new GridBagConstraints();
        genrePanelConstraints.gridx = 0;
        genrePanelConstraints.gridy = 0;
        JPanel genrePanel = new JPanel();
        genrePanel.setLayout(new GridBagLayout());
        genrePanel.setPreferredSize(new Dimension(150, panelHeight * 2 + 120));
        genrePanel.setBackground(Color.DARK_GRAY);
        JLabel genreTitle = new JLabel("Genres:");
        genreTitle.setFont(myFont);
        genreTitle.setForeground(Color.WHITE);
        genrePanel.setBorder(lineBorder);
        genrePanel.add(genreTitle, genrePanelConstraints);
        for (Genre genre : Genre.values()) {
            genrePanelConstraints.gridy += 1;
            JLabel newLabel = new JLabel(genre.getString());
            newLabel.setForeground(Color.WHITE);
            genrePanel.add(newLabel, genrePanelConstraints);
        }
        add(genrePanel, constraints);
    }

    //MODIFIES: this
    //EFFECTS: sets up window listener for this to prompt user when they close the app
    private void setWindowListener() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                getSaveOption();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: sets up shelves view (top left) with existing bookshelves
    public void setUpBookshelvesView(BookshelvesPanel newBookshelvesPanel) {
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        bookshelvesPanel = newBookshelvesPanel;
        add(newBookshelvesPanel, constraints);
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: changes edit view to EditBookView where user can enter title of book to edit
    public void changeBookshelvesView() {
        remove(bookshelvesPanel);
        BookshelvesPanel newPanel = new BookshelvesPanel(this);
        setUpBookshelvesView(newPanel);
    }

    //MODIFIES: this
    //EFFECTS: sets edit view (bottom left) panel to given jpanel
    private void setEditView(JPanel jpanel) {
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 5;
        editView = jpanel;
        add(jpanel, constraints);
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: changes edit view to addBookshelfView where user can enter title of shelf to add
    public void changeToAddBookshelfView() {
        remove(editView);
        AddBookshelfView newPanel = new AddBookshelfView(this);
        setEditView(newPanel);
    }

    //MODIFIES: this
    //EFFECTS: changes edit view to deleteBookshelfView where user can enter title of shelf to delete
    public void changeToDeleteBookshelfView() {
        remove(editView);
        DeleteBookshelfView newPanel = new DeleteBookshelfView(this);
        setEditView(newPanel);
    }

    //MODIFIES: this
    //EFFECTS: changes edit view to EditBookView where user can enter title of book to edit
    public void changeToEditView() {
        remove(editView);
        EditBookView newPanel = new EditBookView(this);
        newPanel.enterTitleToEditView();
        setEditView(newPanel);
    }

    //MODIFIES: this
    //EFFECTS: changes edit view to EditBookView with fields for entering info about a book
    public void changeToEditBookFieldsView(Book book) {
        remove(editView);
        EditBookView newPanel = new EditBookView(this);
        newPanel.editBookFields(book);
        setEditView(newPanel);
    }

    //MODIFIES: this
    //EFFECTS: changes edit view to AddBookView
    public void changeToAddBookView() {
        remove(editView);
        AddBookView newPanel = new AddBookView(this);
        setEditView(newPanel);
    }

    //MODIFIES: this
    //EFFECTS: changes edit view to DeleteBookView
    public void changeToDeleteBookView() {
        remove(editView);
        DeleteBookView newPanel = new DeleteBookView(this);
        setEditView(newPanel);
    }

    public void changeToSearchBookView() {
        remove(editView);
        SearchBookView newPanel = new SearchBookView(this);
        setEditView(newPanel);
    }

    //MODIFIES: this
    //EFFECTS: changes edit view to default (ChangePanel with buttons)
    public void changeToChangePanel() {
        remove(editView);
        ChangePanel changePanel = new ChangePanel(this);
        changePanel.makeAddButton();
        changePanel.makeEditBookButton();
        changePanel.makeDeleteButton();
        changePanel.makeSearchButton();
        changePanel.makeAddBookshelfButton();
        changePanel.makeDeleteBookshelfButton();
        setEditView(changePanel);
    }

    //MODIFIES: this
    //EFFECTS: sets books display (top right) panel to given jpanel
    private void setBooksDisplay(BooksView newBooksView) {
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 5;
        booksView = newBooksView;
        add(newBooksView, constraints);
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: changes books display to new BooksView that displays bookshelf
    public void changeBooksDisplay(Bookshelf bookshelf, int currentPage) {
        this.currentPage = currentPage;
        remove(booksView);
        BooksView books = new BooksView(this, currentPage);
        books.showBookshelfWithBooks(bookshelf);
        setBooksDisplay(books);
    }

    //MODIFIES: this
    //EFFECTS: sets book display (bottom right) panel to given jpanel
    void setBookDisplay(BookView bookPanel) {
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridheight = 1;
        bookDisplay = bookPanel;
        add(bookPanel, constraints);
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: changes book display to new BookView that displays b
    public void changeBookDisplay(Book b) {
        remove(bookDisplay);
        BookView bookView = new BookView(this);
        bookView.displayBook(b);
        setBookDisplay(bookView);
    }

    // MODIFIES: this
    // EFFECTS: initializes BookRoom with number of bookshelves
    private void init() {
        allBooks = new Bookshelf("All Books");
        toRead = new Bookshelf("To Read");
        completed = new Bookshelf("Completed");
        favourites = new Bookshelf("Favourites");

        bookRoom = new BookRoom("My Book Room");
        bookRoom.addShelfToRoom(allBooks);
        bookRoom.addShelfToRoom(toRead);
        bookRoom.addShelfToRoom(completed);
        bookRoom.addShelfToRoom(favourites);

        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        myFont = new Font("Sans-Serif", Font.BOLD, 18);
    }

    // solution adapted from SpaceInvaders CPSC 210 program (SpaceInvaders.centreOnScreen)
    // MODIFIES: this
    // EFFECTS:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    //getters
    public int getPanelWidth() {
        return panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public BookRoom getBookRoom() {
        return bookRoom;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    //EFFECTS: finds and returns shelf with given label in book room
    public Bookshelf getShelfWithLabel(String bookshelfLabel) {
        Bookshelf toFind = new Bookshelf(bookshelfLabel);
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.equals(toFind)) {
                return bs;
            }
        }
        return null;
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (WorkRoomApp.saveWorkRoom)
    // EFFECTS: saves the bookroom to file
    public void saveBookRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(bookRoom);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            optionPane.saveError();
        }
    }

    // solution adapted from JsonSerializationDemo CPSC 210 program (WorkRoomApp.loadWorkRoom)
    // MODIFIES: this
    // EFFECTS: loads bookroom from file
    public void loadBookRoom() {
        try {
            bookRoom = jsonReader.read();
            revalidate();
        } catch (IOException e) {
            optionPane.loadError();
        }
    }

    //MODIFIES: this
    //EFFECTS: crafts new OptionPane to tell user their save options
    public void getSaveOption() {
        optionPane = new OptionPane(this);
        optionPane.showSaveOption();
    }

    //EFFECTS: returns the currently displayed shelf in booksView
    public Bookshelf getCurrentlyDisplayedBookshelf() {
        return booksView.getCurrentlyDisplayedBookshelf();
    }

        //EFFECTS: runs application
    public static void main(String[] args) {
        new BookRoomApplication();
    }
}