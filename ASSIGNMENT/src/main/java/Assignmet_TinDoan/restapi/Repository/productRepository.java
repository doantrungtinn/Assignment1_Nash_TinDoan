package Assignmet_TinDoan.restapi.Repository;

import Assignmet_TinDoan.restapi.Models.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface productRepository extends JpaRepository<product, Long> {

}
