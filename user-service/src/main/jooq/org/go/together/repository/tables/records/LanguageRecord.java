/*
 * This file is generated by jOOQ.
 */
package org.go.together.repository.tables.records;


import org.go.together.repository.tables.Language;
import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
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
public class LanguageRecord extends UpdatableRecordImpl<LanguageRecord> implements Record3<String, String, String> {

    private static final long serialVersionUID = 364376643;

    /**
     * Create a detached LanguageRecord
     */
    public LanguageRecord() {
        super(Language.LANGUAGE);
    }

    /**
     * Create a detached, initialised LanguageRecord
     */
    public LanguageRecord(String id, String code, String name) {
        super(Language.LANGUAGE);

        set(0, id);
        set(1, code);
        set(2, name);
    }

    /**
     * Getter for <code>LANGUAGE.ID</code>.
     */
    public String getId() {
        return (String) get(0);
    }

    /**
     * Setter for <code>LANGUAGE.ID</code>.
     */
    public void setId(String value) {
        set(0, value);
    }

    /**
     * Getter for <code>LANGUAGE.CODE</code>.
     */
    public String getCode() {
        return (String) get(1);
    }

    /**
     * Setter for <code>LANGUAGE.CODE</code>.
     */
    public void setCode(String value) {
        set(1, value);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    /**
     * Getter for <code>LANGUAGE.NAME</code>.
     */
    public String getName() {
        return (String) get(2);
    }

    // -------------------------------------------------------------------------
    // Record3 type implementation
    // -------------------------------------------------------------------------

    /**
     * Setter for <code>LANGUAGE.NAME</code>.
     */
    public void setName(String value) {
        set(2, value);
    }

    @Override
    public Record1<String> key() {
        return (Record1) super.key();
    }

    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }

    @Override
    public Row3<String, String, String> valuesRow() {
        return (Row3) super.valuesRow();
    }

    @Override
    public Field<String> field1() {
        return Language.LANGUAGE.ID;
    }

    @Override
    public Field<String> field2() {
        return Language.LANGUAGE.CODE;
    }

    @Override
    public Field<String> field3() {
        return Language.LANGUAGE.NAME;
    }

    @Override
    public String component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getCode();
    }

    @Override
    public String component3() {
        return getName();
    }

    @Override
    public String value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getCode();
    }

    @Override
    public String value3() {
        return getName();
    }

    @Override
    public LanguageRecord value1(String value) {
        setId(value);
        return this;
    }

    @Override
    public LanguageRecord value2(String value) {
        setCode(value);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    @Override
    public LanguageRecord value3(String value) {
        setName(value);
        return this;
    }

    @Override
    public LanguageRecord values(String value1, String value2, String value3) {
        value1(value1);
        value2(value2);
        value3(value3);
        return this;
    }
}
