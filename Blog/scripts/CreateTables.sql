CREATE TABLE IF NOT EXISTS BYOB.Role
(
    roleid bigint NOT NULL AUTO_INCREMENT,
    role varchar(12) NOT NULL,
    PRIMARY KEY (roleid)
);


CREATE TABLE IF NOT EXISTS BYOB.User
(
    userid varchar(12) NOT NULL,
    password varchar(50) NOT NULL,
    PRIMARY KEY (userid)
);

CREATE TABLE IF NOT EXISTS BYOB.User_role
(
    userid varchar(12) NOT NULL,
    rolegroup varchar(12) NOT NULL DEFAULT "Roles",
	roleid bigint NOT NULL,
    PRIMARY KEY (userid, rolegroup),
	FOREIGN KEY (roleid) references BYOB.Role(roleid),
	FOREIGN KEY (userid) references BYOB.User(userid)
);

CREATE TABLE IF NOT EXISTS BYOB.Post
(
	postid bigint NOT NULL AUTO_INCREMENT,
    postdate datetime NOT NULL,
	posttext text NOT NULL,
	posttitle varchar(100) NOT NULL,
	userid varchar(12) NOT NULL,	
	postlastupdate datetime NOT NULL,
    PRIMARY KEY (postid),
	FOREIGN KEY (userid) references BYOB.User(userid)
);

CREATE TABLE IF NOT EXISTS BYOB.Category
(
	categoryid bigint NOT NULL AUTO_INCREMENT,
	categorylabel varchar(20) NOT NULL,
    PRIMARY KEY (categoryid)
);

CREATE TABLE IF NOT EXISTS BYOB.Comment
(
	commentid bigint NOT NULL AUTO_INCREMENT,
	commenttext text NOT NULL,
	commentmail varchar(40) NOT NULL,
	commentautor varchar(12) NOT NULL,
	commentdate datetime NOT NULL,
	postid bigint NOT NULL,
    PRIMARY KEY (commentid),
	FOREIGN KEY (postid) references BYOB.Post(postid)
);

CREATE TABLE IF NOT EXISTS BYOB.Post_category
(
	postid bigint NOT NULL,
	categoryid bigint NOT NULL,
	PRIMARY KEY (postid,categoryid),
	FOREIGN KEY (postid) references BYOB.Post(postid),
	FOREIGN KEY (categoryid) references BYOB.Category(categoryid)
);

CREATE TABLE IF NOT EXISTS BYOB.Link
(
	linkid bigint NOT NULL AUTO_INCREMENT,
	linktext text NOT NULL,
	linkurl varchar(255) NOT NULL,
	userid varchar(12) NOT NULL,	
	linktitle varchar(100) NOT NULL,
    PRIMARY KEY (linkid),
	FOREIGN KEY (userid) references BYOB.User(userid)
);

CREATE TABLE IF NOT EXISTS BYOB.Link_category
(
	linkid bigint NOT NULL,
	categoryid bigint NOT NULL,
	PRIMARY KEY (linkid,categoryid),
	FOREIGN KEY (linkid) references BYOB.Link(linkid),
	FOREIGN KEY (categoryid) references BYOB.Category(categoryid)
);

CREATE TABLE IF NOT EXISTS BYOB.Profil
(
	profilid bigint NOT NULL AUTO_INCREMENT,
	profilpresentation text ,
	profilphoto text ,
	profilsite varchar(255) ,
	profilmail varchar(40) NOT NULL,
	profildate datetime NOT NULL,
	profilinscription datetime NOT NULL,
	profilcss varchar(20) NOT NULL,
	userid varchar(12) NOT NULL,	
	profilstatut varchar(250),
    PRIMARY KEY (profilid),
	FOREIGN KEY (userid) references BYOB.User(userid)
);