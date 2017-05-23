package cn.lyq2828.gankio.db;

import android.graphics.Bitmap;

/**
 * Created by singinger on 17-5-23.
 */

public class IndexData {
    private String image_url;
    private String desc;

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage_url() {
        return image_url;
    }

    public IndexData(String image_url, String desc) {
        this.image_url = image_url;
        this.desc = desc;
    }
}
