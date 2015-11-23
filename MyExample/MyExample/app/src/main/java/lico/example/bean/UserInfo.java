package lico.example.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2015/10/28.
 */
public class UserInfo implements Parcelable{

    public int avator;
    public String name;
    public String email;
    public String phone;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.avator);
        dest.writeString(this.name);
        dest.writeString(this.email);
        dest.writeString(this.phone);
    }

    public UserInfo() {
    }

    protected UserInfo(Parcel in) {
        this.avator = in.readInt();
        this.name = in.readString();
        this.email = in.readString();
        this.phone = in.readString();
    }

    public static final Creator<UserInfo> CREATOR = new Creator<UserInfo>() {
        public UserInfo createFromParcel(Parcel source) {
            return new UserInfo(source);
        }

        public UserInfo[] newArray(int size) {
            return new UserInfo[size];
        }
    };
}
