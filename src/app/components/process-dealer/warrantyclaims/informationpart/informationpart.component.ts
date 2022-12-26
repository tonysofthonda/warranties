import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';
import { PartsMotorcycleService } from 'src/app/services/parts-motorcycle.service';
import { MessageService, SelectItem } from 'primeng/api';
import { PartsDefectsSymptomsService } from 'src/app/services/parts-defects-symptoms.service';
import { Router } from '@angular/router';
import { StepService } from 'src/app/services/step.service';
import { PartsReplaced } from 'src/app/model/parts-replaced.model';
import { OtherExpenses } from 'src/app/model/warranty-claims.model';
import { EvidenceService } from 'src/app/services/evidence.service';
import { Evidence } from 'src/app/model/evidence.model';
import { WarrantyClaimsService } from 'src/app/services/warranty-claims.service';
import { SettingsService } from 'src/app/services/settings.service';

@Component({
    selector: 'app-informationpart',
    templateUrl: './informationpart.component.html'
})
export class InformationpartComponent implements OnInit {

    partsInformation: FormGroup;
    validations = [];
    chartData: any
    parts: SelectItem[] = [];
    defects: SelectItem[] = [];
    symptoms: SelectItem[] = [];

    //Params to child
    partsReplaced: PartsReplaced[] = [];
    otherExpenses: OtherExpenses[] = [];
    
    laborTime: number = 0;
    //El costo del labor se debe de cambiar por una consulta de la base
    laborCost: number = 0;
    totalLaborTime: number = 0;
    sparePartCost: number = 0;
    landingCost: number = 0;
    totalSparePart: number = 0;
    totalSparePartAndLabor: number = 0;
    evidenceResponse: Evidence[] = [];
    consultForm: boolean = false;
    blockedDocument: boolean = false;

    constructor(private router: Router, private formb: FormBuilder, private messages: AppValidationMessagesService, private partsMotorcycleService: PartsMotorcycleService,
        private partsDefectsSymptomsService: PartsDefectsSymptomsService, private messageService: MessageService, private stepService: StepService, 
        private evidenceService: EvidenceService, private warrantyClaimService: WarrantyClaimsService,
        private settingsService: SettingsService) { }

    ngOnInit(): void {

        setTimeout(() => {
            this.blockedDocument = true; 
        }, 1);

        if(!this.stepService.warrantyInformation.partInformation.evidence) {
            this.evidenceResponse = [];
        }

        this.getLaborCost();
        this.getValidations();
        if(this.stepService.warrantyInformation.status !== 'Draft') {
            this.consultForm = true;
        }
        if (this.stepService.warrantyInformation.partInformation.defectPart.affectedPart.value && this.stepService.warrantyInformation.partInformation.relationPart) {
            // setTimeout(() => { 
                this.getPartsMotorcycle();
            //  }, 1000);
            // setTimeout(() => { 
                this.getPartDefectSymptoms(this.stepService.warrantyInformation.partInformation.defectPart.affectedPart.value);
            // }, 1000);
            // setTimeout(() => { 
                this.getDefectSymptoms(this.stepService.warrantyInformation.partInformation.defectPart.affectedPart.value, this.stepService.warrantyInformation.partInformation.defectPart.affectedDefect.value);
            // }, 1000);

            if(this.stepService.warrantyInformation.partInformation.relationPart) {
                this.partsReplaced = this.stepService.warrantyInformation.partInformation.relationPart;
            }
            if(this.stepService.warrantyInformation.partInformation.relationPart) {
                this.otherExpenses = this.stepService.warrantyInformation.partInformation.otherJobInformation;
            }
            if(this.stepService.warrantyInformation.partInformation.evidence) {
                this.evidenceResponse = this.stepService.warrantyInformation.partInformation.evidence;
            }
            this.costDetail();
            
            // setTimeout(() => {  
                this.makePartForm(); 
            // }, 4000);
           
        } else {
            this.makePartForm();
            this.getPartsMotorcycle();
        }
    }

