import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { ApprovedAmount } from '../model/approved.amount.model';
import { Approved } from '../model/approved.model';
import { HistoryApprovedAmount } from '../model/history.approved.amount.model';
import { HistoryDetailApproved } from '../model/history.detail.approved.model';
import { TotalsAmounts } from '../model/totals.amounts.model';

@Injectable({
    providedIn: 'root'
})
export class ApprovedAmountService {

    constructor(private http: HttpClient) { }

    getApprovedAmount(dealer: number, dateIni: string, dateEnd: string) {
        return this.http.get<Approved>(`${environment.apiUrl}warranty-claims/getAllAprovedAmounts/?dealer=${dealer}&dateIni=${dateIni}&dateEnd=${dateEnd}`);
    }

    getTotalById(idWarranty: number) {
        return this.http.get<Approved>(`${environment.apiUrl}warranty-claims/getTotalById?idWarranty=${idWarranty}`);
    }

    onHoldWarranty(amount: ApprovedAmount) {
        return this.http.post<ApprovedAmount>(`${environment.apiUrl}warranty-claims/onhold`, amount);
    }

    reactiveWarranty(amount: ApprovedAmount) {
        return this.http.post<ApprovedAmount>(`${environment.apiUrl}warranty-claims/reactive`, amount);
    }

    getHistoryApprovedAmount() {
        return this.http.get<HistoryApprovedAmount[]>(`${environment.apiUrl}approved/amounts/history`);
    }

    getHistoryDetailApprovedAmount() {
        return this.http.get<HistoryDetailApproved[]>(`${environment.apiUrl}approved/amounts/history/detail`);
    }

    getTotalsAmount() {
        return this.http.get<TotalsAmounts[]>(`${environment.apiUrl}approved/amounts/totals`);
    }

    updateApprovedAmount(approvedAmount: ApprovedAmount) {
        return this.http.post<ApprovedAmount>(`${environment.apiUrl}approved/amounts/`, approvedAmount);
    }

    //Delete Method
    deleteApprovedAmount(id: number, comment: string) {
        return this.http.post<ApprovedAmount>(`${environment.apiUrl}approved/amounts/delete?id=${id}&comments=${comment}`, null);
    }

    approvedAmount(dealer: number, dateIni: string, dateEnd: string, total: number, dataId: number[]) {
        return this.http.post<ApprovedAmount>(`${environment.apiUrl}approved/amounts/approved/?dealer=${dealer}&dateIni=${dateIni}&dateEnd=${dateEnd}&total=${total}&dataId=${dataId}`, {});
    }
}
