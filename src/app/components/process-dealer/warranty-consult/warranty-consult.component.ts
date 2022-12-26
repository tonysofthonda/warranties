import { DatePipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

//Service
import { WarrantyClaimsService } from 'src/app/services/warranty-claims.service';

//Model
import { HeaderTable } from 'src/app/model/headerTable.model';
import { WarrantyConsult } from 'src/app/model/warranty.consult';

//PrimeNG
import { ConfirmationService, MessageService, SelectItem, ConfirmEventType } from 'primeng/api';

//Util
import * as Excel from 'exceljs/dist/exceljs.min.js';
import * as fs from 'file-saver';

@Component({
  selector: 'app-warranty-consult',
  templateUrl: './warranty-consult.component.html',
  providers: [ConfirmationService, MessageService]
})
export class WarrantyConsultComponent implements OnInit {

  heardes: HeaderTable[];
  data: WarrantyConsult[];
  dataViewOnTable: WarrantyConsult[];
  datosForm: FormGroup;
  statusDrown: SelectItem[] = [];
  abs: string[] = ['A','B','C','D','E','F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'AA', 'AB'];
  loading: boolean;
  btdDownloadExcel: boolean = true;
  blockedDocument: boolean = false;

  constructor(private warranty: WarrantyClaimsService,
    private formBuilder: FormBuilder,
    private datePipe: DatePipe,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private route: Router) { }

  ngOnInit(): void {

    this.statusDrown = [{label: 'Draft', value: 'Draft'},
                        {label: 'Enviado', value: 'Enviado'}]

    this.heardes = [
      { label: 'Número de Reclamo', SortableColumn: 'claimNo' },
      { label: 'Nombre Cliente', SortableColumn: 'client' },
      { label: 'Número de orden de servicio', SortableColumn: 'serviceNo' },
      { label: 'Número de VIN', SortableColumn: 'vin' },
      { label: 'Fecha de creación de reporte', SortableColumn: 'dateCreate' },
      { label: 'Estatus', SortableColumn: 'status' },
      { label: 'Comentarios', SortableColumn: 'comments' },
      { label: 'Acciones', SortableColumn: 'acciones' },
    ];

    this.datosForm = this.formBuilder.group({
      dateIni: ['', [Validators.required]],
      dateEnd: ['', [Validators.required]],
      search: ['', [Validators.required]]
    });
  }

  getWarrantyConsult() {
    this.loading = true;
    this.data = [];
    this.dataViewOnTable = []; 
    let search = this.datosForm.get('search').value;
    let dateIni = this.datePipe.transform(this.datosForm.get('dateIni').value, 'yyyy/MM/dd');
    let dateEnd = this.datePipe.transform(this.datosForm.get('dateEnd').value, 'yyyy/MM/dd');

    this.warranty.getWarrantiesClaimConsult(search, dateIni , dateEnd).subscribe(data => {
      if(!data || data.length > 0) {
        this.btdDownloadExcel = false;
        this.data = data;
        const setObj = new Set();
        this.dataViewOnTable = data.reduce((acc, consult) => {
          if (!setObj.has(consult.claimNo)) {
            setObj.add(consult.claimNo)
            acc.push(consult)
          }
          return acc;
        }, []);
        this.loading = false;
        this.messageService.add({key: 'tst', severity:'success', summary:'¡Exitoso!', detail: 'Busqueda exitosa.'});
      } else {
        this.btdDownloadExcel = true;
        this.loading = false;
        this.messageService.add({key: 'tst', severity:'info', summary:'Nota!', detail: `No se encontro ningun registro con ${search}.`});
      }
      
    }, err => {
      this.messageService.add({key: 'tst', severity:'error', summary:'error', detail: 'Error al buscar la información'});
    });
  }

  clearInputs() {
    this.datosForm.reset();
    this.data = [];
    this.dataViewOnTable = [];
    this.btdDownloadExcel = true;
  }

  downloadExcel() {
    let workbook = new Excel.Workbook();
    let accepted = workbook.addWorksheet('');
    
    // const keys = ['claimNo', 'client', 'serviceNo', 'vin', 'dateCreate', 'status'];

    // const headers = ['Número de Reclamo', 'Nombre del cliente', 'Número de orden de servicio', 'Número de vin',
    //                  'Fecha de creación de reporte', 'Estatus'];
    const keys = ['dealerNo', 'client', 'report', 'model', 'dealerName', 'vin', 'motor', 'sale', 'fail', 'repair',
          'km', 'causalPart', 'descriptionPart', 'reportSymptom', 'descriptionDefect', 'code', 'frt',
          'amountPesos', 'usd', 'spareparts', 'unship', 'labor', 'responseDate', 'status', 'close',
          'samples', 'note', 'comments'];

    const headers = ['Dealer', 'Nombre del cliente', 'Reporte', 'Modelo', 'Proveedor', 'Vin', 'Motor', 'Venta', 'Falla', 'Repara',
                     'Kilometraje', '#Parte causal', 'Descripción de parte', 'Síntoma reportado', 'Descripción del defecto', 
                     'Código', 'Frt', 'Monto en pesos', 'Dolar', 'Refacciones', 'Desembarque',
                     'Labor', 'Fecha de respuesta','Estatus', 'Cierre', 'Muestras', 'Notas', 'Comentarios'];

    const data2 = [];
    for (let i = 0; i < headers.length; i++) {
      if(i == 4 || i == 5 || i == 3) {
        data2.push({ key: keys[i], header: headers[i], center: { left:0.5, top:0.5 }, width: headers[i].length * 5 });
      } else {
        data2.push({ key: keys[i], header: headers[i], center: { left:0.5, top:0.5 }, width: headers[i].length * 2 });
      }
    }
    accepted.columns = data2;

    this.data.forEach(data => {
      // accepted.addRow(Object.values({claimNo: data.claimNo, client: data.clientName, serviceNo: data.serviceNo, 
      //                                vin: data.vin, dateCreate: data.dateCreate, status: data.status}));
      accepted.addRow(Object.values({'dealerNo': data.dealerNo, 'client': data.clientName, 'report': data.report, 'model': data.model, 'dealerName': data.dealerName, 
      'vin': data.vin, 'motor': data.motor,'sale': data.sale, 'fail': data.fail, 'repair': data.repair, 'km': data.km, 'causalPart': data.causalPart, 
      'descriptionPart': data.descriptionPart, 'reportSymptom': data.reportSymptom, 'descriptionDefect': data.descriptionDefect, 'code': data.code, 
      'frt': data.frt, 'amountPesos': data.amountPesos, 'usd': data.usd, 'spareparts': data.spareparts, 'unship': data.unship, 'labor': data.labor, 
      'responseDate': data.responseDate, 'status': data.status, 'close': data.close, 'samples': data.samples, 'note': data.note, 'comments': data.comments}));
    });

    for (let i = 0; i < headers.length; i++) {
      if(this.abs[i]) {
        accepted.getColumn(this.abs[i]).alignment = { vertical: 'middle', horizontal: 'center', wrapText: false };
        accepted.getColumn(this.abs[i]).font = { size: 8, name: 'Sylfaen' };
        accepted.getCell(this.abs[i].concat('1')).fill = { type: 'pattern', pattern:'solid', fgColor:{argb:'000000'}, bgColor:{argb:'525558'} };
        accepted.getCell(this.abs[i].concat('1')).font = { color: { argb: 'FFFFFFFF' },  };
      }
    }
      workbook.xlsx.writeBuffer().then((data) => {
      const blob = new Blob([data], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
      fs.saveAs(blob, `reporte_reclamo_#concecionaria.xlsx`);
      this.messageService.add({key: 'tst', severity:'success', summary:'¡Exitoso!', detail: 'Descarga exitosa'});
    });
  }

  viewWarranty(consultWarranty : WarrantyConsult) {
    this.route.navigate(['warranty/personalInf', { id: consultWarranty.id }]);
  }

  cancelWarranty(consultWarranty : WarrantyConsult) {
    this.confirmationService.confirm({
      message: `Estas seguro de cancelar el reclamo ${consultWarranty.claimNo}?`,
      header: 'Cancelación De Reclamo',
      icon: 'pi pi-exclamation-triangle',
      rejectButtonStyleClass: 'p-button-danger',
      acceptButtonStyleClass: 'p-button-success',
      acceptLabel: 'aceptar',
      rejectLabel: 'cancelar',
      accept: () => {
        this.blockedDocument = true;
        this.warranty.cancelWarrantyClaim(consultWarranty).subscribe((response) => {
          this.blockedDocument = false;
          this.getWarrantyConsult();
          this.messageService.add({key: 'tst', severity:'info', summary:'Cancelado', detail: `El reclamo ${consultWarranty.claimNo} a sido cancelado`});
        });
      },
      reject: (type) => {
      }
    });
  }

  onSelectDate() {
    const dateIni = new Date(this.datosForm.get('dateIni').value);
    const dateEnd = new Date(this.datosForm.get('dateEnd').value);
    if(this.datosForm.valid) {
      if(dateIni.getTime() < dateEnd.getTime()) {
      } else {
          this.messageService.add({key: 'tst', severity:'warn', summary:'Warning', detail: 'La fecha de Inicio no debe ser mayor a la fecha final'});
      }
    } 
  }

}
