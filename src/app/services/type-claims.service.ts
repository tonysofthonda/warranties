import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { TypeClaims } from '../model/type-claims.model';

@Injectable({
    providedIn: 'root'
})
export class TypeClaimsService {

    constructor(private http: HttpClient) { }

    getTypeClaims(status?: Boolean){
        if(status){
            return this.http.get<TypeClaims[]>(`${environment.apiUrl}type-claims/?status=${status}`);
        }else{
        return this.http.get<TypeClaims[]>(`${environment.apiUrl}type-claims/`);
        }
    }
    
    saveTypeClaims(typeClaims: TypeClaims) {
        return this.http.post<TypeClaims>(`${environment.apiUrl}type-claims/`, typeClaims, {});
    }

    //Put Metod
    updateTypeClaims(typeClaims: TypeClaims) {
        return this.http.post<TypeClaims>(`${environment.apiUrl}type-claims/put`, typeClaims, {});
    }

    //Pathch Metod
    changeStatusTypeClaims(typeClaims: TypeClaims){
        return this.http.post<TypeClaims>(`${environment.apiUrl}type-claims/patch`, typeClaims, {});
    }

}