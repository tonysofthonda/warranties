import { AppValidationMessagesService } from '../../../../../utils/app-validation-messages.service';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { ConfirmationService, Header, MessageService } from 'primeng/api';
import { OtherExpensesService } from 'src/app/services/other-expenses.service';
import { OtherExpenses } from 'src/app/model/warranty-claims.model';
import { StepService } from 'src/app/services/step.service';

@Component({
    selector: 'other-jobs',
    templateUrl: './other-jobs.component.html',
    providers:[ConfirmationService]
})
export class OtherJobsComponent implements OnInit {

    @Input() otherExpensesParam: OtherExpenses[];
    @Output() onExpensesEmitted: EventEmitter<OtherExpenses[]> = new EventEmitter();
    first = 0;
    rows = 10;
    heardes: Header[];
    heardesModal: Header[];
    otherJobs: OtherExpenses[] = [];
    category: any[];
    validations = [];
    datosForm: FormGroup;
    title: string;
    selectCategory: string;
    display: boolean;
    typeAccion: boolean;
    warrantyId: number;
    costModel: number = 0;
    quantityModel: number = 1;
    totalModel: number = 0;
    editOtherExpenses: OtherExpenses;
    loading: boolean;
    regexNumeric: string = '^(0|[1-9][0-9]*)$';

    constructor(private fomb: FormBuilder, private messages: AppValidationMessagesService, private otherExpensesService: OtherExpensesService,
        private messageService: MessageService, public stepService: StepService, private confirmationService :ConfirmationService) { }

