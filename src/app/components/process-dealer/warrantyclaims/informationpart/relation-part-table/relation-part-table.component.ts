import { FormGroup, FormBuilder, Validators } from "@angular/forms";
import { Component, EventEmitter, Input, OnInit, Output } from "@angular/core";
import { HeaderTable } from "src/app/model/headerTable.model";
import { PartsReplacedService } from "src/app/services/parts-replaced.service";
import { PartsReplaced } from "src/app/model/parts-replaced.model";
import { PartsService } from "src/app/services/parts.service";
import { ConfirmationService, MessageService } from "primeng/api";
import { Parts } from "src/app/model/warranty-claims.model";
import { AppValidationMessagesService } from "src/app/utils/app-validation-messages.service";
import { StepService } from "src/app/services/step.service";

@Component({
    selector: "relation-part-table",
    templateUrl: "./relation-part-table.component.html",
    providers: [ConfirmationService],
})
export class RelationPartTableComponent implements OnInit {
    @Input() partsReplacedParm: PartsReplaced[];
    @Output() onAddPartClickEmitted: EventEmitter<Boolean> =
    new EventEmitter();
    @Output() onPartsEmitted: EventEmitter<PartsReplaced[]> =
        new EventEmitter();

    first = 0;
    rows = 10;
    heardes: HeaderTable[];
    part: any[];
    partForm: FormGroup;
    title: string;
    isVisibleDialog: Boolean = false;
    typeAccion: boolean;
    warrantyId: number;
    partsReplaced: PartsReplaced[] = [];
    partSelected: Parts;
    myPaginationString: string = "";
    edtirPartsReplaced: PartsReplaced;
    loading: boolean;
    validations = [];

    constructor(
        private formb: FormBuilder,
        private partsReplacedService: PartsReplacedService,
        private partsService: PartsService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private messages: AppValidationMessagesService,
        public stepService: StepService
    ) {}

    ngOnInit(): void {

        this.getValidations();
        if (this.partsReplacedParm.length > 0) {
            this.warrantyId = this.partsReplacedParm[0].warrantyClaimsId;
        }
        this.partsReplaced = this.partsReplacedParm;

        this.newPartForm();
        if(this.stepService.warrantyInformation.status === 'Draft' || !this.stepService.warrantyInformation.status) {
            this.heardes = [
                { label: "No. de parte", SortableColumn: "No. de parte" },
                { label: "Cantidad", SortableColumn: "Cantidad" },
                { label: "Descripción", SortableColumn: "Descripción" },
                { label: "Costo U", SortableColumn: "Costo U" },
                { label: "T. Labor", SortableColumn: "T. Labor" },
                { label: "Total", SortableColumn: "Total" },
                { label: "Acciones", SortableColumn: "Acciones" },
            ];
        } else {
            this.heardes = [
                { label: "No. de parte", SortableColumn: "No. de parte" },
                { label: "Cantidad", SortableColumn: "Cantidad" },
                { label: "Descripción", SortableColumn: "Descripción" },
                { label: "Costo U", SortableColumn: "Costo U" },
                { label: "T. Labor", SortableColumn: "T. Labor" },
                { label: "Total", SortableColumn: "Total" },
            ];
        }
    }

    ////////////////////////Pagination Functions////////////////////////
    next() {
        this.first = this.first + this.rows;
    }

    prev() {
        this.first = this.first - this.rows;
    }

    reset() {
        this.first = 0;
    }

    isLastPage(): boolean {
        return this.partsReplaced
            ? this.first === this.partsReplaced.length - this.rows
            : true;
    }

    isFirstPage(): boolean {
        return this.partsReplaced ? this.first === 0 : true;
    }
    ////////////////////////Pagination Functions////////////////////////

