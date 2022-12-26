import { AppMainComponent } from './../../app.main.component';
import { MenuItem } from 'primeng/api';
import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { MenuService } from 'src/app/services/menu.service';
import { ActivatedRoute, Route, Router } from '@angular/router';

@Component({
    selector: 'app-menu',
    templateUrl: './menu.component.html',
    styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

    menuclick: boolean;
    @Input() reset: boolean;
    @Input() horizontal: boolean;
    @Input() menuActive: Boolean;
    @Input() resetMenu: Boolean;
    @Output() sideBarPress = new EventEmitter<Event>();
    @Output() toggleMenu = new EventEmitter<boolean>();
    @Output() setResetMenu = new EventEmitter<boolean>();
    model: MenuItem[];

    constructor(private service: MenuService,
                public app: AppMainComponent) { }

    ngOnInit() {

        this.service.getMenu().subscribe((menuCategory) => {
            this.model = menuCategory.map(({ name, views, order }) => {
                if (name === 'Reclamos') {
                    return {
                        label: name,
                        icon: 'pi pi-fw pi-check-square',
                        items: views.map(
                            ({ friendlyName, route, order: zOrderView }): MenuItem => {
                                if (friendlyName === 'Crear') {   
                                    console.log('queryParams')              
                                    return {
                                        label: '   ' + friendlyName,
                                        routerLink: [route],
                                        icon: 'pi pi-fw pi-circle-fill',
                                        styleClass: 'font-size: 6px',
                                        id: order,
                                        queryParams: { new: true }
                                    };
                                } else {         
                                    console.log('not queryParams')            
                                    return {
                                        label: '   ' + friendlyName,
                                        routerLink: [route],
                                        icon: 'pi pi-fw pi-circle-fill',
                                        styleClass: 'font-size: 6px',
                                        id: order
                                    };
                                }
                            }
                        ),
                    };
                } else {
                    return {
                    label: name,
                    icon: 'pi pi-fw pi-check-square',
                    items: views.map(
                        ({ friendlyName, route, order: zOrderView }): MenuItem => {
                            return {
                                label: '   ' + friendlyName,
                                routerLink: [route],
                                icon: 'pi pi-fw pi-circle-fill',
                                styleClass: 'font-size: 6px',
                                id: order
                            };
                        }
                    ),
                };

                }
            });
            let result = null;
            if(localStorage.getItem('role') === 'DEALER') {
                // this.aRoute.queryParams.subscribe(params => {
                //     if(params.DealerNumber) {
                //         if(params.DealerNumber !== '77777') {
                            result = this.model.filter(data => data.label === 'Reclamos');
                            this.model = result;
                            // localStorage.setItem('role', 'dealer');
                        // } else {
                            // localStorage.setItem('role', 'admin');
                //         }
                //     }
                // });
                
            }
        });
    }

    onSidebarClick($event) {
        this.menuclick = true;
    }

    callParentSidebarPress($event): void {
        this.sideBarPress.emit($event);
    }

    callParentToggleMenu(toggle: boolean): void {
        this.toggleMenu.emit(toggle);
    }

    callParentResetMenu(reset: boolean): void {
        this.setResetMenu.emit(reset);
    }

    setItems(menuModel) {
        localStorage.setItem('menu', JSON.stringify(menuModel));
    }
}
