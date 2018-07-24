package com.example.m1039158.sqliteexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.security.keystore.KeyNotYetValidException;

import java.util.StringTokenizer;

public class ContactsDB {

    public static final String KEY_ROWID="_id";
    public static final String KEY_NAME="person_name";
    public static final String KEY_CELL="_cell";

    private final String DATABASE_NAME="ContactsDB";
    private final String DATABASE_TABLE="ContactsTable";
    private final int DATABASE_VERSION=1;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabse;

    public ContactsDB (Context context){
        ourContext=context;
    }

    //Note: SQLite is file-based
    private class DBHelper extends SQLiteOpenHelper{

        public DBHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //Only called if the database file exist but the stored version no. is lower that requested in the constructor
        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);
            onCreate(sqLiteDatabase);
        }

        // Runs when database does not exist --> creates it
        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {

            /*

            CREATE TABLE ContactsTable (_id INTEGER PRIMARY KEY AUTOINCREMENT,
                person_name TEXT NOT NULL,
                _cell TEXT NOT NULL);

            * */

            String sqlCode ="CREATE TABLE "+ DATABASE_TABLE + " ("+
                    KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, "+
                    KEY_NAME + " TEXT NOT NULL, "+
                    KEY_CELL + " TEXT NOT NULL);";

            sqLiteDatabase.execSQL(sqlCode);
        }
    }// End of inner class DBHelper


    //Open the connection with DB --> connected with database DATABASE_NAME
    public ContactsDB open() throws SQLException{
        ourHelper=new DBHelper(ourContext);
        ourDatabse=ourHelper.getWritableDatabase();
        return this;
    }

    //Close the connection with DB
    public void close(){
        ourHelper.close();
    }

    //Insert into the table, Note: Use ContentValues to create key-value pairs
    public long createEntry(String name, String cell){
        ContentValues cv =new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_CELL,cell);
        return ourDatabse.insert(DATABASE_TABLE,"No",cv);
    }

    // get all data from the table
    public String getData(){
        //String array of column names
        String[] columns=new String[]{KEY_ROWID,KEY_NAME,KEY_CELL};

        //To move the cursor object to the beginning to start reading. ( Returns a Cursor object, which is positioned before the first entry)
        Cursor cursor=ourDatabse.query(DATABASE_TABLE,columns,null,null,null,null,null);

        int iRowID=cursor.getColumnIndex(KEY_ROWID);  // index of _id column --> couldbe zero or one or anything
        int iName=cursor.getColumnIndex(KEY_NAME);  //index of person_name column in file (e.g, excel)
        int iCell=cursor.getColumnIndex(KEY_CELL);  // index of _cell column

        String result="";
        //Initially move the cursor to first row, keep moving to next row, until cursor is after last row
        for(cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            result=result+cursor.getString(iRowID)+": "+ cursor.getString(iName)+" "+cursor.getString(iCell)+"\n";
        }
        cursor.close(); //close the cursor
        return result;
    }

    //delete entry with rowId in whereClause
    public long deleteEntry(String rowId){
        //Returns int: the number of rows affected if a whereClause is passed in, 0 otherwise. To remove all rows and get a count pass "1" as the whereClause(2nd arg).
        return ourDatabse.delete(DATABASE_TABLE,KEY_ROWID+"=?",new String[]{rowId});
    }

    //Update an entry using ContentValues and where clause
    public long updateEntry(String rowId, String name, String cell){
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_CELL,cell);

        //Returns int: the number of rows affected
        return ourDatabse.update(DATABASE_TABLE,cv,KEY_ROWID+"=?",new String[]{rowId});
    }
}
