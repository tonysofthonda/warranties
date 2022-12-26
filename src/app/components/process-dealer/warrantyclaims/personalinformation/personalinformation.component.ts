import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router, ActivatedRoute } from '@angular/router';
import { Component, Input, OnInit } from '@angular/core';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';
import { FormatDate } from 'src/app/utils/format-date';
import { WarrantyClaimsService } from 'src/app/services/warranty-claims.service';
import { WarrantyConsecutive } from 'src/app/model/warranty-consecutive.model';
import { WarrantyDetail } from 'src/app/model/warranty-detail.model';
import { MessageService, SelectItem } from 'primeng/api';
import { StateService } from 'src/app/services/state.service';
import { LocationService } from 'src/app/services/localtion.service';
import { ModelService } from 'src/app/services/model.service';
import { VinService } from 'src/app/services/vin.service';
import { Models } from 'src/app/model/models.model';
import { StepService } from 'src/app/services/step.service';
import { PartsReplacedService } from 'src/app/services/parts-replaced.service';
import { OtherExpensesService } from 'src/app/services/other-expenses.service';
import { RefreshFormService } from 'src/app/services/RefreshForm.service';
import { EvidenceService } from 'src/app/services/evidence.service';

@Component({
    selector: 'app-personalinformation',
    templateUrl: './personalinformation.component.html',
    providers:[WarrantyClaimsService, MessageService]
})
export class PersonalinformationComponent implements OnInit {

    reportInformation: FormGroup;
    mobileUnitInformation: FormGroup;
    clientInformation: FormGroup;
    validations = [];
    currentDate: String;
    status: String;
    dealer: number;
    warrantyConsecutive: WarrantyConsecutive;
    states: SelectItem[] = [];
    locationsDrown: SelectItem[] = [];
    modelDrown: SelectItem[] = [];
    selectState: any;
    //En espera a la refactorizacion de la BD para consultar los estatus
    regexAlfanumeric: string = '^[a-zA-Z0-9]+.*$';
    regexNumeric: string = '^(0|[1-9][0-9]*)$';
    regexOrderNumber: string = '^[a-zA-Z0-9\s-]*$';
    regexPhone: string = '^[0-9()+].*$';
    regexEmail: string = '^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$';
    regexNotSpecialChar: string = '^[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9]+[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9\\s]*$';;
    regexNotSpecialCharAddress: string = '^[a-zA-Z0-9-.]+[a-zA-Z0-9-#.\\s]*$';
    regexChar: string = '^[a-zA-Z]+[a-zA-Z\\s]*$';
    btnNext: boolean = true;
    serviceNumberModel: string;
    isServiceNumber: boolean = false;
    vinModel: string;
    foundVin: string;
    consultForm: boolean = false;
    blockedDocument: boolean = false;

    constructor(private router: Router, private activatedRoute: ActivatedRoute, private formb: FormBuilder,
        private messages: AppValidationMessagesService, private dateUtil: FormatDate, private warrantyClaimService: WarrantyClaimsService,
        private stateService: StateService, private locationService: LocationService, private messageService: MessageService,
        private modelService: ModelService, private vinService: VinService, private stepService: StepService,
        private partsReplacedService: PartsReplacedService, private otherExpensesService: OtherExpensesService, 
        private refreshService: RefreshFormService, private evidenceService: EvidenceService) { }

