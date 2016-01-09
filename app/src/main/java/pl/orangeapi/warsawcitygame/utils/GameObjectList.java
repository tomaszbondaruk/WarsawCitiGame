package pl.orangeapi.warsawcitygame.utils;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import pl.orangeapi.warsawcitygame.db.pojo.GameObject;

/**
 * Created by Tomek on 2016-01-09.
 */
public class GameObjectList<E> extends ArrayList<E> implements Serializable{

    public GameObjectList<E> subList(int count) {
        GameObjectList<E> newList = new GameObjectList<E>();
        for (int i = 0; i < count; i++) {
            newList.add(this.get(i));
        }
        return newList;
    }
}

