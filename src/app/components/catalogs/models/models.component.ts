import { MessageService, SelectItem } from 'primeng/api';
import { ModelService } from './../../../services/model.service';
import { Models } from './../../../model/models.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit, ViewChild } from '@angular/core';
import { HeaderTable } from '../../../model/headerTable.model';
import { AppValidationMessagesService } from '../../../utils/app-validation-messages.service';
import { CountryService } from 'src/app/services/country.service';
import { ResponseMicro } from 'src/app/model/response.micro.model';
import { ModelMicro } from 'src/app/model/model.micro';

@Component({
  selector: 'app-models',
  templateUrl: './models.component.html'
})
export class ModelsComponent implements OnInit {

  heardes: HeaderTable[];
  models: Models[] = [];
  cityOrigin: SelectItem[] = [];
  plantOrigin: SelectItem[] = [];
  validations = [];
  selectCountry: any;
  selectPlant: any;
  title: string;
  display: boolean;
  typeAccion: boolean;
  datosForm: FormGroup;
  stateOptions: any[];
  loading: boolean;
  regexNotSpecialChar: string = '^[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9]+[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9\\s]*$';
  regexNotSpecialCharModel: string = '^[a-zA-Z0-9\\s]*$';
  regexChar: string = '^[a-zA-Z]+[a-zA-Z\\s]*$';
  regexNumeric: string = '^[0-9]+$';
  btnSave: boolean = true;

  constructor(private modelServ: ModelService, private formb: FormBuilder,
    private msjeService: MessageService, private messages: AppValidationMessagesService, private countryService: CountryService) { }

  ngOnInit(): void {
    this.formData();
    this.getValidations();
    this.getAllModels();    
    this.getAllPlants();

    this.heardes = [
      { label: 'Modelo Comercial', SortableColumn: 'Modelo Comercial' },
      { label: 'Modelo', SortableColumn: 'Modelo' },
      { label: 'Código', SortableColumn: 'Código' },
      { label: 'Año', SortableColumn: 'Año' },
      { label: 'VDS', SortableColumn: 'VDS' },
      { label: 'Tipo', SortableColumn: 'Tipo' },
      { label: 'Planta', SortableColumn: 'Planta' },
      { label: 'Estatus', SortableColumn: 'Estatus' },
      { label: 'Acciones', SortableColumn: 'Acciones' },
    ]
    this.stateOptions = [{label: 'Inactivo', value: false}, {label: 'Activo', value: true}];
  }

  getAllModels() {
    this.loading = true;
    this.modelServ.getAllModels().subscribe((resp: any) => {
      this.models = resp;
      this.loading = false;
    })
  }

  getAllCountries() {
    this.countryService.getAllCountries().subscribe((resp: any) => {
      this.cityOrigin = resp.map(r => (
        { label: r.name, value: r.id }
      ))
    });
  }

  getAllPlants() {
    this.modelServ.getAllPlants().subscribe((resp: any) => {
      this.plantOrigin = resp.map(r => (
        { label: r.name, value: r.id }
      ))
    });
  }

  addModel() {
    this.title = 'Agregar modelo';
    this.display = true;
    this.typeAccion = true;
    this.datosForm.reset();
    this.datosForm.patchValue({status: true});
  }

  editModel(models: Models) {
    this.title = 'Editar modelo';
    this.btnSave = false;
    this.display = true;
    this.typeAccion = false;
    this.selectCountry = this.cityOrigin.find(x => x.value === models.countryOrigin.id);
    this.selectPlant = this.plantOrigin.find(x => x.value === models.plant.id);

    this.datosForm.patchValue({
      id: models.id,
      name: models.name,
      model: models.model,
      codModel: models.codModel,
      segment: models.segment,
      year: models.year,
      vds: models.vds,
      type: models.type,
      countryOrigin: this.selectCountry,
      plant: this.selectPlant,
      status: models.status,
      cchp: models.cchp,
      color: models.color
    });
    this.datosForm.markAllAsTouched();
  }

  saveModel() {
    if (this.datosForm.valid === true) {
      let modelSave: Models = {
        id: this.datosForm.get('id').value,
        name: this.datosForm.get('name').value,
        model: this.datosForm.get('model').value,
        codModel: this.datosForm.get('codModel').value,
        segment: this.datosForm.get('segment').value,
        year: this.datosForm.get('year').value,
        vds: this.datosForm.get('vds').value,
        type: this.datosForm.get('type').value,
        countryOrigin: {
          id: 0,
          name: ''
        }, 
        plant: {
          id: this.datosForm.get('plant').value.value,
          name: this.datosForm.get('plant').value.label,
        },
        status: this.datosForm.get('status').value,
        cchp: this.datosForm.get('cchp').value,
        color: this.datosForm.get('color').value,
      }
      let modelMicro = new ModelMicro();
      modelMicro.description = modelSave.segment;
      modelMicro.model = modelSave.model;
      modelMicro.origin = modelSave.plant.name;
      modelMicro.vds = modelSave.vds;
      modelMicro.anio = modelSave.year;
      modelMicro.cchp = modelSave.cchp;
      modelMicro.color = modelSave.color;

      this.modelServ.saveModelMicroserviceManagebd(modelMicro).subscribe(data => {        
      });
      this.modelServ.saveModel(modelSave).subscribe(resp => {
        this.getAllModels();
        this.display = false;
        this.datosForm.reset();
        if(this.typeAccion){
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
        }else{
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
        }
      });
    }
  }

