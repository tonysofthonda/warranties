import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Country } from '../model/country.model'; 

@Injectable({
    providedIn: 'root'
})
export class CountryService {

    constructor(private http: HttpClient) { }
    
    getAllCountries(): Observable<Country[]> {
        return this.http.get<Country[]>(`${environment.apiUrl}country/`)
    }
    
}