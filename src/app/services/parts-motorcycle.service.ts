import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { PartMotorcycle } from '../model/part-motorcycle.model';

@Injectable({
    providedIn: 'root'
})
export class PartsMotorcycleService {

    constructor(private http: HttpClient) { }

    getPartsMotorcycle() {        
        return this.http.get<PartMotorcycle[]>(`${environment.apiUrl}parts-motorcycle/`);
    }

    getPartsMotorcycleByStatus(status?: Boolean): Observable<PartMotorcycle[]> {      
        if(status){
            return this.http.get<PartMotorcycle[]>(`${environment.apiUrl}parts-motorcycle/by-status/?status=${status}`);
        } else {
            return this.http.get<PartMotorcycle[]>(`${environment.apiUrl}parts-motorcycle/by-status/`);
        }
    }

    savePartMotorcycle(partMotorcycle: PartMotorcycle){
        return this.http.post<PartMotorcycle>(`${environment.apiUrl}parts-motorcycle/`, partMotorcycle, {});
    }

    //Put Metod
    updatePartMotorcycle(partMotorcycle: PartMotorcycle){
        return this.http.post<PartMotorcycle>(`${environment.apiUrl}parts-motorcycle/put`, partMotorcycle, {});
    }

    //Pathch Metod
    updateStatusPartMotorcycle(partMotorcycle: PartMotorcycle){
        return this.http.post<PartMotorcycle>(`${environment.apiUrl}parts-motorcycle/patch`, partMotorcycle, {});
    }

}