Create Table AGENT(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	IP_ADDRESS VARCHAR(128) NOT NULL,
	PORT SMALLINT UNSIGNED NOT NULL,
	CAPABILITIES VARCHAR(256),
	STATUS INT NULL,
	LAST_CHECKED DATETIME,
	PRIMARY KEY (ID)
);

Create Table USERDATA(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	EMAIL VARCHAR(128) NOT NULL,
	PASSWORD VARCHAR(128) NOT NULL,
	NAME VARCHAR(128) NOT NULL,
	REGISTERED_SINCE DATETIME NOT NULL,
	ROLE INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	UNIQUE (NAME)
);

-- Each project has one 'admin user', the one who created the project
Create Table PROJECT(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(256) NOT NULL,
	CREATED_AT DATETIME NOT NULL,
	CREATE_USER_ID INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (CREATE_USER_ID) REFERENCES USERDATA(ID)
);

-- In addition the 'admin user' can add other users to this project, modeled by this JOIN table
-- ManyToMany
Create Table USER_PROJECT(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	USER_ID INT UNSIGNED NOT NULL,
	PROJECT_ID INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT(ID),
	FOREIGN KEY (USER_ID) REFERENCES USERDATA(ID),
	UNIQUE(USER_ID, PROJECT_ID)
);

-- Status as described in bitflow-process-agent REST-API - GET /pipeline/:id
-- PROJECT_ID INT UNSIGNED NOT NULL, -- OneToMany
Create Table PIPELINE(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	NAME VARCHAR(256),
	STATUS VARCHAR(32),
	SCRIPT VARCHAR(256) NOT NULL, -- the actual script, defining this pipeline
	LAST_CHANGED DATETIME,
	PRIMARY KEY (ID),
	UNIQUE (Name)
);

-- ManyToMany
Create Table PIPELINE_PROJECT(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	PROJECT_ID INT UNSIGNED NOT NULL,
	PIPELINE_ID INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT(ID),
	FOREIGN KEY (PIPELINE_ID) REFERENCES PIPELINE(ID),
	UNIQUE(PIPELINE_ID, PROJECT_ID)
);

-- This table models the pipeline steps, containing one specified algorithm
Create Table PIPELINE_STEP(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	AGENT_ID INT UNSIGNED NOT NULL,
	STEP_NUMBER INT UNSIGNED NOT NULL,
	PIPELINE_ID INT UNSIGNED NOT NULL,
	STATUS VARCHAR(32),
	SCRIPT VARCHAR(256) NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (PIPELINE_ID) REFERENCES PIPELINE(ID),
	FOREIGN KEY (AGENT_ID) REFERENCES AGENT(ID)
);

-- We model the pipeline by making each pipeline step aware of its successor steps, that's what this table is about
Create Table PIPELINE_STEP_SUCCESSORS(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	SUCCESSOR_ID INT UNSIGNED NOT NULL,
	STEP_ID INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (SUCCESSOR_ID) REFERENCES PIPELINE_STEP(ID),
	FOREIGN KEY (STEP_ID) REFERENCES PIPELINE_STEP(ID),
	UNIQUE (STEP_ID, SUCCESSOR_ID)
);

Create Table CONFIGURATION(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	DESCRIPTION VARCHAR(128),
	CONFIG_KEY VARCHAR(64) NOT NULL,
	CONFIG_VALUE VARCHAR(64) NOT NULL,
	PRIMARY KEY (ID),
	UNIQUE (CONFIG_KEY)
);


/*
Create Table PIPELINE(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	AGENT_ID INT UNSIGNED NOT NULL,
	ID_ON_AGENT INT UNSIGNED NOT NULL,
	PROJECT_ID INT UNSIGNED NOT NULL,
	STATUS VARCHAR(32),
	SCRIPT VARCHAR(256), -- the actual script, defining this pipeline
	STEP_NUMBER INT NOT NULL,
	LAST_CHANGED DATETIME,
	PRIMARY KEY (ID),
	FOREIGN KEY (PROJECT_ID) REFERENCES PROJECT(ID),
	FOREIGN KEY (AGENT_ID) REFERENCES AGENT(ID)
);

-- show successors of pipeline, end of line have no entry
Create Table PIPELINE_SUCCESSION(
	ID INT UNSIGNED NOT NULL AUTO_INCREMENT,
	PIPELINE_ID INT UNSIGNED NOT NULL,
	SUCCESSOR_ID INT UNSIGNED NOT NULL,
	PRIMARY KEY (ID),
	FOREIGN KEY (PIPELINE_ID) REFERENCES PIPELINE(ID),
	FOREIGN KEY (SUCCESSOR_ID) REFERENCES PIPELINE(ID),
	UNIQUE (PIPELINE_ID, SUCCESSOR_ID)
);
*/