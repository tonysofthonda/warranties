import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService, SelectItem } from 'primeng/api';
import { Defects } from 'src/app/model/defects.model';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { PartMotorcycle } from 'src/app/model/part-motorcycle.model';
import { PartsDefectsSymptoms } from 'src/app/model/parts-defects-symptoms.model';
import { Sintoma } from 'src/app/model/sintoma.model';
import { DefectService } from 'src/app/services/defect.service';
import { PartsDefectsSymptomsService } from 'src/app/services/parts-defects-symptoms.service';
import { PartsMotorcycleService } from 'src/app/services/parts-motorcycle.service';
import { SintomaService } from 'src/app/services/sintoma.service';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';

@Component({
  selector: 'app-symptoms-defects',
  templateUrl: './symptoms-defects.component.html',
  styleUrls: ['./symptoms-defects.component.scss']
})
export class SymptomsDefectsComponent implements OnInit {

  dataForm: FormGroup;
  updateForm: FormGroup;
  showPart: Boolean;
  showDefect: Boolean;
  showSymptom: Boolean;
  heardes: HeaderTable[];
  pds: PartsDefectsSymptoms[] = [];
  partDrown: SelectItem[] = [];
  defectDrown: SelectItem[] = [];
  sympDrown: SelectItem[] = [];
  title: String;
  titleLabel1: String;
  titleLabel2: String;
  display: boolean;
  displayEdit: boolean;
  hiddenCode: boolean;
  modalForm: FormGroup;
  defectForm: FormGroup;
  stateOptions: any[];
  loading: boolean;
  validations = [];
  regexNotSpecialChar: string = '^[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9]+[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9\\s]*$';;

  constructor(private fomb: FormBuilder, private partsDefectsSymptomsService: PartsDefectsSymptomsService, private formb: FormBuilder,
    private partsMotorcycleService: PartsMotorcycleService, private defectService: DefectService, private msjeService: MessageService,
    private sintService: SintomaService, private messages: AppValidationMessagesService) { }

  ngOnInit(): void {
    this.heardes = [
      { label: 'Parte', SortableColumn: 'Parte' },
      { label: 'Defecto', SortableColumn: 'Defecto' },
      { label: 'Código', SortableColumn: 'Código' },
      { label: 'Síntoma', SortableColumn: 'Síntoma' },
      { label: 'Estatus', SortableColumn: 'Estatus' },
      { label: 'Acciones', SortableColumn: 'Acciones' }
    ];
    this.validationForm();
    this.formData();
    this.formUpdateData();
    this.getInformation();
    this.formPartData();
    this.getPartsMotorcycle();
    this.getDefects();
    this.getSymptom();
    this.stateOptions = [{ label: 'Inactivo', value: false }, { label: 'Activo', value: true }];
  }

  getInformation() {
    this.loading = true;
    this.partsDefectsSymptomsService.getAllRelated().subscribe(value => {
      this.pds = value;
      this.loading = false;
    });
  }

  addPart() {
    this.formPartData2();
    this.title = 'Agregar parte';
    this.titleLabel1 = "Nombre de parte (En inglés)";
    this.titleLabel2 = "Nombre de parte (En español)";
    this.display = true;
    this.hiddenCode = true;
    this.modalForm.reset();
    this.modalForm.patchValue({status: true});
  }

  addDefect() {
    this.formPartData2();
    this.title = 'Agregar defecto';
    this.titleLabel1 = "Nombre del defecto (En inglés)";
    this.titleLabel2 = "Nombre del defecto (En español)";
    this.display = true;
    this.hiddenCode = true;
    this.modalForm.reset();
    this.modalForm.patchValue({status: true});
  }

  addSymptom() {
    this.title = 'Agregar síntoma';
    this.titleLabel1 = "Descripción (En inglés)";
    this.titleLabel2 = "Descripción (En español)";
    this.display = true;
    this.modalForm.reset();
    this.hiddenCode = false;
    this.modalForm.patchValue({status: true});
  }

  saveModal() {
    if (this.title === 'Agregar parte') {
      this.savePart();
    }

    if (this.title === 'Agregar defecto') {
      this.saveDefect();
    }

    if (this.title === 'Agregar síntoma') {
      this.saveSymptom();
    }
  }

