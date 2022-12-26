import { Country } from "./country.model";

export interface Models {
    id?: number;
    name: string;
    model: string;
    codModel: string;
    segment: string;
    year: string;
    vds: string;
    type: string;
    countryOrigin: Country;
    plant: Plant;
    status: boolean;
    cchp: string;
    color: string;
}

export interface Plant {
    id?: number
    name: string
}