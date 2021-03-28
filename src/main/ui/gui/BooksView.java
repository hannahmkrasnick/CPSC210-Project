package ui.gui;

import model.Book;
import model.Bookshelf;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


// JPanel with buttons for all books on given shelf
public class BooksView extends JPanel implements ActionListener {
    private Color color = new Color(247, 233, 197);
    private Bookshelf bookshelf;
    private GridBagConstraints constraints;
    private int width;
    private int height;
    private Font myFont = new Font("Sans-Serif", Font.BOLD, 16);
    private Border lineBorder = BorderFactory.createLineBorder(Color.WHITE,4);
    private BookRoomApplication gui;
    private List<Book> books;
    private int currentPage;
    private int pageCount;

    //EFFECTS: constructs JPanel for displaying shelves
    public BooksView(BookRoomApplication gui, int currentPage) {
        this.gui = gui;
        this.currentPage = currentPage;
        width = gui.getPanelWidth();
        height = gui.getPanelHeight();
        setPreferredSize(new Dimension(width, height));
        setBackground(color);
        setBorder(lineBorder);
        setLayout(new GridBagLayout());
    }

    //MODIFIES: this
    //EFFECTS: displays bookshelf with buttons for all books on shelf
    public void showBookshelfWithBooks(Bookshelf bs) {
        this.bookshelf = bs;
        constraints = new GridBagConstraints();

        JPanel labelPanel = new JPanel();
        labelPanel.setPreferredSize(new Dimension(width, 50));
        labelPanel.setMinimumSize(new Dimension(width, 50));
        labelPanel.setLayout(new GridBagLayout());
        labelPanel.setOpaque(false);
        GridBagConstraints newConstraints = new GridBagConstraints();
        JLabel bookshelfLabel = new JLabel(bs.getBookshelfLabel(), SwingConstants.CENTER);
        bookshelfLabel.setFont(myFont);
        labelPanel.add(bookshelfLabel, newConstraints);

        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 2;
        add(labelPanel, constraints);
        constraints.gridwidth = 1;
        constraints.gridx = 1;
        addBookButtons(constraints);
        constraints.gridx = 0;
        constraints.gridy += 1;
        addBottomPanel(constraints);
    }

    //MODIFIES: this
    //EFFECTS: constructs a button for every book for the current page
    private void addBookButtons(GridBagConstraints constraints) {
        this.constraints = constraints;
        books = bookshelf.getBooksOnShelf();
        int displayBooksFromThisIndex = currentPage * 10;
        for (int i = 0; i < 10; i++) {
            JPanel buttonPanel = new JPanel();
            buttonPanel.setPreferredSize(new Dimension(width / 2, 40));
            buttonPanel.setMinimumSize(new Dimension(width / 2, 40));
            buttonPanel.setOpaque(false);
            if (displayBooksFromThisIndex < books.size()) {
                BookButton bookButton = new BookButton(books.get(displayBooksFromThisIndex), gui);
                buttonPanel.setLayout(new GridBagLayout());
                GridBagConstraints newConstraints = new GridBagConstraints();
                buttonPanel.add(bookButton, newConstraints);
            }
            if (constraints.gridx == 0) {
                constraints.gridx = 1;
            } else {
                constraints.gridx = 0;
                constraints.gridy += 1;
            }
            add(buttonPanel, constraints);
            displayBooksFromThisIndex++;
        }
    }

    //MODIFIES: this
    //EFFECTS: adds the panel with prev and next buttons for displaying overflow books, and shows how many pages there
    //         are
    private void addBottomPanel(GridBagConstraints constraints) {
        this.constraints = constraints;
        constraints.gridwidth = 2;

        JPanel bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(width, 50));
        bottomPanel.setMinimumSize(new Dimension(width, 50));
        bottomPanel.setOpaque(false);

        bottomPanel.setLayout(new GridBagLayout());
        GridBagConstraints bottomConstraints = new GridBagConstraints();

        JButton prevButton = new JButton("Prev");
        prevButton.setActionCommand("prev");
        prevButton.addActionListener(this);

        double numBooks = books.size();
        double pageCountDouble = Math.ceil(numBooks / 10);
        pageCount = (int) pageCountDouble;
        JLabel pageAmount = new JLabel("Page " + (currentPage + 1) + "/" + pageCount);

        JButton nextButton = new JButton("Next");
        nextButton.setActionCommand("next");
        nextButton.addActionListener(this);

        bottomPanel.add(prevButton, bottomConstraints);
        bottomPanel.add(pageAmount, bottomConstraints);
        bottomPanel.add(nextButton, bottomConstraints);
        add(bottomPanel, constraints);
    }

    //getter
    public Bookshelf getCurrentlyDisplayedBookshelf() {
        return this.bookshelf;
    }

    //MODIFIES: this
    //EFFECTS: either goes to the previous books page or next depending on prompt. Doesn't change display if prev or
    //         next doesn't exist
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("prev") && currentPage != 0) {
            gui.changeBooksDisplay(bookshelf, currentPage - 1);
        } else if (e.getActionCommand().equals("next") && currentPage != (pageCount - 1)) {
            gui.changeBooksDisplay(bookshelf, currentPage + 1);
        }
    }
}