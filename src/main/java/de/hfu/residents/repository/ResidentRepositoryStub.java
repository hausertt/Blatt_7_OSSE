package de.hfu.residents.repository;

import de.hfu.residents.domain.Resident;

import java.util.ArrayList;
import java.util.List;

public class ResidentRepositoryStub implements ResidentRepository{

    List<Resident> residentList = new ArrayList<>();

    public void addResident(Resident resident){
        residentList.add(resident);
    }

    @Override
    public List<Resident> getResidents(){
        return  residentList;
    }
}
