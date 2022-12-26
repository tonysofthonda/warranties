import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { OtherExpenses } from '../model/warranty-claims.model';

@Injectable({
    providedIn: 'root'
})
export class OtherExpensesService {

    constructor(private http: HttpClient) { }

    getOtherExpenses(warrantyId: number){
        return this.http.get<OtherExpenses[]>(`${environment.apiUrl}other_expenses/?warrantyId=${warrantyId}`);
    }

    saveOtherExpenses(otherExpenses: OtherExpenses){
        return this.http.post<OtherExpenses>(`${environment.apiUrl}other_expenses/`, otherExpenses, {});
    }

    updateOtherExpenses(otherExpenses: OtherExpenses){
        return this.http.post<OtherExpenses>(`${environment.apiUrl}other_expenses/put`, otherExpenses, {});
    }

    //Delete Method
    deleteOtherExpenses(otherExpensesId: number){
        return this.http.post<OtherExpenses>(`${environment.apiUrl}other_expenses/delete?otherExpensesId=${otherExpensesId}`, null);
    }

}