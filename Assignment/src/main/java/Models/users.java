package Models;

import javax.persistence.*;

import java.util.Collection;

@Entity
@Table(name="users")

public class users {
    @Id
    @GeneratedValue

    private Integer id;
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String phone;

    public users(){
        super();
    }

    public users(Integer id, String username, String password, String first_name, String last_name, String phone){
        super();
        this.id = id;
        this.username = username;
        this.password = password;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
    }






}
