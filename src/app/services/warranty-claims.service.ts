import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { WarrantyClaim } from '../model/warranty-claim.model';
import { WarrantyConsecutive } from '../model/warranty-consecutive.model';
import { WarrantyDetail } from '../model/warranty-detail.model';
import { WarrantyConsult } from '../model/warranty.consult';
import { Warranty } from '../model/warranty.model';

@Injectable({
    providedIn: 'root'
})
export class WarrantyClaimsService {

    constructor(private http: HttpClient) { }

    getWarrantyClaimConsecutive(dealerNumber: Number) {        
        return this.http.get<WarrantyConsecutive>(`${environment.apiUrl}warranty-claims/consecutive/?dealerNumber=${dealerNumber}`);
    }

    getWarrantiesClaim() {        
        return this.http.get<Warranty[]>(`${environment.apiUrl}warranty-claims/`);
    }

    getWarrantyClaim(claimNumber: string) {        
        return this.http.get<Warranty>(`${environment.apiUrl}warranty-claims/get-by-claim-number?claimNumber=${claimNumber}`);
    }

    getWarrantyClaimDetail(id: number): Observable<WarrantyDetail> {        
        return this.http.get<WarrantyDetail>(`${environment.apiUrl}warranty-claims/detail?id=${id}`);
    }

    getWarrantiesClaimConsult(search: string, dateIni: string, dateEnd: string) {        
        return this.http.get<WarrantyConsult[]>(`${environment.apiUrl}warranty-claims/consult/?search=${search}&dateIni=${dateIni}&dateEnd=${dateEnd}`);
    }

    saveWarrantyClaim(warrantyClaim: WarrantyClaim) {
        return this.http.post<WarrantyClaim>(`${environment.apiUrl}warranty-claims/warranty-claim-enviado`, warrantyClaim, {});
    }

    saveWarrantyClainInDraft(warrantyClaim: WarrantyClaim) {
        return this.http.post<WarrantyClaim>(`${environment.apiUrl}warranty-claims/warranty-claim-draft`, warrantyClaim, {});
    }

    cancelWarrantyClaim(warrantyConsult: WarrantyConsult) {
        return this.http.post<WarrantyClaim>(`${environment.apiUrl}warranty-claims/warranty-claim-cancel`, warrantyConsult, {});
    }

}