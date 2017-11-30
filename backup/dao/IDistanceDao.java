package fivetwentysix.ware.com.securitytry.dao;

import fivetwentysix.ware.com.securitytry.entity.Distance;
import java.util.List;

public interface IDistanceDao {
    List<Distance> getAllDistance();
    Distance getDistanceById(int distanceId);
    void addDistance(Distance distance);
    void updateDistance(Distance distance);
    void deleteDistance(int distanceId);
    boolean distanceExists(double distance, long date);
}

