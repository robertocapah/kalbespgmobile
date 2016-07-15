package library.salesforce.common;

import java.io.Serializable;

public class ModelListview implements Serializable{
    private String _id;
    private String _name;
    private int _value;
    private boolean _selected;
    private int price;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public int get_value() {
        return _value;
    }

    public void set_value(int _value) {
        this._value = _value;
    }

    public boolean is_selected() {
        return _selected;
    }

    public void set_selected(boolean _selected) {
        this._selected = _selected;
    }

    public boolean isSelected()
    {
        return _selected;
    }

//    public ModelListview(String id, String name, int value, boolean selected){
//        this.id = id;
//        this.name = name;
//        this.value = value;
//        this.selected = selected;
//    }
//    public String getId(){
//        return this.id;
//    }
//    public String getName(){
//        return this.name;
//    }
//    public int getValue(){
//        return this.value;
//    }
//
//    public void setSelected(boolean selected)
//    {
//        this.selected = selected;
//    }
//
//    public void setValue(int value)
//    {
//        this.value = value;
//    }
//
//    public boolean isSelected()
//    {
//        return selected;
//    }
}
