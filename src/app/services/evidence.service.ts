import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Evidence } from '../model/evidence.model';

@Injectable({
    providedIn: 'root'
})
export class EvidenceService {

    constructor(private http: HttpClient) { }

    uploadEvidence(file): Observable<Evidence> {
        let formData:FormData = new FormData(); 
        formData.append("file", file, file.name);
        return this.http.post<Evidence>(`${environment.apiUrl}uploadFile`, formData);
    }

    getEvidenceByWarrantyId(warrantyId: number): Observable<Evidence[]> {
        return this.http.get<Evidence[]>(`${environment.apiUrl}evidences?warrantyId=${warrantyId}`);
    }

}
