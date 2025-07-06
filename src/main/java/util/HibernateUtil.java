package util;

import lombok.Getter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    @Getter
    private static SessionFactory sessionFactory;

    static {

        sessionFactory = new Configuration().configure().buildSessionFactory();

    }

    public static void shutdown() {
        sessionFactory.close();
    }


}
