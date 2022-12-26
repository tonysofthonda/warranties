import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sintoma } from '../model/sintoma.model';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class SintomaService {

    headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'
    });

    constructor(private http: HttpClient) { }

    getAllSintomas(): Observable<Sintoma[]> {
        return this.http.get<any[]>(`${environment.apiUrl}sintoma/` + 'list');
    }

    getById(id: any): Observable<Sintoma> {
        return this.http.get<Sintoma>(`${environment.apiUrl}sintoma/` + id, { headers: this.headers });
    }

    getByCode(code: any): Observable<Sintoma> {
        return this.http.get<Sintoma>(`${environment.apiUrl}sintoma/?code=${code}`, { headers: this.headers });
    }

    saveSintoma(sintoma: Sintoma): Observable<Sintoma> {
        return this.http.post<Sintoma>(`${environment.apiUrl}sintoma/` + 'save', sintoma);
    }

    //Put Metod
    updateSaveSintoma(sintoma: Sintoma): Observable<Sintoma> {
        return this.http.post<Sintoma>(`${environment.apiUrl}sintoma/put`, sintoma);
    }

    //Patch Metod
    updateStatusSintoma(sintoma: Sintoma): Observable<Sintoma> {
        return this.http.post<Sintoma>(`${environment.apiUrl}sintoma/patch`, sintoma);
    }

}
