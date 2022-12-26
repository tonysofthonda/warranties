import { InfoClient } from './info.client.model';
import { InfoReport } from './info.report.model';
import { MobileUnit } from './mobile.unit.model';
import { InfoServiceDetail } from './service.detail.model';

export class InfoCreateService {
  infoClientDto: InfoClient;
  infoReportDto: InfoReport;
  infoMobileUnitDto: MobileUnit;
  infoServiceDetailDto: InfoServiceDetail;

  constructor(infoClient: InfoClient, infoReport: InfoReport, mobileUnit: MobileUnit, infoServiceDetail: InfoServiceDetail) {
    this.infoClientDto = infoClient;
    this.infoReportDto = infoReport;
    this.infoMobileUnitDto = mobileUnit;
    this.infoServiceDetailDto = infoServiceDetail;
 }
}
