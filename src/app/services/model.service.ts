import { Models, Plant } from './../model/models.model';
import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ModelMicro } from '../model/model.micro';
import { ResponseMicro } from '../model/response.micro.model';

@Injectable({
    providedIn: 'root'
})
export class ModelService {

    constructor(private http: HttpClient) { }

    getAllModels(): Observable<Models[]> {
        return this.http.get<Models[]>(`${environment.apiUrl}models/`)
    }

    getAllPlants(): Observable<Plant> {
        return this.http.get<Plant>(`${environment.apiUrl}plants/`)
    }

    saveModel(modelNew: Models): Observable<Models> {
        return this.http.post<Models>(`${environment.apiUrl}models/`, modelNew, {});
    }

    //Pathch Metod
    updateStatus(model: Models): Observable<Models> {
        return this.http.post<Models>(`${environment.apiUrl}models/patch`, model);
    }

    getModelMicroservice(model: string): Observable<ResponseMicro> {
        let headers = new HttpHeaders();
        headers.append('Content-Type', 'application/json')
        .append('Access-Control-Allow-Origin', '*');

        const params = new HttpParams()
        .set('model', model)
        .set('num_part', '')
        .set('vin', '')
        .set('type', "model")
        .set('data', ''); 
        return this.http.get<ResponseMicro>(`${environment.microserviceBpcs}`, { headers, params });
    }

    getModelMicroserviceManagebd(model: string): Observable<ResponseMicro> {
        let headers = new HttpHeaders();
        headers.append('Content-Type', 'application/json')
        .append('Access-Control-Allow-Origin', '*');

        const params = new HttpParams()
        .set('model', model)
        .set('num_part', '')
        .set('vin', '')
        .set('file', '')
        .set('action', 'get')
        .set('type', "model")
        .set('data', ''); 
        return this.http.get<ResponseMicro>(`${environment.microserviceManagebd}`, { headers, params });
    }

    saveModelMicroserviceManagebd(modelMicro: ModelMicro): Observable<ResponseMicro> {
        let responseMicro = new ResponseMicro();
        responseMicro.action = 'insert';
        responseMicro.type = 'model';
        responseMicro.data = modelMicro;

        return this.http.post<ResponseMicro>(`${environment.microserviceManagebd}`, responseMicro);
    }

    getModelBd(model: string): Observable<Models> {
        return this.http.get<Models>(`${environment.apiUrl}models/model/?model=${model}`);
    }
}