    ngOnInit(): void {
        setTimeout(() => {
            this.blockedDocument = true; 
        }, 1);

        this.formData();
        this.getValidations();

        this.activatedRoute.queryParams.subscribe((params) => {
            //Query param to refresh form consult from sidebar
            if(params){
                if(params.new){
                   this.newWarrantyClaimLink(); 
                }
            }
        });

        this.activatedRoute.params.subscribe((params) => {
            if (!params.id) {
                console.log('Sin parametros')
                //Sin parametros
                if(this.stepService.warrantyInformation.personalInformation.reportInformation.claimNumber) {
                    if(this.stepService.warrantyInformation.status !== 'Draft') {
                        this.consultForm = true;
                    } 
                    this.makeFormGroups();
                    this.btnNext = false;
                    this.getTownShips(
                        this.stepService.warrantyInformation.personalInformation.clientInformation.state.value
                    );
                    this.mobileUnitInformation.controls['mileage'].enable();
                    this.clientInformation.controls['address'].enable();
                    this.clientInformation.controls['state'].enable();
                    this.clientInformation.controls['location'].enable();
                    this.clientInformation.controls['phone'].enable();
                } else {
                    this.consultForm = false;
                    this.stepService.warrantyInformation.status = 'Draft';
                    if(localStorage.getItem('DealerNumber')) {
                        this.dealer = parseInt(localStorage.getItem('DealerNumber'), 10);
                    } else {
                        this.dealer = 77777;
                    }
                     //TODO este valor debe de tomarse de la sesion. 
                    this.generateConsecutive();
                }
                this.getAllStates(); 
                this.getModel();
                this.reportInformation.markAsDirty();
                this.clientInformation.markAsDirty();
                  
            } else {
                //Con parametros de redireccionamiento
                this.stepService.warrantyInformation.id = params.id;
                this.warrantyClaimService.getWarrantyClaimDetail(params.id).subscribe((warrantyDetailResponse: WarrantyDetail) =>{
                    if(warrantyDetailResponse.status !== 'Draft') {
                        this.consultForm = true;
                    } 
                    this.getTownShips(
                        warrantyDetailResponse.warrantyClient.location.state.id
                    );
                    this.evidenceService.getEvidenceByWarrantyId(params.id).subscribe(evidenceResponse => {
                        evidenceResponse.forEach(( evidence) => {
                            if(evidence.fileName.includes('http://qhdmglserwfs01:8080/warrantiesmg/viewFile/')) {
                                evidence.fileName = evidence.fileName.replace('http://qhdmglserwfs01:8080/warrantiesmg/viewFile/', '');
                            } else if(evidence.fileName.includes('http://localhost:8081/warrantiesmg/viewFile/')) {
                                evidence.fileName = evidence.fileName.replace('http://localhost:8081/warrantiesmg/viewFile/', '');
                            } else if(evidence.fileName.includes('https://mss.hondaweb.com/warrantiesmg/viewFile/')) {
                                evidence.fileName = evidence.fileName.replace('https://mss.hondaweb.com/warrantiesmg/viewFile/', '');
                            } else if(evidence.fileName.includes('http://mss.hondaweb.com/warrantiesmg/viewFile/')) {
                                evidence.fileName = evidence.fileName.replace('http://mss.hondaweb.com/warrantiesmg/viewFile/', '');
                            } else if(evidence.fileName.includes('http://phdmglserwfs01.hdm.am.honda.com:8080/warrantiesmg/viewFile/')) {
                                evidence.fileName = evidence.fileName.replace('http://phdmglserwfs01.hdm.am.honda.com:8080/warrantiesmg/viewFile/', '');
                            }
                        });
                        this.partsReplacedService.getPartsReplacedByWarrantyClaims(params.id).subscribe(partReplacedResponse => {
                            this.otherExpensesService.getOtherExpenses(params.id).subscribe(otherExpensesResponse => {
                                this.stepService.setWarrantyInformation(warrantyDetailResponse, partReplacedResponse, otherExpensesResponse, null, evidenceResponse);
                                this.formData();
                                this.makeFormGroups();
                            });    
                        });
                    });
                    this.getAllStates();   
                    this.getModel();
                    this.btnNext = false;
                    
                    //setTimeout(() => { this.mobileUnitInformation.markAllAsTouched(); }, 4000);
                },
                (error) => {
                    this.messageService.add({
                        key: "tst",
                        severity: "error",
                        summary: "Error",
                        detail: error.error.message,
                    });
                });
            }
           /* this.mobileUnitInformation.markAllAsTouched();
            this.mobileUnitInformation.markAsDirty();
            this.mobileUnitInformation.markAsPending();*/
        });
    }

    newWarrantyClaimLink() { 
        this.stepService.resetWarrantyInformation();
        //this.generateConsecutive();  
        // this.messageService.add({ key: "tst", severity: 'success', summary: 'Nota', detail: 'Nuevo formulario.' });
        if(!this.router.url.includes('/warranty/personalInf'))
            this.router.navigate([this.router.url]);
    }

    resetForms() {
        this.blockedDocument = true; 
        this.consultForm = false;
        this.formData();
        this.isServiceNumber = false;
        this.foundVin = '';
        this.stepService.warrantyInformation.status = 'Draft';
        this.dealer = parseInt(localStorage.getItem('DealerNumber'), 10);
        setTimeout(() => {
            this.generateConsecutive();  
            this.mobileUnitInformation.controls['mileage'].disable();
            this.clientInformation.controls['address'].disable();
            this.clientInformation.controls['state'].disable();
            this.clientInformation.controls['location'].disable();
            this.clientInformation.controls['phone'].disable();
        }, 500);
        
    }

