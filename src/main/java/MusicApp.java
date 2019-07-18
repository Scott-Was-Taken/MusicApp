import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
public class MusicApp {
    //main method
    public static void main(String[] args) throws IOException {
        System.out.println("Starting up NextGate Music App!");
        List<Singer> singers=SingerParser("C:/Users/scott/Desktop/MusicApp/src/main/resources/ng_singers.txt");
        //singers info can be accessed as follows singers.get(0).getName();

        List<Album> albums = AlbumParser("C:/Users/scott/Desktop/MusicApp/src/main/resources/ng_albums.txt",singers);

        //singer for the album (for example album 4) can be accessed as follows albums.get(4).getSinger().getName());
        //other album info as follows (for example title of album 4) albums.get(4).getTitle());
        List<User> users= UserParser("C:/Users/scott/Desktop/MusicApp/src/main/resources/ng_users.txt");

        // now that all the content is loaded into pojos we can try to get them on the server
        initDB(singers, albums,users);












    }

    private static List<Singer> SingerParser(String filepath) throws IOException {
        //make an arraylist of Singer items
        List<Singer> singerList = new ArrayList<Singer>();
        //make a buffered reader
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        //skip the first line as it only contains headers
        br.readLine();
        String line;
        //while we are not at the end of the file
        while((line = br.readLine()) != null) {
            //System.out.println(line);
            //split the line by the delimiter using escape to avoid regex issue with "|"
            String[] fields = line.split("\\|");
            //print the line to be parse
            //System.out.println(Arrays.toString(fields));

            //trim unnecessary spaces
            String[]data = dataTrimmer(fields);

            //the first value on the line is the name of the singer
            String name=data[0];
            //the second value on the line is the date of birth
            int dob=Integer.parseInt(data[1]);
            //the third line is the sex which is either male or female
            boolean sex;
            if (data[2].contains("FEMALE")){
                //if it contains FEMALE then it is female else it is male
                sex=false;
            }
            else{
                sex=true;
            }


            Singer singer = new Singer(name, dob, sex);
            singerList.add(singer);
            System.out.println("----Singer Parsed----");
            System.out.println("name: "+ singer.getName());
            System.out.println("Date of Birth: "+ singer.getDOB());
            System.out.println("Male?: "+ singer.isSex());
        }
        System.out.println("----ALL SINGERS PARSED----");
        return singerList;
    }

    private static List<Album> AlbumParser(String filepath, List<Singer> singers) throws IOException {
        //make an arraylist of Album items
        List<Album> albumList = new ArrayList<Album>();
        //make a buffered reader
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        //skip the first line as it only contains headers
        br.readLine();
        String line;
        //while we are not at the end of the file
        while((line = br.readLine()) != null) {
            //System.out.println(line);
            //split the line by the delimiter using escape to avoid regex issue with "|"
            String[] fields = line.split("\\|");

            //print the line to be parsed
            //System.out.println(Arrays.toString(fields));

            //trim unnecessary spaces
            String[]data = dataTrimmer(fields);

            //the first value on the line is the name of the singer - we want to get the singer object with this name
            String name=data[0];
            Singer singer=findSingerByName(singers,name);

            //the second value on the line is the album title
            String title=data[1];
            //the third value on the line is the year of the album
            int year=Integer.parseInt(data[2]);
            //the fourth value on the line is the company
            String company=data[3];


            Album album = new Album(singer, title, year, company);
            albumList.add(album);
            System.out.println("----Album Parsed----");
            System.out.println("Singer Name: "+ album.getSinger().getName());
            System.out.println("Title: "+ album.getTitle());
            System.out.println("Year: "+ album.getYear());
            System.out.println("Record Label: "+ album.getCompany());
        }

        System.out.println("----ALL ALBUMS PARSED----");
        return albumList;
    }

    private static List<User> UserParser(String filepath) throws IOException {
        //make an arraylist of User items
        List<User> userList = new ArrayList<User>();
        //make a buffered reader
        BufferedReader br = new BufferedReader(new FileReader(filepath));
        //skip the first line as it only contains headers
        br.readLine();
        String line;
        //while we are not at the end of the file
        while((line = br.readLine()) != null) {
            //System.out.println(line);
            //split the line by the delimiter using escape to avoid regex issue with "|"
            String[] fields = line.split("\\|");
            //print the line to be parsed
            //System.out.println(Arrays.toString(fields));

            //trim unnecessary spaces
            String[]data = dataTrimmer(fields);

            //the first value on the line is the username
            String username=data[0];

            //the second value on the line is the password
            String password=data[1];


            User user = new User(username,password);
            userList.add(user);
            System.out.println("----User Parsed----");
            System.out.println("Username: "+ user.getUsername());
            System.out.println("Password: "+ user.getPassword());

        }
        System.out.println("----ALL USERS PARSED----");
        return userList;
    }

