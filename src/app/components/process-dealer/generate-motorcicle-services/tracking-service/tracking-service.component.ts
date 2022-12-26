import { AppValidationMessagesService } from './../../../../utils/app-validation-messages.service';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Component, OnInit } from '@angular/core';
import { Header } from 'primeng/api';

@Component({
    selector: 'tracking-service',
    templateUrl: './tracking-service.component.html'
})
export class TrackingServiceComponent implements OnInit {

    datosForm: FormGroup;
    historicServ: any[];
    heardes: Header[];
    validations = [];

    constructor(private fb: FormBuilder, private messages: AppValidationMessagesService) { }

    ngOnInit(): void {
        this.heardes = [
            { field: 'dealer', header: 'Distribuidor' },
            { field: 'noServ', header: 'No. de Servicio' },
            { field: 'details', header: 'Descripcion' },
            { field: 'fecha', header: 'Fecha' },
        ];
        this.validForm();
    }

    saveTracking() { }

    validForm() {
        this.datosForm = this.fb.group({
            creatReport: ['', [Validators.required]],
            noServ: ['', [Validators.required]],
            details: ['', [Validators.required]]
        })
    }

    getValidations() {
        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('creatReport'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('noServ'));

        this.messages.messagesRequired = 'true';
        this.validations.push(this.messages.getValidationMessagesWithName('details'));
    }

}
