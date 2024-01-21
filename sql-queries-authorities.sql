CREATE DATABASE api_security_db;

USE api_security_db;

GRANT ALL ON api_security_db.* TO 'root'@'%';

CREATE TABLE USERS (
	USERNAME VARCHAR(128) PRIMARY KEY,
    PASSWORD VARCHAR(128) NOT NULL,
    ENABLED CHAR(1) NOT NULL CHECK (ENABLED IN ('Y','N')) 
);

INSERT INTO `api_security_db`.`users`
(`USERNAME`,
`PASSWORD`,
`ENABLED`)
VALUES
("user1",
"$2y$12$4bavRcFDK8vjIUweLwetZuxnsBGcQqETtoX866/Ztli9Xjsc8PHbm",
'Y');

INSERT INTO `api_security_db`.`users`
(`USERNAME`,
`PASSWORD`,
`ENABLED`)
VALUES
("admin",
"$2y$12$4bavRcFDK8vjIUweLwetZuxnsBGcQqETtoX866/Ztli9Xjsc8PHbm",
'Y');

-- CREATE TABLE AUTHORITIES (
-- 	AUTHORITY_ID INT PRIMARY KEY,	
--     USERNAME VARCHAR(128) NOT NULL,
--     AUTHORITY VARCHAR(128) NOT NULL
-- );

ALTER TABLE AUTHORITIES ADD CONSTRAINT USER_ROLE_UNIQUE UNIQUE (USERNAME, AUTHORITY);
ALTER TABLE AUTHORITIES ADD CONSTRAINT USER_ROLE_FK1 FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME);

INSERT INTO `api_security_db`.`AUTHORITIES`(`AUTHORITY_ID`,`USERNAME`,`AUTHORITY`)
VALUES (1, "admin", "ADMIN");

INSERT INTO `api_security_db`.`AUTHORITIES` (`AUTHORITY_ID`,`USERNAME`,`AUTHORITY`)
VALUES (2, "user1", "USER");

CREATE TABLE ROLES (
    ROLE VARCHAR(128) NOT NULL PRIMARY KEY
);

CREATE TABLE USER_ROLE (
	USER_ROLE_ID INT PRIMARY KEY,	
    USERNAME VARCHAR(128) NOT NULL,
    ROLE VARCHAR(128) NOT NULL
);

ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_UNIQUE UNIQUE (USERNAME, ROLE);
ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_FK1 FOREIGN KEY (USERNAME) REFERENCES USERS (USERNAME);
ALTER TABLE USER_ROLE ADD CONSTRAINT USER_ROLE_FK2 FOREIGN KEY (ROLE) REFERENCES ROLES (ROLE);

CREATE TABLE AUTHORITIES (
	AUTHORITY_ID INT PRIMARY KEY,
    ROLE VARCHAR(128) NOT NULL,
    AUTHORITY VARCHAR(128) NOT NULL
);

ALTER TABLE AUTHORITIES ADD CONSTRAINT AUTHORITIES_UNIQUE UNIQUE (ROLE, AUTHORITY);
ALTER TABLE AUTHORITIES ADD CONSTRAINT AUTHORITIES_FK1 FOREIGN KEY (ROLE) REFERENCES ROLES (ROLE);

INSERT INTO `api_security_db`.`roles` (`ROLE`)
VALUES ("ROLE_ADMIN");

INSERT INTO `api_security_db`.`roles` (`ROLE`)
VALUES ("ROLE_USER");

INSERT INTO `api_security_db`.`USER_ROLE` (`USER_ROLE_ID`,`USERNAME`,`ROLE`)
VALUES (1, "admin", "ROLE_ADMIN");

INSERT INTO `api_security_db`.`USER_ROLE` (`USER_ROLE_ID`,`USERNAME`,`ROLE`)
VALUES (2, "user1", "ROLE_USER");

-- INSERT INTO `api_security_db`.`AUTHORITIES` (`AUTHORITY_ID`,`ROLE`,`AUTHORITY`)
-- VALUES (1, "ROLE_ADMIN", "ADD_BOOK");

INSERT INTO `api_security_db`.`AUTHORITIES` (`AUTHORITY_ID`,`ROLE`,`AUTHORITY`)
VALUES (2, "ROLE_ADMIN", "CREATE_BOOK");

INSERT INTO `api_security_db`.`AUTHORITIES` (`AUTHORITY_ID`,`ROLE`,`AUTHORITY`)
VALUES(3,"ROLE_USER","GET_BOOK");

--Joining Tables
create database Udemy;
use Udemy;
create table university(university_id int primary key,university_name varchar(20) not null);
insert into university values(1,'MIT'),(2,'Harvard');

create table student (student_id int primary key,student_name varchar(30) not null default '',student_age tinyint not null,university int,foreign key (university) references university(university_id));
insert into student values(1,'Kevin',19,null),(2,'Joe',22,null),(3,'Anna',23,1),(4,'Adam',29,1),(5,'Daniel',25,2),(6,'Ariana',43,null),(7,'Michele',67,null);
select * from student;
select * from university;
--Inner join
SELECT Student.student_name, UNIVERSITY.university_name FROM 
Student INNER JOIN UNIVERSITY ON Student.university = 
UNIVERSITY.university_id;

--Left join
SELECT Student.student_name, UNIVERSITY.university_name FROM 
Student LEFT JOIN UNIVERSITY ON Student.university = 
UNIVERSITY.university_id;

--Right join
SELECT Student.student_name, UNIVERSITY.university_name FROM 
Student RIGHT JOIN UNIVERSITY ON Student.university = 
UNIVERSITY.university_id;

--Full join
SELECT Student.student_name, UNIVERSITY.university_name FROM 
Student FULL JOIN UNIVERSITY ON Student.university = 
UNIVERSITY.university_id;

--online w3school sql editor
SELECT c.CustomerName, o.OrderDate
FROM Customers AS c
JOIN Orders AS o ON c.CustomerID = o.CustomerID;

SELECT c.CustomerName, o.OrderDate
FROM Customers AS c
JOIN Orders AS o ON c.CustomerID = o.CustomerID ORDER BY CustomerName;

SELECT c.CustomerName, COUNT(o.CustomerID) AS NumOfOrders
FROM Customers AS c
JOIN Orders AS o ON o.CustomerID = c.CustomerID GROUP BY o.CustomerID ORDER BY NumOfOrders DESC;

SELECT c.Country, COUNT(o.CustomerID) AS NumOfOrders
FROM Customers AS c
JOIN Orders AS o ON c.CustomerID = o.CustomerID GROUP BY o.Country ORDER BY NumOfOrders DESC;

