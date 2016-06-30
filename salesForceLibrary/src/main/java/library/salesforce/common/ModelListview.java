package library.salesforce.common;

public class ModelListview{
    String name;
    int value;
    boolean selected;

    public ModelListview(String name, int value, boolean selected){
        this.name = name;
        this.value = value;
        this.selected = selected;
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
