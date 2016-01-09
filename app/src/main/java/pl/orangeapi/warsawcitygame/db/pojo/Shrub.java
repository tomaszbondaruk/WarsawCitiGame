package pl.orangeapi.warsawcitygame.db.pojo;

/**
 * Created by Grzegorz on 2015-12-30.
 */
public class Shrub extends  GameObject{
    private int _id;
    private String name;
    private String street;
    private String streetNumber;
    private String district;
    private String shrubClass;

    public String getShrubClass() {
        return shrubClass;
    }

    public void setShrubClass(String shrubClass) {
        this.shrubClass = shrubClass;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String getDescription() {
        return "To jest krzew gatunku "+getName() +" - "+getShrubClass();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
