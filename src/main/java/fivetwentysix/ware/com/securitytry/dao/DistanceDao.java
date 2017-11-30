package fivetwentysix.ware.com.securitytry.dao;

import fivetwentysix.ware.com.securitytry.entity.Distance;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class DistanceDao implements IDistanceDao {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public Distance getDistanceById(int distanceId) {
        return entityManager.find(Distance.class, distanceId);
    }
    @SuppressWarnings("unchecked")
    @Override
    public List<Distance> getAllDistance() {
        String hql = "FROM Distance as dist ORDER BY dist.id";
        return (List<Distance>) entityManager.createQuery(hql).getResultList();
    }
    @Override
    public void addDistance(Distance dist) {
        entityManager.persist(dist);
    }
    @Override
    public void updateDistance(Distance distance) {
        Distance dist = getDistanceById(distance.getLocation_id());
//        artcl.setTitle(article.getTitle());
//        artcl.setCategory(article.getCategory());
        entityManager.flush();
    }
    @Override
    public void deleteDistance(int distanceId) {
        entityManager.remove(getDistanceById(distanceId));
    }
    @Override
    public boolean distanceExists(double distance, long date) {
        //case matters on Distance!!
        String hql = "FROM Distance as dist WHERE dist.mileage = ? and dist.distancedate = ?";
        int count = entityManager.createQuery(hql).setParameter(1, distance)
                .setParameter(2, date).getResultList().size();
        return count > 0 ? true : false;
    }
}