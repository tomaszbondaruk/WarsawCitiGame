package pl.orangeapi.warsawcitygame.db.pojo;

import java.io.Serializable;

/**
 * Created by Grzegorz on 2016-01-09.
 */
public abstract class GameObject implements Serializable{
    protected Double longitude;
    protected Double latitude;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public abstract String getDescription();
}