  savePart() {
    if (this.modalForm.valid === true) {
      let partsM: PartMotorcycle = {
        id: this.modalForm.get('id').value,
        partNameEnglish: this.modalForm.get('partNameEnglish').value,
        partNameSpanish: this.modalForm.get('partNameSpanish').value,
        status: this.modalForm.get('status').value,
      }
      this.partsMotorcycleService.savePartMotorcycle(partsM).subscribe(value => {
        this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
        this.reloadPart();
      }, err => {
        this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al guardar la parte' });
      });
    }
  }

  saveDefect() {
    let defectNew: Defects = {
      id: null,
      descriptionEng: this.modalForm.get('partNameEnglish').value,
      descriptionSpa: this.modalForm.get('partNameSpanish').value,
      status: this.modalForm.get('status').value,
    }
    this.defectService.saveNewDefect(defectNew).subscribe(resp => {
      this.reloadDefect()
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
    }, err => {
      this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'El defecto ya existe' });
    });
  }

  saveSymptom() {
    let sintmaNew: Sintoma = {
      code: this.modalForm.get('code').value,
      descriptionEng: this.modalForm.get('partNameEnglish').value,
      descriptionSpa: this.modalForm.get('partNameSpanish').value,
      status: this.modalForm.get('status').value,
    };
    this.sintService.saveSintoma(sintmaNew).subscribe(resp => {
      this.reloadSymptom();
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
    }, err => {
      this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al guardar el síntoma' });
    });
  }

  reloadDefect() {
    this.getDefects();
    this.display = false;
    this.modalForm.reset();
  }

  reloadPart() {
    this.getPartsMotorcycle();
    this.display = false;
    this.modalForm.reset();
  }

  reloadSymptom() {
    this.getSymptom();
    this.display = false;
    this.modalForm.reset();
  }

  getPartsMotorcycle() {
    this.partsMotorcycleService.getPartsMotorcycle().subscribe(value => {
      this.partDrown = value.map(r => (
        { label: r.partNameEnglish, value: r.id }
      ));
    });
  }

  getDefects() {
    this.defectService.getAllDefects().subscribe((resp: any) => {
      this.defectDrown = resp.map(r => (
        { label: r.descriptionEng, value: r.id }
      ));
    });
  }

  getSymptom() {
    this.sintService.getAllSintomas().subscribe((resp: any) => {
      this.sympDrown = resp.map(r => (
        { label: r.descriptionEng, value: r.id }
      ));
    })
  }

  addRelated() {
    let selectPart = this.partDrown.find(x => x.label == this.dataForm.get('part').value.label);
    let selectDedect = this.defectDrown.find(x => x.label == this.dataForm.get('defect').value.label);
    let selectSymptom = this.sympDrown.find(x => x.label == this.dataForm.get('symptom').value.label);

    let partMotorcycle: PartMotorcycle = {
      id: selectPart.value,
      partNameEnglish: selectPart.label,
      partNameSpanish: null,
      status: null,
    };

    let defect: Defects = {
      id: selectDedect.value,
      descriptionEng: selectDedect.label,
      descriptionSpa: null,
      status: null,
    };

    let symptom: Sintoma = {
      id: selectSymptom.value,
      code: '',
      descriptionEng: selectSymptom.label,
      descriptionSpa: null,
      status: null
    };

    let related: PartsDefectsSymptoms = {
      id: null,
      partMotorcycle: partMotorcycle,
      defects: defect,
      symptom: symptom,
      status: true
    }

    this.partsDefectsSymptomsService.addRelated(related).subscribe(value => {
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
      this.getInformation();
      this.dataForm.reset();
    }, err => {
      this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Es posible que la relación ya exista' });
    });
  }

  updateStatus(pds: PartsDefectsSymptoms){
    this.partsDefectsSymptomsService.changeStatusRelated(pds).subscribe(value =>{
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
      this.getInformation();
    }, err => {
      this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar la relación' });
    });
  }

  editRelated(pds: PartsDefectsSymptoms) {   
    this.title = 'Editar combinación';
    let selectPart = this.partDrown.find(x => x.value == pds.partMotorcycle.id);
    let selectDedect = this.defectDrown.find(x => x.value == pds.defects.id);
    let selectSymptom = this.sympDrown.find(x => x.value == pds.symptom.id);
    this.displayEdit = true;
    this.updateForm.setValue({
      id: pds.id,
      updatePart: selectPart,
      updateDefect: selectDedect,
      updateSymptom: selectSymptom,
      status: pds.status
    });
    this.updateForm.markAllAsTouched();
  }
  
  saveEditRelated(){
    let selectPart = this.partDrown.find(x => x.label == this.updateForm.get('updatePart').value.label);
    let selectDedect = this.defectDrown.find(x => x.label == this.updateForm.get('updateDefect').value.label);
    let selectSymptom = this.sympDrown.find(x => x.label == this.updateForm.get('updateSymptom').value.label);

    let partMotorcycle: PartMotorcycle = {
      id: selectPart.value,
      partNameEnglish: selectPart.label,
      partNameSpanish: null,
      status: null,
    };

    let defect: Defects = {
      id: selectDedect.value,
      descriptionEng: selectDedect.label,
      descriptionSpa: null,
      status: null,
    };

    let symptom: Sintoma = {
      id: selectSymptom.value,
      code: '',
      descriptionEng: selectSymptom.label,
      descriptionSpa: null,
      status: null
    };

    let related: PartsDefectsSymptoms = {
      id: this.updateForm.get('id').value,
      partMotorcycle: partMotorcycle,
      defects: defect,
      symptom: symptom,
      status: this.updateForm.get('status').value,
    }

    this.partsDefectsSymptomsService.updateRelated(related).subscribe(value =>{
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
      this.getInformation();
      this.updateForm.reset();
      this.displayEdit=false;
    }, err => {
      this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar la relación' });
    });
    
  }

  formUpdateData() {
    this.updateForm = this.fomb.group({
      id: [''],
      updatePart: ['', [Validators.required, Validators.pattern(this.regexNotSpecialChar)]],
      updateDefect: ['', [Validators.required, Validators.pattern(this.regexNotSpecialChar)]],
      updateSymptom: ['', [Validators.required, Validators.pattern(this.regexNotSpecialChar)]],
      status: [''],
    });
  }

  formData() {
    this.dataForm = this.fomb.group({
      id: [''],
      part: ['', [Validators.required]],
      defect: ['', [Validators.required]],
      symptom: ['', [Validators.required]],
    });
  }

  formPartData() {
    this.modalForm = this.formb.group({
      id: [''],
      code: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(255), Validators.pattern(this.regexNotSpecialChar)]],
      partNameEnglish: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(255), Validators.pattern(this.regexNotSpecialChar)]],
      partNameSpanish: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(255), Validators.pattern(this.regexNotSpecialChar)]],
      status: ['']
    });
  }

  formPartData2() {
    this.modalForm = this.formb.group({
      id: [''],
      code: ['', [Validators.minLength(1), Validators.maxLength(255), Validators.pattern(this.regexNotSpecialChar)]],
      partNameEnglish: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(255), Validators.pattern(this.regexNotSpecialChar)]],
      partNameSpanish: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(255), Validators.pattern(this.regexNotSpecialChar)]],
      status: ['']
    });
  }

  validationForm() {    
    this.messages.messagesRequired = 'true';
    this.messages.messagesPattern = 'Nombre no valido';
    this.validations.push(this.messages.getValidationMessagesWithName('partNameEnglish'));

    this.messages.messagesRequired = 'true';   
    this.messages.messagesPattern = 'Nombre no valido'; 
    this.validations.push(this.messages.getValidationMessagesWithName('partNameSpanish'));

    this.messages.messagesRequired = 'true';   
    this.messages.messagesPattern = 'Codigo no valido'; 
    this.validations.push(this.messages.getValidationMessagesWithName('code'));

    this.messages.messagesRequired = 'true';   
    this.messages.messagesPattern = 'Parte no valida'; 
    this.validations.push(this.messages.getValidationMessagesWithName('part'));

    this.messages.messagesRequired = 'true';   
    this.messages.messagesPattern = 'Defecto no valido'; 
    this.validations.push(this.messages.getValidationMessagesWithName('defect'));

    this.messages.messagesRequired = 'true';   
    this.messages.messagesPattern = 'Síntoma no valido'; 
    this.validations.push(this.messages.getValidationMessagesWithName('symptom'));

    this.messages.messagesRequired = 'true';   
    this.messages.messagesPattern = 'Parte no valida'; 
    this.validations.push(this.messages.getValidationMessagesWithName('updatePart'));

    this.messages.messagesRequired = 'true';   
    this.messages.messagesPattern = 'Defecto no valido'; 
    this.validations.push(this.messages.getValidationMessagesWithName('updateDefect'));

    this.messages.messagesRequired = 'true';   
    this.messages.messagesPattern = 'Síntoma no valido'; 
    this.validations.push(this.messages.getValidationMessagesWithName('updateSymptom'));
  }
}
