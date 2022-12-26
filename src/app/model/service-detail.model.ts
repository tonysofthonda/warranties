export interface ServiceDetail {
    id:               number;
    serviceNumber:    string;
    status:           string;
    dealer:           Dealer;
    km:               number;
    model:            Model;
    vin:              Vin;
    serviceClient:    ServiceClient;
    saleDate:         Date;
    soldBy:           string;
    owner:            string;
    technical:        null;
    servicePerformed: null;
    serviceDetail:    null;
    createReportBy:   string;
    dateService:      null;
    createTimestamp:  Date;
    updateTimestamp:  Date;
}

export interface Dealer {
    id:                   number;
    dealerNumber:         string;
    name:                 string;
    addrStreet:           string;
    neighborhood:         string;
    dealerOwner:          string;
    zipCode:              string;
    openingHours:         string;
    openingHoursSat:      string;
    generalManager:       string;
    generalManagerTel:    string;
    generalManagerEmail:  string;
    serviceManager:       string;
    servicesManagerEmail: string;
    servicesManagerTel:   string;
    sparePartsContact:    string;
    sparePartsEmail:      string;
    sparePartsTel:        string;
    type:                 string;
    status:               boolean;
    group:                Group;
    location:             Group;
    zone:                 Group;
    createTimestamp:      Date;
    updateTimestamp:      Date;
}

export interface Group {
    id:           number;
    name:         string;
    description?: string;
    status:       boolean;
    state?:       Group;
}

export interface Model {
    id:              number;
    codModel:        string;
    model:           string;
    name:            string;
    segment:         string;
    vds:             string;
    year:            string;
    type:            string;
    status:          boolean;
    plant:           Plant;
    createTimestamp: Date;
    updateTimestamp: null;
}

export interface Plant {
    id:                 number;
    name:               string;
    plantCountry:       string;
    typeClassification: string;
    status:             boolean;
    createTimestamp:    Date;
    updateTimestamp:    null;
}

export interface ServiceClient {
    id:              number;
    name:            string;
    address:         string;
    phone:           string;
    email:           string;
    location:        Group;
    createTimestamp: Date;
    updateTimestamp: null;
}

export interface Vin {
    id:                  number;
    vin:                 string;
    engineNumber:        string;
    modelYear:           string;
    color:               string;
    salesDate:           Date;
    warrantyNumberFolio: string;
    warrantyDateExpdate: Date;
    invoiceFolio:        string;
    model:               Model;
}
