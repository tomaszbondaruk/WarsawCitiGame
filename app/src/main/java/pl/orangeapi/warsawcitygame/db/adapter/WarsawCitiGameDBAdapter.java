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
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pl.orangeapi.warsawcitygame.Exception.NotEnoughObjectsInAreaException;
import pl.orangeapi.warsawcitygame.db.pojo.Forest;
import pl.orangeapi.warsawcitygame.db.pojo.GameObject;
import pl.orangeapi.warsawcitygame.db.pojo.Property;
import pl.orangeapi.warsawcitygame.db.pojo.Score;
import pl.orangeapi.warsawcitygame.db.pojo.Shrub;
import pl.orangeapi.warsawcitygame.db.pojo.Square;
import pl.orangeapi.warsawcitygame.db.pojo.Street;
import pl.orangeapi.warsawcitygame.db.pojo.Tree;
import pl.orangeapi.warsawcitygame.utils.GameObjectList;

/**
 * Created by Grzegorz on 2015-12-30.
 */
public class WarsawCitiGameDBAdapter {
    public static final int DB_VERSION = 3;
    public static final String DB_NAME = "warsawcitigame1.db";
    public static final String DB_TREE_TABLE ="tree";
    public static final String DB_SHRUB_TABLE= "shrub";
    public static final String DB_PROPERTY_TABLE = "property";
    public static final String DB_FOREST_TABLE = "forest";
    public static final String DB_SQUARE_TABLE = "square";
    public static final String DB_STREET_TABLE = "street";
    public static final String DB_SCORE_TABLE = "score";

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
    public static final String COLUMN_OBJECT_NUMBER = "number";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_POINTS= "points";
    public static final String COLUMN_USER= "user";


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
    public static final String CREATE_SCORE_TABLE ="CREATE TABLE "+ DB_SCORE_TABLE+" ("+
            KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            COLUMN_POINTS + " TEXT,"+
            COLUMN_TIME + " TEXT," +
            COLUMN_USER + " TEXT," +
            COLUMN_OBJECT_NUMBER + " TEXT)";
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
                    tree.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    tree.setDistrict(cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT)));
                    tree.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATIDUDE)));
                    tree.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                    tree.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    tree.setStreet(cursor.getString(cursor.getColumnIndex(COLUMN_STREET)));
                    tree.setStreetNumber(cursor.getString(cursor.getColumnIndex(COLUMN_STREET_NUMBER)));
                    tree.setTreeClass(cursor.getString(cursor.getColumnIndex(COLUMN_CLASS)));
                    trees.add(tree);
                }
                catch (Exception e){

                    Log.i("tree","no i jeblo "+e.toString());
                }
            } while (cursor.moveToNext());
        }

        return trees;
    }
    public List<Score> getAllScores(String user , String sort){
        String query = "SELECT * FROM score";
        List<Score> scores = new ArrayList<>();
        if (user== null && sort==null)
            query  ="SELECT * FROM score order by points desc";
        if (user!=null && sort!=null)
            query = "SELECT * FROM score where user ='"+user+"' ORDER BY "+sort+" DESC";
        if (user != null)
            query = "SELECT * FROM score where user ='"+user+"'";
        if (sort !=null)
            query="SELECT * FROM SCORE ORDER BY "+ sort+" DESC";

        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                Score score = new Score();
                score.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                score.setNumber(cursor.getString(cursor.getColumnIndex(COLUMN_OBJECT_NUMBER)));
                score.setPoints(cursor.getString(cursor.getColumnIndex(COLUMN_POINTS)));
                score.setPoints(cursor.getString(cursor.getColumnIndex(COLUMN_TIME)));
                score.setUser(cursor.getString(cursor.getColumnIndex(COLUMN_USER)));
                scores.add(score);

            } while (cursor.moveToNext());
        }

        return scores;
    }
    public void addScore(Score score){
        SQLiteStatement ps = db.compileStatement("insert into score ("+COLUMN_TIME+", "+COLUMN_POINTS+", "+COLUMN_OBJECT_NUMBER+", "+COLUMN_USER+") values (?,?,?,?)");
        ps.bindString(1, score.getTime());
        ps.bindString(2, score.getPoints());
        ps.bindString(3, score.getNumber());
        ps.bindString(4, score.getUser());

        ps.execute();
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
    public List<Shrub> getAllShrubs(){

        List<Shrub> shrubs = new ArrayList<>();
        String query  ="SELECT * FROM shrub";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    Shrub shrub = new Shrub();
                    shrub.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    shrub.setDistrict(cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT)));
                    shrub.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATIDUDE)));
                    shrub.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                    shrub.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    shrub.setStreet(cursor.getString(cursor.getColumnIndex(COLUMN_STREET)));
                    shrub.setStreetNumber(cursor.getString(cursor.getColumnIndex(COLUMN_STREET_NUMBER)));
                    shrub.setShrubClass(cursor.getString(cursor.getColumnIndex(COLUMN_CLASS)));
                    shrubs.add(shrub);
                }
                catch (Exception e){

                    Log.i("shrub","no i jeblo "+e.toString());
                }
            } while (cursor.moveToNext());
        }

        return shrubs;
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
    public List<Property> getAllProperties(){

        List<Property> properties = new ArrayList<>();
        String query  ="SELECT * FROM property";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    Property property = new Property();
                    property.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    property.setDistrict(cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT)));
                    property.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATIDUDE)));
                    property.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                    property.setFloorCount(cursor.getString(cursor.getColumnIndex(COLUMN_FLOOR_COUNT)));
                    property.setStreet(cursor.getString(cursor.getColumnIndex(COLUMN_STREET)));
                    property.setStreetNumber(cursor.getString(cursor.getColumnIndex(COLUMN_STREET_NUMBER)));
                    property.setPropertyFunction(cursor.getString(cursor.getColumnIndex(COLUMN_FUNCTION)));
                    property.setBuildYear(cursor.getString(cursor.getColumnIndex(COLUMN_BUILD_YEAR)));
                    properties.add(property);
                }
                catch (Exception e){

                    Log.i("shrub","no i jeblo "+e.toString());
                }
            } while (cursor.moveToNext());
        }

        return properties;
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
    public List<Square> getAllSquares(){

        List<Square> squares = new ArrayList<>();
        String query  ="SELECT * FROM square";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    Square square = new Square();
                    square.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    square.setDistrict(cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT)));
                    square.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATIDUDE)));
                    square.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                    square.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    square.setAreaName(cursor.getString(cursor.getColumnIndex(COLUMN_AREA_NAME)));
                    squares.add(square);
                }
                catch (Exception e){

                    Log.i("shrub","no i jeblo "+e.toString());
                }
            } while (cursor.moveToNext());
        }

        return squares;
    }

    public void addStreet(Street street) {
        SQLiteStatement ps = db.compileStatement("insert into street (latitude, longitude, district, name) values (?,?,?,?)");
        ps.bindDouble(1, street.getLatitude());
        ps.bindDouble(2, street.getLongitude());
        ps.bindString(3, street.getDistrict());
        ps.bindString(4, street.getName());
        ps.execute();
    }

    public List<Street> getAllStreets(){

        List<Street> streets = new ArrayList<>();
        String query  ="SELECT * FROM street";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                try {
                    Street street = new Street();
                    street.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    street.setDistrict(cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT)));
                    street.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATIDUDE)));
                    street.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                    street.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    streets.add(street);
                }
                catch (Exception e){

                    Log.i("shrub","no i jeblo "+e.toString());
                }
            } while (cursor.moveToNext());
        }

        return streets;
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

    public List<Forest> getAllForests(){

        List<Forest> forests = new ArrayList<>();
        String query  ="SELECT * FROM forest";
        Cursor cursor = db.rawQuery(query,null);
        if (cursor.moveToFirst()) {
            do {
                try {

                    Forest forest = new Forest();
                    forest.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                    forest.setDistrict(cursor.getString(cursor.getColumnIndex(COLUMN_DISTRICT)));
                    forest.setLatitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LATIDUDE)));
                    forest.setLongitude(cursor.getDouble(cursor.getColumnIndex(COLUMN_LONGITUDE)));
                    forest.setName(cursor.getString(cursor.getColumnIndex(COLUMN_NAME)));
                    forest.setType(cursor.getString(cursor.getColumnIndex(COLUMN_TYPE)));
                    forest.setSurface(cursor.getString(cursor.getColumnIndex(COLUMN_SURFACE)));
                    forest.setArea(cursor.getString(cursor.getColumnIndex(COLUMN_AREA_NAME)));
                    forests.add(forest);
                }
                catch (Exception e){

                    Log.i("shrub","no i jeblo "+e.toString());
                }
            } while (cursor.moveToNext());
        }

        return forests;
    }
    public int treesCount(){
        String q = "select count(*) as total from tree";
        Cursor mCount= db.rawQuery(q, null);
        mCount.moveToFirst();
        int count= mCount.getInt(0);
        mCount.close();
        return count;
    }
    public int shrubCount(){
        String q = "select count(*) as total from shrub";
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

    public GameObjectList<GameObject> getStartingPoints(String object, int objectCount, Double lat, Double lng, Double radius) throws ClassNotFoundException, NotEnoughObjectsInAreaException {
        final double degToM = 111.196672;
        Double radiusInDegreeLat = radius / degToM;
        Double radiusInDegreeLng = radius / (degToM * Math.abs(Math.cos(lat)));
        Log.d("lat", ""+radiusInDegreeLat);
        Log.d("lng", ""+radiusInDegreeLng);
        GameObjectList<GameObject> lgo = new GameObjectList<>();
        switch (object){
            case "Drzewo" :
                String query  ="SELECT * FROM tree where latitude < "+(lat+radiusInDegreeLat)+" and latitude > "+(lat-radiusInDegreeLat)+ " and longitude < "+(lng+radiusInDegreeLng)+
                        " and longitude > "+(lng - radiusInDegreeLng)+ " order by RANDOM() limit "+objectCount;
                lgo = getTreeToGame(query,lgo);
                if(lgo.size() != objectCount)
                    throw new NotEnoughObjectsInAreaException("Not enough objects");

                return lgo;
            case "Krzewy" :
                String queryShrub  ="SELECT * FROM shrub where latitude < "+(lat+radiusInDegreeLat)+" and latitude > "+(lat-radiusInDegreeLat)+ " and longitude < "+(lng+radiusInDegreeLng)+
                        " and longitude > "+(lng -radiusInDegreeLng)+ " order by RANDOM() limit "+objectCount;
                lgo = getShrubToGame(queryShrub,lgo);
                if(lgo.size() != objectCount)
                    throw new NotEnoughObjectsInAreaException("Not enough objects");
                return lgo;
            case "Drzewa-Krzewy":
                String queryTreeBoth  ="SELECT * FROM tree where latitude < "+(lat+radiusInDegreeLat)+" and latitude > "+(lat-radiusInDegreeLat)+ " and longitude < "+(lng+radiusInDegreeLng)+
                        " and longitude > "+(lng - radiusInDegreeLng)+ " order by RANDOM() limit "+objectCount;
                lgo=getTreeToGame(queryTreeBoth,lgo);
                String queryShrubBoth  ="SELECT * FROM shrub where latitude < "+(lat+radiusInDegreeLat)+" and latitude > "+(lat-radiusInDegreeLat)+ " and longitude < "+(lng+radiusInDegreeLng)+
                        " and longitude > "+(lng -radiusInDegreeLng)+ " order by RANDOM() limit "+objectCount;
                lgo=getShrubToGame(queryShrubBoth,lgo);
                if(lgo.size() < objectCount)
                    throw new NotEnoughObjectsInAreaException("Not enough objects");

                long seed = System.nanoTime();
                Collections.shuffle(lgo, new Random(seed));
                return lgo.subList(objectCount);

            default:
                return lgo;

        }



    }
    public GameObjectList<GameObject> getTreeToGame(String q, GameObjectList<GameObject> go){
        GameObjectList<GameObject> gol = go;

        Cursor cursorTreeBoth = db.rawQuery(q,null);
        if (cursorTreeBoth.moveToFirst()) {
            do {
                Tree tree = new Tree();
                tree.setDistrict(cursorTreeBoth.getString(cursorTreeBoth.getColumnIndex(COLUMN_DISTRICT)));
                tree.setName(cursorTreeBoth.getString(cursorTreeBoth.getColumnIndex(COLUMN_NAME)));
                tree.setTreeClass(cursorTreeBoth.getString(cursorTreeBoth.getColumnIndex(COLUMN_CLASS)));
                tree.setLongitude(cursorTreeBoth.getDouble(cursorTreeBoth.getColumnIndex(COLUMN_LONGITUDE)));
                tree.setLatitude(cursorTreeBoth.getDouble(cursorTreeBoth.getColumnIndex(COLUMN_LATIDUDE)));
                gol.add(tree);
            } while (cursorTreeBoth.moveToNext());
        }
        return go;
    }

    public GameObjectList<GameObject> getShrubToGame(String q, GameObjectList<GameObject> go){
        GameObjectList<GameObject> gol = go;
        Cursor cursorShrubBoth = db.rawQuery(q,null);
        if (cursorShrubBoth.moveToFirst()) {
            do {
                Shrub shrub = new Shrub();
                shrub.setDistrict(cursorShrubBoth.getString(cursorShrubBoth.getColumnIndex(COLUMN_DISTRICT)));
                shrub.setName(cursorShrubBoth.getString(cursorShrubBoth.getColumnIndex(COLUMN_NAME)));
                shrub.setShrubClass(cursorShrubBoth.getString(cursorShrubBoth.getColumnIndex(COLUMN_CLASS)));
                shrub.setLongitude(cursorShrubBoth.getDouble(cursorShrubBoth.getColumnIndex(COLUMN_LONGITUDE)));
                shrub.setLatitude(cursorShrubBoth.getDouble(cursorShrubBoth.getColumnIndex(COLUMN_LATIDUDE)));
                gol.add(shrub);
            } while (cursorShrubBoth.moveToNext());
        }
        return gol;
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
            db.execSQL(CREATE_SCORE_TABLE);
            //db.execSQL(CREATE_PROPERTY_TABLE);
            //db.execSQL(CREATE_SQUARE_TABLE);
            //db.execSQL(CREATE_STREET_TABLE);
           // db.execSQL(CREATE_FOREST_TABLE);


        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table tree");
            db.execSQL("drop table shrub");
            db.execSQL("drop table score");
            //db.execSQL("drop table square");
            db.execSQL(CREATE_TREE_TABLE);
            db.execSQL(CREATE_SHRUB_TABLE);
            //db.execSQL(CREATE_PROPERTY_TABLE);
            //db.execSQL(CREATE_SQUARE_TABLE);
            db.execSQL(CREATE_SCORE_TABLE);

        }
    }
}
