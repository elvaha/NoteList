package evh.notelist.Module;

/**
 * Created by Elias on 06-12-2016.
 */

public class NoteListItem {

    private String name;
    private String quantity;

    public NoteListItem(){}

    public NoteListItem(String name, String quantity){
        this.name = name;
        this.quantity = quantity;
    }


    @Override
    public String toString(){
        return name + ": " + quantity;
    }

}
