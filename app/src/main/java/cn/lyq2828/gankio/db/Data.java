package cn.lyq2828.gankio.db;

/**
 * Created by singinger on 17-5-23.
 */

public class Data {
    private String image_url;
    private String desc;

    public String getImage_url() {
        return image_url;
    }

    public String getDec() {
        return desc;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setDec(String dec) {
        this.desc = dec;
    }

    public Data(String image_url, String desc) {
        this.image_url = image_url;
        this.desc = desc;
    }
}
