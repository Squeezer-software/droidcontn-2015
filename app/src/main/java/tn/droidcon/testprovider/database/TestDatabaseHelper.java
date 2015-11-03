package tn.droidcon.testprovider.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import tn.droidcon.testprovider.database.tables.ListsTable;
import tn.droidcon.testprovider.database.tables.RecordsTable;



public class TestDatabaseHelper extends SQLiteOpenHelper {

    // database name
    private static final String DATABASE_NAME = "my-test.db";
    // data base version
    private static final int DATABASE_VERSION = 1;

    /**
     * Basic constructor
     *
     * @param context
     */
    public TestDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * Constructor
     *
     * @param context
     * @param name
     * @param factory
     * @param version
     */
    public TestDatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        RecordsTable.onCreate(db);
        ListsTable.onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        RecordsTable.onUpgrade(db, oldVersion, newVersion);
        ListsTable.onUpgrade(db, oldVersion, newVersion);
    }

}