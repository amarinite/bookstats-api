-- Clear existing data completely (for development)
TRUNCATE TABLE user_books CASCADE;
TRUNCATE TABLE book_genres CASCADE;
TRUNCATE TABLE books CASCADE;
TRUNCATE TABLE genres CASCADE;
TRUNCATE TABLE users CASCADE;

-- Insert sample users
INSERT INTO users (username, email, password_hash) VALUES
('alice_reader', 'alice@email.com', '$2b$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LeQCeYzxrjK0oQNXy'),
('book_lover_bob', 'bob@email.com', '$2b$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LeQCeYzxrjK0oQNXy'),
('charlie_pages', 'charlie@email.com', '$2b$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LeQCeYzxrjK0oQNXy'),
('diana_novel', 'diana@email.com', '$2b$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LeQCeYzxrjK0oQNXy'),
('eddie_fiction', 'eddie@email.com', '$2b$12$LQv3c1yqBWVHxkd0LHAkCOYz6TtxMQJqhN8/LeQCeYzxrjK0oQNXy');

-- Insert sample genres
INSERT INTO genres (name) VALUES
('Fiction'),
('Fantasy'),
('Science Fiction'),
('Mystery'),
('Romance'),
('Thriller'),
('Biography'),
('History'),
('Philosophy'),
('Self-Help'),
('Young Adult'),
('Classic Literature'),
('Horror'),
('Adventure'),
('Non-Fiction');

-- Insert sample books
INSERT INTO books (title, author, isbn, pages, language, country, author_gender, published_year, cover_url) VALUES
('The Great Gatsby', 'F. Scott Fitzgerald', '978-0-7432-7356-5', 180, 'English', 'United States', 'male', 1925, 'https://example.com/gatsby.jpg'),
('To Kill a Mockingbird', 'Harper Lee', '978-0-06-112008-4', 376, 'English', 'United States', 'female', 1960, 'https://example.com/mockingbird.jpg'),
('1984', 'George Orwell', '978-0-452-28423-4', 328, 'English', 'United Kingdom', 'male', 1949, 'https://example.com/1984.jpg'),
('Pride and Prejudice', 'Jane Austen', '978-0-14-143951-8', 432, 'English', 'United Kingdom', 'female', 1813, 'https://example.com/pride.jpg'),
('The Catcher in the Rye', 'J.D. Salinger', '978-0-316-76948-0', 277, 'English', 'United States', 'male', 1951, 'https://example.com/catcher.jpg'),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', '978-0-439-70818-8', 309, 'English', 'United Kingdom', 'female', 1997, 'https://example.com/hp1.jpg'),
('The Hobbit', 'J.R.R. Tolkien', '978-0-547-92822-7', 366, 'English', 'United Kingdom', 'male', 1937, 'https://example.com/hobbit.jpg'),
('Dune', 'Frank Herbert', '978-0-441-17271-9', 688, 'English', 'United States', 'male', 1965, 'https://example.com/dune.jpg'),
('The Girl with the Dragon Tattoo', 'Stieg Larsson', '978-0-307-47347-9', 590, 'English', 'Sweden', 'male', 2005, 'https://example.com/dragon.jpg'),
('Gone Girl', 'Gillian Flynn', '978-0-307-58836-4', 419, 'English', 'United States', 'female', 2012, 'https://example.com/gone.jpg'),
('The Alchemist', 'Paulo Coelho', '978-0-06-112241-5', 163, 'English', 'Brazil', 'male', 1988, 'https://example.com/alchemist.jpg'),
('Educated', 'Tara Westover', '978-0-399-59050-4', 334, 'English', 'United States', 'female', 2018, 'https://example.com/educated.jpg'),
('Cien años de soledad', 'Gabriel García Márquez', '978-84-376-0494-7', 471, 'Spanish', 'Colombia', 'male', 1967, 'https://example.com/cien_anos.jpg'),
('The Silent Patient', 'Alex Michaelides', '978-1-250-30170-7', 336, 'English', 'Cyprus', 'male', 2019, 'https://example.com/silent.jpg'),
('Where the Crawdads Sing', 'Delia Owens', '978-0-735-21953-0', 370, 'English', 'United States', 'female', 2018, 'https://example.com/crawdads.jpg');

