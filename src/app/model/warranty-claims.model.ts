import { Resources } from './resources.model';
import { ClientOwner } from './client-owner.model';
import { ClientSale } from "./client-sale.model";
import { TypeClaims } from './type-claims.model';
import { GradeType } from './grade-type.model';

export interface WarrantyClaims {
    id: number;
    numberClaims: string;
    kilometros: number;
    noServOrder: String;
    dateFail: string;
    dateRepair: string;
    technicianName: string;
    dateRegister: string;
    dateUpdate: string;
    status: string;
    totalParts: string;
    totalDesembarque: number;
    mohours: number;
    costSpareParts: number;
    totalFrt: number;
    totalPartsFrt: number;
    typeClaim: TypeClaims;
    grade: GradeType;
    partsReplaced: PartsReplaced[];
    clientSale: ClientSale[];
    clientOwnwer: ClientOwner[];
    otherExpenses: OtherExpenses[];
    resources: Resources[];
}


export interface PartsReplaced {
    id: number;
    description: string;
    quantity: number
    packingCost: number;
    unitCost: number;
    total: number;
    parts: Parts[];
}

export interface Parts {
    id: number;
    partNumber: string;
    descriptionPart: string;
    refNo: string;
    price: number;
    frt: number;
    model: string;
}

export interface OtherExpenses {
    id: number;
    description: string;
    quantity: number;
    details: string;
    invoice: number;
    cost: number;
    total: number;
    warrantyClaims: number;
}

export interface CostDetail {
    laborTime: number;
    laborCost: number;
    totalLaborTime: number;
    sparePartCost: number;
    landingCost: number;
    totalSparePart: number;
    totalSparePartAndLabor: number;
}
