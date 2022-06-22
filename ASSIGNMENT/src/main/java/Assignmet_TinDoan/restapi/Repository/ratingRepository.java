package Assignmet_TinDoan.restapi.Repository;

import Assignmet_TinDoan.restapi.Models.rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ratingRepository extends JpaRepository<rating, Integer> {


}
