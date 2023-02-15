package astue.repository;

import astue.model.Device;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DeviceRepository extends JpaRepository< Device,Long> {
    @EntityGraph(attributePaths = {"plant"})
    public List<Device> findAll();

    Optional<Device> findByName(String name);
}
