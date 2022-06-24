//package com.tindoan.tshop.controller;
//
//import com.tindoan.tshop.model.rating;
//import com.tindoan.tshop.repository.RatingRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/rating")
//
//public class RatingController {
//
//    @Autowired
//    private RatingRepository ratingrepository;
//
//    @GetMapping
//    public List<rating> getAllrating(){
//        return this.ratingrepository.findAll();
//    }
//}
