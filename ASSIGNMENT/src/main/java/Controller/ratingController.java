package Controller;


import Models.rating;
import Repository.ratingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
//@RequestMapping("./rating")
@RequestMapping(value = {"/rating"}, method = {RequestMethod.GET})
public class ratingController {
    @Autowired
    private ratingRepository repository;


    public List<rating> findAll(){
        return findAll();
    }

}
