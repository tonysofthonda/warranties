import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { ResponseMicro } from '../model/response.micro.model';
import { Vin } from '../model/warranty-detail.model';

@Injectable({
    providedIn: 'root'
})
export class VinService {

    constructor(private http: HttpClient) { }

    getVinByVin(vin: string): Observable<Vin> {
        return this.http.get<Vin>(`${environment.apiUrl}vin/?vin=${vin}`);
    }

    getVinMicroserviceSales(vin: string): Observable<ResponseMicro> {
        let header: any[] = [
            {type: 'Content-Type', value: 'application/json'},
            {type: 'Access-Control-Allow-Origin', value: '*'},
          ];

          let headers = new HttpHeaders();
        headers.append('Content-Type', 'application/json')
        .append('Access-Control-Allow-Origin', '*')
        .append('Access-Control-Allow-Origin', 'Origin, Content-Type, X-Auth-Token, content-type')
        .append('Access-Control-Allow-Origin', 'no-cache');

        const params = new HttpParams()
        .set('vin', vin); 
        let data = {"vin": vin};


        let httpRequest = {
            headers: headers,
            params: params,
            body: JSON.stringify(data)
        };        
        return this.http.get<ResponseMicro>(`${environment.microserviceSales}`, httpRequest);
    }

    getDataMicroserviceNetcommerce(vin: string): Observable<ResponseMicro> {
        let headers = new HttpHeaders();
        headers.append('Content-Type', 'application/json')
        .append('Access-Control-Allow-Origin', '*')
        .append('Access-Control-Allow-Origin', 'Origin, Content-Type, X-Auth-Token, content-type')
        .append('Access-Control-Allow-Origin', 'no-cache');

        const params = new HttpParams()
        .set('vin', vin); 
        return this.http.get<ResponseMicro>(`${environment.microserviceNetcommerce}`, { headers, params });
    }

}