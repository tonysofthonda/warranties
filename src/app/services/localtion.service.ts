import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Locations } from '../model/Location.model';

@Injectable({
    providedIn: 'root'
})
export class LocationService {

    constructor(private http: HttpClient) { }

    getAllLocations(): Observable<Locations> {
        return this.http.get<Locations>(`${environment.apiUrl}locations/`);
    }

    getAllLocationsByState(id: number): Observable<Locations> {
        return this.http.get<Locations>(`${environment.apiUrl}${'locations/' + id}`);
    }

    getLocationSelect(id: number): Observable<Locations> {
        return this.http.get<Locations>(`${environment.apiUrl}${'locations/selectItem/' + id}`);
    }


}