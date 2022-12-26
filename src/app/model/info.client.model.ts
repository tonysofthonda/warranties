export class InfoClient {
    clientName: string;
    address: string;
    state: string;
    township: string;
    phone: string;
    email: string;

    constructor(infoClient: InfoClient) {
        this.clientName = infoClient.clientName;
        this.address = infoClient.address;
        this.state = infoClient.state;
        this.township = infoClient.township;
        this.phone = infoClient.phone;
        this.email = infoClient.email;
    }
}
