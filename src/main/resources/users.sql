TRUNCATE users RESTART IDENTITY

INSERT INTO users (email, name, password, role) VALUES ("borrower@example.com", "Borrower", "QdSBhhujeH7ki2#X", "USER");
INSERT INTO users (email, name, password, role) VALUES ("librarian@example.com", "Librarian", "t4CvQm!R4SvRmzy$", "ADMIN");
