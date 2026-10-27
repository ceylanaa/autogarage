-- rollen
INSERT INTO rollen (naam) VALUES ('KASSAMEDEWERKER'), ('MONTEUR'), ('BACKOFFICE');

-- test gebruikers, wachtwoord is voor allemaal: wachtwoord123
INSERT INTO gebruikers (gebruikersnaam, wachtwoord, rol_id) VALUES ('kassamedewerker', '$2a$10$fX2EimiG1/JA3Dv1CHF7e.xkHTOxda4h8wzYlj6ufsqAyGxGO910S', 1);
INSERT INTO gebruikers (gebruikersnaam, wachtwoord, rol_id) VALUES ('monteur', '$2a$10$fX2EimiG1/JA3Dv1CHF7e.xkHTOxda4h8wzYlj6ufsqAyGxGO910S', 2);
INSERT INTO gebruikers (gebruikersnaam, wachtwoord, rol_id) VALUES ('backoffice', '$2a$10$fX2EimiG1/JA3Dv1CHF7e.xkHTOxda4h8wzYlj6ufsqAyGxGO910S', 3);

-- klanten
INSERT INTO klanten (naam, telefoonnummer, emailadres) VALUES ('Jan de Vries', '0612345678', 'jan.devries@mail.nl');
INSERT INTO klanten (naam, telefoonnummer, emailadres) VALUES ('Fatima El Amrani', '0687654321', 'fatima@mail.nl');
INSERT INTO klanten (naam, telefoonnummer, emailadres) VALUES ('Pieter Bakker', '0611223344', 'p.bakker@mail.nl');

-- autos
INSERT INTO autos (kenteken, merk, model, klant_id) VALUES ('AB-123-C', 'Volkswagen', 'Golf', 1);
INSERT INTO autos (kenteken, merk, model, klant_id) VALUES ('XY-987-Z', 'Toyota', 'Yaris', 2);
INSERT INTO autos (kenteken, merk, model, klant_id) VALUES ('GH-456-J', 'Opel', 'Corsa', 3);

-- onderdelen (prijzen excl btw)
INSERT INTO onderdelen (naam, prijs_excl_btw, voorraad) VALUES ('Remblokken set', 45.00, 20);
INSERT INTO onderdelen (naam, prijs_excl_btw, voorraad) VALUES ('Remschijf', 60.00, 10);
INSERT INTO onderdelen (naam, prijs_excl_btw, voorraad) VALUES ('Oliefilter', 12.50, 30);
INSERT INTO onderdelen (naam, prijs_excl_btw, voorraad) VALUES ('Motorolie 5L', 39.95, 15);
INSERT INTO onderdelen (naam, prijs_excl_btw, voorraad) VALUES ('Ruitenwisser', 15.00, 25);

-- reparatiehandelingen (prijzen excl btw)
INSERT INTO reparatiehandelingen (naam, prijs_excl_btw) VALUES ('Remblokken vervangen', 75.00);
INSERT INTO reparatiehandelingen (naam, prijs_excl_btw) VALUES ('Olie verversen', 50.00);
INSERT INTO reparatiehandelingen (naam, prijs_excl_btw) VALUES ('Remschijven vervangen', 110.00);
INSERT INTO reparatiehandelingen (naam, prijs_excl_btw) VALUES ('Ruitenwissers vervangen', 20.00);

-- een keuring en reparatie om mee te testen
INSERT INTO keuringen (auto_id, keurings_datum, tekortkomingen, status) VALUES (1, '2026-10-20', 'Remblokken versleten', 'KLANT_AKKOORD');
INSERT INTO keuringen (auto_id, keurings_datum, status) VALUES (2, '2026-11-02', 'GEPLAND');
INSERT INTO reparaties (keuring_id, reparatie_datum, status, betaald) VALUES (1, '2026-10-22', 'GEREED', false);
INSERT INTO reparatie_regels (reparatie_id, reparatiehandeling_id) VALUES (1, 1);
INSERT INTO reparatie_onderdelen (reparatie_id, onderdeel_id, aantal) VALUES (1, 1, 1);
