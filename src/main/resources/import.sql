-- popunjavanje tabele korisnika
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('John', 'Doe', 'johndoe', 'johndoe@email.com', 'password123', '1990-01-01', 'ps1', 'zadovoljan', 'CITALAC');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Jane', 'Duncan', 'janeduncan', 'janeduncan@email.com', 'password123', '1992-05-15', 'ps2', 'vrlo dobar', 'AUTOR');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Admin', 'Admin', 'admin', 'admin@admin.com', 'adminpassword', '1990-01-01', 'ps3', 'ok', 'ADMINISTRATOR');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Mark', 'Smith', 'marksmith', 'marksmith@email.com', 'password456', '1985-07-22', 'ps4', 'odlican', 'AUTOR');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Sara', 'Johnson', 'sarajohnson', 'sarajohnson@email.com', 'password789', '1999-11-03', 'ps5', 'sjajna', 'CITALAC');

-- popunjavanje tabele autora
INSERT INTO AUTOR (aktivan, id) VALUES (true, 2);
INSERT INTO AUTOR (aktivan, id) VALUES (true, 4);

-- popunjavanje tabele žanrova
INSERT INTO ZANR (naziv) VALUES ('Drama');
INSERT INTO ZANR (naziv) VALUES ('Comedy');
INSERT INTO ZANR (naziv) VALUES ('Thriller');
INSERT INTO ZANR (naziv) VALUES ('Mystery');

-- popunjavanje tabele knjiga
INSERT INTO KNJIGA (naslov, slika, opis, isbn, datum, ocena, br_str, zanr_id, autor_id) VALUES ('To Kill a Mockingbird', 'https://www.example.com/to-kill-a-mockingbird.jpg', 'A classic of American literature', '9780061120084', '1960-07-11', 45.23, 281, 1, 2);
INSERT INTO KNJIGA (naslov, slika, opis, isbn, datum, ocena, br_str, zanr_id, autor_id) VALUES ('Pride and Prejudice', 'https://www.example.com/pride-and-prejudice.jpg', 'A novel of manners in early 19th-century England', '9780141439518', '1813-01-28', 47.97, 432, 1, 2);
INSERT INTO KNJIGA (naslov, slika, opis, isbn, datum, ocena, br_str, zanr_id, autor_id) VALUES ('The Da Vinci Code', 'https://www.example.com/the-da-vinci-code.jpg', 'A thriller novel', '9780307474278', '2003-03-18', 39.76, 481, 4, 4);
INSERT INTO KNJIGA (naslov, slika, opis, isbn, datum, ocena, br_str, zanr_id, autor_id) VALUES ('The Catcher in the Rye', 'https://www.example.com/the-catcher-in-the-rye.jpg', 'A controversial novel', '9780316769488', '1951-07-16', 42.56, 277, 2, 2);
INSERT INTO KNJIGA (naslov, slika, opis, isbn, datum, ocena, br_str, zanr_id, autor_id) VALUES ('Harry Potter and the Philosophers Stone', 'https://www.example.com/harry-potter.jpg', 'The first book in the Harry Potter series', '9780747532743', '1997-06-26', 48.32, 223, 3, 4);

-- popunjavanje tabele polica
--INSERT INTO POLICA (naziv, primarno, korisnik_id) VALUES ('Want to Read', true, 1);
--INSERT INTO POLICA (naziv, primarno, korisnik_id) VALUES ('Currently Reading', true, 2);
--INSERT INTO POLICA (naziv, primarno, korisnik_id) VALUES ('Read', true, 3);
--INSERT INTO POLICA (naziv, primarno, korisnik_id) VALUES ('Favourites', false, 4);
--INSERT INTO POLICA (naziv, primarno, korisnik_id) VALUES ('Random', false, 5);

-- popunjavanje tabele stavki police
--INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (1, 1);
--INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (1, 2);
--INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (2, 2);
--INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (2, 3);
--INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (3, 5);
--INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (3, 4);
--INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (4, 4);

-- popunjavanje tabele recenzija
--INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (5, 'Great book, highly recommended', '2022-03-01', 1, 1);
--INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (4, 'Enjoyable read, would recommend', '2022-03-05', 1, 2);
--INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (3, 'Interesting but not my favorite', '2022-03-10', 4, 4);
--INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (4, 'Exciting plot with unexpected twists', '2022-03-15', 5, 3);
--INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (5, 'Amazing book, couldnt put it down!', '2022-04-09', 5, 5);
--INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (2, 'Interesting concept but fell short in execution', '2022-02-28', 2, 6);
--INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (3, 'Interesting story but slow-paced', '2022-01-28', 2, 7);

-- popunjavanje tabele zahteva za aktivaciju naloga autora
INSERT INTO ZAHTEV_ZA_AKTIVACIJU (mejl, telefon, poruka, datum, status, autor_id) VALUES ('newauthor@example.com', '555-5555', 'I would like to become an author on the platform', '2022-03-20', 'CEKANJE', 2);