-- Connect books to genres (many-to-many relationship)
-- Use book titles and genre names to find the correct IDs
INSERT INTO book_genres (book_id, genre_id)
SELECT b.id, g.id FROM
(VALUES
    ('The Great Gatsby', 'Fiction'),
    ('The Great Gatsby', 'Classic Literature'),
    ('To Kill a Mockingbird', 'Fiction'),
    ('To Kill a Mockingbird', 'Classic Literature'),
    ('1984', 'Fiction'),
    ('1984', 'Science Fiction'),
    ('1984', 'Classic Literature'),
    ('Pride and Prejudice', 'Fiction'),
    ('Pride and Prejudice', 'Romance'),
    ('Pride and Prejudice', 'Classic Literature'),
    ('The Catcher in the Rye', 'Fiction'),
    ('The Catcher in the Rye', 'Classic Literature'),
    ('Harry Potter and the Sorcerer''s Stone', 'Fiction'),
    ('Harry Potter and the Sorcerer''s Stone', 'Fantasy'),
    ('Harry Potter and the Sorcerer''s Stone', 'Young Adult'),
    ('The Hobbit', 'Fiction'),
    ('The Hobbit', 'Fantasy'),
    ('The Hobbit', 'Adventure'),
    ('Dune', 'Fiction'),
    ('Dune', 'Science Fiction'),
    ('The Girl with the Dragon Tattoo', 'Fiction'),
    ('The Girl with the Dragon Tattoo', 'Mystery'),
    ('The Girl with the Dragon Tattoo', 'Thriller'),
    ('Gone Girl', 'Fiction'),
    ('Gone Girl', 'Mystery'),
    ('Gone Girl', 'Thriller'),
    ('The Alchemist', 'Fiction'),
    ('The Alchemist', 'Philosophy'),
    ('Educated', 'Biography'),
    ('Educated', 'Non-Fiction'),
    ('Cien años de soledad', 'Fiction'),
    ('Cien años de soledad', 'Classic Literature'),
    ('The Silent Patient', 'Fiction'),
    ('The Silent Patient', 'Mystery'),
    ('The Silent Patient', 'Thriller'),
    ('Where the Crawdads Sing', 'Fiction'),
    ('Where the Crawdads Sing', 'Mystery')
) AS book_genre_map(book_title, genre_name)
JOIN books b ON b.title = book_genre_map.book_title
JOIN genres g ON g.name = book_genre_map.genre_name;

