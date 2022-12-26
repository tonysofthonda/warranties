import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, MessageService, SelectItem } from 'primeng/api';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { HistoryApprovedAmount } from 'src/app/model/history.approved.amount.model';
import { HistoryDetailApproved } from 'src/app/model/history.detail.approved.model';
import { ApprovedAmountService } from 'src/app/services/approved.amount.service';
import { DealerService } from 'src/app/services/dealer.service';
import { PeriodService } from 'src/app/services/period.service';
import * as Excel from 'exceljs/dist/exceljs.min.js';
import * as fs from 'file-saver';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';

@Component({
  selector: 'app-history-approved-amounts',
  templateUrl: './history-approved-amounts.component.html',
  providers: [ConfirmationService, MessageService]
})
export class HistoryApprovedAmountsComponent implements OnInit {

  heardes: HeaderTable[];
  heardesDetail: HeaderTable[];
  dataDetail: HistoryDetailApproved[] = [];
  data: HistoryApprovedAmount[] = [];
  historyApprovedAmountForm: FormGroup;
  displayDetail: boolean = false;
  validations = [];
  dealerSelect: SelectItem[] = [];
  periodSelect: SelectItem[] = [];
  statusItems: SelectItem[] = [];
  loading: boolean;

  constructor(private approvedService: ApprovedAmountService,
    private formb: FormBuilder,
    private dealerServ: DealerService,
    private periodServ: PeriodService,
    private messages: AppValidationMessagesService) { }

  ngOnInit(): void {
      this.statusItems = [{label: '', value: ''}, {label: '', value: ''}, {label: '', value: ''}]

    this.heardes = [
        { label: 'Monto', SortableColumn: 'amount' },
        { label: 'No. Reclamo', SortableColumn: 'claim' },
        { label: 'Vin', SortableColumn: 'vin' },
        { label: 'Fecha de Inicio', SortableColumn: 'dateIni' },
        { label: 'Estatus', SortableColumn: 'status' },
        { label: 'Periodo', SortableColumn: 'period' },
        { label: 'Acciones', SortableColumn: 'acciones' },
      ];

      this.heardesDetail = [
        { label: 'Numero de parte', SortableColumn: 'partNumber' },
        { label: 'Cantidad', SortableColumn: 'quantity' },
        { label: 'Monto', SortableColumn: 'amount' },
        { label: 'Número de reclamo', SortableColumn: 'claimNumber' },
        { label: 'Descripción', SortableColumn: 'description' },
        { label: 'PU', SortableColumn: 'pu' },
        { label: 'Tiempo Labor', SortableColumn: 'laborTime' },
        { label: 'IVA', SortableColumn: 'iva' },
        { label: 'Estatus', SortableColumn: 'status' }
      ];

      this.historyApprovedAmountForm = this.formb.group({
          vin:  ['', [Validators.maxLength(17)]],
          claimNumber:  ['', [Validators.maxLength(13)]],
          dealer:  ['', [Validators.maxLength(5)]],
          dateOrder:  [''],
          status:  [''],
          period:  ['']
      });

      this.getAllPeriods();
      this.getAllDealer();
      this.getHistoryApproved();
  }

  getHistoryApproved() {
    this.loading = true;
    this.approvedService.getHistoryApprovedAmount().subscribe(data => {
        this.data = data;
        this.loading = false;
    });
  }

  historyDetail() {
    this.displayDetail = true;

    this.approvedService.getHistoryDetailApprovedAmount().subscribe(data => {
        this.dataDetail = data;
    });
  }

  getAllDealer() {
    this.dealerServ.getAllDealers().subscribe((data: any) => {
        this.dealerSelect = data.map(r => (
            { label: r.name, value: r.id }
          ));
    })
  }

  getAllPeriods() {
    this.periodServ.getAllPeriods().subscribe((data: any) => {
      this.periodSelect = data.map(r => (
        { label: r.periodName, value: r.id }
      ));;
    });
  }

  downloadExcel() {
    let workbook = new Excel.Workbook();
    let worksheet = workbook.addWorksheet('Facturas Canceladas');

    worksheet.columns = [
      { key: 'invoiceCancel', header: 'Model', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'invoiceNew', header: 'Vin', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'cancellationType', header: 'Fecha de Orden', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'cancellationDate', header: 'Estatus', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'reason', header: 'Periodo Fecha Inicial', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'noteSeries: ', header: 'Periodo Fecha Final', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'noteFolio: ', header: 'Número de Parte', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'stampDate: ', header: 'Descripción', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'uuid: ', header: 'FRT', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'manualInvoice: ', header: 'Cantidad', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'manualInvoice: ', header: 'Costo Pieza Unitaria', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'manualInvoice: ', header: 'Costo desembarque', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'manualInvoice: ', header: 'Tiempo Labor', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'manualInvoice: ', header: 'Monto', center: { left:0.5, top:0.5 }, width: 20 },
      { key: 'manualInvoice: ', header: 'Estatus', center: { left:0.5, top:0.5 }, width: 20 }
    ];

    /*data.forEach(data => {
      worksheet.addRow(Object.values(data));
    });*/

    worksheet.getCell('A1').fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'525558'}, bgColor:{argb:'525558'} };
    worksheet.getCell('A1').font = { color: { argb: 'FFFFFFFF' }, size: 12 };
    worksheet.getCell('A1').alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell('B1').fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'525558'} };
    worksheet.getCell('B1').font = { color: { argb: 'FFFFFFFF' }, size: 12 };
    worksheet.getCell('B1').alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell('C1').fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'525558'} };
    worksheet.getCell('C1').font = { color: { argb: 'FFFFFFFF' }, size: 12 };
    worksheet.getCell('C1').alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell('D1').fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'525558'} };
    worksheet.getCell('D1').font = { color: { argb: 'FFFFFFFF' }, size: 12 };
    worksheet.getCell('D1').alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell('E1').fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'525558'} };
    worksheet.getCell('E1').font = { color: { argb: 'FFFFFFFF' }, size: 12 };
    worksheet.getCell('E1').alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell('F1').fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'525558'} };
    worksheet.getCell('F1').font = { color: { argb: 'FFFFFFFF' }, size: 12 };
    worksheet.getCell('F1').alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell('G1').fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'525558'} };
    worksheet.getCell('G1').font = { color: { argb: 'FFFFFFFF' }, size: 12 };
    worksheet.getCell('G1').alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell('H1').fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'525558'} };
    worksheet.getCell('H1').font = { color: { argb: 'FFFFFFFF' }, size: 12 };
    worksheet.getCell('H1').alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell('I1').fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'525558'} };
    worksheet.getCell('I1').font = { color: { argb: 'FFFFFFFF' }, size: 12 };
    worksheet.getCell('I1').alignment = { vertical: 'middle', horizontal: 'center' };
    worksheet.getCell('J1').fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'525558'} };
    worksheet.getCell('J1').font = { color: { argb: 'FFFFFFFF' }, size: 12 };
    worksheet.getCell('J1').alignment = { vertical: 'middle', horizontal: 'center' };

    workbook.xlsx.writeBuffer().then((data) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, `Excel Historico de Montos.xlsx`);
    });

  }

  getValidations() {
    this.messages.messagesMaxLenght = '17';
    this.validations.push(this.messages.getValidationMessagesWithName('vin'));

    this.messages.messagesMaxLenght = '13';
    this.validations.push(this.messages.getValidationMessagesWithName('claimNumber'));

    this.messages.messagesMaxLenght = '5';
    this.validations.push(this.messages.getValidationMessagesWithName('dealer'));
  }
}
