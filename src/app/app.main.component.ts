import { Component } from '@angular/core';
import { trigger, state, style, transition, animate } from '@angular/animations';
import { MenuService } from './services/menu.service';
import { PrimeNGConfig } from 'primeng/api';

@Component({
    selector: 'app-main',
    templateUrl: './app.main.component.html',
    animations: [
        trigger('mask-anim', [
            state('void', style({
                opacity: 0
            })),
            state('visible', style({
                opacity: 0.8
            })),
            transition('* => *', animate('250ms cubic-bezier(0, 0, 0.2, 1)'))
        ])
    ]
})
export class AppMainComponent {

    menuClick: boolean;

    userMenuClick: boolean;

    topbarUserMenuActive: boolean;

    horizontal = false;

    menuActive: boolean;

    menuHoverActive: boolean;

    topbarColor = 'layout-topbar-#7B241C';

    menuColor = 'layout-menu-light';

    themeColor = 'teal';

    layoutColor = 'teal';

    topbarSize = 'medium';

    configDialogActive: boolean;

    inputStyle = 'filled';

    ripple = false;

    compactMode = false;

    desing = false;

    constructor(private menuService: MenuService, private primengConfig: PrimeNGConfig) { }

    blockBodyScroll(): void {
        if (document.body.classList) {
            document.body.classList.add('blocked-scroll');
        } else {
            document.body.className += ' blocked-scroll';
        }
    }

    unblockBodyScroll(): void {
        if (document.body.classList) {
            document.body.classList.remove('blocked-scroll');
        } else {
            document.body.className = document.body.className.replace(new RegExp('(^|\\b)' +
                'blocked-scroll'.split(' ').join('|') + '(\\b|$)', 'gi'), ' ');
        }
    }

    onWrapperClick() {
        if (!this.menuClick) {
            this.menuActive = false;

            if (this.horizontal) {
                this.menuService.reset();
            }

            this.menuHoverActive = false;
            this.unblockBodyScroll();
        }

        if (!this.userMenuClick) {
            this.topbarUserMenuActive = false;
        }

        this.userMenuClick = false;
        this.menuClick = false;
    }

    onMenuButtonClick(event: Event) {
        this.menuClick = true;

        if (!this.horizontal || this.isMobile()) {
            this.menuActive = !this.menuActive;

            if (this.menuActive) {
                this.blockBodyScroll();
            } else {
                this.unblockBodyScroll();
            }
        }

        event.preventDefault();
    }

    onTopbarUserMenuButtonClick(event) {
        this.userMenuClick = true;
        this.topbarUserMenuActive = !this.topbarUserMenuActive;

        event.preventDefault();
    }

    onTopbarUserMenuClick(event) {
        this.userMenuClick = true;

        if (event.target.nodeName === 'A' || event.target.parentNode.nodeName === 'A') {
            this.topbarUserMenuActive = false;
        }
    }

    onTopbarSubItemClick(event) {
        event.preventDefault();
    }

    onSidebarClick(event: Event): void {
        this.menuClick = true;
    }


    isMobile() {
        return window.innerWidth <= 1024;
    }

    isTablet() {
        const width = window.innerWidth;
        return width <= 1024 && width > 640;
    }

    onRippleChange(event) {
        this.ripple = event.checked;
        this.primengConfig.ripple = event.checked;
    }

    changeTopbarTheme(event, color) {
        this.topbarColor = 'layout-topbar-' + color;

        event.preventDefault();
    }

    changeMenuToHorizontal(event, mode) {
        this.horizontal = mode;

        event.preventDefault();
    }

    changeMenuTheme(event, color) {
        this.menuColor = 'layout-menu-' + color;

        event.preventDefault();
    }

    changeThemeStyle(event, compactMode) {
        let href;
        const themeLink: HTMLLinkElement = document.getElementById('theme-css') as HTMLLinkElement;
        if (compactMode) {
            href = 'assets/theme/' + 'theme-' + this.themeColor + '-compact.css';
        }
        else {
            href = 'assets/theme/' + 'theme-' + this.themeColor + '.css';
        }

        this.replaceLink(themeLink, href);

        event.preventDefault();
    }

    changeComponentTheme(event, theme) {
        let href;
        this.themeColor = theme;
        const themeLink: HTMLLinkElement = document.getElementById('theme-css') as HTMLLinkElement;

        if (this.compactMode) {
            href = 'assets/theme/' + 'theme-' + this.themeColor + '-compact.css';
        }
        else {
            href = 'assets/theme/' + 'theme-' + this.themeColor + '.css';
        }

        this.replaceLink(themeLink, href);

        event.preventDefault();
    }

    changePrimaryColor(event, color) {
        this.layoutColor = color;
        const layoutLink: HTMLLinkElement = document.getElementById('layout-css') as HTMLLinkElement;
        const href = 'assets/layout/css/layout-' + color + '.css';

        this.replaceLink(layoutLink, href);

        event.preventDefault();
    }

    isIE() {
        return /(MSIE|Trident\/|Edge\/)/i.test(window.navigator.userAgent);
    }

    replaceLink(linkElement, href) {
        if (this.isIE()) {
            linkElement.setAttribute('href', href);
        } else {
            const id = linkElement.getAttribute('id');
            const cloneLinkElement = linkElement.cloneNode(true);

            cloneLinkElement.setAttribute('href', href);
            cloneLinkElement.setAttribute('id', id + '-clone');

            linkElement.parentNode.insertBefore(cloneLinkElement, linkElement.nextSibling);

            cloneLinkElement.addEventListener('load', () => {
                linkElement.remove();
                cloneLinkElement.setAttribute('id', id);
            });
        }
    }
}
