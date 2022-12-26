import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { TypeClaims } from 'src/app/model/type-claims.model';
import { TypeClaimsService } from 'src/app/services/type-claims.service';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';

@Component({
  selector: 'app-claim-type',
  templateUrl: './claim-type.component.html'
})
export class ClaimTypeComponent implements OnInit {

  heardes: HeaderTable[];
  claimType: TypeClaims[] = [];
  typeAccion: boolean;
  title: string;
  stateOptions: any[];
  datosForm: FormGroup;
  display: boolean;
  loading: boolean;
  validations = [];

  constructor(private fomb: FormBuilder, private typeClaimsService: TypeClaimsService, private messages: AppValidationMessagesService, private msjeService: MessageService) { }

  ngOnInit(): void {
    this.heardes = [
      { label: 'Tipo de reclamo', SortableColumn: 'Tipo de reclamo' },
      { label: 'Descripción', SortableColumn: 'Descripción' },
      { label: 'Estatus', SortableColumn: 'Estatus' },
      { label: 'Acciones', SortableColumn: 'Acciones' }
    ]
    this.stateOptions = [{ label: 'Inactivo', value: false }, { label: 'Activo', value: true }];
    this.getAllClaimTypes();
    this.formData();
    this.getValidations();
  }

  getAllClaimTypes() {
    this.loading = true;
    this.typeClaimsService.getTypeClaims(null).subscribe(value => {
      this.claimType = value;
      this.loading = false;
    });
  }

  addClaim() {
    this.datosForm.reset();
    this.display = true;
    this.typeAccion = true;  
    this.title = 'Agregar tipo de reclamo';
    this.datosForm.patchValue({status: true});
  }

  editClaim(claimType: TypeClaims) {
    this.title = 'Modificar tipo de reclamo';
    this.display = true;
    this.typeAccion = false;
    this.datosForm.patchValue({
      id: claimType.id,
      claimsType: claimType.claimsType,
      claimsDescription: claimType.claimsDescription,
      status: claimType.status
    });
  }

  saveClaim() {
    if (this.datosForm.valid) {
      let claimType: TypeClaims = {
        id: this.datosForm.get('id').value,
        claimsType: this.datosForm.get('claimsType').value,
        claimsDescription: this.datosForm.get('claimsDescription').value,
        status: this.datosForm.get('status').value
      }
      if (this.typeAccion === true) {
        this.typeClaimsService.saveTypeClaims(claimType).subscribe(resp => {
          this.reloadForm();
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
        }, err => {
          this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'El tipo de reclamo ya existe' });
        });
      } else {
        this.typeClaimsService.updateTypeClaims(claimType).subscribe(resp => {
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
          this.reloadForm();
        }, err => {
          this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar el tipo de reclamo' });
        });
      }
    }
  }

  updateStatus(claimType: TypeClaims) {
    this.typeClaimsService.changeStatusTypeClaims(claimType).subscribe(value => {
      this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
      this.reloadForm();
    }, err => {
      this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar el tipo de reclamo' });
    });
  }

  reloadForm() {
    this.getAllClaimTypes();
    this.display = false;
    this.datosForm.reset();
  }

  formData() {
    this.datosForm = this.fomb.group({
      id: [''],
      claimsType: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(40)]],
      claimsDescription: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(60)]],
      status: []
    });
  }

  getValidations() {
    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '40';
    this.validations.push(this.messages.getValidationMessagesWithName('claimsType'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '60';
    this.validations.push(this.messages.getValidationMessagesWithName('claimsDescription'));
  }

}
