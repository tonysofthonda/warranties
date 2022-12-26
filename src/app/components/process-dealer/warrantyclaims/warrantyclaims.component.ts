import { ActivatedRoute, Router } from '@angular/router';
import { MenuItem, MessageService } from 'primeng/api';
import { Component, OnInit } from '@angular/core';
import { StepService } from 'src/app/services/step.service';
import { Subscription } from 'rxjs/internal/Subscription';
import { RefreshFormService } from 'src/app/services/RefreshForm.service';

@Component({
    selector: 'app-warrantyclaims',
    templateUrl: './warrantyclaims.component.html'
})
export class WarrantyclaimsComponent implements OnInit {

    resetAllForm: string;
    items: MenuItem[];
    activeIndex: number = 0;

    subscription: Subscription;
    
    constructor(private route: ActivatedRoute, private stepService: StepService, private router: Router,
        private messageService: MessageService, private refreshService: RefreshFormService) { }

    ngOnInit(): void {

        this.refreshService.data$.subscribe(res => this.resetAllForm = res)  //read the invoked data or default data

        this.subscription = this.stepService.partComplete$.subscribe((personalInformation) => {
            this.messageService.add({ key: "tst", severity: 'success', summary: 'Reclamo De Garantia Enviado', detail: 'Estimado ' + personalInformation.clientName + ', su reclamo de garantia a sido enviado.' });
        });

        this.route.queryParams.subscribe(({ id, estado }) => {
            this.items = [
                { label: 'DATOS GENERALES', routerLink: 'personalInf', queryParams: { id, estado } },
                { label: 'CLASIFICACIÓN DEL RECLAMO', routerLink: 'classification', queryParams: { id, estado } },
                { label: 'INFORMACIÓN DE PARTES', routerLink: 'informationPart', queryParams: { id, estado } },
                { label: 'CONFIRMACIÓN', routerLink: 'confirmation', queryParams: { id, estado } }
            ];
        });
    }

    refreshForm() { 
        this.stepService.resetWarrantyInformation();
        this.refreshService.refreshForms('RefreshForm');
        this.messageService.add({ key: "tst", severity: 'success', summary: 'Nota', detail: 'El formulario a sido limpiado.' });
        this.router.navigate(['/warranty/personalInf']);
    }

    ngOnDestroy() {
        if (this.subscription) {
            this.subscription.unsubscribe();
        }
    }

}
