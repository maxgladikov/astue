package astue.repository;

import astue.model.Device;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository< Device,Long> {
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"division","switchgear","records"})
    public List<Device> findAll();
   
//    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"division","switchgear","record"})
//    public List<Device> findAllOrderByCreatedAsc();
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH,attributePaths = {"division","switchgear","records"})
    Optional<Device> findByName(String name);
}
