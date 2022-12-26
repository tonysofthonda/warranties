import { AppValidationMessagesService } from '../../../utils/app-validation-messages.service';
import { DealerService } from '../../../services/dealer.service';
import { Dealers, Group, Zone } from './../../../model/dealer.model';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit} from '@angular/core';
import { MessageService, SelectItem } from 'primeng/api';
import { HeaderTable } from '../../../model/headerTable.model';
import { StateService } from 'src/app/services/state.service';
import { LocationService } from 'src/app/services/localtion.service';

@Component({
  selector: 'app-dealer',
  templateUrl: './dealer.component.html'
})
export class DealerComponent implements OnInit {

  heardes: HeaderTable[];
  dealerHeader: Dealers[] = [];
  dealerDrown: Dealers[] = [];
  groupDrown: SelectItem[] = [];
  zoneDrown: SelectItem[] = [];
  stateDrown: SelectItem[] = [];
  locationsDrown: SelectItem[] = [];
  locationsDrownTemp: SelectItem[] = [];
  validations = [];
  selectGroup: any;
  selectZone: any;
  selectState: any;
  selectLocation: any;
  selectedDealers: Dealers;
  datosForm: FormGroup;
  display: boolean;
  edit: Boolean;
  typeAccion: Boolean;
  showGroup: Boolean;
  showZone: Boolean;
  title: string;
  field: string;
  stateOptions: any[];
  loading: boolean;
  regexNotSpecialChar: string = '^[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9]+[a-zA-ZÀ-ÿ\u00f1\u00d1,.0-9\\s]*$';
  regexNotSpecialCharAddress: string = '^[a-zA-Z0-9-.]+[a-zA-Z0-9-.\\s]*$';
  regexChar: string = '^[a-zA-Z]+[a-zA-Z\\s]*$';
  regexNumeric: string = '^[0-9+]*$';
  regexDate: string = '^[0-9-:]+$';

  constructor(private dealerServ: DealerService, private msjeService: MessageService,
    private formb: FormBuilder, public messages: AppValidationMessagesService, private stateService: StateService,
    private locationService: LocationService) { }

  ngOnInit(): void {
    this.formData();
    this.getAllDealer();
    this.getGroups();
    this.getZone();
    this.getState();
    this.getValidations();
    this.heardes = [
      { label: 'Distribuidor', SortableColumn: 'Distribuidor' },
      { label: 'Nombre', SortableColumn: 'Nombre' },
      { label: 'Tipo De Distribuidor', SortableColumn: 'userType' },
      { label: 'Grupo', SortableColumn: 'Grupo' },
      { label: 'Zona', SortableColumn: 'Zona' },
      { label: 'Estatus', SortableColumn: 'Estatus' },
      { label: 'Acciones', SortableColumn: 'Acciones' },
    ]
    this.stateOptions = [{label: 'Inactivo', value: false}, {label: 'Activo', value: true}];
  }

  getAllDealer() {
    this.loading = true;
    this.dealerServ.getAllDealers().subscribe((resp: any) => {      
      if(resp){
        this.dealerHeader = resp;
        this.loading = false;
      }
    })
  }

  getGroups() {
    this.dealerServ.getAllGroups().subscribe((resp: any) => {
      this.groupDrown = resp.map(r => (
        { label: r.name, value: r.id }
      ));
    })
  }

  getZone() {
    this.dealerServ.getAllZone().subscribe((resp: any) => {
      this.zoneDrown = resp.map(r => (
        { label: r.zoneName, value: r.id }
      ));
    })
  }

  getState() {
    this.stateService.getAllStates().subscribe((resp: any) => {
      this.stateDrown = resp.map(r => (
        { label: r.name, value: r.id }
      ));
    });
  }

  getLocations() {
    this.locationService.getAllLocations().subscribe((resp: any) => {
      this.locationsDrown = resp.map(r => (
        { label: r.name, value: r.id }
      ));
      this.locationsDrownTemp = this.locationsDrown;
    });
  }

  onChangeState(state: any) {
    this.locationService.getAllLocationsByState(state).subscribe((resp: any) => {
      this.locationsDrown = resp.map(r => (
        { label: r.name, value: r.id }
      ));
    });
  }

  showHideButtonGroup() {
    this.field = 'group';
    this.showGroup = this.showGroup ? false : true;
    this.datosForm.get('group').setValue('');
  }

  showHideButtonZone() {
    this.field = 'zone';
    this.showZone = this.showZone ? false : true;
  }

