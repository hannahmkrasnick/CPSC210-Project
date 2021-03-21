package gui;

import model.Book;
import model.BookRoom;
import model.Bookshelf;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;

public class GraphicBookRoom extends JFrame implements ActionListener {
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

    //TODO
    public GraphicBookRoom() {
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

    //TODO
    private void setWindowListener() {
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                getSaveOption();
            }
        });
    }

    //TODO
    private void addBookshelfPanels(GridBagConstraints constraints) {
        this.constraints = constraints;
        for (Bookshelf bs : bookRoom.getShelves()) {
            BookshelfView bookshelfView = new BookshelfView(bs, this);
            constraints.gridy += 1;
            add(bookshelfView, constraints);
        }
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

    //TODO
    public void changeToAddBookView() {
        remove(editView);
        AddBookView newPanel = new AddBookView(this);
        setEditView(newPanel);
    }

    //TODO
    public void changeToDeleteBookView() {
        remove(editView);
        DeleteBookView newPanel = new DeleteBookView(this);
        setEditView(newPanel);
    }

    //TODO
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
        JLabel label = new JLabel("Your Bookshelves:", SwingConstants.CENTER);
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

    //TODO
    public Bookshelf getAllBooks() {
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().equals("All Books")) {
                return bs;
            }
        }
        return allBooks;
    }

    //TODO
    public Bookshelf getCompleted() {
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().equals("Completed")) {
                return bs;
            }
        }
        return completed;
    }

    //TODO
    public Bookshelf getToRead() {
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().equals("To Read")) {
                return bs;
            }
        }
        return toRead;
    }

    //TODO
    public Bookshelf getFavourites() {
        for (Bookshelf bs : bookRoom.getShelves()) {
            if (bs.getBookshelfLabel().equals("Favourites")) {
                return bs;
            }
        }
        return favourites;
    }

    public BookRoom getBookRoom() {
        return bookRoom;
    }

    //TODO
    // solution adapted from JsonSerializationDemo CPSC 210 program (WorkRoomApp.saveWorkRoom)
    // EFFECTS: saves the bookroom to file
    public void saveBookRoom() {
        try {
            jsonWriter.open();
            jsonWriter.write(bookRoom);
            jsonWriter.close();
            //System.out.println("Saved " + bookRoom.getName() + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            //System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    //TODO
    // solution adapted from JsonSerializationDemo CPSC 210 program (WorkRoomApp.loadWorkRoom)
    // MODIFIES: this
    // EFFECTS: loads bookroom from file
    public void loadBookRoom() {
        try {
            bookRoom = jsonReader.read();
            revalidate();
            //System.out.println("Loaded " + bookRoom.getName() + " from " + JSON_STORE);
        } catch (IOException e) {
            //System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    //TODO
    public static void main(String[] args) {
        new GraphicBookRoom();
    }

    //TODO
    public void getSaveOption() {
        optionPane = new OptionPane(this);
        optionPane.showSaveOption();
    }
}