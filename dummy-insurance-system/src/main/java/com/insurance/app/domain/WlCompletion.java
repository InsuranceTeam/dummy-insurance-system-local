package com.insurance.app.domain;

public class WlCompletion {
    private WlInsuredPersons wlInsuredPersons;
    private WlContracts wlContracts;

    public WlInsuredPersons getWlInsuredPersons() {
        return wlInsuredPersons;
    }
    public void setWlInsuredPersons(WlInsuredPersons wlInsuredPersons) {
        this.wlInsuredPersons = wlInsuredPersons;
    }
    public WlContracts getWlContracts() {
        return wlContracts;
    }
    public void setWlContracts(WlContracts wlContracts) {
        this.wlContracts = wlContracts;
    }

}
