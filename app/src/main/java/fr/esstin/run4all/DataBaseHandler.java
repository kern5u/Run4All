package fr.esstin.run4all;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "RunData.db";
    public static final String DONNEES_TABLE_NAME = "donnees";
    public static final String DONNEES_KEY = "id";
    public static final String DONNEES_TIMESTAMP = "timestamp";
    public static final String DONNEES_TEMPS = "temps";
    public static final String DONNEES_DISTANCE = "distance";
    public static final String DONNEES_VITESSE = "vitesse";


    public DataBaseHandler(Context context){
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table donnees " +
                        "(id integer primary key, timestamp real, temps real, distance real, vitesse real) "
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS donnees");
        onCreate(db);
    }

    public boolean insertRunData(long ts, long tps, float ds, float vm){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("timestamp", ts);
        contentValues.put("temps", tps);
        contentValues.put("distance", ds);
        contentValues.put("vitesse", vm);
        db.insert("donnees", null, contentValues);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from donnees where id="+id+"", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int)DatabaseUtils.queryNumEntries(db, DONNEES_TABLE_NAME);
        return numRows;
    }

    public ArrayList<Long> getAllTimestamp()
    {
        ArrayList<Long> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from donnees", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getLong(res.getColumnIndex(DONNEES_TIMESTAMP)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Float> getAllvitesse()
    {
        ArrayList<Float> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from donnees", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getFloat(res.getColumnIndex(DONNEES_VITESSE)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Long> getAllTemps()
    {
        ArrayList<Long> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from donnees", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getLong(res.getColumnIndex(DONNEES_TEMPS)));
            res.moveToNext();
        }
        return array_list;
    }

    public ArrayList<Float> getAllDistance()
    {
        ArrayList<Float> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from donnees", null );
        res.moveToFirst();

        while(!res.isAfterLast()){
            array_list.add(res.getFloat(res.getColumnIndex(DONNEES_DISTANCE)));
            res.moveToNext();
        }
        return array_list;
    }

    public void deleteAllTable(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ DONNEES_TABLE_NAME);
    }
}
