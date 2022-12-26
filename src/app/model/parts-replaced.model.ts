import { Parts } from "./warranty-claims.model";

export interface PartsReplaced {
    id: number;
    description: string;
    quantity: number
    packingCost: number;
    unitCost: number;
    total: number;
    part: Parts;
    warrantyClaimsId: number;
    
}