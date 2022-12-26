import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from 'primeng/api';
import { AuthService } from 'src/app/services/auth.service';
import { DealerService } from 'src/app/services/dealer.service';

@Component({
  selector: 'app-saml-credentials',
  templateUrl: './saml-credentials.component.html',
  providers: [MessageService]
})
export class SamlCredentialsComponent implements OnInit {

  constructor(private route: ActivatedRoute, private messageService: MessageService,
    private dealerService: DealerService, private authService: AuthService) { }

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      // Save data to sessionStorage
      if (params.DealerNumber && params.UserID && params.FullName) {
        this.dealerService.getDealer(params.DealerNumber).subscribe(dealerResponse => {
          if (dealerResponse) {
            localStorage.setItem("DealerNumber", params.DealerNumber);
            localStorage.setItem("UserID", params.UserID);
            localStorage.setItem("FullName", params.FullName);
            localStorage.setItem('role', dealerResponse.userType);
            this.authService.login(dealerResponse);
          } else {
            this.messageService.add({
              key: "tst",
              severity: "error",
              summary: "Error",
              detail: 'El distribuidor no esta registrado!!!',
            });
            setTimeout(() => {
              let popup = window.self;
              popup.opener = window.self;
              popup.close();
            }, 800);
          }
        });
      } else {
        this.messageService.add({
          key: "tst",
          severity: "error",
          summary: "Error",
          detail: 'No se encontraron credenciales del usuario!!!',
        });
        setTimeout(() => {
          let popup = window.self;
          popup.opener = window.self;
          popup.close();
        }, 800);
      }

    });
  }

}
