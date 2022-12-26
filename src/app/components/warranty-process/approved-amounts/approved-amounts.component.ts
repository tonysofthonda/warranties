import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, MessageService, SelectItem } from 'primeng/api';
import { ApprovedAmount } from 'src/app/model/approved.amount.model';
import { Approved } from 'src/app/model/approved.model';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { TotalsAmounts } from 'src/app/model/totals.amounts.model';
import { ApprovedAmountService } from 'src/app/services/approved.amount.service';
import { DealerService } from 'src/app/services/dealer.service';

@Component({
  selector: 'app-approved-amounts',
  templateUrl: './approved-amounts.component.html',
  providers: [ConfirmationService, MessageService]
})
export class ApprovedAmountsComponent implements OnInit {

  approvedAmountForm: FormGroup;
  heardes: HeaderTable[];
  data: ApprovedAmount[] = [];
  dataTotals: TotalsAmounts[] = [];
  dealerDropdown: SelectItem[] = [];
  approved: Approved;
  btnDisabled: boolean = true;
  btnSearch: boolean = true;
  loading: boolean;

  display: boolean = false;
  msgDialog: string = '';
  unapprovedAmount: ApprovedAmount;
  textAreaModel: string = '';
  blockedDocument: boolean = false;

  selectedDealer: string;

  constructor(private formb: FormBuilder,
              private approvedService: ApprovedAmountService,
              private confirmationService: ConfirmationService,
              private messageService: MessageService,
              private dealersService: DealerService,
              private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.approvedAmountForm = this.formb.group({
      dateIni: ['', [Validators.required]],
      dateEnd: ['', [Validators.required]],
      dealer: ['', [Validators.required]]
    });
    this.getDealers();
    this.heardes = [
      { label: 'Distribuidor', SortableColumn: 'dealer' },
      { label: 'No. Reclamo', SortableColumn: 'claim' },
      { label: 'Modelo', SortableColumn: 'model' },
      { label: 'Descripción', SortableColumn: 'description' },
      { label: 'Refacción', SortableColumn: 'repair' },
      { label: 'Labor', SortableColumn: 'labor' },
      { label: 'Estatus', SortableColumn: 'estatus' },
      { label: 'Acciones', SortableColumn: 'acciones' },
    ];
  }

  getDealers() {
    this.dealersService.getAllDealers().subscribe((data: any) => {
      this.dealerDropdown = data.map(r => (
        { label: r.dealerNumber + ' ' + r.name, value: r.id }
      ));
    });
  }

  getTotalsAmounts() {
    this.approvedService.getTotalsAmount().subscribe(data => {
      this.dataTotals = data;
    });
}

  getApprovedAmounts() {
    this.loading = true;
    let dateIni = this.datePipe.transform(this.approvedAmountForm.get('dateIni').value, 'yyyy/MM/dd');
    let dateEnd = this.datePipe.transform(this.approvedAmountForm.get('dateEnd').value, 'yyyy/MM/dd');
    let dealer: number = 0;
    if(!(this.approvedAmountForm.get('dealer').value === "")) {
      dealer = this.approvedAmountForm.get('dealer').value.value;
    } 

    this.approvedService.getApprovedAmount(dealer, dateIni, dateEnd).subscribe(data => {
      this.approved = data;
      this.data = data.approved;
      this.dataTotals = data.totals;
      
      if(this.data !== null && this.data.length > 0) {
        this.btnDisabled = false;
      } else {
        this.btnDisabled = true;
      }
      
      this.loading = false;

      if(this.data.length > 0){
        this.messageService.add({key: 'tst', severity:'info', summary:'¡Exitoso!', detail:'Busqueda de montos'});
      } else {
        this.messageService.add({key: 'tst', severity:'warn', summary:'¡Nota!', detail:'No se encontro ningun reclamo en la consulta'});
      }
    });
  }

  onSelectDate() {
    const dateIni = new Date(this.approvedAmountForm.get('dateIni').value);
    const dateEnd = new Date(this.approvedAmountForm.get('dateEnd').value);
    if(dateIni.valueOf() && dateEnd.valueOf()) {
      if(!(dateIni.getTime() < dateEnd.getTime())) {
          this.messageService.add({key: 'tst', severity:'warn', summary:'Warning', detail: 'La fecha de Inicio no debe ser mayor a la fecha final'});
      }
    } 
  }

