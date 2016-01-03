package pl.orangeapi.warsawcitygame.db.adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;


import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import pl.orangeapi.warsawcitygame.db.pojo.Forest;
import pl.orangeapi.warsawcitygame.db.pojo.Property;
import pl.orangeapi.warsawcitygame.db.pojo.Shrub;
import pl.orangeapi.warsawcitygame.db.pojo.Square;
import pl.orangeapi.warsawcitygame.db.pojo.Street;
import pl.orangeapi.warsawcitygame.db.pojo.Tree;

/**
 * Created by Grzegorz on 2015-12-30.
 */
public class WarsawCitiGameDBAdapter {
    public static final int DB_VERSION = 2;
    public static final String DB_NAME = "warsawcitigame.db";
    public static final String DB_TREE_TABLE ="tree";
    public static final String DB_SHRUB_TABLE= "shrub";
    public static final String DB_PROPERTY_TABLE = "property";
    public static final String DB_FOREST_TABLE = "forest";
    public static final String DB_SQUARE_TABLE = "square";
    public static final String DB_STREET_TABLE = "street";

    public static final String KEY_ID ="_id";

    public static final String COLUMN_LONGITUDE = "longitude";
    public static final String COLUMN_LATIDUDE = "latitude";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_STREET_NUMBER ="streetNumber";
    public static final String COLUMN_CLASS = "class";
    public static final String COLUMN_DISTRICT = "district";
    public static final String COLUMN_FUNCTION = "propertyFunction";
    public static final String COLUMN_BUILD_YEAR = "buildYear";
    public static final String COLUMN_FLOOR_COUNT = "floorCount";
    public static final String COLUMN_AREA_NAME = "areaName";
    public static final String COLUMN_SURFACE = "surface";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_TYPE = "type";

    public static final String CREATE_TREE_TABLE = "CREATE TABLE "+ DB_TREE_TABLE +"("+
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_LATIDUDE + " DOUBLE," +
            COLUMN_LONGITUDE + " DOUBLE," +
            COLUMN_NAME + " TEXT," +
            COLUMN_STREET + " TEXT,"+
            COLUMN_STREET_NUMBER + " TEXT," +
            COLUMN_DISTRICT + " TEXT,"+
            COLUMN_CLASS +" TEXT)";
    public static final String CREATE_SHRUB_TABLE = "CREATE TABLE "+ DB_SHRUB_TABLE +"("+
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_LATIDUDE + " DOUBLE," +
            COLUMN_LONGITUDE + " DOUBLE," +
            COLUMN_NAME + " TEXT," +
            COLUMN_STREET + " TEXT,"+
            COLUMN_STREET_NUMBER + " TEXT," +
            COLUMN_DISTRICT + " TEXT,"+
            COLUMN_CLASS +" TEXT)";
    public static final String CREATE_PROPERTY_TABLE = "CREATE TABLE "+ DB_PROPERTY_TABLE +"("+
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_LATIDUDE + " DOUBLE," +
            COLUMN_LONGITUDE + " DOUBLE," +
            COLUMN_NAME + " TEXT," +
            COLUMN_STREET + " TEXT,"+
            COLUMN_STREET_NUMBER + " TEXT," +
            COLUMN_DISTRICT + " TEXT,"+
            COLUMN_FLOOR_COUNT +" TEXT," +
            COLUMN_FUNCTION + " TEXT," +
            COLUMN_BUILD_YEAR + " TEXT)";
    public static final String CREATE_SQUARE_TABLE = "CREATE TABLE "+ DB_SQUARE_TABLE +"("+
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_LATIDUDE + " DOUBLE," +
            COLUMN_LONGITUDE + " DOUBLE," +
            COLUMN_NAME + " TEXT," +
            COLUMN_STREET + " TEXT,"+
            COLUMN_STREET_NUMBER + " TEXT," +
            COLUMN_DISTRICT + " TEXT,"+
            COLUMN_SURFACE +" TEXT," +
            COLUMN_AREA_NAME + " TEXT)";
    public static final String CREATE_FOREST_TABLE = "CREATE TABLE "+ DB_FOREST_TABLE +"("+
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_LATIDUDE + " DOUBLE," +
            COLUMN_LONGITUDE + " DOUBLE," +
            COLUMN_NAME + " TEXT," +
            COLUMN_TYPE + " TEXT,"+
            COLUMN_DISTRICT + " TEXT,"+
            COLUMN_SURFACE +" TEXT," +
            COLUMN_AREA_NAME + " TEXT)";
    public static final String CREATE_STREET_TABLE = "CREATE TABLE "+ DB_STREET_TABLE +"("+
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_LATIDUDE + " DOUBLE," +
            COLUMN_LONGITUDE + " DOUBLE," +
            COLUMN_NAME + " TEXT," +
            COLUMN_DISTRICT + " TEXT)";

    private SQLiteDatabase db;
    private Context context;
    private DatabaseHelper dbHelper;

    public WarsawCitiGameDBAdapter(Context context){
        this.context = context;
        Log.i("adapter", "adapter created");
    }

