import { OrderType } from "./ordertype.model";
import { WorkType } from "./worktype.model";

export interface Services {
    idService:     number;
    orderNumber:   string;
    dateRegister:  Date;
    status:        string;
    dealerNum:     string;
    dealerName:    string;
    reportedBy:    string;
    customerName:  string;
    customerPhone: string;
    customerEmail: string;
    address:       string;
    vin:           string;
    model:         string;
    serie:         string;
    year:          number;
    color:         string;
    dateSale:      Date;
    saleBy:        string;
    owner:         string;
    dateIn:        Date;
    dateOut:       Date;
    kilometer:     number;
    orderType:     OrderType;
    workType:      WorkType;
    description:   string;
    frt:           number;
    workforce:     number;
    total:         number;
    dateCreation:  Date;
    dateUpdate:    Date;
    obs:           string;
    bstate:        number;
}
