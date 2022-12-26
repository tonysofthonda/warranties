import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { Dealers } from '../model/dealer.model';

@Injectable({
    providedIn: "root",
})
export class AuthService {
    private message: string;

    constructor(private _router: Router) {}

    /**
     * this is used to clear anything that needs to be removed
     */
    clear(): void {
        localStorage.clear();
    }

    /**
     * check for expiration and if token is still existing or not
     * @return {boolean}
     */
    isAuthenticated(): boolean {
        return (
            localStorage.getItem("DealerNumber") != null &&
            localStorage.getItem("UserID") != null &&
            localStorage.getItem("FullName") != null
        );
    }

    login(dealer: Dealers): void {
      this._router.navigate(["/"]);
    }

    /**
     * this is used to clear local storage and also the route to login
     */
    logout(): void {
        this.clear();
        this._router.navigate(["/login"]);
    }
}