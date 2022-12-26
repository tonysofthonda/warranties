import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { GradeType } from '../model/grade-type.model';

@Injectable({
    providedIn: 'root'
})
export class GradeTypeService {

    constructor(private http: HttpClient) { }

    getGradeType(status?: boolean) {   
        if(status)     {
            return this.http.get<GradeType[]>(`${environment.apiUrl}grade-type/?status=${status}`);
        }else{
            return this.http.get<GradeType[]>(`${environment.apiUrl}grade-type/`);
        }        
    }

    saveGradeType(gradeType: GradeType) {
        return this.http.post<GradeType>(`${environment.apiUrl}grade-type/`, gradeType, {});
    }

    //Put Metod
    updateGradeType(gradeType: GradeType) {
        return this.http.post<GradeType>(`${environment.apiUrl}grade-type/put`, gradeType, {});
    }

    //Pathch Metod
    changeStatusGradeType(gradeType: GradeType){
        return this.http.post<GradeType>(`${environment.apiUrl}grade-type/patch`, gradeType, {});
    }
    
}