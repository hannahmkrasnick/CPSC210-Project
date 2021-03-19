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
    private BookRoomView brv;
    private BookshelfView bookshelfView;
    private BooksView booksView;
    private EditPanel editPanel;
    private JLabel label;
    private Font myFont;

    private static final String JSON_STORE = "./data/bookroom.json";
    private BookRoom bookRoom;
    private Bookshelf allBooks;
    private Bookshelf completed;
    private Bookshelf toRead;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private GridBagConstraints constraints;
    private JPanel contentView;
    private JPanel bookDisplay;

    private Book testBook1;
    private Book testBook2;

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

        booksView = new BooksView(this, allBooks);
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridy += 1;
        contentView = booksView;
        add(booksView,constraints);

        editPanel = new EditPanel(this);
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.gridheight = 4;
        add(editPanel, constraints);

        brv = new BookRoomView(this);
        bookDisplay = brv;
        constraints.gridy = 4;
        constraints.gridheight = 1;
        add(brv, constraints);

        pack();
        centreOnScreen();
        setVisible(true);
    }

    private void setContentView(JPanel jpanel) {
        remove(contentView);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 4;
        contentView = jpanel;
        add(jpanel, constraints);
        revalidate();
    }

    public void changeContentView(Bookshelf bookshelf) {
        BooksView books = new BooksView(this, bookshelf);
        setContentView(books);
    }

    private void setBookDisplay(JPanel jpanel) {
        remove(bookDisplay);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 1;
        constraints.gridy = 4;
        constraints.gridheight = 1;
        bookDisplay = jpanel;
        add(jpanel, constraints);
        revalidate();
    }

    public void changeBookDisplay(Book b) {
        BookRoomView bookView = new BookRoomView(this);
        bookView.displayBook(b);
        setBookDisplay(bookView);
    }

    // MODIFIES: this
    // EFFECTS: initializes BookRoom with number of bookshelves
    private void init() {
        allBooks = new Bookshelf("All Books");
        toRead = new Bookshelf("To Read");
        completed = new Bookshelf("Completed");

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
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);

        myFont = new Font("Sans-Serif", Font.BOLD, 18);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    // ADD ADAPTATION: SPACE INVADERS
    // Centres frame on desktop
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    private JLabel drawBookRoomLabel() {
        label = new JLabel("Your Bookshelves:", SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(250,50));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(Color.DARK_GRAY);
        //Border lineBorder = BorderFactory.createLineBorder(Color.WHITE,3);
        //label.setBorder(lineBorder);
        label.setFont(myFont);
        return label;
    }
}