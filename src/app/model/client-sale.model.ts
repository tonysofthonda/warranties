import { Country } from './country.model';
import { Dealers } from './dealer.model'; 
import { VIN } from './vin.model';

export interface ClientSale {
    id: number;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    addrStreet: string;
    dateRegister: string;
    typeClient: TypeClient;
    vin: VIN;
    country: Country;
    dealer: Dealers;
}

export interface TypeClient {
    id: number
    nameType: string;
    description: string;
}
