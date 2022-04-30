TRUNCATE users RESTART IDENTITY

INSERT INTO users (email, name, password, role) VALUES ("borrower@example.com", "Borrower", "$2a$10$fsEF00xw3CH4TdhSuhuDm.q8ewpA7rn96BKNaaodaafhVAU.26.Ce", "BORROWER");
INSERT INTO users (email, name, password, role) VALUES ("librarian@example.com", "Librarian", "$2a$10$zYUcORHL0AxOdCq9tdhIiO0mjszwHAE7tX/uC/.BEJEOk/gtd1AOm", "LIBRARIAN");
