import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MessageService, SelectItem } from 'primeng/api';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { Site } from 'src/app/model/sites.model';
import { SitesService } from 'src/app/services/sites.service';

@Component({
  selector: 'app-sites',
  templateUrl: './sites.component.html',
  styles: ['.p-dialog .p-dialog-header { background-color: #f33412 !important; }']
})
export class SitesComponent implements OnInit {

  stateHeader: HeaderTable[];
  stateData: Site[] = [];
  townshipHeader: HeaderTable[];
  townshipData: Site[] = [];
  areaHeader: HeaderTable[];
  areaData: Site[] = [];
  groupHeader: HeaderTable[];
  groupData: Site[] = [];
  display: boolean = false;
  datosForm: FormGroup;
  type: string;
  stateDrown: SelectItem[] = [];
  hiddenStateDrown: boolean = true;
  title: string = '';
  loadingGroup: boolean;
  loadingTownship: boolean;
  loadingArea: boolean;
  loadingState: boolean;
  inputName: string;

  constructor(private sitesService: SitesService,
              private formBuilder: FormBuilder,
              private messageService: MessageService,
    ) { }

  ngOnInit(): void {
    this.stateHeader = [
        { label: 'Número', SortableColumn: 'number' },
        { label: 'Estado', SortableColumn: 'name' },
        { label: 'Estatus', SortableColumn: 'status' },
        { label: 'Acciones', SortableColumn: 'Acciones' }
    ];

    this.townshipHeader = [
        { label: 'Número', SortableColumn: 'number' },
        { label: 'Municipio', SortableColumn: 'name' },
        { label: 'Estatus', SortableColumn: 'status' },
        { label: 'Acciones', SortableColumn: 'Acciones' }
    ];

    this.areaHeader = [
        { label: 'Número', SortableColumn: 'number' },
        { label: 'Región', SortableColumn: 'name' },
        { label: 'Estatus', SortableColumn: 'status' },
        { label: 'Acciones', SortableColumn: 'Acciones' }
    ];

    this.groupHeader = [
        { label: 'Número', SortableColumn: 'number' },
        { label: 'Grupo', SortableColumn: 'name' },
        { label: 'Estatus', SortableColumn: 'status' },
        { label: 'Acciones', SortableColumn: 'Acciones' }
    ];

    this.datosForm = this.formBuilder.group({
        name: ['', Validators.required],
        number: [''],
        state: [''],
        status: ['', Validators.required]
    });

    this.getStates();
    this.getTownship();
    this.getArea();
    this.getGroup();
  }

  updateSite(site: Site, type: string) {
    if (site) {
      site.type = type;
      this.type = type;
      this.sitesService.putUpdate(site).subscribe((response) => {
        this.refreshTable(this.type);
        this.messageService.add({ key: 'tst', severity: 'success', summary: '¡ACTUALIZADO!', detail: 'Se actualizó exitosamente' });
      }, err => {
        this.refreshTable(this.type);
        this.messageService.add({ key: 'tst', severity: 'success', summary: '¡ERROR!', detail: `${err}` });
      });
    }
  }

  getStates() {
    this.sitesService.getAllStates().subscribe(data => {
        this.stateData = data;
        this.stateDrown = data.map(r => (
          { label: r.name, value: r.number }
        ));
    });
  }

  getTownship() {
    this.loadingTownship = true;
    this.sitesService.getAllTownship().subscribe(data => {
        this.townshipData = data;
        this.loadingTownship = false;
    });
  }

  getArea() {
    this.loadingArea = true;
    this.sitesService.getAllArea().subscribe(data => {
        this.areaData = data;
        this.loadingArea = false;
    });
  }

  getGroup() {
    this.loadingGroup = true;
    this.sitesService.getAllGroup().subscribe(data => {
        this.groupData = data;
        this.loadingGroup = false;
    });
  }

  addSite(type: string) {
    this.title = 'AGREGAR';
    this.setInputName(type);
    this.hiddenStateDrown = (type === 'township') ? true : false;
    this.display = true;
    this.type = type;
    this.datosForm.patchValue({status: true});
  }

  saveSite() {
    let site = new Site();
    site.type = this.type;
    site.name = this.datosForm.get('name').value;
    if(this.hiddenStateDrown)
      site.idState = (this.datosForm.get('state').value !== '') ? this.datosForm.get('state').value.value : null;
    site.status =  this.datosForm.get('status').value;
    site.number = this.datosForm.get('number').value !== '' ? this.datosForm.get('number').value : null;
    if(site.type === 'area') {
      if(site.name.length > 15) {
        this.messageService.add({ key: 'tst', severity: 'error', summary: 'Error', detail: 'El nombre de la region debe ser menor a 15 caracteres' });
      }
    }
    if(this.title === 'AGREGAR') {
      this.sitesService.postSave(site).subscribe(data => {
        this.refreshTable(this.type);
        this.display = false;
        this.datosForm.reset();
        this.type = '';
        this.messageService.add({ key: 'tst', severity: 'success', summary: 'Guardo', detail: 'Se guardo correctamente el registro' });
      }), error => {
        this.messageService.add({ key: 'tst', severity: 'error', summary: 'Error', detail: error });
      };
    } else {
      this.sitesService.putUpdate(site).subscribe(data => {
        this.refreshTable(this.type);
        this.display = false;
        this.datosForm.reset();
        this.type = '';
        this.messageService.add({ key: 'tst', severity: 'success', summary: 'Actualizo', detail: 'Se actualizo correctamente el registro' });
      }), error => {
        this.messageService.add({ key: 'tst', severity: 'error', summary: 'Error', detail: error });
      };
    }
    
  }

  editSite(type: string, sites: Site) {
    this.title = 'EDITAR';
    this.display = true;
    this.setInputName(type);
    this.hiddenStateDrown = type === 'township' ? true : false;
    this.type = type;
    this.datosForm.patchValue(sites);
    this.datosForm.markAllAsTouched(); 
  }

  refreshTable(type: string) {
    if(type === 'group') {
      this.getGroup();
    }
    if(type === 'township') {
      this.getTownship();  
    }
    if(type === 'area') {
      this.getArea();  
    }
    if(type === 'state') {
      this.getStates();  
    }
  }
   setInputName(type: string) {
    if(type === 'group') {
      this.inputName = 'Grupo';
    }
    if(type === 'township') {
      this.inputName = 'Municipio';
    }
    if(type === 'area') {
      this.inputName = 'Región'; 
    }
    if(type === 'state') {
      this.inputName = 'Estado'; 
    }
   }
}
