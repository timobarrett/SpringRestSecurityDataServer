package fivetwentysix.ware.com.securitytry.dao;

import fivetwentysix.ware.com.securitytry.entity.Weather;
import org.hibernate.annotations.Proxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by tim on 6/26/2017.
 */
@Transactional
@Repository

public class WeatherDao implements IWeatherDao {
//    @Autowired
//    private SessionFactory _sessionFactory;
//
//    private Session getSession() {
//        return _sessionFactory.getCurrentSession();
//    }
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(Weather weather) {
        //getSession().save(weather);
        entityManager.persist(weather);
    }
    @Override
    public void delete(Weather weather) {
        entityManager.remove(weather);
        //getSession().delete(weather);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Weather> getAll() {
        String hql = "FROM Weather as weat ORDER BY weat.id";
        return (List<Weather>) entityManager.createQuery(hql).getResultList();
       // return entityManager.createQuery("from weather").getResultList();
     //   return getSession().createQuery("from weather").list();
    }

    @Override
    public Weather getWeatherById(int weatherId) {
        return entityManager.find(Weather.class, weatherId);
    }

    // this returned a messed up object find out why?
//    public WeatherData getById(int id) {
//        return (WeatherData) getSession().load(WeatherData.class, id);
//    }

    //    @Query(value = " Select * from weather where weather.id = :id ",nativeQuery = true)
//    public WeatherData get(@Param("id")int id);
//    public Weather getByTemp(double temp) {
//        return (Weather) getSession().load(Weather.class, temp);
//    }
//    public Weather getByHumidity(double humidity){return (Weather)getSession().load(Weather.class, humidity);}
//    public Weather getByWeatherdate(long weatherdate){return (Weather)getSession().load(Weather.class, weatherdate);}
//    public Weather getByShortDesc(String short_desc){
//        return (Weather) getSession().load(Weather.class, short_desc);
//    }
//
//    public void update(Weather weather) {
//        getSession().update(weather);
//    }
    @Override
    public boolean weatherExists(double weather, long date) {
        String hql = "FROM Weather as weat WHERE weat.temp = ? and weat.weatherdate = ?";
        int count = entityManager.createQuery(hql).setParameter(1, weather)
                .setParameter(2, date).getResultList().size();
        return count > 0 ? true : false;
    }
}


