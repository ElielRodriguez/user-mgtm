package org.acme.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import org.acme.entity.Users;

@ApplicationScoped
public class UsersRepository implements PanacheRepository<Users> {

    public Users findByEmail(final String email) {
        return find("email", email).firstResult();
    }

    public PanacheQuery<Users> findByCountry(final String country) {
        return find("country", country);
    }
}
