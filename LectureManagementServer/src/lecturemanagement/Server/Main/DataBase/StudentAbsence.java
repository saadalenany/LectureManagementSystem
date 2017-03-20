package lecturemanagement.Server.Main.DataBase;

/**
 *
 * @author Saad
 */
public class StudentAbsence {
    String user_name , user_department;
    String lecture_name;

    public StudentAbsence(String user_name, String user_department, String lecture_name) {
        this.user_name = user_name;
        this.user_department = user_department;
        this.lecture_name = lecture_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_department() {
        return user_department;
    }

    public void setUser_department(String user_department) {
        this.user_department = user_department;
    }

    public String getLecture_name() {
        return lecture_name;
    }

    public void setLecture_name(String lecture_name) {
        this.lecture_name = lecture_name;
    }

}
