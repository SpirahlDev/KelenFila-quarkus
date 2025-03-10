package org.spirahldev.kelenFila.domain.interfaces.repositories;


import org.spirahldev.kelenFila.domain.model.Person;

public interface IPersonRepository {
    boolean softDelete(Person person);
    Person save(Person person);
}