  addGroupOrZone() {
    if (this.field === 'group') {
      let grupoNew: Group = {
        name: this.datosForm.get('group').value,
        description:''
      }
      if (grupoNew.name != null && grupoNew.name != undefined && grupoNew.name != "") {
        this.dealerServ.saveGroup(grupoNew).subscribe(
          resp => {
            this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
            this.showGroup = false;
            this.getGroups();
          }
        );
      } err => {
        this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al guardar el distribuidor' });
      }
      this.showGroup = false;
    } else if (this.field === 'zone') {
      let zoneNew: Zone = {
        zoneName: this.datosForm.get('zone').value
      }
      if (zoneNew.zoneName != null && zoneNew.zoneName != undefined && zoneNew.zoneName != "") {
        this.dealerServ.saveZone(zoneNew).subscribe(resp => {
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: `${'Se guardo ' + zoneNew.zoneName + ' exitosamente'}` });
          this.showZone = false;
          this.getZone();
        });
      }
    }
    this.showZone = false;
  }

  addDealer() {
    this.title = 'Agregar distribuidor';
    this.display = true;
    this.datosForm.reset();
    this.datosForm.patchValue({status: true});
    this.typeAccion = true;
  }

  editDealer(dealerEdit: Dealers) {
    let value = dealerEdit.location.state.id;
    this.onChangeState(value);
    setTimeout(() => {
    this.title = 'Editar distribuidor';
    this.display = true;
    this.typeAccion = false;
    this.selectGroup = this.groupDrown.find(x => x.value === dealerEdit.group.id);
    this.selectZone = this.zoneDrown.find(x => x.value === dealerEdit.zone.id);
    this.selectState = this.stateDrown.find(x => x.value === dealerEdit.location.state.id);
    this.selectLocation = this.locationsDrown.find(x => x.value === dealerEdit.location.id);
    let userType = (dealerEdit.userType === 'ADMIN') ? true : false; 
    this.datosForm.get('location').setValue(this.selectLocation);
    this.datosForm.patchValue({
      id: dealerEdit.id,
      dealerNumber: dealerEdit.dealerNumber,
      name: dealerEdit.name,
      email: dealerEdit.email,
      phone: dealerEdit.phone,
      worktime: dealerEdit.workTime,
      type: dealerEdit.type,
      saturday: dealerEdit.saturday,
      addrStr: dealerEdit.addrStreet,
      addrHeig: dealerEdit.addrNeigborthoot,
      status: dealerEdit.status,
      gerenteGral: dealerEdit.gerenteGral,
      gerenteServ: dealerEdit.gerenteServ,
      group: this.selectGroup,
      zone: this.selectZone,
      state: this.selectState,
      location: this.selectLocation,
      userType: userType,
    });
    this.datosForm.markAllAsTouched();
  }, 500);
  }

  saveDealer() {
    if (this.datosForm.valid === true) {
      let userType = (this.datosForm.get('userType').value) ? 'ADMIN' : 'DEALER';
      let dealerNew: Dealers = {
        id: this.datosForm.get('id').value,
        dealerNumber: this.datosForm.get('dealerNumber').value,
        name: this.datosForm.get('name').value,
        email: this.datosForm.get('email').value,
        phone: this.datosForm.get('phone').value,
        workTime: this.datosForm.get('worktime').value,
        type: this.datosForm.get('type').value,
        saturday: this.datosForm.get('saturday').value,
        addrStreet: this.datosForm.get('addrStr').value,
        gerenteGral: this.datosForm.get('gerenteGral').value,
        gerenteServ: this.datosForm.get('gerenteServ').value,
        addrNeigborthoot: this.datosForm.get('addrHeig').value,
        state: this.datosForm.get('state').value,
        status: this.datosForm.get('status').value,
        group: {
          id: this.datosForm.get('group').value.value,
          name: this.datosForm.get('group').value.label,
          description:''
        },
        zone: {
          id: this.datosForm.get('zone').value.value,
          zoneName: this.datosForm.get('zone').value.label,
        },
        location: {
          id: this.datosForm.get('location').value.value,
          name: this.datosForm.get('location').value.label,
          state: this.datosForm.get('state').value
        },
        userType: userType,
      }

      this.dealerServ.saveDealer(dealerNew).subscribe(
        resp => {
          this.getAllDealer();
          this.display = false;
          this.datosForm.reset();
          this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡GUARDADO!', detail: 'Se guardó exitosamente' });
        }
      ), (error: any) => {
        this.msjeService.add({ key: 'tst', severity: 'error', summary: '¡ERROR!', detail: 'Error al guardar el distribuidor' });
      };
    }
  }

  formData() {
    this.datosForm = this.formb.group({
      id: [''],
      dealerNumber: ['', [Validators.required, Validators.minLength(1), Validators.maxLength(5),
      Validators.pattern(this.regexNumeric)]],
      name: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(200),
      Validators.pattern(this.regexChar)]],
      email: ['', [Validators.required, Validators.email]],
      phone: ['', [Validators.required, Validators.minLength(10), Validators.maxLength(13), Validators.pattern(this.regexNumeric)]],
      worktime: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(50), Validators.pattern(this.regexDate)]],
      type: ['', [Validators.required, Validators.maxLength(100), Validators.pattern(this.regexChar)]],
      saturday: ['', [Validators.minLength(2), Validators.maxLength(50), Validators.pattern(this.regexDate)]],
      status: [''],
      addrStr: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(200), Validators.pattern(this.regexNotSpecialCharAddress)]],
      addrHeig: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(200), Validators.pattern(this.regexNotSpecialCharAddress)]],
      group: ['', [Validators.required, Validators.maxLength(50)]],
      zone: ['', [Validators.required, Validators.maxLength(15)]],
      gerenteGral: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(200)]],
      gerenteServ: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(200)]],
      state: ['', [Validators.required,]],
      location: ['', [Validators.required]],
      userType: [''],
    });
  }

  updateStatus(dealer: Dealers){
    this.dealerServ.updateStatus(dealer).subscribe((resp: any) => {
      this.getAllDealer();
        this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
    }, err => {
        this.msjeService.add({ key: 'tst', severity: 'success', summary: '¡ERROR!', detail: `${err}` });
    });
  }

  getValidations() {
    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '5';
    this.messages.messagesMinLenght = '1';
    this.messages.messagesPattern = 'Ingresar solo digitos';
    this.validations.push(this.messages.getValidationMessagesWithName('dealerNumber'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '200';
    this.messages.messagesMinLenght = '3';
    this.messages.messagesPattern = 'Ingresar solo caracteres de tipo texto'
    this.validations.push(this.messages.getValidationMessagesWithName('name'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesPattern = 'Ingresar solo caracteres de tipo texto'
    this.validations.push(this.messages.getValidationMessagesWithName('type'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '20';
    this.messages.messagesMinLenght = '3';
    this.validations.push(this.messages.getValidationMessagesWithName('worktime'));

    this.messages.messagesMaxLenght = '20';
    this.messages.messagesMinLenght = '2';
    this.validations.push(this.messages.getValidationMessagesWithName('saturday'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '50';
    this.messages.messagesPattern = 'Nombre no valido'
    this.validations.push(this.messages.getValidationMessagesWithName('group'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '15';
    this.messages.messagesPattern = 'Nombre no valido'
    this.validations.push(this.messages.getValidationMessagesWithName('zone'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '200';
    this.messages.messagesPattern = 'Nombre no valido';
    this.messages.messagesMinLenght = '3';
    this.validations.push(this.messages.getValidationMessagesWithName('gerenteGral'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '200';
    this.messages.messagesMinLenght = '3';
    this.messages.messagesPattern = 'Nombre no valido';
    this.validations.push(this.messages.getValidationMessagesWithName('gerenteServ'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '200';
    this.messages.messagesMinLenght = '3';
    this.messages.messagesPattern = 'Calle invalida';
    this.validations.push(this.messages.getValidationMessagesWithName('addrStr'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '200';
    this.messages.messagesMinLenght = '3';
    this.messages.messagesPattern = 'Colonia invalida';
    this.validations.push(this.messages.getValidationMessagesWithName('addrHeig'));

    this.messages.messagesRequired = 'true';
    this.validations.push(this.messages.getValidationMessagesWithName('state'));

    this.messages.messagesRequired = 'true';
    this.validations.push(this.messages.getValidationMessagesWithName('location'));

    this.messages.messagesRequired = 'true';
    this.messages.messagesMaxLenght = '13';
    this.messages.messagesMinLenght = '10';
    this.messages.messagesPattern = '';
    this.validations.push(this.messages.getValidationMessagesWithName('phone'));

    this.messages.messagesRequired = 'true';
    this.validations.push(this.messages.getValidationMessagesWithName('email'));

  }

}
