// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
    production: false,
    apiUrl: 'http://localhost:8081/warrantiesmg/',
    microserviceManagebd:  'http://localhost:8084/managerbd/api/',
    microserviceBpcs:  'http://localhost:8085/bpcs/api/',
    microserviceSales:  'http://localhost:8086/sales/api/',
    microserviceNetcommerce:  'http://localhost:8088/netcommerce/api/'
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.
