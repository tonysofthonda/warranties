import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { State } from '../model/state.model';

@Injectable({
    providedIn: 'root'
})
export class StateService {

    constructor(private http: HttpClient) { }

    getAllStates(): Observable<State[]> {
        return this.http.get<State[]>(`${environment.apiUrl}state/`)
    }

}