    ngOnInit(): void {
        this.formData();
        if(this.otherExpensesParam.length > 0) {
            this.warrantyId = this.otherExpensesParam[0].warrantyClaims;
            this.otherJobs = this.otherExpensesParam;
        }

        if(this.stepService.warrantyInformation.status === 'Draft' || !this.stepService.warrantyInformation.status) {
            this.heardes = [
                { label: 'Concepto', SortableColumn: 'Concepto' },
                { label: 'Cantidad', SortableColumn: 'Cantidad' },
                { label: 'Detalle', SortableColumn: 'Detalle' },
                { label: 'Factura', SortableColumn: 'Factura' },
                { label: 'Costo', SortableColumn: 'Costo' },
                { label: 'Total', SortableColumn: 'Total' },
                { label: 'Accion', SortableColumn: 'Acciones' }
            ];
        } else {
            this.heardes = [
                { label: 'Concepto', SortableColumn: 'Concepto' },
                { label: 'Cantidad', SortableColumn: 'Cantidad' },
                { label: 'Detalle', SortableColumn: 'Detalle' },
                { label: 'Factura', SortableColumn: 'Factura' },
                { label: 'Costo', SortableColumn: 'Costo' },
                { label: 'Total', SortableColumn: 'Total' },
            ];
        }

        this.getValidations();
        

        this.category = [
            { name: 'T3 Compra llantas a terceros' },
            { name: 'M1 Maquinado' },
            { name: 'P1 Pintura' },
            { name: 'M1 Soldadura' },
        ]
    }

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
        return this.otherJobs ? this.first === (this.otherJobs.length - this.rows): true;
    }

    isFirstPage(): boolean {
        return this.otherJobs ? this.first === 0 : true;
    }

    getOtherExpenses(warrantyId: number){
        this.loading = true;
        this.otherExpensesService.getOtherExpenses(warrantyId).subscribe(value => {
            this.otherJobs = value;
            this.loading = false;       
        });     
    }

    calculateTotal() {
        this.totalModel = this.quantityModel * this.costModel;
    }

    addOther() {
        this.datosForm.reset();
        this.display = true;
        this.typeAccion = true;
        this.title = 'AGREGAR OTRO GASTO'
    }

    editOther(otherExpenses: OtherExpenses) {
        this.title = 'MODIFICAR OTRO GASTO';
        this.editOtherExpenses = otherExpenses;
        this.display = true;
        this.typeAccion = false;
        this.datosForm.patchValue({
            id: (otherExpenses.id) ? otherExpenses.id : 0,        
            description: otherExpenses.details,
            invoice: otherExpenses.invoice,
            quantity: otherExpenses.quantity,
            cost: otherExpenses.cost,
            total: otherExpenses.total
        });
        let category =  this.category.find(data => data.name == otherExpenses.description);
        this.datosForm.get('category').setValue(category);
    }

    saveOtherExpenses() { 
        if(this.datosForm.valid){
            let otherExpenses ={
                id: (this.datosForm.get('id').value)  ? this.datosForm.get('id').value : 0,
                description: this.datosForm.get('category').value.name,
                quantity: this.datosForm.get('quantity').value,
                details: this.datosForm.get('description').value,
                invoice: this.datosForm.get('invoice').value,
                cost: this.datosForm.get('cost').value,
                total: this.datosForm.get('total').value,
                warrantyClaims: (this.warrantyId) ? this.warrantyId : null,
            };
            if(!this.typeAccion) {
                this.otherJobs.forEach((otherExpensesTemp, index) => {
                    if(this.editOtherExpenses == otherExpensesTemp) {
                        this.otherJobs.splice(index, 1, otherExpenses);
                    }
                });
                this.display = false;
            } else {
                this.otherJobs.push(otherExpenses);
            }
            this.otherJobs = [...this.otherJobs];
            this.onExpensesEmitted.emit(this.otherJobs);
            this.display = false;
        }
    }

    deleteInformation(otherExpenses: OtherExpenses){
        this.confirmationService.confirm({
            message: '¿Desea eliminar el gasto?',
            header: 'Eliminar el gasto',
            rejectButtonStyleClass: 'p-button-danger',
            acceptButtonStyleClass: 'p-button-success',
            acceptLabel: 'aceptar',
            rejectLabel: 'cancelar',
            accept: () => {

                if(otherExpenses.warrantyClaims && otherExpenses.id){
                    this.otherExpensesService.deleteOtherExpenses(otherExpenses.id).subscribe((response) => {
                        this.display = false;
                        this.messageService.add({
                            key: "tst",
                            severity: "success",
                            summary: "Eliminado!",
                            detail: "Parte eliminada exitosamente",
                        });
                    }, (error) => {
                        this.display = false;
                        this.messageService.add({
                            key: "tst",
                            severity: "error",
                            summary: "Error",
                            detail: error.error.message,
                        });
                    });
                }

                this.otherJobs.forEach((otherJobsTemp, index) => { 
                    if (otherJobsTemp == otherExpenses) {
                        this.otherJobs.splice(index, 1);
                        this.onExpensesEmitted.emit(this.otherJobs);
                    }
                });
            }
          });
    }

    reloadForm(){
        this.getOtherExpenses(this.otherExpensesParam[0].warrantyClaims);
        this.display = false;
        this.datosForm.reset();    
    }

    formData() {
        this.datosForm = this.fomb.group({
            id: [ { value: '', disabled: false } ],
            category: [ { value: '', disabled: false }, [Validators.required]],
            description: [ { value: '', disabled: false }, [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
            invoice: [ { value: '', disabled: false }, [Validators.required, Validators.minLength(1), Validators.maxLength(10), Validators.pattern(this.regexNumeric)]],
            quantity: [ { value: '', disabled: false }, [Validators.required, Validators.minLength(1), Validators.maxLength(2)]],                  
            cost: [ , [Validators.required, Validators.minLength(1), Validators.maxLength(6)]],
            total: [ { value: '', disabled: false } ]
        });
    }

    getValidations() {
        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('category'));        

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '10';
        this.messages._messagesPattern = "Ingresar solo digitos"
        this.validations.push(this.messages.getValidationMessagesWithName('invoice'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '50';
        this.validations.push(this.messages.getValidationMessagesWithName('description'));
        
        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('quantity'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('cost'));
    }

}