import { Locations } from "./Location.model";
import { State } from "./state.model";

export interface Dealers {

    id?: number;
	dealerNumber: number;
	name: string;
	email: string;
	phone: string;
	workTime: Date;
	type: string;
	saturday: string;
	addrStreet: string;
	addrNeigborthoot: string;
	status: boolean;
	group: Group;
	zone: Zone;
    gerenteGral: string;
    gerenteServ: string;
    state: State;
    location: Locations;
	userType: string;
}

export interface Group {
    id?: number;
    name: String;
	description: String;
}

export interface Zone {
    id?: number;
    zoneName: string;
}