    public WarsawCitiGameDBAdapter open()  {
        dbHelper = new DatabaseHelper(context, DB_NAME, null, DB_VERSION);
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLException e) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }
    public void close() {
        dbHelper.close();
    }

    public List<Tree> getAllTrees(){

        List<Tree> trees = new ArrayList<>();
        String query  ="SELECT * FROM tree";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    Tree tree = new Tree();
                    tree.set_id(cursor.getInt(cursor.getColumnIndex("_id")));
                    tree.setDistrict(cursor.getString(cursor.getColumnIndex("district")));
                    tree.setLatitude(cursor.getDouble(cursor.getColumnIndex("latutude")));
                    tree.setLongitude(cursor.getDouble(cursor.getColumnIndex("longitude")));
                    tree.setName(cursor.getString(cursor.getColumnIndex("name")));
                    tree.setStreet(cursor.getString(cursor.getColumnIndex("street")));
                    tree.setStreetNumber(cursor.getString(cursor.getColumnIndex("streetNumber")));
                    tree.setTreeClass(cursor.getString(cursor.getColumnIndex("class")));
                    trees.add(tree);
                }
                catch (Exception e){

                    Log.i("adapter","no i jeblo "+e.toString());
                }
            } while (cursor.moveToNext());
        }

        return trees;
    }
    public void addTree(Tree tree){
        SQLiteStatement ps = db.compileStatement("insert into tree (latitude, longitude, name, street, streetNumber, district, class) values (?,?,?,?,?,?,?)");
        ps.bindDouble(1, tree.getLatitude());
        ps.bindDouble(2, tree.getLongitude());
        ps.bindString(3, tree.getName());
        ps.bindString(4, tree.getStreet());
        ps.bindString(5, tree.getStreetNumber());
        ps.bindString(6, tree.getDistrict());
        ps.bindString(7, tree.getTreeClass());

        ps.execute();
    }

    public void addShrub(Shrub shrub) {
        SQLiteStatement ps = db.compileStatement("insert into shrub (latitude, longitude, name, street, streetNumber, district, class) values (?,?,?,?,?,?,?)");
        ps.bindDouble(1, shrub.getLatitude());
        ps.bindDouble(2, shrub.getLongitude());
        ps.bindString(3, shrub.getName());
        ps.bindString(4, shrub.getStreet());
        ps.bindString(5, shrub.getStreetNumber());
        ps.bindString(6, shrub.getDistrict());
        ps.bindString(7, shrub.getShrubClass());

        ps.execute();

    }
    public void addProperty(Property property) {
        SQLiteStatement ps = db.compileStatement("insert into property (latitude, longitude, street, streetNumber, district, floorCount,propertyFunction, buildYear) values (0.0,0.0,?,?,?,?,?,?)");
        ps.bindString(1, property.getStreet());
        ps.bindString(2, property.getStreetNumber());
        ps.bindString(3, property.getDistrict());
        ps.bindString(4, property.getFloorCount());
        ps.bindString(5, property.getPropertyFunction());
        ps.bindString(6, property.getBuildYear());
        ps.execute();
    }

    public void addSquare(Square square) {
        SQLiteStatement ps = db.compileStatement("insert into square (latitude, longitude, district, name,areaName) values (?,?,?,?,?)");
        ps.bindDouble(1, square.getLatitude());
        ps.bindDouble(2, square.getLongitude());
        ps.bindString(3, square.getDistrict());
        ps.bindString(4, square.getName());
        ps.bindString(5, square.getAreaName());
        ps.execute();
    }
    public void addStreet(Street street) {
        SQLiteStatement ps = db.compileStatement("insert into street (latitude, longitude, district, name) values (?,?,?,?)");
        ps.bindDouble(1, street.getLatitude());
        ps.bindDouble(2, street.getLongitude());
        ps.bindString(3, street.getDistrict());
        ps.bindString(4, street.getName());
        ps.execute();
    }
    public void addForest(Forest forest) {
        SQLiteStatement ps = db.compileStatement("insert into forest (latitude, longitude, name, type, district, surface, areaName) values (?,?,?,?,?,?,?)");
        ps.bindDouble(1, forest.getLatitude());
        ps.bindDouble(2, forest.getLongitude());
        ps.bindString(3, forest.getName());
        ps.bindString(4, forest.getType());
        ps.bindString(5, forest.getDistrict());
        ps.bindString(6, forest.getSurface());
        ps.bindString(7, forest.getArea());
        ps.execute();
    }
    public int treesCount(){
        String q = "select count(*) as total from tree";
        Cursor mCount= db.rawQuery(q, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }

    public boolean isAlreadyPopulated(){
        String q = "select count(*) as total from tree";
        Cursor mCount= db.rawQuery(q, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        if (count > 0)
            return true;
        else
            return false;
    }



    private static class DatabaseHelper extends SQLiteOpenHelper {
        private Context context;
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TREE_TABLE);
            db.execSQL(CREATE_SHRUB_TABLE);
            db.execSQL(CREATE_PROPERTY_TABLE);
            db.execSQL(CREATE_SQUARE_TABLE);
            db.execSQL(CREATE_STREET_TABLE);
            db.execSQL(CREATE_FOREST_TABLE);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table tree");
            db.execSQL("drop table shrub");
            db.execSQL("drop table property");
            db.execSQL("drop table square");
            db.execSQL(CREATE_TREE_TABLE);
            db.execSQL(CREATE_SHRUB_TABLE);
            db.execSQL(CREATE_PROPERTY_TABLE);
            db.execSQL(CREATE_SQUARE_TABLE);

        }
    }





}
