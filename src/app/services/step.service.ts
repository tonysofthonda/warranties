import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';
import { WarrantyDetail } from '../model/warranty-detail.model';
import { PartsReplaced } from '../model/parts-replaced.model';
import { CostDetail, OtherExpenses } from '../model/warranty-claims.model';
import { Evidence } from '../model/evidence.model';

@Injectable({
    providedIn: 'root'
})
export class StepService {

    warrantyInformation = {
        id: null,
        status: null,
        personalInformation: {
            clientInformation: {
                clientName: null,
                address: null,
                phone: null,
                state: { label: null, value: null },
                location: { label: null, value: null },
                email: null
            },
            reportInformation: {
                claimNumber: null,
                serviceOrderNumber: null,
                status: null,
                reportCreationDate: null,
                dealerName: null,
                elaboratedBY: null
            },
            mobileUnitInformation: {
                vinNumber: null,
                mileage: null,
                model:  { label: null, value: null },
                engineSerie: null,
                saleDate: null,
                saleBy: null,
                owner: null
            }
        },
        classificationInformation: {
            typeClaim: { label: null, value: null },
            grade: { label: null, value: null },
            servicePerformed: null,
            categoryProblem: { label: null, value: null },
            repairTechnician: null,
            reportedDate: null,
            rapairDate: null
        },
        partInformation: {
            defectPart: {
                affectedPart: { label: null, value: null },
                affectedDefect: { label: null, value: null },
                symptom: { label: null, value: null },
                diagTechnical: null,
                correctiveAction: null
            },
            relationPart: [{
                id: null,
                description: null,
                quantity: null,
                packingCost: null,
                unitCost: null,
                total: null,
                part: null,
                warrantyClaimsId: null
            }],
            otherJobInformation: [{
                id: null,
                description: null,
                quantity: null,
                details: null,
                invoice: null,
                cost: null,
                total: null,
                warrantyClaims: null
            }],
            costDetail: {
                laborTime: null,
                laborCost: null,
                totalLaborTime: null,
                sparePartCost: null,
                landingCost: null,
                totalSparePart: null,
                totalSparePartAndLabor: null
            },
            evidence: [{
                fileName: null,
                fileViewUri: null,
                fileType: null,
                size: null
            }]
        }
    };

    private partComplete = new Subject<any>();
    
    partComplete$ = this.partComplete.asObservable();

    getWarrantyInformation() {
        return this.warrantyInformation;
    }

    resetWarrantyInformation() { 
        this.warrantyInformation = {
            id: null,
            status: null,
            personalInformation: {
                clientInformation: {
                    clientName: null,
                    address: null,
                    phone: null,
                    state: { label: null, value: null },
                    location: { label: null, value: null },
                    email: null
                },
                reportInformation: {
                    claimNumber: null,
                    serviceOrderNumber: null,
                    status: null,
                    reportCreationDate: null,
                    dealerName: null,
                    elaboratedBY: null
                },
                mobileUnitInformation: {
                    vinNumber: null,
                    mileage: null,
                    model:  { label: null, value: null },
                    engineSerie: null,
                    saleDate: null,
                    saleBy: null,
                    owner: null
                }
            },
            classificationInformation: {
                typeClaim: { label: null, value: null },
                grade: { label: null, value: null },
                servicePerformed: null,
                categoryProblem: { label: null, value: null },
                repairTechnician: null,
                reportedDate: null,
                rapairDate: null
            },
            partInformation: {
                defectPart: {
                    affectedPart: { label: null, value: null },
                    affectedDefect: { label: null, value: null },
                    symptom: { label: null, value: null },
                    diagTechnical: null,
                    correctiveAction: null
                },
                relationPart: [{
                    id: null,
                    description: null,
                    quantity: null,
                    packingCost: null,
                    unitCost: null,
                    total: null,
                    part: null,
                    warrantyClaimsId: null
                }],
                otherJobInformation: [{
                    id: null,
                    description: null,
                    quantity: null,
                    details: null,
                    invoice: null,
                    cost: null,
                    total: null,
                    warrantyClaims: null
                }],
                costDetail: {
                    laborTime: null,
                    laborCost: null,
                    totalLaborTime: null,
                    sparePartCost: null,
                    landingCost: null,
                    totalSparePart: null,
                    totalSparePartAndLabor: null
                },
                evidence: [{
                    fileName: null,
                    fileViewUri: null,
                    fileType: null,
                    size: null
                }]
            }
        };
    }

