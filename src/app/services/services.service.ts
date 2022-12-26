import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Services } from '../model/Services.model';

@Injectable({
    providedIn: 'root'
})
export class ServicesService {

    constructor(private http: HttpClient) { }

    getAll(): Observable<Services[]> {
        return this.http.get<Services[]>(`${environment.apiUrl}services/`);
    }

    getNewService(dealerNumber: string, dealerName: string, createBy: string): Observable<Services> {
        return this.http.get<Services>(`${environment.apiUrl}services/new-service/?dealerNumber=${dealerNumber}&dealerName=${dealerName}&createBy=${createBy}`);
    }

    deleteService(service: Services): Observable<Services> {
        return this.http.post<Services>(`${environment.apiUrl}services/delete/`, service);
    }

    getServicesByOrderNumber(order: string): Observable<Services> {
        return this.http.get<Services>(`${environment.apiUrl}services/by-order-number/?order=${order}`);
    }

    saveService(service: Services): Observable<Services> {
        return this.http.post<Services>(`${environment.apiUrl}services/save/`, service);
    }
    
    getServicesConsult(search: string, creationDate: string) {        
        return this.http.get<Services[]>(`${environment.apiUrl}services/consult/?search=${search}&creationDate=${creationDate}`);
    }
    
}