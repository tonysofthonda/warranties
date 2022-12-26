import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ProblemCategory } from '../model/problem-category.model';

@Injectable({
    providedIn: 'root'
})
export class ProblemCategoryService {

    constructor(private http: HttpClient) { }

    getProblemCategory(status?: boolean) {   
        if(status)     {
            return this.http.get<ProblemCategory[]>(`${environment.apiUrl}problem-category/?status=${status}`);
        }else{
            return this.http.get<ProblemCategory[]>(`${environment.apiUrl}problem-category/`);
        }        
    }

    saveProblemCategory(problemCategory: ProblemCategory) {
        return this.http.post<ProblemCategory>(`${environment.apiUrl}problem-category/`, problemCategory, {});
    }

    //Put Metod
    updateProblemCategory(problemCategory: ProblemCategory) {
        return this.http.post<ProblemCategory>(`${environment.apiUrl}problem-category/put`, problemCategory, {});
    }

    //Pathch Metod
    changeStatusProblemCategory(problemCategory: ProblemCategory){
        return this.http.post<ProblemCategory>(`${environment.apiUrl}problem-category/patch`, problemCategory, {});
    }

}