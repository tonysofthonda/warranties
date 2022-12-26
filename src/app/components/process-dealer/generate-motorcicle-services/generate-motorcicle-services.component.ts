import { Component, OnInit } from '@angular/core';

@Component({
    selector: 'app-generate-motorcicle-services',
    templateUrl: './generate-motorcicle-services.component.html'
})
export class GenerateMotorcicleServicesComponent implements OnInit {

    setImfo = true;
    constructor() { }

    ngOnInit(): void {
    }

    confirmData() {
        this.setImfo = false;
    }

}
