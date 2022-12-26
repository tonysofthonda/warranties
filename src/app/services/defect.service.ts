import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Defects } from '../model/defects.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DefectService {

  constructor(private http: HttpClient) { }

  getAllDefects(): Observable<Defects> {
    return this.http.get<Defects>(`${environment.apiUrl}defects/`);
  }

  saveNewDefect(defect: Defects): Observable<Defects[]> {
    return this.http.post<Defects[]>(`${environment.apiUrl}defects/`, defect);
  }

  //Put Metod
  updateSaveDefect(defect: Defects): Observable<Defects> {
    return this.http.post<Defects>(`${environment.apiUrl}defects/put`, defect);
  }

  //Pathch Metod
  updateStatusDefect(defect: Defects): Observable<Defects> {
    return this.http.post<Defects>(`${environment.apiUrl}defects/patch`, defect);
  }
  
}
