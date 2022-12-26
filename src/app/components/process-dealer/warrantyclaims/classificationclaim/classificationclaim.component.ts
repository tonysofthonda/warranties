import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { Component, OnInit } from '@angular/core';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';
import { GradeTypeService } from 'src/app/services/grade-type.service';
import { SelectItem, MessageService } from 'primeng/api';
import { TypeClaimsService } from 'src/app/services/type-claims.service';
import { ProblemCategoryService } from 'src/app/services/problem-category.service';
import { StepService } from 'src/app/services/step.service';
import { ServiceModelService } from 'src/app/services/service-model.service';
import { RefreshFormService } from 'src/app/services/RefreshForm.service';
import { WarrantyClaimsService } from 'src/app/services/warranty-claims.service';

@Component({
    selector: 'app-classificationclaim',
    templateUrl: './classificationclaim.component.html',
    providers:[GradeTypeService, TypeClaimsService, ProblemCategoryService]
})
export class ClassificationclaimComponent implements OnInit {

    datosForm: FormGroup;
    validations = [];
    gradeType: SelectItem[] = [];
    typeClaims: SelectItem[] = [];
    problemType: SelectItem[] = [];
    maxDateToRepair: Date;
    maxDateReported: Date;
    servicesModel: SelectItem[] = [];
    consultForm: boolean = false;
    blockedDocument: boolean = false;
    regexNumeric: string = '^(0|[1-9][0-9]*)$';
    
    constructor(private router: Router, private formb: FormBuilder, private messages: AppValidationMessagesService, 
        private gradeTypeService: GradeTypeService, private typeClaimsService: TypeClaimsService,
        private problemCategoryService: ProblemCategoryService, private messageService: MessageService,
        private stepService: StepService, private serviceModel: ServiceModelService, 
        private refreshService: RefreshFormService, private warrantyClaimService: WarrantyClaimsService ) { }

    ngOnInit(): void {

        setTimeout(() => {
            this.blockedDocument = true; 
        }, 1);

        this.refreshService.data$.subscribe(res => {
            if(res === 'RefreshForm') {
                this.refreshService.refreshForms('NotRefresh');
            }
        });

        if(this.stepService.warrantyInformation.status !== 'Draft') {
            this.consultForm = true;
        } 

        this.formData();
        this.getValidations();
        /*this.getAllGradeType();
        this.getAllTypeClaims();
        this.getAllProblemCategory();*/
        
        this.maxDateToRepair = new Date();
        
        this.maxDateReported = new Date();
        //this.getServiceByModel(this.stepService.warrantyInformation.personalInformation.mobileUnitInformation.model.label);
        this.getAllGradeType();
        this.getAllTypeClaims();
        this.getAllProblemCategory();
        this.makeServiceForm();
    }

    // getServiceByModel(model: string) {     
    //     this.serviceModel.getServiceModelByModel(model).subscribe((serviceModelResponse) => {
    //         if(serviceModelResponse) {
    //             this.servicesModel = serviceModelResponse.map(serviceModel =>(
    //                 { label: serviceModel.serviceNumber + ' o ' + serviceModel.km + ' km', value: serviceModel.id }
    //             ));   
    //         } else {
    //             this.servicesModel = [];
    //             this.messageService.add({
    //                 key: "tst",
    //                 severity: "warn",
    //                 summary: "Nota",
    //                 detail: "No se encontraron servicios para este modelo",
    //             });
    //         }
    //     });
    // }

    getAllProblemCategory() {
        this.problemCategoryService.getProblemCategory(true).subscribe(response => { 
            if(response) {
                this.problemType = response.map(r =>(
                    {label: r.problemName, value: r.id}
                ));  
            } else {
                this.problemType = [];
                this.messageService.add({
                    key: "tst",
                    severity: "warn",
                    summary: "Nota",
                    detail: "No se encontraron categorías del problema",
                });
            }
              
        }); 
    }

    getAllTypeClaims(){
        this.typeClaimsService.getTypeClaims(true).subscribe(response =>{   
            if(response) {
                this.typeClaims = response.map(r => (
                    { label: r.claimsType, value: r.id }
                ));
            } else {
                this.typeClaims = [];
                this.messageService.add({
                    key: "tst",
                    severity: "warn",
                    summary: "Nota",
                    detail: "No se encontraron los tipos de reclamo",
                });
            }         
        });        
    }

