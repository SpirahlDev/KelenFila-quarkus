package org.spirahldev.kelenFila.domain.interfaces;

import java.util.List;

import org.spirahldev.kelenFila.domain.model.Person;

public interface PersonRepository {
    Person findById(Long id);
    Person save(Person person);
    void delete(Person person);
    List<Person> findAll();
}