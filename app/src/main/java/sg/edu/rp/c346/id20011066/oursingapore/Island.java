package sg.edu.rp.c346.id20011066.oursingapore;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Island implements Serializable {
    private int id;
    private String name;
    private String description;
    private int sqKM;
    private int stars;

    public Island(int id, String name, String description, int sqKM, int stars) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.sqKM = sqKM;
        this.stars = stars;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSqKM(int sqKM) {
        this.sqKM = sqKM;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public int getSqKM() {
        return sqKM;
    }
    public int getStars() {
        return stars;
    }

    @NonNull
    @Override
    public String toString() {

        return name + "\n" + description + "-" + sqKM + "\n" + toStars();
    }
    public String toStars() {
        String repeated = new String(new char[stars]).replace("\0", "* ");
        return repeated;
    }
}


