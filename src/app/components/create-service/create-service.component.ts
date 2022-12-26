import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { ActivatedRoute, Router } from "@angular/router";

//Service
import { StateService } from "src/app/services/state.service";
import { CreateServiceService } from "src/app/services/create.service.service";
import { LocationService } from "src/app/services/localtion.service";
import { ModelService } from "src/app/services/model.service";

//Model
import { HeaderTable } from "src/app/model/headerTable.model";
import { InfoCreateService } from "src/app/model/info.create.service.model";
import { ServiceDetail } from "src/app/model/service-detail.model";

//Prime
import { ConfirmationService, MessageService, SelectItem } from "primeng/api";

//Util
import { AppValidationMessagesService } from "src/app/utils/app-validation-messages.service";
import { ConsultService } from "src/app/model/consult.service.model";
import { Models } from "src/app/model/models.model";
import { VinService } from "src/app/services/vin.service";
import { DealerService } from "src/app/services/dealer.service";
import { OrderTypeService } from "src/app/services/ordertype.service";
import { WorkTypeService } from "src/app/services/worktype.service";
import { ServicesService } from "src/app/services/services.service";
import { Services } from "src/app/model/Services.model";
import { PartsService } from "src/app/services/parts.service";

@Component({
    selector: "app-create-service",
    templateUrl: "./create-service.component.html",
    providers: [ConfirmationService, MessageService]
})
export class CreateServiceComponent implements OnInit {
    infoReportForm: FormGroup;
    infoDealerForm: FormGroup;

    infoClientForm: FormGroup;
    infoMobileUnitForm: FormGroup;

    serviceDetailForm: FormGroup;
    heardes: HeaderTable[];
    first = 0;
    rows = 10;
    loading: boolean;
    partForm: FormGroup;
    title: string;
    typeAccion: boolean;
    isVisibleDialog: Boolean = false;

    infoCreateService: InfoCreateService;
    modelDrown: SelectItem[] = [];
    orderDrown: SelectItem[] = [];
    workDrown: SelectItem[] = [];
    
    stateDrown: SelectItem[] = [];
    locationsDrown: SelectItem[] = [];
    statusDrown: SelectItem[] = [
        { label: "DRAFT", value: "DRAFT" },
        { label: "GUARDADO", value: "GUARDADO" },
    ];
    validations = [];
    disabledHistory: boolean = false;
    isUpdateService: boolean = false;
    consultService: ConsultService;
    btnSave: boolean;
    serviceNumberModel: string;
    isServiceNumber: boolean = false;
    minDate: Date;
    minDateOut: Date;

    regexAlfanumeric: string = "^[a-zA-Z0-9]+.*$";
    regexNumeric: string = "^(0|[1-9][0-9]*)$";
    regexPhone: string = "^[0-9()+].*$";
    regexEmail: string = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$";
    regexNotSpecialChar: string =
        "^[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9]+[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9\\s]*$";
    regexNotSpecialCharAddress: string = "^[a-zA-Z0-9-.]+[a-zA-Z0-9-.\\s]*$";
    regexSpecialChar: string = '^[A-Za-z0-9)(,\u00f1\u00d1\u00E0-\u00FC]+.*$';
    regexChar: string = "^[a-zA-Z\u00f1\u00d1\u00E0-\u00FC]+[a-zA-Z\\s]+.*$";
    regexOrderNumber: string = "^[a-zA-Z0-9s-]*$";

    blockedDocument: boolean = false;

    isEdit: boolean = false;

    constructor(
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private formb: FormBuilder,
        private dealerService: DealerService,
        private modelService: ModelService,
        private partsService: PartsService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private messages: AppValidationMessagesService,
        private vinService: VinService,
        private orderTypeService: OrderTypeService,
        private workTypeService: WorkTypeService,
        private servicesService: ServicesService
    ) { }

    ngOnInit(): void {

        // this.activatedRoute.params.subscribe((params) => {
        //     //Query param to refresh form consult from sidebar
        //     if(params){
        //         console.log(params)
        //         if(params.action === 'edit') {
        //             this.isEdit = true;
        //         } else {

        //         }
        //     }
        // });

        setTimeout(() => {
            this.blockedDocument = true; 
        }, 1);

        this.initForms();
        this.getValidations();
        this.getDealerInfo();
        this.getModel();
        this.getOrderType();

        this.heardes = [
            { label: "No. de parte", SortableColumn: "No. de parte" },
            { label: "Cantidad", SortableColumn: "Cantidad" },
            { label: "Descripción", SortableColumn: "Descripción" },
            { label: "Costo U", SortableColumn: "Costo U" },
            { label: "T. Labor", SortableColumn: "T. Labor" },
            { label: "Total", SortableColumn: "Total" },
            { label: "Acciones", SortableColumn: "Acciones" },
        ];
        
    }

