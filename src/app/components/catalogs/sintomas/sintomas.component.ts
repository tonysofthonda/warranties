import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators, FormControl } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { Sintoma } from 'src/app/model/sintoma.model';
import { SintomaService } from 'src/app/services/sintoma.service';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';

@Component({
    selector: 'app-sintomas',
    templateUrl: './sintomas.component.html',
    providers: [SintomaService]
})
export class SintomasComponent implements OnInit {

    display: boolean;
    heardes: HeaderTable[];
    sintoma: Sintoma[] = [];
    validations = [];
    datosForm: FormGroup;
    title: string = '';
    loadingSympton: boolean;
    typeAccion = true;
    stateOptions: any[];
    regexNotSpecialChar: string = '^[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9]+[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9\\s]*$';
    
    constructor(private sintService: SintomaService, private formb: FormBuilder,
        private msjeService: MessageService, public messages: AppValidationMessagesService) {
    }

    ngOnInit(): void {
        this.getSintomas();
        this.formData();
        this.validationForm();
        this.heardes = [
            { label: 'Código', SortableColumn: 'Código'},
            { label: 'Descripción inglés', SortableColumn: 'Descripción inglés' },
            { label: 'Descripción español', SortableColumn: 'Descripción español' },
            { label: 'Estatus', SortableColumn: 'Estatus' },
            { label: 'Acciones', SortableColumn: 'Acciones' },
        ]
        this.stateOptions = [{ label: 'Inactivo', value: false }, { label: 'Activo', value: true }];
    }

    getSintomas() {
        this.loadingSympton = true;
        this.sintService.getAllSintomas().subscribe((resp: any) => {
            this.sintoma = resp;
            this.loadingSympton = false;
        })
    }

    addSintoma() {
        this.title = 'Agregar síntoma';
        this.typeAccion = true;
        this.display = true;
        this.datosForm.controls["code"].enable();
        this.datosForm.reset();
        this.datosForm.patchValue({status: true});
    }

    editSintoma(sintoma: Sintoma) {
        this.typeAccion = false;
        this.datosForm.controls["code"].disable();
        this.display = true;
        this.title = 'Editar síntoma';
        this.datosForm.setValue({
            id: sintoma.id,
            code: sintoma.code,
            descriptionEng: sintoma.descriptionEng,
            descriptionSpa: sintoma.descriptionSpa,
            status: sintoma.status
        });
        this.datosForm.markAllAsTouched();
    }

    updateStatus(sintoma: Sintoma) {
        if (sintoma.id != null) {
            this.sintService.updateStatusSintoma(sintoma).subscribe((resp: any) => {
                this.reloadInformation();
                this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
            }, err => {
                this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ERROR!', detail: `${err}` });
            });
        }
    }

    saveSintoma(form: any) {
        let sintmaNew: Sintoma = {
            id: this.datosForm.get('id').value,
            code: this.datosForm.get('code').value,
            descriptionEng: this.datosForm.get('descriptionEng').value,
            descriptionSpa: this.datosForm.get('descriptionSpa').value,
            status: this.datosForm.get('status').value
        };
        if (this.typeAccion) {

            this.sintService.getByCode(sintmaNew.code).subscribe(resp => {
                if(resp) {
                    this.msjeService.add({ key: 'tst', severity: 'warn', summary: '¡Nota!', detail: `Ya existe un síntoma registrado con el código ${sintmaNew.code}` });
                } else {
                    this.sintService.saveSintoma(sintmaNew).subscribe(resp => {
                        this.reloadInformation();
                        this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
                    }, err => {
                        this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al guardar el síntoma' });
                    });
                }
            }, error => {
                this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: error.error });
            });

            
        } else {
            this.sintService.updateSaveSintoma(sintmaNew).subscribe(resp => {
                this.reloadInformation();
                this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
            }, err => {
                this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al actualizar el síntoma' });
            });
        }
    }

    reloadInformation() {
        this.display = false
        this.getSintomas();
    }

    formData() {
        this.datosForm = this.formb.group({
            id: [''],
            code: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(6)]],
            descriptionEng: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100), Validators.pattern(this.regexNotSpecialChar)]],
            descriptionSpa: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(100), Validators.pattern(this.regexNotSpecialChar)]],
            status: ['']
        });
    }

    validationForm() {
        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '6';
        this.messages.messagesMinLenght = '1';
        this.validations.push(this.messages.getValidationMessagesWithName('code'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '100';
        this.messages.messagesMinLenght = '3';
        this.messages.messagesPattern = 'Descripción no valida';
        this.validations.push(this.messages.getValidationMessagesWithName('descriptionEng'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMaxLenght = '100';
        this.messages.messagesMinLenght = '3';
        this.messages.messagesPattern = 'Descripción no valida';
        this.validations.push(this.messages.getValidationMessagesWithName('descriptionSpa'));
    }

}
