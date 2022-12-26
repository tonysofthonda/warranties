import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Periods } from '../model/periods.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PeriodService {

  constructor(private http: HttpClient) { }

  getAllPeriods(): Observable<Periods> {
    return this.http.get<Periods>(`${environment.apiUrl}periods/`);
  }

  savePeriods(periodNew: Periods): Observable<Periods> {
    return this.http.post<Periods>(`${environment.apiUrl}periods/`, periodNew);
  }

  //Pathch Metod
  updateStatus(period: Periods): Observable<Periods> {
    return this.http.post<Periods>(`${environment.apiUrl}periods/patch`, period);
  }

}
