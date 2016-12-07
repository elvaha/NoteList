package evh.notelist.Module;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Elias on 05-12-2016.
 */

public class NoteList implements Parcelable {

    private String name;
    private String quantity;
    private ArrayList<NoteListItem> noteListItems;

    public NoteList(Parcel product){
        name = product.readString();
    }

    public NoteList(String name, String quantity){
        this.name = name;
        this.quantity = quantity;
//        this.id = UUID.randomUUID().toString();
//        this.noteListItems = new ArrayList<NoteListItem>();
    }

    public NoteList(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    //    public String getId(){ return id; }
//    public ArrayList<NoteListItem> getNoteListItems(){
//        return noteListItems;
//    }

//    public void addNoteListItem(NoteListItem noteListItem){
//        noteListItems.add(noteListItem);
//    }

    @Override
    public String toString(){
        String noteList = "";

        if(quantity == null || quantity == "0"){
            noteList = name;
            return noteList;
        }else {
            noteList = name + ": " + quantity;
            return noteList;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    // Creator
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public NoteList createFromParcel(Parcel in) {
            return new NoteList(in);
        }

        public NoteList[] newArray(int size) {
            return new NoteList[size];
        }
    };

}
