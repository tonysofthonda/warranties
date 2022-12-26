import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { PartsReplaced } from '../model/parts-replaced.model';

@Injectable({
    providedIn: 'root'
})
export class PartsReplacedService {

    constructor(private http: HttpClient) { }

    getPartsReplacedByWarrantyClaims(warrantyId: number){
        return this.http.get<PartsReplaced[]>(`${environment.apiUrl}parts-replaced/?warrantyId=${warrantyId}`);
    }

    addPartReplaced(partsReplaced:PartsReplaced){
        return this.http.post<PartsReplaced>(`${environment.apiUrl}parts-replaced/`, partsReplaced, {});
    }

    //Put Metod
    updatePartReplaced(partsReplaced:PartsReplaced){
        return this.http.post<PartsReplaced>(`${environment.apiUrl}parts-replaced/put`, partsReplaced, {});
    }
    
    //Delete Method
    deletePartReplaced(partsReplacedId:number){
        return this.http.post<PartsReplaced>(`${environment.apiUrl}parts-replaced/delete?partsReplacedId=${partsReplacedId}`, null);
    }
    
}