import { OrderType } from "./ordertype.model";

export interface WorkType {
    idWorkType?: number;
    name: number;
    description: string;
    orderType: OrderType;
    details: string;
    dateCreation: Date;
    dateUpdate: Date;
    obs: string;
    bstate: number;
}