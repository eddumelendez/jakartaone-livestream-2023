-- Create the "anime" table
CREATE TABLE anime (
    id serial PRIMARY KEY,
    title varchar(255) NOT NULL
);

-- Insert data into the "anime" table
INSERT INTO anime (title) VALUES
    ('Saint Seiya'),
    ('Demon Slayer'),
    ('Dragon Ball'),
    ('Naruto');

-- Create the "characters" table
CREATE TABLE characters (
    id serial PRIMARY KEY,
    name varchar(255) NOT NULL,
    anime_id integer REFERENCES anime(id)
);

-- Insert data into the "characters" table for Saint Seiya
INSERT INTO characters (name, anime_id) VALUES
    ('Seiya', (SELECT id FROM anime where title = 'Saint Seiya')),
    ('Shiryu', (SELECT id FROM anime where title = 'Saint Seiya')),
    ('Hyoga', (SELECT id FROM anime where title = 'Saint Seiya')),
    ('Ikki', (SELECT id FROM anime where title = 'Saint Seiya')),
    ('Saori', (SELECT id FROM anime where title = 'Saint Seiya')),
    ('Shun', (SELECT id FROM anime where title = 'Saint Seiya')),
    ('Aiolia', (SELECT id FROM anime where title = 'Saint Seiya')),
    ('Saga', (SELECT id FROM anime where title = 'Saint Seiya')),
    ('Shura', (SELECT id FROM anime where title = 'Saint Seiya')),
    ('Camus', (SELECT id FROM anime where title = 'Saint Seiya'));

-- Insert data into the "characters" table for Demon Slayer
INSERT INTO characters (name, anime_id) VALUES
    ('Tanjiro', (SELECT id FROM anime where title = 'Demon Slayer')),
    ('Nezuko', (SELECT id FROM anime where title = 'Demon Slayer')),
    ('Zenitsu', (SELECT id FROM anime where title = 'Demon Slayer')),
    ('Inosuke', (SELECT id FROM anime where title = 'Demon Slayer')),
    ('Giyu', (SELECT id FROM anime where title = 'Demon Slayer')),
    ('Shinobu', (SELECT id FROM anime where title = 'Demon Slayer')),
    ('Kanao', (SELECT id FROM anime where title = 'Demon Slayer')),
    ('Muzan', (SELECT id FROM anime where title = 'Demon Slayer')),
    ('Rengoku', (SELECT id FROM anime where title = 'Demon Slayer')),
    ('Enmu', (SELECT id FROM anime where title = 'Demon Slayer'));

-- Insert data into the "characters" table for Dragon Ball
INSERT INTO characters (name, anime_id) VALUES
    ('Goku', (SELECT id FROM anime where title = 'Dragon Ball')),
    ('Vegeta', (SELECT id FROM anime where title = 'Dragon Ball')),
    ('Piccolo', (SELECT id FROM anime where title = 'Dragon Ball')),
    ('Gohan', (SELECT id FROM anime where title = 'Dragon Ball')),
    ('Krillin', (SELECT id FROM anime where title = 'Dragon Ball')),
    ('Bulma', (SELECT id FROM anime where title = 'Dragon Ball')),
    ('Frieza', (SELECT id FROM anime where title = 'Dragon Ball')),
    ('Cell', (SELECT id FROM anime where title = 'Dragon Ball')),
    ('Majin Buu', (SELECT id FROM anime where title = 'Dragon Ball')),
    ('Trunks', (SELECT id FROM anime where title = 'Dragon Ball'));

-- Insert data into the "characters" table for Naruto
INSERT INTO characters (name, anime_id) VALUES
    ('Naruto', (SELECT id FROM anime where title = 'Naruto')),
    ('Sasuke', (SELECT id FROM anime where title = 'Naruto')),
    ('Sakura', (SELECT id FROM anime where title = 'Naruto')),
    ('Kakashi', (SELECT id FROM anime where title = 'Naruto')),
    ('Gaara', (SELECT id FROM anime where title = 'Naruto')),
    ('Hinata', (SELECT id FROM anime where title = 'Naruto')),
    ('Jiraiya', (SELECT id FROM anime where title = 'Naruto')),
    ('Orochimaru', (SELECT id FROM anime where title = 'Naruto')),
    ('Tsunade', (SELECT id FROM anime where title = 'Naruto')),
    ('Itachi', (SELECT id FROM anime where title = 'Naruto'));