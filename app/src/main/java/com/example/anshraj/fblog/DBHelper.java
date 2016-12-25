package com.example.anshraj.fblog;



import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by ansh raj on 26-May-16.
 */


public class DBHelper extends SQLiteOpenHelper {
    private static final String imageId="image";
    private static final String add="address";
    private static final String price="price";
    private static final String mobile="mobile";
    private static final String type="type";

    private static final String DATABASE_NAME="apunKaDatabse";
    private static final String tableName="MYTABLE";
    private static final String tableName2="MYTABLE2";

    private static final String tableName_item="MYTABLEITEM";

    private static final int DATABASE_VERSION=1;

    private static final String userID="_id"; //primary ey shld be start with underscore

    private static final String userName="Name";
    private static final String CREATE_TABLE="CREATE TABLE "+tableName+" ("+userID+" VARCHAR(255) PRIMARY KEY, "+userName+" VARCHAR(255));";
    private static final String CREATE_TABLE2="CREATE TABLE "+tableName2+" ("+imageId+" VARCHAR(255) PRIMARY KEY, "+add+" VARCHAR(255), "+price+" VARCHAR(255), "+mobile+" VARCHAR(255), "+type+" VARCHAR(255));";

    // private static final String CREATE_TABLE_ITEMS="CREATE TABLE "+tableName_item+" ("+userID+" VARCHAR(255) PRIMARY KEY, "+imageId+" VARCHAR(255), "+add+" VARCHAR(255), "+price+" VARCHAR(255), "+mobile+" VARCHAR(255), "+type+" VARCHAR(255));";

    private static final String DROP_TABLE="DROP TABLE "+tableName+" IF EXISTS";
    private static final String DROP_TABLE_ITEM="DROP TABLE "+tableName_item+" IF EXISTS";

    public final static String TAG="MyApplication";
    public final static String password="sdfsdf";
    private Context context;
    SQLiteDatabase sqLiteDatabase;
    SQLiteDatabase sqLiteDatabaseread;

     public DBHelper(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
         this.context=context;
        sqLiteDatabase=super.getWritableDatabase();
         sqLiteDatabaseread=super.getReadableDatabase();
         Log.i(TAG,"constructor called");
    }
    public String insertData(String email,String password)
    {

        ContentValues contentValues=new ContentValues();
        contentValues.put(userID,email);
        contentValues.put(userName,password);
        long id=sqLiteDatabase.insert(tableName,null,contentValues);
        Log.i(TAG,"ID LINE EXECUTED   "+sqLiteDatabase+",id="+id);

        Cursor cursor=sqLiteDatabaseread.rawQuery("Select * from "+tableName,null);
        String idUser="";
        String data="";

        if(cursor.moveToFirst())
        {
            do{
                idUser=cursor.getString(cursor.getColumnIndex(userID));
                data=cursor.getString(cursor.getColumnIndex(userName));
              }while(cursor.moveToNext());
          }
        Log.i(TAG,"cursor lined-editText 1"+idUser+"text 2"+data);
        return idUser;
}

    public String searchData(String email,String password)
    {

        Cursor cursor=sqLiteDatabaseread.rawQuery("Select * from "+tableName,null);
        String idUser="";
        String data="";

        cursor.moveToFirst();


            do{
                idUser=cursor.getString(cursor.getColumnIndex(userID));
           data=cursor.getString(cursor.getColumnIndex(userName));

                if(idUser.equalsIgnoreCase(email)&&data.equalsIgnoreCase(password))
            {

                return idUser;
              }
                else
            {

            }
            }while(cursor.moveToNext());

    return "false";
    }

@Override
    public void onCreate(SQLiteDatabase db) {

        try{

     db.execSQL(CREATE_TABLE);
     db.execSQL(CREATE_TABLE2);

            Log.i(TAG,"table created");
            //Toast.makeText(context,"ocreate",Toast.LENGTH_LONG).show();
        }
catch(SQLException e)
{
    Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
Log.i(TAG,e.getMessage());
    Log.i(TAG,"ERROR");
}
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG,"DROP CALLED");

        try{
          db.execSQL(DROP_TABLE);
            Log.i(TAG,"DROP CALLED");
          //  db.execSQL(DROP_TABLE_ITEM);

            onCreate(db);
            Log.i(TAG,"DROP CALLED");
            Toast.makeText(context,"onupgrade",Toast.LENGTH_LONG).show();


        }
        catch(SQLException ex)
        {
            Toast.makeText(context,ex.getMessage(),Toast.LENGTH_LONG).show();

            Log.i(TAG,ex.getMessage());

        }

    }
}
