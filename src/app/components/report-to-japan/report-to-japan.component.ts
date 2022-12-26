import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { HeaderTable } from 'src/app/model/headerTable.model';
import { JapanReport } from 'src/app/model/japan-report.model';

@Component({
  selector: 'app-report-to-japan',
  templateUrl: './report-to-japan.component.html',
  styleUrls: ['./report-to-japan.component.scss']
})
export class ReportToJapanComponent implements OnInit {

  dataForm: FormGroup;
  heardes: HeaderTable[];
  reports: JapanReport[] = [];
  loading: boolean;

  constructor(private fomb: FormBuilder) { }

  ngOnInit(): void {
    this.heardes = [
      { label: 'No', SortableColumn: 'No' },
      { label: 'Reporte', SortableColumn: 'Reporte' },
      { label: 'Fichero', SortableColumn: 'Fichero' },
      { label: 'Usuario', SortableColumn: 'Usuario' },      
      { label: 'Acciones', SortableColumn: 'Acciones'}
    ]
    this.formData();

  }

  formData() {
    this.dataForm = this.fomb.group({      
      dateInit: [''],
      dateFinal: ['']      
    });
  }

}
