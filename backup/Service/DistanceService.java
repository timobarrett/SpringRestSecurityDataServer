package fivetwentysix.ware.com.securitytry.Service;

import java.util.List;

import fivetwentysix.ware.com.securitytry.MyApplication;
import fivetwentysix.ware.com.securitytry.dao.IDistanceDao;
import fivetwentysix.ware.com.securitytry.entity.Distance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DistanceService implements IDistanceService {
    @Autowired
    private IDistanceDao distanceDao;
    private final Logger log = LoggerFactory.getLogger(MyApplication.class);

    @Override
    public Distance getDistanceById(int distanceId) {
        log.info("getDistanceById");
        Distance obj = distanceDao.getDistanceById(distanceId);
        return obj;
    }

    @Override
    public List<Distance> getAllDistance() {
        log.info("getALlDistance");
        return distanceDao.getAllDistance();
    }

    @Override
    public synchronized boolean addDistance(Distance distance) {
        log.info("addDistance");
        if (distanceDao.distanceExists(distance.getMileage(), distance.getDate())) {
            return false;
        } else {
            distanceDao.addDistance(distance);
            return true;
        }
    }

    @Override
    public void updateDistance(Distance distance) {
        distanceDao.updateDistance(distance);
    }

    @Override
    public void deleteDistance(int distanceId) {
        distanceDao.deleteDistance(distanceId);
    }
}
