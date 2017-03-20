package SerClass;

import java.io.Serializable;

public class StudentTransfer implements Serializable{
  private static final long serialVersionUID = -5399605122490343339L;
    private int academic_year;

    private String user_name, user_email, user_password, user_department, user_phone, user_gender, academic_id;

    public StudentTransfer() {
    }

    public StudentTransfer(String academic_id, String user_name, String user_email, String user_password, String user_department, String user_gender, String user_phone, int academic_year) {
        this.academic_id = academic_id;
        this.user_name = user_name;
        this.user_email = user_email;
        this.user_password = user_password;
        this.user_department = user_department;
        this.user_phone = user_phone;
        this.user_gender = user_gender;
        this.academic_year = academic_year;
    }

    public String getAcademic_id() {
        return academic_id;
    }

    public void setAcademic_id(String academic_id) {
        this.academic_id = academic_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_department() {
        return user_department;
    }

    public void setUser_department(String user_department) {
        this.user_department = user_department;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_gender() {
        return user_gender;
    }

    public void setUser_gender(String user_gender) {
        this.user_gender = user_gender;
    }

    public int getAcademic_year() {
        return academic_year;
    }

    public void setAcademic_year(int academic_year) {
        this.academic_year = academic_year;
    }

}
