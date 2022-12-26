import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { OrderType } from '../model/ordertype.model';

@Injectable({
    providedIn: 'root'
})
export class OrderTypeService {

    constructor(private http: HttpClient) { }

    getAll(): Observable<OrderType[]> {
        return this.http.get<OrderType[]>(`${environment.apiUrl}order-type/`)
    }

    getById(id: number): Observable<OrderType[]> {
        return this.http.get<OrderType[]>(`${environment.apiUrl}order-type/?id=${id}`)
    }
}