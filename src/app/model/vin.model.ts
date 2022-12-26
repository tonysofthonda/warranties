import { Models } from './models.model';
export interface VIN {
    id: number;
    vin: number;
    engineNumber: number;
    model: Models;
}
