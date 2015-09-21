package lico.example.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by zwl on 15/7/10.
 */
public class ImageInfo implements Parcelable{

    public String title;
    public String description;
    public String picUrl;
    public String url;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.description);
        dest.writeString(this.picUrl);
        dest.writeString(this.url);
    }

    public ImageInfo() {
    }

    protected ImageInfo(Parcel in) {
        this.title = in.readString();
        this.description = in.readString();
        this.picUrl = in.readString();
        this.url = in.readString();
    }

    public static final Creator<ImageInfo> CREATOR = new Creator<ImageInfo>() {
        public ImageInfo createFromParcel(Parcel source) {
            return new ImageInfo(source);
        }

        public ImageInfo[] newArray(int size) {
            return new ImageInfo[size];
        }
    };
}