    serviceOrderNumberChange() {
        if(this.serviceNumberModel) {
            if(this.serviceNumberModel.length > 0 ) {
                this.isServiceNumber = true 
            }
        } else {
            this.isServiceNumber = false;
        }
    }

    vinChange(){
        if(this.foundVin) {
            if ( this.vinModel !== this.foundVin ) {
                setTimeout(() => {
                    this.mobileUnitInformation.get('mileage').setValue('');
                    this.mobileUnitInformation.get('saleBy').setValue('');
                    this.mobileUnitInformation.get('model').setValue('');
                    this.mobileUnitInformation.get('engineSerie').setValue('');
                    this.mobileUnitInformation.get('saleDate').setValue('');
                    this.mobileUnitInformation.get('owner').setValue('');

                    this.clientInformation.get('clientName').setValue('');
                    this.clientInformation.get('address').setValue('');
                    this.clientInformation.get('state').setValue('');
                    this.clientInformation.get('location').setValue('');
                    this.clientInformation.get('phone').setValue('');
                    this.clientInformation.get('email').setValue('');

                    this.mobileUnitInformation.controls['mileage'].disable();
                    this.clientInformation.controls['address'].disable();
                    this.clientInformation.controls['state'].disable();
                    this.clientInformation.controls['location'].disable();
                    this.clientInformation.controls['phone'].disable();

                    this.foundVin = '';
                }, 500);
            }
        }
    }
   
    getAllStates(){
        this.stateService.getAllStates().subscribe(response =>{
            if(response) {
                this.states = response.map(r =>(
                    {label: r.name, value: r.id}
                ));    
            } else {
                this.states = [];
                this.messageService.add({
                    key: "tst",
                    severity: "warn",
                    summary: "Nota",
                    detail: "No se encontraron los estados",
                });
            }
        });
    }

    onChangeState() {
        const state = this.clientInformation.get("state").value;
        this.clientInformation.get('location').setValue('');
        this.getTownShips(state.value);
    }
      
    getTownShips(idState: number) {
        if(idState) {
            this.locationService.getAllLocationsByState(idState).subscribe((resp: any) => {
                if(resp) {
                    if(resp.length > 0) {
                        this.locationsDrown = resp.map(r => (
                            { label: r.name, value: r.id }
                        ));
                    }
                } else {
                    this.locationsDrown = [];
                    this.messageService.add({
                        key: "tst",
                        severity: "warn",
                        summary: "Nota",
                        detail: "No se encontraron los municipios del estado seleccionado",
                    });
                }
            });
        }
    }

    getModel() {
        this.modelService.getAllModels().subscribe((modelsResponse: Models[]) => {
            this.modelDrown = modelsResponse.map((model) => ({
                label: model.model,
                value: model.id,
            }));
        });
    }