    getPartsReplaced(warrantyId: number) {
        this.loading = true;
        this.partsReplacedService
            .getPartsReplacedByWarrantyClaims(warrantyId)
            .subscribe((value) => {
                this.partsReplaced = value;
            });
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
                                    this.partSelected = partResponse;
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
                                    this.partSelected = null;
                                }
                            },
                            (err) => {
                                this.partSelected = null;
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
                                    this.partSelected = partResponse;
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
                                    this.partSelected = null;
                                }
                            },
                            (err) => {
                                this.partSelected = null;
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

    addPart() {
        this.partForm.reset();
        this.title = "AGREGAR PARTE";
        this.typeAccion = true;
        this.isVisibleDialog = true;
    }

    editPart(partsReplaced: PartsReplaced) {
        this.title = "MODIFICAR PARTE";
        this.isVisibleDialog = true;
        this.typeAccion = false;
        this.partSelected = partsReplaced.part;
        this.edtirPartsReplaced = partsReplaced;
        this.partForm.patchValue({
            model: partsReplaced.part.model,
            description: partsReplaced.description,
            price: partsReplaced.unitCost,
            totalHours: partsReplaced.packingCost,
            id: partsReplaced.id,
            partNumber: partsReplaced.part.partNumber,
            quantity: partsReplaced.quantity,
        });
    }

    deleteInformation(partReplaced: PartsReplaced) {
        this.confirmationService.confirm({
            message: "¿Desea eliminar la parte?",
            header: "Eliminar la parte",
            rejectButtonStyleClass: "p-button-danger",
            acceptButtonStyleClass: "p-button-success",
            acceptLabel: "aceptar",
            rejectLabel: "cancelar",
            accept: () => {
                if (partReplaced.warrantyClaimsId && partReplaced.id) {
                    this.partsReplacedService
                        .deletePartReplaced(partReplaced.id)
                        .subscribe(
                            (response) => {
                                this.isVisibleDialog = false;
                                this.messageService.add({
                                    key: "tst",
                                    severity: "success",
                                    summary: "Eliminado!",
                                    detail: "Parte eliminada exitosamente",
                                });
                            },
                            (error) => {
                                this.isVisibleDialog = false;
                                this.messageService.add({
                                    key: "tst",
                                    severity: "error",
                                    summary: "Error",
                                    detail: error.error.message,
                                });
                            }
                        );
                }
                this.partsReplaced.forEach((partsReplacedTemp, index) => {
                    if (partsReplacedTemp == partReplaced) {
                        this.partsReplaced.splice(index, 1);
                        this.onPartsEmitted.emit(this.partsReplaced);
                    }
                });
            },
        });
    }

    savePart() {
        if (this.partForm.valid) {
            if (!this.partSelected) {
                let partNumber = this.partForm.get("partNumber").value;
                this.partsService
                    .getPartsByPartNumber(partNumber).subscribe((value) => {
                        if (!value) {
                            let frt = this.partForm.get("totalHours").value / this.partForm.get("quantity").value;
                            let part = {
                                id: null,
                                partNumber: this.partForm.get("partNumber").value,
                                descriptionPart: this.partForm.get("description").value,
                                refNo: null,
                                price: this.partForm.get("price").value,
                                frt: frt,
                                model: this.partForm.get("model").value,
                            };
                            this.partsService.savePart(part).subscribe((value) => {
                                    this.partsService
                                        .getPartsByPartNumber(part.partNumber)
                                        .subscribe((value) => {
                                            this.partSelected = value;
                                            this.setPartReplaced();
                                        });
                                });
                        } else {
                            this.partSelected = value;
                            this.setPartReplaced();
                        }
                    });
            } else {
                this.setPartReplaced();
            }
        }
    }

    setPartReplaced() {
        let total =
        this.partForm.get("quantity").value *
        this.partForm.get("price").value;

        let partReplaced = {
            id: this.partForm.get("id").value,
            description: this.partForm.get("description").value,
            quantity: this.partForm.get("quantity").value,
            packingCost: this.partForm.get("totalHours").value,
            unitCost: this.partForm.get("price").value,
            total: total,
            part: this.partSelected,
            warrantyClaimsId: this.warrantyId ? this.warrantyId : null,
        };
        if (!this.typeAccion) {
            this.partsReplaced.forEach((partsReplacedTemp, index) => {
                if (this.edtirPartsReplaced == partsReplacedTemp) {
                    this.partsReplaced.splice(index, 1, partReplaced);
                }
            });
            this.isVisibleDialog = false;
        } else {
            this.partsReplaced.push(partReplaced);
        }
        this.partsReplaced = [...this.partsReplaced];
        this.onPartsEmitted.emit(this.partsReplaced);
        this.isVisibleDialog = false;
    }

    reloadForm() {
        this.getPartsReplaced(this.warrantyId);
        this.isVisibleDialog = false;
        this.partForm.reset();
    }

    newPartForm() {
        this.partForm = this.formb.group({
            id: [""],
            partNumber: [
                "",
                [Validators.required, Validators.pattern("^[A-Z0-9s-]*$")],
            ],
            quantity: [
                [""],
                [
                    Validators.required,
                    Validators.minLength(1),
                    Validators.maxLength(255),
                ],
            ],
            totalHours: [
                [""],
                [
                    Validators.required,
                    Validators.minLength(1),
                    Validators.maxLength(3),
                ],
            ],
            price: [
                [""],
                [
                    Validators.required,
                    Validators.minLength(1),
                    Validators.maxLength(255),
                ],
            ],
            description: [
                [""],
                [
                    Validators.required,
                    Validators.minLength(1),
                    Validators.maxLength(255),
                ],
            ],
            model: [
                [""],
                [
                    Validators.required,
                    Validators.minLength(1),
                    Validators.maxLength(255),
                ],
            ],
        });
    }

    getValidations() {
        this.messages.messagesRequired = "true";
        this.messages._messagesMaxLenght = "20";
        this.messages._messagesPattern = "Ingresar letras mayusculas y números";
        this.validations.push(
            this.messages.getValidationMessagesWithName("partNumber")
        );

        this.messages.messagesRequired = "true";
        this.validations.push(
            this.messages.getValidationMessagesWithName("price")
        );

        this.messages.messagesRequired = "true";
        this.validations.push(
            this.messages.getValidationMessagesWithName("description")
        );

        this.messages.messagesRequired = "true";
        this.validations.push(
            this.messages.getValidationMessagesWithName("model")
        );

        this.messages.messagesRequired = "true";
        this.validations.push(
            this.messages.getValidationMessagesWithName("totalHours")
        );

        this.messages.messagesRequired = "true";
        this.validations.push(
            this.messages.getValidationMessagesWithName("quantity")
        );
    }
}
