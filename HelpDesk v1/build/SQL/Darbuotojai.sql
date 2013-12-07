use psi_database;
;--lenteles sukurimas
CREATE TABLE DARBUOTOJAI (
    id INT NOT NULL AUTO_INCREMENT,
    LoginName VARCHAR(255) NOT NULL,
    LoginPassword VARCHAR(255) NOT NULL,
    Vardas VARCHAR(255) NOT NULL,
    Pavarde VARCHAR(999), 
    Pareigos VARCHAR(100) NOT NULL, 
    PRIMARY KEY (ID));
    
    
use psi_database;   
INSERT INTO DARBUOTOJAI values (
default, 
'DoMa',
'admin',
'Dovydas', 
'Maèiulis',
'Inzinierius'
);
use psi_database;  
INSERT INTO DARBUOTOJAI values (
default,
'PaPe',
'admin',
'Paulius', 
'Petkus',
'Inzinierius'
); 
use psi_database;
INSERT INTO DARBUOTOJAI values (
default, 
'KaKl',
'admin', 
'Karolis', 
'Kleiba',
'Inzinierius'
);
use psi_database;
INSERT INTO DARBUOTOJAI values (
default,
'OsZi',
'admin',
'Osvaldas', 
'Ziukas',
'Inzinierius'
);
use psi_database;
INSERT INTO DARBUOTOJAI values (
default,
'LiKa',
'admin',
'Linas', 
'Kavaliauskas',
'Inzinierius'
);
use psi_database;
INSERT INTO DARBUOTOJAI values (
default,
'KrLa',
'admin',
'Kristina', 
'Lapin',
'Grazuole'
);  