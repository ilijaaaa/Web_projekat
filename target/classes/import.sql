-- popunjavanje tabele korisnika
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('John', 'Doe', 'johndoe', 'johndoe@email.com', 'password123', '1990-01-01', 'slika', 'zadovoljan', 'CITALAC');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Jane', 'Duncan', 'janeduncan', 'janeduncan@email.com', 'password123', '1992-05-15', 'https://newprofilepic2.photo-cdn.net//assets/images/article/profile.jpg', 'vrlo dobar', 'AUTOR');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Admin', 'Admin', 'admin', 'admin', 'admin@email.com', '1990-01-01', 'https://cdn.pixabay.com/photo/2015/10/05/22/37/blank-profile-picture-973460_1280.png', 'ok', 'ADMINISTRATOR');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Mark', 'Smith', 'marksmith', 'marksmith@email.com', 'password456', '1985-07-22', 'https://w0.peakpx.com/wallpaper/979/89/HD-wallpaper-purple-smile-design-eye-smily-profile-pic-face.jpg', 'odlican', 'AUTOR');
INSERT INTO KORISNIK (ime, prezime, korisnicko_ime, mejl, lozinka, datum_rodjenja, profilna_slika, opis, uloga) VALUES ('Sara', 'Johnson', 'sarajohnson', 'sarajohnson@email.com', 'password789', '1999-11-03', 'https://play-lh.googleusercontent.com/i1qvljmS0nE43vtDhNKeGYtNlujcFxq72WAsyD2htUHOac57Z9Oiew0FrpGKlEehOvo=w240-h480-rw', 'sjajna', 'CITALAC');

-- popunjavanje tabele autora
INSERT INTO AUTOR (aktivan, id) VALUES (true, 2);
INSERT INTO AUTOR (aktivan, id) VALUES (true, 4);

-- popunjavanje tabele žanrova
INSERT INTO ZANR (naziv) VALUES ('Drama');
INSERT INTO ZANR (naziv) VALUES ('Comedy');
INSERT INTO ZANR (naziv) VALUES ('Thriller');
INSERT INTO ZANR (naziv) VALUES ('Mystery');

-- popunjavanje tabele knjiga
INSERT INTO KNJIGA (naslov, slika, opis, isbn, datum, ocena, br_str, zanr_id, autor_id) VALUES ('To Kill a Mockingbird', 'https://upload.wikimedia.org/wikipedia/commons/4/4f/To_Kill_a_Mockingbird_%28first_edition_cover%29.jpg', 'A classic of American literature', '9780061120084', '1960-07-11', 4.23, 281, 1, 2);
INSERT INTO KNJIGA (naslov, slika, opis, isbn, datum, ocena, br_str, zanr_id, autor_id) VALUES ('Pride and Prejudice', 'https://upload.wikimedia.org/wikipedia/commons/thumb/1/17/PrideAndPrejudiceTitlePage.jpg/800px-PrideAndPrejudiceTitlePage.jpg', 'A novel of manners in early 19th-century England', '9780141439511', '1813-01-28', 4.97, 432, 1, 2);
INSERT INTO KNJIGA (naslov, slika, opis, isbn, datum, ocena, br_str, zanr_id, autor_id) VALUES ('The Da Vinci Code', 'https://upload.wikimedia.org/wikipedia/en/6/6b/DaVinciCode.jpg', 'A thriller novel', '9780307474278', '2003-03-18', 3.76, 481, 4, 4);
INSERT INTO KNJIGA (naslov, slika, opis, isbn, datum, ocena, br_str, zanr_id, autor_id) VALUES ('The Catcher in the Rye', 'https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/The_Catcher_in_the_Rye_%281951%2C_first_edition_cover%29.jpg/800px-The_Catcher_in_the_Rye_%281951%2C_first_edition_cover%29.jpg', 'A controversial novel', '9780316769488', '1951-07-16', 2.56, 277, 2, 2);
INSERT INTO KNJIGA (naslov, slika, opis, isbn, datum, ocena, br_str, zanr_id, autor_id) VALUES ('Harry Potter and the Philosophers Stone', 'https://upload.wikimedia.org/wikipedia/en/6/6b/Harry_Potter_and_the_Philosopher%27s_Stone_Book_Cover.jpg', 'The first book in the Harry Potter series', '9780747532743', '1997-06-26', 4.32, 223, 3, 4);

-- popunjavanje tabele polica
INSERT INTO POLICA (naziv, primarno, korisnik_id) VALUES ('Want to Read', true, 1);
INSERT INTO POLICA (naziv, primarno, korisnik_id) VALUES ('Currently Reading', true, 2);
INSERT INTO POLICA (naziv, primarno, korisnik_id) VALUES ('Read', true, 3);
INSERT INTO POLICA (naziv, primarno, korisnik_id) VALUES ('Favourites', false, 4);
INSERT INTO POLICA (naziv, primarno, korisnik_id) VALUES ('Random', false, 5);

-- popunjavanje tabele stavki police
INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (1, 1);
INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (1, 2);
INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (2, 2);
INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (2, 3);
INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (3, 5);
INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (3, 4);
INSERT INTO STAVKA_POLICE (polica_id, knjiga_id) VALUES (4, 4);

-- popunjavanje tabele recenzija
INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (5, 'Great book, highly recommended', '2022-03-01', 1, 1);
INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (4, 'Enjoyable read, would recommend', '2022-03-05', 4, 2);
INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (3, 'Interesting but not my favorite', '2022-03-10', 4, 4);
INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (4, 'Exciting plot with unexpected twists', '2022-03-15', 3, 3);
INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (5, 'Amazing book, couldnt put it down!', '2022-04-09', 2, 5);
INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (2, 'Interesting concept but fell short in execution', '2022-02-28', 5, 6);
INSERT INTO RECENZIJA (ocena, tekst, datum, korisnik_id, stavka_police_id) VALUES (3, 'Interesting story but slow-paced', '2022-01-28', 1, 7);

-- popunjavanje tabele zahteva za aktivaciju naloga autora
INSERT INTO ZAHTEV_ZA_AKTIVACIJU (mejl, telefon, poruka, datum, status, autor_id) VALUES ('janeduncan@email.com', '555-5555', 'I would like to become an author on the platform', '2022-03-20', 'CEKANJE', 2);