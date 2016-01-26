package pl.orangeapi.warsawcitygame.utils.enums;

/**
 * Created by Tomek on 2016-01-26.
 */
public enum GameItem {

    Trees("Drzewo"),
    Shrubs("Krzewy"),
    TreesShrubs("Drzewa-Krzewy");

    String text;

    GameItem(String text){
        this.text = text;
    }

    public String getText(){
        return this.text;
    }
}
