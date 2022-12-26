import { PeriodService } from './../../../services/period.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { MessageService, SelectItem } from 'primeng/api';
import { AppValidationMessagesService } from '../../../utils/app-validation-messages.service';
import { Periods } from './../../../model/periods.model';
import { HeaderTable } from '../../../model/headerTable.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';

@Component({
  selector: 'app-periods',
  templateUrl: './periods.component.html'
})
export class PeriodsComponent implements OnInit {

  heardes: HeaderTable[];
  periods: Periods[] = [];
  validations = [];
  minDate: Date = null;
  maxDate: Date;
  invalidDates: Array<Date>;
  datosForm: FormGroup;
  typeAccion: boolean;
  display: boolean;
  title: string;
  checked: boolean = true;
  stateOptions: any[];
  loading: boolean;
  regexPeriod: string = '(((0[123456789]|10|11|12)-(([1][9][0-9][0-9])|([2][0-9][0-9][0-9]))))';
  regexNotSpecialChar: string = '^[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9]+[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9\\s]*$';;

  constructor(private periodServ: PeriodService, private formb: FormBuilder, private msjeService: MessageService,
    private messages: AppValidationMessagesService) { }

  ngOnInit(): void {
    this.formData();
    this.getValidations();
    this.getAllPeriods();
    let today = new Date();
    this.maxDate = new Date();
    this.maxDate.setMonth(today.getMonth() + 3);
    let invalidDate = new Date();
        invalidDate.setDate(today.getDate() - 1);
        this.invalidDates = [today,invalidDate];
    this.heardes = [
      { label: 'Periodo', SortableColumn: 'Periodo' },
      { label: 'Descripción', SortableColumn: 'Descripcion' },
      { label: 'Fecha Inicial', SortableColumn: 'Fecha Inicial' },
      { label: 'Fecha Final', SortableColumn: 'Fecha Final' },
      { label: 'Estatus', SortableColumn: 'Estatus' },
      { label: 'Acciones', SortableColumn: 'Acciones' },
    ]
    this.stateOptions = [{label: 'Inactivo', value: false}, {label: 'Activo', value: true}];
  }

  select(event: Date) {
    let min = new Date();
    min.setDate(event.getDate() + 1);
    min.setMonth(event.getMonth());
    min.setFullYear(event.getFullYear());
    this.minDate = min;
    this.datosForm.get('dateEnd').setValue(null);
    (new Date());
  }

  getAllPeriods() {
    this.loading = true;
    this.periodServ.getAllPeriods().subscribe((resp: any) => {
      this.periods = resp;
      this.loading = false;
    });
  }

  addPeriod() {
    this.title = 'Agregar periodo';
    this.display = true;
    this.typeAccion = true;
    this.datosForm.reset();
    this.datosForm.patchValue({status: true});
  }

  editPeriod(periods: Periods) {
    this.title = 'Editar periodo';
    this.display = true;
    this.typeAccion = false;
    this.datosForm.patchValue({
      id: periods.id,
      period: periods.periodName,
      description: periods.description,      
      dateInit: periods.dateInit,
      dateEnd: periods.dateEnd,
      status: periods.status
    });
    this.datosForm.markAllAsTouched();
  }

  updateStatus(periods: Periods){
    this.periodServ.updateStatus(periods).subscribe(value =>{
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
      this.getAllPeriods();
    }, err => {
        this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ERROR!', detail: `${err}` });
    });
  }

  savePeriod() {
    if (this.datosForm.valid === true) {      
      let periosNew: Periods = {
        id: this.datosForm.get('id').value,
        periodName: this.datosForm.get('period').value,
        description: this.datosForm.get('description').value,
        dateInit: this.datosForm.get('dateInit').value,
        dateEnd: this.datosForm.get('dateEnd').value,
        status: this.datosForm.get('status').value
      }
      
      this.periodServ.savePeriods(periosNew).subscribe(resp => {
        this.getAllPeriods();
        this.display = false;
        this.datosForm.reset();
        if(this.typeAccion){
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
        }else{
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
        }        
      }, err => {
        this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al guardar el periodo' });
      }) 
    }
  }

  formData() {
    this.datosForm = this.formb.group({
      id: [''],
      period: ['', [Validators.required, Validators.minLength(7), Validators.maxLength(7), Validators.pattern(this.regexPeriod)]],
      description: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(1000), Validators.pattern(this.regexNotSpecialChar)]],
      dateInit: ['', [Validators.required]],
      dateEnd: ['', [Validators.required]],
      status: ['']
    });
  }

  getValidations() {
    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '7';
    this.messages.messagesMinLenght = '7';
    this.messages.messagesPattern = 'Periodo invalido (Formato MM-YYYY)';
    this.validations.push(this.messages.getValidationMessagesWithName('period'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '50';
    this.messages.messagesMinLenght = '1';
    this.messages.messagesPattern = 'Descripción no valida';
    this.validations.push(this.messages.getValidationMessagesWithName('description'));

    this.messages.messagesRequired = 'true';
    this.validations.push(this.messages.getValidationMessagesWithName('dateInit'));

    this.messages.messagesRequired = 'true';
    this.validations.push(this.messages.getValidationMessagesWithName('dateEnd'));

    this.messages.messagesRequired = 'true';
    this.validations.push(this.messages.getValidationMessagesWithName('status'));
  }
}
