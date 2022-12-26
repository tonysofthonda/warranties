import { DealerComponent } from '../../src/app/components/catalogs/dealer/dealer.component';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { CommonModule, DatePipe, HashLocationStrategy, LocationStrategy } from '@angular/common';
import { AppRoutingModule } from '../../src/app/app-routing.module';
import { RouterModule } from '@angular/router';

// PrimeNG Components for demos
import { AccordionModule } from 'primeng/accordion';
import { BreadcrumbModule } from 'primeng/breadcrumb';
import { ButtonModule } from 'primeng/button';
import { BlockUIModule } from "primeng/blockui";
import { CalendarModule } from 'primeng/calendar';
import { CardModule } from 'primeng/card';
import { CheckboxModule } from 'primeng/checkbox';
import { DialogModule } from 'primeng/dialog';
import { DividerModule } from 'primeng/divider';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DropdownModule } from 'primeng/dropdown';
import { FileUploadModule } from 'primeng/fileupload';
import { FullCalendarModule } from 'primeng/fullcalendar';
import { ImageModule } from 'primeng/image';
import { InputNumberModule } from 'primeng/inputnumber';
import { InputSwitchModule } from 'primeng/inputswitch';
import { InputTextModule } from 'primeng/inputtext';
import { InputTextareaModule } from 'primeng/inputtextarea';
import { InputMaskModule } from 'primeng/inputmask';
import { MenuModule } from 'primeng/menu';
import { MenubarModule } from 'primeng/menubar';
import { MessagesModule } from 'primeng/messages';
import { MessageModule } from 'primeng/message';
import { MultiSelectModule } from 'primeng/multiselect';
import { OrderListModule } from 'primeng/orderlist';
import { PanelModule } from 'primeng/panel';
import { PanelMenuModule } from 'primeng/panelmenu';
import { RippleModule } from 'primeng/ripple';
import { SelectButtonModule } from 'primeng/selectbutton';
import { StepsModule } from 'primeng/steps';
import { TabMenuModule } from 'primeng/tabmenu';
import { TableModule } from 'primeng/table';
import { TabViewModule } from 'primeng/tabview';
import { TerminalModule } from 'primeng/terminal';
import { ToastModule } from 'primeng/toast';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { ToolbarModule } from 'primeng/toolbar';
import { TooltipModule } from 'primeng/tooltip';
import { VirtualScrollerModule } from 'primeng/virtualscroller';
import { TimelineModule } from "primeng/timeline";
import { SplitterModule } from 'primeng/splitter';


// Application Components
import { AppCodeModule } from '../../src/app/app.code.component';
import { AppComponent } from '../../src/app/app.component';
import { AppMainComponent } from '../../src/app/app.main.component';
import { MenuComponent } from '../../src/app/template/menu/menu.component';
import { AppMenuitemComponent } from '../../src/app/template/menu-item/app.menuitem.component'
import { AppBreadcrumbComponent } from '../../src/app/template/breadcrumb/app.breadcrumb.component';
import { AppTopBarComponent } from '../../src/app/template/topbar/app.topbar.component';
import { AppFooterComponent } from '../../src/app/template/footer/app.footer.component';
import { AppInvoiceComponent } from '../../src/app/pages/app.invoice.component';
import { AppHelpComponent } from '../../src/app/pages/app.help.component';
import { AppNotfoundComponent } from '../../src/app/pages/app.notfound.component';
import { AppErrorComponent } from '../../src/app/pages/app.error.component';
import { AppAccessdeniedComponent } from '../../src/app/pages/app.accessdenied.component';
import { AppLoginComponent } from '../../src/app/pages/app.login.component';
import { DefectsComponent } from '../../src/app/components/catalogs/defects/defects.component';

// services
import { BreadcrumbService } from '../../src/app/template/breadcrumb/app.breadcrumb.service';
import { MessageService } from 'primeng/api';

