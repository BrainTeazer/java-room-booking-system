public class Room {
    private int roomNum;
    private String roomType;
    private double roomPrice;
    private boolean hasBalcony;
    private boolean hasLounge;
    private String eMail;

    // default constructor
    public Room() {
        this.roomNum = 0;
        this.roomType = null;
        this.roomPrice = 0;
        this.hasBalcony = false;
        this.hasLounge = false;
        this.eMail = null;
    }

    // parametric constructor
    public Room(int roomNum, String roomType, double roomPrice, boolean hasBalcony, boolean hasLounge, String eMail) {
        this.roomNum = roomNum;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.hasBalcony = hasBalcony;
        this.hasLounge = hasLounge;
        this.eMail = eMail;
    }

    // override toString method 
    @Override
    final public String toString() {
        return String.format(
            "%d %s %.2f %b %b %s\n", 
            this.getRoomNum(), 
            this.getRoomType(), 
            this.getRoomPrice(), 
            this.getHasBalcony(), 
            this.getHasLounge(), 
            this.getEMail()
        );
    }

    public int getRoomNum() {
        return this.roomNum;
    }

    public String getRoomType() {
        return this.roomType;
    }

    public double getRoomPrice() {
        return this.roomPrice;
    }

    public boolean getHasBalcony() {
        return this.hasBalcony;
    }

    public boolean getHasLounge(){
        return this.hasLounge;
    }

    public String getEMail() {
        return this.eMail;
    }


    public void setEMail(final String eMail) {
        this.eMail = eMail;
    }

}