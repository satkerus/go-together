/*
 * This file is generated by jOOQ.
 */
package org.go.together.repository;


import org.go.together.repository.tables.*;
import org.go.together.repository.tables.records.*;
import org.jooq.ForeignKey;
import org.jooq.UniqueKey;
import org.jooq.impl.Internal;

import javax.annotation.processing.Generated;


/**
 * A class modelling foreign key relationships and constraints of tables of
 * the <code></code> schema.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.12.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class Keys {

    // -------------------------------------------------------------------------
    // IDENTITY definitions
    // -------------------------------------------------------------------------


    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<AppuserRecord> CONSTRAINT_F = UniqueKeys0.CONSTRAINT_F;
    public static final UniqueKey<AppuserInterestRecord> CONSTRAINT_5 = UniqueKeys0.CONSTRAINT_5;
    public static final UniqueKey<AppuserLanguageRecord> CONSTRAINT_D = UniqueKeys0.CONSTRAINT_D;
    public static final UniqueKey<InterestRecord> CONSTRAINT_50 = UniqueKeys0.CONSTRAINT_50;
    public static final UniqueKey<LanguageRecord> CONSTRAINT_C = UniqueKeys0.CONSTRAINT_C;

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<AppuserInterestRecord, AppuserRecord> FKAAHACQ6KG6FXN7DVR4DKTGR4V = ForeignKeys0.FKAAHACQ6KG6FXN7DVR4DKTGR4V;
    public static final ForeignKey<AppuserInterestRecord, InterestRecord> FKI7C4EASMWHYS38SFYF7DB6V1L = ForeignKeys0.FKI7C4EASMWHYS38SFYF7DB6V1L;
    public static final ForeignKey<AppuserLanguageRecord, AppuserRecord> FKQCLSSGWN43V98PP5WIDS6GKSF = ForeignKeys0.FKQCLSSGWN43V98PP5WIDS6GKSF;
    public static final ForeignKey<AppuserLanguageRecord, LanguageRecord> FK2NEKN68EYN2CVQK4S480SRYNP = ForeignKeys0.FK2NEKN68EYN2CVQK4S480SRYNP;

    // -------------------------------------------------------------------------
    // [#1459] distribute members to avoid static initialisers > 64kb
    // -------------------------------------------------------------------------

    private static class UniqueKeys0 {
        public static final UniqueKey<AppuserRecord> CONSTRAINT_F = Internal.createUniqueKey(Appuser.APPUSER, "CONSTRAINT_F", Appuser.APPUSER.ID);
        public static final UniqueKey<AppuserInterestRecord> CONSTRAINT_5 = Internal.createUniqueKey(AppuserInterest.APPUSER_INTEREST, "CONSTRAINT_5", AppuserInterest.APPUSER_INTEREST.APPUSER_ID, AppuserInterest.APPUSER_INTEREST.INTERESTS_ID);
        public static final UniqueKey<AppuserLanguageRecord> CONSTRAINT_D = Internal.createUniqueKey(AppuserLanguage.APPUSER_LANGUAGE, "CONSTRAINT_D", AppuserLanguage.APPUSER_LANGUAGE.APPUSER_ID, AppuserLanguage.APPUSER_LANGUAGE.LANGUAGES_ID);
        public static final UniqueKey<InterestRecord> CONSTRAINT_50 = Internal.createUniqueKey(Interest.INTEREST, "CONSTRAINT_50", Interest.INTEREST.ID);
        public static final UniqueKey<LanguageRecord> CONSTRAINT_C = Internal.createUniqueKey(Language.LANGUAGE, "CONSTRAINT_C", Language.LANGUAGE.ID);
    }

    private static class ForeignKeys0 {
        public static final ForeignKey<AppuserInterestRecord, AppuserRecord> FKAAHACQ6KG6FXN7DVR4DKTGR4V = Internal.createForeignKey(org.go.together.repository.Keys.CONSTRAINT_F, AppuserInterest.APPUSER_INTEREST, "FKAAHACQ6KG6FXN7DVR4DKTGR4V", AppuserInterest.APPUSER_INTEREST.APPUSER_ID);
        public static final ForeignKey<AppuserInterestRecord, InterestRecord> FKI7C4EASMWHYS38SFYF7DB6V1L = Internal.createForeignKey(org.go.together.repository.Keys.CONSTRAINT_50, AppuserInterest.APPUSER_INTEREST, "FKI7C4EASMWHYS38SFYF7DB6V1L", AppuserInterest.APPUSER_INTEREST.INTERESTS_ID);
        public static final ForeignKey<AppuserLanguageRecord, AppuserRecord> FKQCLSSGWN43V98PP5WIDS6GKSF = Internal.createForeignKey(org.go.together.repository.Keys.CONSTRAINT_F, AppuserLanguage.APPUSER_LANGUAGE, "FKQCLSSGWN43V98PP5WIDS6GKSF", AppuserLanguage.APPUSER_LANGUAGE.APPUSER_ID);
        public static final ForeignKey<AppuserLanguageRecord, LanguageRecord> FK2NEKN68EYN2CVQK4S480SRYNP = Internal.createForeignKey(org.go.together.repository.Keys.CONSTRAINT_C, AppuserLanguage.APPUSER_LANGUAGE, "FK2NEKN68EYN2CVQK4S480SRYNP", AppuserLanguage.APPUSER_LANGUAGE.LANGUAGES_ID);
    }
}