import { SintomasComponent } from '../../src/app/components/catalogs/sintomas/sintomas.component';
import { ValidationInputComponent } from '../../src/app/components/validation-input/validation-input.component';
import { ModelsComponent } from '../../src/app/components/catalogs/models/models.component';
import { PeriodsComponent } from '../../src/app/components/catalogs/periods/periods.component';
import { WarrantyclaimsComponent } from './components/process-dealer/warrantyclaims/warrantyclaims.component';
import { PersonalinformationComponent } from './components/process-dealer/warrantyclaims/personalinformation/personalinformation.component';
import { ClassificationclaimComponent } from './components/process-dealer/warrantyclaims/classificationclaim/classificationclaim.component';
import { ConfirmationComponent } from './components/process-dealer/warrantyclaims/confirmation/confirmation.component';
import { InformationpartComponent } from './components/process-dealer/warrantyclaims/informationpart/informationpart.component';
import { RelationPartTableComponent } from './components/process-dealer/warrantyclaims/informationpart/relation-part-table/relation-part-table.component';
import { OtherJobsComponent } from './components/process-dealer/warrantyclaims/informationpart/other-jobs/other-jobs.component';
import { ConsultClaimComponent } from './components/process-dealer/consult-claim/consult-claim.component';
import { AmountsConsultComponent } from './components/process-dealer/amounts-consult/amounts-consult.component';
import { GenerateMotorcicleServicesComponent } from './components/process-dealer/generate-motorcicle-services/generate-motorcicle-services.component';
import { TrackingServiceComponent } from './components/process-dealer/generate-motorcicle-services/tracking-service/tracking-service.component';
import { HistoryApprovedAmountsComponent } from './components/warranty-process/history-approved-amounts/history-approved-amounts.component';
import { ApprovedAmountsComponent } from './components/warranty-process/approved-amounts/approved-amounts.component';
import { CreateServiceComponent } from './components/create-service/create-service.component';
import { ConsultServiceComponent } from './components/create-service/consult-service/consult-service.component';
import { SymptomsDefectsComponent } from './components/catalogs/symptoms-defects/symptoms-defects.component';
import { MenuService } from './services/menu.service';
import { GradeComponent } from './components/process-dealer/warranty-variables/values-of-list/grade/grade.component';
import { ClaimTypeComponent } from './components/process-dealer/warranty-variables/values-of-list/claim-type/claim-type.component';
import { ProblemTypeComponent } from './components/process-dealer/warranty-variables/values-of-list/problem-type/problem-type.component';
import { ValuesOfListComponent } from './components/process-dealer/warranty-variables/values-of-list/values-of-list.component';
import { ValuesOfPercentageComponent } from './components/process-dealer/warranty-variables/values-of-percentage/values-of-percentage.component';
import { WarrantyVariablesComponent } from './components/process-dealer/warranty-variables/warranty-variables.component';
import { PartsMotorcycleComponent } from './components/catalogs/parts-motorcycle/parts-motorcycle.component';
import { GeneralReportsComponent } from './components/general-reports/general-reports.component';
import { CampaignReportComponent } from './components/general-reports/campaign-report/campaign-report.component';
import { ReportToJapanComponent } from './components/report-to-japan/report-to-japan.component';
import { SitesComponent } from './components/catalogs/sites/sites.component';
import { WarrantyConsultComponent } from './components/process-dealer/warranty-consult/warranty-consult.component';
import { SlideMenuModule } from 'primeng/slidemenu';
import { SamlCredentialsComponent } from './components/saml-credentials/saml-credentials.component';
import { LoginComponent } from './components/login/login.component';

@NgModule({
    imports: [
        RouterModule,
        AppCodeModule,
        BrowserModule,
        BlockUIModule,
        FormsModule,
        ReactiveFormsModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        CommonModule,
        AccordionModule,
        BreadcrumbModule,
        ButtonModule,
        CalendarModule,
        CardModule,
        CheckboxModule,
        DialogModule,
        DividerModule,
        ConfirmDialogModule,
        DropdownModule,
        FileUploadModule,
        FullCalendarModule,
        ImageModule,
        InputNumberModule,
        InputSwitchModule,
        InputTextModule,
        InputTextareaModule,
        InputMaskModule,
        MenuModule,
        MenubarModule,
        MessageModule,
        MessagesModule,
        MultiSelectModule,
        OrderListModule,
        PanelModule,
        PanelMenuModule,
        RippleModule,
        SelectButtonModule,
        StepsModule,
        SlideMenuModule,
        TableModule,
        TabMenuModule,
        TabViewModule,
        TerminalModule,
        ToastModule,
        ToggleButtonModule,
        ToolbarModule,
        TooltipModule,
        VirtualScrollerModule,
        ConfirmDialogModule,
        TimelineModule,
        SplitterModule,
    ],
    declarations: [
        AppComponent,
        AppMainComponent,
        MenuComponent,
        AppMenuitemComponent,
        AppTopBarComponent,
        AppFooterComponent,
        AppInvoiceComponent,
        AppHelpComponent,
        AppNotfoundComponent,
        AppErrorComponent,
        AppAccessdeniedComponent,
        AppLoginComponent,
        AppBreadcrumbComponent,
        SintomasComponent,
        DefectsComponent,
        DealerComponent,
        ValidationInputComponent,
        ModelsComponent,
        PeriodsComponent,
        WarrantyclaimsComponent,
        PersonalinformationComponent,
        ClassificationclaimComponent,
        InformationpartComponent,
        ConfirmationComponent,
        RelationPartTableComponent,
        OtherJobsComponent,
        ConsultClaimComponent,
        AmountsConsultComponent,
        GenerateMotorcicleServicesComponent,
        TrackingServiceComponent,
        ApprovedAmountsComponent,
        HistoryApprovedAmountsComponent,
        CreateServiceComponent,
        ConsultServiceComponent,
        PartsMotorcycleComponent,
        WarrantyVariablesComponent,
        ValuesOfPercentageComponent,
        ValuesOfListComponent,
        ProblemTypeComponent,
        ClaimTypeComponent,
        GradeComponent,
        SymptomsDefectsComponent,
        GeneralReportsComponent,
        CampaignReportComponent,
        ReportToJapanComponent,
        SitesComponent,
        WarrantyConsultComponent,
        SamlCredentialsComponent,
        LoginComponent
    ],
    providers: [
        { provide: LocationStrategy, useClass: HashLocationStrategy },
        MenuService, BreadcrumbService, MessageService, DatePipe
    ],
    bootstrap: [AppComponent]
})
export class AppModule { }
