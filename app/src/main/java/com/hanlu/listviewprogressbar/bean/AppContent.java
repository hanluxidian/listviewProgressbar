package com.hanlu.listviewprogressbar.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class AppContent implements Parcelable {
    private String mName;
    private String url;
    private int downloadPercent;
    private Status status = Status.PENDING;

    public AppContent(String mName, String url) {
        this.mName = mName;
        this.url = url;
    }

    public String getmName() {
        return mName;
    }

    public String getUrl() {
        return url;
    }

    public int getDownloadPercent() {
        return 50;
    }

    public void setDownloadPercent(int downloadPercent) {
        this.downloadPercent = downloadPercent;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mName);
        dest.writeString(url);
        dest.writeInt(downloadPercent);
        dest.writeValue(status);
    }

    public static  final Parcelable.Creator<AppContent> CREATOR =
            new Parcelable.Creator<AppContent> () {
        @Override
        public AppContent createFromParcel(Parcel source) {

            String name = source.readString();
            String url = source.readString();
            int downloadPercent = source.readInt();
            Status status = (Status)source.readValue(new ClassLoader() {});
            AppContent appContent = new AppContent(name, url);
            appContent.setDownloadPercent(downloadPercent);
            appContent.setStatus(status);
            return appContent;
        }
        @Override
        public AppContent[] newArray(int size) {
            return new AppContent[size];
        }
    };

    public enum Status {
        /**
         * Indicates that the task has not been executed yet.
         */
        PENDING(1),
            /**
             * Indicates that the task is wating.
             */
            WAITING(2),
            /**
             * Indicates that the task is downloading.
             */
            DOWNLOADING(3),

            /**
             * Indicates that the task was paused.
             */
            PAUSED(4),

            /**
             * Indicates that the task has finished.
             */
            FINISHED(5);

        private int value;

        Status(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Status getByValue(int value) {
            switch (value) {
                case 1:
                    return Status.PENDING;
                case 2:
                    return Status.WAITING;
                case 3:
                    return Status.DOWNLOADING;
                case 4:
                    return Status.PAUSED;
                case 5:
                    return Status.FINISHED;
            }
            return Status.PENDING;
        }
    }
}
