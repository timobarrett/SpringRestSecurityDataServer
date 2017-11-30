package fivetwentysix.ware.com.securitytry.Service;

import fivetwentysix.ware.com.securitytry.entity.Distance;
import org.springframework.security.access.annotation.Secured;

import java.util.List;

public interface IDistanceService {
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    List<Distance> getAllDistance();
    @Secured ({"ROLE_ADMIN", "ROLE_USER"})
    Distance getDistanceById(int distanceId);
    @Secured ({"ROLE_ADMIN"})
    boolean addDistance(Distance distance);
    @Secured ({"ROLE_ADMIN"})
    void updateDistance(Distance distance);
    @Secured ({"ROLE_ADMIN"})
    void deleteDistance(int distanceId);
}
