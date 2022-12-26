import { ActivatedRoute, Router } from '@angular/router';
import { createClaim } from './../../../model/claim.model';
import { Component, OnInit } from '@angular/core';
import { WarrantyClaimsService } from 'src/app/services/warranty-claims.service';
import { Warranty } from 'src/app/model/warranty.model';

@Component({
    selector: 'app-consult-claim',
    templateUrl: './consult-claim.component.html'
})
export class ConsultClaimComponent implements OnInit {

    claim: createClaim[] = [];
    claimData: Warranty[] = [];
    selectedClaim: any;
    loading: boolean;

    constructor(private route: Router,
        private warrantyClaimsService: WarrantyClaimsService) { }

    ngOnInit(): void {
        this.getClaims();
    }

    getClaims() {
        this.loading = true;
        this.warrantyClaimsService.getWarrantiesClaim().subscribe(data => {
            this.claimData = data;
            this.loading = false;
        })
    }

    editCleim(id, estado) {
        this.route.navigate(['/warranty'], { queryParams: { id, estado } });
    }

}
