--select * from SYS.SYSTABLES t where t.tablename not like 'SYS%'
--drop table

CREATE TABLE USERS (
    ID_USER       	       INT          NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    NOM_USER      	       VARCHAR (25) NOT NULL,
    PRENOM_USER            VARCHAR (25) NOT NULL,
    EMAIL_USER             VARCHAR (25) NOT NULL,
    DATE_OF_BIRTH          DATE         NOT NULL,
    SEXE                   VARCHAR (1)  NOT NULL,
    USERNAME               VARCHAR (15) NOT NULL,
    PASSWORD_USER      	   VARCHAR (15) NOT NULL,
    ADDRESS_USER  	   	   VARCHAR (70) NOT NULL,
    USER_CREATION_DATE     DATE         NOT NULL,
    USER_MODIFICATION_DATE DATE         NOT NULL,
    CONSTRAINT PK_Users PRIMARY KEY (ID_USER)
);

CREATE TABLE FILM_CATEGORIE (
    ID_FILM_CATEGORIE     INT          NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    LIBELLE_CATEGORIE     VARCHAR (50) NOT NULL,
    CONSTRAINT PK_FILM_CATEGORIE PRIMARY KEY (ID_FILM_CATEGORIE)
);


CREATE TABLE GROUPE (
    ID_GROUPE            INT           NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    NOM_GROUPE           VARCHAR (50)  NOT NULL,
    ADMIN_MESSAGE_GROUPE VARCHAR (250) NOT NULL,
    WATCHING_DATE        DATE          NOT NULL,
    ID_ADMIN             INT           NOT NULL,
    GROUPE_CREATION_DATE DATE          NOT NULL,
    CONSTRAINT PK_ID_GROUPE    PRIMARY KEY (ID_GROUPE),
    CONSTRAINT FK_GROUPE_ADMIN FOREIGN KEY (ID_ADMIN) REFERENCES USERS(ID_USER)
);

CREATE TABLE FILM (
	ID_FILM           INT           NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	NOM_FILM          VARCHAR (50)  NOT NULL,
	DESCRIPTION_FILM  VARCHAR (500) NOT NULL,
	DATE_RELEASED     DATE          NOT NULL,
	NOTATION_FILM     INT 			NOT NULL,
	TRAILER_FILM_LINK VARCHAR (50)  NOT NULL,
	FILM_LINK         VARCHAR (50)  NOT NULL,
	ID_CATEGORIE_FILM INT           NOT NULL,
    FILM_CREATION_DATE     DATE         NOT NULL,
    FILM_MODIFICATION_DATE DATE         NOT NULL,
	CONSTRAINT PK_Film             PRIMARY KEY (ID_FILM),
    CONSTRAINT FK_FILM_CATEGORIE   FOREIGN KEY (ID_CATEGORIE_FILM) REFERENCES FILM_CATEGORIE(ID_FILM_CATEGORIE)
);


CREATE TABLE USER_APPARTIENT_GROUPE (
   ID_APPARTIENT        INT           NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
   ID_USER_APPARTIENT   INT           NOT NULL,
   ID_GROUPE_APPARTIENT INT           NOT NULL,
   USER_ADDED_DATE      DATE          NOT NULL,
   USER_MESSAGE         VARCHAR (250) NOT NULL,
   USER_DATE_MESSAGE    DATE          NOT NULL,
   USER_WATCHING        DATE          NOT NULL,
   CONSTRAINT PK_APPARTIENT        PRIMARY KEY (ID_APPARTIENT),
   CONSTRAINT FK_APPARTIENT_USER   FOREIGN KEY (ID_USER_APPARTIENT)   REFERENCES USERS(ID_USER),
   CONSTRAINT FK_APPARTIENT_GROUPE FOREIGN KEY (ID_GROUPE_APPARTIENT) REFERENCES GROUPE(ID_GROUPE)
);

CREATE TABLE FILM_CONTIENT_GROUPE (
   ID_CONTIENT        INT  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), 
   ID_FILM_CONTIENT   INT  NOT NULL,
   ID_GROUPE_CONTIENT INT  NOT NULL,
   FILM_DATE          DATE NOT NULL,
   CONSTRAINT PK_CONTIENT        PRIMARY KEY (ID_CONTIENT),
   CONSTRAINT FK_CONTIENT_FILM   FOREIGN KEY (ID_FILM_CONTIENT)   REFERENCES FILM(ID_FILM),
   CONSTRAINT FK_CONTIENT_GROUPE FOREIGN KEY (ID_GROUPE_CONTIENT) REFERENCES GROUPE(ID_GROUPE)
);

CREATE TABLE FRIENDS (
   ID_FRIENDS    INT  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
   ID_USER_ONE   INT  NOT NULL,
   ID_USER_TWO   INT  NOT NULL,
   FRIENDS_DATE  DATE NOT NULL,
   CONSTRAINT PK_FRIENDS            PRIMARY KEY (ID_FRIENDS),
   CONSTRAINT FK_FRIENDS_USER_ONE   FOREIGN KEY (ID_USER_ONE)   REFERENCES USERS(ID_USER),
   CONSTRAINT FK_FRIENDS_USER_TWO   FOREIGN KEY (ID_USER_TWO)   REFERENCES USERS(ID_USER)
);