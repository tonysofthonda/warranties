import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
})

export class FormatDate {

    getCurrentDate(){
        let dateOutput = new Date();
        let month = dateOutput.getMonth()+1;
        let newMonth = "";
        if(month <=9){
            newMonth='0'+month;
        }        
        return `${dateOutput.getDate()}-${newMonth}-${dateOutput.getFullYear()}`;
    }

}