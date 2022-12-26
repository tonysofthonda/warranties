import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { ProblemCategory } from 'src/app/model/problem-category.model';
import { ProblemCategoryService } from 'src/app/services/problem-category.service';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';

@Component({
  selector: 'app-problem-type',
  templateUrl: './problem-type.component.html'
})
export class ProblemTypeComponent implements OnInit {

  heardes: HeaderTable[];
  problemCategory: ProblemCategory[] = [];
  typeAccion: boolean;
  title: string;
  stateOptions: any[];
  datosForm: FormGroup;
  display: boolean;
  validations = [];
  loading: boolean;

  constructor(private fomb: FormBuilder, private problemCategoryService:ProblemCategoryService, private messages: AppValidationMessagesService, private msjeService: MessageService) { }

  ngOnInit(): void {
    this.heardes = [
      { label: 'Tipo de problema', SortableColumn: 'Tipo de problema' },
      { label: 'Descripción', SortableColumn: 'Descripción' },
      { label: 'Estatus', SortableColumn: 'Estatus' },
      { label: 'Acciones', SortableColumn: 'Acciones' }
    ]    
    this.getProblemCategory();
    this.formData();
    this.getValidations();
    this.stateOptions = [{ label: 'Inactivo', value: false }, { label: 'Activo', value: true }];
  }

  getProblemCategory(){
    this.loading = true;
    this.problemCategoryService.getProblemCategory(null).subscribe(value =>{      
      this.problemCategory = value;
      this.loading = false;
    });
  }

  addProblem(){
    this.datosForm.reset();
    this.display = true;
    this.typeAccion = true;    
    this.title = 'Agregar tipo de problema';
    this.datosForm.patchValue({status: true});
  }

  editProblem(problemCategory:ProblemCategory) {
    this.title = 'Modificar tipo de problema';
    this.display = true;
    this.typeAccion = false;
    this.datosForm.patchValue({
        id: problemCategory.id,        
        type: problemCategory.problemName,
        description: problemCategory.problemDescription,
        status: problemCategory.status      
    });
  }

  saveProblem(){
    if(this.datosForm.valid){
      let problemType: ProblemCategory = {
        id: this.datosForm.get('id').value,
        problemName: this.datosForm.get('type').value,
        problemDescription: this.datosForm.get('description').value,
        status: this.datosForm.get('status').value,
      }
      if (this.typeAccion === true) {
        this.problemCategoryService.saveProblemCategory(problemType).subscribe(resp => {  
          this.reloadForm();
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
        }, err => {
          this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'El tipo de problema ya existe' });
        }); 
      }else{
        this.problemCategoryService.updateProblemCategory(problemType).subscribe(resp => {
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
          this.reloadForm();
        }, err => {
          this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar el tipo de problema' });
      });
      }
    }  
  }

  updateStatus(problemCategory:ProblemCategory){
    this.problemCategoryService.changeStatusProblemCategory(problemCategory).subscribe(value =>{
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
      this.reloadForm();
    }, err => {
      this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar el tipo de problema' });
    });
  }

  reloadForm(){
    this.getProblemCategory();
    this.display = false;
    this.datosForm.reset();
  }

  formData() {
    this.datosForm = this.fomb.group({
      id: [''],
      type: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(40)]],
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
