/*
 * This file is generated by jOOQ.
 */
package org.go.together.repository.tables.records;


import org.go.together.repository.tables.AppuserInterest;
import org.jooq.Field;
import org.jooq.Record2;
import org.jooq.Row2;
import org.jooq.impl.UpdatableRecordImpl;

import javax.annotation.processing.Generated;


/**
 * This class is generated by jOOQ.
 */
@Generated(
        value = {
                "http://www.jooq.org",
                "jOOQ version:3.12.3"
        },
        comments = "This class is generated by jOOQ"
)
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class AppuserInterestRecord extends UpdatableRecordImpl<AppuserInterestRecord> implements Record2<String, String> {

    private static final long serialVersionUID = -422881010;

    /**
     * Create a detached AppuserInterestRecord
     */
    public AppuserInterestRecord() {
        super(AppuserInterest.APPUSER_INTEREST);
    }

    /**
     * Create a detached, initialised AppuserInterestRecord
     */
    public AppuserInterestRecord(String appuserId, String interestsId) {
        super(AppuserInterest.APPUSER_INTEREST);

        set(0, appuserId);
        set(1, interestsId);
    }

    /**
     * Getter for <code>APPUSER_INTEREST.APPUSER_ID</code>.
     */
    public String getAppuserId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>APPUSER_INTEREST.APPUSER_ID</code>.
     */
    public void setAppuserId(String value) {
        set(0, value);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * Getter for <code>APPUSER_INTEREST.INTERESTS_ID</code>.
     */
    public String getInterestsId() {
        return (String) get(1);
    }

    // -------------------------------------------------------------------------
    // Record2 type implementation
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>APPUSER_INTEREST.INTERESTS_ID</code>.
     */
    public void setInterestsId(String value) {
        set(1, value);
    }

    @Override
    public Record2<String, String> key() {
        return (Record2) super.key();
    }

    @Override
    public Row2<String, String> fieldsRow() {
        return (Row2) super.fieldsRow();
    }

    @Override
    public Row2<String, String> valuesRow() {
        return (Row2) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return AppuserInterest.APPUSER_INTEREST.APPUSER_ID;
    }

    @Override
    public Field<String> field2() {
        return AppuserInterest.APPUSER_INTEREST.INTERESTS_ID;
    }

    @Override
    public String component1() {
        return getAppuserId();
    }

    @Override
    public String component2() {
        return getInterestsId();
    }

    @Override
    public String value1() {
        return getAppuserId();
    }

    @Override
    public String value2() {
        return getInterestsId();
    }

    @Override
    public AppuserInterestRecord value1(String value) {
        setAppuserId(value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    @Override
    public AppuserInterestRecord value2(String value) {
        setInterestsId(value);
        return this;
    }

    @Override
    public AppuserInterestRecord values(String value1, String value2) {
        value1(value1);
        value2(value2);
        return this;
    }
}
