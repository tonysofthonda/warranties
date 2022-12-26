import { Evidence } from "./evidence.model";

export interface WarrantyClaim {
    id:                        number;
    status:                    string;
    personalInformation:       PersonalInformation;
    classificationInformation: ClassificationInformation;
    partInformation:           PartInformation;
}

export interface CostDetail {
    laborTime: number;
    laborCost: number;
    totalLaborTime: number;
    sparePartCost: number;
    landingCost: number;
    totalSparePart: number;
    totalSparePartAndLabor: number;
}

export interface ClassificationInformation {
    typeClaim:        CategoryProblem;
    grade:            CategoryProblem;
    servicePerformed: CategoryProblem;
    categoryProblem:  CategoryProblem;
    repairTechnician: string;
    reportedDate:     Date;
    rapairDate:       Date;
}

export interface CategoryProblem {
    label: string;
    value: number;
}

export interface PartInformation {
    defectPart:          DefectPart;
    relationPart:        RelationPart[];
    otherJobInformation: OtherJobInformation[];
    costDetail: CostDetail;
    evidence: Evidence[];
}

export interface DefectPart {
    affectedPart:     CategoryProblem;
    affectedDefect:   CategoryProblem;
    symptom:          CategoryProblem;
    diagTechnical:    string;
    correctiveAction: string;
}

export interface OtherJobInformation {
    id:             number;
    description:    string;
    quantity:       number;
    details:        string;
    invoice:        string;
    cost:           number;
    total:          number;
    warrantyClaims: number;
}

export interface RelationPart {
    id:               number;
    description:      string;
    quantity:         number;
    packingCost:      number;
    unitCost:         number;
    total:            number;
    part:             Part;
    warrantyClaimsId: number;
}

export interface Part {
    id:                   number;
    model:                string;
    frt:                  number;
    yearModelFrom:        number;
    yearModelTo:          number;
    partNumber:           string;
    descriptionPart:      string;
    price:                number;
    lonCode:              string;
    reference:            number;
    block:                string;
    publishCatalogNumber: string;
}

export interface PersonalInformation {
    clientInformation:     ClientInformation;
    reportInformation:     ReportInformation;
    mobileUnitInformation: MobileUnitInformation;
}

export interface ClientInformation {
    clientName: string;
    address:    string;
    phone:      string;
    state:      CategoryProblem;
    location:   CategoryProblem;
    email:      string;
}

export interface MobileUnitInformation {
    vinNumber:   string;
    mileage:     number;
    model:       CategoryProblem;
    engineSerie: string;
    saleDate:    Date;
    saleBy:      string;
    owner:       string;
}

export interface ReportInformation {
    claimNumber:        string;
    serviceOrderNumber: string;
    status:             Status;
    reportCreationDate: Date;
    dealerName:         string;
    elaboratedBY:       string;
}

export interface Status {
    label: string;
    value: string;
}
