CREATE TABLE Patient (
  id Integer PRIMARY KEY NOT NULL AUTO_INCREMENT,
  lastName VARCHAR(125) NOT NULL,
  firstName VARCHAR(125) NOT NULL,
  birthdate DATE NOT NULL,
  gender VARCHAR(1) NOT NULL,
  address VARCHAR(125),
  phoneNumber VARCHAR(125)
);