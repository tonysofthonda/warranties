import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

//Service
import { CreateServiceService } from 'src/app/services/create.service.service';

//Model
import { ConsultService } from 'src/app/model/consult.service.model';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { HistoryService } from 'src/app/model/history-service.model';

//Uitilities
import * as Excel from 'exceljs/dist/exceljs.min.js';
import * as fs from 'file-saver';

//PrimeNG
import { ConfirmationService, MessageService } from 'primeng/api';
import { PrimeIcons, MenuItem } from "primeng/api";
import { ServicesService } from 'src/app/services/services.service';
import { Services } from 'src/app/model/Services.model';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-consult-service',
  templateUrl: './consult-service.component.html',
  providers: [ConfirmationService, MessageService]
})
export class ConsultServiceComponent implements OnInit {

  consultHeader: HeaderTable[];
  consultService: ConsultService[] = [];
  services: Services[] = [];
  abs: string[] = ['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y'];
  searchForm: FormGroup;
  isVisibleHistory: boolean;
  events1: HistoryService[];
  historyService: HistoryService[] = [];
  historyHeader: HeaderTable[];
  loading: boolean;

  constructor(private formb: FormBuilder, private createService: CreateServiceService, private confirmationService: ConfirmationService,
              private messageService: MessageService, private servicesService: ServicesService, private route: Router, private datePipe: DatePipe) { }

  ngOnInit(): void {

    this.historyHeader = [
      { label: "Distribuidor", SortableColumn: "daler" },
      { label: "No. Servicio", SortableColumn: "serviceNumber" },
      { label: "Descripcion", SortableColumn: "description" },
      { label: "Fecha", SortableColumn: "date" },
    ];

    this.consultHeader = [
      { label: 'Número De Orden', SortableColumn: 'orderNumber' },
      { label: 'Fecha De Creación', SortableColumn: 'dateRegister' },
      { label: 'Número De Distribuidor', SortableColumn: 'dealerNum' },
      { label: 'Nombre De Distribuidor', SortableColumn: 'dealerName' },
      { label: 'Elaborado Por', SortableColumn: 'reportedBy' },
      { label: 'Cliente', SortableColumn: 'customerName' },
      { label: 'VIN', SortableColumn: 'vin' },
      { label: 'Estatus', SortableColumn: 'status' },
      { label: 'Acciones', SortableColumn: 'acciones' }
    ];

    // this.getAllServiceCreated();
    this.initForm();
  }

  clearInputs() {
    this.searchForm.reset();
    this.services = [];
  }

  getServiceConsult() {
    this.loading = true;
    let search = this.searchForm.get('search').value;
    let creationDate = this.datePipe.transform(this.searchForm.get('creationDate').value, 'yyyy/MM/dd');
    this.servicesService.getServicesConsult(search, creationDate).subscribe((servicesResponse) => {
      console.log(servicesResponse)
      this.loading = false;
      this.services = servicesResponse;
    }, (error) => {
        this.loading = false;
        this.messageService.add({
            key: "tst",
            severity: "error",
            summary: "Error",
            detail: error.error,
        });
    });
  }

  getAllServiceCreated() {
    this.loading = true;
    // this.createService.getAllServiceCreated().subscribe(allServicrCreated => {
    //     this.consultService = allServicrCreated;
    //     this.loading = false;
    // });
    this.servicesService.getAll().subscribe((servicesResponse) => {
      if(servicesResponse) {
        this.services = servicesResponse;
        this.loading = false;
      } else {
        this.messageService.add({
          key: "tst",
          severity: "warn",
          summary: "Nota",
          detail: `No se encontro ningun servicio creado.`,
      }); 
      }
    }, (error => {
      this.messageService.add({
        key: "tst",
        severity: "error",
        summary: "Error",
        detail: error.error,
    });
    }))
  }

  initForm() {
    this.searchForm = this.formb.group({
      search: [{ value: '', disabled: false }],
      creationDate: [{ value: new Date(), disabled: false },]
    });
  }

  editService(service: Services) {
    // this.route.navigate(['create-service', { id: service.idService, action: 'edit' }]);
  }

  deleteService(consultService: ConsultService) {
    this.confirmationService.confirm({
        message: '¿Deseas eliminar  la orden de servicio ?',
        header: 'Eliminación orden de servicio',
        rejectButtonStyleClass: "p-button-danger",
        acceptButtonStyleClass: "p-button-success",
        acceptLabel: "aceptar",
        rejectLabel: "cancelar",
        accept: () => {
           this.consultService.forEach((consultServiceTemp,index)=>{
            if(consultServiceTemp == consultService) this.consultService.splice(index,1);
        });
        }
    });
  }

  viewHistory(consultService : ConsultService) {
    this.getHistory(consultService.vin)
  }

  getHistory(vin: string) {
    this.createService.getHistoryService(vin).subscribe((history) => {
        this.historyService = history;
        this.fillHistory(history)
    });
  }

  fillHistory(history: HistoryService[]) {
    let historyArray: any[] = [];
    history.forEach(function(record) {
        let newRecord  = {
            daler: record.dealerNo,
            date: record.dateService,
            serviceNumber: record.serviceNo,
            description: record.description,         
            icon: PrimeIcons.COG,
            color: "#673AB7"
        };
        historyArray.push(newRecord);
    }); 
    this.events1 = historyArray;
    this.isVisibleHistory = true;
}

  updateService(consultService: ConsultService) {
    consultService.isView = 'update';
    this.route.navigate(['create-service', consultService]);
  }

  viewService(consultService: ConsultService) {
    consultService.isView = 'view';
    this.route.navigate(['create-service', consultService]);
  }

  redirectAddService() {
      this.route.navigate(['/create-service']);
  }

  downloadExcel() {
    let workbook = new Excel.Workbook();
    let accepted = workbook.addWorksheet('1.0 - Aceptados');

    const keys = ['vin', 'serviceNumber', 'dealer', 'km', 'dateService', 'createDate', 'status'];

    const headers = ['No. Servicio', 'VIN', 'Dealer', 'Kilometraje', 'Fecha de Servicio', 'Fecha Generación', 'Estatus'];


    const data2 = [];
    for (let i = 0; i < keys.length; i++) {
      data2.push({ key: keys[i], header: headers[i], center: { left:0.5, top:0.5 }, width: 20 });
    }
    accepted.columns = data2;

    this.consultService.forEach(record => {
      const reco = {'vin': record.serviceNumber, 'serviceNumber': record.vin, 'dealer': record.dealer,
                      'km': record.km, 'dateService': record.dateService, 'createDate': record.createDate, 'status': record.status};
      accepted.addRow(Object.values(reco));
    });

    for (let i = 0; i < keys.length; i++) {
      accepted.getCell(this.abs[i].concat('1')).fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'000000'}, bgColor:{argb:'525558'} };
      accepted.getCell(this.abs[i].concat('1')).font = { color: { argb: 'FFFFFFFF' }, size: 12 };
      accepted.getCell(this.abs[i].concat('1')).alignment = { vertical: 'middle', horizontal: 'center' };
      
    }
      workbook.xlsx.writeBuffer().then((data) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, `SERVICES_REPORT.xlsx`);
    });
  }
}
