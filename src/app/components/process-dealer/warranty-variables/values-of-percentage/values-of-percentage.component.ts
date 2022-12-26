import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Setting } from 'src/app/model/setting.model';
import { SettingsService } from 'src/app/services/settings.service';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';

@Component({
  selector: 'app-values-of-percentage',
  templateUrl: './values-of-percentage.component.html',
  styleUrls: ['./values-of-percentage.component.scss']
})
export class ValuesOfPercentageComponent implements OnInit {

  dolarForm: FormGroup;
  shippingForm: FormGroup;
  taxForm: FormGroup;
  updateTaxInformation: String;
  updateCostInformation: String;
  updateDolarInformation: String;
  title: string;
  typeUpdate: string;
  display: boolean;
  datosForm: FormGroup;
  validations = [];
  labelText: String;

  constructor(private fomb: FormBuilder, private settingsService: SettingsService, private messages: AppValidationMessagesService, private msjeService: MessageService) { }

  ngOnInit(): void {
    this.formDataDolar();
    this.getDolarInformation();
    this.getCostInformation();
    this.getTaxInformation();
    this.formData();
  }

  getDolarInformation() {
    let name = "dolar";
    this.settingsService.getSettings(name).subscribe(value => {
      this.dolarForm.get('id').setValue(value[0].id);
      this.dolarForm.get('value').setValue(value[0].valueNumber);
      this.updateDolarInformation = "Última actualización " + value[0].lastUpdate
    });
  }

  getCostInformation() {
    let name = "shipping_cost";
    this.settingsService.getSettings(name).subscribe(value => {
      this.shippingForm.get('id').setValue(value[0].id);
      this.shippingForm.get('value').setValue(value[0].valueNumber);
      this.updateCostInformation = "Última actualización " + value[0].lastUpdate
    });
  }

  getTaxInformation() {
    let name = "tax";
    this.settingsService.getSettings(name).subscribe(value => {
      this.taxForm.get('id').setValue(value[0].id);
      this.taxForm.get('value').setValue(value[0].valueNumber);
      this.updateTaxInformation = "Última actualización " + value[0].lastUpdate
    });
  }

  formDataDolar() {
    this.dolarForm = this.fomb.group({
      id: [''],
      value: ['']
    });

    this.shippingForm = this.fomb.group({
      id: [''],
      value: ['']
    });

    this.taxForm = this.fomb.group({
      id: [''],
      value: ['']
    });
  }

  formData() {
    this.datosForm = this.fomb.group({
      id: [''],
      value: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(8)]]
    });
  }

  updateDolarInfo() {
    this.title = "Actualizar precio del dolar";
    this.labelText = "Precio del dolar (Moneda nacional): $";
    this.typeUpdate = 'dolar';
    this.datosForm.reset();
    this.datosForm.get('id').setValue(this.dolarForm.get('id').value);
    this.datosForm.get('value').setValue(this.dolarForm.get('value').value);
    this.display = true;
  }

  updateCostInfo() {
    this.title = "Actualizar costo de embarque";
    this.labelText = "Porcentaje de costo de embarque: %";
    this.typeUpdate = 'shipping_cost';
    this.datosForm.reset();
    this.datosForm.get('id').setValue(this.shippingForm.get('id').value);
    this.datosForm.get('value').setValue(this.shippingForm.get('value').value);
    this.display = true;
  }

  updateTaxInfo() {
    this.title = "Actualizar porcentaje de IVA";
    this.labelText = "Porcentaje de IVA: %";
    this.typeUpdate = 'tax';
    this.datosForm.reset();
    this.datosForm.get('id').setValue(this.taxForm.get('id').value);
    this.datosForm.get('value').setValue(this.taxForm.get('value').value);
    this.display = true;
  }

  saveValue() {
    if (this.datosForm.valid) {

      if (this.typeUpdate === 'dolar') {
        let setting = this.createType(this.datosForm.get('id').value, 'dolar', this.datosForm.get('value').value);
        this.updateInfor(setting);
        setTimeout(() => {
          this.getDolarInformation();           
        }, 100);
      }

      if (this.typeUpdate === 'shipping_cost') {
        let setting = this.createType(this.datosForm.get('id').value, 'shipping_cost', this.datosForm.get('value').value);
        this.updateInfor(setting); 
        setTimeout(() => {
          this.getCostInformation();
        }, 100);
      }

      if (this.typeUpdate === 'tax') {
        let setting = this.createType(this.datosForm.get('id').value, 'tax', this.datosForm.get('value').value);
        this.updateInfor(setting);        
        setTimeout(() => {
          this.getTaxInformation();
        }, 100);        
      }

    }
  }

  reloadDialog(){
    this.datosForm.reset();
    this.display = false;
  }

  updateInfor(setting: Setting){
    this.settingsService.updateSetting(setting).subscribe(value => {
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });      
      this.reloadDialog();
    }, err => {
      this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar el parámetro' });
    });
  }

  createType(id: number, name: string, value: number): Setting {
    let setting: Setting = {
      id: id,
      name: name,
      valueNumber: value,
      valueText: null,
      lastUpdate: null
    }
    return setting;
  }

}
