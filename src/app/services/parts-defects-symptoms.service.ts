import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Defects } from '../model/defects.model';
import { PartsDefectsSymptoms } from '../model/parts-defects-symptoms.model';
import { Sintoma } from '../model/sintoma.model';

@Injectable({
    providedIn: 'root'
})
export class PartsDefectsSymptomsService {

    constructor(private http: HttpClient) { }
    
    getDefectsBypartId(partId: Number) {      
        return this.http.get<Defects[]>(`${environment.apiUrl}parts-defects-symptom/getRelatedDefects/?partId=${partId}`);
    }
    
    getSymtomByPartIdAndDefectId(partId:Number, defectId: Number) {      
        return this.http.get<Sintoma[]>(`${environment.apiUrl}parts-defects-symptom/getRelatedSymptom/?defectId=${defectId}&partId=${partId}`);
    }

    getAllRelated(){
        return this.http.get<PartsDefectsSymptoms[]>(`${environment.apiUrl}parts-defects-symptom/`);
    }

    addRelated(partsDefectsSymptoms: PartsDefectsSymptoms){
        return this.http.post<PartsDefectsSymptoms>(`${environment.apiUrl}parts-defects-symptom/`, partsDefectsSymptoms, {});
    }

    //Put Metod
    updateRelated(partsDefectsSymptoms: PartsDefectsSymptoms){
        return this.http.post<PartsDefectsSymptoms>(`${environment.apiUrl}parts-defects-symptom/put`, partsDefectsSymptoms, {});
    }

    //Pathch Metod
    changeStatusRelated(partsDefectsSymptoms: PartsDefectsSymptoms){
        return this.http.post<PartsDefectsSymptoms>(`${environment.apiUrl}parts-defects-symptom/patch`, partsDefectsSymptoms, {});
    }
    
}