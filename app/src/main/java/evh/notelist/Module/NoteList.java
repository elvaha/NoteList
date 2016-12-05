package evh.notelist.Module;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Elias on 05-12-2016.
 */

public class NoteList implements Parcelable {

    private String name;
    private int quantity;

    public NoteList(Parcel product){
        name = product.readString();
        quantity = product.readInt();
    }

    public NoteList(String name){
        this.name = name;
    }

    public NoteList(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString(){
        return name + ": " + quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(quantity);
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
