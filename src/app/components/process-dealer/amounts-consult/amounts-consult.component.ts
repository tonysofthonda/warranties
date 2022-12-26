import { Component, OnInit } from '@angular/core';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { HistoryApprovedAmount } from 'src/app/model/history.approved.amount.model';
import { ApprovedAmountService } from 'src/app/services/approved.amount.service';

@Component({
    selector: 'app-amounts-consult',
    templateUrl: './amounts-consult.component.html'
})
export class AmountsConsultComponent implements OnInit {

    amountData: any[] = [];
    selectedAmount: any;
    heardes: HeaderTable[];
    data: HistoryApprovedAmount[] = [];
    loading: boolean = true;

    constructor(private approvedAmount: ApprovedAmountService) { }

    ngOnInit(): void {
        this.heardes = [
            { label: 'No. de reclamo', SortableColumn: 'claim' },
            { label: 'Monto', SortableColumn: 'amount' },
            { label: 'VIN', SortableColumn: 'vin' },
            { label: 'Fecha de orden', SortableColumn: 'dateIni' },
            { label: 'Estatus', SortableColumn: 'status' }
          ];
          this.getApprovedConsult();
    }

    getApprovedConsult() {
        this.loading = true;
        this.approvedAmount.getHistoryApprovedAmount().subscribe(data => {
            this.data = data;
            this.loading = false;
        });
    }
}
