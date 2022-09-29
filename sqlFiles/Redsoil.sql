create database redsoilDB;

use redsoilDB;

create table company(
	ID int NOT NULL auto_increment,
    Company_Name varchar(1024) not null,
    Company_Address text(2048) not null,
    Company_Phone varchar(10) not null,
    primary key(id)
    );
    
insert into company(id, Company_Name, Company_Address, Company_Phone) 
values(null, "Haitomns Blood Bank", "Knowhere", "9800000000");

create table login (
	ID int  NOT NULL AUTO_INCREMENT,
    company_id int NOT NULL,
    username varchar(20) not null, 
    password varchar(50) not null,
    PRIMARY KEY (ID),
    FOREIGN KEY(company_id) REFERENCES company(id)
    );
    
INSERT INTO login (id, company_id, username, password) 
VALUES(null, 1,'haitomns', 'aad0ad135729292b9f0fc454ab7e0568820231b8');

create table bloodDonationUserData(
	ID int unsigned NOT NULL auto_increment,
    Blood_Donation_Orgnization varchar(200),
    Donor_Name varchar(50) NOT NULL,
    Gender varchar(8),
    Age TINYINT unsigned,
    Occupation varchar(100),
    Address varchar(128),
    Phone varchar(10),
    Email varchar(320),
    Patient_Name varchar(50) NOT NULL,
    Donor_ID varchar(100) NOT NULL unique,
    Date_Of_Creation datetime default current_timestamp,
    primary key(ID)
    );

create table bloodDonationTestingDetails(
	ID int unsigned NOT NULL auto_increment,
    Donor_ID int unsigned NOT NULL,
    Previously_Donated bool,
    Previously_Donated_Date date,
    Diseases varchar(512),
    Weight varchar(128),
    BP varchar(128),
    HB varchar(128),
    Resp_Sys varchar(128),
    Cvs varchar(128),
    Gi_System varchar(128),
    Other varchar(128),
    Fit varchar(128),
    Unit varchar(128),
    ABO varchar(128),
    RH varchar(128),
    HIV varchar(128),
    HBsAg varchar(128),
    HCV varchar(128),
    VDRL varchar(128),
    Expiry_date date,
    primary key(ID),
    FOREIGN KEY(Donor_ID) REFERENCES bloodDonationUserData(ID) ON DELETE CASCADE
);


CREATE VIEW bloodTypesTotal AS
SELECT count(ABO) FROM blooddonationtestingdetails inner JOIN blooddonationuserdata
ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID 
inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID 
where abo = "A" AND rh = "+" AND discard_blood = "No"  AND Expiry_date > current_date()
union all
SELECT count(ABO) FROM blooddonationtestingdetails inner JOIN blooddonationuserdata
ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID 
inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID 
where abo = "A" AND rh = "-" AND discard_blood = "No"  AND Expiry_date > current_date()
union all
SELECT count(ABO) FROM blooddonationtestingdetails inner JOIN blooddonationuserdata
ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID 
inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID 
where abo = "B" AND rh = "+" AND discard_blood = "No"  AND Expiry_date > current_date()
union all
SELECT count(ABO) FROM blooddonationtestingdetails inner JOIN blooddonationuserdata
ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID 
inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID 
where abo = "B" AND rh = "-" AND discard_blood = "No"  AND Expiry_date > current_date()
union all
SELECT count(ABO) FROM blooddonationtestingdetails inner JOIN blooddonationuserdata
ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID 
inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID 
where abo = "AB" AND rh = "+" AND discard_blood = "No"  AND Expiry_date > current_date()
union all
SELECT count(ABO) FROM blooddonationtestingdetails inner JOIN blooddonationuserdata
ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID 
inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID 
where abo = "AB" AND rh = "-" AND discard_blood = "No"   AND Expiry_date > current_date()
union all
SELECT count(ABO) FROM blooddonationtestingdetails inner JOIN blooddonationuserdata
ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID 
inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID 
where abo = "O" AND rh = "+" AND discard_blood = "No"  AND Expiry_date > current_date()
union all
SELECT count(ABO) FROM blooddonationtestingdetails inner JOIN blooddonationuserdata
ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID 
inner JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID 
where abo = "O" AND rh = "-" AND discard_blood = "No"  AND Expiry_date > current_date();

CREATE VIEW bloodStautsTotal AS
SELECT count(blooddonationuserdata.Donor_ID) as donorTotal FROM blooddonationuserdata 
inner JOIN blooddonationtestingdetails  
ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID
union all
SELECT count(blooddonationuserdata.Donor_ID) as donorTotal FROM blooddonationuserdata inner 
JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner 
JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where discard_blood = "No"  AND Expiry_date > current_date()
union all
SELECT count(blooddonationuserdata.Donor_ID) FROM blooddonationuserdata 
inner JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID 
where blooddonationtestingdetails.Expiry_date < current_date()
union all
Select sum(Unit) as donorTotal FROM blooddonationuserdata inner 
JOIN blooddonationtestingdetails  ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID inner 
JOIN bloodcomponent on blooddonationuserdata.ID = bloodcomponent.Donor_ID where Expiry_date < current_date() AND discard_blood != "No";

create table bloodComponent(
	ID int unsigned NOT NULL auto_increment,
    Donor_ID int unsigned NOT NULL,
    prbcs varchar(128),
    prbc varchar(128),
    ffp varchar(128),
    platelets varchar(128),
    prp varchar(128),
    cryoppt varchar(128),
    discard_blood varchar(128),
    primary key(ID),
    FOREIGN KEY(Donor_ID) REFERENCES bloodDonationUserData(ID) ON DELETE CASCADE
);

DELIMITER //
CREATE PROCEDURE bloodcompInit()
BEGIN
	SET @donorGet_id =  (select id FROM blooddonationuserdata ORDER BY ID DESC LIMIT 1);
	INSERT INTO `redsoildb`.`bloodcomponent`
	(`ID`,`Donor_ID`,`prbcs`,`prbc`,`ffp`,`platelets`,`prp`,`cryoppt`,`discard_blood`) 
    VALUES(null, @donorGet_id, "No", "No", "No", "No", "No", "No", "No");
END //
DELIMITER ;

DELIMITER $$
create trigger bloodcompInitTRG
after insert on blooddonationuserdata
for each row
begin
	call bloodcompInit();
end $$
DELIMITER ;