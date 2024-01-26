package faheem.microservices.belan.BelanRepository;

import faheem.microservices.belan.entity.Belan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BelanRepository extends JpaRepository<Belan,Integer> {

    public Belan findByBelanColor(String belanColor);

    public Belan findByBelanName(String belanName);
}
