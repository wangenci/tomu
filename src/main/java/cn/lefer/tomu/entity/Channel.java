package cn.lefer.tomu.entity;

import java.util.Date;
import java.util.List;

/**
 * @author : lefer
 * @version : V1.0
 * @date :   2020/6/15
 * @Description : 频道实体类
 */
public class Channel {
    int channelID;
    int channelName;
    int channelOwnerID;
    String channelKey;
    Date channelCreateDate;
    Song currentSong;
    int position;

    public int getChannelID() {
        return channelID;
    }

    public void setChannelID(int channelID) {
        this.channelID = channelID;
    }

    public int getChannelName() {
        return channelName;
    }

    public void setChannelName(int channelName) {
        this.channelName = channelName;
    }

    public int getChannelOwnerID() {
        return channelOwnerID;
    }

    public void setChannelOwnerID(int channelOwnerID) {
        this.channelOwnerID = channelOwnerID;
    }

    public String getChannelKey() {
        return channelKey;
    }

    public void setChannelKey(String channelKey) {
        this.channelKey = channelKey;
    }

    public Date getChannelCreateDate() {
        return channelCreateDate;
    }

    public void setChannelCreateDate(Date channelCreateDate) {
        this.channelCreateDate = channelCreateDate;
    }

    public Song getCurrentSong() {
        return currentSong;
    }

    public void setCurrentSong(Song currentSong) {
        this.currentSong = currentSong;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Channel{" +
                "channelID=" + channelID +
                ", channelName=" + channelName +
                ", channelOwnerID=" + channelOwnerID +
                ", channelKey='" + channelKey + '\'' +
                ", channelCreateDate=" + channelCreateDate +
                ", currentSong=" + currentSong +
                ", position=" + position +
                '}';
    }
}