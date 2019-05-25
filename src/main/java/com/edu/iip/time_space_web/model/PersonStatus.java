package com.edu.iip.time_space_web.model;

/**
 * @author zengc
 * @date 2019/5/25 19:55
 */
public class PersonStatus {

    private String name;
    private double state;
    private String label;
    private String color;



    public PersonStatus(String name, double state) {
        this.name = name;
        this.state = state;
    }

    public PersonStatus(String name, double state, String label) {
        this.name = name;
        this.state = state;
        this.label = label;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getState() {
        return state;
    }

    public void setState(double state) {
        this.state = state;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public PersonStatus fillColor(){
        color = MapColor.getColorByBaiFenBi(state);
        return this;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "PersonStatus{" +
                "name='" + name + '\'' +
                ", state=" + state +
                ", label='" + label + '\'' +
                '}';
    }
}
