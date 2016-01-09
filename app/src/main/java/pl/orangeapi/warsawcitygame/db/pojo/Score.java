package pl.orangeapi.warsawcitygame.db.pojo;

/**
 * Created by Grzegorz on 2016-01-09.
 */
public class Score {
    protected int _id;
    protected String time;
    protected String points;
    protected String number;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
