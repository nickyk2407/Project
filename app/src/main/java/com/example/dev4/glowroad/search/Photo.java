
package com.example.dev4.glowroad.search;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo implements Parcelable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("owner")
    @Expose
    private String owner;
    @SerializedName("secret")
    @Expose
    private String secret;
    @SerializedName("server")
    @Expose
    private String server;
    @SerializedName("farm")
    @Expose
    private Integer farm;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("ispublic")
    @Expose
    private Integer ispublic;
    @SerializedName("isfriend")
    @Expose
    private Integer isfriend;
    @SerializedName("isfamily")
    @Expose
    private Integer isfamily;
    @SerializedName("url_s")
    @Expose
    private String urlS;
    @SerializedName("height_s")
    @Expose
    private String heightS;
    @SerializedName("width_s")
    @Expose
    private String widthS;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public Integer getFarm() {
        return farm;
    }

    public void setFarm(Integer farm) {
        this.farm = farm;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getIspublic() {
        return ispublic;
    }

    public void setIspublic(Integer ispublic) {
        this.ispublic = ispublic;
    }

    public Integer getIsfriend() {
        return isfriend;
    }

    public void setIsfriend(Integer isfriend) {
        this.isfriend = isfriend;
    }

    public Integer getIsfamily() {
        return isfamily;
    }

    public void setIsfamily(Integer isfamily) {
        this.isfamily = isfamily;
    }

    public String getUrlS() {
        return urlS;
    }

    public void setUrlS(String urlS) {
        this.urlS = urlS;
    }

    public String getHeightS() {
        return heightS;
    }

    public void setHeightS(String heightS) {
        this.heightS = heightS;
    }

    public String getWidthS() {
        return widthS;
    }

    public void setWidthS(String widthS) {
        this.widthS = widthS;
    }

    public static Creator getCREATOR() {
        return CREATOR;
    }

    public Photo(Parcel in) {
        this.id = in.readString();
        this.owner = in.readString();
        this.secret = in.readString();
        this.server = in.readString();
        this.farm = in.readInt();
        this.title = in.readString();
        this.ispublic = in.readInt();
        this.isfriend = in.readInt();
        this.isfamily = in.readInt();
        this.urlS = in.readString();
        this.heightS = in.readString();
        this.widthS = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.owner);
        parcel.writeString(this.secret);
        parcel.writeString(this.server);
        parcel.writeInt(this.farm);
        parcel.writeString(this.title);
        parcel.writeInt(this.ispublic);
        parcel.writeInt(this.isfriend);
        parcel.writeInt(this.isfamily);
        parcel.writeString(this.urlS);
        parcel.writeString(this.heightS);
        parcel.writeString(this.widthS);
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                '}';
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Photo createFromParcel(Parcel in) {
            return new Photo(in);
        }

        public Photo[] newArray(int size) {
            return new Photo[size];
        }
    };


    public static final DiffUtil.ItemCallback<Photo> DIFF_CALL = new DiffUtil.ItemCallback<Photo>() {
        @Override
        public boolean areItemsTheSame(Photo oldItem, Photo newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(Photo oldItem, Photo newItem) {
            return oldItem.id == newItem.id;
        }
    };
}
