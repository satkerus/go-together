/*
 * This file is generated by jOOQ.
 */
package org.go.together.repository.tables;


import org.go.together.repository.DefaultSchema;
import org.go.together.repository.Indexes;
import org.go.together.repository.Keys;
import org.go.together.repository.tables.records.LanguageRecord;
import org.jooq.*;
import org.jooq.impl.DSL;
import org.jooq.impl.TableImpl;

import javax.annotation.processing.Generated;
import java.util.Arrays;
import java.util.List;


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
public class Language extends TableImpl<LanguageRecord> {

    /**
     * The reference instance of <code>LANGUAGE</code>
     */
    public static final Language LANGUAGE = new Language();
    private static final long serialVersionUID = 1180380162;
    /**
     * The column <code>LANGUAGE.ID</code>.
     */
    public final TableField<LanguageRecord, String> ID = createField(DSL.name("ID"), org.jooq.impl.SQLDataType.VARCHAR(255).nullable(false), this, "");
    /**
     * The column <code>LANGUAGE.CODE</code>.
     */
    public final TableField<LanguageRecord, String> CODE = createField(DSL.name("CODE"), org.jooq.impl.SQLDataType.VARCHAR(255), this, "");
    /**
     * The column <code>LANGUAGE.NAME</code>.
     */
    public final TableField<LanguageRecord, String> NAME = createField(DSL.name("NAME"), org.jooq.impl.SQLDataType.VARCHAR(255), this, "");

    /**
     * Create a <code>LANGUAGE</code> table reference
     */
    public Language() {
        this(DSL.name("LANGUAGE"), null);
    }

    /**
     * Create an aliased <code>LANGUAGE</code> table reference
     */
    public Language(String alias) {
        this(DSL.name(alias), LANGUAGE);
    }

    /**
     * Create an aliased <code>LANGUAGE</code> table reference
     */
    public Language(Name alias) {
        this(alias, LANGUAGE);
    }

    private Language(Name alias, Table<LanguageRecord> aliased) {
        this(alias, aliased, null);
    }

    private Language(Name alias, Table<LanguageRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""));
    }

    public <O extends Record> Language(Table<O> child, ForeignKey<O, LanguageRecord> key) {
        super(child, key, LANGUAGE);
    }

    /**
     * The class holding records for this type
     */
    @Override
    public Class<LanguageRecord> getRecordType() {
        return LanguageRecord.class;
    }

    @Override
    public Schema getSchema() {
        return DefaultSchema.DEFAULT_SCHEMA;
    }

    @Override
    public List<Index> getIndexes() {
        return Arrays.<Index>asList(Indexes.PRIMARY_KEY_C);
    }

    @Override
    public UniqueKey<LanguageRecord> getPrimaryKey() {
        return Keys.CONSTRAINT_C;
    }

    @Override
    public List<UniqueKey<LanguageRecord>> getKeys() {
        return Arrays.<UniqueKey<LanguageRecord>>asList(Keys.CONSTRAINT_C);
    }

    @Override
    public Language as(String alias) {
        return new Language(DSL.name(alias), this);
    }

    @Override
    public Language as(Name alias) {
        return new Language(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public Language rename(String name) {
        return new Language(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public Language rename(Name name) {
        return new Language(name, null);
    }

    // -------------------------------------------------------------------------
    // Row3 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row3<String, String, String> fieldsRow() {
        return (Row3) super.fieldsRow();
    }
}
