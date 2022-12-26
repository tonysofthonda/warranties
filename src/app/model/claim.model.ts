import { Sintoma } from './sintoma.model';
export class createClaim {
    id?: number;
    noClaim: number;
    noServ: number;
    status: string;
    dateInit: Date;
    dataDealer: string;
    elaboratedBy: string;

    noVin: string;
    mileage: number;
    model: string;
    engineSeries: string;
    dateSale: string;
    saleBy: string;
    propietary: string;

    nameClient: string;
    addres: string;
    country: string;
    state: string;
    phone: string;
    email: string;

    typeClaim: string;
    grade: string;
    servRealized: string;
    categoriProblem;
    reparatorName: string;
    dateFailureReported: Date;
    dateRepairReported: Date;

    affectedPart: string;
    sintoma: Sintoma;
    descSintoma: Sintoma;
    technicalDiagnostics: string;
    correctiveAction: string;

}
