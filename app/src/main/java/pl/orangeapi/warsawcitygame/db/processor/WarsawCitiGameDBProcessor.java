package pl.orangeapi.warsawcitygame.db.processor;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import pl.orangeapi.warsawcitigame.R;
import pl.orangeapi.warsawcitygame.db.adapter.WarsawCitiGameDBAdapter;
import pl.orangeapi.warsawcitygame.db.pojo.Forest;
import pl.orangeapi.warsawcitygame.db.pojo.Property;
import pl.orangeapi.warsawcitygame.db.pojo.Shrub;
import pl.orangeapi.warsawcitygame.db.pojo.Square;
import pl.orangeapi.warsawcitygame.db.pojo.Street;
import pl.orangeapi.warsawcitygame.db.pojo.Tree;

/**
 * Created by Grzegorz on 2015-12-30.
 */
public class WarsawCitiGameDBProcessor {
    private Context context;
    private WarsawCitiGameDBAdapter dbAdapter;

    public WarsawCitiGameDBProcessor(Context context, WarsawCitiGameDBAdapter dbAdapter){
        this.context = context;
        this.dbAdapter = dbAdapter;
    }
    public void populateDatabase() throws IOException, JSONException {
        populateTree();
        populateShrub();
        populateProperty();
        populateSquare();
        populateStreets();
        populateForest();
        // TO DO
    }

