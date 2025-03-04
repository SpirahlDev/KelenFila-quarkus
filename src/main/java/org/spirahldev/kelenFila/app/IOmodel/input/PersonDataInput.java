package org.spirahldev.kelenFila.app.IOmodel.input;

import java.time.LocalDate;

public record PersonDataInput(
    String firstName,               
    String lastName,                
    String email,                   
    String phone,                   
    String address,                 
    String city,                    
    int country,      
    LocalDate birthDate,           
    String avatar
) {}
