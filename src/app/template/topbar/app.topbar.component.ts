import { Component, OnInit } from '@angular/core';
import { AppMainComponent } from '../../app.main.component';

@Component({
    selector: 'app-topbar',
    templateUrl: './app.topbar.component.html'
})
export class AppTopBarComponent implements OnInit{

    date = new Date();
    
    userName: string = '';

    constructor(public app: AppMainComponent) {}

    ngOnInit(): void {
        if(localStorage.getItem('FullName')) {
            this.userName = localStorage.getItem('FullName');
        }
    }

    logout() {
        let popup = window.self;
        popup.opener = window.self;
        popup.close();
    }
}
