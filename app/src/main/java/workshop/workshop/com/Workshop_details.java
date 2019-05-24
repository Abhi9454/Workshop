package workshop.workshop.com;

public class Workshop_details {

    public static final String TABLE_NAME = "workshop";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_HEAD = "work_name";
    public static final String COLUMN_DETAILS = "work_details";
    public static final String COLUMN_TECH = "work_tech";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_HEAD + " TEXT,"
                    + COLUMN_DETAILS + " TEXT,"
                    + COLUMN_TECH + " TEXT "
                    + ")";
    private int id;
    private String work_head;
    private String work_details;
    private String coloumn_tech;

    public Workshop_details() {
    }


    public Workshop_details(int id, String work_head, String work_details, String coloumn_tech) {
        this.id = id;
        this.work_head = work_head;
        this.work_details = work_details;
        this.coloumn_tech = coloumn_tech;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWork_head() {
        return work_head;
    }

    public void setWork_head(String work_head) {
        this.work_head = work_head;
    }

    public String getWork_details() {
        return work_details;
    }

    public void setWork_details(String work_details) {
        this.work_details = work_details;
    }

    public String getColoumn_tech() {
        return coloumn_tech;
    }

    public void setColoumn_tech(String coloumn_tech) {
        this.coloumn_tech = coloumn_tech;
    }
}
