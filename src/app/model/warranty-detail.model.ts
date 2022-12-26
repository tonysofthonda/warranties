export interface WarrantyDetail {
    id:                  number;
    numberClaims:        string;
    noServOrder:         string;
    status:              string;
    dealer:              Dealer;
    serviceEmployee:     string;
    vin:                 Vin;
    firstOwner:          FirstOwner;
    warrantyClient:      WarrantyClient;
    km:                  number;
    typeClaims:          TypeClaims;
    gradeType:           GradeType;
    serviceModel:        ServiceModel;
    problemCategory:     ProblemCategory;
    technicalEmployee:   string;
    failDate:            null;
    repairDate:          Date;
    partsDefectsSymptom: PartsDefectsSymptom;
    technicalDiagnosis:  string;
    correctiveAction:    string;
    partsCost:           number;
    packagingCost:       number;
    laborCost:           number;
    sparePartsCost:      number;
    ttlCost:             number;
    totalAmount:         number;
    createDate:          Date;
    updateDate:          Date;
    saleBy:              string;
    elaboratedBY:        string;
}

export interface ServiceModel {
    id:            number;
    serviceNumber: number;
    model:         string;
    modelYear:     string;
    km:            number;
    status:        boolean;
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

export interface GradeType {
    id:              number;
    grade:           string;
    description:     string;
    status:          boolean;
    createTimestamp: Date;
    updateTimestamp: Date;
}

export interface PartsDefectsSymptom {
    id:             number;
    partMotorcycle: PartMotorcycle;
    defect:         Defect;
    symptom:        Defect;
    status:         boolean;
}

export interface Defect {
    id:              number;
    descriptionEng:  string;
    descriptionSpa:  string;
    status:          boolean;
    createTimestamp: Date;
    updateTimestamp: Date | null;
    code?:           string;
}

export interface PartMotorcycle {
    id:                 number;
    descriptionEnglish: string;
    descriptionSpanish: string;
    status:             boolean;
    createTimestamp:    Date;
    updateTimestamp:    null;
}

export interface ProblemCategory {
    id:                 number;
    problemName:        string;
    problemDescription: string;
    status:             boolean;
    createTimestamp:    Date;
    updateTimestamp:    Date;
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

export interface WarrantyClient {
    id:              number;
    clientName:      string;
    addressStreet:   string;
    email:           string;
    phone:           string;
    location:        Group;
    createTimeStamp: Date;
    updateTimeStamp: null;
}

export interface FirstOwner {
    id:         number;
    firstName:  string;
    lastName:   string;
    addrStreet: string;
    email:      string;
    phone:      string;
    location:   Group;
}

export interface TypeClaims {
    id:                number;
    claimsType:        string;
    claimsDescription: string;
    status:            boolean;
    createTimestamp:   Date;
    updateTimestamp:   Date;
}