    complete() {
        this.partComplete.next(this.warrantyInformation.personalInformation.clientInformation);
    }

    setWarrantyInformation(warrantyDetail?: WarrantyDetail, partsReplaced?: PartsReplaced[], otherExpenses?: OtherExpenses[], costDetail?: CostDetail, evidence?: Evidence[]) {
        if(warrantyDetail) {
            this.warrantyInformation.id = warrantyDetail.id;
            this.warrantyInformation.status = warrantyDetail.status;
            this.warrantyInformation.personalInformation = { 
                clientInformation: {
                    clientName: warrantyDetail.warrantyClient.clientName,
                    address: warrantyDetail.warrantyClient.addressStreet,
                    phone: warrantyDetail.warrantyClient.phone,
                    state: { label: warrantyDetail.warrantyClient.location.state.name, value: warrantyDetail.warrantyClient.location.state.id },
                    location: { label: warrantyDetail.warrantyClient.location.name, value: warrantyDetail.warrantyClient.location.id },
                    email: warrantyDetail.warrantyClient.email
                },
                mobileUnitInformation: {
                    vinNumber: warrantyDetail.vin.vin,
                    mileage: warrantyDetail.km,
                    model: { label: warrantyDetail.vin.model.model, value: warrantyDetail.vin.model.id },
                    engineSerie: warrantyDetail.vin.engineNumber,
                    saleDate: warrantyDetail.vin.salesDate,
                    saleBy: warrantyDetail.saleBy,
                    owner: (warrantyDetail.firstOwner) ? warrantyDetail.firstOwner.firstName : 'NO DISPONIBLE'
                }, 
                reportInformation: {
                    claimNumber: warrantyDetail.numberClaims,
                    serviceOrderNumber: warrantyDetail.noServOrder,
                    status: warrantyDetail.status,
                    reportCreationDate: warrantyDetail.createDate,
                    dealerName: warrantyDetail.dealer.name,
                    elaboratedBY: warrantyDetail.elaboratedBY
                }
            };
            
            if( warrantyDetail.typeClaims ) {
                this.warrantyInformation.classificationInformation = {
                    typeClaim: { label: warrantyDetail.typeClaims.claimsType, value: warrantyDetail.typeClaims.id },
                    grade: { label: warrantyDetail.gradeType.grade, value: warrantyDetail.gradeType.id },
                    servicePerformed: warrantyDetail.serviceModel.serviceNumber,
                    categoryProblem: { label: warrantyDetail.problemCategory.problemName, value: warrantyDetail.problemCategory.id }, 
                    repairTechnician: warrantyDetail.technicalEmployee,
                    reportedDate: warrantyDetail.createDate,
                    rapairDate: warrantyDetail.repairDate
                };
            }

            if( warrantyDetail.partsDefectsSymptom ) {
                this.warrantyInformation.partInformation =  {
                    defectPart: {
                        affectedPart: { label: warrantyDetail.partsDefectsSymptom.partMotorcycle.descriptionSpanish, value: warrantyDetail.partsDefectsSymptom.partMotorcycle.id },
                        affectedDefect: { label: warrantyDetail.partsDefectsSymptom.defect.descriptionSpa, value: warrantyDetail.partsDefectsSymptom.defect.id },
                        symptom: { label: warrantyDetail.partsDefectsSymptom.symptom.descriptionSpa, value: warrantyDetail.partsDefectsSymptom.symptom.id },
                        diagTechnical: warrantyDetail.technicalDiagnosis,
                        correctiveAction: warrantyDetail.correctiveAction
                    },
                    relationPart: partsReplaced,
                    otherJobInformation: otherExpenses,
                    costDetail: costDetail,
                    evidence: evidence
                };
            }
        }
    }
    
}