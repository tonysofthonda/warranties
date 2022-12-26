import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Campaign } from 'src/app/model/campaign.model';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { GeneralReportsService } from 'src/app/services/general.reports.service';
import * as Excel from 'exceljs/dist/exceljs.min.js';
import * as fs from 'file-saver';

@Component({
  selector: 'app-campaign-report',
  templateUrl: './campaign-report.component.html',
  providers: [ConfirmationService, MessageService]
})
export class CampaignReportComponent implements OnInit {

  display: boolean = false;
  files: any[] = [];
  file: File;
  heardes: HeaderTable[];
  campaignData: Campaign[] = [];
  campaignForm: FormGroup;
  displayEdit: boolean = false;
  abs: string[] = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y'];
  loading: boolean;

  constructor(private generalReportsService: GeneralReportsService,
              private confirmationService: ConfirmationService,
              private messageService: MessageService,
              private formBuilder: FormBuilder,
              private datePipe: DatePipe) { }

  ngOnInit(): void {
    this.heardes = [
        { label: 'COD. CMPAÑA', SortableColumn: 'code' },
        { label: 'NO. BOLETIN', SortableColumn: 'bulletinNumber' },
        { label: 'TITULO DE BOLETIN -CAMPAÑA-', SortableColumn: 'bulletinTitle' },
        { label: 'MODELO', SortableColumn: 'model' },
        { label: 'AÑO', SortableColumn: 'year' },
        { label: 'VIN', SortableColumn: 'vin' },
        { label: 'LOCALIZACIÓN / CLIENTE', SortableColumn: 'client' },
        { label: 'FECHA DE VENTA', SortableColumn: 'saleDate' },
        { label: 'FECHA DE INSP/REP', SortableColumn: 'repairDate' },
        { label: 'STATUS DE CAMPAÑA', SortableColumn: 'status' },
        { label: 'Nota', SortableColumn: 'note' },
        { label: 'Acciones', SortableColumn: 'acciones' },
      ];

      this.campaignForm = this.formBuilder.group({
        id: [''],
        code: ['', [Validators.required]],
        bulletinNumber: ['', [Validators.required]],
        bulletinTitle: ['', [Validators.required]],
        model: ['', [Validators.required]],
        year: ['', [Validators.required]],
        vin: ['', [Validators.required]],
        client: ['', [Validators.required]],
        saleDate: ['', [Validators.required]],
        repairDate: ['', [Validators.required]],
        status: ['', [Validators.required]],
        note: ['', [Validators.required]]
      });

      this.getCampaign();
  }

  getCampaign() {
    this.loading = true;
    this.generalReportsService.getReportUnitsCampign().subscribe(data => {
        this.campaignData = data;
        this.loading = false;
    });
  }


  uploadExcel() {
    this.display = true;
  }

  confirmUpdateSaved(event: any, formFiles: any) {
    if (event.files && event.files.length > 0) {
        this.file = event.files[0];
      }

      if (null != this.file && this.file.size > 0) {
        const formData = new FormData();
        formData.append('file', this.file);

        this.generalReportsService.postSaveReport(formData).subscribe((response) => {
            this.messageService.add({key: 'tst', severity: 'success', detail: 'Se guardaron correctamente los registros', summary: '¡Exitoso!'});
        });
      } else {
        this.messageService.add({ key: 'tst', severity: 'error', summary: 'Error en el archivo', detail: 'Error' });
      }
  }

  deleteCampaign(campaign: Campaign) {
    this.confirmationService.confirm({
        message: '¿Deseas eliminar el siguiente registro?',
        header: 'Eliminar el registro',
        rejectButtonStyleClass: "p-button-danger",
        acceptButtonStyleClass: "p-button-success",
        acceptLabel: "aceptar",
        rejectLabel: "cancelar",
        accept: () => {
            this.generalReportsService.deleteRecord(campaign.id).subscribe(data => {
                this.messageService.add({key: 'tst', severity: 'success', detail: 'Se eliminó correctamente el registro.', summary: '¡Exitoso!'});
            });
        }
    });
  }

  editRecord(campaign: Campaign) {
      this.displayEdit = true;
      this.campaignForm.setValue(campaign);
  }

  updateCampaign() {
      this.campaignForm.get('saleDate').setValue(this.datePipe.transform(this.campaignForm.get('saleDate').value, 'dd/MMM/yyyy'));
      this.campaignForm.get('repairDate').setValue(this.datePipe.transform(this.campaignForm.get('repairDate').value, 'dd/MMM/yyyy'));
      const campaign : Campaign = this.campaignForm.value;
      this.generalReportsService.putUpdateReport(campaign).subscribe(data => {
        this.messageService.add({key: 'tst', severity: 'success', detail: 'Se actualizo correctamente el registro.', summary: '¡Exitoso!'});
        this.displayEdit = false;
        this.getCampaign();
      });
  }

  downloadExcelCampaign(data: Campaign[]) {
    let workbook = new Excel.Workbook();
    let campaign = workbook.addWorksheet('CONCENTRADO MC');

    const keys = ['code', 'bulletinNumber', 'bulletinTitle', 'model', 'year', 'vin', 'client', 'saleDate',
                  'repairDate', 'status', 'note', 'amount', 'costPieceUnit', 'costUnship', 'timeLabor', 'total',
                  'status'];

    const headers = ['COD. CMPAÑA', 'NO. BOLETIN', 'TITULO DE BOLETIN -CAMPAÑA-', 'MODELO',	'AÑO', 'VIN',
                     'LOCALIZACIÓN / CLIENTE', 'FECHA DE VENTA', 'FECHA DE INSP/REP', 'STATUS DE CAMPAÑA', 'Nota'];


    const data2 = [];
    for (let i = 0; i < keys.length; i++) {
      data2.push({ key: keys[i], header: headers[i], center: { left:0.5, top:0.5 }, width: 20 });
    }
    campaign.columns = data2;


    data.forEach(record => {
        const reco = {'code': record.code, 'bulletinNumber': record.bulletinNumber, 'bulletinTitle': record.bulletinTitle,
                      'model': record.model, 'year': record.year, 'vin': record.vin, 'client': record.client,
                      'saleDate': record.saleDate, 'repairDate': record.repairDate, 'status': record.status,
                      'note': record.note};
        campaign.addRow(Object.values(reco));
    });

    for (let i = 0; i < 24; i++) {
        campaign.getCell(this.abs[i].concat('1')).fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'000000'}, bgColor:{argb:'C0C0C0'} };
        campaign.getCell(this.abs[i].concat('1')).font = { color: { argb: 'FFFFFFFF' }, size: 10, 'name': 'Arial', bold: true };
        campaign.getCell(this.abs[i].concat('1')).alignment = { vertical: 'middle', horizontal: 'center' };
    }
      workbook.xlsx.writeBuffer().then((data) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, `CONCENTRADO MC.xlsx`);
    });
}
}
