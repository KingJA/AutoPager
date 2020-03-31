package sample.kingja.autopager;

/**
 * Description:TODO
 * Create Time:2017/8/4 14:03
 * Author:KingJA
 * Email:kingjavip@gmail.com
 */
public class Pager {
    private int resId;
    private String title;

    public Pager(int resId, String title) {
        this.resId = resId;
        this.title = title;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
