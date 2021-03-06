package pl.orangeapi.warsawcitygame.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomek on 29/12/2015.
 */
public class GameConfiguration
    implements Serializable{

    private int noParticipants;
    private double gameRadius;
    private int maxN, maxS, maxE, maxW;
    private int noElements;
    private String gameObjects;

    public GameConfiguration (){
        noParticipants = 0;
        gameRadius = 0;
        maxE = 0;
        maxS = 0;
        maxE = 0;
        maxW = 0;
        noElements = 0;
        gameObjects = "";
    }

    public void setNoParticipants(int noParticipants) {
        this.noParticipants = noParticipants;
    }

    public void setGameRadius(double gameRadius) {
        this.gameRadius = gameRadius;
    }

    public void setMaxN(int maxN) {
        this.maxN = maxN;
    }

    public void setMaxS(int maxS) {
        this.maxS = maxS;
    }

    public void setMaxE(int maxE) {
        this.maxE = maxE;
    }

    public void setMaxW(int maxW) {
        this.maxW = maxW;
    }

    public void setNoElements(int noElements) {
        this.noElements = noElements;
    }

    public int getNoParticipants() {
        return noParticipants;
    }

    public double getGameRadius() {
        return gameRadius;
    }

    public int getMaxN() {
        return maxN;
    }

    public int getMaxS() {
        return maxS;
    }

    public int getMaxE() {
        return maxE;
    }

    public int getMaxW() {
        return maxW;
    }

    public int getNoElements() {
        return noElements;
    }

    public String getGameObjects() {
        return gameObjects;
    }

    public void setGameObjects(String gameObjects) {
        this.gameObjects = gameObjects;
    }
}
