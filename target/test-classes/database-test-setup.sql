DROP TABLE "NEWS_SCHEMA_TEST"."AUTHOR" cascade constraints;
DROP TABLE "NEWS_SCHEMA_TEST"."COMMENTS" cascade constraints;
DROP TABLE "NEWS_SCHEMA_TEST"."NEWS" cascade constraints;
DROP TABLE "NEWS_SCHEMA_TEST"."NEWS_AUTHOR" cascade constraints;
DROP TABLE "NEWS_SCHEMA_TEST"."NEWS_TAG" cascade constraints;
DROP TABLE "NEWS_SCHEMA_TEST"."NEWS_USER" cascade constraints;
DROP TABLE "NEWS_SCHEMA_TEST"."ROLE" cascade constraints;
DROP TABLE "NEWS_SCHEMA_TEST"."TAG" cascade constraints;
DROP SEQUENCE "NEWS_SCHEMA_TEST"."AUTHOR_AI";
DROP SEQUENCE "NEWS_SCHEMA_TEST"."AUTO_INCREMENT";
DROP SEQUENCE "NEWS_SCHEMA_TEST"."COMMENTS_AI";
DROP SEQUENCE "NEWS_SCHEMA_TEST"."NEWS_AI";
DROP SEQUENCE "NEWS_SCHEMA_TEST"."NEWS_AUTHOR_AI";
DROP SEQUENCE "NEWS_SCHEMA_TEST"."NEWS_TAG_AI";
DROP SEQUENCE "NEWS_SCHEMA_TEST"."NEWS_USER_AI";
DROP SEQUENCE "NEWS_SCHEMA_TEST"."TAG_AI";
--------------------------------------------------------
--  Sequence AUTHOR_AI
--------------------------------------------------------

   CREATE SEQUENCE  "NEWS_SCHEMA_TEST"."AUTHOR_AI"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 141 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  Sequence AUTO_INCREMENT
--------------------------------------------------------

   CREATE SEQUENCE  "NEWS_SCHEMA_TEST"."AUTO_INCREMENT"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 181 CACHE 10 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  Sequence COMMENTS_AI
--------------------------------------------------------

   CREATE SEQUENCE  "NEWS_SCHEMA_TEST"."COMMENTS_AI"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 101 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  Sequence NEWS_AI
--------------------------------------------------------

   CREATE SEQUENCE  "NEWS_SCHEMA_TEST"."NEWS_AI"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 601 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  Sequence NEWS_AUTHOR_AI
--------------------------------------------------------

   CREATE SEQUENCE  "NEWS_SCHEMA_TEST"."NEWS_AUTHOR_AI"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 621 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  Sequence NEWS_TAG_AI
--------------------------------------------------------

   CREATE SEQUENCE  "NEWS_SCHEMA_TEST"."NEWS_TAG_AI"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1901 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  Sequence NEWS_USER_AI
--------------------------------------------------------

   CREATE SEQUENCE  "NEWS_SCHEMA_TEST"."NEWS_USER_AI"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 21 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  Sequence TAG_AI
--------------------------------------------------------

   CREATE SEQUENCE  "NEWS_SCHEMA_TEST"."TAG_AI"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 81 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  Table AUTHOR
