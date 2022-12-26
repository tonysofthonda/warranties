import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Setting } from '../model/setting.model';

@Injectable({
    providedIn: 'root'
})

export class SettingsService{

    constructor(private http: HttpClient) { }

    getSettings(name: String) {   
        if(name != null)     {
            return this.http.get<Setting[]>(`${environment.apiUrl}setting/?name=${name}`);
        }else{
            return this.http.get<Setting[]>(`${environment.apiUrl}setting/`);
        }        
    }

    //Pathch Metod
    updateSetting(setting:Setting){
        return this.http.post<Setting>(`${environment.apiUrl}setting/`, setting, {});
    }
    
}