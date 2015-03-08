package tn.droidcon.testprovider.wrapper;


public class ListItemWrapper {

    private long mId;
    private String mTitle;
    private String mDescription;

    public ListItemWrapper() {

    }


    public ListItemWrapper(int image, String title, String time, String state) {

        mTitle = title;
        mDescription = time;


    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String desc) {
        this.mDescription = desc;
    }

}