    private void populateForest() throws IOException, JSONException {
        InputStream[] drzewaInputStream = {context.getResources().openRawResource(R.raw.lasy_wilanow), context.getResources().openRawResource(R.raw.lasy_praga)};
        for (InputStream drzewo : drzewaInputStream) {
            String drzewa = convertStreamToString(drzewo);
            JSONObject jsonRootObject = new JSONObject(drzewa);
            JSONArray jsonArray = jsonRootObject.getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonLvl2 = jsonArray.getJSONObject(i);
                JSONArray jsonArrayLvl2 = jsonLvl2.getJSONArray("properties");
                Forest forest = new Forest();
                forest.setName(jsonArrayLvl2.getJSONObject(25).getString("value"));
                forest.setDistrict(jsonArrayLvl2.getJSONObject(27).getString("value"));
                forest.setLatitude(jsonArrayLvl2.getJSONObject(0).getDouble("value"));
                forest.setLongitude(jsonArrayLvl2.getJSONObject(16).getDouble("value"));
                forest.setArea(jsonArrayLvl2.getJSONObject(6).getString("value"));
                forest.setSurface(jsonArrayLvl2.getJSONObject(12).getString("value"));
                forest.setType(jsonArrayLvl2.getJSONObject(24).getString("value"));
                dbAdapter.addForest(forest);
            }
        }
    }


    public void populateTree() throws IOException, JSONException {
        InputStream[] drzewaInputStream = {context.getResources().openRawResource(R.raw.drzewa_mokotow),context.getResources().openRawResource(R.raw.drzewa_ochota),
                context.getResources().openRawResource(R.raw.drzewa_srodmiescie),
                context.getResources().openRawResource(R.raw.drzewa_wilanow),
                context.getResources().openRawResource(R.raw.drzewa_wola),
                context.getResources().openRawResource(R.raw.drzewa_zoliboz)};
        InputStream drzewaPraga = context.getResources().openRawResource(R.raw.drzewa_praga);
        //WSZYSTKO POZA PRAGA
        for (InputStream drzewo : drzewaInputStream) {
            String drzewa = convertStreamToString(drzewo);
            JSONObject jsonRootObject = new JSONObject(drzewa);
            JSONArray jsonArray = jsonRootObject.getJSONArray("results");
            for (int i=0; i < jsonArray.length() ; i++){
                JSONObject jsonLvl2 = jsonArray.getJSONObject(i);
                JSONArray jsonArrayLvl2 = jsonLvl2.getJSONArray("properties");
                Tree tree = new Tree();
                tree.setStreet(jsonArrayLvl2.getJSONObject(2).getString("value"));
                tree.setStreetNumber(jsonArrayLvl2.getJSONObject(7).getString("value"));
                tree.setName(jsonArrayLvl2.getJSONObject(5).getString("value"));
                tree.setTreeClass(jsonArrayLvl2.getJSONObject(3).getString("value"));
                tree.setLatitude(jsonArrayLvl2.getJSONObject(0).getDouble("value"));
                tree.setLongitude(jsonArrayLvl2.getJSONObject(21).getDouble("value"));
                tree.setDistrict(jsonArrayLvl2.getJSONObject(13).getString("value"));
                dbAdapter.addTree(tree);
            }
        }
        //PRAGA
        String drzewaPraga2 = convertStreamToString(drzewaPraga);
        JSONObject jsonRootObjectPraga = new JSONObject(drzewaPraga2);
        JSONArray jsonArray = jsonRootObjectPraga.getJSONArray("results");
        for (int i=0; i < jsonArray.length() ; i++){
            JSONObject jsonLvl2 = jsonArray.getJSONObject(i);
            JSONArray jsonArrayLvl2 = jsonLvl2.getJSONArray("properties");
            Tree tree = new Tree();
            tree.setStreet(jsonArrayLvl2.getJSONObject(2).getString("value"));
            tree.setStreetNumber(""+0);
            tree.setName(jsonArrayLvl2.getJSONObject(11).getString("value"));
            tree.setTreeClass(jsonArrayLvl2.getJSONObject(11).getString("value"));
            tree.setLatitude(jsonArrayLvl2.getJSONObject(0).getDouble("value"));
            tree.setLongitude(jsonArrayLvl2.getJSONObject(1).getDouble("value"));
            tree.setDistrict(jsonArrayLvl2.getJSONObject(10).getString("value"));
            dbAdapter.addTree(tree);
        }


    }

    public void populateShrub() throws IOException, JSONException{
        InputStream[] drzewaInputStream = {context.getResources().openRawResource(R.raw.krzewy_mokotow),context.getResources().openRawResource(R.raw.krzewy_ochota),
                context.getResources().openRawResource(R.raw.krzewy_srodmiescie),
                context.getResources().openRawResource(R.raw.krzewy_wilanow),
                context.getResources().openRawResource(R.raw.krzewy_wola),
                context.getResources().openRawResource(R.raw.krzewy_zoliboz),context.getResources().openRawResource(R.raw.krzewy_praga) };
        for (InputStream drzewo : drzewaInputStream) {
            String drzewa = convertStreamToString(drzewo);
            JSONObject jsonRootObject = new JSONObject(drzewa);
            JSONArray jsonArray = jsonRootObject.getJSONArray("results");
            for (int i=0; i < jsonArray.length() ; i++){
                JSONObject jsonLvl2 = jsonArray.getJSONObject(i);
                JSONArray jsonArrayLvl2 = jsonLvl2.getJSONArray("properties");
                Shrub shrub = new Shrub();
                shrub.setLatitude(jsonArrayLvl2.getJSONObject(0).getDouble("value"));
                shrub.setLongitude(jsonArrayLvl2.getJSONObject(1).getDouble("value"));
                shrub.setDistrict(jsonArrayLvl2.getJSONObject(11).getString("value"));
                shrub.setName(jsonArrayLvl2.getJSONObject(5).getString("value"));
                shrub.setShrubClass(jsonArrayLvl2.getJSONObject(4).getString("value"));
                shrub.setStreet(jsonArrayLvl2.getJSONObject(2).getString("value"));
                shrub.setStreetNumber(""+0);
                dbAdapter.addShrub(shrub);
            }
        }
    }

    public void populateProperty() throws IOException, JSONException{
        InputStream[] drzewaInputStream = {context.getResources().openRawResource(R.raw.ewidencjalokali_mokotow),context.getResources().openRawResource(R.raw.ewidencjalokali_ochota),
                context.getResources().openRawResource(R.raw.ewidencjalokali_srodmiescie),
                context.getResources().openRawResource(R.raw.ewidencjalokali_wilanow),
                context.getResources().openRawResource(R.raw.ewidencjalokali_wola),
                context.getResources().openRawResource(R.raw.ewidencjalokali_zoliboz),context.getResources().openRawResource(R.raw.ewidencjalokali_praga) };
        for (InputStream drzewo : drzewaInputStream) {
            String drzewa = convertStreamToString(drzewo);
            JSONObject jsonRootObject = new JSONObject(drzewa);
            JSONArray jsonArray = jsonRootObject.getJSONArray("results");
            for (int i=0; i < jsonArray.length() ; i++){
                JSONObject jsonLvl2 = jsonArray.getJSONObject(i);
                JSONArray jsonArrayLvl2 = jsonLvl2.getJSONArray("properties");
                Property property = new Property();
                property.setStreetNumber(jsonArrayLvl2.getJSONObject(9).getString("value"));
                property.setStreet(jsonArrayLvl2.getJSONObject(19).getString("value"));
                property.setDistrict(jsonArrayLvl2.getJSONObject(29).getString("value"));
                property.setLatitude(0.0);
                property.setLongitude(0.0);
                property.setBuildYear(jsonArrayLvl2.getJSONObject(15).getString("value"));
                property.setFloorCount(jsonArrayLvl2.getJSONObject(21).getString("value"));
                property.setPropertyFunction(jsonArrayLvl2.getJSONObject(10).getString("value"));
                dbAdapter.addProperty(property);
            }
        }
    }

    public void populateSquare() throws IOException, JSONException{
        InputStream is =context.getResources().openRawResource(R.raw.squares);
        String drzewa = convertStreamToString(is);
        JSONObject jsonRootObject = new JSONObject(drzewa);
        JSONArray jsonArray = jsonRootObject.getJSONArray("data");
        for (int i=0; i < jsonArray.length() ; i++){
            JSONObject jsonLvl2 = jsonArray.getJSONObject(i);
            JSONObject jsonGeomerty = jsonLvl2.getJSONObject("geometry");
            JSONArray jsonCoordinates = jsonGeomerty.getJSONArray("coordinates");
            JSONArray jsonArrayProperties = jsonLvl2.getJSONArray("properties");

            Square square = new Square();
            square.setLatitude(jsonCoordinates.getJSONObject(0).getDouble("lat"));
            square.setLongitude(jsonCoordinates.getJSONObject(0).getDouble("lon"));
            square.setDistrict(jsonArrayProperties.getJSONObject(5).getString("value"));
            square.setAreaName(jsonArrayProperties.getJSONObject(4).getString("value"));
            square.setName(jsonArrayProperties.getJSONObject(2).getString("value"));
            dbAdapter.addSquare(square);

        }
    }

    public void populateStreets() throws IOException, JSONException{
        InputStream is =context.getResources().openRawResource(R.raw.streets);
        String drzewa = convertStreamToString(is);
        JSONObject jsonRootObject = new JSONObject(drzewa);
        JSONArray jsonArray = jsonRootObject.getJSONArray("data");
        for (int i=0; i < jsonArray.length() ; i++){
            JSONObject jsonLvl2 = jsonArray.getJSONObject(i);
            JSONObject jsonGeomerty = jsonLvl2.getJSONObject("geometry");
            JSONArray jsonCoordinates = jsonGeomerty.getJSONArray("coordinates");
            JSONArray jsonArrayProperties = jsonLvl2.getJSONArray("properties");

            Street street = new Street();
            street.setLatitude(jsonCoordinates.getJSONObject(0).getDouble("lat"));
            street.setLongitude(jsonCoordinates.getJSONObject(0).getDouble("lon"));
            street.setDistrict(jsonArrayProperties.getJSONObject(5).getString("value"));
            street.setName(jsonArrayProperties.getJSONObject(2).getString("value"));
            dbAdapter.addStreet(street);

        }
    }



    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