-- Insert user reading progress (user_books)
-- Use usernames and book titles to find the correct IDs
INSERT INTO user_books (user_id, book_id, status, rating, start_date, finish_date, notes)
SELECT u.id, b.id, reading_data.status, reading_data.rating, reading_data.start_date, reading_data.finish_date, reading_data.notes
FROM (VALUES
    -- Alice's reading history
    ('alice_reader', 'The Great Gatsby', 'completed', 5, '2024-01-15'::date, '2024-01-20'::date, 'Beautiful prose, loved the symbolism'),
    ('alice_reader', 'To Kill a Mockingbird', 'completed', 4, '2024-02-01'::date, '2024-02-10'::date, 'Important themes, great character development'),
    ('alice_reader', 'Harry Potter and the Sorcerer''s Stone', 'reading', NULL, '2024-05-20'::date, NULL, 'Finally reading this classic series!'),
    ('alice_reader', 'The Hobbit', 'want_to_read', NULL, NULL, NULL, 'Heard great things about Tolkien'),
    ('alice_reader', 'Educated', 'completed', 5, '2024-03-01'::date, '2024-03-08'::date, 'Incredible memoir, very inspiring'),

    -- Bob's reading history
    ('book_lover_bob', '1984', 'completed', 5, '2024-01-01'::date, '2024-01-12'::date, 'Dystopian masterpiece, very relevant today'),
    ('book_lover_bob', 'Dune', 'completed', 4, '2023-12-15'::date, '2024-01-05'::date, 'Epic sci-fi, complex but rewarding'),
    ('book_lover_bob', 'The Girl with the Dragon Tattoo', 'reading', NULL, '2024-05-15'::date, NULL, 'Gripping thriller so far'),
    ('book_lover_bob', 'Gone Girl', 'want_to_read', NULL, NULL, NULL, 'On my mystery reading list'),
    ('book_lover_bob', 'Cien años de soledad', 'completed', 4, '2024-04-01'::date, '2024-04-20'::date, 'Realismo mágico increíble, García Márquez es un genio'),

    -- Charlie's reading history
    ('charlie_pages', 'Pride and Prejudice', 'completed', 3, '2024-02-14'::date, '2024-02-28'::date, 'Classic romance, a bit slow for me'),
    ('charlie_pages', 'The Catcher in the Rye', 'completed', 4, '2024-03-15'::date, '2024-03-22'::date, 'Interesting coming-of-age story'),
    ('charlie_pages', 'The Silent Patient', 'completed', 5, '2024-04-10'::date, '2024-04-15'::date, 'Couldn''t put it down! Great twist'),
    ('charlie_pages', 'Where the Crawdads Sing', 'reading', NULL, '2024-05-25'::date, NULL, 'Beautiful nature descriptions'),

    -- Diana's reading history
    ('diana_novel', 'Harry Potter and the Sorcerer''s Stone', 'completed', 5, '2024-01-10'::date, '2024-01-18'::date, 'Magical! Brought back childhood wonder'),
    ('diana_novel', 'The Hobbit', 'completed', 5, '2024-01-20'::date, '2024-01-30'::date, 'Perfect adventure, loved Bilbo'),
    ('diana_novel', 'The Alchemist', 'completed', 4, '2024-02-15'::date, '2024-02-18'::date, 'Short but profound, life-changing'),
    ('diana_novel', 'The Great Gatsby', 'want_to_read', NULL, NULL, NULL, 'Classic I should finally read'),
    ('diana_novel', 'To Kill a Mockingbird', 'want_to_read', NULL, NULL, NULL, 'Another important classic'),

    -- Eddie's reading history
    ('eddie_fiction', 'Educated', 'completed', 5, '2024-03-01'::date, '2024-03-12'::date, 'Powerful story of education and family'),
    ('eddie_fiction', 'Cien años de soledad', 'reading', NULL, '2024-05-01'::date, NULL, 'Mi primer libro en español, el realismo mágico es fascinante'),
    ('eddie_fiction', '1984', 'want_to_read', NULL, NULL, NULL, 'Classic dystopian novel'),
    ('eddie_fiction', 'Dune', 'want_to_read', NULL, NULL, NULL, 'Epic sci-fi on my list')
) AS reading_data(username, book_title, status, rating, start_date, finish_date, notes)
JOIN users u ON u.username = reading_data.username
JOIN books b ON b.title = reading_data.book_title;

-- Display some sample queries to test the data
SELECT 'Sample Users:' as info;
SELECT id, username, email, created_at FROM users LIMIT 5;

SELECT 'Sample Books with Genres:' as info;
SELECT b.title, b.author, b.published_year,
       STRING_AGG(g.name, ', ') as genres
FROM books b
LEFT JOIN book_genres bg ON b.id = bg.book_id
LEFT JOIN genres g ON bg.genre_id = g.id
GROUP BY b.id, b.title, b.author, b.published_year
ORDER BY b.title
LIMIT 5;

SELECT 'User Reading Stats (using the view):' as info;
SELECT * FROM user_reading_stats ORDER BY books_completed DESC;