  sendApproved(individualApproved ?: ApprovedAmount) {
    this.blockedDocument = true;
    let dateIni = this.datePipe.transform(this.approvedAmountForm.get('dateIni').value, 'yyyy/MM/dd');
    let dateEnd = this.datePipe.transform(this.approvedAmountForm.get('dateEnd').value, 'yyyy/MM/dd');
    let total: TotalsAmounts = this.dataTotals.find(data => data.title === 'TOTAL');
    let dealer: number = 0;
    let dataId: number[] = [];
    
    if(individualApproved) {
      dataId.push(individualApproved.id);
      this.approvedService.getTotalById(individualApproved.id).subscribe(totalResponse => {
        total = totalResponse.totals.find(data => data.title === 'TOTAL');
      });
    } else {
      this.data.forEach(data => {
        dataId.push(data.id);
      }); 
    }
    
    if(!(this.approvedAmountForm.get('dealer').value === "")) {
      dealer = this.approvedAmountForm.get('dealer').value.value;
    } 

    this.approvedService.approvedAmount(dealer, dateIni, dateEnd, total.total, dataId)
    .subscribe(data => {
      this.blockedDocument = false;
      this.getApprovedAmounts();
      this.messageService.add({key: 'tst', severity:'success', summary:'¡Exitoso!', detail:'Se ha aprobado exitosamente'});
    }, (error) => {
      this.blockedDocument = false;
      this.messageService.add({key: 'tst', severity:'error', summary:'ERROR!', detail:error});
    });    
  }

  unapproveDialog(approved: ApprovedAmount) {
    this.display = true;
    this.msgDialog = approved.claim;
    this.unapprovedAmount = approved;
  }

  closeDialogUnapprove() {
    this.display = false; 
    this.unapprovedAmount = null;
    this.textAreaModel = null;
  }

  unapproved() {
    if (this.unapprovedAmount) {
      this.blockedDocument = true;
      this.approvedService.deleteApprovedAmount(this.unapprovedAmount.id, this.textAreaModel).subscribe(data => {
        this.closeDialogUnapprove();
        this.messageService.add({key: 'tst', severity:'success', summary:'¡Nota!', detail:'El reclamo no a sido aprobado.'});
        this.getApprovedAmounts();
        this.blockedDocument = false;
      });
    }
  }

  onHold(amount: ApprovedAmount) {
    if (amount) {
      this.blockedDocument = true;
      this.approvedService.onHoldWarranty(amount).subscribe(data => {
        this.messageService.add({key: 'tst', severity:'info', summary:'¡Nota!', detail:'El reclamo no a sido puesto en OnHold.'});
        this.getApprovedAmounts();
        this.blockedDocument = false;
      });
    }
  }

  reactive(amount: ApprovedAmount) {
    if (amount) {
      this.blockedDocument = true;
      this.approvedService.reactiveWarranty(amount).subscribe(data => {
        this.messageService.add({key: 'tst', severity:'info', summary:'¡Nota!', detail:'El reclamo no a sido reactivado.'});
        this.getApprovedAmounts();
        this.blockedDocument = false;
      });
    }
  }

  removeApproveAmount(approved: ApprovedAmount) {
    let msg = approved.claim + ' ' + approved.model + ' ' + approved.description;
    this.confirmationService.confirm({
        message: '¿Deseas eliminar el reclamo ' + msg + '?',
        header: 'Eliminar el siguiente reclamo',
        rejectButtonStyleClass: 'p-button-danger',
        acceptButtonStyleClass: 'p-button-success',
        acceptLabel: 'aceptar',
        rejectLabel: 'cancelar',
        accept: () => {
            
        }
    });
  }

  editApprovedAmount(approved: ApprovedAmount) {
    let msg = approved.claim + ' ' + approved.model + ' ' + approved.description;
    this.confirmationService.confirm({
        message: '¿Deseas mover al siguiente mes el reclamo ' + msg + '?',
        header: 'Mover de mes el siguiente reclamo',
        rejectButtonStyleClass: 'p-button-danger',
        acceptButtonStyleClass: 'p-button-success',
        acceptLabel: 'aceptar',
        rejectLabel: 'cancelar',
        accept: () => {
            this.approvedService.updateApprovedAmount(approved).subscribe(data => {
                this.messageService.add({key: 'tst', severity:'success', summary:'¡Exitoso!', detail:'El monto aprobado ha sido movido al siguiente mes.'});
                this.getApprovedAmounts();
            });
        }
    });

  }

}
