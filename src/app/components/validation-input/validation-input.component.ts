import { Component, Input } from '@angular/core';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-validation-input',
  templateUrl: './validation-input.component.html'
})
export class ValidationInputComponent {

     //var used to validations
     @Input() validation_messages;

     //var to control by form group
     @Input() formGroup: FormGroup;

     //var used to formcontrol name to input
     @Input() formInputName: String = '';

  constructor() { }

}
