-- Create enums
CREATE TYPE author_gender AS ENUM ('male', 'female', 'other', 'unknown');
CREATE TYPE reading_status AS ENUM ('want_to_read', 'reading', 'completed');

-- Create users table
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create genres table
CREATE TABLE genres (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

-- Create books table (updated to match your DTOs)
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) UNIQUE,
    pages INTEGER,
    language VARCHAR(50),
    country VARCHAR(100),
    author_gender author_gender,
    published_year INTEGER,
    cover_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Update user_books table to use the enum
CREATE TABLE user_books (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    book_id INTEGER NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    status reading_status NOT NULL,
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    start_date DATE,
    finish_date DATE,
    notes TEXT CHECK (char_length(notes) <= 1000),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE (user_id, book_id),
    CONSTRAINT valid_date_range CHECK (finish_date >= start_date OR finish_date IS NULL OR start_date IS NULL)
);

-- Create book_genres junction table
CREATE TABLE book_genres (
    book_id INTEGER NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    genre_id INTEGER NOT NULL REFERENCES genres(id) ON DELETE CASCADE,
    PRIMARY KEY (book_id, genre_id)
);

-- Create useful indexes for better performance
CREATE INDEX idx_users_username ON users(username);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_books_title ON books(title);
CREATE INDEX idx_books_author ON books(author);
CREATE INDEX idx_books_isbn ON books(isbn);
CREATE INDEX idx_user_books_user_id ON user_books(user_id);
CREATE INDEX idx_user_books_book_id ON user_books(book_id);
CREATE INDEX idx_user_books_status ON user_books(status);

-- Create some common views for easier querying
CREATE VIEW user_reading_stats AS
SELECT
    u.id as user_id,
    u.username,
    COUNT(CASE WHEN ub.status = 'completed' THEN 1 END) as books_completed,
    COUNT(CASE WHEN ub.status = 'reading' THEN 1 END) as books_currently_reading,
    COUNT(CASE WHEN ub.status = 'want_to_read' THEN 1 END) as books_want_to_read,
    AVG(CASE WHEN ub.rating IS NOT NULL THEN ub.rating END) as average_rating
FROM users u
LEFT JOIN user_books ub ON u.id = ub.user_id
GROUP BY u.id, u.username;
