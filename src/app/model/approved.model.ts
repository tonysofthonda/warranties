import { ApprovedAmount } from "./approved.amount.model";
import { TotalsAmounts } from "./totals.amounts.model";

export interface Approved {
    approved: ApprovedAmount[];
    totals: TotalsAmounts[];
}