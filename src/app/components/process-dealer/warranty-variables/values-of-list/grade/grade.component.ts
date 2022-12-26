import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { GradeType } from 'src/app/model/grade-type.model';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { GradeTypeService } from 'src/app/services/grade-type.service';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';

@Component({
  selector: 'app-grade',
  templateUrl: './grade.component.html'
})
export class GradeComponent implements OnInit {

  heardes: HeaderTable[];
  grade: GradeType[] = [];
  typeAccion: boolean;
  title: string;
  stateOptions: any[];
  datosForm: FormGroup;
  display: boolean;
  validations = [];
  loading: boolean;

  constructor(private fomb: FormBuilder, private messages: AppValidationMessagesService, private msjeService: MessageService, private gradeTypeService: GradeTypeService) { }

  ngOnInit(): void {
    this.heardes = [
      { label: 'Tipo de grado', SortableColumn: 'Tipo de grado' },
      { label: 'Descripción', SortableColumn: 'Descripción' },
      { label: 'Estatus', SortableColumn: 'Estatus' },
      { label: 'Acciones', SortableColumn: 'Acciones' }
    ]
    this.getGradeType();
    this.getValidations();
    this.formData();
    this.stateOptions = [{ label: 'Inactivo', value: false }, { label: 'Activo', value: true }];
  }

  addGrade() {
    this.datosForm.reset();
    this.display = true;
    this.typeAccion = true;
    this.title = 'Agregar grado';
    this.datosForm.patchValue({status: true});
  }

  editGrade(grade: GradeType) {
    this.title = 'Modificar grado';
    this.display = true;
    this.typeAccion = false;
    this.datosForm.patchValue({
      id: grade.id,
      type: grade.grade,
      description: grade.description,
      status: grade.status
    });
  }

  updateStatus(grade: GradeType) {
    this.gradeTypeService.changeStatusGradeType(grade).subscribe(value => {
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
      this.reloadForm();
    }, err => {
      this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar el grado' });
    });
  }

  getGradeType() {
    this.loading = true;
    this.gradeTypeService.getGradeType(null).subscribe(value => {
      this.grade = value;
      this.loading = false;
    });
  }

  saveGradeType() {
    if (this.datosForm.valid) {
      let grade: GradeType = {
        id: this.datosForm.get('id').value,
        grade: this.datosForm.get('type').value,
        description: this.datosForm.get('description').value,
        status: this.datosForm.get('status').value
      }
      if (this.typeAccion === true) {
        this.gradeTypeService.saveGradeType(grade).subscribe(resp => {
          this.reloadForm();
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
        }, err => {
          this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'El grado ya existe' });
        });
      } else {
        this.gradeTypeService.updateGradeType(grade).subscribe(resp => {
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
          this.reloadForm();
        }, err => {
          this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar el grado' });
        });
      }
    }
  }

  reloadForm() {
    this.getGradeType();
    this.display = false;
    this.datosForm.reset();
  }

  formData() {
    this.datosForm = this.fomb.group({
      id: [''],
      type: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(3)]],
      description: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(60)]],
      status: ['']
    });
  }

  getValidations() {
    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '40';
    this.validations.push(this.messages.getValidationMessagesWithName('type'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '60';
    this.validations.push(this.messages.getValidationMessagesWithName('description'));
  }

}
