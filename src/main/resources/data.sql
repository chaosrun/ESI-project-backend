TRUNCATE materials, loan_requests, locations, reservations, users RESTART IDENTITY CASCADE;

INSERT INTO users (home_library, email, name, password, role) VALUES ('A', 'borrower@example.com', 'Borrower A', '$2a$10$glHBb0tkb6AP0fS6SnKKbOtW4RZLnK51GoISvaB3sADZ73ENERR9K', 'BORROWER');
INSERT INTO users (home_library, email, name, password, role) VALUES ('A', 'librarian@example.com', 'Librarian A', '$2a$10$zYUcORHL0AxOdCq9tdhIiO0mjszwHAE7tX/uC/.BEJEOk/gtd1AOm', 'LIBRARIAN');
INSERT INTO users (home_library, email, name, password, role) VALUES ('B', 'Bborrower@example.com', 'Borrower B', '$2a$10$glHBb0tkb6AP0fS6SnKKbOtW4RZLnK51GoISvaB3sADZ73ENERR9K', 'BORROWER');
INSERT INTO users (home_library, email, name, password, role) VALUES ('B', 'Blibrarian@example.com', 'Librarian B', '$2a$10$zYUcORHL0AxOdCq9tdhIiO0mjszwHAE7tX/uC/.BEJEOk/gtd1AOm', 'LIBRARIAN');

INSERT INTO materials (home_library, title, author, call_number, published_at) VALUES ('A', 'The Art of Computer Programming', 'Donald Knuth', '12345', '1962-01-01');
INSERT INTO materials (home_library, title, author, call_number, published_at) VALUES ('A', 'Fundamentals of Algorithms', 'Donald Knuth', '22222', '1962-01-01');
INSERT INTO materials (home_library, title, author, call_number, published_at) VALUES ('B', 'A Brief History of Time', 'Stephen Hawking', '54321', '1988-01-01');
INSERT INTO materials (home_library, title, author, call_number, published_at) VALUES ('B', 'The Universe in a Nutshell', 'Stephen Hawking', '33333', '1988-01-01');
