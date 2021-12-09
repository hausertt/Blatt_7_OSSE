package de.hfu.residents;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepository;
import de.hfu.residents.repository.ResidentRepositoryStub;
import de.hfu.residents.service.BaseResidentService;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class ResidentServiceTest {

    private Resident resident1,resident2,resident3;

    @Before
            public void erzeugeResidents(){
        Resident resident1 = new Resident("Helga","Testsubjekt","andereStraße2","Berlin",new Date());
        Resident resident2 = new Resident("Albert","Inkognito","Stuttgarterweg 23","Berlin",new Date(2001,01,01));
        Resident resident3 = new Resident("Herbert","Groenemaier","Diestraßer 1","Bielefeld", new Date(2000,01,01));

    }



    @Test
            public void testgetFilteredResidentsList(){
        ResidentRepositoryStub stub = new ResidentRepositoryStub();
        stub.addResident(resident1);
        stub.addResident(resident2);
        stub.addResident(resident3);

        Resident filterResident = new Resident("H*","","","",null);

        BaseResidentService residentService = new BaseResidentService();

        residentService.setResidentRepository(stub);

        List<Resident> filteredList = residentService.getFilteredResidentsList(filterResident);



    }




}