    createService() {
        let dealerNumber = this.infoDealerForm.get("dealerNumber").value;
        let dealerName = this.infoDealerForm.get('dealerName').value;
        let createBy = this.infoDealerForm.get('createReportBy').value;

        console.log(dealerNumber, dealerName, createBy)
        this.servicesService.getNewService(dealerNumber, dealerName, createBy).subscribe((serviceResponse) => {
            if(serviceResponse) {
                this.infoReportForm.get('serviceNumber').setValue(serviceResponse.orderNumber);
                this.infoReportForm.get('dateCreated').setValue(new Date(serviceResponse.dateRegister));
                this.infoReportForm.get('status').setValue(serviceResponse.status);
            }
            this.blockedDocument = false;
        }, (error) => {
            this.blockedDocument = false;
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "Error",
                detail: error.error,
            });
        });
    }

    getDealerInfo() {
        if (localStorage.getItem("DealerNumber")) {
            let dealerNumber = localStorage.getItem("DealerNumber");
            this.dealerService.getDealer(dealerNumber).subscribe(
                (dealerResponse) => {
                    localStorage.getItem("FullName")
                        ? this.infoDealerForm
                            .get("createReportBy")
                            .setValue(localStorage.getItem("FullName"))
                        : this.infoDealerForm
                            .get("createReportBy")
                            .setValue("No Disponible");
                    this.infoDealerForm
                        .get("dealerNumber")
                        .setValue(dealerResponse.dealerNumber);
                    this.infoDealerForm
                        .get("dealerName")
                        .setValue(dealerResponse.name);
                        this.createService();
                        this.blockedDocument = false;
                },
                (error) => {
                    this.blockedDocument = false;
                 }
            );
        }
    }

    onChangeOrder() {
        let orderType = this.serviceDetailForm.get("orderType").value;
        this.serviceDetailForm.get('workType').setValue('');
        this.serviceDetailForm.get("workType").enable();
        if(orderType) {
            this.getWorkType(orderType.value);
        }
    }

    onChangeWork() {
        let workType = this.serviceDetailForm.get("workType").value;
        if(workType) {
            this.workTypeService.getAll(workType.value).subscribe((workTypeResponse) => {
                if(workTypeResponse) {
                    this.serviceDetailForm.get('detail').setValue(workTypeResponse[0].details);
                } else {
                    this.messageService.add({
                        key: "tst",
                        severity: "warn",
                        summary: "Alerta",
                        detail: `No se encontro ningun tipo de trabajo dado de alta en la aplicacion.`,
                    }); 
                }
            }, (error) => {
                this.messageService.add({
                    key: "tst",
                    severity: "error",
                    summary: "Error",
                    detail: error.error,
                });
            });
        }
    }

    getWorkType(idOrderType: number) {
        this.workTypeService.getByIdOrderType(idOrderType).subscribe((workTypeResponse) => {
            if(workTypeResponse) {
                this.workDrown = workTypeResponse.map((work) => ({
                    label: `${work.name}, ${work.description}`,
                    value: work.idWorkType,
                }));
            } else {
                this.serviceDetailForm.get("workType").disable();
                this.messageService.add({
                    key: "tst",
                    severity: "warn",
                    summary: "Alerta",
                    detail: `No se encontro ningun tipo de trabajo dado de alta en la aplicacion.`,
                }); 
            }
        }, (error) => {
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "Error",
                detail: error.error,
            });
        });
    }

    getOrderType() {
        this.orderTypeService.getAll().subscribe((orderTypeResponse) => {
            this.blockedDocument = false;
            if(orderTypeResponse) {
                this.orderDrown = orderTypeResponse.map((order) => ({
                    label: `${order.name}, ${order.description}`,
                    value: order.idOrderType,
                }));
            } else {
                this.messageService.add({
                    key: "tst",
                    severity: "warn",
                    summary: "Alerta",
                    detail: `No se encontro ningun tipo de orden dado de alta en la aplicacion.`,
                }); 
            }

        }, (error) => {
            this.blockedDocument = false;
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "Error",
                detail: error.error,
            });
        });
    }

    getModel() {
        this.modelService.getAllModels().subscribe((modelsResponse: Models[]) => {
            this.blockedDocument = false;
            if(modelsResponse) {
                this.modelDrown = modelsResponse.map((model) => ({
                    label: model.model,
                    value: model.id,
                }));
            } else {
                this.blockedDocument = false;
                this.messageService.add({
                    key: "tst",
                    severity: "warn",
                    summary: "Alerta",
                    detail: `No se encontro ningun modelo dado de alta en la aplicacion.`,
                }); 
            }
        });
    }

    searchVin() {
        this.blockedDocument = true;
        let vinNumber: string = this.infoMobileUnitForm.get("vin").value;
        if (vinNumber) {
            this.vinService.getDataMicroserviceNetcommerce(vinNumber).subscribe((netcommerceResponse) => {
                console.log(netcommerceResponse)
                if(netcommerceResponse.status != 1) {
                    this.enableInfoMobile();
                    this.messageService.add({
                        key: "tst",
                        severity: "warn",
                        summary: vinNumber,
                        detail: "El VIN no se encontro en la consulta al microservicio.",
                    });
                    this.blockedDocument = false;
                } else { 
                    this.disableInfoMobile();
                    if(netcommerceResponse.data.nomotor && netcommerceResponse.data.model) { 
                        let model = this.modelDrown.find(x => x.label === netcommerceResponse.data.model);
                        if (model) { 
                            this.infoMobileUnitForm.get('model').setValue(model);
                            this.infoMobileUnitForm.get('engineSeries').setValue(netcommerceResponse.data.nomotor);
                            this.infoMobileUnitForm.get('color').setValue(netcommerceResponse.data.color);
                            this.infoMobileUnitForm.get('saleDate').setValue(netcommerceResponse.data.date_sale);
                            this.infoMobileUnitForm.get('soldBy').setValue(netcommerceResponse.data.name_dealer);
                            this.infoMobileUnitForm.get('owner').setValue(netcommerceResponse.data.data_custumer.name);
                            this.infoMobileUnitForm.get('year').setValue(netcommerceResponse.data.year);
                        } else {
                            this.messageService.add({
                                key: "tst",
                                severity: "warn",
                                summary: "Alerta",
                                detail: `El modelo (${netcommerceResponse.data.model}) asignado al VIN (${vinNumber}) no se encuentradado de alta.`,
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
                    this.blockedDocument = false;
                }
            });
        }
    }

    getValidations() {
        this.messages.messagesRequired = "true";
        this.messages.messagesPattern = "Ingresar caracteres alfanumericos";
        this.validations.push(
            this.messages.getValidationMessagesWithName("serviceNumber")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "10";
        this.messages.messagesPattern = "Ingresar caracteres de texto";
        this.validations.push(
            this.messages.getValidationMessagesWithName("clientName")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "10";
        this.messages.messagesPattern = "Ingresar solo caracteres de texto";
        this.validations.push(
            this.messages.getValidationMessagesWithName("address")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "10";
        this.messages.messagesPattern = "Ingresar solo digitos";
        this.validations.push(
            this.messages.getValidationMessagesWithName("phone")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesPattern = "Correo no valido";
        this.validations.push(
            this.messages.getValidationMessagesWithName("email")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "20";
        this.messages.messagesPattern = "Ingresar solo caracteres alfanumericos";
        this.validations.push(
            this.messages.getValidationMessagesWithName("vin")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "6";
        this.messages.messagesPattern = "Ingresar solo digitos";
        this.validations.push(
            this.messages.getValidationMessagesWithName("mileage")
        );

        this.messages.messagesRequired = "true";
        this.validations.push(
            this.messages.getValidationMessagesWithName("dateAdmission")
        );

        this.messages.messagesRequired = "true";
        this.validations.push(
            this.messages.getValidationMessagesWithName("departureDate")
        );

        this.messages.messagesRequired = "true";
        this.validations.push(
            this.messages.getValidationMessagesWithName("orderType")
        );

        this.messages.messagesRequired = "true";
        this.validations.push(
            this.messages.getValidationMessagesWithName("workType")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "200";
        this.messages.messagesPattern = "Ingresar solo caracteres alfanumericos";
        this.validations.push(
            this.messages.getValidationMessagesWithName("description")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "200";
        this.messages.messagesPattern = "Ingresar solo caracteres de texto";
        this.validations.push(
            this.messages.getValidationMessagesWithName("owner")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "100";
        this.messages.messagesPattern = "Ingresar solo caracteres de texto";
        this.validations.push(
            this.messages.getValidationMessagesWithName("soldBy")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "200";
        this.messages.messagesPattern = "Ingresar solo caracteres de texto";
        this.validations.push(
            this.messages.getValidationMessagesWithName("createReportBy")
        );

        this.messages.messagesRequired = "true";
        this.validations.push(
            this.messages.getValidationMessagesWithName("model")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "4";
        this.messages.messagesPattern = "Ingresar solo digitos";
        this.validations.push(
            this.messages.getValidationMessagesWithName("year")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "50";
        this.messages.messagesPattern = "Ingresar solo caracteres de texto";
        this.validations.push(
            this.messages.getValidationMessagesWithName("color")
        );

        this.messages.messagesRequired = "true";
        this.messages.messagesMaxLenght = "100";
        this.messages.messagesPattern = "Ingresar solo caracteres de texto";
        this.validations.push(
            this.messages.getValidationMessagesWithName("engineSeries")
        );



    }

    initForms() {
        this.infoReportForm = this.formb.group({
            serviceNumber: [
                { value: '', disabled: true },
            ],
            dateCreated: [{ value: '', disabled: true }],
            status: [{value: '', disabled: true}]
        });

        this.infoDealerForm = this.formb.group({
            dealerNumber: [{ value: "", disabled: true }],
            dealerName: [{ value: "", disabled: true }],
            createReportBy: [{ value: "", disabled: false },
                [
                    Validators.required,
                    Validators.maxLength(200),
                    Validators.pattern(this.regexChar),
                ],
            ],
        });

        this.infoClientForm = this.formb.group({
            clientName: [
                { value: "", disabled: false },
                [
                    Validators.required,
                    Validators.maxLength(200),
                    Validators.pattern(this.regexChar),
                ],
            ],
            address: [
                { value: "", disabled: false },
                [
                    Validators.required,
                    Validators.maxLength(200),
                    Validators.pattern(this.regexNotSpecialCharAddress),
                ],
            ],
            phone: [
                { value: "", disabled: false },
                [
                    Validators.required,
                    Validators.minLength(10),
                    Validators.maxLength(10),
                    Validators.pattern(this.regexNumeric),
                ],
            ],
            email: [
                { value: "", disabled: false },
                [
                    Validators.required,
                    Validators.maxLength(200),
                    Validators.pattern(this.regexEmail),
                ],
            ],
        });

        this.infoMobileUnitForm = this.formb.group({
            vin: [{ value: '', disabled: false },
                [Validators.required, Validators.maxLength(20), Validators.pattern(this.regexAlfanumeric)]
            ],
            model: [{ value: '', disabled: true },
                [Validators.required]
            ],
            engineSeries: [{ value: '', disabled: true },
                [Validators.required, Validators.maxLength(100), Validators.pattern(this.regexAlfanumeric)]
            ],
            saleDate: [{ value: '', disabled: false }],
            soldBy: [{ value: '', disabled: false },
                [
                    Validators.required,
                    Validators.maxLength(100),
                    Validators.pattern(this.regexChar)
                ],
            ],
            year: [{ value: '', disabled: true },
                [Validators.required, Validators.maxLength(4), Validators.pattern(this.regexNumeric)]
            ],
            color: [{ value: '', disabled: true },
                [Validators.required, Validators.maxLength(50), Validators.pattern(this.regexChar)]
            ],
            owner: [{ value: '', disabled: false },
                [
                    Validators.required,
                    Validators.maxLength(200),
                    Validators.pattern(this.regexChar)
                ],
            ],
        });

        this.serviceDetailForm = this.formb.group({
            dateAdmission: [
                { value: '', disabled: false },
                [Validators.required],
            ],
            departureDate: [{ value: '', disabled: false },
                [Validators.required]
            ],
            mileage: [
                { value: '', disabled: false },
                [
                    Validators.required,
                    Validators.maxLength(6),
                    Validators.pattern(this.regexNumeric),
                ],
            ],
            orderType: [{ value: '', disabled: false },
                [
                    Validators.required
                ],
            ],
            workType: [{ value: '', disabled: true },
                [
                    Validators.required
                ],
            ],
            detail: [{ value: '', disabled: true }],
            description: [{ value: '', disabled: false },
                [
                    Validators.required,
                    Validators.maxLength(200),
                    Validators.pattern(this.regexSpecialChar)
                ]
            ],
        });

        this.partForm = this.formb.group({
            id: [''],
            partNumber: ['', [Validators.required, Validators.pattern("^[A-Z0-9s-]*$")],
            ],
            quantity: [[''], [Validators.required, Validators.minLength(1), Validators.maxLength(255),],
            ],
            totalHours: [[''], [Validators.required, Validators.minLength(1), Validators.maxLength(3),],
            ],
            price: [[''], [Validators.required, Validators.minLength(1), Validators.maxLength(255),],
            ],
            description: [[''], [Validators.required, Validators.minLength(1), Validators.maxLength(255),],
            ],
            model: [[''], [Validators.required, Validators.minLength(1), Validators.maxLength(255),],
            ],
        });
        // this.markAsTouched();
    }

    inputsDisable() {
        this.infoClientForm.get("clientName").enable();
        this.infoClientForm.get("address").enable();
        this.infoClientForm.get("phone").enable();
        this.infoClientForm.get("email").enable();

        this.infoClientForm.get("clientName").setValue('');
        this.infoClientForm.get("address").setValue('');
        this.infoClientForm.get("phone").setValue('');
        this.infoClientForm.get("email").setValue('');
    }

    disableInfoMobile() {
        this.infoMobileUnitForm.get('model').disable();
        this.infoMobileUnitForm.get('engineSeries').disable();
        this.infoMobileUnitForm.get('color').disable();
        this.infoMobileUnitForm.get('year').disable();

        this.infoMobileUnitForm.get('model').setValue('');
        this.infoMobileUnitForm.get('engineSeries').setValue('');
        this.infoMobileUnitForm.get('color').setValue('');
        this.infoMobileUnitForm.get('saleDate').setValue('');
        this.infoMobileUnitForm.get('soldBy').setValue('');
        this.infoMobileUnitForm.get('owner').setValue('');
        this.infoMobileUnitForm.get('year').setValue('');
    }

    enableInfoMobile() {
        this.infoMobileUnitForm.get('model').enable();
        this.infoMobileUnitForm.get('engineSeries').enable();
        this.infoMobileUnitForm.get('color').enable();
        this.infoMobileUnitForm.get('year').enable();

        this.infoMobileUnitForm.get('model').setValue('');
        this.infoMobileUnitForm.get('engineSeries').setValue('');
        this.infoMobileUnitForm.get('color').setValue('');
        this.infoMobileUnitForm.get('saleDate').setValue('');
        this.infoMobileUnitForm.get('soldBy').setValue('');
        this.infoMobileUnitForm.get('owner').setValue('');
        this.infoMobileUnitForm.get('year').setValue('');
    }

    deleteService() {
        let serviceNumber = this.infoReportForm.get('serviceNumber').value;
        
        this.confirmationService.confirm({
            message: `¿Deseas eliminar el servicio ${serviceNumber}?`,
            header: 'Eliminar Servicio',
            icon: 'pi pi-exclamation-triangle',
            rejectButtonStyleClass: 'p-button-info',
            acceptButtonStyleClass: 'p-button-danger',
            acceptLabel: 'Eliminar',
            rejectLabel: 'Cancelar',
            acceptIcon: 'pi pi-trash',
            accept: () => {
                this.blockedDocument = true;
                this.servicesService.getServicesByOrderNumber(serviceNumber).subscribe((serviceResponse) => {
                    if(serviceResponse) {
                        this.servicesService.deleteService(serviceResponse).subscribe((response) => {
                            this.blockedDocument = false;
                            this.messageService.add({
                                key: "tst",
                                severity: "info",
                                summary: "Nota",
                                detail: `Servicio (${serviceNumber}) eliminado.`,
                            }); 
                            setTimeout(() => {
                                this.router.navigate(['/consult-service']);
                            }, 1000);
                        });
                    }
                }, (error) => {
                    this.blockedDocument = false;
                    this.messageService.add({
                        key: "tst",
                        severity: "error",
                        summary: "Error",
                        detail: error.error,
                    });
                });
                
            }
        });
    }

    saveService() {
        this.blockedDocument = true;
        let serviceNumber = this.infoReportForm.get('serviceNumber').value;
        this.servicesService.getServicesByOrderNumber(serviceNumber).subscribe((serviceResponse) => {
            if(serviceResponse) {
                let createBy = this.infoDealerForm.get('createReportBy').value;
                let clientName = this.infoClientForm.get('clientName').value;
                let phone = this.infoClientForm.get('phone').value;
                let email = this.infoClientForm.get('email').value;
                let address = this.infoClientForm.get('address').value;
                let vin = this.infoMobileUnitForm.get('vin').value;
                let model = this.infoMobileUnitForm.get('model').value;
                let engineSeries = this.infoMobileUnitForm.get('engineSeries').value;
                let year = this.infoMobileUnitForm.get('year').value;
                let color = this.infoMobileUnitForm.get('color').value;
                let saleDate = this.infoMobileUnitForm.get('saleDate').value;
                let soldBy = this.infoMobileUnitForm.get('soldBy').value;
                let owner = this.infoMobileUnitForm.get('owner').value;
                let dateIn = this.serviceDetailForm.get('dateAdmission').value;
                let dateOut = this.serviceDetailForm.get('departureDate').value;
                let kilometer = this.serviceDetailForm.get('mileage').value;
                let orderType = this.serviceDetailForm.get('orderType').value;
                let workType = this.serviceDetailForm.get('workType').value;
                let description = this.serviceDetailForm.get('description').value;
                this.orderTypeService.getById(orderType.value).subscribe((orderResponse) => {
                    if(orderResponse){
                        this.workTypeService.getAll(workType.value).subscribe((workResponse) => {
                            if(workResponse) {
                                serviceResponse.reportedBy = createBy;
                                serviceResponse.customerName = clientName;
                                serviceResponse.customerPhone = phone;
                                serviceResponse.customerEmail = email;
                                serviceResponse.address = address;
                                serviceResponse.vin = vin;
                                serviceResponse.model = model.label;
                                serviceResponse.serie = engineSeries;
                                serviceResponse.year = year;
                                serviceResponse.color = color;
                                serviceResponse.dateSale = saleDate;
                                serviceResponse.saleBy = soldBy;
                                serviceResponse.owner = owner;
                                serviceResponse.dateIn = dateIn;
                                serviceResponse.dateOut = dateOut;
                                serviceResponse.kilometer = kilometer;
                                serviceResponse.orderType = orderResponse[0];
                                serviceResponse.workType = workResponse[0];
                                serviceResponse.description = description;
                                this.servicesService.saveService(serviceResponse).subscribe((response) => {
                                    this.blockedDocument = false;
                                    this.messageService.add({
                                        key: "tst",
                                        severity: "success",
                                        summary: "Nota",
                                        detail: 'Servicio Guardado',
                                    });
                                    setTimeout(() => {
                                        this.router.navigate(['/consult-service']);
                                    }, 1000);
                                }, (error) => {
                                    this.blockedDocument = false;
                                    this.messageService.add({
                                        key: "tst",
                                        severity: "error",
                                        summary: "Error",
                                        detail: error.error,
                                    });
                                });
                                
                            }
                        }, (error) => {
                            this.blockedDocument = false;
                            this.messageService.add({
                                key: "tst",
                                severity: "error",
                                summary: "Error",
                                detail: error.error,
                            });
                        });
                    }
                }, (error) => {
                    this.blockedDocument = false;
                    this.messageService.add({
                        key: "tst",
                        severity: "error",
                        summary: "Error",
                        detail: error.error,
                    });
                });

            }
        }, (error) => {
            this.blockedDocument = false;
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "Error",
                detail: error.error,
            });
        });
    }

    cancelService() {
        this.messageService.add({
            key: "tst",
            severity: "warn",
            summary: "Nota",
            detail: 'Servicio Cancelado',
        });
        setTimeout(() => {
            this.router.navigate(['/consult-service']);
        }, 1000);
    }

    selectDateIn(event: Date) {
        let min = new Date();
        min.setDate(event.getDate());
        min.setMonth(event.getMonth());
        min.setFullYear(event.getFullYear());
        this.minDateOut = min;
        this.serviceDetailForm.get('departureDate').setValue(null);
        (new Date());
    }

    addPart() {
        this.partForm.reset();
        this.title = "AGREGAR PARTE";
        this.typeAccion = true;
        this.isVisibleDialog = true;
    }

    searchPart(event: Event) {
        let partNumber = this.partForm.get("partNumber").value;
        if (partNumber) {
            let sparePart = partNumber.replaceAll('-','');
            this.partsService.getSparePartMicroservice(sparePart).subscribe((response) => {
                if(response) {
                    if(response.status == 1) {
                        this.partsService.getPartsByPartNumber(partNumber).subscribe(
                            (partResponse) => {
                                if (partResponse) {
                                    // this.partSelected = partResponse;
                                    this.partForm.patchValue({
                                        model: partResponse.model,
                                        description: partResponse.descriptionPart,
                                        price: response.data.price,
                                        totalHours: partResponse.frt,
                                        quantity: 1,
                                    });
                                    this.messageService.add({
                                        key: "tst",
                                        severity: "success",
                                        summary: "¡Encontrada!",
                                        detail: "Parte encontrada exitosamente",
                                    });
                                } else {
                                    this.partForm.patchValue({
                                        model: null,
                                        description: null,
                                        price: response.data.price,
                                        totalHours: null,
                                        quantity: null
                                    });
                                    this.confirmationService.confirm({
                                        header: 'NOTA',
                                        message: `La parte ${partNumber} no se encuentra registrada\n
                                        ¿Deseas agregarla de manera manual?`,
                                        icon: 'pi pi-exclamation-triangle',
                                        acceptButtonStyleClass: 'p-button-success',
                                        rejectButtonStyleClass: 'p-button-danger',
                                        acceptLabel: 'Si',
                                        accept: () => {
                                            this.messageService.add({key: "tst", severity:'info', summary:'Nota!', detail:'Asegurate de ingresar todos los campos para el registro manual.'});
                                        },
                                        reject: () => {
                                            this.isVisibleDialog = false;
                                        }
                                    });
                                    // this.partSelected = null;
                                }
                            },
                            (err) => {
                                // this.partSelected = null;
                                this.messageService.add({
                                    key: "tst",
                                    severity: "error",
                                    summary: "¡ERROR!",
                                    detail: err.error,
                                });
                            }
                        );
                    } else {
                        this.partsService.getPartsByPartNumber(partNumber).subscribe(
                            (partResponse) => {
                                if (partResponse) {
                                    //this.partSelected = partResponse;
                                    this.partForm.patchValue({
                                        model: partResponse.model,
                                        description: partResponse.descriptionPart,
                                        price: partResponse.price,
                                        totalHours: partResponse.frt,
                                        quantity: 1,
                                    });
                                    this.messageService.add({
                                        key: "tst",
                                        severity: "success",
                                        summary: "¡Encontrada!",
                                        detail: "Parte encontrada exitosamente",
                                    });
                                } else {
                                    this.partForm.patchValue({
                                        model:null,
                                        description:null,
                                        price:null,
                                        totalHours:null,
                                        quantity:null
                                    });
            
                                    this.confirmationService.confirm({
                                        header: 'NOTA',
                                        message: `La parte ${partNumber} no se encuentra registrada\n
                                        ¿Deseas agregarla de manera manual?`,
                                        icon: 'pi pi-exclamation-triangle',
                                        acceptButtonStyleClass: 'p-button-success',
                                        rejectButtonStyleClass: 'p-button-danger',
                                        acceptLabel: 'Si',
                                        accept: () => {
                                            this.messageService.add({key: "tst", severity:'info', summary:'Nota!', detail:'Asegurate de ingresar todos los campos para el registro manual.'});
                                        },
                                        reject: () => {
                                            this.isVisibleDialog = false;
                                        }
                                    });
                                    //this.partSelected = null;
                                }
                            },
                            (err) => {
                                //this.partSelected = null;
                                this.messageService.add({
                                    key: "tst",
                                    severity: "error",
                                    summary: "¡ERROR!",
                                    detail: err.error,
                                });
                            }
                        );
                    }
                }
            });
        } else {
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "¡ERROR!",
                detail: "Ingrese una parte a reamplazar",
            });
        }
    }
   
}
