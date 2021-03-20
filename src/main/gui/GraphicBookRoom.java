package gui;

import model.Book;
import model.BookRoom;
import model.Bookshelf;
import model.Genre;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphicBookRoom extends JFrame implements ActionListener {
    private BookshelfView bookshelfView;
    private JLabel label;
    private Font myFont;

    private int panelWidth = 400;
    private int panelHeight = 300;
    private static final String JSON_STORE = "./data/bookroom.json";
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

    private Book testBook1;
    private Book testBook2;

    //TODO
    public GraphicBookRoom() {
        super("My Book Room");
        init();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setVisible(false);
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        setBackground(Color.BLACK);

        add(drawBookRoomLabel(), constraints);

        for (Bookshelf bs : bookRoom.getShelves()) {
            bookshelfView = new BookshelfView(bs, this);
            constraints.gridy += 1;
            add(bookshelfView, constraints);
        }

        setBooksDisplay(new BooksView(this, allBooks));

        setBookDisplay(new BookView(this));

        ChangePanel changePanel = new ChangePanel(this);
        setEditView(changePanel);
        changeToChangePanel();

        pack();
        centreOnScreen();
        setVisible(true);
    }

    //TODO
    private void setEditView(JPanel jpanel) {
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 5;
        editView = jpanel;
        add(jpanel, constraints);
        revalidate();
    }

    //TODO
    public void changeToEditView() {
        remove(editView);
        EditBookView newPanel = new EditBookView(this);
        newPanel.enterTitleToEditView();
        setEditView(newPanel);
    }

    //TODO
    public void changeToEditBookFieldsView(Book book) {
        remove(editView);
        EditBookView newPanel = new EditBookView(this);
        newPanel.editBookFields(book);
        setEditView(newPanel);
    }

    public void changeToAddBookView() {
        remove(editView);
        AddBookView newPanel = new AddBookView(this);
        setEditView(newPanel);
    }

    public void changeToDeleteBookView() {
        remove(editView);
        DeleteBookView newPanel = new DeleteBookView(this);
        setEditView(newPanel);
    }

    public void changeToChangePanel() {
        remove(editView);
        ChangePanel changePanel = new ChangePanel(this);
        changePanel.makeAddButton();
        changePanel.makeEditBookButton();
        changePanel.makeDeleteButton();
        setEditView(changePanel);
    }

    //TODO
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

    //TODO
    public void changeBooksDisplay(Bookshelf bookshelf) {
        remove(contentView);
        BooksView books = new BooksView(this, bookshelf);
        setBooksDisplay(books);
    }

    //TODO
    private void setBookDisplay(JPanel jpanel) {
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 5;
        constraints.gridheight = 1;
        bookDisplay = jpanel;
        add(jpanel, constraints);
        revalidate();
    }

    //TODO
    public void changeBookDisplay(Book b) {
        remove(bookDisplay);
        BookView bookView = new BookView(this);
        bookView.displayBook(b);
        setBookDisplay(bookView);
    }

    //TODO
    // MODIFIES: this
    // EFFECTS: initializes BookRoom with number of bookshelves
    private void init() {
        allBooks = new Bookshelf("All Books");
        toRead = new Bookshelf("To Read");
        completed = new Bookshelf("Completed");
        favourites = new Bookshelf("Favourites");

        testBook1 = new Book("Harry Potter", "JK Rowling", Genre.CLASSIC, 5, "Very good");
        testBook2 = new Book("Game of Thrones");
        allBooks.addBookToShelf(testBook1);
        allBooks.addBookToShelf(testBook2);
        toRead.addBookToShelf(testBook1);
        completed.addBookToShelf(testBook2);

        bookRoom = new BookRoom("My Book Room");
        bookRoom.addShelfToRoom(allBooks);
        bookRoom.addShelfToRoom(toRead);
        bookRoom.addShelfToRoom(completed);
        bookRoom.addShelfToRoom(favourites);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        myFont = new Font("Sans-Serif", Font.BOLD, 18);
    }

    //TODO
    @Override
    public void actionPerformed(ActionEvent e) {
    }

    //TODO
    // ADD ADAPTATION: SPACE INVADERS
    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screen.width - getWidth()) / 2, (screen.height - getHeight()) / 2);
    }

    //TODO
    private JLabel drawBookRoomLabel() {
        label = new JLabel("Your Bookshelves:", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(panelWidth,panelHeight / 5));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        label.setFont(myFont);
        return label;
    }

    public int getPanelWidth() {
        return panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public Bookshelf getAllBooks() {
        return allBooks;
    }

    public Bookshelf getCompleted() {
        return completed;
    }

    public Bookshelf getToRead() {
        return toRead;
    }

    public Bookshelf getFavourites() {
        return favourites;
    }

    public BookRoom getBookRoom() {
        return bookRoom;
    }


}