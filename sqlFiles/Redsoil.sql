create database redsoilDB;

create table login (
	ID int  NOT NULL AUTO_INCREMENT,
    company_id int NOT NULL,
    username varchar(20) not null, 
    password varchar(50) not null,
    PRIMARY KEY (ID),
    FOREIGN KEY(company_id) REFERENCES company(id)
    );
    
INSERT INTO login (id, company_id, username, password) VALUES(null, 1,'haitomns', '12345');

create table company(
	ID int NOT NULL auto_increment,
    Company_Name varchar(1024) not null,
    Company_Address text(2048) not null,
    Company_Phone varchar(10) not null,
    primary key(id)
    );
    
insert into company(id, Company_Name, Company_Address, Company_Phone) 
values(null, "Haitomns Blood Bank", "Knowhere", "9800000000");

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
    Donor_ID varchar(100) NOT NULL,
    Date_Of_Creation datetime default current_timestamp,
    primary key(ID)
    );

INSERT INTO `redsoildb`.`blooddonationuserdata`(`ID`,`Blood_Donation_Orgnization`,`Donor_Name`,`Gender`,`Age`,`Occupation`,`Address`,`Phone`,`Email`,`Patient_Name`,`Donor_ID`,`Date_Of_Creation`)VALUES();

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
    FOREIGN KEY(Donor_ID) REFERENCES bloodDonationUserData(ID)
);

SELECT blooddonationuserdata.Donor_ID, Donor_Name, Phone, ABO, RH, Unit, Date_Of_Creation, Expiry_date 
FROM blooddonationuserdata inner JOIN blooddonationtestingdetails  
ON blooddonationuserdata.ID = blooddonationtestingdetails.Donor_ID;

drop table blooddonationtestingdetails;
drop table blooddonationuserdata;








