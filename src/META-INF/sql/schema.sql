create table CONTACT(
	id INT NOT NULL AUTO_INCREMENT,
        fname VARCHAR(32),
	mi VARCHAR(32),
	lname  VARCHAR(32),
        title VARCHAR(32),
	SPOUSE_lname  VARCHAR(32),
        SPOUSE_fname VARCHAR(32),
	SPOUSE_mi VARCHAR(32),
        SPOUSE_title VARCHAR(32),

	unit  VARCHAR(32),
	streetNumber  VARCHAR(32),
	streetName  VARCHAR(32),
	bldgcplxname  VARCHAR(32),
	city  VARCHAR(32),
	state  VARCHAR(32),
	zip  VARCHAR(32),
	country  VARCHAR(32),

	unit2  VARCHAR(32),
	streetNumber2  VARCHAR(32),
	streetName2  VARCHAR(32),
	bldgcplxname2  VARCHAR(32),
	city2  VARCHAR(32),
	state2  VARCHAR(32),
	zip2  VARCHAR(32),
	country2  VARCHAR(32),
	empposition varchar(32),

	anniversaryDate date,
	birthday date,
    	note varchar(512),
    	email1 varchar(32),
    	email2 varchar(32),
    	spouseemail varchar(32),
    	fax varchar(32),

	language varchar(32),
    	source varchar(32),
    	preferedPhone varchar(32),

    	clientsite varchar(32),
	hphone varchar(32),
    	cphone varchar(32),
    	wphone varchar(32),
    	hphone2 varchar(32),
	primary key (ID)
);

create table web_lead(
	id INT NOT NULL,
  zip4 varchar(4),
 	primary key(ID)
);