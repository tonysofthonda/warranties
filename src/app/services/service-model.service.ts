import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ServiceModel } from '../model/service-model.model';

@Injectable({
    providedIn: 'root'
})
export class ServiceModelService {

    constructor(private http: HttpClient) { }

    getServiceModelByModel(model: string): Observable<ServiceModel[]> {
        return this.http.get<ServiceModel[]>(`${environment.apiUrl}service-model?model=${model}`);
    }
    
}