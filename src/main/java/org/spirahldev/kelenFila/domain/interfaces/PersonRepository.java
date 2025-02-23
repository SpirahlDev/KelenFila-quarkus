package org.spirahldev.kelenFila.domain.interfaces;


import org.spirahldev.kelenFila.domain.model.Person;

public interface PersonRepository {
    boolean softDelete(Person person);
}