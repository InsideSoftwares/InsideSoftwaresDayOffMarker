-- Create Database Change Log Table
CREATE TABLE public.dom_databasechangelog (ID VARCHAR(255) NOT NULL, AUTHOR VARCHAR(255) NOT NULL, FILENAME VARCHAR(255) NOT NULL, DATEEXECUTED TIMESTAMP WITHOUT TIME ZONE NOT NULL, ORDEREXECUTED INTEGER NOT NULL, EXECTYPE VARCHAR(10) NOT NULL, MD5SUM VARCHAR(35), DESCRIPTION VARCHAR(255), COMMENTS VARCHAR(255), TAG VARCHAR(255), LIQUIBASE VARCHAR(20), CONTEXTS VARCHAR(255), LABELS VARCHAR(255), DEPLOYMENT_ID VARCHAR(10));

SET SEARCH_PATH TO public, "$user","public";

SET SEARCH_PATH TO public, "$user","public";

-- Create Database Lock Table
CREATE TABLE public.dom_databasechangeloglock (ID INTEGER NOT NULL, LOCKED BOOLEAN NOT NULL, LOCKGRANTED TIMESTAMP WITHOUT TIME ZONE, LOCKEDBY VARCHAR(255), CONSTRAINT dom_databasechangeloglock_pkey PRIMARY KEY (ID));

-- Initialize Database Lock Table
DELETE FROM public.dom_databasechangeloglock;

INSERT INTO public.dom_databasechangeloglock (ID, LOCKED) VALUES (1, FALSE);

SET SEARCH_PATH TO public, "$user","public";

SET SEARCH_PATH TO public, "$user","public";

-- *********************************************************************
-- SQL to roll back currently unexecuted changes
-- *********************************************************************
-- Change Log: db/changelog/db.changelog-master.yaml
-- Ran at: 15/11/2023 16:27
-- Against: InsideSoftwaresAdmin@jdbc:postgresql://localhost:5432/InsideSoftwares
-- Liquibase version: 4.23.2
-- *********************************************************************

SET SEARCH_PATH TO public, "$user","public";

-- Lock Database
UPDATE public.dom_databasechangeloglock SET LOCKED = TRUE, LOCKEDBY = 'SawCunha-PC (192.168.4.62)', LOCKGRANTED = NOW() WHERE ID = 1 AND LOCKED = FALSE;

SET SEARCH_PATH TO public, "$user","public";

SET SEARCH_PATH TO public, "$user","public";

SET SEARCH_PATH TO public, "$user","public";

-- Rolling Back ChangeSet: db/changelog/v1.0.0/create_column_dom_state.yml::addColumn-DOM_STATE::System
ALTER TABLE public.DOM_STATE DROP CONSTRAINT UK_CODE_DOM_STATE;

ALTER TABLE public.DOM_STATE DROP COLUMN CODE;

DELETE FROM public.dom_databasechangelog WHERE ID = 'addColumn-DOM_STATE' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/v1.0.0/create_column_dom_state.yml';

-- Rolling Back ChangeSet: db/changelog/v1.0.0/create_column_dom_holiday.yml::addColumn-DOM_HOLIDAY-NH::System
ALTER TABLE public.DOM_HOLIDAY ALTER COLUMN NATIONAL_HOLIDAY DROP DEFAULT;

ALTER TABLE public.DOM_HOLIDAY DROP COLUMN NATIONAL_HOLIDAY;

DELETE FROM public.dom_databasechangelog WHERE ID = 'addColumn-DOM_HOLIDAY-NH' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/v1.0.0/create_column_dom_holiday.yml';

-- Rolling Back ChangeSet: db/changelog/v1.0.0/create_column_dom_holiday.yml::addColumn-DOM_HOLIDAY::System
ALTER TABLE public.DOM_HOLIDAY DROP COLUMN FIXED_HOLIDAY_ID;

DELETE FROM public.dom_databasechangelog WHERE ID = 'addColumn-DOM_HOLIDAY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/v1.0.0/create_column_dom_holiday.yml';

-- Rolling Back ChangeSet: db/changelog/v1.0.0/create_column_dom_fixed_holiday.yml::update-DOM_FIXED_HOLIDAY::System
DELETE FROM public.dom_databasechangelog WHERE ID = 'update-DOM_FIXED_HOLIDAY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/v1.0.0/create_column_dom_fixed_holiday.yml';

-- Rolling Back ChangeSet: db/changelog/v1.0.0/create_column_dom_fixed_holiday.yml::addColumn-DOM_FIXED_HOLIDAY::System
ALTER TABLE public.DOM_FIXED_HOLIDAY DROP COLUMN ENABLE;

DELETE FROM public.dom_databasechangelog WHERE ID = 'addColumn-DOM_FIXED_HOLIDAY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/v1.0.0/create_column_dom_fixed_holiday.yml';

-- Rolling Back ChangeSet: db/changelog/v1.0.0/create_column_dom_day.yml::create_index-DOM_DAY::System
DROP INDEX public.IDX_DD_DATE_HOLIDAY;

DROP INDEX public.IDX_DD_DATE_WEEKEND;

DROP INDEX public.IDX_DD_DATE_OF_WEEK;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_index-DOM_DAY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/v1.0.0/create_column_dom_day.yml';