    //a method to trim spaces from parsed values before pushing to pojos
    private static String[] dataTrimmer(String[] data){
        String[] trimmed=data;
        //for each value on the line
        for(int i = 0; i< trimmed.length; i++){
            //trim and replace it
            trimmed[i]=trimmed[i].trim();
        }
        //return the trimmed array
        return trimmed;
    }

    public static Singer findSingerByName(List<Singer> singers, String name){
        Singer match = new Singer("UNDEFINED", 00000000, true);
        /* For Loop for iterating ArrayList */
        for (int counter = 0; counter < singers.size(); counter++) {

            //System.out.println(singers.get(counter).getName());
            //if getName matches name queried then we have a match
            if (singers.get(counter).getName().equals(name)){
                //match found
                //System.out.println("SINGER IDENTIFIED: "+singers.get(counter).getName());
                match=singers.get(counter);
            }
        }
        return match;
    }

    //a function to initialise the DB with the POJO arrays singers albums and users
    public static void initDB(List<Singer> singers, List<Album> albums, List<User> users){
        try
        {
            // create a mysql database connection
            String Driver = "com.mysql.jdbc.Driver";
            String Url = "jdbc:mysql://localhost:3306/ng_music";
            Class.forName(Driver);
            Connection conn = DriverManager.getConnection(Url, "root", "password");


            //add the albums to the DB
            insertSingers(conn, singers);
            //add the singers to the DB
            insertAlbums(conn, albums);
            //add the users to the DB
            insertUsers(conn, users);


            conn.close();
        }
        catch (Exception e)
        {
            System.err.println("Error Caught!...");
            System.err.println(e.getMessage());
        }
    }

    public static void insertSingers(Connection conn, List<Singer> singers) throws SQLException {
        // the mysql insert statement
        String query = " insert into ng_singers (name, dob, sex)"
                + " values (?, ?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        //for each singer
        for (int counter = 0; counter < singers.size(); counter++) {
            //add the singer details to the query
            //singer name
            preparedStmt.setString (1, singers.get(counter).getName());
            //singer DOB
            //preparedStmt.setString (2, singers.get(counter).getDOB());
            String myDOB=Integer.toString(singers.get(counter).getDOB());
            preparedStmt.setString (2, myDOB);

            //preparedStmt.setString (2, singers.get(counter).getDOB());
            //singer SEX
            //if sex true then we put male into DB else put female
            if (singers.get(counter).isSex()){
                preparedStmt.setString (3, "MALE");
            }else{
                preparedStmt.setString (3, "FEMALE");
            }
            // execute the preparedstatement
            preparedStmt.execute();
        }
    }

    public static void insertAlbums(Connection conn, List<Album> albums) throws SQLException {
        // the mysql insert statement
        String query = " insert into ng_albums (ng_singers_id , album_name , release_year , record_company)"
                + " values (?, ?, ?,?)";


        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        // create the mysql singerQuery preparedstatement
        PreparedStatement singerQuery = conn.prepareStatement("Select ng_singers_id from ng_singers WHERE name=?");
        //create the mysql result set for the singer query
        ResultSet resultSet;
        //for each album
        for (int counter = 0; counter < albums.size(); counter++) {
            //add the album details to the query
            //singer id for the album
            //query the singer table for this singer to get the ID
            String singerName=albums.get(counter).getSinger().getName();
            singerQuery.setString(1,singerName);
            //execute the singer query
            resultSet = singerQuery.executeQuery();
            while (resultSet.next()) {
                //ID has been found, set the insert query parameter to the singer ID for the album
                preparedStmt.setString (1,resultSet.getString(1));
             }

            //album title
            preparedStmt.setString (2, albums.get(counter).getTitle());

            //album year
            String myYear=Integer.toString(albums.get(counter).getYear());
            preparedStmt.setString (3, myYear);
            //album record company
            preparedStmt.setString (4, albums.get(counter).getCompany());

            //execute the preparedstatement
            preparedStmt.execute();
        }
    }

    public static void insertUsers(Connection conn, List<User> users) throws SQLException {


        // the mysql insert statement
        String query = " insert into ng_users (username, password)"
                + " values (?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = conn.prepareStatement(query);
        preparedStmt.setString (1, users.get(1).getUsername());
        preparedStmt.setString (2, users.get(1).getPassword());

        //for each user
        for (int counter = 0; counter < users.size(); counter++) {
            //add the singer details to the query
            //username
            preparedStmt.setString (1, users.get(counter).getUsername());
            //password
            preparedStmt.setString (2, users.get(counter).getPassword());

            // execute the preparedstatement
            preparedStmt.execute();
        }
    }
}


