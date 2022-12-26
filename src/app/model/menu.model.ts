export interface MenuCategory {
    id: number;
    name: string;
    order: number;
    views: View[];
}

export interface View {
    id: number;
    name: string;
    friendlyName: string;
    route: string;
    order: number;    
}
