import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { GeneralReportsService } from 'src/app/services/general.reports.service';
import * as Excel from 'exceljs/dist/exceljs.min.js';
import * as fs from 'file-saver';
import { TechnicalSummary } from 'src/app/model/technical.summary.model';
import { DatePipe, getLocaleWeekEndRange } from '@angular/common';
import { HistoricaAmountReport } from 'src/app/model/historical.amount.approved.report.model';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'app-general-reports',
  templateUrl: './general-reports.component.html',
  providers: [MessageService]
})
export class GeneralReportsComponent implements OnInit {

  technicalSummaryForm: FormGroup;
  unitsCampign: FormGroup;
  historicalAmounts: FormGroup;
  technicalSummary: TechnicalSummary[] = [];
  historicaAmount: HistoricaAmountReport[] = [];
  btnhistoricalAmounts: boolean = true;
  btnTechnicalSummary: boolean = true;

  abs: string[] = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

  constructor(private formBuilder: FormBuilder,
              private generalReportsService: GeneralReportsService,
              private datePipe: DatePipe,
              private messageService: MessageService) { }

  ngOnInit(): void {
    this.technicalSummaryForm = this.formBuilder.group({
        dateIni:  ['', [Validators.required]],
        dateEnd:  ['', [Validators.required]]
    });

    this.unitsCampign = this.formBuilder.group({
        dateIni:  ['', [Validators.required]],
        dateEnd:  ['', [Validators.required]]
    });

    this.historicalAmounts = this.formBuilder.group({
        dateIni:  ['', [Validators.required]],
        dateEnd:  ['', [Validators.required]]
    });
  }

  getReportTechnicalSummary() {
    let dateIni = this.datePipe.transform(this.technicalSummaryForm.get('dateIni').value, 'dd/MM/yyyy');
    let dateEnd = this.datePipe.transform(this.technicalSummaryForm.get('dateEnd').value, 'dd/MM/yyyy');

    if(this.technicalSummaryForm.valid) {
        this.generalReportsService.getReportTechnicalSummary(dateIni, dateEnd).subscribe(data => {
            this.technicalSummary = data;
            this.downloadExcel(data, dateIni.replace('/','').replace('/',''), dateEnd.replace('/','').replace('/',''));
        });
    } else {
        this.messageService.add({key: 'tst', severity:'error', summary:'Error', detail: 'Debe haber un rago de fechas para la busqueda'});
    }
  }

  getReportUnitsCampign() {

  }

  getReportHistoricalAmounts() {
    let dateIni = this.datePipe.transform(this.historicalAmounts.get('dateIni').value, 'dd/MM/yyyy');
    let dateEnd = this.datePipe.transform(this.historicalAmounts.get('dateEnd').value, 'dd/MM/yyyy');

    if(this.historicalAmounts.valid) {
        this.generalReportsService.getReportHistoricalAmounts(dateIni, dateEnd).subscribe(data => {
            this.historicaAmount = data;

            this.downloadExcelHistoricalApproved(data, dateIni.replace('/','').replace('/',''), dateEnd.replace('/','').replace('/',''));
        });
    } else {
        this.messageService.add({key: 'tst', severity:'error', summary:'Error', detail: 'Debe haber un rago de fechas para la busqueda'});
    }
  }

