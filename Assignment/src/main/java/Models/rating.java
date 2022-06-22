package Models;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name="rating")
public class rating {
    @Id
    @GeneratedValue

    private Integer id;
    private String review_id;
    private String user_id;
    private String rating;

    public rating(){
        super();
    }

    public rating(Integer id, String review_id, String user_id, String rating){
        this.id = id;
        this.review_id = review_id;
        this.user_id = user_id;
        this.rating = rating;
    }

//    @ManyToOne
//    @JoinColumn(name="user_id", referencedColumnName = "id")
//    private users users;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReview_id() {
        return review_id;
    }

    public void setReview_id(String review_id) {
        this.review_id = review_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

//    public Models.users getUsers() {
//        return users;
//    }
//
//    public void setUsers(Models.users users) {
//        this.users = users;
//    }

    @Override
    public String toString() {
        return "rating{" +
                "id=" + id +
                ", review_id='" + review_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", rating='" + rating + '\'' +
//                ", users=" + users +
                '}';
    }
}
