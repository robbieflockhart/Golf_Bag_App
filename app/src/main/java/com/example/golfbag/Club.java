/*
 * This class sets out the structure of a club object.
 *
 * @author  Robbie Flockhart - 40343879
 */
package com.example.golfbag;

public class Club {

    private String name;
    private String description;
    private int image;

    //club constructor.
    public Club(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    //club getters and setter.
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    //club toString.
    @Override
    public String toString() {
        return "Club{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image=" + image +
                '}';
    }
}
