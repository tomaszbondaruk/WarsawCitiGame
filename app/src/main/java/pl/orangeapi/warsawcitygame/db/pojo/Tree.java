package pl.orangeapi.warsawcitygame.db.pojo;

/**
 * Created by Grzegorz on 2015-12-30.
 */
public class Tree extends GameObject{
    private int _id;
    private String name;
    private String street;
    private String streetNumber;
    private String district;
    private String treeClass;

    public String getTreeClass() {
        return treeClass;
    }

    public void setTreeClass(String treeClass) {
        this.treeClass = treeClass;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String getDescription() {
        return "Drzewo gatunku "+this.getName();
    }
}
