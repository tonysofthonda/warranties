import { Group, Zone } from './../model/dealer.model';
import { Dealers } from '../model/dealer.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

@Injectable({
    providedIn: 'root'
})
export class DealerService {

    headers = new HttpHeaders({
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Headers': 'Content-Type',
        'Access-Control-Allow-Methods': 'GET,POST,OPTIONS,DELETE,PUT'
    });

    constructor(private http: HttpClient) { }

    getAllDealers(): Observable<Dealers> {
        return this.http.get<Dealers>(`${environment.apiUrl}dealers/`);
    }

    getDealer(dealerNumber: string): Observable<Dealers> {
        return this.http.get<Dealers>(`${environment.apiUrl}dealers/getDealer?dealerNumber=${dealerNumber}`);
    }

    saveDealer(dealer: Dealers): Observable<Dealers> {
        return this.http.post<Dealers>(`${environment.apiUrl}dealers/`, dealer);
    }

    getAllGroups(): Observable<Group> {
        return this.http.get<Group>(`${environment.apiUrl}groups/`);
    }

    getAllZone(): Observable<Zone> {
        return this.http.get<Zone>(`${environment.apiUrl}zone/`);
    }

    saveGroup(name: Group) {
        return this.http.post(`${environment.apiUrl}groups/`, name);
    }

    saveZone(name: Zone): Observable<Zone> {
        return this.http.post<Zone>(`${environment.apiUrl}zone/`, name);
    }

    //Put Metod
    updateDealer(dealerUp: Dealers): Observable<Dealers> {
        return this.http.post<Dealers>(`${environment.apiUrl}dealers/put`, dealerUp);
    }

    //Pathch Metod
    updateStatus(dealer: Dealers): Observable<Dealers> {
        return this.http.post<Dealers>(`${environment.apiUrl}dealers/patch`, dealer);
    }
}