--------------------------------------------------------

  CREATE TABLE "NEWS_SCHEMA_TEST"."AUTHOR"
   (	"AUTHOR_ID" NUMBER(20,0),
	"AUTHOR_NAME" NVARCHAR2(30),
	"EXPIRED" TIMESTAMP (6)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Table COMMENTS
--------------------------------------------------------

  CREATE TABLE "NEWS_SCHEMA_TEST"."COMMENTS"
   (	"COMMENT_ID" NUMBER(20,0),
	"COMMENT_TEXT" NVARCHAR2(100),
	"CREATION_DATE" TIMESTAMP (6),
	"NEWS_ID" NUMBER(20,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Table NEWS
--------------------------------------------------------

  CREATE TABLE "NEWS_SCHEMA_TEST"."NEWS"
   (	"NEWS_ID" NUMBER(20,0),
	"SHORT_TEXT" NVARCHAR2(100),
	"FULL_TEXT" NVARCHAR2(2000),
	"TITLE" NVARCHAR2(30),
	"CREATION_DATE" TIMESTAMP (6),
	"MODIFICATION_DATE" DATE
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Table NEWS_AUTHOR
--------------------------------------------------------

  CREATE TABLE "NEWS_SCHEMA_TEST"."NEWS_AUTHOR"
   (	"NEWS_AUTHOR_ID" NUMBER(20,0),
	"AUTHOR_ID" NUMBER(20,0),
	"NEWS_ID" NUMBER(20,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Table NEWS_TAG
--------------------------------------------------------

  CREATE TABLE "NEWS_SCHEMA_TEST"."NEWS_TAG"
   (	"NEWS_TAG_ID" NUMBER(20,0),
	"NEWS_ID" NUMBER(20,0),
	"TAG_ID" NUMBER(20,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Table NEWS_USER
--------------------------------------------------------

  CREATE TABLE "NEWS_SCHEMA_TEST"."NEWS_USER"
   (	"USER_ID" NUMBER(20,0),
	"USER_NAME" NVARCHAR2(50),
	"LOGIN" VARCHAR2(30 BYTE),
	"PASSWORD" VARCHAR2(30 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Table ROLE
--------------------------------------------------------

  CREATE TABLE "NEWS_SCHEMA_TEST"."ROLE"
   (	"USER_ID" NUMBER(20,0),
	"ROLE_NAME" VARCHAR2(50 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Table TAG
--------------------------------------------------------

  CREATE TABLE "NEWS_SCHEMA_TEST"."TAG"
   (	"TAG_ID" NUMBER(20,0),
	"TAG_NAME" NVARCHAR2(30)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
REM INSERTING into NEWS_SCHEMA_TEST.AUTHOR
SET DEFINE OFF;
Insert into NEWS_SCHEMA_TEST.AUTHOR (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (1,'John',null);
Insert into NEWS_SCHEMA_TEST.AUTHOR (AUTHOR_ID,AUTHOR_NAME,EXPIRED) values (2,'Bill',null);
REM INSERTING into NEWS_SCHEMA_TEST.COMMENTS
SET DEFINE OFF;
Insert into NEWS_SCHEMA_TEST.COMMENTS (COMMENT_ID,COMMENT_TEXT,CREATION_DATE,NEWS_ID) values (1,'first',to_timestamp('30-JAN-15 10.55.55.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),1);
Insert into NEWS_SCHEMA_TEST.COMMENTS (COMMENT_ID,COMMENT_TEXT,CREATION_DATE,NEWS_ID) values (2,'second',to_timestamp('10-FEB-15 10.55.55.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),2);
Insert into NEWS_SCHEMA_TEST.COMMENTS (COMMENT_ID,COMMENT_TEXT,CREATION_DATE,NEWS_ID) values (3,'third',to_timestamp('12-JAN-15 10.55.55.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),2);
Insert into NEWS_SCHEMA_TEST.COMMENTS (COMMENT_ID,COMMENT_TEXT,CREATION_DATE,NEWS_ID) values (4,'fourth',to_timestamp('12-JUL-15 10.55.55.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),3);
Insert into NEWS_SCHEMA_TEST.COMMENTS (COMMENT_ID,COMMENT_TEXT,CREATION_DATE,NEWS_ID) values (5,'fifth',to_timestamp('03-MAR-15 10.55.55.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),3);
Insert into NEWS_SCHEMA_TEST.COMMENTS (COMMENT_ID,COMMENT_TEXT,CREATION_DATE,NEWS_ID) values (6,'sixth',to_timestamp('11-NOV-15 10.55.55.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),3);
REM INSERTING into NEWS_SCHEMA_TEST.NEWS
SET DEFINE OFF;
Insert into NEWS_SCHEMA_TEST.NEWS (NEWS_ID,SHORT_TEXT,FULL_TEXT,TITLE,CREATION_DATE,MODIFICATION_DATE) values (1,'firstShort','firstFull','firstTitle',to_timestamp('15-MAR-15 10.55.55.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_date('15-MAR-15','DD-MON-RR'));
Insert into NEWS_SCHEMA_TEST.NEWS (NEWS_ID,SHORT_TEXT,FULL_TEXT,TITLE,CREATION_DATE,MODIFICATION_DATE) values (2,'secondShort','secondFull','secondTitle',to_timestamp('30-JAN-15 10.55.55.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_date('30-JAN-15','DD-MON-RR'));
Insert into NEWS_SCHEMA_TEST.NEWS (NEWS_ID,SHORT_TEXT,FULL_TEXT,TITLE,CREATION_DATE,MODIFICATION_DATE) values (3,'thirdShort','thirdFull','thirdTitle',to_timestamp('23-FEB-15 10.55.55.000000000 AM','DD-MON-RR HH.MI.SSXFF AM'),to_date('23-FEB-15','DD-MON-RR'));
REM INSERTING into NEWS_SCHEMA_TEST.NEWS_AUTHOR
SET DEFINE OFF;
Insert into NEWS_SCHEMA_TEST.NEWS_AUTHOR (NEWS_AUTHOR_ID,AUTHOR_ID,NEWS_ID) values (619,1,1);
Insert into NEWS_SCHEMA_TEST.NEWS_AUTHOR (NEWS_AUTHOR_ID,AUTHOR_ID,NEWS_ID) values (620,2,2);
REM INSERTING into NEWS_SCHEMA_TEST.NEWS_TAG
SET DEFINE OFF;
Insert into NEWS_SCHEMA_TEST.NEWS_TAG (NEWS_TAG_ID,NEWS_ID,TAG_ID) values (1893,2,2);
Insert into NEWS_SCHEMA_TEST.NEWS_TAG (NEWS_TAG_ID,NEWS_ID,TAG_ID) values (1891,1,1);
Insert into NEWS_SCHEMA_TEST.NEWS_TAG (NEWS_TAG_ID,NEWS_ID,TAG_ID) values (1892,1,3);
REM INSERTING into NEWS_SCHEMA_TEST.NEWS_USER
SET DEFINE OFF;
Insert into NEWS_SCHEMA_TEST.NEWS_USER (USER_ID,USER_NAME,LOGIN,PASSWORD) values (6,'first','FirstLogin','12345');
Insert into NEWS_SCHEMA_TEST.NEWS_USER (USER_ID,USER_NAME,LOGIN,PASSWORD) values (7,'second','SecondLogin','67890');
REM INSERTING into NEWS_SCHEMA_TEST.ROLE
SET DEFINE OFF;
REM INSERTING into NEWS_SCHEMA_TEST.TAG
SET DEFINE OFF;
Insert into NEWS_SCHEMA_TEST.TAG (TAG_ID,TAG_NAME) values (3,'Fashion');
Insert into NEWS_SCHEMA_TEST.TAG (TAG_ID,TAG_NAME) values (1,'Politics');
Insert into NEWS_SCHEMA_TEST.TAG (TAG_ID,TAG_NAME) values (2,'Economics');
--------------------------------------------------------
--  Index TAG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "NEWS_SCHEMA_TEST"."TAG_PK" ON "NEWS_SCHEMA_TEST"."TAG" ("TAG_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Index NEWS_USER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "NEWS_SCHEMA_TEST"."NEWS_USER_PK" ON "NEWS_SCHEMA_TEST"."NEWS_USER" ("USER_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Index AUTHOR_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "NEWS_SCHEMA_TEST"."AUTHOR_PK" ON "NEWS_SCHEMA_TEST"."AUTHOR" ("AUTHOR_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Index NEWS_AUTHOR_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "NEWS_SCHEMA_TEST"."NEWS_AUTHOR_PK" ON "NEWS_SCHEMA_TEST"."NEWS_AUTHOR" ("NEWS_AUTHOR_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Index COMMENTS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "NEWS_SCHEMA_TEST"."COMMENTS_PK" ON "NEWS_SCHEMA_TEST"."COMMENTS" ("COMMENT_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Index NEWS_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "NEWS_SCHEMA_TEST"."NEWS_PK" ON "NEWS_SCHEMA_TEST"."NEWS" ("NEWS_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Index NEWS_TAG_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "NEWS_SCHEMA_TEST"."NEWS_TAG_PK" ON "NEWS_SCHEMA_TEST"."NEWS_TAG" ("NEWS_TAG_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE" ;
--------------------------------------------------------
--  Constraints for Table TAG
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."TAG" MODIFY ("TAG_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."TAG" MODIFY ("TAG_NAME" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."TAG" ADD CONSTRAINT "TAG_PK" PRIMARY KEY ("TAG_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE"  ENABLE;
--------------------------------------------------------
--  Constraints for Table NEWS_AUTHOR
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_AUTHOR" MODIFY ("NEWS_AUTHOR_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_AUTHOR" MODIFY ("AUTHOR_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_AUTHOR" MODIFY ("NEWS_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_AUTHOR" ADD CONSTRAINT "NEWS_AUTHOR_PK" PRIMARY KEY ("NEWS_AUTHOR_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE"  ENABLE;
--------------------------------------------------------
--  Constraints for Table ROLE
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."ROLE" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."ROLE" MODIFY ("ROLE_NAME" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table NEWS
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS" MODIFY ("NEWS_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS" MODIFY ("SHORT_TEXT" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS" MODIFY ("FULL_TEXT" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS" MODIFY ("TITLE" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS" MODIFY ("CREATION_DATE" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS" MODIFY ("MODIFICATION_DATE" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS" ADD CONSTRAINT "NEWS_PK" PRIMARY KEY ("NEWS_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE"  ENABLE;
--------------------------------------------------------
--  Constraints for Table COMMENTS
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."COMMENTS" MODIFY ("COMMENT_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."COMMENTS" MODIFY ("COMMENT_TEXT" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."COMMENTS" MODIFY ("CREATION_DATE" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."COMMENTS" MODIFY ("NEWS_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."COMMENTS" ADD CONSTRAINT "COMMENTS_PK" PRIMARY KEY ("COMMENT_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE"  ENABLE;
--------------------------------------------------------
--  Constraints for Table NEWS_TAG
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_TAG" MODIFY ("NEWS_TAG_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_TAG" MODIFY ("NEWS_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_TAG" MODIFY ("TAG_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_TAG" ADD CONSTRAINT "NEWS_TAG_PK" PRIMARY KEY ("NEWS_TAG_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE"  ENABLE;
--------------------------------------------------------
--  Constraints for Table AUTHOR
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."AUTHOR" MODIFY ("AUTHOR_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."AUTHOR" MODIFY ("AUTHOR_NAME" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."AUTHOR" ADD CONSTRAINT "AUTHOR_PK" PRIMARY KEY ("AUTHOR_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE"  ENABLE;
--------------------------------------------------------
--  Constraints for Table NEWS_USER
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_USER" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_USER" MODIFY ("USER_NAME" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_USER" MODIFY ("LOGIN" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_USER" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_USER" ADD CONSTRAINT "NEWS_USER_PK" PRIMARY KEY ("USER_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "NEWS_TABLESPACE"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table COMMENTS
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."COMMENTS" ADD CONSTRAINT "COMMENT_NEWS_ID_FK" FOREIGN KEY ("NEWS_ID")
	  REFERENCES "NEWS_SCHEMA_TEST"."NEWS" ("NEWS_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table NEWS_AUTHOR
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_AUTHOR" ADD CONSTRAINT "AUTHOR_ID_FK" FOREIGN KEY ("AUTHOR_ID")
	  REFERENCES "NEWS_SCHEMA_TEST"."AUTHOR" ("AUTHOR_ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_AUTHOR" ADD CONSTRAINT "NEWS_AUTHOR_NEWS_ID_FK" FOREIGN KEY ("NEWS_ID")
	  REFERENCES "NEWS_SCHEMA_TEST"."NEWS" ("NEWS_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table NEWS_TAG
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_TAG" ADD CONSTRAINT "NEWS_ID_FK" FOREIGN KEY ("NEWS_ID")
	  REFERENCES "NEWS_SCHEMA_TEST"."NEWS" ("NEWS_ID") ON DELETE CASCADE ENABLE;
  ALTER TABLE "NEWS_SCHEMA_TEST"."NEWS_TAG" ADD CONSTRAINT "TAG_ID_FK" FOREIGN KEY ("TAG_ID")
	  REFERENCES "NEWS_SCHEMA_TEST"."TAG" ("TAG_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table ROLE
--------------------------------------------------------

  ALTER TABLE "NEWS_SCHEMA_TEST"."ROLE" ADD CONSTRAINT "USER_ID_FK" FOREIGN KEY ("USER_ID")
	  REFERENCES "NEWS_SCHEMA_TEST"."NEWS_USER" ("USER_ID") ON DELETE CASCADE ENABLE;
--------------------------------------------------------
--  Trigger AUTHOR_AI
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "NEWS_SCHEMA_TEST"."AUTHOR_AI"
   before insert on "AUTHOR"
   for each row
begin
   if inserting then
      if :NEW."AUTHOR_ID" is null then
         select AUTHOR_AI.nextval into :NEW."AUTHOR_ID" from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER "NEWS_SCHEMA_TEST"."AUTHOR_AI" ENABLE;
--------------------------------------------------------
--  Trigger COMMENTS_AI
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "NEWS_SCHEMA_TEST"."COMMENTS_AI"
   before insert on "COMMENTS"
   for each row
begin
   if inserting then
      if :NEW."COMMENT_ID" is null then
         select COMMENTS_AI.nextval into :NEW."COMMENT_ID" from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER "NEWS_SCHEMA_TEST"."COMMENTS_AI" ENABLE;
--------------------------------------------------------
--  Trigger NEWS_AI
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "NEWS_SCHEMA_TEST"."NEWS_AI"
   before insert on "NEWS"
   for each row
begin
   if inserting then
      if :NEW."NEWS_ID" is null then
         select NEWS_AI.nextval into :NEW."NEWS_ID" from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER "NEWS_SCHEMA_TEST"."NEWS_AI" ENABLE;
--------------------------------------------------------
--  Trigger NEWS_AUTHOR_AI
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "NEWS_SCHEMA_TEST"."NEWS_AUTHOR_AI"
   before insert on "NEWS_AUTHOR"
   for each row
begin
   if inserting then
      if :NEW."NEWS_AUTHOR_ID" is null then
         select NEWS_AUTHOR_AI.nextval into :NEW."NEWS_AUTHOR_ID" from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER "NEWS_SCHEMA_TEST"."NEWS_AUTHOR_AI" ENABLE;
--------------------------------------------------------
--  Trigger NEWS_TAG_AI
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "NEWS_SCHEMA_TEST"."NEWS_TAG_AI"
   before insert on "NEWS_TAG"
   for each row
begin
   if inserting then
      if :NEW."NEWS_TAG_ID" is null then
         select NEWS_TAG_AI.nextval into :NEW."NEWS_TAG_ID" from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER "NEWS_SCHEMA_TEST"."NEWS_TAG_AI" ENABLE;
--------------------------------------------------------
--  Trigger NEWS_USER_AI
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "NEWS_SCHEMA_TEST"."NEWS_USER_AI"
   before insert on "NEWS_USER"
   for each row
begin
   if inserting then
      if :NEW."USER_ID" is null then
         select NEWS_USER_AI.nextval into :NEW."USER_ID" from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER "NEWS_SCHEMA_TEST"."NEWS_USER_AI" ENABLE;
--------------------------------------------------------
--  Trigger TAG_AI
--------------------------------------------------------

  CREATE OR REPLACE TRIGGER "NEWS_SCHEMA_TEST"."TAG_AI"
   before insert on "TAG"
   for each row
begin
   if inserting then
      if :NEW."TAG_ID" is null then
         select TAG_AI.nextval into :NEW."TAG_ID" from dual;
      end if;
   end if;
end;
/
ALTER TRIGGER "NEWS_SCHEMA_TEST"."TAG_AI" ENABLE;
