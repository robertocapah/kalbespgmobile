package library.salesforce.common;

public class ModelListview{
    String id;
    String name;
    int value;
    boolean selected;

    public ModelListview(String id, String name, int value, boolean selected){
        this.id = id;
        this.name = name;
        this.value = value;
        this.selected = selected;
    }
    public String getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public int getValue(){
        return this.value;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean isSelected()
    {
        return selected;
    }
}
