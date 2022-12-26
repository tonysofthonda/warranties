import { Defects } from "./defects.model";
import { PartMotorcycle } from "./part-motorcycle.model";
import { Sintoma } from "./sintoma.model";

export interface PartsDefectsSymptoms {

    id: number;
    partMotorcycle :PartMotorcycle;
    defects: Defects;
    symptom: Sintoma;
    status: boolean;
}