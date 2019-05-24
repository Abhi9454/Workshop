package workshop.workshop.com;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Helperclass extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "workshop_db";


    public Helperclass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(userdetails.CREATE_TABLE);
        db.execSQL(Workshop_details.CREATE_TABLE);
        db.execSQL(userworkshop.CREATE_TABLE);
        Log.i("data created", "cre");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + userdetails.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Workshop_details.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + userworkshop.TABLE_NAME);

        onCreate(db);
    }


    public long insertuser(String email, String password, String name, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(userdetails.COLUMN_EMAIL, email);
        values.put(userdetails.COLUMN_PASSWORD, password);
        values.put(userdetails.COLUMN_NAME, name);
        values.put(userdetails.COLUMN_PHONE, phone);
        values.put(userdetails.COLUMN_ADDRESS, address);

        // INSERT ROW

        long id = db.insert(userdetails.TABLE_NAME, null, values);

        // close db connection
        db.close();

        return id;
    }

    public void clearrecord() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + Workshop_details.TABLE_NAME);
    }

    public void clearworkshop_detail() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + userworkshop.TABLE_NAME);
    }


    public long user_workinsert(String eid, Integer wid) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(userworkshop.COLUMN_EID, eid);
        values.put(userworkshop.COLUMN_WID, wid);

        // INSERT ROW

        long id = db.insert(userworkshop.TABLE_NAME, null, values);

        // close db connection
        db.close();

        return id;
    }


    public userdetails getuserdetails(String email) {
        // GET readable database
        SQLiteDatabase db = this.getReadableDatabase();
        userdetails note = null;

        Cursor cursor = db.query(userdetails.TABLE_NAME,
                new String[]{userdetails.COLUMN_ID, userdetails.COLUMN_EMAIL, userdetails.COLUMN_PASSWORD, userdetails.COLUMN_NAME, userdetails.COLUMN_PHONE, userdetails.COLUMN_ADDRESS},
                userdetails.COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null, null);

        if (cursor != null) {
            cursor.moveToFirst();

            note = new userdetails(
                    cursor.getInt(cursor.getColumnIndex(userdetails.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(userdetails.COLUMN_EMAIL)),
                    cursor.getString(cursor.getColumnIndex(userdetails.COLUMN_PASSWORD)),
                    cursor.getString(cursor.getColumnIndex(userdetails.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndex(userdetails.COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndex(userdetails.COLUMN_ADDRESS)));
        } else {
            Log.i("no details", "user");
        }

        // close the db connection
        cursor.close();

        return note;
    }

    public Integer getuserid(String email) {
        // GET readable database
        SQLiteDatabase db = this.getReadableDatabase();
        Integer uid = 0;

        Cursor cursor1 = db.query(userdetails.TABLE_NAME,
                new String[]{userdetails.COLUMN_ID},
                userdetails.COLUMN_EMAIL + "=?",
                new String[]{email}, null, null, null, null);

        if (cursor1 != null) {
            cursor1.moveToFirst();
            uid = cursor1.getInt(cursor1.getColumnIndex(userdetails.COLUMN_ID));
        } else {
            Log.i("no details", "user");
        }

        // close the db connection
        cursor1.close();

        return uid;
    }

    public ArrayList<Integer> getworkshops(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Integer> wids = new ArrayList<>();
        Cursor cursor = null;

        try {
            cursor = db.query(userworkshop.TABLE_NAME,
                    new String[]{userworkshop.COLUMN_WID},
                    userworkshop.COLUMN_EID + "=?",
                    new String[]{email}, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        wids.add(cursor.getInt(cursor.getColumnIndex(userworkshop.COLUMN_WID)));
                    } while (cursor.moveToNext());
                }
            } else {
                Log.i("no details", "user");
            }
            cursor.close();
        } catch (SQLiteException e) {
            e.printStackTrace();

        }
        // close the db connection

        return wids;
    }


    public long insertworkshop(String head, String details, String tech) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Workshop_details.COLUMN_HEAD, head);
        values.put(Workshop_details.COLUMN_DETAILS, details);
        values.put(Workshop_details.COLUMN_TECH, tech);

        // INSERT ROW

        long id = db.insert(Workshop_details.TABLE_NAME, null, values);

        // close db connection
        db.close();

        return id;
    }

    public List<Workshop_details> getAllWorkshop() {
        List<Workshop_details> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Workshop_details.TABLE_NAME + " ORDER BY " +
                Workshop_details.COLUMN_HEAD + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                Workshop_details work = new Workshop_details();
                work.setId(cursor.getInt(cursor.getColumnIndex(Workshop_details.COLUMN_ID)));
                work.setWork_head(cursor.getString(cursor.getColumnIndex(Workshop_details.COLUMN_HEAD)));
                work.setWork_details(cursor.getString(cursor.getColumnIndex(Workshop_details.COLUMN_DETAILS)));
                work.setColoumn_tech(cursor.getString(cursor.getColumnIndex(Workshop_details.COLUMN_TECH)));
                notes.add(work);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }


    public List<Workshop_details> registeredworkshop(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Workshop_details> wks = new ArrayList<>();
        ArrayList<Integer> wks_id = new ArrayList<>();
        Cursor cursor = null;
        Cursor cursor1 = null;

        try {
            cursor = db.query(userworkshop.TABLE_NAME,
                    new String[]{userworkshop.COLUMN_WID},
                    userworkshop.COLUMN_EID + "=?",
                    new String[]{email}, null, null, null, null);
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        wks_id.add(cursor.getInt(cursor.getColumnIndex(userworkshop.COLUMN_WID)));
                    } while (cursor.moveToNext());
                }
            } else {
                Log.i("no details", "user");
            }
            cursor.close();
            if (wks_id.size() > 0) {
                for (Integer s : wks_id) {
                    cursor1 = db.query(Workshop_details.TABLE_NAME,
                            new String[]{Workshop_details.COLUMN_ID, Workshop_details.COLUMN_HEAD, Workshop_details.COLUMN_DETAILS, Workshop_details.COLUMN_TECH},
                            Workshop_details.COLUMN_ID + "=?",
                            new String[]{String.valueOf(s)}, null, null, null, null);
                    if (cursor1 != null) {
                        cursor1.moveToFirst();
                        Workshop_details work = new Workshop_details();
                        work.setId(cursor1.getInt(cursor1.getColumnIndex(Workshop_details.COLUMN_ID)));
                        work.setWork_head(cursor1.getString(cursor1.getColumnIndex(Workshop_details.COLUMN_HEAD)));
                        work.setWork_details(cursor1.getString(cursor1.getColumnIndex(Workshop_details.COLUMN_DETAILS)));
                        work.setColoumn_tech(cursor1.getString(cursor1.getColumnIndex(Workshop_details.COLUMN_TECH)));
                        wks.add(work);
                    }
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();

        }
        // close the db connection

        return wks;
    }


}
