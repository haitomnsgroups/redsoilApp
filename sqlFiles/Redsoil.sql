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

select username, password from login 