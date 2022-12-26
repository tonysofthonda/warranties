import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ResponseMicro } from '../model/response.micro.model';
import { Parts } from '../model/warranty-claims.model';


@Injectable({
    providedIn: 'root'
})
export class PartsService {

    constructor(private http: HttpClient) { }

    getPartsByPartNumber(partNumber: string) {
        return this.http.get<Parts>(`${environment.apiUrl}parts/?partNumber=${partNumber}`);
    }

    savePart(part: Parts) {
        return this.http.post<Parts>(`${environment.apiUrl}parts/`, part);
    }

    getSparePartMicroservice(sparePart: string): Observable<ResponseMicro> {
        let headers = new HttpHeaders();
        headers.append('Content-Type', 'application/json')
        .append('Access-Control-Allow-Origin', '*');

        const params = new HttpParams()
        .set('model', '')
        .set('num_part', sparePart)
        .set('vin', '')
        .set('type', 'spareparts')
        .set('data', ''); 
        return this.http.get<ResponseMicro>(`${environment.microserviceBpcs}`, { headers, params });
    }

}
