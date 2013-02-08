ALTER TABLE BYOB.Post
ADD postlastupdate datetime NOT NULL;

UPDATE BYOB.Post
SET postlastupdate = postdate;
