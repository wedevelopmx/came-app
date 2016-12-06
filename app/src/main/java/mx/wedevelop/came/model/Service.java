package mx.wedevelop.came.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Base64;

/**
 * Created by root on 5/12/16.
 */
public class Service implements Parcelable {
    private int id;
    private String name;
    private String description;
    private String instructions;
    private String pictureDataURI;
    private int onlyImmigrant;
    private int oneTime;
    private int storeList;
    private int order;


    private boolean selected;
    private Bitmap image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getPictureDataURI() {
        return pictureDataURI;
    }

    public void setPictureDataURI(String pictureDataURI) {
        this.pictureDataURI = pictureDataURI;
    }

    public int getOnlyImmigrant() {
        return onlyImmigrant;
    }

    public void setOnlyImmigrant(int onlyImmigrant) {
        this.onlyImmigrant = onlyImmigrant;
    }

    public int getOneTime() {
        return oneTime;
    }

    public void setOneTime(int oneTime) {
        this.oneTime = oneTime;
    }

    public int getStoreList() {
        return storeList;
    }

    public void setStoreList(int storeList) {
        this.storeList = storeList;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public Bitmap getImage() {
        if(image == null) {
            String uri = this.pictureDataURI.substring(this.pictureDataURI.indexOf(","));
            byte[] decodedString = Base64.decode(uri.getBytes(), Base64.DEFAULT);
            image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return image;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    // Parcelling part
    public Service(Parcel in){
        String[] data = new String[9];
        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.name = data[1];
        this.description = data[2];
        this.instructions = data[3];
        this.pictureDataURI = data[4];
        this.onlyImmigrant = Integer.parseInt(data[5]);
        this.oneTime = Integer.parseInt(data[6]);
        this.storeList = Integer.parseInt(data[7]);
        this.order = Integer.parseInt(data[8]);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.id + "",
                this.name,
                this.description,
                this.instructions ,
                this.pictureDataURI,
                this.onlyImmigrant + "",
                this.oneTime + "",
                this.storeList+ "",
                this.order + ""
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Service createFromParcel(Parcel in) {
            return new Service(in);
        }

        public Service[] newArray(int size) {
            return new Service[size];
        }
    };
}
