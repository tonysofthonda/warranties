import { TrackingServiceComponent } from './components/process-dealer/generate-motorcicle-services/tracking-service/tracking-service.component';
import { GenerateMotorcicleServicesComponent } from './components/process-dealer/generate-motorcicle-services/generate-motorcicle-services.component';
import { AmountsConsultComponent } from './components/process-dealer/amounts-consult/amounts-consult.component';
import { AppLoginComponent } from './pages/app.login.component';
import { ConsultClaimComponent } from './components/process-dealer/consult-claim/consult-claim.component';
import { InformationpartComponent } from './components/process-dealer/warrantyclaims/informationpart/informationpart.component';
import { ClassificationclaimComponent } from './components/process-dealer/warrantyclaims/classificationclaim/classificationclaim.component';
import { PersonalinformationComponent } from './components/process-dealer/warrantyclaims/personalinformation/personalinformation.component';
import { WarrantyclaimsComponent } from './components/process-dealer/warrantyclaims/warrantyclaims.component';
import { ModelsComponent } from './components/catalogs/models/models.component';
import { DealerComponent } from './components/catalogs/dealer/dealer.component';
import { SintomasComponent } from './components/catalogs/sintomas/sintomas.component';
import { RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { AppMainComponent } from './app.main.component';
import { DefectsComponent } from './components/catalogs/defects/defects.component';
import { PeriodsComponent } from './components/catalogs/periods/periods.component';
import { ApprovedAmountsComponent } from './components/warranty-process/approved-amounts/approved-amounts.component';
import { HistoryApprovedAmountsComponent } from './components/warranty-process/history-approved-amounts/history-approved-amounts.component';
import { CreateServiceComponent } from './components/create-service/create-service.component';
import { ConsultServiceComponent } from './components/create-service/consult-service/consult-service.component';
import { PartsMotorcycleComponent } from './components/catalogs/parts-motorcycle/parts-motorcycle.component';
import { WarrantyVariablesComponent } from './components/process-dealer/warranty-variables/warranty-variables.component';
import { GeneralReportsComponent } from './components/general-reports/general-reports.component';
import { CampaignReportComponent } from './components/general-reports/campaign-report/campaign-report.component';
import { SymptomsDefectsComponent } from './components/catalogs/symptoms-defects/symptoms-defects.component';
import { ReportToJapanComponent } from './components/report-to-japan/report-to-japan.component';
import { SitesComponent } from './components/catalogs/sites/sites.component';
import { WarrantyConsultComponent } from './components/process-dealer/warranty-consult/warranty-consult.component';
import { ConfirmationComponent } from './components/process-dealer/warrantyclaims/confirmation/confirmation.component';
import { SamlCredentialsComponent } from './components/saml-credentials/saml-credentials.component';
import { LoginComponent } from './components/login/login.component';
import { AuthGuardService } from './guard/auth-guard.service';

@NgModule({
    imports: [
        RouterModule.forRoot([
            {
                path: '', component: AppMainComponent, canActivate: [AuthGuardService],
                
                children: [
                    { path: 'sintomas', component: SintomasComponent },
                    { path: 'defects', component: DefectsComponent },
                    { path: 'dealers', component: DealerComponent },
                    { path: 'models', component: ModelsComponent },
                    { path: 'periods', component: PeriodsComponent },
                    { path: 'parts', component: PartsMotorcycleComponent },
                    { path: 'sintomas-defectos', component: SymptomsDefectsComponent },
                    {
                        path: 'warranty', component: WarrantyclaimsComponent,
                        children: [
                            { path: '', redirectTo: 'personalInf', pathMatch: 'full' },
                            { path: 'personalInf', component: PersonalinformationComponent },
                            { path: 'classification', component: ClassificationclaimComponent },
                            { path: 'informationPart', component: InformationpartComponent },
                            { path: 'confirmation', component: ConfirmationComponent },
                        ]
                    },
                    { path: 'warranty-consult', component: WarrantyConsultComponent },
                    { path: 'status-claim', component: ConsultClaimComponent },
                    { path: 'amounts-consult', component: AmountsConsultComponent },
                    { path: 'motorcicle-services', component: GenerateMotorcicleServicesComponent },
                    { path: 'tracking-services', component: TrackingServiceComponent },
                    { path: 'warranty-amount', component: ApprovedAmountsComponent },
                    { path: 'variables-reportes-garantias', component: WarrantyVariablesComponent },
                    { path: 'history-approved-amounts', component: HistoryApprovedAmountsComponent },
                    { path: 'create-service', component: CreateServiceComponent },
                    { path: 'consult-service', component: ConsultServiceComponent },
                    { path: 'create-service/:id', component: CreateServiceComponent },
                    { path: 'general-reports', component: GeneralReportsComponent },
                    { path: 'campaign-report', component: CampaignReportComponent },
                    { path: 'japan-report', component: ReportToJapanComponent},
                    { path: 'sites', component: SitesComponent}
                ]
            },
            {
                path: 'login', component: LoginComponent
            },
            { path: 'iN', component: SamlCredentialsComponent },
            { path: '**', redirectTo: '/notfound' },
        ], { scrollPositionRestoration: 'enabled' })
    ],
    exports: [RouterModule]
})
export class AppRoutingModule {
}
