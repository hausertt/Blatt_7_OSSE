package de.hfu.residents;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepositoryStub;
import de.hfu.residents.service.BaseResidentService;
import de.hfu.residents.service.ResidentServiceException;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ResidentServiceTest {

    private Resident resident1,resident2,resident3;
    private BaseResidentService residentService;

    @Before
            public void erzeugeResidents(){
        resident1 = new Resident("Helga","Testsubjekt","andereStraße2","Berlin",new Date(1950,02,03));
        resident2 = new Resident("Albert","Inkognito","Stuttgarterweg 23","Berlin",new Date(2001,01,01));
        resident3 = new Resident("Herbert","Groenemaier","Diestraßer 1","Bielefeld", new Date(2000,01,01));

        ResidentRepositoryStub stub = new ResidentRepositoryStub();
        stub.addResident(resident1);
        stub.addResident(resident2);
        stub.addResident(resident3);

        residentService = new BaseResidentService();

        residentService.setResidentRepository(stub);
    }

    @Test
    public void getFilteredResidentsList_Test(){

        Resident filterResident_givenName = new Resident("H*","","","",null);               // 2 hits
        Resident filterResident_familyName = new Resident("","Testsubjekt","","",null);     // 1 hit
        Resident filterResident_street = new Resident("","","Stuttgarterweg 23","",null);   // 1 hit
        Resident filterResident_city = new Resident("","","","Berlin",null);                // 2 hits
        Resident filterResident_dateOfBirth = new Resident("","","","",new Date(2001,01,01));   // 1 hit
        Resident filterResident_nullMatrix = new Resident("","","","",null);

        List<Resident> filteredList = residentService.getFilteredResidentsList(filterResident_givenName);   //Vorname
        assertEquals(resident1.getGivenName(), filteredList.get(0).getGivenName());
        assertEquals(resident3.getGivenName(), filteredList.get(1).getGivenName());

        filteredList = residentService.getFilteredResidentsList(filterResident_familyName); //Nachname
        assertEquals(resident1.getGivenName(),filteredList.get(0).getGivenName());

        filteredList = residentService.getFilteredResidentsList(filterResident_street); //Straße
        assertEquals(resident2.getStreet(),filteredList.get(0).getStreet());

        filteredList = residentService.getFilteredResidentsList(filterResident_city);   //Stadt
        assertEquals(resident1.getCity(),filteredList.get(0).getCity());
        assertEquals(resident2.getCity(),filteredList.get(1).getCity());

        filteredList = residentService.getFilteredResidentsList(filterResident_dateOfBirth);    //Geburtstag
        assertEquals(resident2.getDateOfBirth(),filteredList.get(0).getDateOfBirth());

        filteredList = residentService.getFilteredResidentsList(filterResident_nullMatrix);     //"Kein" Filter
        assertEquals(resident1.getGivenName(), filteredList.get(0).getGivenName());
        assertEquals(resident2.getGivenName(), filteredList.get(1).getGivenName());
        assertEquals(resident3.getGivenName(), filteredList.get(2).getGivenName());
    }

    @Test
    public void getUniqueResident_Test() throws ResidentServiceException {

        Resident resident1_copy = new Resident("Helga","Testsubjekt","andereStraße2","Berlin",new Date(1950,02,03));
        Resident resident2_copy = new Resident("Albert","Inkognito","Stuttgarterweg 23","Berlin",new Date(2001,01,01));
        Resident resident3_copy = new Resident("Herbert","Groenemaier","Diestraßer 1","Bielefeld", new Date(2000,01,01));
        Resident filterResident_assertFalse = new Resident("","","","",new Date());


        residentService.getUniqueResident(resident1_copy);  // True

        residentService.getUniqueResident(resident2_copy);  // True

        residentService.getUniqueResident(resident3_copy);  // True

        try {
            residentService.getUniqueResident(filterResident_assertFalse);  //False
            assert(false);
        } catch (ResidentServiceException e) {
        }

    }




}