  updateStatus(model: Models){
    this.modelServ.updateStatus(model).subscribe((resp: any) => {
      this.getAllModels();
        this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
    }, err => {
        this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ERROR!', detail: `${err}` });
    });
  }

  formData() {
    this.datosForm = this.formb.group({
      id: [''],
      name: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50), Validators.pattern(this.regexNotSpecialCharModel)]],
      model: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(50)]],
      codModel: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(4), Validators.pattern(this.regexNotSpecialChar)]],
      segment: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50), Validators.pattern(this.regexNotSpecialChar)]],
      year: ['', [Validators.required, Validators.minLength(4), Validators.maxLength(4), Validators.pattern(this.regexNumeric)]],
      vds: ['', [Validators.minLength(1), Validators.maxLength(5), Validators.pattern(this.regexNotSpecialChar)]],
      type: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(4)]],
      plant: ['', [Validators.required]],
      cchp: ['', Validators.pattern(this.regexNumeric)],
      color: ['', [Validators.pattern(this.regexNotSpecialChar)]],
      status: ['']
    });
  }

  getValidations() {
    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '50';
    this.messages.messagesMinLenght = '1';
    this.messages.messagesPattern = 'Nombre invalido';
    this.validations.push(this.messages.getValidationMessagesWithName('name'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '50';
    this.messages.messagesMinLenght = '1';
    this.validations.push(this.messages.getValidationMessagesWithName('model'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '4';
    this.messages.messagesMinLenght = '3';
    this.messages.messagesPattern = 'Codigo invalido';
    this.validations.push(this.messages.getValidationMessagesWithName('codModel'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '50';
    this.messages.messagesMinLenght = '3';
    this.messages.messagesPattern = 'Segmento invalido';
    this.validations.push(this.messages.getValidationMessagesWithName('segment'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '4';
    this.messages.messagesMinLenght = '4';
    this.messages.messagesPattern = 'Año invalido';
    this.validations.push(this.messages.getValidationMessagesWithName('year'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '4';
    this.messages.messagesMinLenght = '2';
    this.validations.push(this.messages.getValidationMessagesWithName('type'));
    
    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '5';
    this.messages.messagesMinLenght = '1';
    this.messages.messagesPattern = 'Vds invalido';
    this.validations.push(this.messages.getValidationMessagesWithName('vds'));

    this.messages.messagesRequired = 'true';
    this.validations.push(this.messages.getValidationMessagesWithName('plant'));

    this.messages.messagesPattern = 'Ingresar solo números';
    this.validations.push(this.messages.getValidationMessagesWithName('cchp'));

    this.messages.messagesPattern = 'Ingresar solo números y letras';
    this.validations.push(this.messages.getValidationMessagesWithName('color'));
  }

  searchModel() {
    let model: string = this.datosForm.get('model').value;
    this.modelServ.getModelBd(model).subscribe(data => {
      if(!data) {
        this.modelServ.getModelMicroservice(model).subscribe(data => {
          if(!data.data) {
            this.modelServ.getModelMicroserviceManagebd(model).subscribe(data => {
                this.setData(data);
                this.btnSave = false;
                this.msjeService.add({ key: 'tst', severity: 'warn', summary: '¡Modelo no existe en las bases de datos!', detail: 'Si deseas registrar el modelo completa la información requerida!' });
            });
          } else {
            this.setData(data);
            this.btnSave = false;
          }     
        });
      } else {
        this.btnSave = true;
        this.msjeService.add({ key: 'tst', severity: 'warn', summary: '¡Modelo ya existe!', detail: 'El modelo ya existe' });
      }
    });
  }

  setData(data: ResponseMicro) {
    if(data.data !== null) {
      this.datosForm.get('vds').setValue(data.data.vds);
      this.datosForm.get('year').setValue(data.data.anio);
      this.datosForm.get('segment').setValue(data.data.description);
      this.datosForm.get('cchp').setValue(data.data.cchp);
      this.datosForm.get('color').setValue(data.data.color);
      this.selectPlant = this.plantOrigin.find(x => x.label === data.data.origin);
      this.datosForm.get('plant').setValue(this.selectPlant);
      this.datosForm.get('vds').disable();
      this.datosForm.get('year').disable();
      this.datosForm.get('segment').disable();
      this.datosForm.get('plant').disable();
      this.datosForm.get('status').disable();
    }
  }
}
