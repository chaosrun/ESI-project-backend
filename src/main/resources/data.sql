TRUNCATE users RESTART IDENTITY;

INSERT INTO users (user_id, email, name, password, role) VALUES (1, 'borrower@example.com', 'Borrower', '$2a$10$glHBb0tkb6AP0fS6SnKKbOtW4RZLnK51GoISvaB3sADZ73ENERR9K', 'BORROWER');
INSERT INTO users (user_id, email, name, password, role) VALUES (2, 'librarian@example.com', 'Librarian', '$2a$10$zYUcORHL0AxOdCq9tdhIiO0mjszwHAE7tX/uC/.BEJEOk/gtd1AOm', 'LIBRARIAN');