  downloadExcel(data: any, dateIni: string, dateEnd: string) {
    let workbook = new Excel.Workbook();
    let accepted = workbook.addWorksheet('1.0 - Aceptados');

    const keys = ['deal', 'reporte', 'model', 'proveed', 'vin', 'engine', 'sale', 'fail', 'repair', 'km', 'partCausal', 
                'descriptionPart', 'reportedSymptom', 'defectDescription', 'code', 'frt', 'amountPesos', 
                'usdt', 'spareParts', 'unship', 'labor', 'responseDate', 'status', 'close', 'sample', 'note'];

    const headers = ['DEAL', 'REPORTE', 'MODELO', 'PROVEED', 'VIN', 'MOTOR', 'VENTA', 'FALLA', 'REPARA', 'KILOMETRAJE', '# PARTE CAUSAL',
                     'DESCRIPCIÓN DE PARTE', 'SÍNTOMA REPORTADO POR EL CLIENTE', 'DESCRIPCIÓN DEL DEFECTO', 'CODE', 'FRT', 
                     'MONTO PESOS', 'DÓLAR', 'REFACCIONES', 'DESEMBARQUE', 'LABOR', 'FECHA RESPUESTA', 'STATUS', 'CIERRE', 'MUESTRAS', 'NOTAS'];


    const data2 = [];
    for (let i = 0; i < headers.length; i++) {
      data2.push({ key: keys[i], header: headers[i], center: { left:0.5, top:0.5 }, width: headers[i].length * 2 });
    }
    accepted.columns = data2;

    data.accepted.forEach(data => {
      accepted.addRow(Object.values(data));
    });

    for (let i = 0; i < headers.length; i++) {
      accepted.getColumn(this.abs[i]).alignment = { vertical: 'middle', horizontal: 'center', wrapText: false };
      accepted.getColumn(this.abs[i]).font = { size: 8, name: 'Sylfaen' };
      accepted.getCell(this.abs[i].concat('1')).fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'000000'}, bgColor:{argb:'525558'} };
      accepted.getCell(this.abs[i].concat('1')).font = { color: { argb: 'FFFFFFFF' },  };

    }
      workbook.xlsx.writeBuffer().then((data) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, `TECHNICAL_MC_REPORT_` + dateIni + '_'+ dateEnd + `.xlsx`);
    });
  }

    downloadExcelHistoricalApproved(data: any, dateIni: string, dateEnd: string) {
        let workbook = new Excel.Workbook();
        let accepted = workbook.addWorksheet('Historicos de Montos Aprobados');

        const keys = ['dealerNumber', 'claimNumber', 'model', 'vin', 'dateOrder', 'status', 
                      'partNumber', 'description', 'frt', 'amount', 'costPieceUnit', 'costUnship', 'timeLabor', 'total',
                      'status'];

        const headers = ['Numero de Dealer','Número de reclamo', 'MODELO', 'VIN', 'Fecha de orden', 'Estatus', 'Periodo fecha inicial', 'Periodo fecha final',
                    	'Descripción', 'FRT', 'Cantidad',	'Costo Pieza Unitaria',
                         'Costo Desembarque', 'Tiempo Labor', 'Monto', 'Estatus'];


        const data2 = [];
        for (let i = 0; i < 17; i++) {
          data2.push({ key: keys[i], header: headers[i], center: { left:0.5, top:0.5 }, width: 20 });
        }
        accepted.columns = data2;

        data.forEach(data => {
          accepted.addRow(Object.values(data));
        });

        for (let i = 0; i < 24; i++) {
          accepted.getCell(this.abs[i].concat('1')).fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'000000'}, bgColor:{argb:'525558'} };
          accepted.getCell(this.abs[i].concat('1')).font = { color: { argb: 'FFFFFFFF' }, size: 12 };
          accepted.getCell(this.abs[i].concat('1')).alignment = { vertical: 'middle', horizontal: 'center' };
        }
        workbook.xlsx.writeBuffer().then((data) => {
          const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
          fs.saveAs(blob, `APPROVAL HISTORICAL AMOUNTS_` + dateIni + '_'+ dateEnd + `.xlsx`);
        });
  }

  onSelectHistory() {
    const dateIni = new Date(this.historicalAmounts.get('dateIni').value);
    const dateEnd = new Date(this.historicalAmounts.get('dateEnd').value);
    if(dateIni.getTime() < dateEnd.getTime()) {
        this.btnhistoricalAmounts = false;
    } else {
        this.btnhistoricalAmounts = true;
        this.messageService.add({key: 'tst', severity:'warn', summary:'Cuidado', detail: 'La fecha de Inicio no debe ser mayor a la fecha final'});
    }
  }

  onSelectTechnicalSummary() {
    const dateIni = new Date(this.technicalSummaryForm.get('dateIni').value);
    const dateEnd = new Date(this.technicalSummaryForm.get('dateEnd').value);
    if(dateIni.getTime() < dateEnd.getTime()) {
        this.btnTechnicalSummary = false;
    } else {
        this.btnTechnicalSummary = true;
        this.messageService.add({key: 'tst', severity:'warn', summary:'Cuidado', detail: 'La fecha de Inicio no debe ser mayor a la fecha final'});
    }
  }
}
