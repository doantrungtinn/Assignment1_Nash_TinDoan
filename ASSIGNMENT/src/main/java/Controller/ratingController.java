package Controller;


import Models.rating;
import Repository.ratingRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rating")
public class ratingController {
    @Autowired
    private ratingRespository repository;

    @GetMapping("")
    public List<rating> findAll(){
        return repository.findAll();
    }

}
