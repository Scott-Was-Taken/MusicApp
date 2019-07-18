public class Album {
    private Singer singer;
    private String title;
    private int Year;
    private String company;

    // Create a class constructor for the Album class
    public Album(Singer sing, String album, int year, String comp) {
        singer = sing;
        title=album;
        Year=year;
        company=comp;
    }

    public Singer getSinger() {
        return singer;
    }

    public void setSinger(Singer singer) {
        this.singer = singer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
