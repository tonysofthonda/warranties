import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SamlCredentialsComponent } from './saml-credentials.component';

describe('SamlCredentialsComponent', () => {
  let component: SamlCredentialsComponent;
  let fixture: ComponentFixture<SamlCredentialsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SamlCredentialsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SamlCredentialsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
