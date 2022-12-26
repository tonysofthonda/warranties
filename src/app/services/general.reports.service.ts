import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { Campaign } from "../model/campaign.model";
import { HistoricaAmountReport } from "../model/historical.amount.approved.report.model";
import { TechnicalSummary } from "../model/technical.summary.model";

@Injectable({
    providedIn: 'root'
})
export class GeneralReportsService {

    constructor(private http: HttpClient) { }

    getReportTechnicalSummary(dateIni: string, dateEnd: string) {
        return this.http.get<TechnicalSummary[]>(`${environment.apiUrl}general/reports/technical/summary?dateIni=${dateIni}&&dateEnd=${dateEnd}`);
    }

    getReportUnitsCampign() {
        return this.http.get<Campaign[]>(`${environment.apiUrl}general/reports/units/campign`);
    }

    getReportHistoricalAmounts(dateIni: string, dateEnd: string) {
        return this.http.get<HistoricaAmountReport[]>(`${environment.apiUrl}general/reports/historical/amounts?dateIni=${dateIni}&&dateEnd=${dateEnd}`);
    }

    postSaveReport(formData: any) {
        return this.http.post(`${environment.apiUrl}general/reports/units/campign`, formData);
    }

    //Delete Method
    deleteRecord(id: Number) {
        return this.http.post(`${environment.apiUrl}general/reports/delete?id=${id}`, null);
    }

    putUpdateReport(campaign: Campaign) {
        return this.http.post<Campaign>(`${environment.apiUrl}general/reports/put`, campaign, {});
    }
}
