import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { WarrantyClaim } from 'src/app/model/warranty-claim.model';
import { OtherExpensesService } from 'src/app/services/other-expenses.service';
import { PartsReplacedService } from 'src/app/services/parts-replaced.service';
import { StepService } from 'src/app/services/step.service';
import { WarrantyClaimsService } from 'src/app/services/warranty-claims.service';

@Component({
    selector: 'app-confirmation',
    templateUrl: './confirmation.component.html',
    providers:[]
})
export class ConfirmationComponent implements OnInit { 

    first = 0;
    rows = 10;
    headerRelationPart: HeaderTable[];
    headerOtherJob: HeaderTable[];
    warrantyClaim: WarrantyClaim;
    consultForm = false;
    blockedDocument: boolean = false;

    constructor(private stepService: StepService, private router: Router, private warrantyClaimsService: WarrantyClaimsService, private messageService: MessageService,
        private WarrantyService: WarrantyClaimsService, private partsReplacedService: PartsReplacedService, private otherExpensesService: OtherExpensesService) {}

    ngOnInit(): void {
        this.warrantyClaim = this.stepService.warrantyInformation;

        let claimNumber = this.stepService.warrantyInformation.personalInformation.reportInformation.claimNumber;

        this.WarrantyService.getWarrantyClaim(claimNumber).subscribe(response => {
            let warrantyId = response.id;
            this.partsReplacedService.getPartsReplacedByWarrantyClaims(warrantyId).subscribe(partResponse => {
                this.stepService.warrantyInformation.partInformation.relationPart = partResponse;
                this.otherExpensesService.getOtherExpenses(warrantyId).subscribe(otherExpensesResponse => {
                    this.stepService.warrantyInformation.partInformation.otherJobInformation = otherExpensesResponse;
                });  
            }, error => {
                this.messageService.add({
                    key: "tst",
                    severity: "error",
                    summary: "Error",
                    detail: error.error.message,
                });
            });
        }, error => {
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "Error",
                detail: error.error.message,
            });
        });

        if(this.warrantyClaim.status !== 'Draft') {
            this.consultForm = true;
        }
        this.headerRelationPart = [
            { label: "No. de parte", SortableColumn: "No. de parte" },
            { label: "Cantidad", SortableColumn: "Cantidad" },
            { label: "Descripción", SortableColumn: "Descripción" },
            { label: "Costo U", SortableColumn: "Costo U" },
            { label: "T. Labor", SortableColumn: "T. Labor" },
            { label: "Total", SortableColumn: "Total" },
        ];

        this.headerOtherJob = [
            { label: 'Concepto', SortableColumn: 'Concepto' },
            { label: 'Cantidad', SortableColumn: 'Cantidad' },
            { label: 'Detalle', SortableColumn: 'Detalle' },
            { label: 'Factura', SortableColumn: 'Factura' },
            { label: 'Costo', SortableColumn: 'Costo' },
            { label: 'Total', SortableColumn: 'Total' },
        ];
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
        return this.stepService.warrantyInformation.partInformation.relationPart
            ? this.first === this.stepService.warrantyInformation.partInformation.relationPart.length - this.rows
            : true;
    }

    isFirstPage(): boolean {
        return this.stepService.warrantyInformation.partInformation.relationPart ? this.first === 0 : true;
    }

    backStep() {
        this.router.navigate(['/warranty/informationPart']);
    }

    endView(){
        this.messageService.add({
            key: "tst",
            severity: "success",
            summary: "Nota",
            detail: `A finalizado con la consulta del reclamo ${this.stepService.warrantyInformation.personalInformation.reportInformation.claimNumber}` ,
        });
        this.stepService.resetWarrantyInformation();
        setTimeout(() => {
            this.router.navigate(['/warranty-consult']);
        }, 1200);
    }

    confirmation(){
        this.blockedDocument = true;
        this.warrantyClaimsService.saveWarrantyClaim(this.warrantyClaim).subscribe((response) => {
            this.stepService.complete();
            this.stepService.resetWarrantyInformation();
            setTimeout(() => {
                this.blockedDocument = false;
                this.router.navigate(['/warranty-consult']);
            }, 1200);
        }, (error) => {
            this.messageService.add({
                key: "tst",
                severity: "error",
                summary: "Error",
                detail: error.error.message,
            });
            this.blockedDocument = false;
        });
    }
}