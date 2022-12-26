import { Models, Plant } from './../model/models.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { WorkType } from '../model/worktype.model';

@Injectable({
    providedIn: 'root'
})
export class WorkTypeService {

    constructor(private http: HttpClient) { }

    getAll(id?: number): Observable<WorkType[]> {
        return this.http.get<WorkType[]>(`${environment.apiUrl}work-type/?id=${id}`)
    }

    getByIdOrderType(id: number): Observable<WorkType[]> {
        return this.http.get<WorkType[]>(`${environment.apiUrl}work-type/by-order-type/?id=${id}`)
    }
}