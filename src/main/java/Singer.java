public class Singer {
    private String name;
    private int DOB;
    private boolean sex;

    // Create a class constructor for the Singer class
    public Singer(String Name, int dob, boolean Sex) {
        name=Name;
        DOB=dob;
        sex=Sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDOB() {
        return DOB;
    }

    public void setDOB(int DOB) {
        this.DOB = DOB;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public boolean checkSingerName(String name){
        boolean found=false;
        //if the name matches
        if (this.name.equals(name)){
            found=true;
        }
        return found;
    }
}
