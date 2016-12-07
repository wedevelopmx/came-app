package mx.wedevelop.came.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Base64;

import java.util.Date;

import mx.wedevelop.came.utils.Utils;

/**
 * Created by root on 5/12/16.
 */
public class Visitor implements Parcelable {
    private String id;
    private String firstName;
    private String lastName;
    private String secondSurename;
    private String alias;
    private String gender;
    private String profilePic;
    private String pictureDataURI;
    private String country;
    private String state;
    private String town;
    private String status;
    private Date birthdate;

    private Bitmap image;

    public Visitor(String firstName, String lastName, String secondSurename, String country, String state, String town) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.secondSurename = secondSurename;
        this.country = country;
        this.state = state;
        this.town = town;
    }

    public Visitor() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getSecondSurename() {
        return secondSurename;
    }

    public void setSecondSurename(String secondSurename) {
        this.secondSurename = secondSurename;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getPictureDataURI() {
        return pictureDataURI;
    }

    public void setPictureDataURI(String pictureDataURI) {
        this.pictureDataURI = pictureDataURI;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getDisplayName() {
        return getFirstName() + " " + getLastName() + " " + getSecondSurename();
    }

    public String getLocation() {
        return getCountry() + ", " + getState() + ", " + getTown();
    }

    public Bitmap getImage() {
        if(image == null) {
            String uri = this.pictureDataURI.substring(this.pictureDataURI.indexOf(","));
            byte[] decodedString = Base64.decode(uri.getBytes(), Base64.DEFAULT);
            image = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        }
        return image;
    }

    // Parcelling part
    public Visitor(Parcel in){
        String[] data = new String[13];
        in.readStringArray(data);
        this.id = data[0];
        this.firstName = data[1];
        this.lastName = data[2];
        this.secondSurename = data[3];
        this.alias = data[4];
        this.gender = data[5];
        this.profilePic = data[6];
        this.pictureDataURI = data[7];
        this.country = data[8];
        this.state = data[9];
        this.town = data[10];
        this.status = data[11];
        this.birthdate = Utils.parseDate(data[12]);
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {
                this.id + "",
                this.firstName,
                this.lastName,
                this.secondSurename,
                this.alias,
                this.gender,
                this.profilePic,
                this.pictureDataURI,
                this.country,
                this.state,
                this.town,
                this.status,
                Utils.formatDate(this.birthdate)
        });
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Visitor createFromParcel(Parcel in) {
            return new Visitor(in);
        }

        public Visitor[] newArray(int size) {
            return new Visitor[size];
        }
    };
}
