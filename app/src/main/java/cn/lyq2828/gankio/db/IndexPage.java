package cn.lyq2828.gankio.db;

import org.litepal.crud.DataSupport;

/**
 * Created by singinger on 17-5-27.
 */

public class IndexPage extends DataSupport {
    private int id;

    private String tag_id;
    private String publish_time;
    private String image_url;
    private String dec;
    private String date;
    private String who;
    private int page;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_id() {

        return tag_id;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPage() {
        return page;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setDec(String dec) {
        this.dec = dec;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public String getImage_url() {
        return image_url;
    }

    public String getDec() {
        return dec;
    }

    public String getDate() {
        return date;
    }

    public String getWho() {
        return who;
    }
}
