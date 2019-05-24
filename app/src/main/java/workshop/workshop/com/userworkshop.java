package workshop.workshop.com;

public class userworkshop {

    public static final String TABLE_NAME = "usersworkshop";

    public static final String COLUMN_EID = "user_email";
    public static final String COLUMN_WID = "workshop_id";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_EID + " TEXT ,"
                    + COLUMN_WID + " INTEGER"
                    + ")";
    private int user_eid;
    private int workshop_id;

    public userworkshop() {
    }

    public userworkshop(int user_id, int workshop_id) {
        this.user_eid = user_id;
        this.workshop_id = workshop_id;
    }

    public int getUser_id() {
        return user_eid;
    }

    public void setUser_id(int user_id) {
        this.user_eid = user_id;
    }

    public int getWorkshop_id() {
        return workshop_id;
    }

    public void setWorkshop_id(int workshop_id) {
        this.workshop_id = workshop_id;
    }
}
