# My Digital Book Room

## A desktop application for sorting, rating, and tracking books

This application intends to be a digital bookshelf that allows users to:
- Input a book they have read (including author, title, genre, etc.)
- Rate their books (on a 10-point scale)
- Post reviews of their books
- Sort their books onto customizable bookshelves (to-read, favourites, currently reading etc.)

This intends to be an application for personal use, to help users keep track of the books they have read and loved.
It can also work as a way for students to keep track of the book they read in university, for parents to track which 
picture books their kids responded to, or any number of other uses specific to the user. 

This project is of particular interest to me as a big reader who likes to keep track of the books I read. 
As someone who doesn't buy most of my books, I can't rely on looking at my bookshelf to remember which books I've 
read in the past. However, I want to be able to remember which books I read and how I felt about them in order to 
help me choose my next reads. **Therefore, this application should be of particular use to users like myself who read a 
lot of library books and ebooks, and want a way to keep all of their reads in one place.**

## User Stories
In creating this application, I will consider a number of user stories that give a good idea of how this 
application may be used. Here are a few to start:
- As a user, I want to be able to add a book to my bookshelf and include information about the book (i.e. 
title, author, genre) and rate and review these books at any time
- As a user, I want to be able to select a book and view all the information about that book
- As a user, I want to be able to sort my books onto "bookshelves" that I can label myself, and I want a 
book to be able to appear on more than one bookshelf
- As a user, I want to be view my shelves and which books are on those shelves
- As a user, I want to be able to save my Book Room to file
- As a user, I want to be able to load my Book Room from file

## Phase 4: Task 2
I chose to implement the first option. I made my BookRoom class robust, and made addShelfToRoom throw a 
DuplicateBookshelfNameException if someone tries to add a shelf to the room when it already exists.

## Phase 4: Task 3
If I had time, I might consider making the following changes:
- Creating a clearer hierarchy in the GUI (i.e. every component of the main frame has its own superclass, and then
  subclasses for various functionalities). I have this somewhat but I think it could be neater and done better.
- I would add more exceptions to account for text limits in fields, number of bookshelves you can add, etc.