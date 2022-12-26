import { Component, OnInit } from '@angular/core';
import { PartMotorcycle } from 'src/app/model/part-motorcycle.model';
import { PartsMotorcycleService } from 'src/app/services/parts-motorcycle.service';
import { HeaderTable } from '../../../model/headerTable.model';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';

@Component({
  selector: 'app-parts-motorcycle',
  templateUrl: './parts-motorcycle.component.html',
  providers:[PartsMotorcycleService]
})
export class PartsMotorcycleComponent implements OnInit {

  heardes: HeaderTable[];
  partMotorcycle: PartMotorcycle[] = [];  
  title :String;
  display: boolean;
  typeAccion: boolean;
  datosForm: FormGroup;
  stateOptions: any[];
  validations = [];
  loading: boolean;
  regexNotSpecialChar: string = '^[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9]+[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9\\s]*$';;

  constructor(private partsMotorcycleService: PartsMotorcycleService, private formb: FormBuilder, private msjeService: MessageService,
    public messages: AppValidationMessagesService) { }

  ngOnInit(): void {    
    this.validationForm();
    this.heardes = [
      { label: 'ID', SortableColumn: 'id' },
      { label: 'Nombre de parte (Inglés)', SortableColumn: 'partNameEnglish' },
      { label: 'Nombre de parte (Español)', SortableColumn: 'partNameSpanish' },      
      { label: 'Estatus', SortableColumn: 'status' },
      { label: 'Acciones', SortableColumn: 'Acciones' },
    ]    
    this.formData();
    this.stateOptions = [{label: 'Inactivo', value: false}, {label: 'Activo', value: true}];
    this.getAllPartsMotorcycle();
    this.title ="Agregar parte";
  }

  getAllPartsMotorcycle() {
    this.loading = true;
    this.partsMotorcycleService.getPartsMotorcycle().subscribe(value => {           
      this.partMotorcycle = value;
      this.loading = false;
    });
  }

  formData() {
    this.datosForm = this.formb.group({
      id: [''],
      partNameEnglish: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255), Validators.pattern(this.regexNotSpecialChar)]],
      partNameSpanish: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(255), Validators.pattern(this.regexNotSpecialChar)]],
      status: ['']
    });
  }

  addPart() {
    this.title = 'Agregar parte';
    this.display = true;
    this.typeAccion = true;
    this.datosForm.reset();
    this.datosForm.patchValue({status: true});
  }

  editPart(part: PartMotorcycle) {
    this.title = 'Editar parte';
    this.display = true;
    this.typeAccion = false;
    this.datosForm.patchValue({
      id: part.id,
      partNameEnglish: part.partNameEnglish,
      partNameSpanish: part.partNameSpanish,
      status: part.status
    });
    this.datosForm.markAllAsTouched();
  }

  savePart() {
    if (this.datosForm.valid === true) {
      let partsM : PartMotorcycle = {
        id: this.datosForm.get('id').value,
        partNameEnglish: this.datosForm.get('partNameEnglish').value,
        partNameSpanish: this.datosForm.get('partNameSpanish').value,
        status: this.datosForm.get('status').value,
      }      
      if(this.typeAccion){
        this.partsMotorcycleService.savePartMotorcycle(partsM).subscribe(value =>{          
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
          this.reloadForm();
        }, err => {
          this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al guardar la parte' });
        });
      }else{        
        this.partsMotorcycleService.updatePartMotorcycle(partsM).subscribe(value =>{                    
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
          this.reloadForm();
        }, err => {
          this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar la parte' });
        });
      }      
    }
  }

  reloadForm() {
    this.getAllPartsMotorcycle();
    this.display = false;
    this.datosForm.reset();    
  }

  updateStatus(part: PartMotorcycle){
    this.partsMotorcycleService.updateStatusPartMotorcycle(part).subscribe(value => {
      this.reloadForm();
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
    }, err => {
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ERROR!', detail: `${err}` });
    });
  }

  validationForm() {    
    this.messages.messagesRequired = 'true';
    this.messages.messagesMinLenght = '3';
    this.messages.messagesMaxLenght = '255';
    this.messages.messagesPattern = 'Parte no valida';
    this.validations.push(this.messages.getValidationMessagesWithName('partNameEnglish'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMinLenght = '3';
    this.messages.messagesMaxLenght = '255';   
    this.messages.messagesPattern = 'Parte no valida'; 
    this.validations.push(this.messages.getValidationMessagesWithName('partNameSpanish'));
}

}
