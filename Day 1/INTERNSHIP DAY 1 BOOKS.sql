SHOW DATABASES;
SHOW TABLES;
CREATE TABLE books (
  id INT AUTO_INCREMENT PRIMARY KEY,
  title VARCHAR(200) NOT NULL,
  author VARCHAR(200) NOT NULL,
  year INT,
  price DECIMAL(10,2)
);
DESCRIBE books;
SELECT * FROM books;
INSERT INTO books (title, author, year, price) VALUES
('The Hobbit', 'J.R.R. Tolkien', 1937, 12.99),
('1984', 'George Orwell', 1949, 9.99),
('Clean Code', 'Robert C. Martin', 2008, 34.50),
('The Pragmatic Programmer', 'Andrew Hunt', 1999, 29.95),
('Atomic Habits', 'James Clear', 2018, 16.99);
SELECT * FROM books;