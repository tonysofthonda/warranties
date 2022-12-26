
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

//Enviroment
import { environment } from 'src/environments/environment';

//Model
import { ConsultService } from '../model/consult.service.model';
import { HistoryService } from '../model/history-service.model';
import { InfoCreateService } from '../model/info.create.service.model';
import { InfoReport } from '../model/info.report.model';
import { ServiceDetail } from '../model/service-detail.model';

@Injectable({
    providedIn: 'root'
})
export class CreateServiceService {

    constructor(private http: HttpClient) { }

    //Get Services
    getAllServiceCreated(): Observable<ConsultService[]> {
        return this.http.get<ConsultService[]>(`${environment.apiUrl}create/service/`);
    }

    getServiceCreatedDetail(id: number): Observable<ServiceDetail> {
        return this.http.get<ServiceDetail>(`${environment.apiUrl}create/service/detail/?id=${id}`);
    }

    getGenerateServiceNumber(): Observable<string> {
        return this.http.get(`${environment.apiUrl}create/service/number`, {responseType: 'text'});
    }

    getOneCreateService(id: number): Observable<InfoCreateService> {
        return this.http.get<InfoCreateService>(`${environment.apiUrl}create/service/by/?id=${id}`);
    }

    getHistoryService(vin: string): Observable<HistoryService[]> {
        return this.http.get<HistoryService[]>(`${environment.apiUrl}create/service/history/?vin=${vin}`);
    }

    //Post Services
    postCreateService(infoCreateService: InfoCreateService): Observable<InfoReport> {
        return this.http.post<InfoReport>(`${environment.apiUrl}create/service/`, infoCreateService);
    }

    //Delete Services
    deleteService(id: number): Observable<InfoCreateService> {
        return this.http.post<InfoCreateService>(`${environment.apiUrl}create/service/delete?id=${id}`, null);
    }

    //Put Services
    updateService(infoCreateService: InfoCreateService, id: number): Observable<InfoReport> {
        return this.http.post<InfoReport>(`${environment.apiUrl}create/service/put?id=${id}`, infoCreateService);
    }
    
}
