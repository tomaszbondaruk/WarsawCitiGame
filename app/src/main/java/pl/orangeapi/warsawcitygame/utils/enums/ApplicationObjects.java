package pl.orangeapi.warsawcitygame.utils.enums;

/**
 * Created by Tomek on 2016-01-26.
 */
public enum ApplicationObjects {
    GameObject ("gameObjects");

    String text;

    ApplicationObjects(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