    getAllGradeType(){
        this.gradeTypeService.getGradeType(true).subscribe((response) => {   
            if(response) {
                this.gradeType = response.map(r => (
                    { label: r.grade, value: r.id }
                ));
            } else {
                this.gradeType = [];
                this.messageService.add({
                    key: "tst",
                    severity: "warn",
                    summary: "Nota",
                    detail: "No se encontraron los grados de reclamo",
                });
            }      
        }, (error) => {
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "Error",
                detail: error.error.message,
            });
        });
    }

    nextStep() {
        this.blockedDocument = true;
        if(this.consultForm) { 
            this.blockedDocument = false;
            this.router.navigate(['/warranty/informationPart']);
        } else {
            this.datosForm.markAllAsTouched();
            if (this.datosForm.valid){
                this.stepService.warrantyInformation.classificationInformation = this.datosForm.value;
                this.warrantyClaimService.saveWarrantyClainInDraft(this.stepService.warrantyInformation).subscribe((response) => {
                    this.router.navigate(['/warranty/informationPart']);
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
                this.blockedDocument = false;
                this.messageService.add({
                    key: "tst",
                    severity: "error",
                    summary: "Error",
                    detail: "Favor de completar todos los campos necesarios",
                });
            }
        }
    }

    backStep() {
           this.router.navigate(['/warranty/personalInf']);
    }

    formData(){
            this.datosForm = this.formb.group({
                typeClaim: [[''],  [Validators.required]],
                grade: ['', [Validators.required]],
                servicePerformed: ['', [Validators.required, Validators.pattern(this.regexNumeric)]],
                categoryProblem: ['', [Validators.required]],
                repairTechnician: ['', [Validators.required]],
                reportedDate: ['', [Validators.required]],
                rapairDate: ['', [Validators.required]],
            });
    }

    makeServiceForm(){
        setTimeout(() => {
            if(this.stepService.warrantyInformation.status !== 'Draft') {
                this.datosForm.controls['typeClaim'].disable();
                this.datosForm.controls['grade'].disable();
                this.datosForm.controls['servicePerformed'].disable();
                this.datosForm.controls['categoryProblem'].disable();
                this.datosForm.controls['repairTechnician'].disable();
                this.datosForm.controls['reportedDate'].disable();
                this.datosForm.controls['rapairDate'].disable();
            }
            let isDraft: Boolean = (this.stepService.warrantyInformation.status) ? ((this.stepService.warrantyInformation.status === 'Draft') ? false : true) : false ; 
            let reportedDate = (this.stepService.warrantyInformation.classificationInformation.reportedDate) ? new Date(this.stepService.warrantyInformation.classificationInformation.reportedDate) : new Date();
            let repairDate = (this.stepService.warrantyInformation.classificationInformation.rapairDate) ? new Date(this.stepService.warrantyInformation.classificationInformation.rapairDate) : new Date(); 
            let type = this.typeClaims.find(x => x.value === this.stepService.warrantyInformation.classificationInformation.typeClaim.label);
            if(type !== null) {
                this.datosForm.get('typeClaim').setValue(this.stepService.warrantyInformation.classificationInformation.typeClaim);
            }
            let grade = this.gradeType.find(x => x.label === this.stepService.warrantyInformation.classificationInformation.grade.label);
            
            this.datosForm.get('grade').setValue(grade);
            let service = this.stepService.warrantyInformation.classificationInformation.servicePerformed;
            this.datosForm.get('servicePerformed').setValue(service);
            let category = this.problemType.find(x => x.label === this.stepService.warrantyInformation.classificationInformation.categoryProblem.label);
            this.datosForm.get('categoryProblem').setValue(category);
            this.datosForm.get('repairTechnician').setValue(this.stepService.warrantyInformation.classificationInformation.repairTechnician);
            this.datosForm.get('reportedDate').setValue(reportedDate);
            this.datosForm.get('rapairDate').setValue(repairDate);
            this.blockedDocument = false;
        }, 700);
    }

    getValidations(){
        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('typeClaim'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('grade'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesPattern = 'Ingresar solo caracteres numericos';
        this.validations.push(this.messages.getValidationMessagesWithName('servicePerformed'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('categoryProblem'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('repairTechnician'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('reportedDate'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('rapairDate'));
    }

}
