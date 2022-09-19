create database redsoilDB;

create table login (
	ID int  NOT NULL AUTO_INCREMENT,
    company_id int NOT NULL,
    username varchar(20) not null, 
    password varchar(50) not null,
    PRIMARY KEY (ID),
    FOREIGN KEY(company_id) REFERENCES company(id)
    );
    
INSERT INTO login (id, company_id, username, password) VALUES(null, 1,'haitomns', '123');

SELECT username, password from login where username like 'haitomns' and password like '123';

update login set password = "12345" where id = 1;

create table company(
	ID int NOT NULL auto_increment,
    Company_Name varchar(1024) not null,
    Company_Address text(2048) not null,
    Company_Phone varchar(10) not null,
    primary key(id)
    );
    
SELECT * FROM company;

insert into company(id, Company_Name, Company_Address, Company_Phone) 
values(null, "Haitomns Blood Bank", "Knowhere", "9800000000");

UPDATE company SET Company_Name = '"+ companyName + "', Company_Address = '" + companyAddress + "', Company_Phone = '" + companyPhone + "' WHERE ID = 1;
 
UPDATE login SET username = '" + companyUsername + "', password = '" + companyPassword + "' WHERE ID = 1;

select Company_Name, Company from company;

select username, password from login; 

create table bloodDonationUserData(
	ID int unsigned NOT NULL auto_increment,
    Doner_ID varchar(100) NOT NULL,
    Doner_Name varchar(50) NOT NULL,
    Date_Of_Birth date,
    Age TINYINT unsigned,
    Gender varchar(8),
    Occupation varchar(100),
    Address varchar(128),
    Phone varchar(10),
    Email varchar(320),
    Date_Of_Creation datetime default current_timestamp,
    primary key(ID)
    );

insert into blooddonationuserdata (Doner_ID, Doner_Name, Date_Of_Birth, Age, Gender, Occupation, Address, Phone, Email)
value (1, "Alok Gupta", "2022-4-6", 42, "male", "Nothing", "Knowhere", "9845011214", "email.com");

drop table blooddonationtestingdetails;
drop table blooddonationuserdata;

create table bloodDonationTestingDetails(
	ID int unsigned NOT NULL auto_increment,
    Donor_ID int unsigned NOT NULL,
    Previously_Donated bool,
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
    primary key(ID),
    FOREIGN KEY(Doner_ID) REFERENCES bloodDonationUserData(ID)
);

INSERT INTO `redsoildb`.`blooddonationtestingdetails`(`ID`, `Doner_ID`, `Previously_Donated`, `Diseases`, `Weight`, `BP`, `HB`, `Resp_Sys`, `Cvs`, `Gi_System`, `Other`, `Fit`, `Unit`, `ABO`, `RH`, `HIV`, `HBsAg`, `HCV`, `VDRL`)VALUES();


SELECT blooddonationuserdata.Doner_ID, Doner_Name, Gender, Phone, Diseases, Weight, ABO, RH, HIV, HBsAg, HCV, VDRL, Date_Of_Creation 
FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  
ON blooddonationuserdata.ID = blooddonationtestingdetails.Doner_ID;