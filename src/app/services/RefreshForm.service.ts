import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs/internal/BehaviorSubject";

@Injectable({
    providedIn: 'root'
})
export class RefreshFormService {

  private data = new BehaviorSubject('NotRefresh');
  data$ = this.data.asObservable();

  refreshForms(data: string) {
    this.data.next(data)
  }
}