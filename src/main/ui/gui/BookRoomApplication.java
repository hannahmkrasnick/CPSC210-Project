package ui.gui;

import model.Book;
import model.BookRoom;
import model.Bookshelf;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
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
    private JPanel contentView;
    private JPanel bookDisplay;
    private JPanel editView;
    private OptionPane optionPane;
    private Font myFont;

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
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        setBackground(Color.BLACK);

        add(drawBookRoomLabel(), constraints);

        addBookshelfPanels(constraints);

        setBooksDisplay(new BooksView(this, allBooks));

        setBookDisplay(new BookView(this));

        setEditView(new ChangePanel(this));
        changeToChangePanel();

        pack();
        centreOnScreen();
        setVisible(true);
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
    //EFFECTS: adds panels for each bookshelf to this
    private void addBookshelfPanels(GridBagConstraints constraints) {
        this.constraints = constraints;
        for (Bookshelf bs : bookRoom.getShelves()) {
            BookshelfView bookshelfView = new BookshelfView(bs, this);
            constraints.gridy += 1;
            add(bookshelfView, constraints);
        }
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

    //MODIFIES: this
    //EFFECTS: changes edit view to default (ChangePanel with buttons)
    public void changeToChangePanel() {
        remove(editView);
        ChangePanel changePanel = new ChangePanel(this);
        changePanel.makeAddButton();
        changePanel.makeEditBookButton();
        changePanel.makeDeleteButton();
        setEditView(changePanel);
    }

    //MODIFIES: this
    //EFFECTS: sets books display (top right) panel to given jpanel
    private void setBooksDisplay(JPanel jpanel) {
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 5;
        contentView = jpanel;
        add(jpanel, constraints);
        revalidate();
    }

    //MODIFIES: this
    //EFFECTS: changes books display to new BooksView that displays bookshelf
    public void changeBooksDisplay(Bookshelf bookshelf) {
        remove(contentView);
        BooksView books = new BooksView(this, bookshelf);
        setBooksDisplay(books);
    }

    //MODIFIES: this
    //EFFECTS: sets book display (bottom right) panel to given jpanel
    private void setBookDisplay(JPanel jpanel) {
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 5;
        constraints.gridheight = 1;
        bookDisplay = jpanel;
        add(jpanel, constraints);
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

    //EFFECTS: creates label for above bookshelves
    private JLabel drawBookRoomLabel() {
        JLabel label = new JLabel("Your Bookshelves:", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(panelWidth,panelHeight / 5));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        label.setFont(myFont);
        return label;
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

    //EFFECTS: returns shelf labeled "All Books" in bookRoom
    public Bookshelf getAllBooks() {
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().equals("All Books")) {
                return bs;
            }
        }
        return allBooks;
    }

    //EFFECTS: returns shelf labeled "Completed" in bookRoom
    public Bookshelf getCompleted() {
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().equals("Completed")) {
                return bs;
            }
        }
        return completed;
    }

    //EFFECTS: returns shelf labeled "To Read" in bookRoom
    public Bookshelf getToRead() {
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().equals("To Read")) {
                return bs;
            }
        }
        return toRead;
    }

    //EFFECTS: returns shelf labeled "Favourites" in bookRoom
    public Bookshelf getFavourites() {
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().equals("Favourites")) {
                return bs;
            }
        }
        return favourites;
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

    //EFFECTS: runs application
    public static void main(String[] args) {
        new BookRoomApplication();
    }
}