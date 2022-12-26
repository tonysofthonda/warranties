import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Sintoma } from '../model/sintoma.model';
import { environment } from 'src/environments/environment';
import { Site } from '../model/sites.model';

@Injectable({
    providedIn: 'root'
})
export class SitesService {

    constructor(private http: HttpClient) { }

    getAllStates(): Observable<Site[]> {
        return this.http.get<Site[]>(`${environment.apiUrl}site/state`)
    }

    getAllTownship(): Observable<Site[]> {
        return this.http.get<Site[]>(`${environment.apiUrl}site/township`)
    }

    getAllArea(): Observable<Site[]> {
        return this.http.get<Site[]>(`${environment.apiUrl}site/area`)
    }

    getAllGroup(): Observable<Site[]> {
        return this.http.get<Site[]>(`${environment.apiUrl}site/group`)
    }

    postSave(site: Site): Observable<Site> {
        return this.http.post<Site>(`${environment.apiUrl}site/`, site, {})
    }

    //Put Metod
    putUpdate(site: Site): Observable<Site> {
        return this.http.post<Site>(`${environment.apiUrl}site/put`, site);
    }
}
