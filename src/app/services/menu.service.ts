import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { MenuCategory, View } from '../model/menu.model';
import { environment } from 'src/environments/environment';

@Injectable()
export class MenuService {

    private menuSource = new Subject<string>();
    private resetSource = new Subject();

    menuSource$ = this.menuSource.asObservable();
    resetSource$ = this.resetSource.asObservable();

    constructor(private http: HttpClient) { }

    getMenu(): Observable<any[]> {
        return this.http.get<any[]>(`${environment.apiUrl}menu/list`);
    }

    onMenuStateChange(key: string) {
        this.menuSource.next(key);
    }

    reset() {
        this.resetSource.next();
    }
}
