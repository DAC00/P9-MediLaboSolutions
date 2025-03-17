CREATE TABLE Patient (
  id Integer PRIMARY KEY NOT NULL auto_increment,
  lastName VARCHAR(125) NOT NULL,
  firstName VARCHAR(125) NOT NULL,
  birthdate date NOT NULL,
  gender VARCHAR(1) NOT NULL,
  address VARCHAR(125),
  phoneNumber VARCHAR(125)
);

insert into Patient(lastName,firstName,birthdate,gender,address,phoneNumber) values ('TestNone','Test','1966-12-03','F','1 Brookside St','100-222-3333');
insert into Patient(lastName,firstName,birthdate,gender,address,phoneNumber) values ('TestBorderline','Test','1945-06-02','M','2 High St','200-333-4444');
insert into Patient(lastName,firstName,birthdate,gender,address,phoneNumber) values ('TestInDanger','Test','2004-06-18','M','3 Club Road','300-444-5555');
insert into Patient(lastName,firstName,birthdate,gender,address,phoneNumber) values ('TestEarlyOnset','Test','2002-06-02','F','4 Valley Dr','400-555-6666');