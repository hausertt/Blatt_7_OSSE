package de.hfu.residents;

import de.hfu.residents.domain.Resident;
import de.hfu.residents.repository.ResidentRepository;
import de.hfu.residents.repository.ResidentRepositoryStub;
import de.hfu.residents.service.BaseResidentService;
import de.hfu.residents.service.ResidentServiceException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.easymock.EasyMock.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class ResidentServiceTestMock {

    private Resident resident1,resident2,resident3;
    private BaseResidentService residentService;
    private List<Resident> list;

    @Before
    public void erzeugeResidents(){
        resident1 = new Resident("Helga","Testsubjekt","andereStraße2","Berlin",new Date(1950,02,03));
        resident2 = new Resident("Albert","Inkognito","Stuttgarterweg 23","Berlin",new Date(2001,01,01));
        resident3 = new Resident("Herbert","Groenemaier","Diestraßer 1","Bielefeld", new Date(2000,01,01));
        list = new ArrayList<>();
        list.add(resident1);
        list.add(resident2);
        list.add(resident3);
        residentService = new BaseResidentService();
    }

    @Test
    public void getFilteredResidentsList_Test(){
        ResidentRepository residentRepositoryMock = createMock(ResidentRepository.class);

        expect(residentRepositoryMock.getResidents()).andReturn(list).times(6);
        residentService.setResidentRepository(residentRepositoryMock);
        replay(residentRepositoryMock);

        Resident filterResident_givenName = new Resident("H*","","","",null);               // 2 hits
        Resident filterResident_familyName = new Resident("","Testsubjekt","","",null);     // 1 hit
        Resident filterResident_street = new Resident("","","Stuttgarterweg 23","",null);   // 1 hit
        Resident filterResident_city = new Resident("","","","Ber*",null);                // 2 hits
        Resident filterResident_dateOfBirth = new Resident("","","","",new Date(2001,01,01));   // 1 hit
        Resident filterResident_nullMatrix = new Resident("","","","",null);

        List<Resident> filteredList = residentService.getFilteredResidentsList(filterResident_givenName);   //Vorname
        assertThat(filteredList.get(0).getGivenName(), is(resident1.getGivenName()));
        assertThat(filteredList.get(1).getGivenName(), is(resident3.getGivenName()));

        filteredList = residentService.getFilteredResidentsList(filterResident_familyName); //Nachname
        assertThat(filteredList.get(0).getFamilyName(), is(resident1.getFamilyName()));

        filteredList = residentService.getFilteredResidentsList(filterResident_street); //Straße
        assertThat(filteredList.get(0).getStreet(), is(resident2.getStreet()));

        filteredList = residentService.getFilteredResidentsList(filterResident_city);   //Stadt
        assertThat(filteredList.get(0).getCity(), is(resident1.getCity()));
        assertThat(filteredList.get(1).getCity(), is(resident2.getCity()));

        filteredList = residentService.getFilteredResidentsList(filterResident_dateOfBirth);    //Geburtstag
        assertThat(filteredList.get(0).getDateOfBirth(), is(resident2.getDateOfBirth()));

        filteredList = residentService.getFilteredResidentsList(filterResident_nullMatrix);     //"Kein" Filter
        assertThat(filteredList.get(0).getGivenName(), is(resident1.getGivenName()));
        assertThat(filteredList.get(1).getGivenName(), is(resident2.getGivenName()));
        assertThat(filteredList.get(2).getGivenName(), is(resident3.getGivenName()));

        verify(residentRepositoryMock);
    }

    @Test
    public void getUniqueResident_Test() throws ResidentServiceException {
        ResidentRepository residentRepositoryMock = createMock(ResidentRepository.class);
        expect(residentRepositoryMock.getResidents()).andReturn(list).times(4);
        residentService.setResidentRepository(residentRepositoryMock);
        replay(residentRepositoryMock);

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

        verify(residentRepositoryMock);
    }
}
