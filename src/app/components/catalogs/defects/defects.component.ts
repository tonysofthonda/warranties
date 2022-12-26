import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import { Defects } from 'src/app/model/defects.model';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { DefectService } from 'src/app/services/defect.service';
import { SintomaService } from 'src/app/services/sintoma.service';
import { AppValidationMessagesService } from 'src/app/utils/app-validation-messages.service';

@Component({
    selector: 'app-defects',
    templateUrl: './defects.component.html'
})
export class DefectsComponent implements OnInit {
    heardes: HeaderTable[];    
    defects: Defects[] = [];    
    validations = [];            
    datosForm: FormGroup;
    display: boolean;    
    typeAccion: boolean;
    title: string;
    stateOptions: any[];
    loadingDefects: boolean;
    regexNotSpecialChar: string = '^[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9]+[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9\\s]*$';;

    constructor(private defectService: DefectService, private sintomaService: SintomaService, private msjeService: MessageService, 
        private formb: FormBuilder, public messages: AppValidationMessagesService) {
    }

    ngOnInit(): void {
        this.validationsForm();
        this.formData();
        this.getDefects();
        this.heardes = [
            { label: 'Nombre del defecto (Inglés)', SortableColumn: 'Nombre del defecto (Inglés)' },
            { label: 'Nombre del defecto (Español)', SortableColumn: 'Nombre del defecto (Español)' },
            { label: 'Estatus', SortableColumn: 'estatus' },
            { label: 'Acciones', SortableColumn: 'acciones' },
        ]
        this.stateOptions = [{ label: 'Inactivo', value: false }, { label: 'Activo', value: true }];
    }

    getDefects() {
        this.loadingDefects = true;
        this.defectService.getAllDefects().subscribe((resp: any) => {
            this.defects = resp;
            this.loadingDefects = false;
        }, err => {
            this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'error al consultar los defectos' });
        });
    }

    addDefect() {
        this.datosForm.reset(); 
        this.datosForm.patchValue({status: true});       
        this.title = 'Agregar defecto';
        this.display = true;
        this.typeAccion = true;
    }

    editDefects(defec: Defects) {
        this.typeAccion = false;        
        this.display = true;
        this.title = 'Editar defecto';
        this.datosForm.patchValue({
            id:defec.id,
            descriptionEng: defec.descriptionEng,
            descriptionSpa: defec.descriptionSpa,
            status: defec.status
        });
        this.datosForm.markAllAsTouched();
    }

    saveDefect() {
        let defectNew: Defects = {
            id: this.datosForm.get('id').value,
            descriptionSpa: this.datosForm.get('descriptionSpa').value,
            descriptionEng: this.datosForm.get('descriptionEng').value,
            status: this.datosForm.get('status').value,
        }
        if (this.typeAccion === true) {            
            this.defectService.saveNewDefect(defectNew).subscribe(resp => {
                this.reloadInformation();
                this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
            }, err => {
                this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'El defecto ya existe' });
            });
        } else if (this.typeAccion === false) {            
            this.defectService.updateSaveDefect(defectNew).subscribe((resp: any) => {
                this.reloadInformation();
                this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
            }, err => {
                this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ERROR!', detail: `${err}` });
            });
        }
    }

    updateStatus(defec: Defects){
        this.defectService.updateStatusDefect(defec).subscribe((resp: any) => {
            this.reloadInformation();
            this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
        }, err => {
            this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ERROR!', detail: `${err}` });
        });
    }

    reloadInformation(){
        this.display = false;
        this.getDefects();
        this.datosForm.reset();
    }

    formData() {
        this.datosForm = this.formb.group({
            id:[''],
            descriptionEng: ['', [Validators.required, , Validators.minLength(3), Validators.maxLength(50), Validators.pattern(this.regexNotSpecialChar)]],
            descriptionSpa: ['', [Validators.required, , Validators.minLength(3), Validators.maxLength(50), Validators.pattern(this.regexNotSpecialChar)]],
            status: ['']
        });
    }

    validationsForm(){
        this.messages.messagesRequired = 'true';
        this.messages.messagesMinLenght = '3';
        this.messages.messagesMaxLenght = '50';
        this.messages.messagesPattern = 'Descripión no valida';        
        this.validations.push(this.messages.getValidationMessagesWithName('descriptionEng'));

        this.messages.messagesRequired = 'true';
        this.messages.messagesMinLenght = '3';
        this.messages.messagesPattern = 'Descripión no valida';  
        this.validations.push(this.messages.getValidationMessagesWithName('descriptionSpa'));
    }

}