-- Rolling Back ChangeSet: db/changelog/v1.0.0/create_column_dom_day.yml::addColumn-DOM_DAY::System
ALTER TABLE public.DOM_DAY DROP COLUMN DAY_OF_WEEK;

ALTER TABLE public.DOM_DAY DROP COLUMN DAY_OF_YEAR;

DELETE FROM public.dom_databasechangelog WHERE ID = 'addColumn-DOM_DAY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/v1.0.0/create_column_dom_day.yml';

-- Rolling Back ChangeSet: db/changelog/init/insert_holidays_br.json::insert_holidays_br::System
TRUNCATE TABLE DOM_FIXED_HOLIDAY;

DELETE FROM public.dom_databasechangelog WHERE ID = 'insert_holidays_br' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/insert_holidays_br.json';

-- Rolling Back ChangeSet: db/changelog/init/insert_configuration.yml::insert_configuration::System
TRUNCATE TABLE DOM_CONFIGURATION;

DELETE FROM public.dom_databasechangelog WHERE ID = 'insert_configuration' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/insert_configuration.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_index.yml::create_index::System
DROP INDEX public.IDX_DT_CODE;

DROP INDEX public.IDX_DD_DATE_WEEKEND_HOLIDAY;

DROP INDEX public.IDX_DD_DATE;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_index' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_index.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_DAY_TAG::System
ALTER TABLE public.DOM_DAY_TAG DROP CONSTRAINT PK_DAY_TAG;

DROP TABLE public.DOM_DAY_TAG;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_DAY_TAG' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_TAG::System
ALTER TABLE public.DOM_TAG DROP CONSTRAINT UK_CODE_DOM_TAG;

DROP TABLE public.DOM_TAG;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_TAG' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_REQUEST_PARAMETER::System
DROP TABLE public.DOM_REQUEST_PARAMETER;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_REQUEST_PARAMETER' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_REQUEST::System
DROP TABLE public.DOM_REQUEST;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_REQUEST' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_CONFIGURATION::System
ALTER TABLE public.DOM_CONFIGURATION DROP CONSTRAINT UK_DOM_CONFIGURATION;

DROP TABLE public.DOM_CONFIGURATION;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_CONFIGURATION' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_FIXED_HOLIDAY::System
ALTER TABLE public.DOM_FIXED_HOLIDAY DROP CONSTRAINT UK_FIXED_HOLIDAY_COUNTRY;

DROP TABLE public.DOM_FIXED_HOLIDAY;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_FIXED_HOLIDAY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_STATE_HOLIDAY::System
ALTER TABLE public.DOM_STATE_HOLIDAY DROP CONSTRAINT PK_DOM_STATE_HOLIDAY;

DROP TABLE public.DOM_STATE_HOLIDAY;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_STATE_HOLIDAY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_CITY_HOLIDAY::System
ALTER TABLE public.DOM_CITY_HOLIDAY DROP CONSTRAINT PK_DOM_CITY_HOLIDAY;

DROP TABLE public.DOM_CITY_HOLIDAY;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_CITY_HOLIDAY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_HOLIDAY::System
ALTER TABLE public.DOM_DAY DROP CONSTRAINT UK_DAY_DOM_HOLIDAY;

DROP TABLE public.DOM_HOLIDAY;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_HOLIDAY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_CITY::System
ALTER TABLE public.DOM_CITY DROP CONSTRAINT UK_CODE_DOM_CITY;

ALTER TABLE public.DOM_CITY DROP CONSTRAINT UK_ACRONYM_DOM_CITY;

ALTER TABLE public.DOM_CITY DROP CONSTRAINT UK_NAME_DOM_CITY;

DROP TABLE public.DOM_CITY;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_CITY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_STATE::System
ALTER TABLE public.DOM_STATE DROP CONSTRAINT UK_ACRONYM_DOM_STATE;

ALTER TABLE public.DOM_STATE DROP CONSTRAINT UK_NAME_DOM_STATE;

DROP TABLE public.DOM_STATE;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_STATE' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_DAY::System
ALTER TABLE public.DOM_DAY DROP CONSTRAINT UK_DATE_DOM_DAY;

DROP TABLE public.DOM_DAY;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_DAY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::create_DOM_COUNTRY::System
ALTER TABLE public.DOM_COUNTRY DROP CONSTRAINT UK_CODE_DOM_COUNTRY;

ALTER TABLE public.DOM_COUNTRY DROP CONSTRAINT UK_ACRONYM_DOM_COUNTRY;

ALTER TABLE public.DOM_COUNTRY DROP CONSTRAINT UK_NAME_DOM_COUNTRY;

DROP TABLE public.DOM_COUNTRY;

DELETE FROM public.dom_databasechangelog WHERE ID = 'create_DOM_COUNTRY' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Rolling Back ChangeSet: db/changelog/init/create_db.yml::postgres-extension-uuid::System
DROP EXTENSION "uuid-ossp";

DELETE FROM public.dom_databasechangelog WHERE ID = 'postgres-extension-uuid' AND AUTHOR = 'System' AND FILENAME = 'db/changelog/init/create_db.yml';

-- Release Database Lock
SET SEARCH_PATH TO public, "$user","public";

UPDATE public.dom_databasechangeloglock SET LOCKED = FALSE, LOCKEDBY = NULL, LOCKGRANTED = NULL WHERE ID = 1;

SET SEARCH_PATH TO public, "$user","public";