    generateConsecutive() {
        this.warrantyClaimService.getWarrantyClaimConsecutive(this.dealer).subscribe(response => {            
            console.log(response.warrantyConsecutive)
            this.warrantyConsecutive = response;         
            this.reportInformation.get('status').setValue('Draft'); 
            this.reportInformation.get('claimNumber').setValue(response.warrantyConsecutive);
            this.reportInformation.get('dealerName').setValue(response.dealer);
            if(localStorage.getItem('FullName')) {
                this.reportInformation.get('elaboratedBY').setValue(localStorage.getItem('FullName'));  //TODO este valor debe de tomarse de la sesion.  
            }
            this.stepService.warrantyInformation.personalInformation.reportInformation.claimNumber = this.reportInformation.get('claimNumber').value; 
            this.stepService.warrantyInformation.personalInformation.reportInformation.dealerName = this.reportInformation.get('dealerName').value;
            this.stepService.warrantyInformation.personalInformation.reportInformation.elaboratedBY = this.reportInformation.get('elaboratedBY').value;
            this.stepService.warrantyInformation.personalInformation.reportInformation.reportCreationDate = this.reportInformation.get('reportCreationDate').value;
            this.stepService.warrantyInformation.personalInformation.reportInformation.status = this.reportInformation.get('status').value;
            this.stepService.warrantyInformation.id = response.id;
            this.warrantyClaimService.saveWarrantyClainInDraft(this.stepService.warrantyInformation).subscribe((response) => {
                this.blockedDocument = false; 
            });
        }, error => {
            this.blockedDocument = false; 
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "Error",
                detail: error.error.message,
            });
        });
    }

    searchVin() {
        let vinNumber = this.mobileUnitInformation.get("vinNumber").value;
        if (vinNumber) {
            this.foundVin = vinNumber;
            this.vinService.getVinMicroserviceSales(vinNumber).subscribe(data => {
                if(data.status != 1) {
                    this.mobileUnitInformation.controls['mileage'].disable();
                    this.clientInformation.controls['address'].disable();
                    this.clientInformation.controls['state'].disable();
                    this.clientInformation.controls['location'].disable();
                    this.clientInformation.controls['phone'].disable();

                    this.messageService.add({
                        key: "tst",
                        severity: "warn",
                        summary: vinNumber,
                        detail: "El VIN no existe",
                    });
                    this.btnNext = true;
                } else {
                    // qa y prod 
                    if(data.data.nomotor && data.data.model) {
                    
                    // local
                    // if(data.data.motor && data.data.model) {
                        let model = this.modelDrown.find(x => x.label === data.data.model);
                        if (model) {
                            this.mobileUnitInformation.get('model').setValue(model);
                        
                        
                            this.mobileUnitInformation.controls['mileage'].enable();
                            this.clientInformation.controls['address'].enable();
                            this.clientInformation.controls['state'].enable();
                            this.clientInformation.controls['location'].enable();
                            this.clientInformation.controls['phone'].enable();
                            
                            // qa y prod 
                            this.mobileUnitInformation.get('engineSerie').setValue(data.data.nomotor);
                            // local
                            // this.mobileUnitInformation.get('engineSerie').setValue(data.data.motor);

                            // qa y prod 
                            if(data.data.date_invoice) {
                                this.mobileUnitInformation.get('saleDate').setValue(new Date(data.data.date_invoice));
                            } else {
                                this.mobileUnitInformation.get('saleDate').setValue(new Date());
                            }

                            // local
                            // if(data.data.invoice_date) {
                            //     this.mobileUnitInformation.get('saleDate').setValue(new Date(data.data.invoice_date));
                            // } else {
                            //     this.mobileUnitInformation.get('saleDate').setValue(new Date());
                            // }

                            this.mobileUnitInformation.get('saleBy').setValue(data.data.name_dealer);
                            
                            
                            this.btnNext = false;
                            this.vinService.getDataMicroserviceNetcommerce(vinNumber).subscribe(dataNetCommerce => {
                                if(dataNetCommerce.status == 1) {
                                    if(dataNetCommerce.data.data_custumer.name) {
                                        this.mobileUnitInformation.get('owner').setValue(dataNetCommerce.data.data_custumer.name);
                                        this.clientInformation.get('clientName').setValue(dataNetCommerce.data.data_custumer.name);
                                    } else {
                                        this.mobileUnitInformation.get('owner').setValue('NO DISPONIBLE');
                                        this.clientInformation.controls['clientName'].enable();
                                        this.clientInformation.controls['email'].enable();
                                    }
                                    if(dataNetCommerce.data.data_custumer.email) {
                                        if(dataNetCommerce.data.data_custumer.email === 'El campo value esta vacio') {
                                            this.clientInformation.get('email').setValue('NO DISPONIBLE');
                                        } else {
                                            this.clientInformation.get('email').setValue(dataNetCommerce.data.data_custumer.email);
                                        }
                                    } else {
                                        this.clientInformation.controls['email'].enable();   
                                    }
                                } else {
                                    this.mobileUnitInformation.get('owner').setValue('NO DISPONIBLE');
                                    this.clientInformation.controls['clientName'].enable();
                                    this.clientInformation.controls['email'].enable();
                                }
                            });
                            this.messageService.add({
                                key: "tst",
                                severity: "success",
                                summary: vinNumber,
                                detail: "VIN encontrado",
                            });
                        } else {
                            this.messageService.add({
                                key: "tst",
                                severity: "warn",
                                summary: "Alerta",
                                detail: `El modelo (${data.data.model}) asignado al VIN (${vinNumber}) no se encuentradado de alta.`,
                            }); 
                        }
                    } else {
                        this.messageService.add({
                            key: "tst",
                            severity: "warn",
                            summary: "Alerta",
                            detail: `No esta completa la información correspondiente al VIN seleccionado (${vinNumber})`,
                        }); 
                    }
                }
            });
        } else {
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "Error",
                detail: "Favor de ingresar un VIN",
            });
        }
    }
 
    nextStep() {
        this.blockedDocument = true;
        if(this.consultForm) {
            this.router.navigate(['/warranty/classification']);
            this.blockedDocument = false;
        } else {
            if (this.clientInformation.valid && this.mobileUnitInformation.valid){
                let reportInformationTemp = {
                    claimNumber: this.reportInformation.get('claimNumber').value,
                    serviceOrderNumber: this.reportInformation.get('serviceOrderNumber').value,
                    status: this.reportInformation.get('status').value,
                    reportCreationDate: this.reportInformation.get('reportCreationDate').value,
                    dealerName: this.reportInformation.get('dealerName').value,
                    elaboratedBY: this.reportInformation.get('elaboratedBY').value
                }
                this.stepService.warrantyInformation.personalInformation = { clientInformation: this.clientInformation.getRawValue(), reportInformation: reportInformationTemp, mobileUnitInformation: this.mobileUnitInformation.getRawValue() };
                
                this.warrantyClaimService.saveWarrantyClainInDraft(this.stepService.warrantyInformation).subscribe((response) => {
                    this.router.navigate(['/warranty/classification']);
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

    formData() {
        this.clientInformation = this.formb.group({
            clientName: [{value: '', disabled: true}, [Validators.required, Validators.maxLength(200), Validators.pattern(this.regexChar)]],
            address: [{value: '', disabled: true}, [Validators.required, Validators.maxLength(200), Validators.pattern(this.regexNotSpecialCharAddress)]],
            phone: [{value: '', disabled: true}, [Validators.required, Validators.minLength(10), Validators.maxLength(10), Validators.pattern(this.regexNumeric)]],
            state: [{value: '', disabled: true}, [Validators.required]],
            location: [{value: '', disabled: true}, [Validators.required]],
            email: [{value: '', disabled: true}, [Validators.maxLength(200), Validators.pattern(this.regexEmail)]],
        });

        this.reportInformation = this.formb.group({
            claimNumber: [
                {value: '', disabled: true }, 
                [Validators.required, Validators.minLength(6), Validators.maxLength(6)]
            ],
            serviceOrderNumber: [
                { value: '', disabled: false }, 
                [Validators.required, Validators.maxLength(10), Validators.pattern(this.regexOrderNumber)]
            ],
            status: [ 
                {   value: { label: 'Draft', value: 'Draft' }, 
                    disabled: true }, 
                [Validators.required]
            ],
            reportCreationDate: [{ value: new Date(), disabled: true },  [Validators.required]],
            dealerName: [{ value: '', disabled: true }, [Validators.required]],
            elaboratedBY: [{ value: '', disabled: true }, [Validators.required]],
        });

        this.mobileUnitInformation = this.formb.group({
            vinNumber: ['', [Validators.required, Validators.maxLength(20), Validators.pattern(this.regexAlfanumeric)]],
            mileage: [{value: '', disabled: true}, [Validators.required, Validators.maxLength(6), Validators.pattern(this.regexNumeric)]],
            model: [{value: '', disabled: true}, [Validators.required]],
            engineSerie: [{value: '', disabled: true}, [Validators.required, Validators.maxLength(20), Validators.pattern(this.regexAlfanumeric)]],
            saleDate: [{value: '', disabled: true}, [Validators.required]],
            saleBy: [{value: '', disabled: true}, [Validators.required, Validators.maxLength(100), Validators.pattern(this.regexChar)]],
            owner: [{value: '', disabled: true}, [Validators.required, Validators.maxLength(100), Validators.pattern(this.regexChar)]],
        });
    }

    getValidations() {
        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '20';
        this.messages.messagesPattern = 'Ingresar solo caracteres alfanumericos';
        this.validations.push(this.messages.getValidationMessagesWithName('vinNumber'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '20';
        this.messages.messagesPattern = 'Ingresar solo caracteres alfanumericos'
        this.validations.push(this.messages.getValidationMessagesWithName('engineSerie'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '100';
        this.messages.messagesPattern = 'Ingresar solo caracteres'
        this.validations.push(this.messages.getValidationMessagesWithName('saleBy'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '100';
        this.messages.messagesPattern = 'Ingresar solo caracteres'
        this.validations.push(this.messages.getValidationMessagesWithName('owner'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '10';
        this.messages.messagesPattern = 'Ingresar caracteres alfanumericos';
        this.validations.push(this.messages.getValidationMessagesWithName('serviceOrderNumber'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '6';
        this.messages.messagesPattern = 'Ingresar solo digitos'
        this.validations.push(this.messages.getValidationMessagesWithName('mileage'));
        
        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('status'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('dealerName'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('elaboratedBY'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('numVin'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '200';
        this.messages.messagesPattern = 'Ingresar solo caracteres alfanumericos'
        this.validations.push(this.messages.getValidationMessagesWithName('address'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '200';
        this.messages._messagesPattern = 'Ingresar solo caracteres'
        this.validations.push(this.messages.getValidationMessagesWithName('clientName'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '10';
        this.messages.messagesPattern = 'Ingresar solo digitos';
        this.validations.push(this.messages.getValidationMessagesWithName('phone'));

        this.messages.messagesRequired = 'false';
        this.messages.messagesMaxLenght = '13';
        this.messages.messagesPattern = 'Correo no valido'
        this.validations.push(this.messages.getValidationMessagesWithName('email'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('state'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('location'));

    }

    makeFormGroups() {
        setTimeout(() => {

            if(this.stepService.warrantyInformation.status === 'Draft') {
                this.mobileUnitInformation.controls['mileage'].enable();
                this.clientInformation.controls['address'].enable();
                this.clientInformation.controls['state'].enable();
                this.clientInformation.controls['location'].enable();
                this.clientInformation.controls['phone'].enable();
            } else {
                this.mobileUnitInformation.controls['mileage'].disable();
                this.clientInformation.controls['address'].disable();
                this.clientInformation.controls['state'].disable();
                this.clientInformation.controls['location'].disable();
                this.clientInformation.controls['phone'].disable();
            }

            this.clientInformation.get('clientName').setValue(this.stepService.warrantyInformation.personalInformation.clientInformation.clientName);
            this.clientInformation.get('address').setValue(this.stepService.warrantyInformation.personalInformation.clientInformation.address);
            this.clientInformation.get('phone').setValue(this.stepService.warrantyInformation.personalInformation.clientInformation.phone);
            this.clientInformation.get('state').setValue(this.stepService.warrantyInformation.personalInformation.clientInformation.state);
            this.clientInformation.get('location').setValue(this.stepService.warrantyInformation.personalInformation.clientInformation.location);
            this.clientInformation.get('email').setValue(this.stepService.warrantyInformation.personalInformation.clientInformation.email);

            this.reportInformation.get('claimNumber').setValue(this.stepService.warrantyInformation.personalInformation.reportInformation.claimNumber);
            this.reportInformation.get('serviceOrderNumber').setValue(this.stepService.warrantyInformation.personalInformation.reportInformation.serviceOrderNumber);
            this.reportInformation.get('status').setValue(this.stepService.warrantyInformation.personalInformation.reportInformation.status);
            this.reportInformation.get('reportCreationDate').setValue(new Date(this.stepService.warrantyInformation.personalInformation.reportInformation.reportCreationDate));
            this.reportInformation.get('dealerName').setValue(this.stepService.warrantyInformation.personalInformation.reportInformation.dealerName);
            this.reportInformation.get('elaboratedBY').setValue(this.stepService.warrantyInformation.personalInformation.reportInformation.elaboratedBY);

            this.mobileUnitInformation.get('vinNumber').setValue(this.stepService.warrantyInformation.personalInformation.mobileUnitInformation.vinNumber);
            this.mobileUnitInformation.get('mileage').setValue(this.stepService.warrantyInformation.personalInformation.mobileUnitInformation.mileage);
            this.mobileUnitInformation.get('model').setValue(this.stepService.warrantyInformation.personalInformation.mobileUnitInformation.model);
            this.mobileUnitInformation.get('engineSerie').setValue(this.stepService.warrantyInformation.personalInformation.mobileUnitInformation.engineSerie);
            this.mobileUnitInformation.get('saleDate').setValue(new Date(this.stepService.warrantyInformation.personalInformation.mobileUnitInformation.saleDate));
            this.mobileUnitInformation.get('saleBy').setValue(this.stepService.warrantyInformation.personalInformation.mobileUnitInformation.saleBy);
            this.mobileUnitInformation.get('owner').setValue(this.stepService.warrantyInformation.personalInformation.mobileUnitInformation.owner);
            this.mobileUnitInformation.markAllAsTouched();
            this.blockedDocument = false;
        }, 500);

            
    }

}