    getLaborCost() {
        this.settingsService.getSettings('labor_cost').subscribe(settingResponse => {
            this.laborCost = settingResponse[0].valueNumber;
        }, error => {
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "Error",
                detail: error.error,
            });
            this.blockedDocument = false;
        });
    }

    relationPartsEmitted(partsEmittedTemp: PartsReplaced[]) {
        this.partsReplaced = partsEmittedTemp;
        this.costDetail();
    }

    costDetail() {
        let totalLaborTimeTemp: number = 0;
        let sparePartCostTemp: number = 0;
        this.partsReplaced.forEach((part) => {
            sparePartCostTemp += (part.unitCost * part.quantity);
            totalLaborTimeTemp += part.packingCost;
        });
        this.sparePartCost = sparePartCostTemp;
        //El valor a multiplicar del 0.25 se cambiara a un servicio de consulta del settings
        this.landingCost = (this.sparePartCost * 0.25);
        this.totalSparePart = (this.sparePartCost + this.landingCost);
        this.laborTime = totalLaborTimeTemp;
        this.totalLaborTime = this.laborTime * this.laborCost;
        this.totalSparePartAndLabor = this.totalLaborTime + this.totalSparePart;
    }

    otherExpensesEmitted(expensesEmittedTemp: OtherExpenses[]) {
        this.otherExpenses = expensesEmittedTemp;
    }

    nextStep() {
        this.blockedDocument = true;
        if(this.consultForm) { 
            let costDetail = {
                laborTime: this.laborTime,
                laborCost: this.laborCost,
                totalLaborTime: this.totalLaborTime,
                sparePartCost: this.sparePartCost,
                landingCost: this.landingCost,
                totalSparePart: this.totalSparePart,
                totalSparePartAndLabor: this.totalSparePartAndLabor
                };
            this.stepService.warrantyInformation.partInformation.costDetail = costDetail;
            this.blockedDocument = false;
            this.router.navigate(['/warranty/confirmation']);
        } else {
            if(this.partsInformation.valid) {
                let partInformation = { defectPart: this.partsInformation.value, relationPart: this.partsReplaced, otherJobInformation: this.otherExpenses, costDetail: {
                    laborTime: this.laborTime,
                    laborCost: this.laborCost,
                    totalLaborTime: this.totalLaborTime,
                    sparePartCost: this.sparePartCost,
                    landingCost: this.landingCost,
                    totalSparePart: this.totalSparePart,
                    totalSparePartAndLabor: this.totalSparePartAndLabor
                    },
                    evidence: this.evidenceResponse 
                };
                this.stepService.warrantyInformation.partInformation = partInformation;
                
                this.warrantyClaimService.saveWarrantyClainInDraft(this.stepService.warrantyInformation).subscribe((response) => {
                    this.router.navigate(['/warranty/confirmation'])
                    this.blockedDocument = false;
                }, error => {
                    this.messageService.add({
                        key: "tst",
                        severity: "error",
                        summary: "Error",
                        detail: error.error,
                    });
                    this.blockedDocument = false;
                });
            } else {
                this.messageService.add({
                    key: "tst",
                    severity: "error",
                    summary: "Error",
                    detail: "Favor de completar todos los campos necesarios",
                });
                this.blockedDocument = false;
            }
        }
    }

    backStep() {
        this.router.navigate(['/warranty/classification']);
    }

    onClear() {
        // this.evidenceResponse = [];
    }

    myUploader(event, form) {
        
        event.files.forEach(file => {  
            // this.messageService.add({key: "tst", severity: 'info', summary: 'Nota', detail: `Las evidencia ${file.name} han sido cargadas`});
            // this.evidenceResponse.push(file);
            this.evidenceService.uploadEvidence(file).subscribe(
                (response) => {
                    if(response.fileType.includes('application/')) {
                        response.fileType = 'APP';
                    } else if(response.fileType.includes('image/')) {
                        response.fileType = 'IMAGE';
                    } else if(response.fileType.includes('video/')) {
                        response.fileType = 'VIDEO';
                    }
                    this.messageService.add({key: "tst", severity: 'info', summary: 'Nota', detail: `Las evidencia ${response.fileName} han sido cargadas`});
                    this.evidenceResponse.push(response);
                }, (error) => {
                    this.messageService.add({
                        key: "tst",
                        severity: "error",
                        summary: "Error",
                        detail: error,
                    });  
            });

            form.clear();
        });
    }

    getPartsMotorcycle() {
        this.partsMotorcycleService.getPartsMotorcycleByStatus(true).subscribe(value => {
            if(value) {
                this.parts = value.map(r => (
                    { label: r.partNameSpanish, value: r.id }
                ));
            } else {
                this.parts = [];
                this.messageService.add({
                    key: "tst",
                    severity: "warn",
                    summary: "Nota",
                    detail: "No se encontraron partes afectadas",
                });
            }
        });
    }

    onChangePart() {
        let partId = this.partsInformation.get('affectedPart').value.value
        this.getPartDefectSymptoms(partId);
    }

    getPartDefectSymptoms(partId: number) {
        this.partsDefectsSymptomsService.getDefectsBypartId(partId).subscribe(value => {
            if(value){
                this.defects = value.map(r => (
                    { label: r.descriptionSpa, value: r.id }
                ));
            } else {
                this.defects = [];
                this.messageService.add({
                    key: "tst",
                    severity: "warn",
                    summary: "Nota",
                    detail: "No se encontraron defectos con la parte seleccionada",
                });
            }
        });
    }

    onChangeDefect() {
        let defectId = this.partsInformation.get('affectedDefect').value.value
        let partId = this.partsInformation.get('affectedPart').value.value
        this.getDefectSymptoms(partId, defectId);
    }

    getDefectSymptoms(partId: number, defectId: number) {
        this.partsDefectsSymptomsService.getSymtomByPartIdAndDefectId(partId, defectId).subscribe(value => {
            if(value) {
                this.symptoms = value.map(r => (
                    { label: r.descriptionSpa, value: r.id }
                ));
            } else {
                this.symptoms = [];
                this.messageService.add({
                    key: "tst",
                    severity: "warn",
                    summary: "Nota",
                    detail: "No se encontraron síntomas con la parte y el defecto seleccionado",
                });
            }
        });
    }

    makePartForm() {
        this.partsInformation = this.formb.group({
            affectedPart: [[''],  [Validators.required]],
            affectedDefect: [[''], [Validators.required]],
            symptom: [[''], [Validators.required]],
            diagTechnical: [[''], [Validators.required, Validators.maxLength(200)]],
            correctiveAction: [[''], [Validators.required, Validators.maxLength(200)]],
        });
        
        setTimeout(() => {

            if(this.stepService.warrantyInformation.status !== 'Draft') { 
                this.partsInformation.controls['affectedPart'].disable();
                this.partsInformation.controls['affectedDefect'].disable();
                this.partsInformation.controls['symptom'].disable();
                this.partsInformation.controls['diagTechnical'].disable();
                this.partsInformation.controls['correctiveAction'].disable();
            }

            let parts = this.parts.find(x => x.value === this.stepService.warrantyInformation.partInformation.defectPart.affectedPart.value);
            this.partsInformation.get('affectedPart').setValue(parts);
            let defect = this.defects.find(x => x.value === this.stepService.warrantyInformation.partInformation.defectPart.affectedDefect.value);
            this.partsInformation.get('affectedDefect').setValue(defect);
            let sympto = this.symptoms.find(x => x.value === this.stepService.warrantyInformation.partInformation.defectPart.symptom.value);
            this.partsInformation.get('symptom').setValue(sympto);
            this.partsInformation.get('diagTechnical').setValue(this.stepService.warrantyInformation.partInformation.defectPart.diagTechnical);
            this.partsInformation.get('correctiveAction').setValue( this.stepService.warrantyInformation.partInformation.defectPart.correctiveAction);
            this.blockedDocument = false; 
        }, 500);
}

    getValidations() {
        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('affectedPart'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('symptom'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('descriptionSymptom'));

        this.messages.messagesRequired = 'true';
        this.messages._messagesMaxLenght = '200';
        this.validations.push(this.messages.getValidationMessagesWithName('diagTechnical'));

        this.messages.messagesRequired = 'true';
        this.messages._messagesMaxLenght = '200';
        this.validations.push(this.messages.getValidationMessagesWithName('correctiveAction'));
    }

}
