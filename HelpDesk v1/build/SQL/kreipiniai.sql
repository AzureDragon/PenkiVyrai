use psi_database;
;--lenteles sukurimas
CREATE TABLE KREIPINIAI (
    id INT NOT NULL AUTO_INCREMENT, 
    TEMA VARCHAR(255) NOT NULL,
    APRASYMAS VARCHAR(999), 
    TIPAS VARCHAR(100) NOT NULL, 
    BUSENA VARCHAR(100) NOT NULL, 
    KLIENTAS VARCHAR(70) NOT NULL,
    UZREGISTRUOTA DATE NOT NULL,
    PRISKIRTA  VARCHAR(100) NOT NULL, 
    PRIMARY KEY (ID));

--duomenu sukurimas    
use psi_database;
INSERT INTO KREIPINIAI values (
default, 
'Nesudeti validacijos pranesimai', 
'Prisijungus prie puslapio, ir pasirinkus mano hdd, prie lauko data leidzia ivesti betkokia reiksme',
'Incidentas',
'Neiðspræstas', 
'UAB Insoft',
'2012-12-01',
'Dovydui');   

use psi_database;
INSERT INTO KREIPINIAI values (
default, 
'Sugedo hdd', 
'Siandien pirma valanda po pietu, valgydamas arbuzus paspringau ir netycia istryniaus visus partitionus. Po to uzsipisau kariaut ir nutariau, kad reikia paspardyti kompiuteri. Taciau tai nepadejo. ',
'Incidentas',
'Neiðspræstas', 
'UAB Insoft',
'2012-12-01',
'Linui');   

use psi_database;
INSERT INTO KREIPINIAI values (
default, 
'Skauda ranka', 
'Problema atsirado, kai pradejau kandzioti sau i ranka. Gal galite patarti, kaip reiktu isvengti skausmo?',
'Paklausimas',
'Neiðspræstas', 
'UAB Insoft',
'2012-12-01',
'Dovydui'); 

use psi_database;
INSERT INTO KREIPINIAI values (default, 'Normali tema', 'Normalus apraðymas', 'Uþklausimas', 'Sprendþiamas', 'Klientas', '2013/10/26 20:11', 'Karolis Kleiba');


--priskirti kreipini darbuotojui
UPDATE KREIPINIAI SET PRISKIRTA="Paulius" WHERE ID=2