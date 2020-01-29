CREATE TABLE INTEREST
(
    ID   UUID PRIMARY KEY,
    NAME TEXT NOT NULL
);

CREATE TABLE LANGUAGE
(
    ID   UUID PRIMARY KEY,
    NAME TEXT NOT NULL,
    CODE TEXT NOT NULL
);

CREATE TABLE APP_USER
(
    ID          UUID PRIMARY KEY,
    LOGIN       TEXT NOT NULL,
    MAIL        TEXT NOT NULL,
    FIRST_NAME  TEXT NOT NULL,
    LAST_NAME   TEXT NOT NULL,
    DESCRIPTION TEXT NOT NULL,
    PASSWORD    TEXT NOT NULL,
    LOCATION_ID UUID NOT NULL,
    PHOTO_ID    UUID,
    ROLE        TEXT NOT NULL
);

CREATE TABLE USER_INTEREST
(
    ID          UUID PRIMARY KEY,
    APP_USER_ID UUID NOT NULL,
    INTEREST_ID UUID NOT NULL
);

CREATE INDEX "idx_user_interest__interest" ON USER_INTEREST (INTEREST_ID);

CREATE INDEX "idx_user_interest__user" ON USER_INTEREST (APP_USER_ID);

ALTER TABLE USER_INTEREST
    ADD CONSTRAINT "fk_user_interest__interest" FOREIGN KEY (INTEREST_ID) REFERENCES INTEREST (ID) ON DELETE CASCADE;

ALTER TABLE USER_INTEREST
    ADD CONSTRAINT "fk_user_interest__user" FOREIGN KEY (APP_USER_ID) REFERENCES APP_USER (ID) ON DELETE CASCADE;

CREATE TABLE USER_LANGUAGE
(
    ID          UUID PRIMARY KEY,
    APP_USER_ID UUID NOT NULL,
    LANGUAGE_ID UUID NOT NULL
);

CREATE INDEX "idx_user_language__language" ON USER_LANGUAGE (LANGUAGE_ID);

CREATE INDEX "idx_user_language__user" ON USER_LANGUAGE (APP_USER_ID);

ALTER TABLE USER_LANGUAGE
    ADD CONSTRAINT "fk_user_language__language" FOREIGN KEY (LANGUAGE_ID) REFERENCES LANGUAGE (ID) ON DELETE CASCADE;

ALTER TABLE USER_LANGUAGE
    ADD CONSTRAINT "fk_user_language__user" FOREIGN KEY (APP_USER_ID) REFERENCES APP_USER (ID) ON DELETE CASCADE