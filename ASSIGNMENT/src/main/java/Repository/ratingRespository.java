package Repository;

import Models.rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ratingRespository extends JpaRepository<rating, Integer> {


}
