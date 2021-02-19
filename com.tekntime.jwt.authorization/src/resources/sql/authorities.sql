CREATE TABLE IF NOT EXISTS authorities (
  id [int] NOT NULL  PRIMARY KEY,
  userid [int] NOT NULL ,
  username VARCHAR(256) NOT NULL,
  authority VARCHAR(256) NOT NULL
);


--INSERT INTO `authorities`(`username`, `authority`, `id`, `userid`) VALUES ('user','MANAGER',2,1);