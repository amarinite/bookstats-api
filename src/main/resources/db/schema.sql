-- Create user_books table
CREATE TABLE user_books (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    book_id INTEGER NOT NULL REFERENCES books(id) ON DELETE CASCADE,
    status VARCHAR(20) NOT NULL CHECK (status IN ('want_to_read', 'reading', 'completed')),
    rating INTEGER CHECK (rating BETWEEN 1 AND 5),
    start_date DATE,
    finish_date DATE,
    notes TEXT,
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
