package Sa.Nozel;

public class userProfile {


    public String Email;
    public String Fullname;
    public String Mobile;
    public String Age;




    // here we should provide default constructor empty as standard for java beans


    public userProfile() {

    }
    // Create constructor for userprofile class So when user profile created this method will run

    public userProfile(String email, String fullname, String mobile, String age) {
        Email = email;
        Fullname = fullname;
        Mobile = mobile;
        Age = age;
    }

    // create Getter and Setter to help us access the variables here and set it with the value that we provide

}
