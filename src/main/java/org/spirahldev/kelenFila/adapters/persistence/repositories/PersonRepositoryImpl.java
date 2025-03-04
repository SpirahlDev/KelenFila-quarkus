package org.spirahldev.kelenFila.adapters.persistence.repositories;

import java.time.LocalDateTime;

import org.spirahldev.kelenFila.domain.interfaces.IPersonRepository;
import org.spirahldev.kelenFila.domain.model.Person;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class PersonRepositoryImpl implements PanacheRepository<Person>, IPersonRepository {

    @Override
    public boolean softDelete(Person person) {
        try {
            person.setDeletedAt(LocalDateTime.now()); 
            persist(person);
            return true;
        } catch (Exception e) {
            Log.error("Error while soft deleting person", e);
            return false;
        }
    }

    @Override
    public Person save(Person person){
        persist(person);
        return person;
    }
}
